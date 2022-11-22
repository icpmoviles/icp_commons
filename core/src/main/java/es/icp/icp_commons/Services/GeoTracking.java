package es.icp.icp_commons.Services;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import es.icp.icp_commons.CommonsGeocoder;
import es.icp.icp_commons.Interfaces.GeocoderListener;
import es.icp.icp_commons.Objects.Coordenada;

public class GeoTracking {

    private Context context;

    private static GeoTracking INSTANCE;

    @Nullable
    private Coordenada          coordenadas;
    private CommonsGeocoder     geocoder;
    private Coordenada          ultimaCoordenadaListener;
    private DistanceListener    distanceListener;
    private DataUpdatedListener dataUpdatedListener;
    private double              distance;

    private GeoTracking(Context context) {
        this.context = context;
    }

    public static GeoTracking getInstance(Context context) {
        return INSTANCE != null ? INSTANCE : new GeoTracking(context);
    }

    public void startGeoTracking() {
        startGeoTracking(0, 0);
    }

    public void startGeoTracking(long minInterval, long maxInterval) {
        if (geocoder == null) geocoder = CommonsGeocoder.getINSTANCE(context);
        geocoder.makeContinuo();
        if (minInterval > 0) geocoder.setMinInterval(minInterval);
        if (maxInterval > 0) geocoder.setMaxInterval(maxInterval);
        geocoder.obtenerCoordenadas(new GeocoderListener<Coordenada>() {
            @Override
            public void onDataObtained(Coordenada data) {
                coordenadas = data;
                if (distanceListener != null && distance > 0) {
                    if (ultimaCoordenadaListener == null) {
                        ultimaCoordenadaListener = coordenadas;
                        distanceListener.onDistanceUpdated(ultimaCoordenadaListener);
                    } else {
                        if (ultimaCoordenadaListener.calcularDistanciaDesde(coordenadas) >= distance) {
                            ultimaCoordenadaListener = coordenadas;
                            distanceListener.onDistanceUpdated(ultimaCoordenadaListener);
                        }
                    }
                }
                if (dataUpdatedListener != null) dataUpdatedListener.onDataUpdated(coordenadas);
            }
        });
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Nullable
    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    public void setDistanceListener(DistanceListener distanceListener) {
        this.distanceListener = distanceListener;
    }

    public void setDistanceListener(double distance, DistanceListener distanceListener) {
        this.distance = distance;
        this.distanceListener = distanceListener;
    }

    public void setDataUpdatedListener(DataUpdatedListener dataUpdatedListener) {
        this.dataUpdatedListener = dataUpdatedListener;
    }

    @Override
    public String toString() {
        return "GeoTracking{" +
                "coordenadas=" + coordenadas +
                '}';
    }

    public interface DistanceListener {
        void onDistanceUpdated(Coordenada nuevaCoordenada);
    }

    public interface DataUpdatedListener {
        void onDataUpdated(Coordenada nuevaCoordenada);
    }

    public static class Builder {
        private Context context;
        private DistanceListener    distanceListener;
        private DataUpdatedListener dataUpdatedListener;
        private double              distance;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder setDistanceListener(double distance, DistanceListener distanceListener) {
            this.distance = distance;
            this.distanceListener = distanceListener;
            return this;
        }

        public Builder setDataUpdatedListener(DataUpdatedListener dataUpdatedListener) {
            this.dataUpdatedListener = dataUpdatedListener;
            return this;
        }

        public GeoTracking build() {
            GeoTracking geoTracking = getInstance(context);
            geoTracking.setDistance(distance);
            geoTracking.setDataUpdatedListener(dataUpdatedListener);
            geoTracking.setDistanceListener(distanceListener);
            return geoTracking;
        }
    }
}
