package es.icp.pruebas_commons;

import static es.icp.icp_commons.Helpers.Constantes.DIALOG_NORMAL;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.android.volley.VolleyError;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.icp.icp_commons.Camara.Camara;
import es.icp.icp_commons.CheckRequest;
import es.icp.icp_commons.CommonsGeocoder;
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
import es.icp.icp_commons.Interfaces.GeocoderListener;
import es.icp.icp_commons.Interfaces.ListenerEditTextAccion;
import es.icp.icp_commons.Interfaces.NewVolleyCallBack;
import es.icp.icp_commons.Interfaces.ResponseDialog;
import es.icp.icp_commons.Loading;
import es.icp.icp_commons.Objects.CheckRequestException;
import es.icp.icp_commons.Objects.CommonsInputType;
import es.icp.icp_commons.Objects.Coordenada;
import es.icp.icp_commons.Objects.ImagenCommons;
import es.icp.icp_commons.Objects.ParametrosPeticion;
import es.icp.icp_commons.Objects.SmartButton;
import es.icp.icp_commons.Services.GeoTracking;
import es.icp.icp_commons.Services.WebService;
import es.icp.icp_commons.Utils.SpeedTest;
import es.icp.icp_commons.Utils.Utils;
import es.icp.icp_commons.Utils.UtilsKt;
import es.icp.logs.core.MyLog;
import es.icp.pruebas_commons.databinding.MainActivityBinding;
import es.icp.pruebas_commons.helpers.CommonsBaseApp;
import es.icp.pruebas_commons.helpers.GlobalVariables;
import es.icp.pruebas_commons.helpers.PruebasLoginRequest;
import es.icp.pruebas_commons.helpers.PruebasLoginResult;

public class MainActivity extends CommonsBaseApp {

    private Activity            context = MainActivity.this;
    private MainActivityBinding binding;
    private Handler             handler;
    private CustomSmartDialog   customSmartDialog;

