package com.example.studev19.bctools;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab2 extends Fragment {

    private static RecyclerView recyclerView;
    private static SwipeRefreshLayout eventSwipe;
    private static eventViewAdapter adapter;
    private static List<EventDetails> eventArray;
    private TextView noData;
    private static boolean connectionStatus;
    private static Context context;
    private static Date today;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        View v = inflater.inflate(R.layout.tab_2, container, false);                                //Find view
        noData = (TextView) v.findViewById(R.id.txtDataNotFoundForEvents);
        recyclerView = (RecyclerView) v.findViewById(R.id.eventList);                               //Find Recycler View
        eventSwipe = (SwipeRefreshLayout) v.findViewById(R.id.eventSwipeRefresh);                   //Find Swipe Refresh Layout
        eventSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);              //Set colors for swipeRefreshLayout
        adapter = new eventViewAdapter(getActivity(), getData());                                   //Create Adapter
        Log.v("Events Received", "Tab2 " + getData().size());
        recyclerView.setAdapter(adapter);                                                           //Set Adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new Handler().postDelayed(new Runnable() {                                                  //Refresh the view after 1 second to show information from the beginning
            @Override
            public void run() {
                adapter.notifyDataSetChanged();
            }
        }, 1000);

        eventSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {                                                     // Set Refresh Listener
                eventArray.clear();                                                                 //Clear data set

                //Set Date for current day (today)
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                today = c.getTime();

                //PARSE QUERY FOR EVENTS//                                                          //Re-run the parseQuery
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

        return v;
    }

    public static void setData(List<EventDetails> array) {
        eventArray = array;
    }

    public static List<EventDetails> getData() {
        return eventArray;
    }

    public static void setConnectionStatus(boolean status) {
        connectionStatus = status;
    }

}
