package com.example.studev19.ldsbctools;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.CalendarContract;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
        TextView eventDescText = (TextView) findViewById(R.id.txtEventDescription);     //Find view for Event Description
        eventDescText.setText(displayedInformation.getDescription());                   //Set value for description
        TextView eventStartDate = (TextView) findViewById(R.id.txtEventSchedule);       //Find view for Event Start Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd hh:mm a");              //Format Date
        eventStartDate.setOnClickListener(new View.OnClickListener() {                  //Set onClickListener event for Start Date
            @Override
            public void onClick(View v) {
                try{                                                                    //Start Calendar Activity
                    Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                    calendarIntent.setType("vnd.android.cursor.item/event");
                    calendarIntent.putExtra(CalendarContract.Events.TITLE, displayedInformation.getName());                         //Set event name for calendar
                    calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, displayedInformation.getLocation());            //Set event location for calendar
                    calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION, displayedInformation.getDescription());            //Set event description for calendar
                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, displayedInformation.getStartDateOnMST());     //Set event start date for calendar
                    calendarIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, displayedInformation.getEndDateOnMST());         //Set event end date for calendar
                    startActivity(calendarIntent);
                }
                catch (ActivityNotFoundException activityException){
                    Toast.makeText(getApplicationContext(), "An error occurred during the event creation process", Toast.LENGTH_SHORT).show();
                }
            }
        });
        eventStartDate.setText(dateFormat.format(displayedInformation.getStartDate())); //Set value for Start Date
        TextView eventLocation = (TextView) findViewById(R.id.txtEventLocation);        //Find view for Event Location
        eventLocation.setText(displayedInformation.getLocation());                      //Set value for Event Location

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
            Toast.makeText(getApplicationContext(), "This option is not available for now", Toast.LENGTH_SHORT).show();
            return true;
        }

        //Navigates up to MainActivity
        if (id == android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static  void setEventInfo(EventDetails eventDetails){
        displayedInformation = eventDetails;
    }

}
