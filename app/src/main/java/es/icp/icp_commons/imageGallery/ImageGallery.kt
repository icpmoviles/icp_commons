package es.icp.icp_commons.imageGallery

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.media.Image
import android.view.ActionMode
import android.view.Menu
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.icp.icp_commons.R

/*

  _____ __  __          _____ ______    _____          _      _      ______ _______     __
 |_   _|  \/  |   /\   / ____|  ____|  / ____|   /\   | |    | |    |  ____|  __ \ \   / /
   | | | \  / |  /  \ | |  __| |__    | |  __   /  \  | |    | |    | |__  | |__) \ \_/ /
   | | | |\/| | / /\ \| | |_ |  __|   | | |_ | / /\ \ | |    | |    |  __| |  _  / \   /
  _| |_| |  | |/ ____ \ |__| | |____  | |__| |/ ____ \| |____| |____| |____| | \ \  | |
 |_____|_|  |_/_/    \_\_____|______|  \_____/_/    \_\______|______|______|_|  \_\ |_|

 */

/**
 * ====
 * EJEMPLO DE IMPLEMENTACIÓN
 * ====
 *
 * ImageGallery(
 *      mActivity = this,
 *      mContext = this,
 *      recyclerView = binding.recycler,
 *      listaDeImagenes = elementosDePrueba
 * )
 * .setCustomSelectionTrackerFilledIcon(R.drawable.dx_default_icon)   // El icono en caso de que la imagen esté seleccionada
 * .setCustomSelectionTrackerUnfilledIcon(R.drawable.ic_camara_focus) // El icono en caso de que la imagen no esté seleccionada
 * .setDefaultImageDrawable(R.drawable.circled_orange_button)         // En caso de que no exista la imagen se pondrá este circulo naranja como alternativa
 * .setPrevisualizable(true)                                          // Permite previsualizar la imagen al presionar sobre ellas
 * .setColumns(4)
 * .setBackgroundColor(R.color.colorAccent)
 * .setTopbarColor(R.color.colorPrimary)
 * .drawGallery()
 *
 */

/**
 * @author Gonzalo Racero Galán
 * @version 1.0
 *
 * Esta clase dado un recyclerview y una lista de imágenes muestra realiza toodo lo necesario con
 * la visualización y manejo de las mismas. (Borrado, selección...)
 */
const val RUTA_IMAGEN_INTENT = "RUTA_DE_LA_IMAGEN"
const val RUTA_IMAGEN_ALTERNATIVA = "RUTA_DE_LA_IMAGEN_ALTERNATIVA"
const val SHARED_ELEMENT_NAME = "prev_imagegallery"
const val IMAGE_TITLE_INTENT = "IMAGE_TITLE"
const val CAMERA_BUTTON_POSITION_IN_RECYCLERVIEW = "CAMERA_BUTTON_POSITION_IN_RECYCLERVIEW"

