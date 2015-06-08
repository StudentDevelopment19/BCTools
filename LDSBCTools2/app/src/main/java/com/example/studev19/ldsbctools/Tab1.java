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
 * Created by studev19 on 6/3/2015.
 */
public class Tab1 extends Fragment {

    private static RecyclerView recyclerView;
    private static directoryViewAdapter adapter;
    private static List<DirectoryObject> directory;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        View v = inflater.inflate(R.layout.tab_1, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.directoryList);
        adapter = new directoryViewAdapter(getActivity(), getData());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return v;
    }

    public static void setData(List<DirectoryObject> array){
        directory = array;
    }

    public static List<DirectoryObject> getData(){
        return directory;
    }

}