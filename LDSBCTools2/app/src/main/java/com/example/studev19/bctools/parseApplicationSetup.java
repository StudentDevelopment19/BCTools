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
import com.parse.PushService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by studev19 on 6/12/2015.
 */
public class parseApplicationSetup extends Application {

    private static List<EventDetails> eventArray = new ArrayList<EventDetails>();
    private static List<DealObject> dealArray = new ArrayList<DealObject>();
    private static List<DirectoryObject> directoryArray = new ArrayList<DirectoryObject>();
    private static List<JobObject> employmentArray = new ArrayList<JobObject>();
    private static Date today;
    private static boolean connection;
    Context context;

    @Override
    public void onCreate() {

        super.onCreate();
        Parse.enableLocalDatastore(this);                                                           //Enable local storage
        Parse.initialize(this, getString(R.string.parseAppID),                          //Initialize Parse Connection
                getString(R.string.parseClientID));
        PushService.setDefaultPushCallback(this, MainActivity.class);                               //Initialize push notifications. When notification is clicked open the Main Activity

        //SET DATA TO ARRAYS
        setDirectoryData();
        setEventData();
        setDealData();
        setEmploymentData();

        //SENDS INFORMATION TO DIRECTORY INFLATER
        DirectoryListActivity.setData(getDirectoryData());

        //SENDS INFORMATION TO EVENT INFLATER
        EventListActivity.setData(getEventData());

        //SENDS INFORMATION TO DEAL INFLATER
        DealListActivity.setData(getDealData());

        //SENDS INFORMATION TO JOB INFLATER
        JobListActivity.setData(getEmploymentData());

        connection = internetConnection();
        FeedbackActivity.setConnectionStatus(connection);
        EmploymentEventWeb.setConnectionStatus(connection);


        ParseUser.enableAutomaticUser();
        ParseACL defaultACL = new ParseACL();
        ParseACL.setDefaultACL(defaultACL, true);

    }

