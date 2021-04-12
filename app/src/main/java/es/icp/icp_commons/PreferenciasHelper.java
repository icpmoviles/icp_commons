package es.icp.icp_commons;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.Method;
import java.util.Map;

@SuppressWarnings({"unused", "WeakerAccess"})
public class PreferenciasHelper {

    private static final String FILE_NAME = "APP_PREFERENCES";

    /**
     * Guarda en SharedPreferences el objeto introducido como parámetro junto con su respectiva clave
     *
     * @param context Context. Contexto de la aplicación.
     * @param key     String. Clave o nombre por el que se llamará o recuperará el valor guardado.
     * @param object  Object. Objeto a guardar en SharedPreferences.
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences        sp     = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        if (object instanceof String) {
            editor.putString(key, (String) object);
        }else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * Lee de SharedPreferences el objeto correspondiente a la clave introducida como parámetro
     *
     * @param context       Context. Contexto de la aplicación.
     * @param key           String. Clave o nombre por el que se llamará o recuperará un valor previamente guardado.
     * @param defaultObject Object. Objeto por defecto en caso de no existir dicha clave. El tipo del objecto tiene que ser igual al del valor a leer deseado.
     */
    public static Object get(Context context, String key, Object defaultObject) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        }else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        }

        return null;
    }

    /**
     * Elimina de SharedPreferences el objeto correspondiente a la clave introducida como parámetro
     *
     * @param context Context. Contexto de la aplicación.
     * @param key     String. Clave o nombre por el que se llama o recupera un valor previamente guardado.
     */
    public static void remove(Context context, String key) {
        SharedPreferences        sp     = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * Elimina todas las preferencias guardadas SharedPreferences
     *
     * @param context Context. Contexto de la aplicación.
     */
    public static void clear(Context context) {
        SharedPreferences        sp     = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * Pregunta si una SharedPreference existe.
     *
     * @param context Context. Contexto de la aplicación.
     * @param key     String. Clave o nombre por el que se llama o recupera un valor previamente guardado.
     * @return Devuelve un 'boolean'. ('true': existe; 'false': no existe)
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * Obtiene todas las SharedPreferences existentes.
     *
     * @param context Context. Contexto de la aplicación.
     * @return Devuelve un 'Map<String, ?> con todas las SharedPreferences existentes. (clave, objeto)
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            editor.commit();


        }
    }
}
