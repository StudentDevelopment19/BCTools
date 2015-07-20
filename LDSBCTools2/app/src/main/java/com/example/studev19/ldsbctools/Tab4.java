package com.example.studev19.ldsbctools;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab4 extends Fragment {

    private static RecyclerView recyclerView;
    private static dealViewAdapter adapter;
    private static List<DealObject> dealArray;
    private TextView noData;
    private static boolean connectionStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_4, container, false);
        noData = (TextView) v.findViewById(R.id.txtDataNotFoundForDeals);
        recyclerView = (RecyclerView) v.findViewById(R.id.dealList);
        adapter = new dealViewAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        if (dealArray.isEmpty() == true){
            recyclerView.setVisibility(View.GONE);
            if (connectionStatus == true){
                noData.setText("There are no deals to show right now. Try again latter by refreshing the view");
            }
        }
        else {
            noData.setVisibility(View.GONE);
        }
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
