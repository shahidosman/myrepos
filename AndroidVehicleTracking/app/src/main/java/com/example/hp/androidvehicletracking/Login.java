package com.example.hp.androidvehicletracking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.Date;

/**
 * Created by hp on 8/30/2016.
 */
public class Login extends AppCompatActivity {

    RelativeLayout ray, uray, pray;
    EditText user, pass;
    Button btn;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    String status = "";
    Dialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        status = sp.getString("status", "");

        if (!weHavePermissionToReadFiles()) {
            requestReadFilePermissionFirst();
        }

        mDialog = new Dialog(Login.this, R.style.AppTheme);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mDialog.setContentView(R.layout.circular_dialog);
        mDialog.setCancelable(false);

        if (status.compareTo("in") == 0) {
            Intent i = new Intent(Login.this, Home.class);
            startActivity(i);
            finish();
        } else if (status.compareTo("out") == 0 || status.compareTo("") == 0) {
            setContentView(R.layout.login);

            ray = (RelativeLayout) findViewById(R.id.loginray);
            uray = (RelativeLayout) findViewById(R.id.userray);
            pray = (RelativeLayout) findViewById(R.id.passray);
            user = (EditText) findViewById(R.id.username);
            pass = (EditText) findViewById(R.id.pass);
            btn = (Button) findViewById(R.id.login);


            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!weHavePermissionToReadFiles()) {
                        requestReadFilePermissionFirst();

                    } else {

                        if (user.getText().toString().compareTo("") != 0 || pass.getText().toString().compareTo("") != 0) {
                            if (user.getText().toString().compareTo("") != 0) {
                                if (pass.getText().toString().compareTo("") != 0) {


                                    try {
                                        GPS_Tracker gps_tracker = new GPS_Tracker(Login.this, Login.this);
                                        if (gps_tracker.canGetLocation()) {
                                            double Lat = gps_tracker.getLatitude();
                                            double Lng = gps_tracker.getLongitude();

                                            if (Lat != 0 && Lng != 0) {
                                                new logintask().execute(user.getText().toString(), pass.getText().toString());
                                            }
                                        } else {

                                            Snackbar snackbar = Snackbar.make(ray, "Enable GPS", Snackbar.LENGTH_SHORT);
                                            View vw = snackbar.getView();
                                            TextView txt = (TextView) vw.findViewById(android.support.design.R.id.snackbar_text);
                                            txt.setTextColor(Color.RED);
                                            snackbar.show();

                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                                    }


                                } else {
                                    pray.setBackgroundColor(Color.RED);
                                    pass.setError("Enter Contact No");
                                    pass.requestFocus();
                                }
                            } else {
                                uray.setBackgroundColor(Color.RED);
                                user.setError("Enter User Id");
                                user.requestFocus();
                            }
                        } else {


                            uray.setBackgroundColor(Color.RED);
                            pray.setBackgroundColor(Color.RED);
                            Snackbar snackbar = Snackbar.make(ray, "Enter Username & Password", Snackbar.LENGTH_SHORT);
                            View vw = snackbar.getView();
                            TextView txt = (TextView) vw.findViewById(android.support.design.R.id.snackbar_text);
                            txt.setTextColor(Color.RED);
                            snackbar.show();
                            user.requestFocus();
                        }

                    }


                }
            });

            user.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    uray.setBackgroundColor(Color.BLACK);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            pass.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    pray.setBackgroundColor(Color.BLACK);
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        user.setText("");
//        pass.setText("");
    }


    public class logintask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.driverlogin(params[0], params[1]);
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
            mDialog.dismiss();

//            Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();

            if (s.contains("*")) {

                String temp[] = s.split("\\*");
                String owerId = temp[0];
                String speedLimit = temp[1];

                Intent i = new Intent(Login.this, Home.class);
                startActivity(i);
                editor = sp.edit();
                editor.putString("did", user.getText().toString());
                editor.putString("status", "in");
                editor.putString("uid", owerId);
                editor.putString("speedLimit", speedLimit);
                editor.commit();
                finish();

            } else if (s.compareTo("false") == 0) {
                Snackbar snackbar = Snackbar.make(ray, "Invalid Credentails", Snackbar.LENGTH_SHORT);
                View vw = snackbar.getView();
                TextView txt = (TextView) vw.findViewById(android.support.design.R.id.snackbar_text);
                txt.setTextColor(Color.RED);
                snackbar.show();
                user.setText("");
                pass.setText("");
                user.requestFocus();
            } else {
                Toast.makeText(Login.this, s, Toast.LENGTH_SHORT).show();
            }
        }
    }

    public Boolean isgps() {
        Boolean is;
        GPS_Tracker gps = new GPS_Tracker(this, Login.this);
        is = gps.isGPSEnabled;
        return is;
    }

    private boolean weHavePermissionToReadFiles() {
        return (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) && ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestReadFilePermissionFirst() {
        if ((ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)) && (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION))) {
            requestForResultFilePermission();
        } else {
            requestForResultFilePermission();
        }
    }

    private void requestForResultFilePermission() {
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION}, 333);
    }


}
