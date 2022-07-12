package es.icp.icp_commons.Helpers

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.*
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.android.volley.Request
import com.google.android.gms.location.*
import es.icp.icp_commons.Services.*
import org.json.JSONObject

class LocationService() : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private var enviar : Boolean? = false
    private var url : String? = ""
    private var intervalParameter : Long = 0
    private var objecto : JSONObject? = null
    private var cdt : CountDownTimer? = null
    private var intentService : Intent? = null
    private var duration : Long = 0
    private var distance : Float = 0f

    private var locationRequest: LocationRequest? = null


    private var locationCallback: LocationCallback = object : LocationCallback() {


        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                Log.d("Location Latitude", location.latitude.toString())
                Log.i("Location longitud", location.longitude.toString())
                if (enviar!= false) {
                    guardarDatosRepo(url!!, location, objecto!!, applicationContext)
                }

            }
        }
    }

    fun guardarDatosRepo(url: String, location : Location , jsonObject : JSONObject, context: Context) {
        try {
            var latitud = location.latitude.toString()
            var longitud = location.longitude.toString()

            jsonObject.put("LATITUD", latitud)
            jsonObject.put("LONGITUD", longitud)

            WebServiceKt.procesarRequest(context, jsonObject, Request.Method.POST, object : Ws_Callback {
                override fun online(response: DefaultResult) {
                    if (response.RETCODE == 0) {
                        Log.i("Location Llamada", "llamada realizada con éxito")
                    }
                }

                override fun offline() {
                    Log.e("Location Llamada", "La llamada no ha podido realizarse")
                }

            }, url)
        } catch (ex : Exception) {
            ex.printStackTrace()
        }
    }

    override fun onCreate() {
        super.onCreate()

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) createNotificationChanel() else startForeground(
            1,
            Notification()
        )

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChanel() {
        val notificationChannelId = "Location channel id"
        val channelName = "Background Service"
        val chan = NotificationChannel(
            notificationChannelId,
            channelName,
            NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val manager = (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)
        val notificationBuilder =
            NotificationCompat.Builder(this, notificationChannelId)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setContentTitle("Location")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(2, notification)

    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent != null) {
            intentService = intent
            try {
                enviar = intent.getBooleanExtra("ENVIAR", false)
                url = intent.getStringExtra("URL").toString()
                objecto = JSONObject(intent.getStringExtra("JsonObject"))
                intervalParameter = intent.getIntExtra("INTERVAL",Constantes.INTERVAL_TIME).toLong()
                duration = intent.getIntExtra("DURATION", 0).toLong()
                distance = intent.getFloatExtra("DISTANCE",0F)

            } catch (ex : Exception) {
                enviar = false
                url = ""
                objecto = JSONObject()
                intervalParameter = Constantes.INTERVAL_TIME.toLong()
                duration = Constantes.DURATION_DEFAULT.toLong()
                distance = Constantes.DISTANCE_DEFAULT

            }

            checkPermissionsAndStart()

        }
        return START_STICKY
    }

    private fun checkPermissionsAndStart() {
        locationRequest = LocationRequest.create().apply {
            interval = intervalParameter
            fastestInterval = intervalParameter
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            maxWaitTime = intervalParameter
            smallestDisplacement = distance
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            Log.w("PERMISO REQUERIDO", "se requiere del permiso")
            return
        } else {
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

        cdt = object : CountDownTimer(duration,1000){
            override fun onTick(p0: Long) {
                //Log.w("++++++++++++", p0.toString())
            }

            override fun onFinish() {
                fusedLocationClient.removeLocationUpdates(locationCallback)
                stopService(intentService)
            }
        }

        if (duration!=0L) {
            cdt!!.start()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
        Log.w("Location Service", "FINISHED SERVICE")
        cdt!!.cancel()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}