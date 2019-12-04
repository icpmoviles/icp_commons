package es.icp.icp_commons.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Objects.Accion;

public class DBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ICP_Commons";

    private static final String CREATE_TABLE_ACCIONES = "CREATE TABLE ACCIONES (ID INTEGER PRIMARY KEY," +
            "JSON TEXT," +
            "URL TEXT," +
            "METODO TEXT," +
            "F_INSERT TEXT," +
            "RUTA_IMAGEN TEXT)";

    private static final String DROP_TABLE_ACCIONES = "DROP TABLE IF EXISTS ACCIONES";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_ACCIONES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (newVersion > oldVersion){
            db.execSQL(DROP_TABLE_ACCIONES);

            onCreate(db);
        }
    }

    public void addAccion(Accion accion)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("JSON", accion.getJSON());
        values.put("URL", accion.getURL());
        values.put("METODO", accion.getMetodo());
        values.put("F_INSERT", accion.getF_insert());
        values.put("RUTA_IMAGEN", accion.getRUTA_IMAGEN());

        db.insert("ACCIONES", null, values);
    }

    public List<Accion> getAcciones()
    {
        List<Accion> acciones = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ACCIONES ORDER BY ID ASC", null);
        if (cursor.moveToFirst())
        {
            do{
                Accion accion = new Accion(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
                acciones.add(accion);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return acciones;
    }

    public void removeAccion(int id)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM ACCIONES WHERE ID = " + id);
    }
}
