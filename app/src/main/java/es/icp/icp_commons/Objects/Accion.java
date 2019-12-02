package es.icp.icp_commons.Objects;

public class Accion {

    private int ID;
    private String JSON;
    private String URL;
    private String Metodo;
    private String F_insert;
    private String RUTA_IMAGEN;

    public Accion(){

    }

    public Accion(int ID, String JSON, String URL, String metodo, String f_insert, String RUTA_IMAGEN) {
        this.JSON = JSON;
        this.URL = URL;
        Metodo = metodo;
        F_insert = f_insert;
        this.ID = ID;
        this.RUTA_IMAGEN = RUTA_IMAGEN;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getJSON() {
        return JSON;
    }

    public void setJSON(String JSON) {
        this.JSON = JSON;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getMetodo() {
        return Metodo;
    }

    public void setMetodo(String metodo) {
        Metodo = metodo;
    }

    public String getF_insert() {
        return F_insert;
    }

    public void setF_insert(String f_insert) {
        F_insert = f_insert;
    }

    public String getRUTA_IMAGEN() {
        return RUTA_IMAGEN;
    }

    public void setRUTA_IMAGEN(String RUTA_IMAGEN) {
        this.RUTA_IMAGEN = RUTA_IMAGEN;
    }
}
