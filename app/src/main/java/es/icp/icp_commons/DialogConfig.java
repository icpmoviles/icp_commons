package es.icp.icp_commons;

import android.graphics.drawable.Drawable;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class DialogConfig {

    private boolean      mostrarEditText     = false;
    private String       textoPositivo       = "SI";
    private String       textoNegativo       = "NO";
    private int          estiloPositivo      = 0;
    private int          estiloNegativo      = 0;
    private boolean      mostrarBotonNeutral = false;
    private String       textoNeutral        = "";
    private int          estiloNeutral       = 0;
    private boolean      mostrarCantidad     = false;
    private boolean      mostrarImagen       = false;
    private boolean      mostrarBotones      = false;
    private List<Button> botones             = new ArrayList<>();
    private Drawable     imagen              = null;
    private String       titulo              = "";
    private String       mensaje             = "";
    private Drawable     iconoTitulo         = null;
    private String       hint                = "";
    private int          maxLength           = 0;
    private int          cantidadInicial     = 0;

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

    public boolean isMostrarBotonNeutral() {
        return mostrarBotonNeutral;
    }

    public String getTextoNeutral() {
        return textoNeutral;
    }

    public int getEstiloNeutral() {
        return estiloNeutral;
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

    public List<Button> getBotones() {
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

    private void setMostrarBotonNeutral(boolean mostrarBotonNeutral) {
        this.mostrarBotonNeutral = mostrarBotonNeutral;
    }

    private void setTextoNeutral(String textoNeutral) {
        this.textoNeutral = textoNeutral;
    }

    private void setEstiloNeutral(int estiloNeutral) {
        this.estiloNeutral = estiloNeutral;
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

    private void setBotones(List<Button> botones) {
        this.botones = botones;
    }

    private void setImagen(Drawable imagen) {
        this.imagen = imagen;
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

    private void setHint(String hint) {
        this.hint = hint;
    }

    private void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }


    public static final class Builder {
        private boolean      mostrarEditText     = false;
        private String       textoPositivo       = "SI";
        private String       textoNegativo       = "NO";
        private int          estiloPositivo      = 0;
        private int          estiloNegativo      = 0;
        private boolean      mostrarBotonNeutral = false;
        private String       textoNeutral        = "";
        private int          estiloNeutral       = 0;
        private boolean      mostrarCantidad     = false;
        private boolean      mostrarImagen       = false;
        private boolean      mostrarBotones      = false;
        private List<Button> botones             = new ArrayList<>();
        private Drawable     imagen              = null;
        private String       titulo              = "";
        private String       mensaje             = "";
        private Drawable     iconoTitulo         = null;
        private String       hint                = "";
        private int          maxLength           = 0;
        private int          cantidadInicial     = 0;

        public Builder() {
        }

        public static Builder aDialogConfig() {
            return new Builder();
        }

        public Builder setMostrarEditText(boolean mostrarEditText) {
            this.mostrarEditText = mostrarEditText;
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

        public Builder setMostrarBotonNeutral(boolean mostrarBotonNeutral) {
            this.mostrarBotonNeutral = mostrarBotonNeutral;
            return this;
        }

        public Builder setTextoNeutral(String textoNeutral) {
            this.textoNeutral = textoNeutral;
            return this;
        }

        public Builder setEstiloNeutral(int estiloNeutral) {
            this.estiloNeutral = estiloNeutral;
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

        public Builder setBotones(List<Button> botones) {
            this.botones = botones;
            return this;
        }

        public Builder setImagen(Drawable imagen) {
            this.imagen = imagen;
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

        public DialogConfig build() {
            DialogConfig dialogConfig = new DialogConfig();
            dialogConfig.mostrarEditText     = this.mostrarEditText;
            dialogConfig.textoPositivo       = this.textoPositivo;
            dialogConfig.estiloNegativo      = this.estiloNegativo;
            dialogConfig.mostrarCantidad     = this.mostrarCantidad;
            dialogConfig.textoNegativo       = this.textoNegativo;
            dialogConfig.mensaje             = this.mensaje;
            dialogConfig.iconoTitulo         = this.iconoTitulo;
            dialogConfig.mostrarImagen       = this.mostrarImagen;
            dialogConfig.mostrarBotones      = this.mostrarBotones;
            dialogConfig.hint                = this.hint;
            dialogConfig.estiloPositivo      = this.estiloPositivo;
            dialogConfig.botones             = this.botones;
            dialogConfig.maxLength           = this.maxLength;
            dialogConfig.imagen              = this.imagen;
            dialogConfig.estiloNeutral       = this.estiloNeutral;
            dialogConfig.mostrarBotonNeutral = this.mostrarBotonNeutral;
            dialogConfig.cantidadInicial     = this.cantidadInicial;
            dialogConfig.textoNeutral        = this.textoNeutral;
            dialogConfig.titulo              = this.titulo;
            return dialogConfig;
        }
    }
}
