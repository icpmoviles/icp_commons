package es.icp.icp_commons.Helpers

import android.Manifest
import android.app.*
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import com.android.volley.Request
import com.google.android.gms.location.*
import es.icp.icp_commons.CheckRequest
import es.icp.icp_commons.Services.DefaultResult
import es.icp.icp_commons.Services.WebService
import es.icp.icp_commons.Services.WebServiceKt
import es.icp.icp_commons.Services.Ws_Callback
import org.json.JSONObject

class LocationService() : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var _locationsListFinal : MutableList<Location> = ArrayList()
    val locationsListFinal get() = _locationsListFinal
    var enviar : Boolean = false
    var url : String = ""
    var objecto : JSONObject? = null

    private val locationRequest: LocationRequest = LocationRequest.create().apply {
        interval = Constantes.INTERVAL_TIME
        fastestInterval = Constantes.FASTEST_INTERVAL_TIME
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        maxWaitTime = Constantes.INTERVAL_TIME
    }


    private var locationCallback: LocationCallback = object : LocationCallback() {


        override fun onLocationResult(locationResult: LocationResult) {
            val locationList = locationResult.locations
            if (locationList.isNotEmpty()) {
                val location = locationList.last()
                locationsListFinal += locationList
                Toast.makeText(
                    this@LocationService, "Latitud: " + location.latitude.toString() + '\n' +
                            "Longitud: " + location.longitude, Toast.LENGTH_LONG
                ).show()
                Log.d("+++++++++++++++++Location d", location.latitude.toString())
                Log.i("+++++++++++++++++Location i", location.longitude.toString())
                if (enviar) {
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
                        Log.i("LLAMADA", "llamada realizada con éxito")
                    }
                }

                override fun offline() {
                    Log.e("ERROR", "La llamada no ha podido realizarse")
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {

            Toast.makeText(applicationContext, "Permiso requerido", Toast.LENGTH_LONG).show()
            return
        } else {
            fusedLocationClient?.requestLocationUpdates(
                locationRequest,
                locationCallback,
                Looper.getMainLooper()
            )
        }

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
            .setContentTitle("Location updates:")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .build()

        startForeground(2, notification)

    }



    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        if (intent != null) {
            enviar = intent.getBooleanExtra("ENVIAR", false)
            url = intent.getStringExtra("URL").toString()
            objecto = JSONObject(intent.getStringExtra("JsonObject"))
        }
        return START_STICKY
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


}