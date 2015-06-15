package com.example.studev19.ldsbctools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab2 extends Fragment {

    private static RecyclerView recyclerView;
    private static eventViewAdapter adapter;
    private static List<EventDetails> eventArray;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.tab_2, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.eventList);
        adapter = new eventViewAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public static void setData(List<EventDetails> array){
        eventArray = array;
    }

    public static List<EventDetails> getData(){
        return eventArray;
    }

}
