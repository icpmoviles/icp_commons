package es.icp.icp_commons.imageGallery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import com.bumptech.glide.Glide
import es.icp.icp_commons.R
import es.icp.icp_commons.databinding.ActivityImageGalleryPrevisualizationBinding

class ImageGalleryPrevisualizationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityImageGalleryPrevisualizationBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityImageGalleryPrevisualizationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.title = intent.getStringExtra(IMAGE_TITLE_INTENT)

        val imagen = intent.getStringExtra(RUTA_IMAGEN_INTENT)
        val imagenAlternativa = intent.getIntExtra(RUTA_IMAGEN_ALTERNATIVA, -1)

        setupImagen(imagenAlternativa, imagen)
        setupAppbar()
    }

    private fun setupImagen(imagenAlternativa: Int, imagen: String?) {

        if (imagenAlternativa != -1) {
            Glide.with(this)
                .load(imagen)
                .placeholder(imagenAlternativa)
                .into(binding.imagenDePrevisualizacionImagegallery)
        } else {
            Glide.with(this)
                .load(imagen)
                .into(binding.imagenDePrevisualizacionImagegallery)
        }

    }

    private fun setupAppbar() = with(binding) {

        toolbarImageGalleryPrevisualization.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)
        toolbarImageGalleryPrevisualization.setNavigationOnClickListener {

            this@ImageGalleryPrevisualizationActivity.onBackPressed()
        }

        // Le añado el elevation que se ve en el diseño
        toolbarImageGalleryPrevisualization.elevation = 20f

    }

}