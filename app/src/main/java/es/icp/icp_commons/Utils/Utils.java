package es.icp.icp_commons.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Base64;
import android.util.DisplayMetrics;

import java.io.ByteArrayOutputStream;

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

    public static byte[] convertBase64ToByteArray(String cadena) {
        String[] cadenaSplit = cadena.split("\\|");
        String base64 = cadenaSplit[0];
        int degrees = Integer.parseInt(cadenaSplit[1]);
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);

        Bitmap storedBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, null);

        Matrix mat = new Matrix();
        if (degrees == 90 || degrees == 270) mat.postRotate(degrees);
        storedBitmap = Bitmap.createBitmap(storedBitmap, 0, 0, storedBitmap.getWidth(), storedBitmap.getHeight(), mat, true);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        storedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);

        return stream.toByteArray();
    }
}
