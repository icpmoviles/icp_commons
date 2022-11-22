package es.icp.icp_commons.Interfaces;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

public interface CustomSmartDialogSiNoResponse {
    void positivo(String valor, @Nullable AlertDialog dialog);

    void negativo(String valor, @Nullable AlertDialog dialog);
}
