package es.icp.icp_commons;

import android.content.Context;
import android.media.MediaPlayer;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.GlobalVariables;
import es.icp.icp_commons.Interfaces.OnCompletionListener;

public class Sonido {

    public static void reproducirSonidoLib(Context context, int audio) {
        reproducirSonidoLib(context, audio, null);
    }

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

    public static void reproducirSonido(Context context, int sonido) {
        reproducirSonido(context, sonido, null);
    }

    public static  void reproducirSonido(Context context, int sonido, final OnCompletionListener onCompletionListener) {
        if (!GlobalVariables.Sonido){
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
}
