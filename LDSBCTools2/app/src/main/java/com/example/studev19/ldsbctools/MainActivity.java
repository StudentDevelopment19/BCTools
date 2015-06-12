package com.example.studev19.ldsbctools;

import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.parse.Parse;

import java.util.ArrayList;
import java.util.List;
import android.support.v4.view.ViewPager;
import com.example.studev19.ldsbctools.tabs.SlidingTabLayout;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Directory", "BCSA", "Events", "Food", "Feedback"}; //Titles for the Tab Bar
    int NumbOfTabs = 5; //This controls the number of tabs on the Tab Bar
    public List<DirectoryObject> directoryArray = new ArrayList<DirectoryObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[][] contactInfo = {
                {"Academic Advising",           "Receive help with academic planning and scheduling.",              "801-524-8152", "advising@ldsbc.edu*",      "Room 905",     "Monday-Friday 9am-5pm"},
                {"Admissions",                  "Ask about applying or for more information.",                      "801-524-8145", "admiss@ldsbc.edu",         "Second Floor", "Monday-Friday 9am-5pm"},
                {"Bookstore",                   "Pre-order textbooks.",                                             "801-524-8130", "book@ldsbc.edu",           "First Floor",  "Monday-Friday 9am-5pm"},
                {"Campus Visits",               "Schedule a campus visit.",                                         "801-524-8159", "campusvisit@ldsbc.edu",    "Second Floor", "Monday-Friday 9am-5pm"},
                {"Career Management",           "Find a part-time or full-time job.",                               "801-524-8159", "career@ldsbc.edu",         "Room 406",     "Monday-Friday 9am-5pm"},
                {"Cashiers Office",             "Questions about tuition and insurance.",                           "801-524-8153", "cashier@ldsbc.edu",        "Second Floor", "Monday-Friday 9am-5pm"},
                {"Financial Aid",               "Ask about financial aid, loans, and grants.",                      "801-524-8111", "fa@ldsbc.edu",             "Second Floor", "Monday-Friday 9am-5pm"},
                {"Helpdesk",                    "For help and support with user accounts and College Computers.",   "801-524-8119", "helpdesk@ldsbc.edu",       "Second Floor", "Monday-Friday 9am-5pm"},
                {"Honor Code",                  "For questions or concerns regarding the Honor Code.",              "801-524-8157", "hc@ldsbc.edu",             "Room 905",     "Monday-Friday 9am-5pm"},
                {"Housing",                     "Find a place to live.",                                            "801-524-8180", "house@ldsbc.edu",          "Room 905",     "Monday-Friday 9am-5pm"},
                {"Registration",                "Ask about registration, transcripts, and class schedules.",        "801-524-8140", "reg@ldsbc.edu",            "Second Floor", "Monday-Friday 9am-5pm"},
                {"Scholarships",                "Learn how to qualify for scholarships.",                           "801-524-8111", "sch@ldsbc.edu",            "Second Floor", "Monday-Friday 9am-5pm"},
                {"Student Development Center",  "Find out about campus activities and events.",                     "801-524-8151", "sdc@ldsbc.edu",            "Room 905",     "Monday-Friday 9am-5pm"}
        };

        for (String[] i : contactInfo){
            DirectoryObject newObject = new DirectoryObject();
            newObject.setName(i[0]);
            newObject.setDescription(i[1]);
            newObject.setPhone(i[2]);
            newObject.setEmail(i[3]);
            newObject.setLocation(i[4]);
            newObject.setHours(i[5]);
            directoryArray.add(newObject);
        }

        Tab1.setData(directoryArray);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        //Creating the ViewPagerAdapter and passing Fragment Manager, Titles and Number of Tabs
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),Titles,NumbOfTabs);

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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*public void directoryClick (View v){
        //Start intent to open the Detailed View
        Intent intent = new Intent(this, directoryDetailedActivity.class);
        startActivity(intent);
    }*/

}

//PARSE QUERY//
        /*ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("serviceDirectory");
        query.addAscendingOrder("serviceName");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e != null) {
                    Toast.makeText(getApplicationContext(), "An error has occurred " + e, Toast.LENGTH_LONG).show();
                } else {
                    for (ParseObject objects : list) {
                        //Get data from Parse.com table
                        String serviceName = objects.getString("serviceName");
                        String servicePhone = objects.getString("phoneNumber");
                        String serviceDesc = objects.getString("description");
                        String serviceLocation = objects.getString("Location");
                        String serviceHours = objects.getString("hours");
                        String serviceEmail = objects.getString("email");

                        //Assign data to a DirectoryObject
                        DirectoryObject newObject = new DirectoryObject();
                        newObject.setName(serviceName);
                        newObject.setPhone(servicePhone);
                        newObject.setDescription(serviceDesc);
                        newObject.setLocation(serviceLocation);
                        newObject.setHours(serviceHours);
                        newObject.setEmail(serviceEmail);

                        //Add object to array
                        directoryArray.add(newObject);
                    }
                }

                //

            }
        });*/