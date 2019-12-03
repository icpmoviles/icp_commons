package es.icp.icp_commons;

import android.content.Context;

import java.util.Calendar;
import java.util.Date;

import es.icp.icp_commons.Database.DBHandler;
import es.icp.icp_commons.Objects.Accion;

public class AddAction {

    private static DBHandler dbHandler;

    public static void AddActionDatabase(String json, Context context, String url, String metodo, String ruta_imagen)
    {
        Date currentTime = Calendar.getInstance().getTime();
        Accion accion = new Accion(0, json,url, metodo, currentTime.toString(), ruta_imagen);
        if (dbHandler == null) dbHandler = new DBHandler(context);
        dbHandler.addAccion(accion);
    }

}
