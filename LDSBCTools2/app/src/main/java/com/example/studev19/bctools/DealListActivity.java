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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
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

public class DealListActivity extends AppCompatActivity {

    private static Toolbar toolbar;
    private static final int DIALOG_ALERT = 10;
    private static final int NO_INTERNET_DIALOG = 5;
    private static RecyclerView recyclerView;
    private static SwipeRefreshLayout dealsSwipe;
    private static dealViewAdapter adapter;
    private static List<DealObject> dealArray;
    private static Context context;
    private static Date today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_list);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        context = this;

        recyclerView = (RecyclerView) findViewById(R.id.dealList);
        dealsSwipe = (SwipeRefreshLayout) findViewById(R.id.dealSwipeRefresh);
        dealsSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);              //Set colors for swipeRefreshLayout
        adapter = new dealViewAdapter(context, getData());                                    //Create Adapter
        recyclerView.setAdapter(adapter);                                                           //Set Adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 1000);

        dealsSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {                                                     // Set Refresh Listener
                dealArray.clear();                                                                  //Clear data set

                //Set Date for current day (today)
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                today = c.getTime();

                //PARSE QUERY DEALS FROM THE INTERNET//
                ParseQuery <ParseObject> dealsQuery = new ParseQuery<ParseObject>("deals");
                dealsQuery.addAscendingOrder("startDate");
                dealsQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(final List<ParseObject> list, ParseException e) {
                        if (e != null){
                        }
                        else{
                            ParseObject.unpinAllInBackground("deals", new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    ParseObject.pinAllInBackground("deals", list);
                                }
                            });
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

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        adapter.updateDealData(dealArray);
                        adapter.notifyDataSetChanged();
                        dealsSwipe.setRefreshing(false);
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
        getMenuInflater().inflate(R.menu.menu_deal_list, menu);
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

    public static void setData(List<DealObject> array){
        dealArray = array;
    }

    public static List<DealObject> getData(){
        return dealArray;
    }

}
