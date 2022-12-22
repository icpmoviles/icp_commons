package es.icp.icp_commons.Extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

/**
 * @author Carlos del Campo Cebrian
 * @version 1.0
 *
 */

/**
 * Si se pasa null el formato que por default es
 * pattern: dd/MM/yyyy
 */
fun getHoy(formato: String?) : String {

    return try {
        if (formato != null) SimpleDateFormat(formato, Locale.getDefault()).format(Date())
        else SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date())

    } catch (e: Exception){
        e.printStackTrace()
        ""
    }
}

/**
 * DEVUELVE UN LOCALDATETIME CON POSIBILIDAD DE FORMATO PERSONALIDAZO
 * pattern default: dd/MM/yyyy HH:mm
 */
@RequiresApi(Build.VERSION_CODES.O)
fun toLocalDateTime(fechaEnTexto: String, formato: String?): LocalDateTime {
    var elFormato =
        if (formato != null) DateTimeFormatter.ofPattern(formato)
        else DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
    return LocalDateTime.parse(fechaEnTexto, elFormato)
}

fun nowToMilisLong(): Long {
    return System.currentTimeMillis()
}

fun convertFecha(fromFormat: String?, toFormat: String?, dateToFormat: String): String {

    val formatoEntrada =
        if (fromFormat != null) SimpleDateFormat(fromFormat)
        else SimpleDateFormat("yyyy-MM-dd'T'hh:mm")

    val formatoSalida =
        if (toFormat != null) SimpleDateFormat(toFormat)
        else SimpleDateFormat("dd/MM/yyyy HH:mm")

    try {
        val date = formatoEntrada.parse(dateToFormat)
        if (date != null) return formatoSalida.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}


@RequiresApi(Build.VERSION_CODES.O)
fun daysBetween(fechaInicio: String, fechaFin: String?, formatIN: String?): String {
    val pattern = formatIN ?: "dd/MM/yyyy"
    val inDate = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern(pattern))
    val endDate =
        if (fechaFin != null) LocalDate.parse((fechaFin), DateTimeFormatter.ofPattern(pattern))
        else LocalDate.now()
    return ChronoUnit.DAYS.between(inDate, endDate).toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun monthsBetween(fechaInicio: String, fechaFin: String?, formatIN: String?): String {
    val pattern = formatIN ?: "dd/MM/yyyy"
    val inDate = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern(pattern))
    val endDate =
        if (fechaFin != null) LocalDate.parse((fechaFin), DateTimeFormatter.ofPattern(pattern))
        else LocalDate.now()
    return ChronoUnit.MONTHS.between(inDate, endDate).toString()
}

@RequiresApi(Build.VERSION_CODES.O)
fun yearsBetween(fechaInicio: String, fechaFin: String?, formatIN: String?): String {
    val pattern = formatIN ?: "dd/MM/yyyy"

    val inDate = LocalDate.parse(fechaInicio, DateTimeFormatter.ofPattern(pattern))
    val endDate =
        if (fechaFin != null) LocalDate.parse((fechaFin), DateTimeFormatter.ofPattern(pattern))
        else LocalDate.now()
    return ChronoUnit.YEARS.between(inDate, endDate).toString()
}


fun UTCtoTimeZone (fecha:String) : String{
    val offset = TimeZone.getDefault().rawOffset
    val formato = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val dateAux = formato.parse(fecha)
    val utcTime = dateAux.time

    return formato.format(utcTime + offset)
}

/**
 * Pattern: yyyy-MM-dd HH:mm:ss
 */
fun Date.formatToServerDateTimeDefaults(): String{
    val sdf= SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

fun Date.formatToTruncatedDateTime(): String{
    val sdf= SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: yyyy-MM-dd
 */
fun Date.formatToServerDateDefaults(): String{
    val sdf= SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToServerTimeDefaults(): String{
    val sdf= SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy HH:mm:ss
 */
fun Date.formatToViewDateTimeDefaults(): String{
    val sdf= SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: dd/MM/yyyy
 */
fun Date.formatToViewDateDefaults(): String{
    val sdf= SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Pattern: HH:mm:ss
 */
fun Date.formatToViewTimeDefaults(): String{
    val sdf= SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    return sdf.format(this)
}

/**
 * Add field date to current date
 */
fun Date.add(field: Int, amount: Int): Date {
    Calendar.getInstance().apply {
        time = this@add
        add(field, amount)
        return time
    }
}

fun Date.addYears(years: Int): Date{
    return add(Calendar.YEAR, years)
}
fun Date.addMonths(months: Int): Date {
    return add(Calendar.MONTH, months)
}
fun Date.addDays(days: Int): Date{
    return add(Calendar.DAY_OF_MONTH, days)
}
fun Date.addHours(hours: Int): Date{
    return add(Calendar.HOUR_OF_DAY, hours)
}
fun Date.addMinutes(minutes: Int): Date{
    return add(Calendar.MINUTE, minutes)
}
fun Date.addSeconds(seconds: Int): Date{
    return add(Calendar.SECOND, seconds)
}