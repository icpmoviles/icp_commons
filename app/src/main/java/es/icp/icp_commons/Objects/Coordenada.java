package es.icp.icp_commons.Objects;

public class Coordenada {
    
    private double longitud;
    private double latitud;

    public Coordenada() {
    }

    public Coordenada(double longitud, double latitud) {
        this.longitud = longitud;
        this.latitud  = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "Coordenada{" +
                "longitud=" + longitud +
                ", latitud=" + latitud +
                '}';
    }
}
