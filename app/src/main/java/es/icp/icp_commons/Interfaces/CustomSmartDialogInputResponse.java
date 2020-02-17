package es.icp.icp_commons.Interfaces;

import android.app.AlertDialog;
import android.content.DialogInterface;

import es.icp.icp_commons.CustomSmartDialog;

public interface CustomSmartDialogInputResponse {

    int ACEPTAR = 0;
    int CANCELAR = 1;
    int NEUTRAL = 2;

    void onResponse(int retCode, String input, DialogInterface dialog);
}
