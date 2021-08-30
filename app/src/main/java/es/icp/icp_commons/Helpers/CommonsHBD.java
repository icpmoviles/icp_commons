package es.icp.icp_commons.Helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteCantOpenDatabaseException;
import android.database.sqlite.SQLiteDatabase;

import es.icp.logs.core.MyLog;

public class CommonsHBD {
    private static int    LONGITUD_COLUMNA = 100;

    private static String rellenaCaracteres(String t, int l, String c) {
        t = (t.length() > LONGITUD_COLUMNA) ? t.substring(0, LONGITUD_COLUMNA) : t;

        for (int i = t.length(); i < l; i++) {
            t = t.concat(c);
        }
        return t;
    }

    public static void muestraTabla(Context context, String tabla) {

        muestraTabla(context, tabla, LONGITUD_COLUMNA);
    }

    public static void muestraTabla(Context context, String tabla, int longitud) {
        LONGITUD_COLUMNA = longitud;
        MyLog.c("TABLA: " + tabla);
        crearEstrucuraLogcat(context, "Select * from " + tabla);
    }

    public static void muestraSQL(Context context, String sql) {
        muestraSQL(context, sql, LONGITUD_COLUMNA);
    }

    public static void muestraSQL(Context context, String sql, int longitud) {
        LONGITUD_COLUMNA = longitud;
        MyLog.c("SQL: " + sql);
        crearEstrucuraLogcat(context, sql);
    }

    private static void crearEstrucuraLogcat(Context context, String sql) {
        try {
            String         separadorColumna = " | ";
            SQLiteDatabase db               = SQLiteDatabase.openDatabase("data/data/" + context.getPackageName() + "/databases/ICP_Commons", null, 0);
            Cursor         c                = db.rawQuery(sql, null);

            MyLog.d("");

            String columnas = rellenaCaracteres("#", 3, " ") + "| ";
            for (String columna : c.getColumnNames()) {
                columnas = columnas + rellenaCaracteres(columna, LONGITUD_COLUMNA, " ") + separadorColumna;
            }
            MyLog.v(rellenaCaracteres("", LONGITUD_COLUMNA * (c.getColumnCount() + 1), "-"));
            MyLog.v(columnas);
            MyLog.v(rellenaCaracteres("", LONGITUD_COLUMNA * (c.getColumnCount() + 1), "-"));

            c.moveToFirst();

            if (c.getCount() == 0) {
                MyLog.d("Tabla vacía");
                MyLog.d(rellenaCaracteres("", LONGITUD_COLUMNA * (c.getColumnCount() + 1), "="));
                return;
            }

            int contador = 0;
            do {
                String fila = "";
                contador++;
                fila = rellenaCaracteres(String.valueOf(contador), 3, " ") + "| ";
                //for (int i = 0; i < c.getColumnCount(); i++) {
                for (String columna : c.getColumnNames()) {

                    int indiceColumna = c.getColumnIndex(columna);
                    switch (c.getType(c.getColumnIndex(columna))) {
                        case Cursor.FIELD_TYPE_NULL:
                            break;
                        case Cursor.FIELD_TYPE_INTEGER:
                            int valorInt = c.getInt(indiceColumna);
                            fila = fila + rellenaCaracteres((String.valueOf(valorInt)), LONGITUD_COLUMNA, " ") + separadorColumna;

                            break;
                        case Cursor.FIELD_TYPE_FLOAT:
                            float valorFloat = c.getFloat(indiceColumna);
                            fila = fila + rellenaCaracteres((String.valueOf(valorFloat)), LONGITUD_COLUMNA, " ") + separadorColumna;

                            break;
                        case Cursor.FIELD_TYPE_STRING:
                            String valorString = c.getString(indiceColumna);
                            fila = fila + rellenaCaracteres((valorString), LONGITUD_COLUMNA, " ") + separadorColumna;
                            break;
                    }
                }
                MyLog.d(fila);
            } while (c.moveToNext());

            MyLog.i("- " + c.getCount() + " filas encontradas -");
            MyLog.v(rellenaCaracteres("", LONGITUD_COLUMNA * (c.getColumnCount() + 1), "="));
            MyLog.d("");
        } catch (SQLiteCantOpenDatabaseException ex) {
            ex.printStackTrace();
        }
    }
}
