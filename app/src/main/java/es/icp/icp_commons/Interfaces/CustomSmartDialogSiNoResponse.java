package es.icp.icp_commons.Interfaces;


import androidx.appcompat.app.AlertDialog;

public interface CustomSmartDialogSiNoResponse {
    void positivo(String valor, AlertDialog dialog);

    void negativo(String valor, AlertDialog dialog);
}
