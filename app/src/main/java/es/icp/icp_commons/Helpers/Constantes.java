package es.icp.icp_commons.Helpers;

import android.Manifest;

public class Constantes {

    public static final int DIALOG_NORMAL  = 0;
    public static final int DIALOG_BUTTONS = 1;
    public static final int AUDIO_EXITO    = 0;
    public static final int AUDIO_ERROR    = 1;
    public static final int AUDIO_OHOH     = 2;

    public static final int NUM_RETRY = 3;
    public static final int TIMEOUT   = 15000;

    public static final int CAMERA_REQUEST_CODE = 7002;
    public static final int CODE_PERMISSIONS    = 4001;

    public static String[] PERMISOS_LOCALIZACION = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
}
