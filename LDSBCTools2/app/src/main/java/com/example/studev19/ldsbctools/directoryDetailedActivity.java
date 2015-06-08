package com.example.studev19.ldsbctools;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
        TextView descText = (TextView) findViewById(R.id.txtDescription);
        descText.setText(displayedInformation.getDescription());
        TextView phoneText = (TextView) findViewById(R.id.txtPhone);
        phoneText.setText(displayedInformation.getPhone());
        TextView emailText = (TextView) findViewById(R.id.txtEmail);
        emailText.setText(displayedInformation.getEmail());
        TextView locationText = (TextView) findViewById(R.id.txtLocation);
        locationText.setText(displayedInformation.getLocation());
        TextView hoursText = (TextView) findViewById(R.id.txtSchedule);
        hoursText.setText(displayedInformation.getHours());

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
