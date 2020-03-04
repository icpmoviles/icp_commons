package es.icp.icp_commons;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.net.ContentHandler;
import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Objects.SmartButton;

public class DialogConfig {

    private boolean           mostrarEditText             = false;
    private Drawable          iconoEditText               = null;
    private int               iconoEditTextInt            = 0;
    private String            textoPositivo               = "SI";
    private String            textoNegativo               = "NO";
    private int               estiloPositivo              = 0; // TODO: 21/02/2020
    private int               estiloNegativo              = 0; // TODO: 21/02/2020
    private boolean           mostrarCantidad             = false;
    private boolean           mostrarImagen               = false;
    private boolean           mostrarBotones              = false;
    private List<SmartButton> botones                     = new ArrayList<>();
    private Drawable          imagen                      = null;
    private int               imagenInt                   = 0;
    private String            titulo                      = "";
    private String            mensaje                     = "";
    private Drawable          iconoTitulo                 = null;
    private int               iconoTituloInt              = 0;
    private boolean           mostrarIconoTitulo          = false;
    private String            hint                        = "";
    private int               maxLength                   = 0;
    private int               cantidadInicial             = 0;
    private boolean           autoDismiss                 = false;
    private boolean           mostrarPositivo             = true;
    private boolean           mostrarNegativo             = true;
    private boolean           mostrarImagenPredeterminada = true;
    private boolean           cancelable                  = false;
    private int               colorTitulo                 = 0;

    public int getIconoEditTextInt() {
        return iconoEditTextInt;
    }

    private void setIconoEditTextInt(int iconoEditTextInt) {
        this.iconoEditTextInt = iconoEditTextInt;
    }

    public int getImagenInt() {
        return imagenInt;
    }

    private void setImagenInt(int imagenInt) {
        this.imagenInt = imagenInt;
    }

    public int getIconoTituloInt() {
        return iconoTituloInt;
    }

    private void setIconoTituloInt(int iconoTituloInt) {
        this.iconoTituloInt = iconoTituloInt;
    }

    public int getColorTitulo() {
        return colorTitulo;
    }

    private void setColorTitulo(int colorTitulo) {
        this.colorTitulo = colorTitulo;
    }

    public boolean isCancelable() {
        return cancelable;
    }

    private void setCancelable(boolean cancelable) {
        this.cancelable = cancelable;
    }

    public boolean isMostrarImagenPredeterminada() {
        return mostrarImagenPredeterminada;
    }

    private void setMostrarImagenPredeterminada(boolean mostrarImagenPredeterminada) {
        this.mostrarImagenPredeterminada = mostrarImagenPredeterminada;
    }

    public Drawable getIconoEditText() {
        return iconoEditText;
    }

    private void setIconoEditText(Drawable iconoEditText) {
        this.iconoEditText = iconoEditText;
    }

    public void setIconoEditText(Context context) {
        if (iconoEditTextInt != 0) iconoEditText = context.getDrawable(iconoEditTextInt);
    }

    public boolean isMostrarPositivo() {
        return mostrarPositivo;
    }

    private void setMostrarPositivo(boolean mostrarPositivo) {
        this.mostrarPositivo = mostrarPositivo;
    }

    public boolean isMostrarNegativo() {
        return mostrarNegativo;
    }

    private void setMostrarNegativo(boolean mostrarNegativo) {
        this.mostrarNegativo = mostrarNegativo;
    }

    public boolean isAutoDismiss() {
        return autoDismiss;
    }

    private void setAutoDismiss(boolean autoDismiss) {
        this.autoDismiss = autoDismiss;
    }

    public boolean isMostrarIconoTitulo() {
        return mostrarIconoTitulo;
    }

    private void setMostrarIconoTitulo(boolean mostrarIconoTitulo) {
        this.mostrarIconoTitulo = mostrarIconoTitulo;
    }

    public int getCantidadInicial() {
        return cantidadInicial;
    }

    private void setCantidadInicial(int cantidadInicial) {
        this.cantidadInicial = cantidadInicial;
    }

    public boolean isMostrarEditText() {
        return mostrarEditText;
    }

    public String getTextoPositivo() {
        return textoPositivo;
    }

    public String getTextoNegativo() {
        return textoNegativo;
    }

    public int getEstiloPositivo() {
        return estiloPositivo;
    }

    public int getEstiloNegativo() {
        return estiloNegativo;
    }

