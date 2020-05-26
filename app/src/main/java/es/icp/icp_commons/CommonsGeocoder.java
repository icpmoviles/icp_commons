package es.icp.icp_commons;

import android.annotation.SuppressLint;
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

import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.icp.icp_commons.Enums.GeocoderMetodo;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Helpers.MyApplication;
import es.icp.icp_commons.Interfaces.GeocoderListener;
import es.icp.icp_commons.Objects.Coordenada;
import es.icp.icp_commons.Utils.Utils;

@SuppressLint("MissingPermission")
public class CommonsGeocoder {

    private static CommonsGeocoder INSTANCE;

    private Context         context;
    private LocationManager lm;
    private Location        bestLocation = null;
    private List<String> providers;
    private static Integer contadorProviders = 0;

    public static CommonsGeocoder getINSTANCE(Context context) {
        if (INSTANCE == null) INSTANCE = new CommonsGeocoder(context);
        return INSTANCE;
    }

    public CommonsGeocoder(Context context) {
        this.context = context;
    }

    public void obtener(GeocoderMetodo metodo, GeocoderListener listener) {
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

    private void getLastKnownLocation(GeocoderMetodo metodo, GeocoderListener listener) {
        lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        providers = lm.getProviders(true);
        for (String provider : providers) {
            lm.requestLocationUpdates(provider, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
//                    comprobarMejorUbicacion(metodo, location.getProvider(), listener);
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
            comprobarMejorUbicacion(metodo, provider, listener);
        }

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (bestLocation != null) {
                    MyApplication.runOnUiThread(() -> geocoderAddress(metodo, listener));
                    timer.cancel();
                } else {
                    timer.schedule(this, 500);
                }
            }
        };
        timer.schedule(timerTask, 500);
    }

    private synchronized void comprobarMejorUbicacion(GeocoderMetodo metodo, String provider, GeocoderListener listener) {
        Location l = lm.getLastKnownLocation(provider);
        if (l == null) {
            return;
        }
        if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
            bestLocation = l;
        }
        synchronized (contadorProviders) {
            contadorProviders++;
            if (contadorProviders == providers.size()) {
                contadorProviders = 0;

                if (bestLocation != null) {

                    geocoderAddress(metodo, listener);

                } else {
                    if (metodo == GeocoderMetodo.DIRECCION) {
                        if (listener != null) {
                            listener.onDataObtained(context.getString(R.string.geocoder_no_pudo_obtener_ubicacion));
                            listener = null;
                        }
                    }
                    else {
                        if (listener != null) {
                            listener.onDataObtained(new Coordenada());
                            listener = null;
                        }
                    }
                    Log.d("DEBUG_ICP", "127");
                }
            }
        }
    }

    private synchronized void geocoderAddress(GeocoderMetodo metodo, GeocoderListener listener) {
        double longitude = bestLocation.getLongitude();
        double latitude  = bestLocation.getLatitude();

        synchronized (listener) {
            if (metodo == GeocoderMetodo.COORDENADAS) {
                if (listener != null) {
                    listener.onDataObtained(new Coordenada(longitude, latitude));
                    listener = null;
                }
                Log.d("DEBUG_ICP", "138");
                return;
            }

            Geocoder geocoder = new Geocoder(context);
            try {
                List<Address> direcciones = geocoder.getFromLocation(latitude, longitude, 1);
                if (listener != null) {
                    listener.onDataObtained(direcciones.get(0).getAddressLine(0));
                    listener = null;
                }
                Log.d("DEBUG_ICP", "146");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        lm.removeUpdates(new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        });
    }

    private void toggleGPS() {
        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
    }

    private boolean isGPSOn() {
        String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
        return provider.contains("gps");
    }
}
