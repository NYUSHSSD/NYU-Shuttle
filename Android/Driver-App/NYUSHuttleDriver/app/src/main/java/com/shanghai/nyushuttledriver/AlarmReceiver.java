package com.shanghai.nyushuttledriver;

import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

/**
 * Created by Alexandru on 3/23/2015.
 */
public class AlarmReceiver extends WakefulBroadcastReceiver implements LocationListener {
    LocationManager locationManager;
    private String provider;
    public Context myContext;
    String selected_route;
    boolean executed = false;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.w("alex-log-alarm","Alarm started");
        myContext = context;
        selected_route = intent.getStringExtra("route");
        locationManager = (LocationManager) myContext.getSystemService(myContext.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, true);
        locationManager.requestLocationUpdates(provider, 5000, 1, this);

        Location location = locationManager.getLastKnownLocation(provider);
        Log.w("alex-location",location + "");
        if (location != null)
        {
            if (!executed) {
                executed = true;
                double lat = (double) (location.getLatitude());
                double lng = (double) (location.getLongitude());

                new UpdateLocation(myContext).execute(selected_route, String.valueOf(lat), String.valueOf(lng));
                Log.w("alex-log", "Executing |" + selected_route + "|" + lat + ", " + lng);
            }
        }

    }

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
}