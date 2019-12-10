package es.icp.icp_commons;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Interfaces.CustomDialogResponse;
import es.icp.icp_commons.Interfaces.ListenerAccion;
import es.icp.icp_commons.Interfaces.ResponseDialog;

@SuppressWarnings({"unused", "WeakerAccess"})
public class CustomDialog {

    private Context context;
    private String titulo;
    private int Kind;
    private List<Button> buttons;
    private List<TextView> textViews;
    private AlertDialog dialog;
    private boolean cancelable;
    private int color = 0;
    private Drawable drawable = null;

    /**
     * Constructor CustomDialog de 2 parámetros.
     *
     * @param context Context. Contexto de la aplicación.
     * @param kind Constante (DIALOG_NORMAL / DIALOG_BUTTONS). Define el tipo de diálogo.
     */
    public CustomDialog(Context context, int kind) {
        this.context = context;
        this.Kind = kind;
        this.cancelable = true;
    }

    /**
     * Constructor CustomDialog de 3 parámetros.
     *
     * @param context Context. Contexto de la aplicación.
     * @param titulo String. Título del diálogo.
     * @param kind Constante (DIALOG_NORMAL / DIALOG_BUTTONS). Define el tipo de diálogo.
     */
    public CustomDialog(Context context, String titulo, int kind) {
        this(context, kind);
        this.titulo = titulo;
    }

    /**
     * Constructor CustomDialog de 4 parámetros.
     *
     * @param context Context. Contexto de la aplicación.
     * @param kind Constante (DIALOG_NORMAL / DIALOG_BUTTONS). Define el tipo de diálogo.
     * @param color int. Color del decorado de la cabecera del diálogo.
     * @param drawable Drawable. Imagen o icono a visualizar en la cabecera del diálogo.
     */
    public CustomDialog(Context context, int kind, int color, Drawable drawable) {
        this(context, kind);
        this.color = color;
        this.drawable = drawable;
    }

    /**
     * Constructor CustomDialog de 5 parámetros.
     *
     * @param context Context. Contexto de la aplicación.
     * @param titulo String. Título del diálogo.
     * @param kind Constante (DIALOG_NORMAL / DIALOG_BUTTONS). Define el tipo de diálogo.
     * @param color int. Color del decorado de la cabecera del diálogo.
     * @param drawable Drawable. Imagen o icono a visualizar en la cabecera del diálogo.
     */
    public CustomDialog(Context context, String titulo, int kind, int color, Drawable drawable) {
        this(context, kind, color, drawable);
        this.titulo = titulo;
    }

    /**
     * Añade un mensaje al cuerpo del diálogo.
     *
     * @param text String. Mensaje del diálogo.
     */
    public void AddMensaje(String text)
    {
        TextView textView = new TextView(context);
        textView.setText(text);
        //textView.setTextSize(12);
        textView.setTextColor(Color.BLACK);
        textView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0, 22);
        textView.setLayoutParams(params);

