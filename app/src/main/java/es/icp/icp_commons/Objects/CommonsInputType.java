package es.icp.icp_commons.Objects;

import android.text.InputType;

/**
 * Clase auxiliar para poder instanciar objetos a partir de la interfaz android.text.InpuType
 */
public class CommonsInputType implements InputType {

    private int inputType;

    public CommonsInputType() {
        this.inputType = InputType.TYPE_CLASS_TEXT;
    }

    public int getInputType() {
        return inputType;
    }

    public void setInputType(int inputType) {
        this.inputType = inputType;
    }

    @Override
    public String toString() {
        return "CommonsInputType{" +
                "inputType=" + inputType +
                '}';
    }

    public static class Builder {

        private int              inputType = InputType.TYPE_CLASS_TEXT;
        private CommonsInputType commonsInputType;

        public Builder() {

        }

        public Builder setInputType(int inputType) {
            this.inputType = inputType;
            return this;
        }

        public CommonsInputType build() {
            CommonsInputType commonsInputType = new CommonsInputType();
            commonsInputType.inputType = inputType;
            return commonsInputType;
        }

    }
}
