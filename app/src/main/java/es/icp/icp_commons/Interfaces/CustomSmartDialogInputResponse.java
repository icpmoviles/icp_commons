package es.icp.icp_commons.Interfaces;

import android.content.DialogInterface;

public interface CustomSmartDialogInputResponse {

    int ACEPTAR  = 0;
    int CANCELAR = 1;
    int NEUTRAL  = 2;

    void onResponse(int retCode, String input, DialogInterface dialog);
}
