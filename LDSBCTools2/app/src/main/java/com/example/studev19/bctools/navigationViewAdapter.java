package com.example.studev19.bctools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by studev19 on 9/14/2015.
 */
public class navigationViewAdapter extends RecyclerView.Adapter<navigationViewAdapter.MyViewHolder> {

    private static List<NavigationObject> navList = Collections.emptyList();                        //List of Navigation Objects
    public Context context;
    private LayoutInflater inflater;

    public navigationViewAdapter(Context context, List<NavigationObject> navigationList) {          //Initialize Inflater and Navigation Object List
        this.context = context;
        inflater = LayoutInflater.from(context);
        navList = navigationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.nav_custom_row, parent, false);                       //Inflates the nav_custom_row.xml layout.
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {                               //Inflate rows with data
        NavigationObject currentData = navList.get(position);
        holder.title.setText(currentData.getName());                                                //Set value for Item Name
        holder.icon.setImageResource(currentData.getIcon());                                        //Set value for Item Icon
    }

    @Override
    public int getItemCount() {
        return navList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.txtNavItem);                                  //Find TextView for Name
            icon = (ImageView) view.findViewById(R.id.icoNavItem);                                  //Find ImageView for Icon
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {                                                               //onClickListener
            //Open new activity by position on Item List
            switch (getPosition()) {
                case 0:
                    context.startActivity(new Intent(context, MainActivity.class));                 //Open MainActivity "Home"
                    break;
                case 1:
                    context.startActivity(new Intent(context, DirectoryListActivity.class));        //Open DirectoryListActivity "Directory"
                    break;
                case 2:
                    Intent events = new Intent(context, EventListActivity.class);                   //Open EventListActivity "Events"
                    events.putExtra("from", "All Events");                                          //Pass intent with information where the activity was opened from
                    context.startActivity(events);
                    break;
                case 3:
                    context.startActivity(new Intent(context, NewsListActivity.class));
                    break;
                case 4:
                    context.startActivity(new Intent(context, BCSAActivity.class));                 //Open BCSAActivity "BCSA"
                    break;
                case 5:
                    context.startActivity(new Intent(context, DealListActivity.class));             //Open DealListActivity "Deals"
                    break;
                case 6:
                    context.startActivity(new Intent(context, JobServicesActivity.class));          //Open JobServicesActivity "Career Services"
                    break;
                case 7:
                    Intent feedback = new Intent(context, FeedbackActivity.class);
                    feedback.putExtra("feedback", "https://docs.google.com/a/ldsbc.edu/forms/d/1PGgxSl2w9vsp4cq5jxii5Y6AOgCfCJ75o527xOrXD4U/viewform");
                    context.startActivity(feedback);                                                //Open FeedbackActivity "Feedback"
                    break;
            }
        }
    }

}
