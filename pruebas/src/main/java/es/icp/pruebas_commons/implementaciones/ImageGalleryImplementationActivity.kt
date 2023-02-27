package es.icp.pruebas_commons.implementaciones

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import es.icp.icp_commons.camara.imageGallery.ImageGallery
import es.icp.pruebas_commons.KotlinActivity
import es.icp.pruebas_commons.R
import es.icp.pruebas_commons.databinding.ActivityRecyclerSelectorExampleBinding

class ImageGalleryImplementationActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecyclerSelectorExampleBinding
    private lateinit var imageGallery : ImageGallery

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecyclerSelectorExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.sopaMaterialToolbar)
        setupListeners()
        setUpGaleriaDeImagenes()
    }

    private fun setUpGaleriaDeImagenes() {

        // Esta string se utiliza como identificador en el tracker
        // Pueden usuarse imágenes o uris correspondientes a rutas del teléfono
        val elementosDePrueba = arrayListOf(
            "https://picsum.photos/200",
            "https://picsum.photos/300",
            "https://picsum.photos/400",
            "https://picsum.photos/500",
            "https://picsum.photos/600",
            "https://picsum.photos/700",
            "https://picsum.photos/800",
            "https://picsum.photos/900",
            "IMAGEN ERRONEA A PROPÓSITO"
        )

        imageGallery = ImageGallery(
            mActivity = this,
            mContext = this,
            recyclerView = binding.recycler,
            listaDeImagenes = elementosDePrueba,
            //appBarLayout = binding.sopaAppBarLayout
        )
            .setCustomSelectionTrackerFilledIcon(R.drawable.dx_default_icon)   // El icono en caso de que la imagen esté seleccionada
            .setCustomSelectionTrackerUnfilledIcon(R.drawable.ic_camara_focus) // El icono en caso de que la imagen no esté seleccionada
            .setDefaultImageDrawable(R.drawable.circled_orange_button)         // En caso de que no exista la imagen se pondrá este circulo naranja como alternativa
            .setPrevisualizable(true)                                          // Permite previsualizar la imagen al presionar sobre ellas
            .setColumns(4)                                                     // Número de columnas
            .setBackgroundColor(R.color.colorAccent)                           // Color de fondo del recycler
            .drawGallery()                                                     // Dibuja la galería

        imageGallery.addImage("https://picsum.photos/1000")          // Añade una imagen a la galería una vez ya creada de forma dinámica

        // Esto debe tirar excepcion
        // imageGallery.setColumns(12)

    }

    private fun setupListeners() = with(binding) {
        btnVolverAKtAtivityDesdeRecyclerSelector.setOnClickListener {
            val intent = Intent(
                this@ImageGalleryImplementationActivity,
                KotlinActivity::class.java
            )
            startActivity(intent)
            finish()
        }
    }

}