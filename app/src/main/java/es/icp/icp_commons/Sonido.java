package es.icp.icp_commons;

import android.content.Context;
import android.media.MediaPlayer;
import android.provider.MediaStore;

import androidx.annotation.RawRes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.GlobalVariables;

public class Sonido {

    public static void reproducirSonidoApp(Context context, int resource) throws IOException{
        InputStream raw = context.getResources().openRawResource(resource);
        File file = new File(context.getFilesDir().getAbsolutePath() + "/temp.txt");
        copyInputStreamToFile(raw, file);
        FileInputStream fis = new FileInputStream(file);

        if (!GlobalVariables.Sonido){
            final MediaPlayer mp = new MediaPlayer();
            mp.setDataSource(fis.getFD());
            mp.prepare();
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    GlobalVariables.Sonido = false;
                }
            });
            mp.start();
            GlobalVariables.Sonido = true;
        }
    }

    public static void reproducirSonidoLib(Context context, int audio) {
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
        reproducir(context, sonido);
    }

    private static void reproducir(Context context, int sonido) {
        if (!GlobalVariables.Sonido){
            final MediaPlayer mp = MediaPlayer.create(context, sonido);
            mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    GlobalVariables.Sonido = false;
                }
            });
            mp.start();
            GlobalVariables.Sonido = true;
        }
    }

    private static void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            int read;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, read);
            }
        }
    }
}
