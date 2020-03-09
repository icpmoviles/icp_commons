package es.icp.pruebas_commons.helpers;

public class PruebasLoginRequest {
    private String USERNAME;
    private String PASSWORD;
    private String TIPO;
    private String SO;
    private String MARCA;
    private String MODELO;
    private String RAM_DISPONIBLE;
    private String ESPACIO_DISPONIBLE;
    private String TOKEN_FIREBASE;
    private String VERSION;

    public PruebasLoginRequest() {
    }

    public PruebasLoginRequest(String USERNAME, String PASSWORD, String TIPO, String SO, String MARCA, String MODELO, String RAM_DISPONIBLE, String ESPACIO_DISPONIBLE, String TOKEN_FIREBASE, String VERSION) {
        this.USERNAME           = USERNAME;
        this.PASSWORD           = PASSWORD;
        this.TIPO               = TIPO;
        this.SO                 = SO;
        this.MARCA              = MARCA;
        this.MODELO             = MODELO;
        this.RAM_DISPONIBLE     = RAM_DISPONIBLE;
        this.ESPACIO_DISPONIBLE = ESPACIO_DISPONIBLE;
        this.TOKEN_FIREBASE     = TOKEN_FIREBASE;
        this.VERSION            = VERSION;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public void setUSERNAME(String USERNAME) {
        this.USERNAME = USERNAME;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public void setPASSWORD(String PASSWORD) {
        this.PASSWORD = PASSWORD;
    }

    public String getTIPO() {
        return TIPO;
    }

    public void setTIPO(String TIPO) {
        this.TIPO = TIPO;
    }

    public String getSO() {
        return SO;
    }

    public void setSO(String SO) {
        this.SO = SO;
    }

    public String getMARCA() {
        return MARCA;
    }

    public void setMARCA(String MARCA) {
        this.MARCA = MARCA;
    }

    public String getMODELO() {
        return MODELO;
    }

    public void setMODELO(String MODELO) {
        this.MODELO = MODELO;
    }

    public String getRAM_DISPONIBLE() {
        return RAM_DISPONIBLE;
    }

    public void setRAM_DISPONIBLE(String RAM_DISPONIBLE) {
        this.RAM_DISPONIBLE = RAM_DISPONIBLE;
    }

    public String getESPACIO_DISPONIBLE() {
        return ESPACIO_DISPONIBLE;
    }

    public void setESPACIO_DISPONIBLE(String ESPACIO_DISPONIBLE) {
        this.ESPACIO_DISPONIBLE = ESPACIO_DISPONIBLE;
    }

    public String getTOKEN_FIREBASE() {
        return TOKEN_FIREBASE;
    }

    public void setTOKEN_FIREBASE(String TOKEN_FIREBASE) {
        this.TOKEN_FIREBASE = TOKEN_FIREBASE;
    }

    public String getVERSION() {
        return VERSION;
    }

    public void setVERSION(String VERSION) {
        this.VERSION = VERSION;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "USERNAME='" + USERNAME + '\'' +
                ", PASSWORD='" + PASSWORD + '\'' +
                ", TIPO='" + TIPO + '\'' +
                ", SO='" + SO + '\'' +
                ", MARCA='" + MARCA + '\'' +
                ", MODELO='" + MODELO + '\'' +
                ", RAM_DISPONIBLE='" + RAM_DISPONIBLE + '\'' +
                ", ESPACIO_DISPONIBLE='" + ESPACIO_DISPONIBLE + '\'' +
                ", TOKEN_FIREBASE='" + TOKEN_FIREBASE + '\'' +
                ", VERSION='" + VERSION + '\'' +
                '}';
    }
}
