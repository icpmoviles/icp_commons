package es.icp.icp_commons.Helpers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.Objects;

public class Helper {
    public static boolean CheckConnection(Context mContext) {
        boolean connected = false;
        if (mContext != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null) return false;
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI) != null) {
                if (Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() == NetworkInfo.State.CONNECTED) {
                    connected = true;
                }
            }
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null) {

                if (Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() == NetworkInfo.State.CONNECTED) {
                    switch (Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getSubtype()) {
                        case TelephonyManager.NETWORK_TYPE_EVDO_0: //3G
                        case TelephonyManager.NETWORK_TYPE_EVDO_A:
                        case TelephonyManager.NETWORK_TYPE_HSDPA:
                        case TelephonyManager.NETWORK_TYPE_HSPA:
                        case TelephonyManager.NETWORK_TYPE_HSUPA:
                        case TelephonyManager.NETWORK_TYPE_UMTS:
                        case TelephonyManager.NETWORK_TYPE_EHRPD:
                        case TelephonyManager.NETWORK_TYPE_EVDO_B:
                        case TelephonyManager.NETWORK_TYPE_HSPAP:
                        case TelephonyManager.NETWORK_TYPE_LTE: //4G
                        case TelephonyManager.NETWORK_TYPE_NR: //5G
                            connected = true;
                            break;

                        default:
                            connected = false;
                            break;
                    }
                }
            }
        }

        return connected;
    }
}
