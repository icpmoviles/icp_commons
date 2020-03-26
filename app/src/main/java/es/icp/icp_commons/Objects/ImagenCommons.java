package es.icp.icp_commons.Objects;

import android.graphics.Bitmap;

public class ImagenCommons {

    private String                contenido;
    private Bitmap.CompressFormat formato;
    private int                   orientacion;
    private String                fecha;

    public ImagenCommons() {
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
