package es.icp.icp_commons.Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UtilsFechas {


    public static String convertFecha(String fromFormat, String toFormat, String dateToFormat) {
        SimpleDateFormat inFormat = new SimpleDateFormat(fromFormat);
        Date             date     = null;
        try {
            date = inFormat.parse(dateToFormat);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat outFormat = new SimpleDateFormat(toFormat);
        return outFormat.format(date);
    }


    public static String getHoy(String formato) {
        String currentDate = new SimpleDateFormat(formato, Locale.getDefault()).format(new Date());
        return currentDate;
    }


}
