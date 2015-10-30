package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class jobDetailedActivity extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static Toolbar toolbar;                                                                 //Declared Toolbar
    private static JobObject displayedInformation;                                                  //JobObject with info for Detailed View

    public static void setJobInfo(JobObject jobDetails) {
        displayedInformation = jobDetails;
    }                                       //Set Current Info for Detailed View

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_detailed);                                             //Layout and views come from activity_job_detailed.xml

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);                                             //Initialize toolbar as app_bar
        toolbar.setTitle("Details");                                                                //Set Title for toolbar as "Details"
        setSupportActionBar(toolbar);                                                               //Enables the AppBar
        getSupportActionBar().setHomeButtonEnabled(true);                                           //Displays home/back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                      //Home button will show as back button

        //-----THE FOLLOWING SECTION FILLS THE INFORMATION OF THE DETAILED VIEW-----//

        TextView positionText = (TextView) findViewById(R.id.txtDetailedJobPosition);               //Find TextView for Position
        positionText.setText(displayedInformation.getJobPosition());                                //Set value for Position
        TextView companyText = (TextView) findViewById(R.id.txtDetailedJobCompany);                 //Find TextView for Company
        companyText.setText(displayedInformation.getJobCompany());                                  //Set value for Company
        TextView locationText = (TextView) findViewById(R.id.txtDetailedJobLocation);               //Find TextView for Location
        locationText.setText(displayedInformation.getJobLocation());                                //Set value for Location
        TextView descriptionText = (TextView) findViewById(R.id.txtDetailedJobDescription);         //Find TextView for Job Description
        descriptionText.setText(displayedInformation.getJobDescription());                          //Set value for Job Description

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_details, menu);
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
            return true;
        }

        if (id == android.R.id.home) {
            this.finish();
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
        }
        return super.onCreateDialog(id);
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
