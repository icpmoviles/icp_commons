package es.icp.icp_commons.Objects;

import androidx.appcompat.app.AlertDialog;

public class AlertDialog2 {
    private AlertDialog dialog;
    private boolean     loadingDialog;

    public AlertDialog2(AlertDialog dialog) {
        this(dialog, false);
    }

    public AlertDialog2(AlertDialog dialog, boolean loadingDialog) {
        this.dialog        = dialog;
        this.loadingDialog = loadingDialog;
    }

    public AlertDialog getDialog() {
        return dialog;
    }

    public void setDialog(AlertDialog dialog) {
        this.dialog = dialog;
    }

    public boolean isLoadingDialog() {
        return loadingDialog;
    }

    public void setLoadingDialog(boolean loadingDialog) {
        this.loadingDialog = loadingDialog;
    }
}
