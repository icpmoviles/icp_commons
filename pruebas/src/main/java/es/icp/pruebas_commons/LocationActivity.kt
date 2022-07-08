package es.icp.pruebas_commons

import android.Manifest
import android.app.AlertDialog
import android.content.ComponentName
import android.content.DialogInterface
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.ViewModelProvider
import es.icp.icp_commons.Extensions.requestBackgroundLocationPermission
import es.icp.icp_commons.Extensions.requestFineLocationPermission
import es.icp.icp_commons.Helpers.LocationService
import es.icp.icp_commons.Utils.Utils
import es.icp.icp_commons.Utils.UtilsKt
import org.json.JSONObject

class LocationActivity : AppCompatActivity() {
    lateinit var mLocationService: LocationService
    lateinit var mServiceIntent: Intent
    var localizaciones: MutableList<Location> = ArrayList()
    private var mBound: Boolean = false
    var contador = 0
    lateinit var startServiceBtn: TextView
    lateinit var stopServiceBtn: TextView
    lateinit var btnSummar: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_location)
        mLocationService = LocationService()
        startServiceBtn = findViewById(R.id.start_service_btn)
        stopServiceBtn = findViewById(R.id.stop_service_btn)
        btnSummar = findViewById(R.id.btnSumar)

        startServiceBtn.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED
            ) {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                        != PackageManager.PERMISSION_GRANTED
                    ) {

                        AlertDialog.Builder(this).apply {
                            setTitle("Background permission")
                            setMessage(R.string.background_location_permission_message)
                            setPositiveButton("Start service anyway",
                                DialogInterface.OnClickListener { dialog, id ->
                                    starServiceFunc()
                                })
                            setNegativeButton("Grant background Permission",
                                DialogInterface.OnClickListener { dialog, id ->
                                    requestBackgroundLocationPermission()
                                })
                        }.create().show()

                    } else if (ActivityCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_BACKGROUND_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        starServiceFunc()
                    }
                } else {
                    starServiceFunc()
                }

            } else if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                ) {
                    AlertDialog.Builder(this)
                        .setTitle("ACCESS_FINE_LOCATION")
                        .setMessage("Location permission required")
                        .setPositiveButton(
                            "OK"
                        ) { _, _ ->
                            requestFineLocationPermission()
                        }
                        .create()
                        .show()
                } else {
                    requestFineLocationPermission()
                }
            }

        }

        stopServiceBtn.setOnClickListener {
            stopServiceFunc()
        }

        btnSummar.setOnClickListener {
            contador++
        }


    }

    private fun starServiceFunc() {
        mLocationService = LocationService()
        mServiceIntent = Intent(this, mLocationService.javaClass)

        mServiceIntent.apply {
            putExtra("URL", "url de ejemplo")
            putExtra("JsonObject", JSONObject().toString())
            putExtra("ENVIAR", false)
            putExtra("INTERVAL",5000)
            putExtra("DURATION", 60000)
            //putExtra("DISTANCE", 0F)
        }

        if (!UtilsKt.isMyServiceRunning(mLocationService.javaClass, this)) {
            startService(mServiceIntent)

            Toast.makeText(this, "El servicio ha empezado con éxito", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "El servicio está corriendo", Toast.LENGTH_SHORT).show()
        }
    }

    private fun stopServiceFunc() {
        mLocationService = LocationService()
        mServiceIntent = Intent(this, mLocationService.javaClass)
        if (UtilsKt.isMyServiceRunning(mLocationService.javaClass, this)) {
            stopService(mServiceIntent)
            Toast.makeText(this, "Service stopped!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Service is already stopped!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
//         if (::mServiceIntent.isInitialized) {
//              stopService(mServiceIntent)
//          }
        super.onDestroy()
    }

    private fun requestBackgroundLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_BACKGROUND_LOCATION), MY_BACKGROUND_LOCATION_REQUEST
        )
    }

    private fun requestFineLocationPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            MY_FINE_LOCATION_REQUEST
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        Toast.makeText(this, requestCode.toString(), Toast.LENGTH_LONG).show()
        when (requestCode) {
            MY_FINE_LOCATION_REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        requestBackgroundLocationPermission()
                    }

                } else {
                    Toast.makeText(
                        this,
                        "permiso denegado ACCESS_FINE_LOCATION ",
                        Toast.LENGTH_LONG
                    ).show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    ) {
                        startActivity(
                            Intent(
                                Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                Uri.fromParts("package", this.packageName, null),
                            ),
                        )
                    }
                }
                return
            }
            MY_BACKGROUND_LOCATION_REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(
                            this,
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
                        Toast.makeText(
                            this,
                            "Permiso concedido Background location",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(this, "Background location permission denied", Toast.LENGTH_LONG)
                        .show()
                }
                return
            }
        }
    }

    companion object {
        private const val MY_FINE_LOCATION_REQUEST = 99
        private const val MY_BACKGROUND_LOCATION_REQUEST = 100
    }
}