        if (textViews == null)
            textViews = new ArrayList<>();
        textViews.add(textView);
    }

    /**
     * Añade un botón al diálogo.
     *
     * @param text String. Texto del botón.
     * @param response CustomDialogResponse. Listener con la respuesta del botón.
     * @param style int. Estilo del botón.
     */
    public void AddButton(String text, final CustomDialogResponse response, int style)
    {

        Button btn = new Button(context);
        btn.setBackgroundResource(style);
        btn.setTextColor(Color.WHITE);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,0,0, 64);
        btn.setLayoutParams(params);
        btn.setText(text);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                response.onResponse( dialog);
            }
        });


        if (buttons == null)
            buttons = new ArrayList<>();
        buttons.add(btn);
    }

    /**
     * Se muestra el diálogo previamente construido.
     *
     */
    public void Show() {
        ViewGroup viewGroup = ((Activity) context).findViewById(android.R.id.content);
        int layout = 0;
        switch (Kind) {
            case Constantes.DIALOG_NORMAL : layout = R.layout.alert_dialog; break;
            case Constantes.DIALOG_BUTTONS : layout = R.layout.alert_dialog_buttons; break;
        }

        final View dialogView = LayoutInflater.from(context).inflate(layout, viewGroup, false);
        TextView tituloView = dialogView.findViewById(R.id.tituloCustom);

        if (Kind == Constantes.DIALOG_BUTTONS) {
            if (color != 0) {
                TextView txtAtencion = dialogView.findViewById(R.id.txtAtencion);
                txtAtencion.setBackgroundColor(color);
            }
            if (drawable != null) {
                ImageView imagen = dialogView.findViewById(R.id.imagen);
                imagen.setImageDrawable(drawable);
                imagen.setScaleType(ImageView.ScaleType.FIT_CENTER);
            }
        }

        tituloView.setText(Html.fromHtml(titulo));


        //Añadir diferentes mensajes en la vista
        if (textViews != null && textViews.size() > 0 && Kind == Constantes.DIALOG_BUTTONS)
        {
            LinearLayout linearLayout = dialogView.findViewById(R.id.linearButtonsDialog);
            for (TextView txt : textViews)
            {
                linearLayout.addView(txt);
            }
        }



        if (buttons != null && buttons.size() > 0 && Kind == Constantes.DIALOG_BUTTONS)
        {
            LinearLayout linearLayout = dialogView.findViewById(R.id.linearButtonsDialog);
            for (Button btn : buttons)
            {
                linearLayout.addView(btn);
            }
        }


        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        builder.setCancelable(this.cancelable);
        dialog = builder.create();
        dialog.show();
    }

    /**
     * Añade un título al diálogo.
     *
     * @param titulo String. Título del diálogo.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Permite o no  que el usuario puede cerrar el diálogo clicando fuera de la vista.
     *
     * @param cancelable boolean. Si es 'true', se puede cancelar clicando fuera; 'false', no se puede.
     */
    public void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    /**
     * Construye y muestra un diálogo de advertencia.
     *
     * @param ctx Context. Contexto de la aplicación.
     * @param texto String. Texto informativo para el cuerpo del diálogo de advertencia.
     * @param color int. Color del decorado de la cabecera del diálogo.
     * @param drawable Drawable. Imagen o icono a visualizar en la cabecera del diálogo.
     * @param listener ListenerAccion. Listener para la respuesta al click del botón 'Aceptar'
     */
    public static void dialogAdvertencia (final Context ctx, final String texto, final int color, final Drawable drawable, final ListenerAccion listener){
        Activity activity = (Activity) ctx;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run (){
                CustomDialog dialog = new CustomDialog(ctx, Constantes.DIALOG_BUTTONS, color, drawable);
                dialog.setTitulo(texto);
                dialog.AddButton(ctx.getString(R.string.aceptar), new CustomDialogResponse() {
                    @Override
                    public void onResponse (Dialog dialog){
                        dialog.dismiss();
                        if (listener != null) listener.accion(0, null);
                    }
                }, R.drawable.rounded_black_button);
                dialog.Show();
            }
        });
    }

    /**
     * Construye y muestra un diálogo común con valores de SI / NO en los botones, la acción de estos botones se toma en ResponseDialog.
     *
     * @param ctx Context. Contexto de la aplicación.
     * @param texto String. Texto informativo para el cuerpo del diálogo.
     * @param color int. Color del decorado de la cabecera del diálogo.
     * @param drawable Drawable. Imagen o icono a visualizar en la cabecera del diálogo.
     * @param responseDialog ListenerAccion. Listener que emite la respuesta SI / NO.
     */
    //
    public static  void dialogSiNO (final Context ctx, final String texto, final int color, final Drawable drawable, final ResponseDialog responseDialog){
        Activity activity = (Activity) ctx;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run (){
                CustomDialog dialog = new CustomDialog(ctx, Constantes.DIALOG_BUTTONS, color, drawable);
                dialog.setTitulo(texto);
                dialog.AddButton(ctx.getString(R.string.si), new CustomDialogResponse() {
                    @Override
                    public void onResponse (Dialog dialog){
                        dialog.dismiss();
                        if (responseDialog != null) responseDialog.si();
                    }
                }, R.drawable.rounded_orange_button);

                dialog.AddButton(ctx.getString(R.string.no), new CustomDialogResponse() {
                    @Override
                    public void onResponse (Dialog dialog){
                        dialog.dismiss();
                        if (responseDialog != null) responseDialog.no();

                    }
                }, R.drawable.rounded_black_button);
                dialog.Show();
            }
        });
    }
}