    public static DialogConfig.UltraConfig ultraConfig = new DialogConfig.UltraConfig.Builder()
            .setMinHeight(0.3f)
            .build();

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

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

//        geoTrackingService();
    }

    private void geoTrackingService() {
        Init.geoTracking.setDistanceListener(10, new GeoTracking.DistanceListener() {
            @Override
            public void onDistanceUpdated(Coordenada nuevaCoordenada) {
                Toast.makeText(context, "Coordenadas = LAT: " + nuevaCoordenada.getLatitud() + " - LNG: " + nuevaCoordenada.getLongitud(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setEvents() {
        handler = new Handler() {
            @Override
            public void onClickBtn0(View view) {

                Intent intent = new Intent(context, KotlinActivity.class);
                startActivity(intent);
            }

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

            @Override
            public void onClickBtn26(View view) {
                pruebasGeocode1();
            }

            @Override
            public void onClickBtn27(View view) {
                pruebasGeocode2();
            }

            @Override
            public void onClickBtn28(View view) {
                mostrarCustomDialog1();
            }

            @Override
            public void onClickBtn29(View view) {
                pruebasGeocode3();
            }

            @Override
            public void onClickBtn30(View view) {
                crearDialog22();
            }

            @Override
            public void onClickBtn31(View view) {
                geoTracking();
            }

            @Override
            public void onClickBtn32(View view) {
                cantidades();
            }

            @Override
            public void onClickBtn33(View view) {
                progressActualizable();
            }

            @Override
            public void onClickBtn34(View view) {
                smartProgressActualizable();
            }

            @Override
            public void onClickBtn35(View view) {
                testVelocidad();
            }

            @Override
            public void onClickBtn36(View view) {
                LevantarCamara();
            }

            @Override
            public void onClickBtn37(View view) {
                LevantarCamaraVideo();
            }

            @Override
            public void onClickBtn38(View view) {
                crearDialogolistado();
            }

            @Override
            public void onClickBtn39(View view) {
                Intent intent = new Intent(context, LocationActivity.class);
                startActivity(intent);
            }
        };
    }

    private void crearDialogolistado() {




        ArrayList<String> subgrupos = new ArrayList<>(Arrays.asList(
                "ALT - Altamira",
                "APL - Apple",
                "BLA - Blackstone",
                "CER - Cerberus",
                "DIV - Divaran",
                "HAY - Haya",
                "MAC - Macc",
                "SAR - Sarebb",
                "SOL - Solvia"/*,
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia",
                "SOL - Solvia"*/
        ));

        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()
                        .setMinHeight(0.2f)
                        .build())
                .setTitulo("Subgrupos")
                .setMensaje("Seleccione el subgrupo del envío:")
                .setAutoDismiss(true)
                .setMostrarImagenPredeterminada(false)
                .setMostrarNegativo(true)
                .setTextoNegativo("Cancelar")
                .setMostrarPositivo(false)
                .setMostrarListado(true)
                .setListado(subgrupos)
                .build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, @Nullable AlertDialog dialog) {
                // el valor es el string seleccionado del listado
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(valor);
            }

            @Override
            public void negativo(String valor, @Nullable AlertDialog dialog) {
                // se ha pulsado el botón de cancelar selección
            }
        });
    }

    private void testVelocidad() {
        SpeedTest speedTest = SpeedTest.getInstance(context);
        speedTest.startTest(true);
    }

    private void progressActualizable() {
        Loading.ShowLoading(context);
        TimerTask timerTask = new TimerTask() {
            int seconds = 0;

            @Override
            public void run() {
                Loading.setMessage("Han pasado " + seconds + " segundos...");
                seconds++;
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 0, 1000);
    }

    private void smartProgressActualizable() {
        Loading.ShowSmartLoading(context, "Titulo", "Han pasado 0 segundos", true);
        TimerTask timerTask = new TimerTask() {
            int seconds = 0;

            @Override
            public void run() {
                Loading.setSmartMessage("Han pasado " + seconds + " segundos...");
                seconds++;
            }
        };
        new Timer().schedule(timerTask, 0, 1000);
    }

    private void cantidades() {
        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()
                        .setMinHeight(0.2f)
                        .build())
                .setTitulo("Cantidades")
                .setMensaje("Introduzca cantidad")
                .setAutoDismiss(true).setMostrarImagenPredeterminada(false)
                .setTextoPositivo("Aceptar")
                .setMostrarCantidad(true)
                .setCantidadInicial(5)
                .setCantidadMinima(1)
                .build();

        new CustomSmartDialog().dialogGenerico(context, config, null);
    }

    private void geoTracking() {
        Coordenada coordenada = Init.geoTracking.getCoordenadas();

        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()
                        .setMinHeight(0.2f)
                        .build())
                .setMostrarIconoTitulo(true)
                .setIconoTitulo(R.drawable.ic_launcher_round)
                .setTitulo("GeoTracking System")
                .setTiempo(3000)
                .showTemporizador(true)
                .setMensaje(coordenada != null ? coordenada.toString() : "Sin coordenadas")
                .setMostrarNegativo(false)
                .setMostrarPositivo(false)
                .setColorTitulo(android.R.color.holo_blue_light)
                .setMostrarImagenPredeterminada(false)
                .build();

        new CustomSmartDialog().dialogGenerico(context, config, null);
    }

    private void crearDialog22() {
        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()           // diálogo estética ULTA
                        .setMinHeight(0.1f)                                 // mínima altura de los diálogos ULTRA
                        .build())
                .setMostrarIconoTitulo(true)                                // mostrar icono en el titulo
                .setIconoTitulo(R.drawable.ic_launcher_round)               // icono del titulo
                .setTitulo("Temporizador")                                  // titulo
                .setTiempo(3000)                                            // tiempo para la automorision
                .showTemporizador(true)                                     // mostrar progressbar de automorision
                .setMensaje("Este diálogo se autodestruirá en 3 segundos")  // mensaje de automorision
                .setMostrarNegativo(false)                                  // sin boton negativo
                .setMostrarPositivo(false)                                  // sin boton postivo
                .setColorTitulo(android.R.color.holo_blue_light)            // color de fondo para la barra del titulo
                .setMostrarImagenPredeterminada(false)                      // sin ninguna imagen por defecto
                .build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Temporizador finalizado");
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {

            }
        });

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText("Temporizador finalizado");
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {

            }
        });

