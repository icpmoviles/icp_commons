package es.icp.icp_commons;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import es.icp.icp_commons.Database.DBHandler;
import es.icp.icp_commons.Objects.Accion;
import es.icp.logs.core.MyLog;

@SuppressWarnings("unused")
public class AddAction {

    /**
     * @param json        JSON que se va a guardar a la espera de un CheckRequest.Check() / CheckRequest.CheckAndSend()
     * @param context     Contexto de la aplicación
     * @param url         URL HTTP a enviar la petición
     * @param metodo      Método POST/GET. Literal "POST" / "GET"
     * @param ruta_imagen String, en caso de no adjuntar imagen, pasar NULL
     * @author Ventura de Lucas
     */
    public static void AddActionDatabase(String json, Context context, String url, String metodo, String ruta_imagen) {
        AddActionDatabase(json, context, url, metodo, ruta_imagen, 0);
    }

    /**
     * @param json        JSON que se va a guardar a la espera de un CheckRequest.Check() / CheckRequest.CheckAndSend()
     * @param context     Contexto de la aplicación
     * @param url         URL HTTP a enviar la petición
     * @param metodo      Método POST/GET. Literal "POST" / "GET"
     * @param ruta_imagen String, en caso de no adjuntar imagen, pasar NULL
     * @param errorCode int, Código de error por el cual se está guardando la petición. En caso de no ser un error, es 0 (offline).
     * @author Ventura de Lucas
     */
    public static void AddActionDatabase(String json, Context context, String url, String metodo, String ruta_imagen, int errorCode) {
        Date currentTime = Calendar.getInstance().getTime();
        Accion accion = new Accion(0, json, url, metodo, currentTime.toString(), ruta_imagen);
        DBHandler dbHandler = new DBHandler(context);
        dbHandler.addAccion(accion);

        MyLog.setDEBUG(true);
//        CommonsHBD.muestraTabla(context, "ACCIONES");

        MyLog.d(accion.toString());
//        MyLog.d("Guardado desde :" + MyLog.getTrace(5));
    }

    /**
     * Borra todas las acciones.
     * @author Ventura de Lucas
     */
    public static void removeAllActions(Context context) {
        DBHandler dbHandler = new DBHandler(context);
        dbHandler.removeAllActions();
    }
}
