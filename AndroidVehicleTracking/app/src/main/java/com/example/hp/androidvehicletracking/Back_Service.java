package com.example.hp.androidvehicletracking;

import android.Manifest;
import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by hp on 8/30/2016.
 */

public class Back_Service extends Service {

    String did = "", date, time;
    SimpleDateFormat d, t;
    Date dt;
    SharedPreferences sp;
    private Context mContext;

    // Flag for GPS status
    boolean isGPSEnabled = false;

    // Flag for network status
    boolean isNetworkEnabled = false;

    // Flag for GPS status
    boolean canGetLocation = false;

    Location location; // Location
    double latitude = 0.0; // Latitude
    double longitude = 0.0; // Longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1000; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;

    Timer timer;
    TimerTask timerTask;
    Handler handler = new Handler();

    Timer timer1;
    TimerTask timerTask1;
    Handler handler1 = new Handler();

    List<Double> latlist;
    List<Double> lnglist;
    List<String> seconds;

    SimpleDateFormat sdf = new SimpleDateFormat("ss.SS");
    boolean CheckValue = false;
    int SpeedLimit = 0;
    String Uid = "", LatLngValue = "";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        d = new SimpleDateFormat("yyyy-MM-dd");
        t = new SimpleDateFormat("HH:mm");
        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        did = sp.getString("did", "");
        Uid = sp.getString("uid", "");
        String sValue = sp.getString("speedLimit", "");
        SpeedLimit = Integer.parseInt(sValue);

        GPSTrack gps = new GPSTrack(getApplicationContext());
        gps.getLocation();

        timer = new Timer();
        initializeTimerTask();
        timer.schedule(timerTask, 0, 7000);

        latlist = new ArrayList<Double>();
        lnglist = new ArrayList<Double>();
        seconds = new ArrayList<String>();

