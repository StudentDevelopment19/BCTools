package com.example.studev19.ldsbctools;

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
        View v = inflater.inflate(R.layout.tab_2, container, false);
        eventSwipe = (SwipeRefreshLayout) v.findViewById(R.id.eventSwipeRefresh);
        eventSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);
        noData = (TextView) v.findViewById(R.id.txtDataNotFoundForEvents);
        recyclerView = (RecyclerView) v.findViewById(R.id.eventList);
        adapter = new eventViewAdapter(getActivity(), getData());
        Log.v("Events Received", "Tab2 " + getData().size());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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

                //PARSE QUERY FOR EVENTS//
                ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("events");
                query.addAscendingOrder("startDate");
                query.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null) {
                            Toast.makeText(context, "An error has occurred", Toast.LENGTH_LONG).show();
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


        /*if (this.getConnectionStatus() == false){
            recyclerView.setVisibility(View.GONE);
        }

        else if (this.getConnectionStatus() == true && eventArray.isEmpty() == true){
            recyclerView.setVisibility(View.GONE);
            noData.setText("There are no upcoming events to show right now. Try again latter by refreshing the view");
        }
        else if (this.getConnectionStatus() == true && eventArray.isEmpty() == false){
            noData.setVisibility(View.GONE);
        }*/
        return v;
    }

    public static void setData(List<EventDetails> array){
        eventArray = array;
    }

    public static List<EventDetails> getData(){
        return eventArray;
    }

    public static void setConnectionStatus(boolean status){
        connectionStatus = status;
    }

    public boolean getConnectionStatus(){
        return connectionStatus;
    }

}
