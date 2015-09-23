package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.studev19.bctools.R;
import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventListActivity extends AppCompatActivity {

    private static Toolbar toolbar;
    private static final int DIALOG_ALERT = 10;
    private static final int NO_INTERNET_DIALOG = 5;
    private static RecyclerView recyclerView;
    private static SwipeRefreshLayout eventSwipe;
    private static eventViewAdapter adapter;
    private static List<EventDetails> eventArray;
    private static Context context;
    private static Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        context = this;

        //Create RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.eventList);
        eventSwipe = (SwipeRefreshLayout) findViewById(R.id.eventSwipeRefresh);
        eventSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);
        adapter = new eventViewAdapter(context, getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 1000);

        eventSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                eventArray.clear();


                //Set Date for current day (today)
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                today = c.getTime();

                //PARSE QUERY FOR EVENTS FROM THE INTERNET
                ParseQuery<ParseObject> eventQuery = new ParseQuery<ParseObject>("events");
                eventQuery.addAscendingOrder("startDate");
                eventQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> list, ParseException e) {
                        if (e != null) {

                        }
                        else {
                            ParseObject.unpinAllInBackground("events", new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    ParseObject.pinAllInBackground("events", list);
                                }
                            });
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

                                //Add object to eventArray
                                eventArray.add(newObject);
                            }

                        }

                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updatedEventData(eventArray);
                        adapter.notifyDataSetChanged();
                        eventSwipe.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigationDrawer);
        drawerFragment.setUp(R.id.navigationDrawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_event_list, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About this App");
                builder.setMessage(getString(R.string.current_version) + "\n\u00a92015 LDS Business College");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case NO_INTERNET_DIALOG:
                AlertDialog.Builder internet = new AlertDialog.Builder(this);
                internet.setTitle("You are not connected to the internet");
                internet.setMessage("Some information cannot be displayed without internet connection");
                internet.setCancelable(true);
                internet.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog internetDialog = internet.create();
                internetDialog.show();
                break;
        }

        return super.onCreateDialog(id);
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public static void setData(List<EventDetails> array) {
        eventArray = array;
    }

    public static List<EventDetails> getData() {
        return eventArray;
    }

}
