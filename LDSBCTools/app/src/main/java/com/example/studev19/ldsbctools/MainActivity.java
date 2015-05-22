package com.example.studev19.ldsbctools;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studev19.ldsbctools.tabs.SlidingTabLayout;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    Toolbar toolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[] = {"Directory", "BCSA", "Events", "Food", "Feedback"}; //Titles for the Tab Bar
    int NumbOfTabs = 5; //This controls the number of tabs on the Tab Bar
    public static List<DirectoryObject> directoryArray = new ArrayList<DirectoryObject>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize Parse.com
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "y4mrxs1MVgSv9qv5L79fui8d4ElGs7iQH6mJOWha", "9Y6aanyHQdDjxzpx6QYsBYxgMUb8tfFsB95caB3c");


        //Parse Query gets items from Parse.com and sends them to directoryArray
        ParseQuery<ParseObject> directoryQuery = ParseQuery.getQuery("serviceDirectory");
        directoryQuery.addAscendingOrder("serviceName");
        directoryQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject objects : list) {
                        String serviceName = objects.getString("serviceName");
                        int servicePhone = objects.getInt("phoneNumber");
                        String serviceDesc = objects.getString("description");
                        DirectoryObject newObject = new DirectoryObject();
                        newObject.setName(serviceName);
                        newObject.setPhone(servicePhone);
                        newObject.setDescription(serviceDesc);
                        directoryArray.add(newObject);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "You didn't get the items", Toast.LENGTH_SHORT).show();
                }
            }
        });

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

}
