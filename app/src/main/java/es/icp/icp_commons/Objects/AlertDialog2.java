package es.icp.icp_commons.Objects;

import androidx.appcompat.app.AlertDialog;

import es.icp.icp_commons.DialogConfig;

public class AlertDialog2 {
    private AlertDialog dialog;
    private boolean     loadingDialog;
    private DialogConfig config;

    public AlertDialog2(AlertDialog dialog) {
        this(dialog, false);
    }

    public AlertDialog2(AlertDialog dialog, boolean loadingDialog) {
        this.dialog        = dialog;
        this.loadingDialog = loadingDialog;
    }

    public AlertDialog2(AlertDialog dialog, boolean loadingDialog, DialogConfig config) {
        this.dialog = dialog;
        this.loadingDialog = loadingDialog;
        this.config = config;
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

    public DialogConfig getConfig() {
        return config;
    }

    public void setConfig(DialogConfig config) {
        this.config = config;
    }
}
