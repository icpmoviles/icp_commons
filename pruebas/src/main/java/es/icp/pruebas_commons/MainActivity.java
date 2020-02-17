package es.icp.pruebas_commons;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.CustomDialog;
import es.icp.icp_commons.CustomEditText;
import es.icp.icp_commons.CustomNotification;
import es.icp.icp_commons.CustomSmartDialog;
import es.icp.icp_commons.CustomTitle;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Interfaces.CustomDialogButtonClicked;
import es.icp.icp_commons.Interfaces.CustomDialogResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogInputResponse;
import es.icp.icp_commons.Interfaces.ListenerEditTextAccion;
import es.icp.pruebas_commons.databinding.MainActivityBinding;
import es.icp.pruebas_commons.helpers.GlobalVariables;

public class MainActivity extends Activity {

    private Context context = MainActivity.this;
    private MainActivityBinding binding;
    private Handler handler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.main_activity);
        setEvents();
        binding.setHandler(handler);
    }

    private void setEvents() {
        handler = new Handler() {
            @Override
            public void onClickBtn1(View view) {
                crearNotif1();
            }

            @Override
            public void onClickBtn2(View view) {
                crearDialog1();
            }

            @Override
            public void onClickBtn3(View view) {
                crearDialog2();
            }

            @Override
            public void onClickBtn4(View view) {
                crearDialog3();
            }

            @Override
            public void onClickBtn5(View view) {
                crearDialog4();
            }

            @Override
            public void onClickBtn6(View view) {
                crearDialog5();
            }

            @Override
            public void onClickBtn7(View view) {
                crearDialog6();
            }
        };
    }

    private void crearDialog6() {
        CustomSmartDialog.dialogInput(context, "Instalar", "Introduce el código de abonado", "Código abonado", getDrawable(R.drawable.ic_search_black_24dp), getDrawable(R.drawable.ic_person_black_24dp), 25, new CustomSmartDialogInputResponse() {
            @Override
            public void onResponse(int retCode, String input, DialogInterface dialog) {
                if (retCode == ACEPTAR) {
                    CustomNotification customNotification = new CustomNotification.Builder(context)
                            .setSimpleMode()
                            .setDuration(CustomNotification.LENGTH_SHORT)
                            .build();
                    customNotification.showText(input);
                } else {
                    CustomNotification customNotification = new CustomNotification.Builder(context)
                            .setSimpleMode()
                            .setDuration(CustomNotification.LENGTH_SHORT)
                            .build();
                    customNotification.showText("Cancelado...");
                }
            }
        });
    }

    private void crearDialog5() {
        CustomTitle customTitle = new CustomTitle.Builder(context)
                .setTitle("Datos personales")
                .setIcon(getDrawable(R.drawable.ic_search_black_24dp))
                .setBackgroundColor(R.color.colorAccent)
                .setTextColor(R.color.white)
                .setIconColor(R.color.white)
                .build();

        TextView message = new CustomSmartDialog.Message.Builder(context)
                .setText("Introduce nombre y apellidos")
                .build();

        final CustomEditText customEditText = new CustomEditText.Builder(context)
                .setHintText("Nombre y apellidos")
                .setStartIconDrawable(getDrawable(R.drawable.ic_person_black_24dp))
                .setStartIconColor(R.color.colorAccent)
                .setTextAppearance(R.style.MyHintStyle)
                .setCounterMaxLength(25)
                .setCounterOverflowAppearance(android.R.color.holo_orange_dark)
                .setErrorIconColor(android.R.color.holo_orange_dark)
                .build();

        CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, "ACEPTAR")
                .setTextColor(R.color.colorAccent)
                .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        CustomNotification customNotification = new CustomNotification.Builder(context)
                                .setSimpleMode()
                                .setDuration(CustomNotification.LENGTH_SHORT)
                                .build();
                        customNotification.showText(customEditText.getText());
                    }
                })
                .build();

        CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, "CANCELAR")
                .setTextColor(android.R.color.darker_gray)
                .setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        CustomNotification customNotification = new CustomNotification.Builder(context)
                                .setSimpleMode()
                                .setDuration(CustomNotification.LENGTH_SHORT)
                                .build();
                        customNotification.showText("Cancelado...");
                    }
                })
                .build();

        new CustomSmartDialog.Builder(context)
                .setTitle(customTitle)
                .addView(message)
                .addView(customEditText)
                .addButton(buttonAceptar)
                .addButton(buttonCancelar)
                .build();
    }

    private void crearDialog3() {
        CustomDialog.dialogInput(context, "Incidencia", "Rellene la incidencia", GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP, new ListenerEditTextAccion() {
            @Override
            public void accion(int code, List<String> inputs) {
                CustomNotification customNotification = new CustomNotification.Builder(context)
                        .setSimpleMode()
                        .setDuration(CustomNotification.LENGTH_SHORT)
                        .build();
                StringBuilder text = new StringBuilder();
                for (String input : inputs) {
                    text.append(input).append(" - ");
                }
                customNotification.showText(text.toString());
            }
        }, "Observaciones");
    }

    private void crearDialog4() {
        CustomDialog dialog = new CustomDialog(context, Constantes.DIALOG_BUTTONS, GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP);
        final ArrayList<StringBuilder> stringBuilders = new ArrayList<>();
        dialog.setTitulo("Rellene la incidencia");
        dialog.setTituloAdvertencia("Incidencia");
        final StringBuilder observaciones = new StringBuilder();
        dialog.AddEditText("Observaciones", observaciones, 0);
        dialog.AddButton(context.getString(es.icp.icp_commons.R.string.aceptar), new CustomDialogButtonClicked() {
            @Override
            public void onResponse(CustomDialog dialog) {
                if (observaciones.toString().isEmpty()) {
                    dialog.setError(0, "Introduzca observaciones");
                } else {
                    dialog.dismiss();
                    CustomNotification customNotification = new CustomNotification.Builder(context)
                            .setSimpleMode()
                            .setDuration(CustomNotification.LENGTH_SHORT)
                            .build();
                    customNotification.showText(observaciones.toString());
                }
            }
        }, es.icp.icp_commons.R.drawable.rounded_black_button);
        dialog.Show();
    }

    private void crearDialog2() {
        CustomDialog.dialogInput(context, "Diálogo con editTexts", GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP, new ListenerEditTextAccion() {
            @Override
            public void accion(int code, List<String> inputs) {
                CustomNotification customNotification = new CustomNotification.Builder(context)
                        .setSimpleMode()
                        .setDuration(CustomNotification.LENGTH_SHORT)
                        .build();
                StringBuilder text = new StringBuilder();
                for (String input : inputs) {
                    text.append(input).append(" - ");
                }
                customNotification.showText(text.toString());
            }
        }, "Introduzca su nombre", "Introduzca su apellido", "Introduzca su edad");
    }

    private void crearDialog1() {
        final StringBuilder nombre = new StringBuilder();
        CustomDialog customDialog = new CustomDialog(context, "Diálogo con editText", Constantes.DIALOG_BUTTONS, GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP);
        customDialog.AddMensaje("Este diálogo es la primera prueba de un diálogo con editText");
        customDialog.AddEditText("Introduzca su nombre", nombre, 0);
        customDialog.AddButton("Aceptar", new CustomDialogResponse() {
            @Override
            public void onResponse(Dialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context)
                        .setSimpleMode()
                        .setDuration(CustomNotification.LENGTH_SHORT)
                        .build();
                customNotification.showText(nombre.toString());
            }
        }, R.drawable.rounded_black_button);
        customDialog.Show();
    }

    private void crearNotif1() {
        CustomNotification customNotification = new CustomNotification.Builder(context)
                .setSimpleMode()
                .setDuration(CustomNotification.LENGTH_SHORT)
                .build();
        customNotification.showText("Notificación nº1");
    }




    public interface Handler {
        void onClickBtn1(View view);
        void onClickBtn2(View view);
        void onClickBtn3(View view);
        void onClickBtn4(View view);
        void onClickBtn5(View view);
        void onClickBtn6(View view);
        void onClickBtn7(View view);
    }
}
