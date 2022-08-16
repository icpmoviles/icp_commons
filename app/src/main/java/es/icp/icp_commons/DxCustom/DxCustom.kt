package es.icp.icp_commons.DxCustom

import android.app.Dialog
import android.content.Context
import android.content.res.XmlResourceParser
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.text.Html
import android.view.*
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import es.icp.icp_commons.Extensions.visible
import es.icp.icp_commons.R

/*

            ██████╗░██╗░░██╗░█████╗░██╗░░░██╗░██████╗████████╗░█████╗░███╗░░░███╗
            ██╔══██╗╚██╗██╔╝██╔══██╗██║░░░██║██╔════╝╚══██╔══╝██╔══██╗████╗░████║
            ██║░░██║░╚███╔╝░██║░░╚═╝██║░░░██║╚█████╗░░░░██║░░░██║░░██║██╔████╔██║
            ██║░░██║░██╔██╗░██║░░██╗██║░░░██║░╚═══██╗░░░██║░░░██║░░██║██║╚██╔╝██║
            ██████╔╝██╔╝╚██╗╚█████╔╝╚██████╔╝██████╔╝░░░██║░░░╚█████╔╝██║░╚═╝░██║
            ╚═════╝░╚═╝░░╚═╝░╚════╝░░╚═════╝░╚═════╝░░░░╚═╝░░░░╚════╝░╚═╝░░░░░╚═╝

 */
/**
 * @author Julio Landazuri Diaz
 * @version 1.0
 *
 * Esta calse permite tener dialogos personalizados a partir de un layout respetando las IDs de los elementos.
 */
/* ------- Ejemplo de uso -------

        DxCustom(this@LoginActivity)
                .createDialog()
                .setTitulo("Titulo")
                .setMensaje("Mensaje")
                .noPermitirSalirSinBotones()
                .showAceptarButton("aceptar personalizado") {
                    Log.d(":::", "Aceptar pulsado.")
                }
                .showCancelarButton ("cancelar personalizado") {
                    Log.d(":::", "Cancelar pulsado.")
                }
                .addCustomView(bindingCustomView.root)
                .showDialog()


 */
