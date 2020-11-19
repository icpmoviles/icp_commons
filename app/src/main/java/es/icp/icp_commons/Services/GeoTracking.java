package es.icp.icp_commons.Services;

import android.content.Context;

import androidx.annotation.Nullable;

import es.icp.icp_commons.CommonsGeocoder;
import es.icp.icp_commons.Interfaces.GeocoderListener;
import es.icp.icp_commons.Objects.Coordenada;

public class GeoTracking {

    private Context context;

    private static GeoTracking INSTANCE;

    @Nullable
    private Coordenada coordenadas;
    private CommonsGeocoder geocoder;

    private GeoTracking(Context context) {
        this.context = context;
    }

    public static GeoTracking getInstance(Context context) {
        return INSTANCE != null ? INSTANCE : new GeoTracking(context);
    }

    public void startGeoTracking() {
        if (geocoder == null) geocoder = CommonsGeocoder.getINSTANCE(context);
        geocoder.makeContinuo();
        geocoder.obtenerCoordenadas(new GeocoderListener<Coordenada>() {
            @Override
            public void onDataObtained(Coordenada data) {
                coordenadas = data;
            }
        });
    }

    @Nullable
    public Coordenada getCoordenadas() {
        return coordenadas;
    }

    @Override
    public String toString() {
        return "GeoTracking{" +
                "coordenadas=" + coordenadas +
                '}';
    }
}
