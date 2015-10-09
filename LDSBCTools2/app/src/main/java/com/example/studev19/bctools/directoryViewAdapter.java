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

    public static List<DirectoryObject> directoryArray;
    private LayoutInflater inflater;
    private Context context;
    private String iconChar;

    public directoryViewAdapter(Context context, List<DirectoryObject> directory) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        directoryArray = directory;
        Log.v("Directory Received", "directoryViewAdapter " + directoryArray.size());
    }

    public void updatedData(List<DirectoryObject> directory) {
        directoryArray = directory;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        DirectoryObject currentInfo = directoryArray.get(position);
        holder.title.setText(currentInfo.getName());                                                //Set value for Service Name
        iconChar = currentInfo.getName().substring(0, 1);
        holder.icoChar.setText(iconChar);
    }

    @Override
    public int getItemCount() {
        Log.v("Directory Counted", "directoryViewAdapter " + directoryArray.size());
        return directoryArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        TextView icoChar;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            title = (TextView) itemView.findViewById(R.id.listNameText);                            //Find TextView for Service Name
            icoChar = (TextView) itemView.findViewById(R.id.txtDirectoryChar);
        }

        @Override
        public void onClick(View v) {                                                               //Set onClick Listener
            directoryDetailedActivity.setServiceInfo(directoryArray.get(getPosition()));
            context.startActivity(new Intent(context, directoryDetailedActivity.class));
        }
    }

}