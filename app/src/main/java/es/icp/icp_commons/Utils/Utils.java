package es.icp.icp_commons.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.util.Base64;
import android.util.DisplayMetrics;

import androidx.core.app.ActivityCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;

import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Objects.ImagenCommons;

public class Utils {

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static boolean isDebuggable(Context context) {
        String packageName = context.getPackageName();
        PackageInfo packageInfo = context.getPackageManager().getPackageArchiveInfo(packageName, PackageManager.GET_SIGNATURES);
        if (packageInfo != null) {
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            return (0 != (applicationInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        }
        return false;
    }

    public static int getCameraPhotoOrientation(String imagePath) {
        int rotate = 0;
        try {
            File imageFile = new File(imagePath);

            ExifInterface exif        = new ExifInterface(imageFile.getAbsolutePath());
            int           orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotate = 270;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotate = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotate = 90;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rotate;
    }

    public static Bitmap.CompressFormat getFormatFromFile(String path) {
        String[] pathSplit = path.split("\\.");
        String extension = pathSplit[pathSplit.length - 1];
        switch (extension.toLowerCase()) {
            case "jpg":
            case "jpeg":
                return Bitmap.CompressFormat.JPEG;
            case "png":
                return Bitmap.CompressFormat.PNG;
            default:
                return Bitmap.CompressFormat.JPEG;
        }
    }


    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean comprobarPermisos(Context context, String[] permisos) {
        if (!Utils.hasPermissions(context, permisos)) {
            ActivityCompat.requestPermissions(((Activity) context), permisos, Constantes.CODE_PERMISSIONS);
            return false;
        } else return true;
    }

    public static String fromFileToBase64Image(File file) {
        //        int degrees = getCameraPhotoOrientation(file.getAbsolutePath());

        Bitmap                bm   = BitmapFactory.decodeFile(file.getAbsolutePath());
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos); //bm is the bitmap object
        byte[] b = baos.toByteArray();

        return Base64.encodeToString(b, Base64.DEFAULT)/* + "|" + degrees*/;
    }

    public static byte[] convertBase64ToByteArray(ImagenCommons imagenCommons) {
//        String[] cadenaSplit = cadena.split("\\|");
//        String base64 = cadenaSplit[0];
//        int degrees = Integer.parseInt(cadenaSplit[1]);
//        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        int degrees = imagenCommons.getOrientacion();
        byte[] bytes = Base64.decode(imagenCommons.getContenido(), Base64.DEFAULT);

        Bitmap storedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);

        Matrix mat = new Matrix();
        if (degrees == 90 || degrees == 270) mat.postRotate(degrees);
        storedBitmap = Bitmap.createBitmap(storedBitmap, 0, 0, storedBitmap.getWidth(), storedBitmap.getHeight(), mat, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        storedBitmap.compress(imagenCommons.getFormato(), 100, stream);

        return stream.toByteArray();
    }
}
