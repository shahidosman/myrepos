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
public class Add_Fuel extends AppCompatActivity {

    SharedPreferences sp;
    String did = "";
    RelativeLayout bray, lray, aray;
    Button sub, can;
    EditText billno, amt, lit;
    double lat = 0.0, lng = 0.0;
    String date, time;
    GPS_Tracker gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_fuel);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setHomeButtonEnabled(true);
        ab.setTitle("Add Fuel Details");
        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        did = sp.getString("did", "");
        gps = new GPS_Tracker(this, Add_Fuel.this);
        getlatlng();
        bray = (RelativeLayout) findViewById(R.id.billnoray);
        lray = (RelativeLayout) findViewById(R.id.litray);
        aray = (RelativeLayout) findViewById(R.id.amtray);
        sub = (Button) findViewById(R.id.submit);
        can = (Button) findViewById(R.id.cancel);
        billno = (EditText) findViewById(R.id.billno);
        amt = (EditText) findViewById(R.id.amt);
        lit = (EditText) findViewById(R.id.lit);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date d = Calendar.getInstance().getTime();
        date = sdf.format(d);

        SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
        Date d1 = Calendar.getInstance().getTime();
        time = sdf1.format(d1);

//        Toast.makeText(Add_Fuel.this,date +"\n"+time, Toast.LENGTH_SHORT).show();

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (billno.getText().toString().compareTo("") != 0 || lit.getText().toString().compareTo("") != 0 || amt.getText().toString().compareTo("") != 0) {
                    if (billno.getText().toString().compareTo("") != 0) {
                        if (lit.getText().toString().compareTo("") != 0) {
                            if (amt.getText().toString().compareTo("") != 0) {


                                try {
                                    GPS_Tracker gps_tracker = new GPS_Tracker(Add_Fuel.this, Add_Fuel.this);
                                    if (gps_tracker.canGetLocation()) {
                                        double Lat = gps_tracker.getLatitude();
                                        double Lng = gps_tracker.getLongitude();

                                        if (Lat != 0 && Lng != 0) {
                                            new fueltask().execute(did, "" + lat, "" + lng, lit.getText().toString(), amt.getText().toString(), billno.getText().toString(), date, time);
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
                            lray.setBackgroundColor(Color.RED);
                            lit.setError("Enter Litre.");
                            lit.requestFocus();
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
                    lray.setBackgroundColor(Color.RED);
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

        lit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                lray.setBackgroundColor(Color.BLACK);
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

    public class fueltask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.addfuel(params[0], params[1], params[2], params[3], params[4], params[5], params[6], params[7]);
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
                Toast.makeText(Add_Fuel.this, "Fuel Details Added", Toast.LENGTH_SHORT).show();
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
