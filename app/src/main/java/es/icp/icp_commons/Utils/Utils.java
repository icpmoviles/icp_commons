package es.icp.icp_commons.Utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Base64;
import android.util.DisplayMetrics;

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

    public static byte[] convertBase64ToByteArray(String base64) {
        return Base64.decode(base64, Base64.DEFAULT);
    }
}
