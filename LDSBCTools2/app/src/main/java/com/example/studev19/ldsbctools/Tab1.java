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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;


/**
 * Created by studev19 on 6/3/2015.
 */
public class Tab1 extends Fragment {

    private static RecyclerView recyclerView;
    private static SwipeRefreshLayout swipeRefreshLayout;
    private static directoryViewAdapter adapter;
    private static List<DirectoryObject> directory;
    private static Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        context = getActivity();
        View v = inflater.inflate(R.layout.tab_1, container, false);                                //Find view
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefresh);                //Find Swipe Refresh Layout
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);      //Set colors for swipeRefreshLayout
        recyclerView = (RecyclerView) v.findViewById(R.id.directoryList);                           //Find Recycler View
        adapter = new directoryViewAdapter(getActivity(), getData());                               //Create Adapter
        Log.v("Directory Received", "Tab1 " + getData().size());
        recyclerView.setAdapter(adapter);                                                           //Set Adapter to RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        new Handler().postDelayed(new Runnable() {                                                  //Refresh the view after 1 second to show information from the beginning
            @Override
            public void run() {
                adapter.notifyDataSetChanged();                                                     //Notify that the data has changed
            }
        }, 1000);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {                                             // Set Refresh Listener

                directory.clear();                                                                  //Clear data set

                //PARSE QUERY FOR CONTACTS//                                                        //Re-run the parseQuery
                ParseQuery<ParseObject> dQuery = new ParseQuery<ParseObject>("serviceDirectory");
                dQuery.addAscendingOrder("serviceName");
                dQuery.findInBackground(new FindCallback<ParseObject>() {
                    @Override
                    public void done(List<ParseObject> list, ParseException e) {
                        if (e != null) {
                            Toast.makeText(context, "An error has occurred. \n" +
                                    "Please connect to the Internet and refresh this view", Toast.LENGTH_LONG).show();
                        } else for (ParseObject object : list) {
                            //Get data from Parse.com table
                            String contactName = object.getString("serviceName");
                            String contactDesc = object.getString("description");
                            String contactPhone = object.getString("phone");
                            String contactEmail = object.getString("email");
                            String contactLoc = object.getString("Location");
                            String contactSch = object.getString("hours");
                            String contactWeb = object.getString("website");
                            if (object.getString("website").isEmpty()){
                                contactWeb = "";
                            }
                            Log.v(contactName, contactWeb);

                            //Assign data to a DirectoryObject
                            DirectoryObject newObject = new DirectoryObject();
                            newObject.setName(contactName);
                            newObject.setDescription(contactDesc);
                            newObject.setPhone(contactPhone);
                            newObject.setEmail(contactEmail);
                            newObject.setLocation(contactLoc);
                            newObject.setHours(contactSch);
                            newObject.setWebSite(contactWeb);

                            //Add object to eventArray
                            directory.add(newObject);
                        }
                    }
                });

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {                                                   //Notify that data has changed
                        adapter.updatedData(directory);
                        adapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });

        return v;
    }

    public static void setData(List<DirectoryObject> array){
        directory = array;
    }

    public static List<DirectoryObject> getData(){
        return directory;
    }

}