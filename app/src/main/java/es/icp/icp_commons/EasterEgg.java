package es.icp.icp_commons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import es.icp.icp_commons.Utils.UtilsFechas;

public class EasterEgg {

    private static Context context;

    private static       long    tiempoDown;
    private static final long    DURACION = 7000;
    private static       boolean activado = false;
    private static       int     contador = 0;

    public static void activar(Context ctx) {
//        context = ctx;
//
//        Intent intent = new Intent(context);
//        intent.getComponent().getCl;
//
//        Class mainActivityClass = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()).getClass();
//        try {
//            Method method = mainActivityClass.getMethod("getContext");
//            Object object = mainActivityClass.newInstance();
//            context = (Context) method.invoke(object);
//
//            View root = ((Activity) context).findViewById(android.R.id.content);
//
//            root.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                        if (activado) {
//                            contador++;
//                            if (contador == 7) {
//                                mostrarEasterEgg();
//                            }
//                        } else {
//                            tiempoDown = UtilsFechas.getNow();
//                        }
//                    } else if (event.getAction() == MotionEvent.ACTION_UP) {
//                        long tiempoUp = UtilsFechas.getNow();
//                        if (tiempoUp - tiempoDown > DURACION) {
//                            activado = true;
//                        }
//                    }
//                    return false;
//                }
//            });
//
//        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
//            e.printStackTrace();
//        }

    }

    private static void mostrarEasterEgg() {
        DialogConfig config = new DialogConfig.Builder()
                .makeULTRA(new DialogConfig.UltraConfig.Builder()
                        .setMinHeight(0.2f)
                        .build())
                .setTitulo("ICP Commons")
                .setTiempo(7000)
                .showTemporizador(true)
                .setMensaje("powered by ICP Commons 2.0")
                .setMostrarNegativo(false)
                .setMostrarPositivo(false)
                .setColorTitulo(android.R.color.holo_blue_light)
                .setMostrarImagenPredeterminada(false)
                .build();

        new CustomSmartDialog().dialogGenerico(context, config, null);
    }

}
