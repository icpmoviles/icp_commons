package es.icp.icp_commons.Objects;

@SuppressWarnings("unused")
public class Accion {

    private int ID;
    private String JSON;
    private String URL;
    private String Metodo;
    private String F_insert;
    private String RUTA_IMAGEN;

    /**
     * Constructor Accion sin parámetros
     *
     */
    public Accion(){

    }

    /**
     * Constructor Accion de 6 parámetros
     *
     * @param ID int. Id de la acción.
     * @param JSON String. JSON de la petición a guardar.
     * @param URL String. Diracción HTTP a la que se enviará la petición.
     * @param metodo String. Método HTTP, literal: "POST" / "GET".
     * @param f_insert String. Fecha de inserción en local.
     * @param RUTA_IMAGEN String. Imagen adjunta a la petición. Indicar NULL si no se adjunta imagen.
     */
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