const val DELAY_TIME = 500L
class DxCustom(
    private val context: Context
) {
    /* ------- Ojo con las IDs!! -------

        Para que DxCustom funcione correctamente, se deben respetar las IDs de los elementos del XML.

        Para ver las IDs que se usan ver la funcion ->       loadLayoutComponentes()

    */

    private var parentDxCustomLayout:   LinearLayout?   = null
    private var acceptButtonLayout:     Button?         = null
    private var cancelButtonLayout:     Button?         = null
    private var tituloDxCustomLayout:   TextView?       = null
    private var mensajeDxCustomLayout:  TextView?       = null
    private var iconoDxCustomLayout:    ImageView?      = null
    private var customViewUpLayout:     LinearLayout?   = null
    private var customViewDownLayout:   LinearLayout?   = null

    private var tituloDxCustom:                 String              = "Sin titulo."
    private var mensajeDxCustom:                String              = "Sin mensaje."
    private var iconoDxCustom:                  Drawable?           = null
    private var layoutDxCustomPersonalizado:    XmlResourceParser?  = null
    private lateinit var dialog:                Dialog

    /**
     * Crea y asigna las configuraciones del dialogo.
     *
     * @return DxCustom
     */
    fun createDialog(fullScreen: Boolean = false): DxCustom {

        verificarExistenciaDeRecursos()

        //Este estilo puede cambiar dependiendo del proyecto
        dialog = Dialog(context, R.style.DxCustom)

        val inflater = LayoutInflater.from(context)

        try{

            val dialogView = inflater.inflate(layoutDxCustomPersonalizado, null)

            dialog.setContentView(dialogView)

        }catch (e: Exception){
            e.printStackTrace()
        }

        //Importante, indicar un drawable como fondo, en el se podran cambiar los margenes de la ventana del dialogo.
        dialog.window?.setBackgroundDrawableResource(R.drawable.dx_transparent_default_background)

        //Para que el layout que se asigna al DxCustom, este en pantalla completa.
        if(fullScreen)
            dialog.window?.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);

        val window = dialog.window

        window?.let {

            val wlp = window.attributes

            wlp.gravity = Gravity.BOTTOM
        }?: run {

            throwIllegalStateException("Error al crear la ventana del dialogo.")

        }

        loadLayoutComponentes()

        return this
    }

    /**
     * Permite asignar el titulo del dialogo.
     *
     * @throws IllegalArgumentException
     *
     * @param titulo Texto que se asigna al titulo.
     *
     * @return DxCustom
     */
    fun setTitulo(titulo: String = tituloDxCustom): DxCustom {

        if(titulo.isEmpty())
            throwIllegalArgumentException("El titulo proporcionado no es valido.")
        else{
            tituloDxCustom = titulo
        }

        return this
    }

    /**
     * Permite asignar un mensaje al dialogo.
     *
     * @param mensaje Texto que se asigna al mensaje.
     *
     * @return DxCustom
     */
    fun setMensaje(mensaje: String? = mensajeDxCustom): DxCustom {

        mensaje?.let { mensaje ->
            mensajeDxCustom = mensaje
        } ?: run {
            mensajeDxCustomLayout?.visibility = View.GONE
        }

        return this
    }

    /**
     * Permite asginar un icono al dialogo.
     *
     * @throws IllegalArgumentException
     *
     * @param icono Drawable a mostrar en el icono del dialogo.
     *
     * @return DxCustom
     */
    fun setIcono(icono: Drawable? = iconoDxCustom): DxCustom {

        icono?.let {
            iconoDxCustom = icono
        }?:run{
            throwIllegalArgumentException("El icono proporcionado no es valido.")
        }

        return this
    }

    /**
     * Configura el dialogo para que solo se pueda cerrar mediante los botones, recuerda, tienes que mostrarlos.
     *
     * @return DxCustom
     */
    fun noPermitirSalirSinBotones(): DxCustom {

        dialog.setCancelable(false)

        return this
    }

    /**
     * Configura el dialogo para que se pueda cerrar mediante el boton de 'volver atras' o pulsando sobre la zona oscurecida.
     *
     * @return DxCustom
     */
    fun permitirSalirSinBotones(): DxCustom {

        dialog.setCancelable(true)

        return this
    }

    /**
     * Muestra el boton de aceptar y luego ejecuta onAccept.
     *
     * @param texto Permite sustituir el texto mostrado en el boton aceptar.
     * @param onAccept Codigo proporcionado para que se ejecuta cuando el boton aceptar sea pulsado.
     *
     * @return DxCustom
     */
    fun showAceptarButton(
        texto: String? = "Aceptar",
        onAccept: () -> Unit): DxCustom {

        acceptButtonLayout?.let { acceptBtn ->

            with(acceptBtn) {

                visibility = VISIBLE
                text = texto

                setOnClickListener {
                    animateDialogOnHide()
                    onAccept.invoke()
                    isEnabled = false
                    postDelayed({ isEnabled = true }, DELAY_TIME)
                }

            }

        }?:run{

            throwNullPointerException("No se encontro: acceptButtonLayout.")
        }

        return this
    }

    /**
     * Muestra el boton de cancelar y luego ejecuta onCancel.
     * Ademas, el boton de cancelar tiene un pequeño retraso para que no pueda ser accionado
     * y desencadenar 2 veces la acción.
     *
     * @param texto Permite sustituir el texto mostrado en el boton cancelar.
     * @param onCancel Codigo proporcionado para que se ejecuta cuando el boton cancelar sea pulsado.
     *
     * @return DxCustom
     */
    fun showCancelarButton(
        texto: String? = "Cancelar",
        onCancel: () -> Unit
    ): DxCustom {

        cancelButtonLayout?.let { cancelBtn ->

            with (cancelBtn) {

                visibility = VISIBLE
                text = texto

                setOnClickListener {
                    animateDialogOnHide()
                    onCancel.invoke()
                    isEnabled = false
                    postDelayed({ isEnabled = true }, DELAY_TIME)
                }

            }

        }?:run{
            throwNullPointerException("No se encontro: cancelButtonLayout.")
        }

        return this
    }

    /**
     * Permite añadir una vista personalizada al dialogo.
     *
     * @throws NullPointerException
     *
     * @param customView Layout personalizado que se añade al layout del dialogo, recuerda,
     * para que puedas seguir jugando con los elementos, pasa 'binding.root' como customView.
     *
     * @param position Permite decidir si quieres que el customView este encima o debajo de los botones de cancelar y aceptar.
     *
     * @return DxCustom
     */
    fun addCustomView(customView: View, position: Boolean = true): DxCustom {

        var parentCustomView: LinearLayout? = null

        if (position){

            customViewUpLayout?.let {
                parentCustomView = it
            }?:run {

                throwNullPointerException("No se encontro customViewUpLayout.")
            }

        }
        else{
            customViewDownLayout?.let {
                parentCustomView = it
            }?:run {

                throwNullPointerException("No se encontro customViewDownLayout.")
            }
        }

        parentCustomView?.addView(customView) ?:run{
            throwNullPointerException("Error al crear customView.")
        }

        return this
    }

    /**
     * Ejecuta el dialogo.
     *
     * @return Dialog
     */
    fun showDialogReturnDialog(): Dialog{

        try{

            SetDataOnDialog()
            animateDialogOnShow()

            dialog.show()


        }catch (e: Exception){

            e.printStackTrace()

        }

        return dialog
    }

    /**
     * Ejecuta el dialogo.
     *
     * @return DxCustom
     */
    fun showDialogReturnDxCustom(): DxCustom{

        try{

            SetDataOnDialog()
            animateDialogOnShow()
            dialog.show()


        }catch (e: Exception){

            e.printStackTrace()

        }

        return this
    }

    /**
     * Oculta el dialogo con la animacion de cierre.
     */
    fun hideDialog(){

        animateDialogOnHide()

    }

    private fun loadLayoutComponentes(){

        parentDxCustomLayout   =  dialog.findViewById<LinearLayout>(R.id.parentDxCustomLayout)
        acceptButtonLayout     =  dialog.findViewById<Button>(R.id.acceptButton)
        cancelButtonLayout     =  dialog.findViewById<Button>(R.id.cancelButton)
        tituloDxCustomLayout   =  dialog.findViewById<TextView>(R.id.tituloDxCustom)
        mensajeDxCustomLayout  =  dialog.findViewById<TextView>(R.id.mensajeDxCustom)
        iconoDxCustomLayout    =  dialog.findViewById<ImageView>(R.id.iconDxCustom)
        customViewUpLayout     =  dialog.findViewById<LinearLayout>(R.id.customViewLinearLayoutDxCustomUp)
        customViewDownLayout   =  dialog.findViewById<LinearLayout>(R.id.customViewLinearLayoutDxCustomDown)

    }

    /**
     * Pinta los datos proporcionados en el layout proporcionado.
     */
    private fun SetDataOnDialog(){

        tituloDxCustomLayout?.let {
            it.text = tituloDxCustom
        }?:run{
            throwNullPointerException("No se encontro: tituloDxCustomLayout.")
        }

        mensajeDxCustomLayout?.let {
//            it.text = mensajeDxCustom
            it.text = Html.fromHtml(mensajeDxCustom, Html.FROM_HTML_MODE_COMPACT)
        }?:run{
            throwNullPointerException("No se encontro: mensajeDxCustomLayout.")
        }

        iconoDxCustomLayout?.setImageDrawable(iconoDxCustom) ?:run{
            throwNullPointerException("No se encontro: iconoDxCustomLayout.")
        }

    }

    /**
     * Anima el dialogo cuando se muestra
     */
    private fun animateDialogOnShow(){


        parentDxCustomLayout?.let {

            it.measure(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

            it.translationY = +1000f + it.measuredHeight

        }?:run {
            throwNullPointerException("No se encontro: parentDxCustomLayout.")
        }

        parentDxCustomLayout?.visible() ?:run {
            throwNullPointerException("No se encontro: parentDxCustomLayout.")
        }

        Handler(Looper.getMainLooper()).postDelayed({

            parentDxCustomLayout?.animate()?.translationY(0f)?.setDuration(DELAY_TIME)
                ?.start()
                ?:run {
                    throwNullPointerException("No se encontro: parentDxCustomLayout.")
                }

        }, 100)

    }

    /**
     * Anima el dialogo cuando se esconde
     */
    private fun animateDialogOnHide() {

        parentDxCustomLayout?.animate()?.translationY(2000f)?.setDuration(DELAY_TIME)
            ?.start()
            ?:run{
            throwNullPointerException("No se encontro: parentDxCustomLayout.")
        }


        Handler(Looper.getMainLooper()).postDelayed({

            dialog.dismiss()

        }, DELAY_TIME)

    }

    /**
     * Verficia los datos.
     */
    private fun verificarExistenciaDeRecursos(){

        try{

            iconoDxCustom = context.getDrawable(R.drawable.dx_default_icon)

        }catch (e: Exception){
            throw e
        }

        try {

            layoutDxCustomPersonalizado = context.applicationContext.resources.getLayout(R.layout.dx_custom_default_layout)

        }catch (e: Exception){
            throw e
        }

    }

    /**
     * Función que me devuelve la vista del boton aceptar.
     */
    fun getAcceptButton(): Button? {
        return acceptButtonLayout
    }

    /**
     * Función que me devuelve la vista del boton cancelar.
     */
    fun getCancelButton(): Button? {
        return cancelButtonLayout
    }

    /**
     * Función que lanza IllegalArgumentException con mensaje personalizado.
     */
    @Throws(IllegalArgumentException::class)
    fun throwIllegalArgumentException(mensaje: String = "Error no definido") {
        throw IllegalArgumentException(mensaje)
    }

    /**
     * Función que lanza IllegalStateException con mensaje personalizado.
     */
    @Throws(IllegalStateException::class)
    fun throwIllegalStateException(mensaje: String = "Error no definido") {
        throw IllegalStateException(mensaje)
    }

    /**
     * Función que lanza IllegalStateException con mensaje personalizado.
     */
    @Throws(NullPointerException::class)
    fun throwNullPointerException(mensaje: String = "Error no definido") {
        throw NullPointerException(mensaje)
    }

}