package es.icp.pruebas_commons;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.icp.icp_commons.CheckRequest;
import es.icp.icp_commons.CustomDialog;
import es.icp.icp_commons.CustomEditText;
import es.icp.icp_commons.CustomNotification;
import es.icp.icp_commons.CustomSmartDialog;
import es.icp.icp_commons.CustomSmartDialogButton;
import es.icp.icp_commons.CustomTitle;
import es.icp.icp_commons.DialogConfig;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Interfaces.AdjuntarImagenesListener;
import es.icp.icp_commons.Interfaces.CustomDialogButtonClicked;
import es.icp.icp_commons.Interfaces.CustomDialogResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogInputResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogQuantityResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogResponse;
import es.icp.icp_commons.Interfaces.CustomSmartDialogSiNoResponse;
import es.icp.icp_commons.Interfaces.ListenerEditTextAccion;
import es.icp.icp_commons.Interfaces.NewVolleyCallBack;
import es.icp.icp_commons.Loading;
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.Objects.ImagenCommons;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.Objects.SmartButton;
import es.icp.icp_commons.Services.WebService;
import es.icp.icp_commons.Utils.Utils;
import es.icp.logs.core.MyLog;
import es.icp.pruebas_commons.databinding.MainActivityBinding;
import es.icp.pruebas_commons.helpers.CommonsBaseApp;
import es.icp.pruebas_commons.helpers.GlobalVariables;
import es.icp.pruebas_commons.helpers.PruebasLoginRequest;
import es.icp.pruebas_commons.helpers.PruebasLoginResult;

import static es.icp.icp_commons.Helpers.Constantes.DIALOG_NORMAL;

public class MainActivity extends CommonsBaseApp {

    private Context             context = MainActivity.this;
    private MainActivityBinding binding;
    private Handler             handler;
    private CustomSmartDialog   customSmartDialog;

    public MainActivity() {
    }

    //    public MainActivity(Context context) {
    //        super(context);
    //    }

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

            @Override
            public void onClickBtn8(View view) {
                crearDialog7();
            }

            @Override
            public void onClickBtn9(View view) {
                crearDialog8();
            }

            @Override
            public void onClickBtn10(View view) {
                crearDialog9();
            }

            @Override
            public void onClickBtn11(View view) {
                crearDialog10();
            }

            @Override
            public void onClickBtn12(View view) {
                crearDialog11();
            }

            @Override
            public void onClickBtn13(View view) {
                crearDialog12();
            }

            @Override
            public void onClickBtn14(View view) {
                crearDialog13();
            }

            @Override
            public void onClickBtn15(View view) {
                crearDialog14();
            }

            @Override
            public void onClickBtn16(View view) {
                crearDialog15();
            }

            @Override
            public void onClickBtn17(View view) {
                crearDialog16();
            }

            @Override
            public void onClickBtn18(View view) {
                crearDialog17();
            }

            @Override
            public void onClickBtn19(View view) {
                crearDialog18();
            }

            @Override
            public void onClickBtn20(View view) {
                crearDialog19();
            }

            @Override
            public void onClickBtn21(View view) {
                checkRequest1();
            }

            @Override
            public void onClickBtn22(View view) {
                checkRequest2();
            }

            @Override
            public void onClickBtn23(View view) {
                crearDialog20();
            }

            @Override
            public void onClickBtn24(View view) {
                crearDialog21();
            }

