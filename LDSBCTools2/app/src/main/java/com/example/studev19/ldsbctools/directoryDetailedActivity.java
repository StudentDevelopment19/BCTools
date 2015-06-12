package com.example.studev19.ldsbctools;

import android.content.ActivityNotFoundException;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class directoryDetailedActivity extends ActionBarActivity {
    Toolbar toolbar;
    private static DirectoryObject displayedInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_detailed);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //Set Title of the AppBar
        toolbar.setTitle(displayedInformation.getName());

        //Applies the AppBar
        setSupportActionBar(toolbar);

        //Set the Navigation Up button and enables is
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This section fills the information of the detailed view
        TextView descText = (TextView) findViewById(R.id.txtDescription);   //Find view for Description
        descText.setText(displayedInformation.getDescription());            //Sets Value for description from array
        TextView phoneText = (TextView) findViewById(R.id.txtPhone);        //Find view for Phone Number
        phoneText.setOnClickListener(new View.OnClickListener(){            //Sets onClick listener for Phone Call
            @Override
            public void onClick(View view) {
                try{
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:"+displayedInformation.getPhone()));
                    startActivity(callIntent);
                }
                catch(ActivityNotFoundException activityException){
                    Log.e("BC Tools", "Call failed", activityException);
                }
            }
        });
        phoneText.setText(displayedInformation.getPhone());                 //Sets value for phone number from array
        TextView emailText = (TextView) findViewById(R.id.txtEmail);        //Find view for Email
        emailText.setOnClickListener(new View.OnClickListener() {           //Sets onClick listener for Email
            @Override
            public void onClick(View view) {
                try{
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:"+displayedInformation.getEmail()));
                    startActivity(emailIntent);
                }
                catch(ActivityNotFoundException activityException){
                    Log.e("BC Tools", "Email failed", activityException);
                }
            }
        });
        emailText.setText(displayedInformation.getEmail());                 //Sets value for Email address from Array
        TextView locationText = (TextView) findViewById(R.id.txtLocation);  //Find view for Location
        locationText.setText(displayedInformation.getLocation());           //Sets value for Location from Array
        TextView scheduleText = (TextView) findViewById(R.id.txtSchedule);  //Finds view for schedule
        scheduleText.setText(displayedInformation.getHours());              //Sets value for Schedule

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_directory_detailed, menu);
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
            return true;
        }

        //Navigates up to MainActivity
        if (id == android.R.id.home){
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setServiceInfo(DirectoryObject directoryObject) {
        displayedInformation = directoryObject;
    }

}
