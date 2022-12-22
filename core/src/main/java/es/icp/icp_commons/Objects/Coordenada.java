package es.icp.icp_commons.Objects;

import es.icp.logs.core.MyLog;

public class Coordenada {

    private double longitud;
    private double latitud;

    public Coordenada() {
    }

    public Coordenada(double longitud, double latitud) {
        this.longitud = longitud;
        this.latitud  = latitud;
    }

    public double calcularDistanciaDesde(Coordenada comparar) {
        double radioTierra = 6371000; //en metros
        double dLat        = Math.toRadians(comparar.getLatitud() - this.latitud);
        double dLng        = Math.toRadians(comparar.getLongitud() - this.longitud);
        double sindLat     = Math.sin(dLat / 2);
        double sindLng     = Math.sin(dLng / 2);
        double va1 = Math.pow(sindLat, 2) + Math.pow(sindLng, 2) * Math.cos(Math.toRadians(this.latitud)) * Math.cos(Math.toRadians(comparar.getLatitud()));
        double va2 = 2 * Math.atan2(Math.sqrt(va1), Math.sqrt(1 - va1));
        double result = radioTierra * va2;
        return result;

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