    public void setDirectoryData() {

        context = getApplicationContext();

        //PARSE QUERY FOR CONTACTS FROM THE INTERNET//
        ParseQuery<ParseObject> dQuery = new ParseQuery<ParseObject>("serviceDirectory");
        dQuery.addAscendingOrder("serviceName");
        dQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Data could not be downloaded from server", Toast.LENGTH_LONG).show();
                } else {
                    ParseObject.pinAllInBackground("directory", list);
                }
            }
        });

        //PARSE QUERY FOR CONTACTS FROM LOCAL DATA
        ParseQuery<ParseObject> localDirQuery = new ParseQuery<ParseObject>("serviceDirectory");
        localDirQuery.addAscendingOrder("serviceName");
        localDirQuery.fromLocalDatastore();
        localDirQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "There is not saved information", Toast.LENGTH_LONG).show();
                } else for (ParseObject object : list) {
                    //Get data from Parse.com table
                    String contactName = object.getString("serviceName");
                    String contactDesc = object.getString("description");
                    String contactPhone = object.getString("phone");
                    if (object.getString("phone").isEmpty()) {
                        contactPhone = "";
                    }
                    String contactEmail = object.getString("email");
                    if (object.getString("email").isEmpty()) {
                        contactEmail = "";
                    }
                    String contactLoc = object.getString("Location");
                    if (object.getString("Location").isEmpty()) {
                        contactLoc = "";
                    }
                    String contactWeb = object.getString("website");
                    if (object.getString("website").isEmpty()) {
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

    public List<DirectoryObject> getDirectoryData() {
        return directoryArray;
    }

    public void setEventData() {

        //Set Date for current day (today)
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        today = c.getTime();

        context = getApplicationContext();

        //PARSE QUERY FOR EVENTS FROM THE INTERNET
        ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>("events");
        eventQuery.addAscendingOrder("startDate");
        eventQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Data could not be downloaded from server", Toast.LENGTH_LONG).show();
                } else {
                    ParseObject.pinAllInBackground("events", list);
                }
            }
        });

        //PARSE QUERY FOR EVENTS FROM LOCAL DATA//
        ParseQuery<ParseObject> localEventQuery = new ParseQuery<ParseObject>("events");
        localEventQuery.addAscendingOrder("startDate");
        localEventQuery.fromLocalDatastore();
        localEventQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
                } else for (ParseObject objects : list) {
                    //Get data from Parse.com table
                    String eventName = objects.getString("eventName");
                    if (objects.getString("eventName").isEmpty()) {
                        eventName = "";
                    }
                    String eventDesc = objects.getString("description");
                    if (objects.getString("description").isEmpty()) {
                        eventDesc = "";
                    }
                    String eventLocation = objects.getString("location");
                    if (objects.getString("location").isEmpty()) {
                        eventLocation = "";
                    }
                    String eventWebPage = objects.getString("website");
                    if (objects.getString("website").isEmpty()) {
                        eventWebPage = "";
                    }
                    Date eventStartDate = objects.getDate("startDate");
                    Date eventEndDate = objects.getDate("endDate");
                    ParseFile eventImage = objects.getParseFile("image");
                    String eventCategory = objects.getString("category");
                    if (objects.getString("category").isEmpty()) {
                        eventCategory = "";
                    }

                    //ADD ONLY THE UPCOMING EVENTS
                    if (eventEndDate.before(today) == false) {
                        //Assign data to an EventDetails object
                        EventDetails newObject = new EventDetails();
                        newObject.setName(eventName);
                        newObject.setDescription(eventDesc);
                        newObject.setLocation(eventLocation);
                        newObject.setEventWeb(eventWebPage);
                        newObject.setStartDate(eventStartDate);
                        newObject.setStartDateCalendar(eventStartDate);
                        newObject.setEndDate(eventEndDate);
                        newObject.setEndDateCalendar(eventEndDate);
                        newObject.setEventImage(eventImage);
                        newObject.setCategory(eventCategory);

                        //Add object to eventArray
                        eventArray.add(newObject);
                    }

                }

            }
        });

    }

    public List<EventDetails> getEventData() {
        return eventArray;
    }

    public void setDealData() {

        //Set Date for current day (today)
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        today = c.getTime();

        context = getApplicationContext();

        //PARSE QUERY DEALS FROM THE INTERNET//
        ParseQuery<ParseObject> dealsQuery = new ParseQuery<ParseObject>("deals");
        dealsQuery.addAscendingOrder("startDate");
        dealsQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {

                } else {
                    ParseObject.pinAllInBackground("deals", list);
                }
            }
        });

        //PARSE QUERY FOR DEALS FROM LOCAL DATA//
        ParseQuery<ParseObject> localDealsQuery = new ParseQuery<ParseObject>("deals");
        localDealsQuery.addAscendingOrder("startDate");
        localDealsQuery.fromLocalDatastore();
        localDealsQuery.findInBackground(new FindCallback<ParseObject>() {
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

    public List<DealObject> getDealData() {
        return dealArray;
    }

    public void setEmploymentData() {

        context = getApplicationContext();

        //PARSE QUERY DEALS FROM THE INTERNET//
        ParseQuery<ParseObject> employmentQuery = new ParseQuery<ParseObject>("jobListing");
        employmentQuery.addAscendingOrder("createdAt");
        employmentQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {

                } else {
                    ParseObject.pinAllInBackground("jobs", list);
                }
            }
        });

        //PARSE QUERY FOR DEALS FROM LOCAL DATA//
        ParseQuery<ParseObject> localEmploymentQuery = new ParseQuery<ParseObject>("jobListing");
        localEmploymentQuery.addAscendingOrder("createdAt");
        localEmploymentQuery.fromLocalDatastore();
        localEmploymentQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(context, "An error has occurred. \n" +
                            "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
                } else for (ParseObject objects : list) {
                    //Get data from Parse.com table
                    String jobPosition = objects.getString("position");
                    String jobCompany = objects.getString("company");
                    String jobLocation = objects.getString("location");
                    String jobDesc = objects.getString("description");

                    //ADD ONLY THE UPCOMING EVENTS

                    //Assign data to a DealObject
                    JobObject newObject = new JobObject();
                    newObject.setJobPosition(jobPosition);
                    newObject.setJobCompany(jobCompany);
                    newObject.setJobLocation(jobLocation);
                    newObject.setJobDescription(jobDesc);

                    //Add object to eventArray
                    employmentArray.add(newObject);


                }
            }
        });

    }

    public List<JobObject> getEmploymentData() {
        return employmentArray;
    }

    public boolean internetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}
