package es.icp.icp_commons.Interfaces;

import android.content.DialogInterface;

public interface CustomSmartDialogQResponse {
    void onAccept(int retCode, int quantity, DialogInterface dialog);
    void onCancel(int retCode, int quantity, DialogInterface dialog);
}
