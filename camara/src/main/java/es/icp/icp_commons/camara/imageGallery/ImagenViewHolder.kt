package es.icp.icp_commons.camara.imageGallery

import android.app.Activity
import android.content.Intent
import android.util.DisplayMetrics
import android.view.View
import android.widget.RelativeLayout
import androidx.core.app.ActivityOptionsCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import es.icp.icp_commons.camara.R
import es.icp.icp_commons.camara.databinding.ImagenViewholderBinding

class ImagenViewHolder(

    private val activity                : Activity,

    private val view                    : View,
    private val binding                 : ImagenViewholderBinding,
    private val numberOfCols            : Int,
    private val alternativeImage        : Int?,
    private val isPrevisualizable       : Boolean,
    private val selectionTrackerFilled  : Int?,
    private val selectionTrackerUnfilled: Int?

) : RecyclerView.ViewHolder(view) {

    fun bind(
        imagen: String,
        posicionImagen: Int,
        longitudTotal: Int,
        isSelected: Boolean,
        isInDeleteMode: LiveData<Boolean>
    ) = with(binding) {

        // Ajusto el tamaño del viewholder de manera proporcial al numero de vistas del gridrecycler
        val displayMetrics: DisplayMetrics = view.context.resources.displayMetrics
        layoutImageContainer.layoutParams = RelativeLayout.LayoutParams(
            displayMetrics.widthPixels / numberOfCols,
            displayMetrics.widthPixels / numberOfCols
        )

        // En caso de que se haya seteado una imagen alternativa...
        alternativeImage?.let {
            Glide.with(view.context)
                .load(imagen)
                .placeholder(it)
                .into(binding.imagen)
        } ?: run {
            Glide.with(view.context)
                .load(imagen)
                .into(binding.imagen)
        }

        isInDeleteMode.observeForever {
            if (it) {
                marcarParaBorrar.visibility = View.VISIBLE
            } else {
                marcarParaBorrar.visibility = View.GONE
            }
        }

        // Reemplazo los iconos de borrado por defecto en caso de haber metido iconos personalizados
        val trackerFilledIcon = selectionTrackerFilled ?: R.drawable.ic_check_filled
        val trackerUnfilledIcon = selectionTrackerUnfilled ?: R.drawable.ic_check_unfilled

        if (isSelected) {
            marcarParaBorrar.background = ContextCompat.getDrawable(view.context, trackerFilledIcon)
        } else {
            marcarParaBorrar.background = ContextCompat.getDrawable(view.context, trackerUnfilledIcon)
        }

        // En caso de que sea previsualizable
        if (isPrevisualizable) {

            layoutImageContainer.setOnClickListener {

                val intent = Intent(view.context, ImageGalleryPrevisualizationActivity::class.java)
                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    activity,
                    binding.imagen,
                    SHARED_ELEMENT_NAME
                )

                intent.putExtra(RUTA_IMAGEN_INTENT, imagen)
                intent.putExtra(IMAGE_TITLE_INTENT, "Imagen $posicionImagen de $longitudTotal")

                if (alternativeImage != null) {
                    intent.putExtra(RUTA_IMAGEN_ALTERNATIVA, alternativeImage)
                }

                startActivity(view.context, intent, options.toBundle())
            }

        }

    }

    fun getItem(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = absoluteAdapterPosition
            override fun getSelectionKey(): Long = itemId
        }

}