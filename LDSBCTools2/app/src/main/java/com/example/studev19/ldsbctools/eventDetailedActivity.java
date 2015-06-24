package com.example.studev19.ldsbctools;

import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;


public class eventDetailedActivity extends ActionBarActivity {
    Toolbar toolbar;
    private static EventDetails displayedInformation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detailed);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //Set Title of the AppBar
        toolbar.setTitle(displayedInformation.getName());

        //Applies the AppBar
        setSupportActionBar(toolbar);

        //Set the Navigation Up button and enables it
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This section fills the information of the detailed view
        TextView eventDescText = (TextView) findViewById(R.id.txtEventDescription);  //Find view for Event Description
        eventDescText.setText(displayedInformation.getDescription());                //Set value for description
        TextView eventStartDate = (TextView) findViewById(R.id.txtEventSchedule);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM,hh:mm a");
        eventStartDate.setText(dateFormat.format(displayedInformation.getStartDate()));
        TextView eventLocation = (TextView) findViewById(R.id.txtEventLocation);
        eventLocation.setText(displayedInformation.getLocation());

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_detailed, menu);
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

    public static  void setEventInfo(EventDetails eventDetails){
        displayedInformation = eventDetails;
    }

}
