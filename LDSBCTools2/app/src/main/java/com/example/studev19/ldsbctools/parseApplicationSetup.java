package com.example.studev19.ldsbctools;

import android.app.Application;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by studev19 on 6/12/2015.
 */
public class parseApplicationSetup extends Application{

    public List<EventDetails> eventArray = new ArrayList<EventDetails>();
    private static Date today;

    @Override
    public void onCreate(){

        super.onCreate();
        Parse.initialize(this, getString(R.string.parseAppID), getString(R.string.parseClientID));

        //Set Date for current day (today)
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        today = c.getTime();

        //PARSE QUERY//
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("events");
        query.addAscendingOrder("startDate");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), "An error has occurred " + e, Toast.LENGTH_LONG).show();
                } else for (ParseObject objects : list) {
                    //Get data from Parse.com table
                    String eventName = objects.getString("eventName");
                    String eventDesc = objects.getString("description");
                    String eventLocation = objects.getString("location");
                    Date eventStartDate = objects.getDate("startDate");
                    Date eventEndDate = objects.getDate("endDate");

                    //ADD ONLY THE UPCOMING EVENTS
                    if ( eventStartDate.before(today) == false){
                        //Assign data to a DirectoryObject
                        EventDetails newObject = new EventDetails();
                        newObject.setName(eventName);
                        newObject.setDescription(eventDesc);
                        newObject.setLocation(eventLocation);
                        newObject.setStartDate(eventStartDate);
                        newObject.setEndDate(eventEndDate);

                        //Add object to eventArray
                        eventArray.add(newObject);
                    }

                }

            }
        });

        Tab2.setData(eventArray);

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

    }
}
