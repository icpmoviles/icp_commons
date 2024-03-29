package es.icp.icp_commons;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;

import es.icp.icp_commons.Enums.GeocoderMetodo;
import es.icp.icp_commons.Helpers.Constantes;
import es.icp.icp_commons.Interfaces.GeocoderListener;
import es.icp.icp_commons.Objects.Coordenada;
import es.icp.icp_commons.Services.WSHelper;
import es.icp.icp_commons.Utils.Utils;

@SuppressLint("MissingPermission")
public class CommonsGeocoder {

    private static CommonsGeocoder INSTANCE;

    private Context          context;
    private LocationManager  lm;
    private GeocoderMetodo   metodo;
    private GeocoderListener listener;
    private int              numUpdates = PUNTUAL;
    private static FusedLocationProviderClient fusedClient;

    public static final int CONTINUO = 0;
    public static final int PUNTUAL = 1;

    private long minInterval = 5000;
    private long maxInterval = 10000;

    public static CommonsGeocoder getINSTANCE(Context context) {
        if (INSTANCE == null) INSTANCE = new CommonsGeocoder(context);
        if (fusedClient == null) fusedClient = LocationServices.getFusedLocationProviderClient(context);
        return INSTANCE;
    }

    private CommonsGeocoder(Context context) {
        this.context = context;
        lm           = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    public int getNumUpdates() {
        return numUpdates;
    }

    public void makeContinuo() {
        this.numUpdates = CONTINUO;
    }

    public void makePuntual() {
        this.numUpdates = PUNTUAL;
    }

    public void setMaxInterval(long maxInterval) {
        this.maxInterval = maxInterval;
    }

    public void setMinInterval(long minInterval) {
        this.minInterval = minInterval;
    }

    private void obtener(GeocoderMetodo metodo, GeocoderListener listener) {
        this.metodo   = metodo;
        this.listener = listener;
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
        if (fusedClient != null) {
            fusedClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    procesarLocalizacion(metodo, location, listener);
                }
            });
        }

        LocationRequest mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationCallback mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {

                if (locationResult == null) {
//                    WSHelper.logWS("Obteniendo la localización... LOCATION RESULT NULL");
                    return;
                }
                Location location = locationResult.getLastLocation();
                if (location != null) procesarLocalizacion(metodo, locationResult.getLastLocation(), listener);
//                else WSHelper.logWS("Obteniendo la localización... LOCATION NULL");

            }
        };
        if (numUpdates == PUNTUAL) mLocationRequest.setNumUpdates(1);
        else if (numUpdates == CONTINUO) {
            mLocationRequest.setInterval(maxInterval);
            mLocationRequest.setFastestInterval(minInterval);
        }
        LocationServices.getFusedLocationProviderClient(context).requestLocationUpdates(mLocationRequest, mLocationCallback, null);
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
//            Log.d("DEBUG_ICP", "146");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void geocoderAddress(GeocoderMetodo metodo, Location location, GeocoderListener listener) {
        double longitude = location.getLongitude();
        double latitude  = location.getLatitude();

//        WSHelper.logWS("Obteniendo la localización... LATITUD: " + latitude + " - LONGITUD: " + longitude);

        geocoderAddress(metodo, new Coordenada(longitude, latitude), listener);
    }

    private void reprocesarMetodo() {
        if (metodo != null) {
            switch (metodo) {
                case DIRECCION:
                    obtenerDireccion(listener);
                    break;
                case COORDENADAS:
                    obtenerCoordenadas(listener);
                    break;
            }
        }
    }

    public void toggleGPS() {
        context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
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

    public boolean isGPSOn() {
//        if (Build.VERSION.SDK_INT >= 31) {
            return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        } else {
//            String provider = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
//            return provider.contains("gps");
//        }
    }
}
