package es.icp.icp_commons;

import android.content.Context;
import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

import es.icp.icp_commons.Interfaces.AdjuntarImagenesListener;
import es.icp.icp_commons.Objects.CommonsInputType;
import es.icp.icp_commons.Objects.ImagenCommons;
import es.icp.icp_commons.Objects.SmartButton;

public class DialogConfig {

    private boolean                  mostrarEditText             = false;
    private Drawable                 iconoEditText               = null;
    private int                      iconoEditTextInt            = 0;
    private String                   textoEditText               = "";
    private String                   textoPositivo               = "SI";
    private String                   textoNegativo               = "NO";
    private int                      estiloPositivo              = 0; // TODO: 21/02/2020
    private int                      estiloNegativo              = 0; // TODO: 21/02/2020
    private boolean                  mostrarCantidad             = false;
    private boolean                  mostrarImagen               = false;
    private boolean                  mostrarBotones              = false;
    private boolean                  mostrarVisorImagenes        = false;
    private boolean                  mostrarLoading              = false;
    private ArrayList<ImagenCommons> imagenes                    = new ArrayList<>();
    private List<SmartButton>        botones                     = new ArrayList<>();
    private Drawable                 imagen                      = null;
    private int                      imagenInt                   = 0;
    private String                   titulo                      = "";
    private String                   mensaje                     = "";
    private Drawable                 iconoTitulo                 = null;
    private int                      iconoTituloInt              = 0;
    private boolean                  mostrarIconoTitulo          = false;
    private String                   hint                        = "";
    private int                      maxLength                   = 0;
    private int                      cantidadInicial             = 0;
    private boolean                  autoDismiss                 = false;
    private boolean                  mostrarPositivo             = true;
    private boolean                  mostrarNegativo             = true;
    private boolean                  mostrarImagenPredeterminada = true;
    private boolean                  cancelable                  = false;
    private int                      colorTitulo                 = 0;
    private AdjuntarImagenesListener adjuntarImagenesListener    = null;
    private boolean                  isIconoGif                  = false;
    private boolean                  withLoading                 = false;
    private int                      tiempo                      = 0;
    private boolean                  showTemporizador            = false;
    private boolean                  makeULTRA                   = false;
    private UltraConfig              ultraConfig;
    private CommonsInputType         inputType                   = new CommonsInputType();

    public UltraConfig getUltraConfig() {
        return ultraConfig;
    }

    public void setUltraConfig(UltraConfig ultraConfig) {
        this.ultraConfig = ultraConfig;
    }

    public CommonsInputType getInputType() {
        return inputType;
    }

    public void setInputType(CommonsInputType inputType) {
        this.inputType = inputType;
    }

    public boolean isMakeULTRA() {
        return makeULTRA;
    }

    public void setMakeULTRA() {
        this.makeULTRA = true;
    }

    public boolean isShowTemporizador() {
        return showTemporizador;
    }

    public void setShowTemporizador(boolean showTemporizador) {
        this.showTemporizador = showTemporizador;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public boolean isIconoGif() {
        return isIconoGif;
    }

    public boolean isWithLoading() {
        return withLoading;
    }

    public void setWithLoading(boolean withLoading) {
        this.withLoading = withLoading;
    }

    public void setTextoEditText(String textoEditText) {
        this.textoEditText = textoEditText;
    }

    public String getTextoEditText() {
        return textoEditText;
    }

    public void setIconoGif(boolean iconoGif) {
        isIconoGif = iconoGif;
    }

    public boolean isMostrarLoading() {
        return mostrarLoading;
    }

    public void setMostrarLoading(boolean mostrarLoading) {
        this.mostrarLoading = mostrarLoading;
    }

    public AdjuntarImagenesListener getAdjuntarImagenesListener() {
        return adjuntarImagenesListener;
    }

    public void setAdjuntarImagenesListener(AdjuntarImagenesListener adjuntarImagenesListener) {
        this.adjuntarImagenesListener = adjuntarImagenesListener;
    }

    public boolean isMostrarVisorImagenes() {
        return mostrarVisorImagenes;
    }

    public void setMostrarVisorImagenes(boolean mostrarVisorImagenes) {
        this.mostrarVisorImagenes = mostrarVisorImagenes;
    }

    public ArrayList<ImagenCommons> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<ImagenCommons> imagenes) {
        this.imagenes = imagenes;
    }

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
        private boolean                  mostrarEditText             = false;
        private Drawable                 iconoEditText               = null;
        private int                      iconoEditTextInt            = 0;
        private String                   textoEditText               = "";
        private String                   textoPositivo               = "SI";
        private String                   textoNegativo               = "NO";
        private int                      estiloPositivo              = 0;
        private int                      estiloNegativo              = 0;
        private boolean                  mostrarCantidad             = false;
        private boolean                  mostrarImagen               = false;
        private boolean                  mostrarBotones              = false;
        private boolean                  mostrarVisorImagenes        = false;
        private boolean                  mostrarLoading              = false;
        private List<SmartButton>        botones                     = new ArrayList<>();
        private Drawable                 imagen                      = null;
        private int                      imagenInt                   = 0;
        private String                   titulo                      = "";
        private String                   mensaje                     = "";
        private Drawable                 iconoTitulo                 = null;
        private int                      iconoTituloInt              = 0;
        private boolean                  mostrarIconoTitulo          = false;
        private String                   hint                        = "";
        private int                      maxLength                   = 0;
        private int                      cantidadInicial             = 0;
        private boolean                  autoDismiss                 = false;
        private boolean                  mostrarPositivo             = true;
        private boolean                  mostrarNegativo             = true;
        private boolean                  mostrarImagenPredeterminada = true;
        private boolean                  cancelable                  = false;
        private int                      colorTitulo                 = 0;
        private ArrayList<ImagenCommons> imagenes                    = new ArrayList<>();
        private AdjuntarImagenesListener adjuntarImagenesListener    = null;
        private boolean                  isIconoGif                  = false;
        private boolean                  withLoading                 = false;
        private int                      tiempo                      = 0;
        private boolean                  showTemporizador            = false;
        private boolean                  makeULTRA                   = false;
        private UltraConfig              ultraConfig;
        private CommonsInputType         inputType                   = new CommonsInputType();

