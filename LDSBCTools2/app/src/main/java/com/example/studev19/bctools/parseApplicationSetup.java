package com.example.studev19.bctools;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by studev19 on 6/12/2015.
 */
public class parseApplicationSetup extends Application{

    private static List<EventDetails> eventArray = new ArrayList<EventDetails>();
    private static List<DealObject> dealArray = new ArrayList<DealObject>();
    private static List<DirectoryObject> directoryArray = new ArrayList<DirectoryObject>();
    private static Date today;
    private static boolean connection;
    Context context;

    @Override
    public void onCreate(){

        super.onCreate();
        Parse.initialize(this, getString(R.string.parseAppID), getString(R.string.parseClientID));

        //SET DATA TO ARRAYS
        setDirectoryData();
        setEventData();
        setDealData();

        //SENDS INFORMATION TO DIRECTORY INFLATER
        Tab1.setData(getDirectoryData());
        Log.v("Directory Sent", "parseApplicationSetup " + directoryArray.size());

        //SENDS INFORMATION TO EVENT INFLATER
        Tab2.setData(getEventData());
        Log.v("Events Sent", "parseApplicationSetup " + eventArray.size());

        //SENDS INFORMATION TO DEAL INFLATER
        Tab4.setData(getDealData());
        Log.v("Deals Sent", "parseApplicationSetup " + dealArray.size());

        connection = internetConnection();

        Tab2.setConnectionStatus(connection);
        Tab4.setConnectionStatus(connection);
        Tab5.setConnectionStatus(connection);



        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

    }

    public void setDirectoryData(){

        context = getApplicationContext();

        //PARSE QUERY FOR CONTACTS//
        ParseQuery<ParseObject> dQuery = new ParseQuery<ParseObject>("serviceDirectory");
        dQuery.addAscendingOrder("serviceName");
        dQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
                } else for (ParseObject object : list) {
                    //Get data from Parse.com table
                    String contactName = object.getString("serviceName");
                    String contactDesc = object.getString("description");
                    String contactPhone = object.getString("phone");
                    if (object.getString("phone").isEmpty()){
                        contactPhone = "";
                    }
                    String contactEmail = object.getString("email");
                    if (object.getString("email").isEmpty()){
                        contactEmail = "";
                    }
                    String contactLoc = object.getString("Location");
                    if (object.getString("Location").isEmpty()){
                        contactLoc = "";
                    }
                    String contactWeb = object.getString("website");
                    if (object.getString("website").isEmpty()){
                        contactWeb = "";
                    }
                    Log.v(contactName, contactWeb);

                    //Assign data to a DirectoryObject
                    DirectoryObject newObject = new DirectoryObject();

                    newObject.setName(contactName);
                    newObject.setDescription(contactDesc);
                    newObject.setPhone(contactPhone);
                    newObject.setEmail(contactEmail);
                    newObject.setLocation(contactLoc);
                    newObject.setWebSite(contactWeb);

                    //Add object to eventArray
                    directoryArray.add(newObject);
                }
            }
        });

    }

    public List<DirectoryObject> getDirectoryData(){
        return directoryArray;
    }

    public void setEventData(){

        //Set Date for current day (today)
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        today = c.getTime();

        context = getApplicationContext();

        //PARSE QUERY FOR EVENTS//
        ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("events");
        query.addAscendingOrder("startDate");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
                } else for (ParseObject objects : list) {
                    //Get data from Parse.com table
                    String eventName = objects.getString("eventName");
                    String eventDesc = objects.getString("description");
                    String eventLocation = objects.getString("location");
                    Date eventStartDate = objects.getDate("startDate");
                    Date eventEndDate = objects.getDate("endDate");

                    //ADD ONLY THE UPCOMING EVENTS
                    if (eventEndDate.before(today) == false) {
                        //Assign data to an EventDetails object
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

    }

    public  List<EventDetails> getEventData(){
        return eventArray;
    }

    public void setDealData(){

        //Set Date for current day (today)
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        today = c.getTime();

        context = getApplicationContext();

        //PARSE QUERY FOR DEALS//
        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("deals");
        query2.addAscendingOrder("startDate");
        query2.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
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
                        //Assign data to a DealObject
                        DealObject newObject = new DealObject();
                        newObject.setDealTitle(dealTitle);
                        newObject.setDealDescription(dealDesc);
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

    }

    public List<DealObject> getDealData(){
        return dealArray;
    }

    public boolean internetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
