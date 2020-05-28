package es.icp.icp_commons.Utils;

import java.util.Locale;

public class Localizacion {

    public String pais          = "";
    public String lenguaje      = "";
    public String formatoFechas = "";

    private Localizacion() {
    }

    public static Localizacion getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final Localizacion INSTANCE = new Localizacion();
    }

    public void init() {

        pais     = Locale.getDefault().getCountry();
        lenguaje = Locale.getDefault().getLanguage();

        switch (pais) {
            case "ES": //España:
                formatoFechas = "dd-MM-yyyy";
                break;
            default:
                formatoFechas = "yyyy-MM-dd";
                break;
        }
    }

    @Override
    public String toString() {
        return "Localizacion{" + "lenguaje='" + lenguaje + '\'' + ", formatoFechas='" + formatoFechas + '\'' + '}';
    }
}
