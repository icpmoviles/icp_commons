package es.icp.icp_commons.Objects;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import es.icp.icp_commons.R;
import es.icp.icp_commons.Utils.CommonsUtilsImagenes;
import es.icp.icp_commons.Utils.Localizacion;
import es.icp.icp_commons.Utils.UtilsFechas;

public class ImagenCommons {

    private String                contenido;
    private Bitmap.CompressFormat formato;
    private int                   orientacion;
    private String                fecha;

    public ImagenCommons(Drawable drawable) {
        this(CommonsUtilsImagenes.fromDrawableToBase64Image(drawable), Bitmap.CompressFormat.JPEG, 0, UtilsFechas.getHoy(Localizacion.getInstance().formatoFechas));
    }

    public ImagenCommons(Drawable drawable, Bitmap.CompressFormat formato, int orientacion) {
        this(CommonsUtilsImagenes.fromDrawableToBase64Image(drawable), formato, orientacion, UtilsFechas.getHoy(Localizacion.getInstance().formatoFechas));
    }

    public ImagenCommons(String contenido, Bitmap.CompressFormat formato, int orientacion) {
        this(contenido, formato, orientacion, UtilsFechas.getHoy(Localizacion.getInstance().formatoFechas));
    }

    public ImagenCommons(String contenido, Bitmap.CompressFormat formato, int orientacion, String fecha) {
        this.contenido   = contenido;
        this.formato     = formato;
        this.orientacion = orientacion;
        this.fecha       = fecha;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Bitmap.CompressFormat getFormato() {
        return formato;
    }

    public void setFormato(Bitmap.CompressFormat formato) {
        this.formato = formato;
    }

    public int getOrientacion() {
        return orientacion;
    }

    public void setOrientacion(int orientacion) {
        this.orientacion = orientacion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "ImagenCommons{" + "contenido='" + contenido + '\'' + ", formato=" + formato + ", orientacion=" + orientacion + ", fecha='" + fecha + '\'' + '}';
    }
}