//        DialogConfig config2 = new DialogConfig.Builder()
//                .setMostrarIconoTitulo(true)                                // mostrar icono en el titulo
//                .setIconoTitulo(R.drawable.ic_launcher_round)               // icono del titulo
//                .setTitulo("Temporizador")                                  // titulo
////                .setTiempo(3000)                                            // tiempo para la automorision
////                .showTemporizador(true)                                     // mostrar progressbar de automorision
//                .setMensaje("Este diálogo se autodestruirá en 3 segundos")  // mensaje de automorision
//                .setMostrarNegativo(false)                                  // sin boton negativo
//                .setMostrarPositivo(false)                                  // sin boton postivo
//                .setColorTitulo(android.R.color.holo_blue_light)            // color de fondo para la barra del titulo
//                .setMostrarImagenPredeterminada(false)                      // sin ninguna imagen por defecto
//                .setCancelable(true)
//                .build();
//
//        new CustomSmartDialog().dialogGenerico(context, config2, null);
    }

    private void mostrarCustomDialog1() {
        CustomDialog.dialogSiNO(context, "Esto es un mensaje",
                R.color.colorPrimaryDesarrollo,
                ContextCompat.getDrawable(context, R.mipmap.ic_launcher),
                new ResponseDialog() {
                    @Override
                    public void si() {

                    }

                    @Override
                    public void no() {

                    }
                });

    }

    private void pruebasGeocode1() {
        CommonsGeocoder.getINSTANCE(context).obtenerDireccion(new GeocoderListener<String>() {
            @Override
            public void onDataObtained(String data) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(data);
            }
        });
    }

    private void pruebasGeocode2() {
        CommonsGeocoder.getINSTANCE(context).obtenerCoordenadas(new GeocoderListener<Coordenada>() {
            @Override
            public void onDataObtained(Coordenada data) {
                CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                customNotification.showText(data.toString());
            }
        });
    }

    private void pruebasGeocode3() {
        CommonsGeocoder.getINSTANCE(context).obtenerCoordenadas(new GeocoderListener<Coordenada>() {
            @Override
            public void onDataObtained(Coordenada coordenada) {
                CommonsGeocoder.getINSTANCE(context).obtenerDireccion(coordenada, new GeocoderListener<String>() {
                    @Override
                    public void onDataObtained(String data) {
                        CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
                        customNotification.showText(data.toString());
                    }
                });
            }
        });
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

        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()
                        .setMinHeight(0.5f)
                        .build())
                .setMostrarIconoTitulo(true)
                .setMostrarImagenPredeterminada(false)
                .setIconoTitulo(R.drawable.ic_launcher_round)
                .setTitulo("Imágenes incidencia")
                .setMensaje("Esto es un visor de imágenes de las incidencias.")
                .setMostrarNegativo(false)
                .setMostrarPositivo(true)
                .setAutoDismiss(true)
                .setTextoPositivo("ACEPTAR")
                .setMostrarVisorImagenes(true)
                .setImagenes(imagenes)
                .setAdjuntarImagenesListener(new AdjuntarImagenesListener() {
                    @Override
                    public void imagenAdjuntada(ImagenCommons imagen) {
                        imagen.getFormato();
                    }

                    @Override
                    public void imagenEliminada(int position, ImagenCommons imagen) {
                        imagen.getFormato();
                    }

                    @Override
                    public void aceptar(ArrayList<ImagenCommons> imagenes) {
                        imagenes.size();
                    }
                })
                .build();
        new CustomSmartDialog().dialogGenerico(context, config, null);
    }

    private void checkRequest2() {
        WebService.TratarExcepcion(context, "MetodoPrueba", 1234, "Prueba de la version_name", new Exception(), "", "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/save_log");
    }

    private void checkRequest1() {
        CustomNotification customNotification = new CustomNotification.Builder(context).setSimpleMode().setDuration(CustomNotification.LENGTH_SHORT).build();
        customNotification.showText(String.valueOf(Utils.isDebuggable(getApplicationContext())));

        WebService.setLoaderType(Loading.LoaderType.SMART_DIALOG);

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
            }, true, 0, "http://integracion.icp.es/WS_Orange_RFID_DES/api/orange.rfid/save_log", true);
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

        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()
                        .setMinHeight(0.5f)
                        .build())
                .isWithLoading(true)
                .setMostrarIconoTitulo(true)
                .setIconoTitulo(R.drawable.ic_launcher_round)
                .setTitulo("Elija una opción")
                .setMensaje("Elija una opción entre las tres opciones posibles que puedes ver...")
                .setMostrarNegativo(false)
                .setMostrarPositivo(false)
                .setAutoDismiss(true)
                .setMostrarBotones(true)
                .setBotones(botones)
                .setCancelable(true)
                .setMostrarImagenPredeterminada(false)
                .build();

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

        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA()
                .setMostrarIconoTitulo(true)
                .setIconoTitulo(R.drawable.ic_launcher_round)
                .setTextoEditText("Hola, texto por default :D")
                .setTitulo("Pruebas #4")
                .setInputType(new CommonsInputType.Builder().setInputType(InputType.TYPE_CLASS_NUMBER).build())
                .setMensaje("Mensaje de prueba 1número1 con un diálogo que acepta un editText y tres botones neutral (BARCODE), positivo y negativo.")
                .setMostrarNegativo(true)
                .setMostrarPositivo(true)
                .setColorTitulo(android.R.color.holo_red_light)
                .setAutoDismiss(false)
                .setTextoNegativo("CANCELAR")
                .setTextoPositivo("ACEPTAR")
                .setMostrarEditText(true)
                .setIconoEditText(R.drawable.ic_person_black_24dp)
                .setMaxLength(10)
                .setHint("Nombre y apellidos")
                .setMostrarBotones(true)
                .setBotones(botones).build();

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
        String error = "<b>No se pueden encontrar <b><big>65</big></b> destinos:</b> <br />   <br /> ■ AVENIDA DE LA CIUDAD DE BARCELONA 138 ESC- 2 7º  C MADRID, Madrid, 28007, ES<br/><br/> ■ CALLE GRANADA 42 PLANTA 4 C MADRID, MADRID, 28007, ES<br/><br/> ■ CL DOCTOR ESQUERDO 98 B Piso 003 MADRID, MADRID, 28007, ES<br/><br/> ■ CALLE SANCHEZ BARCAIZTEGUI 31 IZQUIERDA PLANTA 6 F MADRID, MADRID, 28007, ES<br/><br/> ■ C/ Granada, 62,1ºA Madrid, Madrid, 28007, ES<br/><br/> ■ CALLE JESUS APRENDIZ 10 PORTAL B 0 PLANTA 3 B MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7 D MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7 D MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7 D MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7D MADRID, MADRID, 28007, ES<br/><br/> ■ CALLE FOBOS 15 6º B MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE CORREGIDOR JUAN DE BOBADILLA 34 3º B MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE ENCOMIENDA DE PALACIOS 199 B Piso 001 MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE HACIENDA DE PAVONES 280  BAJO A MADRID, Madrid, 28030, ES<br/><br/> ■ CALLE ENCOMIENDA DE PALACIOS 161 PLANTA 7 B MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE PICO DE LOS ARTILLEROS 17 PLANTA 7 C MADRID, MADRID, 28030, ES<br/><br/> ■ CL ANTONIO CUMELLA 20 D Piso 006 MADRID, MADRID, 28030, ES<br/><br/> ■ CL ENCOMIENDA DE PALACIOS 77 A Bajo MADRID, MADRID, 28030, ES<br/><br/> ■ C\\ Hacienda de Pavones 63 PISO 4 PTA D Madrid, MADRID, 28030, ES<br/><br/> ■ C/ CORREGIDOR DIEGO DE VALDERRABANO 74 MADRID, MADRID, 28030, ES<br/><br/> ■ Cº DE LOS VINATEROS 77 01 D MADRID, MADRID, 28030, ES<br/><br/> ■ Cº DE LOS VINATEROS 77 01 D MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE MERIDA 2 PLANTA 3 D MADRID, MADRID, 28030, ES<br/><br/> ■ Cº DE LOS VINATEROS 77 01 D MADRID, MADRID, 28030, ES<br/><br/> ■ C/ DE LA HACIENDA DE PAVONES 49 02 C MADRID, MADRID, 28030, ES<br/><br/> ■ AVD. MORATALAZ 122 01 B MADRID, MADRID, 28030, ES<br/><br/> ■ AVD. MORATALAZ 21 4 B MADRID, MADRID, 28030, ES<br/><br/> ■ CL CAMINO DE LOS VINATEROS 113 C Piso 007 MADRID, MADRID, 28030, ES<br/><br/> ■ AVD. DEL DR GCIA. TAPIA 114 CO B MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE ARROYO FONTARRON 261 Bajo A MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE FELIX RODRIGUEZ DE LA FUENTE 27 BAJO DERECHA MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE CORREGIDOR MENDO DE ZUÑIGA, 8, 2-A MADRID, Madrid, 28030, ES<br/><br/> ■ AGUILAS 26,  2 A MADRID, Madrid, 28030, ES<br/><br/> ■ AVD. DEL DR GCIA. TAPIA 114 CO B MADRID, MADRID, 28030, ES<br/><br/> ■ CL LUIS DE HOYOS SAINZ 68 IZDA Piso 004 MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE FOBOS 9 PLANTA 9 C MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE ENTRE ARROYOS 50 PLANTA 3 A MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE CORREGIDOR DIEGO DE VALDERRABANO 74  2 E Madrid, Madrid, 28030, ES<br/><br/> ■ CL JOSÉ DEL PRADO Y PALACIO 5 LOCL5 Local MADRID, MADRID, 28030, ES<br/><br/> ■ CL ANTONIO CUMELLA 23 C Piso 008 MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE VINATEROS 89 PLANTA 3 C MADRID, MADRID, 28030, ES<br/><br/> ■ CAMINO VINATEROS 135 PLANTA 3 B MADRID, MADRID, 28030, ES<br/><br/> ■ CL HACIENDA DE PAVONES 219 C Piso 008 MADRID, MADRID, 28030, ES<br/><br/> ■ CALLE HACIENDA DE PAVONES 100 PLANTA 3 B MADRID, MADRID, 28030, ES<br/><br/> ■ Marroquina 72 - 6º B MADRID, Madrid, 28030, ES<br/><br/> ■ CALLE TENIENTE CORONEL NOREÑA 30 28045 MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE TEJO 22 BLOQUE 8 PLANTA 2 C MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE JOSE MIGUEL GORDOA 1  B1 MADRID, Madrid, 28045, ES<br/><br/> ■ CL ESTRELLA DENEBOLA 12 C Piso 007 MADRID, MADRID, 28045, ES<br/><br/> ■ CL PARROCO EUSEBIO CUENCA 46 C ESC ESC Piso 005 MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE FERNANDO POO 34 PLANTA 1 C MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE CANARIAS 84 PLANTA 2 B MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE ENRIQUE TROMPETA 8 PLANTA 1 B MADRID, MADRID, 28045, ES<br/><br/> ■ GLORIETA SANTA MARIA DE LA CABEZA 9 PLANTA 10 DERECHA MADRID, MADRID, 28045, ES<br/><br/> ■ Paseo SANTA MARÍA DE LA CABEZA 86 PISO 4 PTA A MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE COMANDANTE BENITEZ 4 11º IZQ MADRID, MADRID, 28045, ES<br/><br/> ■ PASEO DELICIAS 81 PLANTA 1 D MADRID, MADRID, 28045, ES<br/><br/> ■ CL EMBAJADORES 196  Bajo MADRID, MADRID, 28045, ES<br/><br/> ■ Pº DE LAS DELICIAS 139 1 C A MADRID, MADRID, 28045, ES<br/><br/> ■ PS DELICIAS 45 F Piso 001 MADRID, MADRID, 28045, ES<br/><br/> ■ CALLE COMANDANTE BENITEZ 4 11º IZQ MADRID, MADRID, 28045, ES<br/><br/> ■ TELLEZ 19 7 D MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7 D MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7 D MADRID, MADRID, 28007, ES<br/><br/> ■ TELLEZ 19 7D MADRID, MADRID, 28007, ES<br/><br/>";
        DialogConfig config = new DialogConfig.Builder().makeULTRA(ultraConfig).setMostrarIconoTitulo(true).setIconoTitulo(context.getDrawable(R.drawable.error_ex)).setTitulo("Error").
                setMensaje(error).setMostrarNegativo(false).setMostrarPositivo(true).setAutoDismiss(true).setColorTitulo(android.R.color.holo_red_light).setMostrarImagenPredeterminada(false).setTextoNegativo(context.getString(R.string.no)).setTextoPositivo(context.getString(R.string.aceptar)).setMostrarCantidad(false).build();

        new CustomSmartDialog().dialogGenerico(context, config, new CustomSmartDialogSiNoResponse() {
            @Override
            public void positivo(String valor, AlertDialog dialog) {

//                if (response != null) {
//                    response.siguiente(0, true);
//                }
            }

            @Override
            public void negativo(String valor, AlertDialog dialog) {

//                if (response != null) {
//                    response.siguiente(1, false);
//                }
            }
        });
    }

    private void crearDialog16() {
        DialogConfig config = new DialogConfig.Builder().makeULTRA().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Pruebas #2").setMensaje("<span style='color:red'>Mensaje de prueba 1número1 con un diálogo que acepta un editText y dos botones positivo yt negativo.</span>").setMostrarNegativo(true).setMostrarPositivo(true).setAutoDismiss(true).setTextoNegativo("CANCELAR").setTextoPositivo("ACEPTAR").setMostrarEditText(true).setIconoEditText(R.drawable.ic_person_black_24dp).setMaxLength(10).setHint("Nombre y apellidos").build();

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
        DialogConfig config = new DialogConfig.Builder().makeULTRA().setMostrarIconoTitulo(true).setIconoTitulo(R.drawable.ic_launcher_round).setTitulo("Pruebas #1").setMensaje("Mensaje de prueba 1número1 con un diálogo que acepta una imagen y un solo boton negativo.Mensaje de prueba 1número1 con un diálogo que acepta una imagen y un solo boton negativo.Mensaje de prueba 1número1 con un diálogo que acepta").setMostrarNegativo(true).setMostrarPositivo(false).setTextoNegativo("CANCELAR").setColorTitulo(android.R.color.holo_red_light).setMostrarImagen(true).setImagen(R.drawable.ic_thumb_up).build();

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

    private void LevantarCamara() {
        Intent intent = new Intent(this, Camara.class);
        intent.putExtra(Camara.CAMERA_BACK, true);
        startActivityForResult(intent, Constantes.INTENT_CAMARA);
    }

    private void LevantarCamaraVideo() {
        Intent intent = new Intent(this, Camara.class);
        intent.putExtra(Camara.VIDEO, true);
        intent.putExtra(Camara.GALLERY, true);
        startActivityForResult(intent, Constantes.INTENT_CAMARA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK){
            if (requestCode == Constantes.INTENT_CAMARA) {
                String photoFile = data.getStringExtra(Constantes.INTENT_CAMARAX);
                Toast.makeText(this, "PATH: " + photoFile, Toast.LENGTH_SHORT).show();
                Log.e("PHOTOFILE", photoFile);
            }
        }
    }

    public interface Handler {
        void onClickBtn0(View view);

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

        void onClickBtn26(View view);

        void onClickBtn27(View view);

        void onClickBtn28(View view);

        void onClickBtn29(View view);

        void onClickBtn30(View view);

        void onClickBtn31(View view);

        void onClickBtn32(View view);

        void onClickBtn33(View view);

        void onClickBtn34(View view);

        void onClickBtn35(View view);

        void onClickBtn36(View view);

        void onClickBtn37(View view);

        void onClickBtn38(View view);

        void onClickBtn39(View view);
    }
}