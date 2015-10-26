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

    private static List<JobObject> employmentArray;
    private LayoutInflater inflater;
    private Context context;

    public employmentViewAdapter(Context context, List<JobObject> list) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        employmentArray = list;
        Log.v("Job List Received", "employmentViewAdapter " + employmentArray.size());
    }

    public void updateData(List<JobObject> list) {
        employmentArray = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.employment_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        JobObject currentInfo = employmentArray.get(position);
        holder.position.setText(currentInfo.getJobPosition() + " @ ");
        holder.company.setText(currentInfo.getJobCompany());
        holder.location.setText(currentInfo.getJobLocation());
    }

    @Override
    public int getItemCount() {
        return employmentArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView position;
        TextView company;
        TextView location;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            position = (TextView) itemView.findViewById(R.id.txtJobPosition);
            company = (TextView) itemView.findViewById(R.id.txtJobCompany);
            location = (TextView) itemView.findViewById(R.id.txtJobLocation);
        }

        @Override
        public void onClick(View v) {
            jobDetailedActivity.setJobInfo(employmentArray.get(getPosition()));
            context.startActivity(new Intent(context, jobDetailedActivity.class));
        }
    }
}
