package es.icp.icp_commons;

import android.content.Context;
import android.media.MediaPlayer;

import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.GlobalVariables;
import es.icp.icp_commons.Interfaces.OnCompletionListener;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Sonido {

    /**
     * Reproduce un audio ya existente en la librería.
     *
     * @param context Context. Contexto de la aplicación.
     * @param audio   Constante para indicar el audio a reproducir:
     *                AUDIO_ERROR: error.wav
     *                AUDIO_EXITO: exito.wav
     *                AUDIO_OHOH: ohoh.wav
     * @author Ventura de Lucas
     */
    public static void reproducirSonidoLib(Context context, int audio) {
        reproducirSonidoLib(context, audio, null);
    }

    /**
     * Reproduce un audio ya existente en la librería.
     *
     * @param context              Context. Contexto de la aplicación.
     * @param audio                Constante para indicar el audio a reproducir:
     *                             AUDIO_ERROR: error.wav
     *                             AUDIO_EXITO: exito.wav
     *                             AUDIO_OHOH: ohoh.wav
     * @param onCompletionListener OnCompletionListener. Listener para conocer el momento en el que finaliza el audio.
     * @author Ventura de Lucas
     */
    public static void reproducirSonidoLib(Context context, int audio, OnCompletionListener onCompletionListener) {
        int sonido;
        switch (audio) {
            case Constantes.AUDIO_ERROR:
                sonido = R.raw.error;
                break;
            case Constantes.AUDIO_EXITO:
                sonido = R.raw.exito;
                break;
            case Constantes.AUDIO_OHOH:
                sonido = R.raw.ohoh;
                break;
            default:
                sonido = R.raw.ohoh;
                break;
        }
        reproducirSonido(context, sonido, onCompletionListener);
    }

    public static void reproducirSonidoLib(Context context, Pista pista, OnCompletionListener onCompletionListener) {
        int sonido;
        switch (pista) {
            case EXITO:
                sonido = R.raw.exito;
                break;
            case ERROR:
                sonido = R.raw.error;
                break;
            case OHOH:
                sonido = R.raw.ohoh;
                break;
            default:
                sonido = R.raw.ohoh;
                break;
        }
        reproducirSonido(context, sonido, onCompletionListener);
    }

    public static void reproducirSonidoLib(Context context, Pista pista) {
        reproducirSonidoLib(context, pista, null);
    }

    /**
     * Reproduce un audio propio (no existente en la librería.
     *
     * @param context Context. Contexto de la aplicación.
     * @param sonido  int. Valor id del audio a reproducir.
     * @author Ventura de Lucas
     */
    private static void reproducirSonido(Context context, int sonido) {
        reproducirSonido(context, sonido, null);
    }

    /**
     * Reproduce un audio propio (no existente en la librería.
     *
     * @param context              Context. Contexto de la aplicación.
     * @param sonido               int. Valor id del audio a reproducir.
     * @param onCompletionListener OnCompletionListener. Listener para conocer el momento en el que finaliza el audio.
     * @author Ventura de Lucas
     */
    private static void reproducirSonido(Context context, int sonido, final OnCompletionListener onCompletionListener) {
        if (!GlobalVariables.Sonido) {
            final MediaPlayer mp = MediaPlayer.create(context, sonido);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    GlobalVariables.Sonido = false;
                    if (onCompletionListener != null) {
                        onCompletionListener.onCompletion(mediaPlayer);
                    }
                }
            });
            mp.start();
            GlobalVariables.Sonido = true;
        }
    }

    public enum Pista {
        ERROR, EXITO, OHOH
    }
}
