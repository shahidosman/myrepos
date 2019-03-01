package com.example.hp.androidvehicletracking;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Nevon-Sony on 13-Feb-18.
 */

public class Addleaves_Activity extends AppCompatActivity {

    TextView StartDateText, EndDateText;
    ImageView StartDateBtn, EndDateBtn;

    DatePickerDialog EndDateDialog;
    DatePickerDialog.OnDateSetListener End_date;
    Calendar EndCalendar = Calendar.getInstance();
    Calendar StartCalendar = Calendar.getInstance();
    Calendar c;
    SimpleDateFormat sdfd = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
    EditText ReasonText;
    Button SubmitBtn;

    SharedPreferences sp;
    String did,Uid;
    SimpleDateFormat datetext, timetext;
    Date dt;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_leaves_layout);

        StartDateText = (TextView) findViewById(R.id.startdate_text);
        EndDateText = (TextView) findViewById(R.id.enddate_text);
        StartDateBtn = (ImageView) findViewById(R.id.startdate_btn);
        EndDateBtn = (ImageView) findViewById(R.id.enddate_btn);

        ReasonText = (EditText) findViewById(R.id.reason);
        SubmitBtn = (Button) findViewById(R.id.submitbtn);


        getSupportActionBar().setTitle("Apply Leaves");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        sp = getSharedPreferences("vehicletrack", Context.MODE_PRIVATE);
        did = sp.getString("did", "");
        Uid = sp.getString("uid", "");


        Date d = new Date();
        String da = sdfd.format(d.getTime());
        StartDateText.setText(da);
        EndDateText.setText(da);

        EndDateDialog = new DatePickerDialog(Addleaves_Activity.this, End_date, EndCalendar.get(Calendar.YEAR), EndCalendar.get(Calendar.MONTH), EndCalendar.get(Calendar.DAY_OF_MONTH));

        StartDateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(Addleaves_Activity.this, Start_date, StartCalendar.get(Calendar.YEAR), StartCalendar.get(Calendar.MONTH), StartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        StartDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Addleaves_Activity.this, Start_date, StartCalendar.get(Calendar.YEAR), StartCalendar.get(Calendar.MONTH), StartCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        EndDateBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showEndDate_CalendarDialog();

                try {
                    EndDateDialog.getDatePicker().setMinDate(c.getTime().getTime());
                } catch (Exception e) {

                }

                EndDateDialog.show();
            }
        });


        EndDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);

                showEndDate_CalendarDialog();

                try {
                    EndDateDialog.getDatePicker().setMinDate(c.getTime().getTime());
                } catch (Exception e) {

                }

                EndDateDialog.show();

            }
        });


        SubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (StartDateText.getText().toString().compareTo("") == 0) {
                    Toast.makeText(Addleaves_Activity.this, "Start Date is required", Toast.LENGTH_SHORT).show();

                } else if (EndDateText.getText().toString().compareTo("") == 0) {
                    Toast.makeText(Addleaves_Activity.this, "End Date is required", Toast.LENGTH_SHORT).show();

                } else if (ReasonText.getText().toString().compareTo("") == 0) {
                    Toast.makeText(Addleaves_Activity.this, "Leave Reason is required", Toast.LENGTH_SHORT).show();
                } else {

//                    string uid,string did,string sdate,string edate,string reason,string dt

                    datetext = new SimpleDateFormat("yyyy/MM/dd");
                    timetext = new SimpleDateFormat("HH:mm");

                    dt = Calendar.getInstance().getTime();
                    String datestring = datetext.format(dt);
                    String timestring = timetext.format(dt);

                    new AddleaveTask().execute(Uid,did,StartDateText.getText().toString(),
                            EndDateText.getText().toString(),ReasonText.getText().toString(),datestring+" "+timestring);


                }
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

    DatePickerDialog.OnDateSetListener Start_date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            StartCalendar.set(Calendar.YEAR, year);
            StartCalendar.set(Calendar.MONTH, monthOfYear);
            StartCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            c = StartCalendar;
//            ToDateDialog.getDatePicker().setMinDate(c.getTimeInMillis());
            c.set(year, monthOfYear, dayOfMonth);
            String myFormat = "yyyy/MM/dd";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
            StartDateText.setText(sdf.format(StartCalendar.getTime()));

        }
    };


    public void showEndDate_CalendarDialog() {

        Calendar cal = Calendar.getInstance();
        EndDateDialog = new DatePickerDialog(Addleaves_Activity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                EndCalendar.set(Calendar.YEAR, year);
                EndCalendar.set(Calendar.MONTH, month);
                EndCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                String myFormat = "yyyy/MM/dd";
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                EndDateText.setText(sdf.format(EndCalendar.getTime()));
                String toastValue = sdf.format(EndCalendar.getTime());
            }
        }, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));

    }


    public class AddleaveTask extends AsyncTask<String, JSONObject, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {
            String a = "back";
            RestAPI api = new RestAPI();
            try {
                JSONObject json = api.addleave(params[0], params[1], params[2], params[3], params[4], params[5]);
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

//            Toast.makeText(Addleaves_Activity.this, s, Toast.LENGTH_SHORT).show();

            if (s.compareTo("true") == 0) {

                Toast.makeText(Addleaves_Activity.this, "Leaves Applied Successfully", Toast.LENGTH_SHORT).show();

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);


            } else if (s.compareTo("already") == 0) {
                Toast.makeText(Addleaves_Activity.this, "You have already applied leave for above dates", Toast.LENGTH_SHORT).show();

            } else {
                if (s.contains("Unable to resolve host")) {
                    AlertDialog.Builder ad = new AlertDialog.Builder(Addleaves_Activity.this);
                    ad.setTitle("Unable to Connect!");
                    ad.setMessage("Check your Internet Connection,Unable to connect the Server");
                    ad.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    ad.show();
                } else {
                    Toast.makeText(Addleaves_Activity.this, s, Toast.LENGTH_SHORT).show();
                }
            }


        }
    }


}
