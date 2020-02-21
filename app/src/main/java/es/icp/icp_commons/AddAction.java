package es.icp.icp_commons;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import es.icp.icp_commons.Database.DBHandler;
import es.icp.icp_commons.Objects.Accion;

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
        Date      currentTime = Calendar.getInstance().getTime();
        Accion    accion      = new Accion(0, json, url, metodo, currentTime.toString(), ruta_imagen);
        DBHandler dbHandler   = new DBHandler(context);
        dbHandler.addAccion(accion);
    }

}
