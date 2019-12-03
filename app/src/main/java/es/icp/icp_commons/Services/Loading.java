package es.icp.icp_commons.Services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;

import es.icp.icp_commons.R;

public class Loading {

    private static ProgressDialog progress;
    private static Context contextWS;

    public static void ShowLoading(Context ctx) {
        ShowLoading(ctx, false);
    }

    public static void ShowLoading(Context ctx, boolean cancelable){
        String title = ctx.getString(R.string.cargando);
        String message = ctx.getString(R.string.obteniendo_informacion);
        ShowLoading(ctx, title, message, cancelable);
    }

    public static void ShowLoading(final Context ctx, String title, String message, boolean cancelable) {
        contextWS = ctx;
        ((Activity)(ctx)).runOnUiThread(new Runnable() {
            @Override
            public void run (){
                try{
                    progress = new ProgressDialog(ctx);
                    progress.setTitle(ctx.getString(R.string.cargando));
                    progress.setMessage(ctx.getString(R.string.obteniendo_informacion));
                    progress.setCancelable(false); // disable dismiss by tapping outside of the dialog
                    progress.show();
                }catch (Exception e){

                }
            }
        });
    }

    public static void HideLoading(){
        ((Activity)(contextWS)).runOnUiThread(new Runnable() {
            @Override
            public void run (){
                try{
                    if (progress != null){
                        progress.dismiss();
                    }
                }catch (Exception e){

                }
            }
        });
    }
}
