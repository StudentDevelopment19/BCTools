package com.example.studev19.ldsbctools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab2 extends Fragment {

    private static RecyclerView recyclerView;
    private static eventViewAdapter adapter;
    private static List<EventDetails> eventArray;
    private TextView noData;
    private static boolean connectionStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_2, container, false);
        noData = (TextView) v.findViewById(R.id.txtDataNotFoundForEvents);
        recyclerView = (RecyclerView) v.findViewById(R.id.eventList);
        adapter = new eventViewAdapter(getActivity(), getData());
        Log.v("Events Received", "Tab2 " + getData().size());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
