package es.icp.pruebas_commons.helpers;

public class PruebasLoginResult extends PruebasDefaultResult {

    private int     ID_USUARIO;
    private String  SFID;
    private boolean RESETEAR_PASSWORD;
    private boolean IS_MASTER;
    private boolean SET_EMAIL;

    public PruebasLoginResult(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public PruebasLoginResult(int RETCODE, String MENSAJE, int ID_USUARIO, boolean IS_MASTER, boolean SET_EMAIL) {
        super(RETCODE, MENSAJE);
        this.ID_USUARIO = ID_USUARIO;
        this.IS_MASTER = IS_MASTER;
        this.SET_EMAIL = SET_EMAIL;
    }

    public int getID_USUARIO() {
        return ID_USUARIO;
    }

    public void setID_USUARIO(int ID_USUARIO) {
        this.ID_USUARIO = ID_USUARIO;
    }

    public String getSFID() {
        return SFID;
    }

    public void setSFID(String SFID) {
        this.SFID = SFID;
    }

    public boolean isRESETEAR_PASSWORD() {
        return RESETEAR_PASSWORD;
    }

    public void setRESETEAR_PASSWORD(boolean RESETEAR_PASSWORD) {
        this.RESETEAR_PASSWORD = RESETEAR_PASSWORD;
    }

    public boolean isIS_MASTER() {
        return IS_MASTER;
    }

    public void setIS_MASTER(boolean IS_MASTER) {
        this.IS_MASTER = IS_MASTER;
    }

    public boolean isSET_EMAIL() {
        return SET_EMAIL;
    }

    public void setSET_EMAIL(boolean SET_EMAIL) {
        this.SET_EMAIL = SET_EMAIL;
    }
}
