package es.icp.pruebas_commons.helpers;

public class PruebasDefaultResult {

    private int    RETCODE;
    private String MENSAJE;

    public PruebasDefaultResult() {
        this.RETCODE = -1;
        this.MENSAJE = "SIN INICIALIZAR";
    }

    public PruebasDefaultResult(int RETCODE, String MENSAJE) {
        this.RETCODE = RETCODE;
        this.MENSAJE = MENSAJE;
    }

    public int getRETCODE() {
        return RETCODE;
    }

    public void setRETCODE(int RETCODE) {
        this.RETCODE = RETCODE;
    }

    public String getMENSAJE() {
        return MENSAJE;
    }

    public void setMENSAJE(String MENSAJE) {
        this.MENSAJE = MENSAJE;
    }
}
