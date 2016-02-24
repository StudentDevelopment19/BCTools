package com.example.studev19.bctools;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Spinner;
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

public class NewsListActivity extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static final int NO_INTERNET_DIALOG = 5;                                                //ID for No Internet Connection Dialog
    private static Toolbar toolbar;                                                                 //Declared Toolbar
    private static RecyclerView recyclerView;                                                       //RecyclerView for event list
    private static SwipeRefreshLayout newsSwipe;                                                    //Refresh Layout
    private static NewsViewAdapter adapter;                                                         //RecyclerView Adapter
    private static List<NewsFeedObject> newsArray;                                                   //List with events
    private static Context context;                                                                 //Context
    private static Date today;

    public static void setData(List <NewsFeedObject> array){
        newsArray = array;
    }
    public static List<NewsFeedObject> getData(){
        return newsArray;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("News Feed");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        //Create RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.newsList);                                  //Find view for RecyclerView
        newsSwipe = (SwipeRefreshLayout) findViewById(R.id.newsSwipeRefresh);                       //Find view for RefreshSwipe
        newsSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);               //Set colors for swipeRefreshLayout
        adapter = new NewsViewAdapter(context, getData());                                          //Initialize Adapter for RecyclerView
        recyclerView.setAdapter(adapter);                                                           //Set Adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));                               //Give layout for RecyclerView

        //Refresh the view after 1 second to show information from the beginning
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 1000);

        newsSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {                                                               //Set Refresh Listener
                newsArray.clear();                                                                  //Clear data set


                //Set Date for current day (today)
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                today = c.getTime();

                context = getApplicationContext();
                //PARSE QUERY FOR NEWS FROM THE INTERNET
                ParseQuery<ParseObject> newsQuery = new ParseQuery<ParseObject>("newsFeed");
                newsQuery.addAscendingOrder("postAt");
                newsQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null) {
                            Toast.makeText(context, "An error has occurred. \n" +
                                    "Data could not be downloaded from server", Toast.LENGTH_LONG).show();
                        } else {
                            ParseObject.pinAllInBackground("newsFeed", list);
                        }
                    }
                });

                //PARSE QUERY FOR NEWS FROM LOCAL DATA//
                final ParseQuery<ParseObject> localNewsQuery = new ParseQuery<ParseObject>("newsFeed");
                localNewsQuery.addAscendingOrder("postAt");
                localNewsQuery.fromLocalDatastore();
                localNewsQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null) {
                            Toast.makeText(context, "An error has occurred. \n" +
                                    "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
                        } else for (ParseObject objects : list) {
                            //Get data from Parse.com table
                            String newsTitle = objects.getString("title");
                            if (objects.getString("title").isEmpty()) {
                                newsTitle = "";
                            }
                            String newsDesc = objects.getString("description");
                            if (objects.getString("description").isEmpty()) {
                                newsDesc = "";
                            }
                            String newsWebPage = objects.getString("website");
                            if (objects.getString("website").isEmpty()) {
                                newsWebPage = "";
                            }
                            Date newsPostAt = objects.getDate("postAt");
                            ParseFile eventImage = objects.getParseFile("image");



                            //Assign data to an EventDetails object
                            NewsFeedObject newObject = new NewsFeedObject();
                            newObject.setTitle(newsTitle);
                            newObject.setDescription(newsDesc);
                            newObject.setWebsite(newsWebPage);
                            newObject.setPostAt(newsPostAt);
                            newObject.setImage(eventImage);


                            //Add object to eventArray
                            newsArray.add(newObject);


                        }

                    }
                });

                //Notify that data has changed and refresh
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updatedNewsData(newsArray);
                        adapter.notifyDataSetChanged();
                        newsSwipe.setRefreshing(false);
                    }
                }, 1000);
            }
        });

        //NAVIGATION SIDEBAR
        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigationDrawer);
        drawerFragment.setUp(R.id.navigationDrawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

    }

}
