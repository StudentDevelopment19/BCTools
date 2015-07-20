package com.example.studev19.ldsbctools;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
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
    public List<DealObject> dealArray = new ArrayList<DealObject>();
    private static Date today;
    private boolean connection;

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

        connection = internetConnection();

        Tab2.setConnectionStatus(connection);
        Tab4.setConnectionStatus(connection);
        Tab5.setConnectionStatus(connection);

        if (connection == true) {

            //PARSE QUERY//
            ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("events");
            query.addAscendingOrder("startDate");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e != null) {
                        Toast.makeText(getApplicationContext(), "An error has occurred ", Toast.LENGTH_LONG).show();
                    } else for (ParseObject objects : list) {
                        //Get data from Parse.com table
                        String eventName = objects.getString("eventName");
                        String eventDesc = objects.getString("description");
                        String eventLocation = objects.getString("location");
                        Date eventStartDate = objects.getDate("startDate");
                        Date eventEndDate = objects.getDate("endDate");

                        //ADD ONLY THE UPCOMING EVENTS
                        if (eventEndDate.before(today) == false) {
                            //Assign data to a DirectoryObject
                            EventDetails newObject = new EventDetails();
                            newObject.setName(eventName);
                            newObject.setDescription(eventDesc);
                            newObject.setLocation(eventLocation);
                            newObject.setStartDate(eventStartDate);
                            newObject.setStartDateCalendar(eventStartDate);
                            newObject.setEndDate(eventEndDate);
                            newObject.setEndDateCalendar(eventEndDate);

                            //Add object to eventArray
                            eventArray.add(newObject);
                        }

                    }

                }
            });

            Tab2.setData(eventArray);

            ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("deals");
            query2.addAscendingOrder("startDate");
            query2.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> list, ParseException e) {
                    if (e != null) {
                        Toast.makeText(getApplicationContext(), "An error has occurred " + e, Toast.LENGTH_LONG).show();
                    } else for (ParseObject objects : list) {
                        //Get data from Parse.com table
                        String dealTitle = objects.getString("title");
                        String dealDesc = objects.getString("description");
                        String dealAddress = objects.getString("address");
                        String dealCompany = objects.getString("company");
                        Date dealStartDate = objects.getDate("startDate");
                        Date dealEndDate = objects.getDate("endDate");
                        ParseFile dealImage = objects.getParseFile("image");

                        //ADD ONLY THE UPCOMING EVENTS
                        if (dealEndDate.before(today) == false) {
                            //Assign data to a DirectoryObject
                            DealObject newObject = new DealObject();
                            newObject.setDealTitle(dealTitle);
                            newObject.setDealDesciption(dealDesc);
                            newObject.setDealAddress(dealAddress);
                            newObject.setDealCompany(dealCompany);
                            newObject.setDealStartDate(dealStartDate);
                            newObject.setDealEndDate(dealEndDate);
                            newObject.setDealImage(dealImage);

                            //Add object to eventArray
                            dealArray.add(newObject);
                        }

                    }
                }
            });

            Tab4.setData(dealArray);

        }

        else{
            //Toast.makeText(getApplicationContext(), "You are not connected to the internet", Toast.LENGTH_LONG).show();
            Tab2.setData(eventArray);
            Tab4.setData(dealArray);
        }

        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

    }

    public boolean internetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
