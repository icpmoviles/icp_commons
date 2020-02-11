package es.icp.pruebas_commons;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import java.util.List;

import es.icp.icp_commons.CheckRequest;
import es.icp.icp_commons.CustomDialog;
import es.icp.icp_commons.CustomNotification;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Interfaces.CustomDialogResponse;
import es.icp.icp_commons.Interfaces.ListenerEditTextAccion;
import es.icp.icp_commons.Interfaces.VolleyCallBack;
import es.icp.icp_commons.Objects.ParametrosPeticion;
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
        };

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
    }
}