        public Builder() {
        }

        public static Builder aDialogConfig() {
            return new Builder();
        }

        public Builder makeULTRA() {
            return makeULTRA(new UltraConfig.Builder().build());
        }

        public Builder makeULTRA(UltraConfig ultraConfig) {
            this.makeULTRA   = true;
            this.ultraConfig = ultraConfig;
            return this;
        }

        public Builder showTemporizador(boolean showTemporizador) {
            this.showTemporizador = showTemporizador;
            return this;
        }

        public Builder setTiempo(int tiempo) {
            this.tiempo = tiempo;
            return this;
        }

        public Builder setInputType(CommonsInputType inputType) {
            this.inputType = inputType;
            return this;
        }

        public Builder isWithLoading(boolean withLoading) {
            this.withLoading = withLoading;
            return this;
        }

        public Builder isIconoGif(boolean isIconoGif) {
            this.isIconoGif = isIconoGif;
            return this;
        }

        public Builder setTextoEditText(String textoEditText) {
            this.textoEditText = textoEditText;
            return this;
        }

        public Builder setMostrarLoading(boolean mostrarLoading) {
            this.mostrarLoading = mostrarLoading;
            return this;
        }

        public Builder setAdjuntarImagenesListener(AdjuntarImagenesListener adjuntarImagenesListener) {
            this.adjuntarImagenesListener = adjuntarImagenesListener;
            return this;
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

        public Builder setMostrarVisorImagenes(boolean mostrarVisorImagenes) {
            this.mostrarVisorImagenes = mostrarVisorImagenes;
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

        public Builder setImagenes(ArrayList<ImagenCommons> imagenes) {
            this.imagenes = imagenes;
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
            dialogConfig.mostrarVisorImagenes        = this.mostrarVisorImagenes;
            dialogConfig.mostrarLoading              = this.mostrarLoading;
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
            dialogConfig.imagenes                    = this.imagenes;
            dialogConfig.adjuntarImagenesListener    = this.adjuntarImagenesListener;
            dialogConfig.isIconoGif                  = this.isIconoGif;
            dialogConfig.withLoading                 = this.withLoading;
            dialogConfig.textoEditText               = this.textoEditText;
            dialogConfig.tiempo                      = this.tiempo;
            dialogConfig.showTemporizador            = this.showTemporizador;
            dialogConfig.makeULTRA                   = this.makeULTRA;
            dialogConfig.ultraConfig                 = this.ultraConfig;
            dialogConfig.inputType                   = this.inputType;
            return dialogConfig;
        }
    }

    public static final class UltraConfig {
        private float minHeight = 0f;

        public UltraConfig() {
        }

        public float getMinHeight() {
            return minHeight;
        }

        public void setMinHeight(float minHeight) {
            this.minHeight = minHeight;
        }

        public static final class Builder {
            private float minHeight = 0f;

            public Builder() {
            }

            public Builder setMinHeight(float minHeight) {
                this.minHeight = minHeight;
                return this;
            }

            public UltraConfig build() {
                UltraConfig ultraConfig = new UltraConfig();
                ultraConfig.minHeight = this.minHeight;
                return ultraConfig;
            }
        }
    }
}
