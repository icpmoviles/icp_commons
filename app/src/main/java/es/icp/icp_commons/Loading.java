package es.icp.icp_commons;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import es.icp.icp_commons.Helpers.MyApplication;
import es.icp.icp_commons.Interfaces.AdjuntarImagenesListener;
import es.icp.icp_commons.Objects.ImagenCommons;

public class Loading {

    private static ProgressDialog progress;
    private static Context        contextWS;
    private static CustomSmartDialog customSmartDialog;

    /**
     * Muestra un diálogo de carga.
     * Por defecto, el diálogo no se puede cancelar por el usuario haciendo click fuera del mismo.
     *
     * @param ctx Context. Contexto de la aplicación.
     * @author Ventura de Lucas
     */
    public static void ShowLoading(Context ctx) {
        ShowLoading(ctx, false);
    }

    /**
     * Muestra un diálogo de carga.
     *
     * @param ctx        Context. Contexto de la aplicación.
     * @param cancelable boolean. Indica si el diálogo se puede cancelar o no por el usuario haciendo click fuera del mismo. ('true': cancelable; 'false': no cancelable)
     * @author Ventura de Lucas
     */
    public static void ShowLoading(Context ctx, boolean cancelable) {
        String title   = ctx.getString(R.string.cargando);
        String message = ctx.getString(R.string.obteniendo_informacion);
        ShowLoading(ctx, title, message, cancelable);
    }

    /**
     * Muestra un diálogo de carga.
     *
     * @param ctx        Context. Contexto de la aplicación.
     * @param title      String. Título del diálogo de carga.
     * @param message    String. Mensaje del diálogo de carga.
     * @param cancelable boolean. Indica si el diálogo se puede cancelar o no por el usuario haciendo click fuera del mismo. ('true': cancelable; 'false': no cancelable)
     * @author Ventura de Lucas
     */
    public static void ShowLoading(final Context ctx, String title, String message, boolean cancelable) {
        if (progress != null) return;
        contextWS = ctx;
        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    progress = new ProgressDialog(ctx);
                    progress.setTitle(title);
                    progress.setMessage(message);
                    progress.setCancelable(cancelable); // disable dismiss by tapping outside of the dialog
                    progress.show();
                } catch (Exception e) {

                }
            }
        });
    }

    /**
     * Muestra un diálogo de carga.
     *
     * @param ctx        Context. Contexto de la aplicación.
     * @param title      String. Título del diálogo de carga.
     * @param message    String. Mensaje del diálogo de carga.
     * @param cancelable boolean. Indica si el diálogo se puede cancelar o no por el usuario haciendo click fuera del mismo. ('true': cancelable; 'false': no cancelable)
     * @author Ventura de Lucas
     */
    public static void ShowSmartLoading(final Context ctx, String title, String message, boolean cancelable) {
        if (customSmartDialog != null/* && customSmartDialog.isShowingLoading()*/) return;
        contextWS = ctx;
        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mostrarCustomSmartDialog(ctx, title, message, cancelable, null);
            }
        });
    }

    /**
     * Muestra un diálogo de carga.
     *
     * @param ctx        Context. Contexto de la aplicación.
     * @param title      String. Título del diálogo de carga.
     * @param message    String. Mensaje del diálogo de carga.
     * @param cancelable boolean. Indica si el diálogo se puede cancelar o no por el usuario haciendo click fuera del mismo. ('true': cancelable; 'false': no cancelable)
     * @author Ventura de Lucas
     */
    public static void ShowSmartLoading(final Context ctx, String title, String message, boolean cancelable, CustomSmartDialog.LoadingListener loadingListener) {
        if (customSmartDialog != null/* && customSmartDialog.isShowingLoading()*/) return;
        contextWS = ctx;
        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mostrarCustomSmartDialog(ctx, title, message, cancelable, loadingListener);
            }
        });
    }

    private static void mostrarCustomSmartDialog(Context context, String title, String message, boolean cancelable, CustomSmartDialog.LoadingListener loadingListener) {
        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).isIconoGif(true).setMostrarLoading(true).setMostrarImagenPredeterminada(false).setIconoTitulo(R.raw.loading_gif).setTitulo(title).setMensaje(message).setMostrarNegativo(false).setMostrarPositivo(false).setAutoDismiss(true).setCancelable(cancelable)
                .build();
        customSmartDialog = new CustomSmartDialog();
        customSmartDialog.dialogGenerico(context, config, null, loadingListener);
    }

    public static void HideSmartLoading() {
        if (customSmartDialog != null) {
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    try {
//                        while (CustomSmartDialog.isEnConstruccion()) {
//                            Thread.sleep(100);
//                        }
                        customSmartDialog.dismissLoading();
                        customSmartDialog = null;
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }).start();
        }
    }

    /**
     * Oculta el diálogo de carga que se haya mostrado previamente.
     *
     * @author Ventura de Lucas
     */
    public static void HideLoading() {
        MyApplication.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (progress != null) {
                        progress.dismiss();
                        progress = null;
                    }
                } catch (Exception e) {

                }
            }
        });
    }
}
