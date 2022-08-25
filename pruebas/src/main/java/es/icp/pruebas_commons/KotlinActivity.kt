package es.icp.pruebas_commons

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import es.icp.icp_commons.Extensions.*
import es.icp.icp_commons.Helpers.Constantes
import es.icp.icp_commons.Helpers.Constantes.MY_FINE_LOCATION_REQUEST
import es.icp.icp_commons.Helpers.LocationService
import es.icp.icp_commons.Utils.UtilsKt
import es.icp.icp_commons.simplesearchview.SimpleSearchView
import es.icp.icp_commons.simplesearchview.utils.DimensUtils.convertDpToPx
import es.icp.pruebas_commons.databinding.ActivityKotlinBinding
import es.icp.pruebas_commons.implementaciones.DxImplementacion

class KotlinActivity : AppCompatActivity() {

    private lateinit var binding: ActivityKotlinBinding
    private lateinit var context: Context
    private lateinit var mLocationService : LocationService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mLocationService = LocationService()
        binding = ActivityKotlinBinding.inflate(layoutInflater)
        setContentView(binding.root)
        context = this

        setSupportActionBar(binding.toolbar)
        setUpView()
    }

    private fun setUpView() = with(binding) {
        btnEmpezarCoordenadas.setOnClickListener {
            if (checkAccessFineLocationPermission(context)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    if (!checkBackgroundLocationPermission(context)) {
                        AlertDialog.Builder(context).apply {
                            setTitle("Permisos Segundo plano")
                            setMessage(es.icp.icp_commons.R.string.background_location_permission_message)
                            setPositiveButton("Empezar servicio" , object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    starServiceFunc(context)
                                }
                            })
                            setNegativeButton("Dar permisos en segundo plano", object : DialogInterface.OnClickListener {
                                override fun onClick(p0: DialogInterface?, p1: Int) {
                                    requestFineLocationPermission(context)
                                }

                            })
                        }.create().show()
                    } else if (checkBackgroundLocationPermission(context)) {
                        starServiceFunc(context)
                    } else{
                        starServiceFunc(context)
                    }
                }
            } else if (!checkAccessFineLocationPermission(context)) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(this@KotlinActivity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    AlertDialog.Builder(this@KotlinActivity)
                        .setTitle("ACCESS_FINE_LOCATION")
                        .setMessage("Location permission required")
                        .setPositiveButton(
                            "OK"
                        ) { _, _ ->
                            requestFineLocationPermission(context)
                        }
                        .create()
                        .show()
                } else {
                    requestFineLocationPermission(context)
                }
            }
        }

        btnPararCoordenadas.setOnClickListener {
            stopServiceFunc(context)
        }

        btnMostrarDxCustomEjemplo.setOnClickListener {

            val onAccept = {
                Toast.makeText(context, "Boton aceptar/SI pulsado.", Toast.LENGTH_SHORT).show()
            }

            val onCancel = {
                Toast.makeText(context, "Boton cancelar pulsado.", Toast.LENGTH_SHORT).show()
            }

            DxImplementacion.mostrarDxEjemplo(
                context,
                onAccept,
                onCancel
            )

        }

        btnMostrarDxCustomEjemploConCustomView.setOnClickListener {

            val onCancel = {
                Toast.makeText(context, "Boton cancelar pulsado.", Toast.LENGTH_SHORT).show()
            }

            DxImplementacion.mostrarDxEjemploConCustomView(
                context,
                onAccept = {

                    val nombre = it.txtInputNombre.text

                    Toast.makeText(context, "Boton aceptar/SI pulsado. Nombre: $nombre", Toast.LENGTH_SHORT).show()

                },
                onCancel
            )

            Log.d(":::", getColor(R.color.colorPrimary).toString())

        }

    }

    fun starServiceFunc(context: Context) {
        var mLocationService = LocationService()
        var mServiceIntent = Intent(context, mLocationService.javaClass)
        if (!UtilsKt.isMyServiceRunning(mLocationService.javaClass, this)) {
            startService(mServiceIntent)
            Toast.makeText(context, "Service started successfully", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Service is already running", Toast.LENGTH_SHORT).show()
        }
    }

    fun stopServiceFunc(context: Context) {
        var mLocationService = LocationService()
        var mServiceIntent = Intent(context, mLocationService.javaClass)
        if (UtilsKt.isMyServiceRunning(mLocationService.javaClass, this)) {
            stopService(mServiceIntent)
            Toast.makeText(context, "Service stopped!!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "Service is already stopped!!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_FINE_LOCATION_REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        requestBackgroundLocationPermission(context)
                    }

                } else {
                    Toast.makeText(this, "ACCESS_FINE_LOCATION permission denied", Toast.LENGTH_LONG).show()
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                        startActivity(Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package", this.packageName, null),),)
                    }
                }
                return
            }
            Constantes.MY_BACKGROUND_LOCATION_REQUEST -> {

                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Background location Permission Granted", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Background location permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        setupSearchView(menu)
        return true
    }

    private fun setupSearchView(menu: Menu) = with(binding) {
        val item = menu.findItem(R.id.action_search)
        searchView.setMenuItem(item)
        searchView.setHint("Buscar...")
        binding.toolbar.title = "Nuevo Titulo"
//        searchView.setTabLayout(tabLayout)

        binding.toolbar.setOnMenuItemClickListener {it->
            when(it.itemId){

            }
            true
        }

        searchView.setOnQueryTextListener(object : SimpleSearchView.OnQueryTextListener{
            override fun onQueryTextChange(newText: String): Boolean {
                Log.w("SEARCHVIEW", "onQueryTextChange -> $newText")
                return false
            }

            override fun onQueryTextSubmit(query: String): Boolean {
                Log.w("SEARCHVIEW", "onQueryTextSubmit -> $query")
                return false
            }

            override fun onQueryTextCleared(): Boolean {
                Log.w("SEARCHVIEW", "onQueryTextCleared")
                return false
            }
        })

        // Adding padding to the animation because of the hidden menu item
        val revealCenter = searchView.revealAnimationCenter
        revealCenter!!.x -= convertDpToPx(EXTRA_REVEAL_CENTER_PADDING, context)
    }

    override fun onBackPressed() = with(binding) {
        if (searchView.onBackPressed()) {
            return
        }
        super.onBackPressed()
    }


    companion object {
        const val EXTRA_REVEAL_CENTER_PADDING = 0 // 0 Si es el 1º 40 si es el 2º
    }

}