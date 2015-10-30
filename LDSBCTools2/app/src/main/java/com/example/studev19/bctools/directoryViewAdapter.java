package com.example.studev19.bctools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by studev19 on 6/3/2015.
 */
public class directoryViewAdapter extends RecyclerView.Adapter<directoryViewAdapter.MyViewHolder> {

    public static List<DirectoryObject> directoryArray;                                             //List of Directory Objects
    private LayoutInflater inflater;                                                                //Declared Inflater
    private Context context;                                                                        //Declared Context
    private String iconChar;                                                                        //Char with first letter from service

    public directoryViewAdapter(Context context, List<DirectoryObject> directory) {                 //Initialized from DirectoryListActivity.java
        this.context = context;
        inflater = LayoutInflater.from(context);
        directoryArray = directory;
        Log.v("Directory Received", "directoryViewAdapter " + directoryArray.size());
    }

    public void updatedData(List<DirectoryObject> directory) {
        directoryArray = directory;
    }                                  //Updates data in directoryArray

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row, parent, false);                           //Inflates the custom_row.xml layout.
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {                               //Inflate rows with data
        DirectoryObject currentInfo = directoryArray.get(position);
        holder.title.setText(currentInfo.getName());                                                //Set value for Service Name
        iconChar = currentInfo.getName().substring(0, 1);                                           //Get first char from service name
        holder.icoChar.setText(iconChar);                                                           //Set char in icon
    }

    @Override
    public int getItemCount() {
        return directoryArray.size();                                                               //Return List Size
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView icoChar;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listNameText);                            //Find TextView for Service Name
            icoChar = (TextView) itemView.findViewById(R.id.txtDirectoryChar);                      //Find TextView for Service Char
        }

        @Override
        public void onClick(View v) {                                                               //Set onClick Listener
            directoryDetailedActivity.setServiceInfo(directoryArray.get(getPosition()));
            context.startActivity(new Intent(context, directoryDetailedActivity.class));
        }
    }

}