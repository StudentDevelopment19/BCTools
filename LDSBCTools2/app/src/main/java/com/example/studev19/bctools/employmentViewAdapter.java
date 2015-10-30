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
 * Created by studev19 on 10/7/2015.
 */
public class employmentViewAdapter extends RecyclerView.Adapter<employmentViewAdapter.MyViewHolder> {

    private static List<JobObject> employmentArray;                                                 //List of Job Objects
    private LayoutInflater inflater;                                                                //Inflater
    private Context context;                                                                        //Context

    public employmentViewAdapter(Context context, List<JobObject> list) {                           //Initialized from JobListActivity.java
        this.context = context;
        inflater = LayoutInflater.from(context);
        employmentArray = list;
        Log.v("Job List Received", "employmentViewAdapter " + employmentArray.size());
    }

    public void updateData(List<JobObject> list) {
        employmentArray = list;
    }                                              //Updates data in dealArray

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.employment_custom_row, parent, false);                //Inflates the deal_custom_row.xml layout.
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {                               //Inflate rows with data
        JobObject currentInfo = employmentArray.get(position);
        holder.position.setText(currentInfo.getJobPosition() + " @ ");                              //Set Job Position
        holder.company.setText(currentInfo.getJobCompany());                                        //Set Job Company
        holder.location.setText(currentInfo.getJobLocation());                                      //Set Job Location
    }

    @Override
    public int getItemCount() {
        return employmentArray.size();
    }                                                                 //Return List Size

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView position;
        TextView company;
        TextView location;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            position = (TextView) itemView.findViewById(R.id.txtJobPosition);                       //Find TextView for Position
            company = (TextView) itemView.findViewById(R.id.txtJobCompany);                         //Find TextView for Company
            location = (TextView) itemView.findViewById(R.id.txtJobLocation);                       //Find TextView for Location
        }

        @Override
        public void onClick(View v) {                                                               //Set onClickListener
            jobDetailedActivity.setJobInfo(employmentArray.get(getPosition()));
            context.startActivity(new Intent(context, jobDetailedActivity.class));
        }
    }
}
