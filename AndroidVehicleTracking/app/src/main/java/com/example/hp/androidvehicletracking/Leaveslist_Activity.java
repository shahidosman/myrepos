package com.example.hp.androidvehicletracking;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Nevon-Sony on 13-Feb-18.
 */

public class Leaveslist_Activity extends AppCompatActivity {

    RelativeLayout relativeLayout;
    ListView listView;
    FloatingActionButton floatingActionButton;

    ArrayList<String> data;
    SharedPreferences sp;
    String did,Uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.leaves_layout);


        getSupportActionBar().setTitle("Leaves");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        did = sp.getString("did", "");
        Uid = sp.getString("uid", "");


        relativeLayout = (RelativeLayout) findViewById(R.id.leave_layout);
        listView = (ListView) findViewById(R.id.leave_list);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.addFloatButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Leaveslist_Activity.this, Addleaves_Activity.class);
                startActivity(intent);

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

    @Override
    protected void onResume() {
        super.onResume();

        new getLeaveList().execute(did);
    }


    public class getLeaveList extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.getLeaves(params[0]);
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

//            Toast.makeText(Leaveslist_Activity.this, s, Toast.LENGTH_SHORT).show();

            if (s.compareTo("") == 0) {
                Snackbar.make(relativeLayout, "Leave list not available", Snackbar.LENGTH_SHORT).show();
                listView.setAdapter(null);

            } else if (s.compareTo("no") == 0) {
                Snackbar.make(relativeLayout, "Leave list not available", Snackbar.LENGTH_SHORT).show();
                listView.setAdapter(null);

            } else if (s.contains("*")) {
                String temp[] = s.split("\\#");
                data = new ArrayList<String>();
                for (int i = 0; i < temp.length; i++) {
                    data.add(temp[i]);
                    //lid*sdate*edate*reason*dt*status
                }

                Adapter adapt = new Adapter(Leaveslist_Activity.this, data);
                listView.setAdapter(adapt);

            } else {
                if (s.contains("Unable to resolve host")) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Leaveslist_Activity.this);
                    ad.setTitle("Unable to Connect!");
                    ad.setMessage("Check your Internet Connection,Unable to connect the Server");
                    ad.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ad.show();

                } else {
                    Toast.makeText(Leaveslist_Activity.this, s, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    public class Adapter extends ArrayAdapter<String> {

        Context con;
        ArrayList<String> dataset;

        public Adapter(Context context, ArrayList<String> data) {
            super(context, R.layout.leave_list_row, data);
            con = context;
            dataset = data;

        }

        @NonNull
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = LayoutInflater.from(con).inflate(R.layout.leave_list_row, null, true);

             TextView StartDate = (TextView) v.findViewById(R.id.startdate_text);
             TextView EndDate = (TextView) v.findViewById(R.id.enddate_text);
             TextView Reasontext = (TextView) v.findViewById(R.id.reason_text);
             TextView AppliedDate = (TextView) v.findViewById(R.id.application_date);
             TextView Status = (TextView) v.findViewById(R.id.application_status);

            final String temp[] = dataset.get(position).split("\\*");

            // 0    1     2     3     4   5
            //lid*sdate*edate*reason*dt*status


            String startd = "<b>StartDate : </b>"+temp[1];
            String endd = "<b>EndDate : </b>"+temp[2];
            String applieddate = "<b>Application Date :  </b>"+temp[4];
            String status = "<b>Status :  </b>"+temp[5];


            StartDate.setText(Html.fromHtml(startd));
            EndDate.setText(Html.fromHtml(endd));
            Reasontext.setText(temp[3]);
            AppliedDate.setText(Html.fromHtml(applieddate));
            Status.setText(Html.fromHtml(status));


            return v;
        }
    }

}
