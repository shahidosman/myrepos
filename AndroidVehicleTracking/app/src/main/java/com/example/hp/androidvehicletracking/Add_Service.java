package com.example.hp.androidvehicletracking;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by hp on 8/30/2016.
 */
public class Add_Service extends AppCompatActivity {

    String date, time;
    double lat = 0.0, lng = 0.0;
    GPS_Tracker gps;
    SharedPreferences sp;
    String did = "";
    RelativeLayout bray, sray, aray;
    Button sub, can;
    EditText billno, status, amt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_service);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("Add Service Details");

        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        did = sp.getString("did", "");
        gps = new GPS_Tracker(this, Add_Service.this);
        getlatlng();

        bray = (RelativeLayout) findViewById(R.id.billnoray);
        sray = (RelativeLayout) findViewById(R.id.statusray);
        aray = (RelativeLayout) findViewById(R.id.amtray);
        sub = (Button) findViewById(R.id.submit);
        can = (Button) findViewById(R.id.cancel);
        billno = (EditText) findViewById(R.id.billno);
        amt = (EditText) findViewById(R.id.amt);
        status = (EditText) findViewById(R.id.status);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = Calendar.getInstance().getTime();
        date = sdf.format(d);

        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date d1 = Calendar.getInstance().getTime();
        time = sdf1.format(d1);

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (billno.getText().toString().compareTo("") != 0 || status.getText().toString().compareTo("") != 0 || amt.getText().toString().compareTo("") != 0) {
                    if (billno.getText().toString().compareTo("") != 0) {
                        if (status.getText().toString().compareTo("") != 0) {
                            if (amt.getText().toString().compareTo("") != 0) {

                                try {
                                    GPS_Tracker gps_tracker = new GPS_Tracker(Add_Service.this, Add_Service.this);
                                    if (gps_tracker.canGetLocation()) {
                                        double Lat = gps_tracker.getLatitude();
                                        double Lng = gps_tracker.getLongitude();

                                        if (Lat != 0 && Lng != 0) {
                                            new servicetask().execute(did, "" + lat, "" + lng, status.getText().toString(), amt.getText().toString(), billno.getText().toString(), date, time);
                                        }
                                    } else {
                                        Snackbar snackbar = Snackbar.make(bray, "Enable GPS", Snackbar.LENGTH_SHORT);
                                        View vw = snackbar.getView();
                                        TextView txt = (TextView) vw.findViewById(android.support.design.R.id.snackbar_text);
                                        txt.setTextColor(Color.RED);
                                        snackbar.show();

                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                }


                            } else {
                                aray.setBackgroundColor(Color.RED);
                                amt.setError("Enter Amount.");
                                amt.requestFocus();
                            }
                        } else {
                            sray.setBackgroundColor(Color.RED);
                            status.setError("Enter Status.");
                            status.requestFocus();
                        }
                    } else {
                        bray.setBackgroundColor(Color.RED);
                        billno.setError("Enter Bill No.");
                        billno.requestFocus();
                    }
                } else {
                    Snackbar snackbar = Snackbar.make(bray, "All Fields are Mandatory", Snackbar.LENGTH_SHORT);
                    View vw = snackbar.getView();
                    TextView txt = (TextView) vw.findViewById(android.support.design.R.id.snackbar_text);
                    txt.setTextColor(Color.RED);
                    snackbar.show();
                    bray.setBackgroundColor(Color.RED);
                    sray.setBackgroundColor(Color.RED);
                    aray.setBackgroundColor(Color.RED);
                    billno.requestFocus();
                }
            }
        });


        can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        billno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                bray.setBackgroundColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        amt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                aray.setBackgroundColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        status.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                sray.setBackgroundColor(Color.BLACK);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return true;
    }


    public class servicetask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.addservice(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
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

            Toast.makeText(Add_Service.this, s, Toast.LENGTH_SHORT).show();

            if (s.compareTo("true") == 0) {
                Toast.makeText(Add_Service.this, "Service Details Added", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    public void getlatlng() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);

        } else {
            //Toast.makeText(mContext,"You need have granted permission",Toast.LENGTH_SHORT).show();

            // Check if GPS enabled
            if (gps.canGetLocation()) {
//                gpsdailog();
//                initializeTimerTask();
//                timer.schedule(timerTask, 0, 500);
                lat = gps.getLatitude();
                lng = gps.getLongitude();


                //Toast.makeText(getActivity(), "Your Location is - \nLat: " + Latitude + "\nLong: " + Longitude, Toast.LENGTH_LONG).show();
            } else {
                Snackbar snackbar = Snackbar.make(bray, "Enable GPS", Snackbar.LENGTH_SHORT);
                View vw = snackbar.getView();
                TextView txt = (TextView) vw.findViewById(android.support.design.R.id.snackbar_text);
                txt.setTextColor(Color.RED);
                snackbar.show();
            }
        }
    }
}