            @Override
            public void onClickBtn25(View view) {
                mostrarSmartLoading();
            }
        };
    }

    private void mostrarSmartLoading() {
        Loading.ShowSmartLoading(context, "Cargando...", "Por favor, espere mientras se carga la información...", true);
        final Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Loading.HideSmartLoading();
                timer.cancel();
            }
        };
        timer.schedule(timerTask, 5000);
    }

    private void crearDialog21() {
        ArrayList<ImagenCommons> imagenes = new ArrayList<>();
        imagenes.add(new ImagenCommons(context.getDrawable(R.drawable.imagen_demo_comprimida)));
        imagenes.add(new ImagenCommons(context.getDrawable(R.drawable.imagen_demo_comprimida)));
        imagenes.add(new ImagenCommons(context.getDrawable(R.drawable.imagen_demo_comprimida)));

        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).setMostrarImagenPredeterminada(false).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Imágenes incidencia").setMensaje("Esto es un visor de imágenes de las incidencias.").setMostrarNegativo(false).setMostrarPositivo(true).setAutoDismiss(true).setTextoPositivo("ACEPTAR").setMostrarVisorImagenes(true).setImagenes(imagenes).setAdjuntarImagenesListener(new AdjuntarImagenesListener() {
            @Override
            public void imagenAdjuntada(ImagenCommons imagen) {

            }

            @Override
            public void imagenEliminada(int position, ImagenCommons imagen) {

            }
        }).build();
        new CustomSmartDialog().dialogGenerico(context, config, null);
    }

    private void checkRequest2() {
        WebService.TratarExcepcion(context, "MetodoPrueba", 1234, "Prueba de la version_name", new Exception(), "", "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/save_log");
    }

    private void checkRequest1() {
        CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
        customNotification.showText(String.valueOf(Utils.isDebuggable(getApplicationContext())));

        PruebasLoginRequest loginRequest = new PruebasLoginRequest("Pruebas", "p", "2", "28", "SAMSUNG", "A20e", "12121", "12312313", "cLF5wFYRxZE:APA91bFJHOM1MLBDunVBvzVip-MeLkDbfroplfELJSsCxUcX6pvNdwL_vtHiJdNjiQyXLH9zaTjHi3Lmcs-XmvAPtAcWAYbmfWXxP8kI1I4iD-rJKsbMIWasMTq_ocNFHqB_zMrtVsuh", "1.0.136");
        try {
            CheckRequest.CheckAndSend(getApplicationContext(), new ParametrosPeticion(ParametrosPeticion.Method.POST, "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/Login", loginRequest, PruebasLoginResult.class), new NewVolleyCallBack() {
                @Override
                public void onSuccess(Object result) {
                    MyLog.d(result);
                }

                @Override
                public void onError(VolleyError error) {
                    MyLog.d(error);
                }

                @Override
                public void onOffline() {

                }
            }, 0, "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/save_log", true);
        } catch (CheckRequestException ex) {
            ex.printStackTrace();
        }

        //        CheckRequest.ShowActions(context);
    }

    private void crearDialog20() {
        CustomDialog dialog = new CustomDialog(context, DIALOG_NORMAL, GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP);
        //        dialog.setTitulo("Error: sdvasvcsdacsdacsdavcasfdvasfvfqsdVBADVASFDBVSFDABADFBVSDBSFGB");
        dialog.setTitulo("Error: casnjkcsdajkcnsadkcnlsadc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc\nhsdancjksdhuicsjklanbcildn\nasnjcklsjakdncasncasjlc");
        dialog.Show();
    }

    private void crearDialog19() {
        List<SmartButton> botones  = new ArrayList<>();
        SmartButton       btnCrear = new SmartButton(context);
        btnCrear.setText("Crear");
        btnCrear.setBackgroundTintList(ColorStateList.valueOf(getColor(android.R.color.holo_green_light)));
        btnCrear.setCustomListener(new SmartButton.CustomListener() {
            @Override
            public void onClick(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Creando...");
            }
        });
        botones.add(btnCrear);
        SmartButton btnEditar = new SmartButton(context);
        btnEditar.setText("Editar");
        btnEditar.setBackgroundTintList(ColorStateList.valueOf(getColor(android.R.color.holo_orange_light)));
        btnEditar.setCustomListener(new SmartButton.CustomListener() {
            @Override
            public void onClick(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Editando...");
            }
        });
        botones.add(btnEditar);
        SmartButton btnEliminar = new SmartButton(context);
        btnEliminar.setText("Eliminar");
        btnEliminar.setBackgroundTintList(ColorStateList.valueOf(getColor(android.R.color.holo_red_light)));
        btnEliminar.setCustomListener(new SmartButton.CustomListener() {
            @Override
            public void onClick(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Eliminando...");
            }
        });
        botones.add(btnEliminar);

        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Elija una opción").setMensaje("Elija una opción entre las tres opciones posibles que puedes ver...").setMostrarNegativo(false).setMostrarPositivo(false).setAutoDismiss(true).setMostrarBotones(true).setBotones(botones).setCancelable(true).setMostrarImagenPredeterminada(false).build();

        new CustomSmartDialog().dialogGenerico(context, config, null);
    }

    private void crearDialog18() {
        List<SmartButton> botones = new ArrayList<>();
        SmartButton       boton   = new SmartButton(context);
        boton.setText("BARCODE");
        boton.setCustomListener(new SmartButton.CustomListener() {
            @Override
            public void onClick(String valor, AlertDialog dialog) {
                EditText editText = dialog.findViewById(R.id.txtEditText);
                if (editText != null) {
                    editText.setText("Barcode leído");
                }
            }
        });
        botones.add(boton);

        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Pruebas #4").setMensaje("Mensaje de prueba 1número1 con un diálogo que acepta un editText y tres botones neutral (BARCODE), positivo y negativo.").setMostrarNegativo(true).setMostrarPositivo(true).setColorTitulo(android.R.color.holo_red_light).setAutoDismiss(false).setTextoNegativo("CANCELAR").setTextoPositivo("ACEPTAR").setMostrarEditText(true).setIconoEditText(R.drawable.ic_person_black_24dp).setMaxLength(10).setHint("Nombre y apellidos").setMostrarBotones(true).setBotones(botones).build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {
                dialog.dismiss();
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(valor);
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {
                dialog.dismiss();
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("No...");
            }
        });
    }

    private void crearDialog17() {
        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Pruebas #3").setMensaje("Mensaje de prueba 1número1 con un diálogo que acepta un quantity y dos botones positivo yt negativo.").setMostrarNegativo(true).setMostrarPositivo(true).setAutoDismiss(true).setTextoNegativo("CANCELAR").setTextoPositivo("ACEPTAR").setMostrarCantidad(true).setCantidadInicial(7).build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(valor);
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("No...");
            }
        });
    }

    private void crearDialog16() {
        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Pruebas #2").setMensaje("<span style='color:red'>Mensaje de prueba 1número1 con un diálogo que acepta un editText y dos botones positivo yt negativo.</span>").setMostrarNegativo(true).setMostrarPositivo(true).setAutoDismiss(true).setTextoNegativo("CANCELAR").setTextoPositivo("ACEPTAR").setMostrarEditText(true).setIconoEditText(R.drawable.ic_person_black_24dp).setMaxLength(10).setHint("Nombre y apellidos").build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(valor);
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("No...");
            }
        });
    }

    private void crearDialog15() {
        DialogConfig config = new DialogConfig.Builder().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Pruebas #1").setMensaje("Mensaje de prueba 1número1 con un diálogo que acepta una imagen y un solo boton negativo.").setMostrarNegativo(true).setMostrarPositivo(false).setTextoNegativo("CANCELAR").setColorTitulo(android.R.color.holo_red_light).setMostrarImagen(true).setImagen(R.drawable.ic_thumb_up).build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {
                dialog.dismiss();
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("SIIIIII");
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {
                dialog.dismiss();
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("No...");
            }
        });
    }

    private void crearDialog14() {
        //        CustomSmartDialog.dialogGenerico(context, "Diálogo de ejemplo", "Esto es un texto de ejemplo para un diálogo de ejemplo. Esto es un texto de ejemplo para un diálogo de ejemplo. Esto es un texto de ejemplo para un diálogo de ejemplo.", "Nombre y apellidos", 50, new CustomSmartDialogInputResponse());
    }

    private void crearDialog13() {
        //        CustomSmartDialog.dialogTextos(context, "¿Te gusta más el McDonalds que el BurgerKing?", "POSITIVO", "NEGATIVO", new CustomSmartDialogSiNoResponse() {
        //            @Override
        //            public void positivo() {
        //                CustomNotification customNotification = new CustomNotification.Builder(context)
        //                        .setSimpleMode()
        //                        .setDuration(CustomNotification.LENGTH_SHORT)
        //                        .build();
        //                customNotification.showText("SIIIII!!!!!");
        //            }
        //
        //            @Override
        //            public void negativo() {
        //                CustomNotification customNotification = new CustomNotification.Builder(context)
        //                        .setSimpleMode()
        //                        .setDuration(CustomNotification.LENGTH_SHORT)
        //                        .build();
        //                customNotification.showText("No...");
        //            }
        //        });
        //        CustomSmartDialog.dialogSiNo(context, "¿Te gusta más el McDonalds que el BurgerKing?", new CustomSmartDialogSiNoResponse() {
        //            @Override
        //            public void positivo() {
        //                CustomNotification customNotification = new CustomNotification.Builder(context)
        //                        .setSimpleMode()
        //                        .setDuration(CustomNotification.LENGTH_SHORT)
        //                        .build();
        //                customNotification.showText("SIIIII!!!!!");
        //            }
        //
        //            @Override
        //            public void negativo() {
        //                CustomNotification customNotification = new CustomNotification.Builder(context)
        //                        .setSimpleMode()
        //                        .setDuration(CustomNotification.LENGTH_SHORT)
        //                        .build();
        //                customNotification.showText("No...");
        //            }
        //        });
    }

    private void crearDialog12() {
        CustomSmartDialog.dialogToastListener(context, "Toast Dialog", "<span style='color:red'>Mensaje genérico!!!</span>", new CustomSmartDialogResponse() {
            @Override
            public void onResponse(int retCode, DialogInterface dialog) {
                if (retCode == ACEPTAR) {
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText("Aceptado!");
                }
            }
        });
    }

    private void crearDialog11() {
        CustomSmartDialog.dialogToast(context, "Toast Dialog", "Mensaje genérico!!!");
    }

    private void crearDialog10() {
        CustomSmartDialog.dialogImage(context, "Material Recepcionado", getDrawable(R.drawable.ic_search_black_24dp), getDrawable(R.drawable.ic_thumb_up), new CustomSmartDialogResponse() {
            @Override
            public void onResponse(int retCode, DialogInterface dialog) {
                if (retCode == ACEPTAR) {
                    dialog.dismiss();
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText("Aceptado!");
                }
            }
        });
    }

    private void crearDialog9() {
        customSmartDialog = CustomSmartDialog.dialogInputExtra(context, "Instalar", "Introduce el código de abonado", "Código abonado", getDrawable(R.drawable.ic_search_black_24dp), getDrawable(R.drawable.ic_person_black_24dp), 25, "BARCODE", new CustomSmartDialogInputResponse() {
            @Override
            public void onResponse(int retCode, String input, DialogInterface dialog) {
                if (retCode == ACEPTAR) {
                    dialog.dismiss();
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText(input);
                } else if (retCode == CANCELAR) {
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText("Cancelado...");
                } else {
                    neutralAction();
                }
            }
        });
    }

    private void neutralAction() {
        CustomEditText customEditText = (CustomEditText) customSmartDialog.getView(1);
        customEditText.setText("BARCODE");
    }

    private void crearDialog8() {
        CustomSmartDialog.dialogButtons(context, "Selecciona una opción", getDrawable(R.drawable.ic_search_black_24dp), new CustomSmartDialogButton("<span style'color:red'>Activar</span>", R.drawable.rounded_primary_medium_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Activar seleccionado");
            }
        }), new CustomSmartDialogButton("Editar", R.drawable.rounded_primary_medium_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Editar seleccionado");
            }
        }), new CustomSmartDialogButton("Eliminar", R.drawable.rounded_primary_medium_button, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Eliminar seleccionado");
            }
        }));
    }

    private void crearDialog7() {
        CustomSmartDialog.dialogQuantity(context, "Referencia_cliente2_A", "Modifica la cantidad", getDrawable(R.drawable.ic_edit_blue_24dp), 7, new CustomSmartDialogQuantityResponse() {
            @Override
            public void onResponse(int retCode, int quantity, DialogInterface dialog) {
                if (retCode == ACEPTAR) {
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText(String.valueOf(quantity));
                }
            }
        });
    }

    private void crearDialog6() {
        CustomSmartDialog.dialogInput(context, "Instalar", "Introduce el código de abonado", "Código abonado", getDrawable(R.drawable.ic_search_black_24dp), getDrawable(R.drawable.ic_person_black_24dp), 25, new CustomSmartDialogInputResponse() {
            @Override
            public void onResponse(int retCode, String input, DialogInterface dialog) {
                if (retCode == ACEPTAR) {
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText(input);
                } else {
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                    customNotification.showText("Cancelado...");
                }
            }
        });
    }

    private void crearDialog5() {
        CustomTitle customTitle = new CustomTitle.Builder(context).setTitle("Datos personales").setIcon(getDrawable(R.drawable.ic_search_black_24dp)).setBackgroundColor(R.color.colorAccent).setTextColor(R.color.white).setIconColor(R.color.white).build();

        TextView message = new CustomSmartDialog.Message.Builder(context).setText("Introduce nombre y apellidos").build();

        final CustomEditText customEditText = new CustomEditText.Builder(context).setHintText("Nombre y apellidos").setStartIconDrawable(getDrawable(R.drawable.ic_person_black_24dp)).setStartIconColor(R.color.colorAccent).setTextAppearance(R.style.MyHintStyle).setCounterMaxLength(25).setCounterOverflowAppearance(android.R.color.holo_orange_dark).setErrorIconColor(android.R.color.holo_orange_dark).build();

        CustomSmartDialog.Button buttonAceptar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.POSSITIVE, "ACEPTAR").setTextColor(R.color.colorAccent).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(customEditText.getText());
            }
        }).build();

        CustomSmartDialog.Button buttonCancelar = new CustomSmartDialog.Button.Builder(CustomSmartDialog.Button.Type.NEGATIVE, "CANCELAR").setTextColor(android.R.color.darker_gray).setOnClickListener(new CustomSmartDialog.Button.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Cancelado...");
            }
        }).build();

        new CustomSmartDialog.Builder(context).setTitle(customTitle).addView(message).addView(customEditText).addButton(buttonAceptar).addButton(buttonCancelar).build();
    }

    private void crearDialog3() {
        CustomDialog.dialogInput(context, "Incidencia", "Rellene la incidencia", GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP, new ListenerEditTextAccion() {
            @Override
            public void accion(int code, List<String> inputs) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                StringBuilder      text               = new StringBuilder();
                for (String input : inputs) {
                    text.append(input).append(" - ");
                }
                customNotification.showText(text.toString());
            }
        }, "Observaciones");
    }

    private void crearDialog4() {
        CustomDialog                   dialog         = new CustomDialog(context, Constantes.DIALOG_BUTTONS, GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP);
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
                    CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
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
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                StringBuilder      text               = new StringBuilder();
                for (String input : inputs) {
                    text.append(input).append(" - ");
                }
                customNotification.showText(text.toString());
            }
        }, "Introduzca su nombre", "Introduzca su apellido", "Introduzca su edad");
    }

    private void crearDialog1() {
        final StringBuilder nombre       = new StringBuilder();
        CustomDialog        customDialog = new CustomDialog(context, "Diálogo con editText", Constantes.DIALOG_BUTTONS, GlobalVariables.COLOR_APP, GlobalVariables.ICONO_APP);
        customDialog.AddMensaje("Este diálogo es la primera prueba de un diálogo con editText");
        customDialog.AddEditText("Introduzca su nombre", nombre, 0);
        customDialog.AddButton("Aceptar", new CustomDialogResponse() {
            @Override
            public void onResponse(Dialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(nombre.toString());
            }
        }, R.drawable.rounded_black_button);
        customDialog.Show();
    }

    private void crearNotif1() {
        //        CustomNotification customNotification = new CustomNotification.Builder(context)
        //                .setSimpleMode()
        //                .setDuration(CustomNotification.LENGTH_SHORT)
        //                .build();
        //        customNotification.showText("Notificación nº1");

        final CustomNotification customNotification = new CustomNotification.Builder(context).setProgressMode().setDuration(CustomNotification.LENGTH_LONG) // .setDuration(CutomNotification.LENGTH_MEDIUM) // .setDuration(CutomNotification.LENGTH_LONG)
                .setMax(100).setProgress(40).setMinimizable(false).setText("Esto es un progress bar... (40%)").build();

        customNotification.show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            customNotification.setProgress(60);
                            customNotification.setText("Esto es un progress bar... (60%)");
                            //                            customNotification.show();
                        }
                    });
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public interface Handler {
        void onClickBtn1(View view);

        void onClickBtn2(View view);

        void onClickBtn3(View view);

        void onClickBtn4(View view);

        void onClickBtn5(View view);

        void onClickBtn6(View view);

        void onClickBtn7(View view);

        void onClickBtn8(View view);

        void onClickBtn9(View view);

        void onClickBtn10(View view);

        void onClickBtn11(View view);

        void onClickBtn12(View view);

        void onClickBtn13(View view);

        void onClickBtn14(View view);

        void onClickBtn15(View view);

        void onClickBtn16(View view);

        void onClickBtn17(View view);

        void onClickBtn18(View view);

        void onClickBtn19(View view);

        void onClickBtn20(View view);

        void onClickBtn21(View view);

        void onClickBtn22(View view);

        void onClickBtn23(View view);

        void onClickBtn24(View view);

        void onClickBtn25(View view);
    }
}