        timer1 = new Timer();
        gettim();
        timer1.schedule(timerTask1, 0, 1000);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        timer1.cancel();
    }

    public class GPSTrack {


        Activity activity;

        public GPSTrack() {

        }

        public GPSTrack(Context context) {
            mContext = context;
            getLocation();
        }


        public Location getLocation() {
            try {


                locationManager = (LocationManager) mContext.getSystemService(mContext.LOCATION_SERVICE);

                // Getting GPS status
                isGPSEnabled = locationManager
                        .isProviderEnabled(LocationManager.GPS_PROVIDER);

                // Getting network status
                isNetworkEnabled = locationManager
                        .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

                if (!isGPSEnabled && !isNetworkEnabled) {
                    // No network provider is enabled
                } else {
                    canGetLocation = true;
                    if (isNetworkEnabled) {
                        int requestPermissionsCode = 50;

                        locationManager.requestLocationUpdates(
                                LocationManager.NETWORK_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                        Log.d("Network", "Network");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }
                // If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                            ActivityCompat.requestPermissions(mContext, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 50);

                        } else {
                            locationManager.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES, mLocationListener);
                            Log.d("GPS Enabled", "GPS Enabled");
                            if (locationManager != null) {

                                location = locationManager
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                                if (location != null) {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return location;
        }


        private final LocationListener mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(final Location location) {

                if (location != null) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();

                    //Toast.makeText(activity, ""+latitude+","+longitude, Toast.LENGTH_SHORT).show();
                }
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
        };


        public boolean canGetLocation() {
            return canGetLocation;
        }


        public double getLatitude() {
            if (location != null) {
                latitude = location.getLatitude();
            }

            return latitude;
        }


        public double getLongitude() {
            if (location != null) {
                longitude = location.getLongitude();
            }

            return longitude;
        }

    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        dt = Calendar.getInstance().getTime();
                        date = d.format(dt);
                        time = t.format(dt);

                        try {
                            GPSTrack gps_tracker = new GPSTrack(getApplicationContext());
                            if (gps_tracker.canGetLocation()) {
                                double Lat = gps_tracker.getLatitude();
                                double Lng = gps_tracker.getLongitude();

                                if (Lat != 0 && Lng != 0) {
                                    new latlng().execute(did, "" + latitude, "" + longitude, date, time);
                                }
                            } else {

//                                Toast.makeText(Back_Service.this, "Enable GPS", Toast.LENGTH_SHORT).show();

                            }
                        } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                        }


                    }
                });
            }
        };
    }

    public void gettim() {

        timerTask1 = new TimerTask() {
            public void run() {
                handler1.post(new Runnable() {
                    public void run() {

                        Boolean ans = weHavePermissionforGPS();
                        if (ans) {

                            try {
                                GPSTrack gps_tracker = new GPSTrack(getApplicationContext());
                                if (gps_tracker.canGetLocation()) {
                                    double Lat = gps_tracker.getLatitude();
                                    double Lng = gps_tracker.getLongitude();

                                    if (Lat != 0 && Lng != 0) {
                                        LatLngValue = Lat + "," + Lng;
                                        marking(new LatLng(latitude, longitude));
                                    }
                                } else {

//                                Toast.makeText(Back_Service.this, "Enable GPS", Toast.LENGTH_SHORT).show();

                                }
                            } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                            }


                        } else {

                            Toast.makeText(Back_Service.this, "Location Permission not given", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        };
    }


    public void marking(LatLng latLng) {

        latlist.add(latLng.latitude);
        lnglist.add(latLng.longitude);
        Date d = new Date();
        String s = sdf.format(d);
        seconds.add(s);


        if (seconds.size() > 1) {

            Location l1 = new Location("");
            l1.setLatitude(latlist.get(latlist.size() - 2));
            l1.setLongitude(lnglist.get(lnglist.size() - 2));

            Location l2 = new Location("");
            l2.setLatitude(latlist.get(latlist.size() - 1));
            l2.setLongitude(lnglist.get(lnglist.size() - 1));

            LatLng ll1 = new LatLng(l1.getLatitude(), l1.getLongitude());
            LatLng ll2 = new LatLng(l2.getLatitude(), l2.getLongitude());


            Float tdist = l1.distanceTo(l2);
            Float t1 = Float.parseFloat(seconds.get(seconds.size() - 2));
            Float t2 = Float.parseFloat(seconds.get(seconds.size() - 1));
            Float time = null;

            if (t1 > t2) {
                time = t1 - t2;
            } else {
                time = t2 - t1;
            }

//            disttxt.setText("Dist : " + tdist);
//            timetxt.setText("Time : " + time);
        }


        if (seconds.size() > 5) {
            calculate();
        } else {
            if (seconds.size() > 1) {
                Location l1 = new Location("");
                l1.setLatitude(latlist.get(latlist.size() - 2));
                l1.setLongitude(lnglist.get(lnglist.size() - 2));

                Location l2 = new Location("");
                l2.setLatitude(latlist.get(latlist.size() - 1));
                l2.setLongitude(lnglist.get(lnglist.size() - 1));

                Float tdist = l1.distanceTo(l2);
                Float t1 = Float.parseFloat(seconds.get(seconds.size() - 2));
                Float t2 = Float.parseFloat(seconds.get(seconds.size() - 1));
                Float time = null;

                if (t1 > t2) {
                    time = t1 - t2;
                } else {
                    time = t2 - t1;
                }

//                disttxt.setText("Dist : " + tdist);
//                timetxt.setText("Time : " + time);

                tdist = tdist * (Float.parseFloat("0.001"));
                time = time * (Float.parseFloat("0.000277778"));
                Float speed = tdist / time;

                Intent intent = new Intent("SpeedLimit");
                intent.putExtra("speed", speed);
                LocalBroadcastManager.getInstance(Back_Service.this).sendBroadcast(intent);

                int SpeedValue = Math.round(speed);
                CallAPIFuntion(SpeedValue);
//                speedtxt.setText(speed + " Km/Hr");
//                newspeed.setText(gps.getspeed() + " Km/Hr");


            }
        }
    }

    public void calculate() {
        float distance = 0, time = 0;
        int start = latlist.size() - 5;
        final Location l1 = new Location("");

        for (int i = start; i < latlist.size(); i++) {
            l1.setLatitude(latlist.get(i));
            l1.setLongitude(lnglist.get(i));
            Float t1 = Float.parseFloat(seconds.get(i));

            if ((i + 1) < latlist.size()) {
                Location l2 = new Location("");
                l2.setLatitude(latlist.get(i + 1));
                l2.setLongitude(lnglist.get(i + 1));
                Float tdist = l1.distanceTo(l2);
                if (tdist > 2) {
                    distance += tdist;
                } else {
                    distance += 0;
                }

                Float t2 = Float.parseFloat(seconds.get(i + 1));
                if (t2 > t1) {
                    time += t2 - t1;
                } else {
                    time += t1 - t2;
                }
            }
        }
        distance = distance * (Float.parseFloat("0.001"));
        time = time * (Float.parseFloat("0.000277778"));
        Float speed = distance / time;
//        speedtxt.setText(speed + " Km/Hr");
//        newspeed.setText(gps.getspeed() + " Km/Hr");
        Intent intent = new Intent("SpeedLimit");
        intent.putExtra("speed", speed);
        LocalBroadcastManager.getInstance(Back_Service.this).sendBroadcast(intent);

        int SpeedValue = Math.round(speed);
        CallAPIFuntion(SpeedValue);

    }


    public class latlng extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.sendlatlng(params[0], params[1], params[2], params[3], params[4]);
                JSONParse jp = new JSONParse();
                a = jp.mainparse(json);
            } catch (Exception e) {
                a = e.getMessage();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.compareTo("true") == 0) {
                Toast.makeText(Back_Service.this, "Your Cordinates are sent!!", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(Back_Service.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public class AddOverSpeed_Task extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.addOverSpeed(params[0], params[1], params[2], params[3], params[4], params[5]);
                JSONParse jp = new JSONParse();
                a = jp.mainparse(json);
            } catch (Exception e) {
                a = e.getMessage();
            }
            return a;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            Toast.makeText(mContext, s, Toast.LENGTH_SHORT).show();

            if (s.compareTo("true") == 0) {
                Toast.makeText(Back_Service.this, "You crossed speed limit", Toast.LENGTH_SHORT).show();
            } else {
//                Toast.makeText(Back_Service.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void CallAPIFuntion(int SpeedValue) {

        if (SpeedValue > SpeedLimit) {

            if (!CheckValue) {

//                String did,String uid,String speed,String latlng,String date,String time
                String datetext = d.format(dt);
                String timetext = t.format(dt);
                new AddOverSpeed_Task().execute(did, Uid, SpeedValue + "", LatLngValue, datetext, timetext);
                CheckValue = true;
            }

        } else if (SpeedValue <= SpeedLimit) {
            CheckValue = false;
        }

    }


    private boolean weHavePermissionforGPS() {
        return ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

}