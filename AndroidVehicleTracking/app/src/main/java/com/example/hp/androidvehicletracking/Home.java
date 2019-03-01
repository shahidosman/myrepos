package com.example.hp.androidvehicletracking;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

public class Home extends AppCompatActivity {

    TextView name, vehicle;
    String did;
    ImageView service, fuel;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    Button logout;
    TextView speedlimit;
    String SpeedLimitValue="";

    Button Leavelist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        did = sp.getString("did", "");
        SpeedLimitValue = sp.getString("speedLimit","");

        ActionBar ab = getSupportActionBar();
        ab.setIcon(R.drawable.logo);

        Leavelist = (Button) findViewById(R.id.leave_list);
        service = (ImageView) findViewById(R.id.service);
        fuel = (ImageView) findViewById(R.id.fuel);
        name = (TextView) findViewById(R.id.name);
        vehicle = (TextView) findViewById(R.id.vehicleno);
        logout = (Button) findViewById(R.id.logout);
        speedlimit = (TextView) findViewById(R.id.speedlimit);

        new driverinfo().execute(did);
        service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Add_Service.class);
                startActivity(i);
            }
        });


        Leavelist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent  = new Intent(Home.this,Leaveslist_Activity.class);
                startActivity(intent);
            }
        });

        fuel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Home.this, Add_Fuel.class);
                startActivity(i);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopService(new Intent(Home.this, Back_Service.class));
                Intent i = new Intent(Home.this, Login.class);
                startActivity(i);
                finish();
                editor = sp.edit();
                editor.putString("did", "");
                editor.putString("status", "out");
                editor.commit();
            }
        });

        Intent i = new Intent(Home.this, Back_Service.class);
        startService(i);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(message, new IntentFilter("SpeedLimit"));
    }

    private BroadcastReceiver message = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            float ans = intent.getFloatExtra("speed",0);

            String a = "<b>" + "Speed: " + "</b>" + ans+" / "+SpeedLimitValue;
            speedlimit.setText(Html.fromHtml(a));
        }
    };



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mi = getMenuInflater();
        mi.inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if (item.getItemId() == R.id.logout) {
            stopService(new Intent(Home.this, Back_Service.class));
            Intent i = new Intent(Home.this, Login.class);
            startActivity(i);
            finish();
            editor = sp.edit();
            editor.putString("did", "");
            editor.putString("status", "out");
            editor.commit();
        }
        return true;
    }

    public class driverinfo extends AsyncTask<String, JSONObject, String> {

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.driverinfo(params[0]);
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
//            Toast.makeText(Home.this,s, Toast.LENGTH_SHORT).show();
            if (s.compareTo("false") != 0) {
                String temp[] = s.split("\\*");
                String n = "<b>" + "Name: " + "</b>" + temp[0];
                String v = "<b>" + "Vehicle No: " + "</b>" + temp[1];
                name.setText(Html.fromHtml(n));
                vehicle.setText(Html.fromHtml(v));
            }
        }
    }
}
