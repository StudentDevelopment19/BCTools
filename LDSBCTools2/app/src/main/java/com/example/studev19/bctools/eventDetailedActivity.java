package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;


public class eventDetailedActivity extends ActionBarActivity {
    Toolbar toolbar;
    private static EventDetails displayedInformation;
    Calendar calStartDate;
    Calendar calEndDate;
    private static final int DIALOG_ALERT = 10;
    private static String hyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detailed);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //Set Title of the AppBar
        toolbar.setTitle("");

        //Applies the AppBar
        setSupportActionBar(toolbar);

        //Set the Navigation Up button and enables it
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Initialize Calendars
        calStartDate = Calendar.getInstance();
        calStartDate.setTime(displayedInformation.getStartDate());
        calEndDate = Calendar.getInstance();
        calEndDate.setTime(displayedInformation.getEndDate());

        //This section fills the information of the detailed view
        final ParseImageView dealImage = (ParseImageView) findViewById(R.id.imgEventAd);            //Find image view
        ParseFile imageFile = displayedInformation.getEventImage();                                 //Set ParseFile as image from parse
        dealImage.setParseFile(imageFile);                                                          //Set dealImage as Image from parse
        dealImage.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {                                                  //This process makes the image visible from app
                //The image is loaded and displayed
                int oldHeight = dealImage.getHeight();
                int oldWidth = dealImage.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });

        //This section fills the information of the detailed view
        //EVENT NAME SECTION
        TextView eventNameText = (TextView) findViewById(R.id.txtEventName);
        eventNameText.setText(displayedInformation.getName());

        //EVENT DESCRIPTION SECTION
        TextView eventDescText = (TextView) findViewById(R.id.txtEventDescription);                 //Find view for Event Description
        eventDescText.setText(displayedInformation.getDescription());                               //Set value for description


        //DATE SECTION
        TextView eventStartDate = (TextView) findViewById(R.id.txtEventSchedule);                   //Find view for Event Start Date
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, hh:mm a");                      //Set date format for start date
        dateFormat.setTimeZone(TimeZone.getTimeZone("MST"));                                        //Set time zone to mst for start date
        SimpleDateFormat endTime = new SimpleDateFormat("hh:mm a");                                 //Set date format for end date
        endTime.setTimeZone(TimeZone.getTimeZone("MST"));                                           //Set time zone to mst for end date
        eventStartDate.setText(dateFormat.format(displayedInformation.getStartDate())               //Set value for "Start Date to End Date"
                + " to " + endTime.format(displayedInformation.getEndDate()));

        //LOCATION SECTION
        TextView eventLocation = (TextView) findViewById(R.id.txtEventLocation);                    //Find view for Event Location
        ImageView eventLocationIcon = (ImageView) findViewById(R.id.icoEventLocation);              //Find view for Event Location Icon
        if (displayedInformation.getLocation() == "") {                                              //If Location value is empty dismiss the views
            eventLocationIcon.setVisibility(View.GONE);
            eventLocation.setVisibility(View.GONE);
        } else if (displayedInformation.getLocation() != "") {                                         //If Location value is not empty show Location
            eventLocation.setText(displayedInformation.getLocation());                              //Set value for Event Location
        }

        //WEB SECTION
        TextView eventWebText = (TextView) findViewById(R.id.txtEventWeb);                          //Finds view for Website
        ImageView eventWebIco = (ImageView) findViewById(R.id.icoEventWeb);                         //Finds view for Website Icon
        if (displayedInformation.getEventWeb() == "") {                                              //If website is empty dismiss the views
            eventWebIco.setVisibility(View.GONE);
            eventWebText.setVisibility(View.GONE);
        } else if (displayedInformation.getEventWeb() != "") {                                         //If website is not empty create hyperlink
            hyperlink = "<a href='" + displayedInformation.getEventWeb() + "'>" + "Website" + "</a>"; //Set hyperlink value
            eventWebText.setClickable(true);                                                        //Sets Website text clickable
            eventWebText.setMovementMethod(LinkMovementMethod.getInstance());                       //Enables hyperlink
            eventWebText.setText(Html.fromHtml(hyperlink));                                         //Sets value for Website));

            if (eventLocationIcon.getVisibility() == View.GONE) {
                ViewGroup.LayoutParams p = eventWebIco.getLayoutParams();
                if (p instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                    lp.addRule(RelativeLayout.BELOW, R.id.icoEventSchedule);
                    eventWebIco.setLayoutParams(lp);
                }
            }
        }

        //ADD TO CALENDAR BUTTON
        Button addEventButton = (Button) findViewById(R.id.btnAddEvent);                            //Find view for Button

        //Initialize Calendars
        calStartDate = Calendar.getInstance();
        calEndDate = Calendar.getInstance();
        TimeZone tz = TimeZone.getDefault();
        calStartDate.setTimeZone(tz);
        calEndDate.setTimeZone(tz);
        calStartDate.setTime(displayedInformation.getStartDateCalendar());
        calEndDate.setTime(displayedInformation.getEndDateCalendar());

        addEventButton.setOnClickListener(new View.OnClickListener() {                      //Set onClickListener event for AddEventButton

            @Override
            public void onClick(View v) {
                try {                                                                               //Start Calendar Activity
                    Intent calendarIntent = new Intent(Intent.ACTION_INSERT);
                    calendarIntent.setType("vnd.android.cursor.item/event");
                    calendarIntent.putExtra(CalendarContract.Events.TITLE,                          //Set event name for calendar
                            displayedInformation.getName());
                    calendarIntent.putExtra(CalendarContract.Events.EVENT_LOCATION,                 //Set event location for calendar
                            displayedInformation.getLocation());
                    calendarIntent.putExtra(CalendarContract.Events.DESCRIPTION,                    //Set event description for calendar
                            displayedInformation.getDescription());
                    calendarIntent.putExtra("beginTime", calStartDate.getTimeInMillis());           //Set event start date for calendar
                    calendarIntent.putExtra("endTime", calEndDate.getTimeInMillis());               //Set event end date for calendar
                    startActivity(calendarIntent);                                                  //Start calendar activity
                } catch (ActivityNotFoundException activityException) {                             //Exception handler for calendar
                    Toast.makeText(getApplicationContext(), "An error occurred during the event creation process",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (eventWebIco.getVisibility() == View.GONE) {
            ViewGroup.LayoutParams p = addEventButton.getLayoutParams();
            if (p instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                lp.addRule(RelativeLayout.BELOW, R.id.txtEventLocation);
                addEventButton.setLayoutParams(lp);
            }
        }
        if (eventWebIco.getVisibility() == View.GONE &&
                eventLocationIcon.getVisibility() == View.GONE) {
            ViewGroup.LayoutParams p = addEventButton.getLayoutParams();
            if (p instanceof RelativeLayout.LayoutParams) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                lp.addRule(RelativeLayout.BELOW, R.id.txtEventSchedule);
                addEventButton.setLayoutParams(lp);
            }
        }

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
            showDialog(DIALOG_ALERT);
            //Toast.makeText(getApplicationContext(), "This option is not available for now", Toast.LENGTH_SHORT).show();
            return true;
        }

        //Navigates up to MainActivity
        if (id == android.R.id.home) {
            //NavUtils.navigateUpFromSameTask(this);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setEventInfo(EventDetails eventDetails) {
        displayedInformation = eventDetails;
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
