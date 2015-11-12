package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class JobServicesActivity extends AppCompatActivity {
    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static final int NO_INTERNET_DIALOG = 5;                                                //ID for No Internet Connection Dialog
    private static Toolbar toolbar;                                                                 //Declared Toolbar
    private static Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_services);                                             //Layout and views come from activity_bcsa.xml

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);                                             //Initialize toolbar as app_bar
        setSupportActionBar(toolbar);                                                               //Enables toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);                                      //Displays home/back button on toolbar
        context = this;

        //----ICONS TO CAREER SERVICES' ACTIVITIES----//

        //--TO JOB LIST--//
        RelativeLayout jobSearch = (RelativeLayout) findViewById(R.id.layoutJobSearch);
        jobSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, JobListActivity.class));
            }
        });

        //--TO APPOINTMENTS--//
        RelativeLayout appointments = (RelativeLayout) findViewById(R.id.layoutAppointments);
        appointments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, AppointmentActivity.class));
            }
        });

        //--TO CAREER CENTER EVENTS--//
        RelativeLayout employmentEvents = (RelativeLayout) findViewById(R.id.layoutEmploymentEvents);
        employmentEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startEvents = new Intent(context, EmploymentEventWeb.class);
                context.startActivity(startEvents);
            }
        });

        //--TO FEEDBACK FORM--//
        RelativeLayout employmentFeedback = (RelativeLayout) findViewById(R.id.layoutEmploymentFeedback);
        employmentFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Intent feedbackIntent = new Intent(context, FeedbackActivity.class);
                    feedbackIntent.putExtra("feedback", "https://docs.google.com/a/ldsbc.edu/forms/d/1IjiLH7JQJXGcZPM3ZE2rw9dAN0NjC5SaZ-9uWXjIjbs/viewform");
                    startActivity(feedbackIntent);
                }
                catch (ActivityNotFoundException activityException){
                    Toast.makeText(context, "Application has stopped, failed to send email to the Career Center", Toast.LENGTH_LONG).show();
                }
            }
        });

        //TO FACEBOOK PAGE//
        LinearLayout facebookBanner = (LinearLayout) findViewById(R.id.facebookButton);
        facebookBanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    final String fbURL = "fb://page/424097204331940";
                    Intent facebookAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fbURL));
                    facebookAppIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
                    startActivity(facebookAppIntent);
                }
                catch (Exception e){
                    Intent facebookFromBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/424097204331940"));
                    startActivity(facebookFromBrowser);
                }
            }
        });

        //NAVIGATION DRAWER//
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigationDrawer);
        drawerFragment.setUp(R.id.navigationDrawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_services, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showDialog(DIALOG_ALERT);
            //Toast.makeText(getApplicationContext(), "This option is not available for now", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About this App");
                builder.setMessage(getString(R.string.current_version) + "\n\u00a92015 LDS Business College");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case NO_INTERNET_DIALOG:
                AlertDialog.Builder internet = new AlertDialog.Builder(this);
                internet.setTitle("You are not connected to the internet");
                internet.setMessage("Some information cannot be displayed without internet connection");
                internet.setCancelable(true);
                internet.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog internetDialog = internet.create();
                internetDialog.show();
                break;
        }

        return super.onCreateDialog(id);

    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