class ImageGallery(

    // Contexto y actividad necesarios
    private val mActivity      : Activity,
    private val mContext       : Context,

    // La lista de imágenes en formato uri, url...
    private val recyclerView   : RecyclerView,
    private val listaDeImagenes: ArrayList<String>,

) {
    /**
     * Elementos opcionales
     */
    private var selectionTrackerFilled  : Int? = null
    private var selectionTrackerUnfilled: Int? = null
    private var alternativeImage        : Int? = null
    private var onDeleteAction          : (() -> Unit)? = null
    private var numberOfColumns         : Int = 3
    private var isPrevisualizable       : Boolean = false

    /**
     * Elementos generales que necesito a lo largo de la clase.
     */
    private lateinit var tracker        : SelectionTracker<Long>
    private lateinit var adapterImagenes: ImagenRecyclerViewAdapter
    private          var isInDeleteMode = false
    private          var liveDelete     = MutableLiveData(false)
    private          var isDrawed       = false

    // ############################################################################################
    //                         MÉTODOS QUE MODIFICAN LA CLASE
    // ############################################################################################

    /**
     * Añade icono de selección personalizado para el tracker
     */
    fun setCustomSelectionTrackerFilledIcon(icon: Int): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        selectionTrackerFilled = icon
        return this
    }

    /**
     * El icono anterior suele llevar asociado un borde, que si solo se ve este
     * indica que esta vacío
     */
    fun setCustomSelectionTrackerUnfilledIcon(icon: Int): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        selectionTrackerUnfilled = icon
        return this
    }

    /**
     * Añado la posibilidad de poder insertar una imagen por defecto en caso de que falle la carga
     */
    fun setDefaultImageDrawable(drawableImage: Int): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        alternativeImage = drawableImage
        return this
    }

    /**
     * Modificación del número de columnas
     */

    fun setColumns(columns: Int): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        numberOfColumns = columns
        return this
    }

    /**
     * Indicamos el valor de si se puede previsualizar o no
     */
    fun setPrevisualizable(previsualizable: Boolean): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        isPrevisualizable = previsualizable
        return this
    }

    /**
     * Añadimos el color de fondo
     */
    fun setBackgroundColor(color: Int): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        recyclerView.background = ContextCompat.getDrawable(mContext, color)
        return this
    }

    /**
     * Esta función deberá ejecutarse siempre al final, es la que pintará la galería de imágenes
     * una vez seteados los diferentes atributos.
     */
    fun drawGallery(): ImageGallery {
        if (isDrawed) throw ImageGalleryException("No se puede modificar este atributo una vez dibujada la galería")
        adapterImagenes = ImagenRecyclerViewAdapter(
            mActivity,
            listaDeImagenes,
            liveDelete,
            numberOfColumns,
            alternativeImage,
            isPrevisualizable,
            selectionTrackerFilled,
            selectionTrackerUnfilled
        )
        recyclerView.adapter = adapterImagenes
        recyclerView.layoutManager = GridLayoutManager(mContext, numberOfColumns)
        setUpTracker()

        isDrawed = true
        return this
    }

    // ############################################################################################
    //                              MÉTODOS INTERNOS DE LA CLASE
    // ############################################################################################

    /**
     * Esto es lo que ocurre cuando se le hace un long click a una de las imágenes
     */
    private fun getTrackerObserver(): SelectionTracker.SelectionObserver<Long> {

        return object : SelectionTracker.SelectionObserver<Long>() {

            override fun onSelectionChanged() {
                super.onSelectionChanged()

                if (tracker.hasSelection()) {

                    if (actionMode == null) {
                        actionMode = mActivity.startActionMode(actionModeCallback)
                    }

                    actionMode?.title = "${tracker.selection.size()}"

                    if (!isInDeleteMode) {
                        isInDeleteMode = true
                        enableDeleteMode()
                    }

                } else {

                    actionMode?.finish()

                    if (isInDeleteMode) {
                        isInDeleteMode = false
                        disableDeleteMode()
                    }

                }

            }

        }
    }

    /**
     * Asigna el observer anterior al sensor de longclick del recycler
     */
    private fun setUpTracker() {
        tracker = SelectionTracker.Builder(
            "imagen",
            recyclerView,
            ImageKeyProvider(recyclerView),
            ImageDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()
        tracker.addObserver(getTrackerObserver())
        adapterImagenes.selectionTracker = tracker
    }

    /**
     * Muestro la checkbox en todos los viewholder para dar feedback al usuario.
     */
    private fun enableDeleteMode() {
        liveDelete.postValue(true)
    }

    /**
     * Oculto la checkbox en todos los viewholder para dar feedback al usuario.
     */
    private fun disableDeleteMode() {
        liveDelete.postValue(false)
    }

    /**
     * Cuando presione al botón de borrar de la action bar me pedirá confirmación y realizará
     * la accion que establecí desde fuera de esta clase (opcional)
     */
    @SuppressLint("NotifyDataSetChanged")
    private fun borrarImagenesSeleccionadas() {

        DxImageGalleryImplementation.mostrarDxConfirmarBorrado(mContext, tracker.selection.size()) {

            // En caso de confirmar el borrado en el diálogo...
            val listaDeImagenesSeleccionadas = tracker.selection.map {
                listaDeImagenes[it.toInt()]
            }
            listaDeImagenes.removeAll(listaDeImagenesSeleccionadas.toSet())
            adapterImagenes.notifyDataSetChanged()
            tracker.clearSelection()

            if (listaDeImagenes.isEmpty()) {
                onDeleteAction?.invoke()
            }

        }

    }

    /**
     * El actionMode de cuando activo la selección múltiple
     */
    private var actionMode: ActionMode? = null

    /**
     * Las acciones del ciclo de vida del action mode o cuando interactuo con el mismo.
     */
    private val actionModeCallback = object : ActionMode.Callback {

        override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
            val inflater = mActivity.menuInflater
            inflater.inflate(R.menu.menu_appbar_borrar_imagenes, menu)
            return true
        }

        override fun onPrepareActionMode(mode: ActionMode?, menu: Menu): Boolean {
            return false
        }

        override fun onActionItemClicked(mode: ActionMode?, item: MenuItem): Boolean {
            return when (item.itemId) {
                R.id.borrar_imagen_boton -> {
                    borrarImagenesSeleccionadas()
                    true
                }
                else -> false
            }
        }

        override fun onDestroyActionMode(mode: ActionMode?) {
            tracker.clearSelection()
            actionMode = null
        }

    }

    /**
     * Añadir una imagen a la galería
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addImage(image: String) {
        listaDeImagenes.add(image)
        adapterImagenes.notifyDataSetChanged()
    }

    /**
     * Añadir una lista de imágenes a la galería
     */
    @SuppressLint("NotifyDataSetChanged")
    fun addImages(images: List<String>) {
        listaDeImagenes.addAll(images)
        adapterImagenes.notifyDataSetChanged()
    }

    /**
     * Eliminar una imagen de la galería
     */
    @SuppressLint("NotifyDataSetChanged")
    fun removeImage(image: String) {
        listaDeImagenes.remove(image)
        adapterImagenes.notifyDataSetChanged()
    }

    /**
     * Eliminar una lista de imágenes de la galería
     */
    @SuppressLint("NotifyDataSetChanged")
    fun removeImages(images: List<String>) {
        listaDeImagenes.removeAll(images.toSet())
        adapterImagenes.notifyDataSetChanged()
    }

    /**
     * Vacía la galería de imágenes
     */
    @SuppressLint("NotifyDataSetChanged")
    fun clearGallery() {
        listaDeImagenes.clear()
        adapterImagenes.notifyDataSetChanged()
    }

}