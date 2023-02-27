package es.icp.icp_commons.camara.imageGallery

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import es.icp.icp_commons.camara.databinding.ImagenViewholderBinding

class ImagenRecyclerViewAdapter(

    private val activity: Activity,

    private var lista: List<String> = listOf(),
    private var isInDeleteMode: LiveData<Boolean>,


    private val numberOfCols: Int,
    private val alternativeImage: Int?,
    private val isPrevisualizable: Boolean,

    // Posibilidad de personalizar el icono de borrado
    private val selectionTrackerFilled: Int?,
    private val selectionTrackerUnfilled: Int?

) : RecyclerView.Adapter<ImagenViewHolder>() {

    var selectionTracker: SelectionTracker<Long>? = null

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagenViewHolder {
        val binding = ImagenViewholderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImagenViewHolder(
            activity,
            binding.root,
            binding,
            numberOfCols,
            alternativeImage,
            isPrevisualizable,
            selectionTrackerFilled,
            selectionTrackerUnfilled
        )
    }

    override fun onBindViewHolder(holder: ImagenViewHolder, position: Int) {
        val valor: String = lista[position]
        selectionTracker?.let {
            holder.bind(
                valor,
                position + 1,
                lista.size,
                it.isSelected(position.toLong()),
                isInDeleteMode
            )
        }
    }

    override fun getItemCount(): Int = lista.size

    // Serán identificados a la hora de seleccionarse por su posición
    override fun getItemId(position: Int): Long = position.toLong()

    fun setNewDataSet(nuevaLista: List<String>) {
        lista = nuevaLista
        notifyDataSetChanged()
    }

}