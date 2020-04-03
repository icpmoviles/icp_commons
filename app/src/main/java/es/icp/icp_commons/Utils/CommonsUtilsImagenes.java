package es.icp.icp_commons.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;

import es.icp.icp_commons.Objects.ImagenCommons;

public class CommonsUtilsImagenes {

    public static String fromFileToBase64Image(File file) {
        Bitmap bm = BitmapFactory.decodeFile(file.getAbsolutePath());
        return fromBitmaptoBase64Image(bm);
    }

    public static String fromDrawableToBase64Image(Drawable drawable) {
        Bitmap bm = ((BitmapDrawable) drawable).getBitmap();
        return fromBitmaptoBase64Image(bm);
    }

    public static String fromBitmaptoBase64Image(Bitmap bm) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT)/* + "|" + degrees*/;
    }

    public static byte[] convertBase64ToByteArray(ImagenCommons imagenCommons) {
        int    degrees = imagenCommons.getOrientacion();
        byte[] bytes   = Base64.decode(imagenCommons.getContenido(), Base64.DEFAULT);

        Bitmap storedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);

        Matrix mat = new Matrix();
        if (degrees == 90 || degrees == 270) mat.postRotate(degrees);
        storedBitmap = Bitmap.createBitmap(storedBitmap, 0, 0, storedBitmap.getWidth(), storedBitmap.getHeight(), mat, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        storedBitmap.compress(imagenCommons.getFormato(), 100, stream);

        return stream.toByteArray();
    }
}
