package es.icp_commons.authentication

import android.content.Context
import android.content.Intent
import android.provider.Settings
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import java.util.concurrent.Executor

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 * NECESITAREMOS INSTANCIAR UN LAUNCHER EN EL FRAGMENTO EN EL CASO DE QUE EL RECONOCIMIENTO NO ESTE CONFIGURADO
 * OJO ESTE LAUNCHER SOLO ACTUA EN EL CASO DE QUE SE PUEDA USAR LA HUELLA Y ESTA NO ESTE CONFIGURADA
 * NO SE DA ACCESO SI NO INICIO AL MENOS UNA VEZ CON CONTRASEÑA DE APP Y TAMPOCO SE DA ACESSO AL EJECUTAR ESTE LAUNCHER
 * EJ ->
 *   val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
 *      if (it.resultCode == RESULT_CANCELED) USUARIO NO QUIERE REGISTRAR HUELLA
 *      else if (it.resultCode == RESULT_OK)  USUARIO REGISTRO LA HUELLA CORRECTAMENTE
 * }
 *
 * Biometrics.init(requireContext(), requireActivity()){ success ->
 *      if (success) ACCESO CORRECTO iniciamos navegacion hacia home
 * }
 * binding.miBotonBiometrico = Biometrics.checkDeviceHasBiometric(requireContext(), launcher)
 *
 * PARA MOSTRAR DIALOGO DE RECONOCIMIENTO BIOMETRICO
 * Biometrics.promptInfo?.let { it -> Biometrics.biometricPrompt?.authenticate(it) }
 */
object Biometrics  {

    private lateinit var executor: Executor
    var biometricPrompt: BiometricPrompt? = null
    var promptInfo: BiometricPrompt.PromptInfo? = null


    /**
     * RESPONSABLE DE INICIALIZAR LAS VARIABLES DE CLASE INVOLUCRADAS EN LA AUTENTIFICACION BIOMETRICA
     * DEVUELVE TRUE O FALSE DEPENDIENDO DE SI LA AUTENTICACION BIOMETRICA FUE SATISFACTORIA O NO
     *
     * @param context Contexto de la aplicacion (requireContext())
     * @param activityFragment requireActivity()
     * @param onResult Tratamiento del resultado
     */
    fun init(context: Context, activityFragment: FragmentActivity, onResult: (Boolean) -> Unit){
        executor = ContextCompat.getMainExecutor(context)

        biometricPrompt = BiometricPrompt(activityFragment, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationError(
                    errorCode: Int,
                    errString: CharSequence,
                ) {
                    super.onAuthenticationError(errorCode, errString)
//                    Toast.makeText(context,
//                        errString, Toast.LENGTH_SHORT)
//                        .show()
                    onResult.invoke(false)
                }

                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult,
                ) {
                    super.onAuthenticationSucceeded(result)
                    onResult.invoke(true)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    onResult.invoke(false)
                }
            })

        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Acceso biométrico")
            .setSubtitle("Inicie sesión con sus credenciales biométricas")
            .setNegativeButtonText("Cancelar")
            .build()

    }

    /**
     * CHECKEA SI EL DISPOSITIVO SOPORTA EL ACCESO BIOMETRICO Y SI ESTE SE ENCUENTRA ACTIVADO.
     * SI EL USO BIOMETRICO ES POSIBLE Y NO ESTA ACTIVADO REDIRIGE AL USUARIO A AJUSTES PARA ACTIVAR EL RECONOCIMIENTO BIOMETRICO
     * EN CASO CONTRARIO DEVUELVE FALSE.
     * CON ESTE RESULTADO PODEMOS ACTIVAR O DESACTIVAR EL BOTON DE ACCESO BIOMETRICO
     *
     */
    fun checkDeviceHasBiometric(context: Context, launcher: ActivityResultLauncher<Intent>) : Boolean {
        val biometricManager = BiometricManager.from(context)
        return when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                promptInfo?.let { biometricPrompt?.authenticate(it) }
                true
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE,
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE->  false

            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                // Prompts the user to create credentials that your app accepts.
                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                    putExtra(
                        Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                        BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                    )
                }

                launcher.launch(enrollIntent)
                true
            }
            else -> false
        }
    }
}