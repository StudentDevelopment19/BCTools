package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class JobListActivity extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static final int NO_INTERNET_DIALOG = 5;                                                //ID for No Internet Connection Dialog
    private static Toolbar toolbar;                                                                 //Declared Toolbar
    private static RecyclerView recyclerView;                                                       //RecyclerView for job list
    private static SwipeRefreshLayout swipeRefreshLayout;                                           //Refresh Layout
    private static employmentViewAdapter adapter;                                                   //RecyclerView Adapter
    private static List<JobObject> employmentList;                                                  //List with jobs
    private static Context context;                                                                 //Declared Context

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);                                                 //Layout and views come from activity_job_list.xml

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);                                             //Initialize toolbar as app_bar
        setSupportActionBar(toolbar);                                                               //Enable toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);                                      //Displays home/back button on toolbar
        context = this;

        //RECYCLER VIEW
        recyclerView = (RecyclerView) findViewById(R.id.employmentList);                            //Find view for RecyclerView
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeJobRefresh);               //Find view for RefreshSwipe
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);      //Set colors for swipeRefreshLayout
        adapter = new employmentViewAdapter(context, getData());                                    //Initialize Adapter for RecyclerView
        recyclerView.setAdapter(adapter);                                                           //Set Adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));                               //Give layout for RecyclerView

        //Refresh the view after 1 second to show information from the beginning
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 1000);

        //SWIPE LISTENER
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                employmentList.clear();                                                             //Clear data set

                //PARSE QUERY DEALS FROM THE INTERNET//
                ParseQuery<ParseObject> employmentQuery = new ParseQuery<ParseObject>("jobListing");
                employmentQuery.addAscendingOrder("createdAt");
                employmentQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> list, ParseException e) {
                        if (e != null) {

                        } else {
                            ParseObject.unpinAllInBackground("jobs", new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    ParseObject.pinAllInBackground("jobs", list);
                                }
                            });
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
                            employmentList.add(newObject);


                        }
                    }
                });

                //Notify that data has changed and refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateData(employmentList);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });

        //NAVIGATION SIDEBAR
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigationDrawer);

        drawerFragment.setUp(R.id.navigationDrawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_list, menu);
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

    public List<JobObject> getData() {
        return employmentList;
    }

    public static void setData(List<JobObject> array) {
        employmentList = array;
    }                                         //Set data from parseApplicationSetup

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
