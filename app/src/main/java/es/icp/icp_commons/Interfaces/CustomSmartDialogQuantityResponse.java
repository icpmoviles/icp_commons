package es.icp.icp_commons.Interfaces;

import android.content.DialogInterface;

/**
 * @deprecated Usar CustomSmartDialogQResponse
 */
@Deprecated
public interface CustomSmartDialogQuantityResponse {

    int ACEPTAR  = 0;
    int CANCELAR = 1;

    void onResponse(int retCode, int quantity, DialogInterface dialog);
}
