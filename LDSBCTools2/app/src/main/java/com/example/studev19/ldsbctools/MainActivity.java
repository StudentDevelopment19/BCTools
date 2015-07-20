package com.example.studev19.ldsbctools;

import android.app.Application;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.parse.FindCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.support.v4.view.ViewPager;

import com.example.studev19.ldsbctools.tabs.SlidingTabLayout;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Directory", "Events", "BCSA", "Deals", "Feedback"}; //Titles for the Tab Bar
    int NumbOfTabs = 5; //This controls the number of tabs on the Tab Bar
    public List<DirectoryObject> directoryArray = new ArrayList<DirectoryObject>();
    private static final int DIALOG_ALERT = 10;
    private static final int NO_INTERNET_DIALOG = 5;
    private boolean connection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection = internetConnection();

        //ARRAY WITH SERVICES INFORMATION.
        String[][] contactInfo = {
                {"Academic Advising",
                        "Meet with an academic advisor for academic guidance in planning, scheduling, and readiness to graduate.",
                        "801-524-8152",
                        "advising@ldsbc.edu*",
                        "Room 905",
                        "Monday-Friday 9am-5pm"},
                {"Admissions",
                        "Ask about applying or for more information.",
                        "801-524-8145",
                        "admiss@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Bookstore",
                        "Pre-order textbooks.",
                        "801-524-8130",
                        "book@ldsbc.edu",
                        "First Floor",
                        "Monday-Friday 9am-5pm"},
                {"Campus Visits",
                        "Discover if the LDS Business College experience is right for you. Let us share with you opportunities and resources that have been designed for your success.",
                        "801-524-8159",
                        "campusvisit@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Career Management",
                        "Find a part-time or full-time job.",
                        "801-524-8159",
                        "career@ldsbc.edu",
                        "Room 406",
                        "Monday-Friday 9am-5pm"},
                {"Cashiers Office",
                        "Questions about tuition and insurance.",
                        "801-524-8153",
                        "cashier@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Financial Aid",
                        "Ask about financial aid, loans, and grants.",
                        "801-524-8111",
                        "fa@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Helpdesk",
                        "For help and support with user accounts and College Computers.",
                        "801-524-8119", "helpdesk@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Honor Code",
                        "Contact the Honor Code Office for inquiries or reporting concerns about conduct, Honor Code policies, and ecclesiastical endorsements.",
                        "801-524-8157",
                        "hc@ldsbc.edu",
                        "Room 905",
                        "Monday-Friday 9am-5pm"},
                {"Housing",
                        "Learn how and where to find housing close to campus.",
                        "801-524-8180",
                        "house@ldsbc.edu",
                        "Room 905",
                        "Monday-Friday 9am-5pm"},
                {"Registration",
                        "Ask about registration, transcripts, and class schedules.",
                        "801-524-8140",
                        "reg@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Scholarships",
                        "Learn how to qualify for scholarships.",
                        "801-524-8111",
                        "sch@ldsbc.edu",
                        "Second Floor",
                        "Monday-Friday 9am-5pm"},
                {"Student Development Center",
                        "Find out about campus activities and events.",
                        "801-524-8151",
                        "sdc@ldsbc.edu",
                        "Room 905",
                        "Monday-Friday 9am-5pm"}
        };

        //PASSES THE INFORMATION FROM contactInfo ARRAY TO directoryArray
        for (String[] i : contactInfo) {
            DirectoryObject newObject = new DirectoryObject();
            newObject.setName(i[0]);
            newObject.setDescription(i[1]);
            newObject.setPhone(i[2]);
            newObject.setEmail(i[3]);
            newObject.setLocation(i[4]);
            newObject.setHours(i[5]);
            directoryArray.add(newObject);
        }

        //SENDS INFORMATION TO INFLATER
        Tab1.setData(directoryArray);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Creating the ViewPagerAdapter and passing Fragment Manager, Titles and Number of Tabs
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, NumbOfTabs);

        //Assigning ViewPager View and setting the adapter
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);

        //Assigning the Slide Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(false);

        //Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabScroll);
            }
        });

        //Setting the ViewPager for the SlidingTabsLayout
        tabs.setViewPager(pager);

        if (connection == false){
            //Snackbar.make(findViewById(android.R.id.content), "You are not connected to the internet. Some information will not be displayed", Snackbar.LENGTH_LONG).show();
            showDialog(NO_INTERNET_DIALOG);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About this App");
                builder.setMessage("Version 1.0" + "\n\u00a92015 LDS Business College");
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

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

    public boolean internetConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

}