package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.icp.icp_commons.Enums.GeocoderMetodo;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Interfaces.GeocoderListener;
import es.icp.icp_commons.Objects.Coordenada;
import es.icp.icp_commons.Utils.Utils;

@SuppressLint("MissingPermission")
public class CommonsGeocoder {

    private static CommonsGeocoder INSTANCE;

    private        Context          context;
    private        LocationManager  lm;
    private        Location         bestLocation      = null;
    private        List<String>     providers;
    private static Integer          contadorProviders = 0;
    private        Timer            timer;
    private        TimerTask        timerTask;
    private        boolean          disparadoEvento   = false;
    private        GeocoderMetodo   metodo;
    private        GeocoderListener listener;

    public static CommonsGeocoder getINSTANCE(Context context) {
        if (INSTANCE == null) INSTANCE = new CommonsGeocoder(context);
        return INSTANCE;
    }

    private CommonsGeocoder(Context context) {
        this.context = context;
        lm           = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private void obtener(GeocoderMetodo metodo, GeocoderListener listener) {
        this.metodo     = metodo;
        this.listener   = listener;
        disparadoEvento = false;
        if (Utils.comprobarPermisos(context, Constantes.PERMISOS_LOCALIZACION)) {
            if (isGPSOn()) {
                getLastKnownLocation(metodo, listener);
            } else {
                toggleGPS();
            }
        }
    }

    public void obtenerDireccion(GeocoderListener<String> listener) {
        obtener(GeocoderMetodo.DIRECCION, listener);
    }

    public void obtenerCoordenadas(GeocoderListener<Coordenada> listener) {
        obtener(GeocoderMetodo.COORDENADAS, listener);
    }

    public void obtenerDireccion(Coordenada coordenada, GeocoderListener<String> listener) {
        geocoderAddress(GeocoderMetodo.DIRECCION, coordenada, listener);
    }

    private void getLastKnownLocation(GeocoderMetodo metodo, GeocoderListener listener) {
        FusedLocationProviderClient fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(((Activity) context), new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        // Got last known location. In some rare situations this can be null.
                        procesarLocalizacion(metodo, location, listener);
                    }
                });
    }

    private void procesarLocalizacion(GeocoderMetodo metodo, Location location, GeocoderListener listener) {
        if (location != null) {
            geocoderAddress(metodo, location, listener);
        } else {
            switch (metodo) {
                case DIRECCION:
                    listener.onDataObtained(context.getString(R.string.geocoder_no_pudo_obtener_ubicacion));
                    break;
                case COORDENADAS:
                    listener.onDataObtained(new Coordenada());
                    break;
            }
        }
    }

    private void geocoderAddress(GeocoderMetodo metodo, Coordenada coordenada, GeocoderListener listener) {
        if (metodo == GeocoderMetodo.COORDENADAS) {
            listener.onDataObtained(coordenada);
            return;
        }

        Geocoder geocoder = new Geocoder(context);
        try {
            List<Address> direcciones = geocoder.getFromLocation(coordenada.getLatitud(), coordenada.getLongitud(), 1);
            listener.onDataObtained(direcciones.get(0).getAddressLine(0));
            Log.d("DEBUG_ICP", "146");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void geocoderAddress(GeocoderMetodo metodo, Location location, GeocoderListener listener) {
        double longitude = location.getLongitude();
        double latitude  = location.getLatitude();

        geocoderAddress(metodo, new Coordenada(longitude, latitude), listener);
    }

    private void reprocesarMetodo() {
        switch (metodo) {
            case DIRECCION:
                obtenerDireccion(listener);
                break;
            case COORDENADAS:
                obtenerCoordenadas(listener);
                break;
        }
    }

    private void toggleGPS() {
        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        lm.requestLocationUpdates("gps", 0, 0, new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            @Override
            public void onProviderEnabled(String provider) {
                reprocesarMetodo();
            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private boolean isGPSOn() {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return provider.contains("gps");
    }
}