    public boolean isMostrarCantidad() {
        return mostrarCantidad;
    }

    public boolean isMostrarImagen() {
        return mostrarImagen;
    }

    public boolean isMostrarBotones() {
        return mostrarBotones;
    }

    public List<SmartButton> getBotones() {
        return botones;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public Drawable getIconoTitulo() {
        return iconoTitulo;
    }

    public String getHint() {
        return hint;
    }

    public int getMaxLength() {
        return maxLength;
    }

    private void setMostrarEditText(boolean mostrarEditText) {
        this.mostrarEditText = mostrarEditText;
    }

    private void setTextoPositivo(String textoPositivo) {
        this.textoPositivo = textoPositivo;
    }

    private void setTextoNegativo(String textoNegativo) {
        this.textoNegativo = textoNegativo;
    }

    private void setEstiloPositivo(int estiloPositivo) {
        this.estiloPositivo = estiloPositivo;
    }

    private void setEstiloNegativo(int estiloNegativo) {
        this.estiloNegativo = estiloNegativo;
    }

    private void setMostrarCantidad(boolean mostrarCantidad) {
        this.mostrarCantidad = mostrarCantidad;
    }

    private void setMostrarImagen(boolean mostrarImagen) {
        this.mostrarImagen = mostrarImagen;
    }

    private void setMostrarBotones(boolean mostrarBotones) {
        this.mostrarBotones = mostrarBotones;
    }

    private void setBotones(List<SmartButton> botones) {
        this.botones = botones;
    }

    private void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }

    public void setImagen(Context context) {
        if (imagenInt != 0) imagen = context.getDrawable(imagenInt);
    }

    private void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    private void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    private void setIconoTitulo(Drawable iconoTitulo) {
        this.iconoTitulo = iconoTitulo;
    }

    public void setIconoTitulo(Context context) {
        if (iconoTituloInt != 0) iconoTitulo = context.getDrawable(iconoTituloInt);
    }

    private void setHint(String hint) {
        this.hint = hint;
    }

