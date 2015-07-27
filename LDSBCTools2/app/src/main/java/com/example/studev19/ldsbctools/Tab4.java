package com.example.studev19.ldsbctools;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
public class Tab4 extends Fragment {

    private static RecyclerView recyclerView;
    private static SwipeRefreshLayout dealsSwipe;
    private static dealViewAdapter adapter;
    private static List<DealObject> dealArray;
    private TextView noData;
    private static boolean connectionStatus;
    private static Context context;
    private static Date today;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        context = getActivity();
        View v = inflater.inflate(R.layout.tab_4, container, false);
        noData = (TextView) v.findViewById(R.id.txtDataNotFoundForDeals);
        recyclerView = (RecyclerView) v.findViewById(R.id.dealList);
        dealsSwipe = (SwipeRefreshLayout) v.findViewById(R.id.dealSwipeRefresh);
        dealsSwipe.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);
        adapter = new dealViewAdapter(getActivity(), getData());
        Log.v("Deals Received", "Tab4 " + getData().size());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        dealsSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                dealArray.clear();

                //Set Date for current day (today)
                Calendar c = Calendar.getInstance();
                c.set(Calendar.HOUR, 0);
                c.set(Calendar.MINUTE, 0);
                c.set(Calendar.SECOND, 0);
                today = c.getTime();

                //PARSE QUERY FOR DEALS//
                ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("deals");
                query2.addAscendingOrder("startDate");
                query2.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null) {
                            Toast.makeText(context, "An error has occurred", Toast.LENGTH_LONG).show();
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
                                newObject.setDealDesciption(dealDesc);
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

        /*if (dealArray.isEmpty() == true){
            recyclerView.setVisibility(View.GONE);
            if (connectionStatus == true){
                noData.setText("There are no deals to show right now. Try again latter by refreshing the view");
            }
        }
        else {
            noData.setVisibility(View.GONE);
        }*/
        return v;
    }

    public static void setData(List<DealObject> array){
        dealArray = array;
    }

    public static List<DealObject> getData(){
        return dealArray;
    }

    public static void setConnectionStatus(boolean status){
        connectionStatus = status;
    }

}