    private void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public static final class Builder {
        private boolean           mostrarEditText             = false;
        private Drawable          iconoEditText               = null;
        private int               iconoEditTextInt            = 0;
        private String            textoPositivo               = "SI";
        private String            textoNegativo               = "NO";
        private int               estiloPositivo              = 0;
        private int               estiloNegativo              = 0;
        private boolean           mostrarCantidad             = false;
        private boolean           mostrarImagen               = false;
        private boolean           mostrarBotones              = false;
        private List<SmartButton> botones                     = new ArrayList<>();
        private Drawable          imagen                      = null;
        private int               imagenInt                   = 0;
        private String            titulo                      = "";
        private String            mensaje                     = "";
        private Drawable          iconoTitulo                 = null;
        private int               iconoTituloInt              = 0;
        private boolean           mostrarIconoTitulo          = false;
        private String            hint                        = "";
        private int               maxLength                   = 0;
        private int               cantidadInicial             = 0;
        private boolean           autoDismiss                 = false;
        private boolean           mostrarPositivo             = true;
        private boolean           mostrarNegativo             = true;
        private boolean           mostrarImagenPredeterminada = true;
        private boolean           cancelable                  = false;
        private int               colorTitulo                 = 0;

        public Builder() {
        }

        public static Builder aDialogConfig() {
            return new Builder();
        }

        public Builder setMostrarEditText(boolean mostrarEditText) {
            this.mostrarEditText = mostrarEditText;
            return this;
        }

        public Builder setIconoEditText(Drawable iconoEditText) {
            this.iconoEditText = iconoEditText;
            return this;
        }

        public Builder setIconoEditText(int iconoEditTextInt) {
            this.iconoEditTextInt = iconoEditTextInt;
            return this;
        }

        public Builder setTextoPositivo(String textoPositivo) {
            this.textoPositivo = textoPositivo;
            return this;
        }

        public Builder setTextoNegativo(String textoNegativo) {
            this.textoNegativo = textoNegativo;
            return this;
        }

        public Builder setEstiloPositivo(int estiloPositivo) {
            this.estiloPositivo = estiloPositivo;
            return this;
        }

        public Builder setEstiloNegativo(int estiloNegativo) {
            this.estiloNegativo = estiloNegativo;
            return this;
        }

        public Builder setMostrarCantidad(boolean mostrarCantidad) {
            this.mostrarCantidad = mostrarCantidad;
            return this;
        }

        public Builder setMostrarImagen(boolean mostrarImagen) {
            this.mostrarImagen = mostrarImagen;
            return this;
        }

        public Builder setMostrarBotones(boolean mostrarBotones) {
            this.mostrarBotones = mostrarBotones;
            return this;
        }

        public Builder setBotones(List<SmartButton> botones) {
            this.botones = botones;
            return this;
        }

        public Builder setImagen(Drawable imagen) {
            this.imagen = imagen;
            return this;
        }

        public Builder setImagen(int imagenInt) {
            this.imagenInt = imagenInt;
            return this;
        }

        public Builder setTitulo(String titulo) {
            this.titulo = titulo;
            return this;
        }

        public Builder setMensaje(String mensaje) {
            this.mensaje = mensaje;
            return this;
        }

        public Builder setIconoTitulo(Drawable iconoTitulo) {
            this.iconoTitulo = iconoTitulo;
            return this;
        }

        public Builder setIconoTitulo(int iconoTituloInt) {
            this.iconoTituloInt = iconoTituloInt;
            return this;
        }

        public Builder setMostrarIconoTitulo(boolean mostrarIconoTitulo) {
            this.mostrarIconoTitulo = mostrarIconoTitulo;
            return this;
        }

        public Builder setHint(String hint) {
            this.hint = hint;
            return this;
        }

        public Builder setMaxLength(int maxLength) {
            this.maxLength = maxLength;
            return this;
        }

        public Builder setCantidadInicial(int cantidadInicial) {
            this.cantidadInicial = cantidadInicial;
            return this;
        }

        public Builder setAutoDismiss(boolean autoDismiss) {
            this.autoDismiss = autoDismiss;
            return this;
        }

        public Builder setMostrarPositivo(boolean mostrarPositivo) {
            this.mostrarPositivo = mostrarPositivo;
            return this;
        }

        public Builder setMostrarNegativo(boolean mostrarNegativo) {
            this.mostrarNegativo = mostrarNegativo;
            return this;
        }

        public Builder setMostrarImagenPredeterminada(boolean mostrarImagenPredeterminada) {
            this.mostrarImagenPredeterminada = mostrarImagenPredeterminada;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

        public Builder setColorTitulo(int colorTitulo) {
            this.colorTitulo = colorTitulo;
            return this;
        }

        public DialogConfig build() {
            DialogConfig dialogConfig = new DialogConfig();
            dialogConfig.mostrarEditText             = this.mostrarEditText;
            dialogConfig.iconoEditText               = this.iconoEditText;
            dialogConfig.iconoEditTextInt            = this.iconoEditTextInt;
            dialogConfig.textoPositivo               = this.textoPositivo;
            dialogConfig.estiloNegativo              = this.estiloNegativo;
            dialogConfig.mostrarCantidad             = this.mostrarCantidad;
            dialogConfig.textoNegativo               = this.textoNegativo;
            dialogConfig.mostrarNegativo             = this.mostrarNegativo;
            dialogConfig.mensaje                     = this.mensaje;
            dialogConfig.iconoTitulo                 = this.iconoTitulo;
            dialogConfig.iconoTituloInt              = this.iconoTituloInt;
            dialogConfig.mostrarImagen               = this.mostrarImagen;
            dialogConfig.mostrarBotones              = this.mostrarBotones;
            dialogConfig.autoDismiss                 = this.autoDismiss;
            dialogConfig.hint                        = this.hint;
            dialogConfig.estiloPositivo              = this.estiloPositivo;
            dialogConfig.botones                     = this.botones;
            dialogConfig.maxLength                   = this.maxLength;
            dialogConfig.imagen                      = this.imagen;
            dialogConfig.imagenInt                   = this.imagenInt;
            dialogConfig.mostrarPositivo             = this.mostrarPositivo;
            dialogConfig.cantidadInicial             = this.cantidadInicial;
            dialogConfig.mostrarIconoTitulo          = this.mostrarIconoTitulo;
            dialogConfig.titulo                      = this.titulo;
            dialogConfig.mostrarImagenPredeterminada = this.mostrarImagenPredeterminada;
            dialogConfig.cancelable                  = this.cancelable;
            dialogConfig.colorTitulo                 = this.colorTitulo;
            return dialogConfig;
        }
    }
}
