package com.example.studev19.ldsbctools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Adrian Lopez on 15/06/2015.
 */
public class eventViewAdapter extends RecyclerView.Adapter<eventViewAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    public static List<EventDetails> eventArray;
    private Context context;

    public eventViewAdapter(Context context, List<EventDetails> eventList){
        this.context = context;
        inflater = LayoutInflater.from(context);
        eventArray = eventList;
        Log.v("Events Received", "eventViewAdapter " + eventArray.size());
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.event_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(eventViewAdapter.MyViewHolder holder, int position) {
        EventDetails currentInfo = eventArray.get(position);
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, hh:mm a");
        df.setTimeZone(TimeZone.getTimeZone("MST"));
        holder.eventName.setText(currentInfo.getName());
        holder.eventDate.setText(df.format(currentInfo.getStartDate()));
    }

    @Override
    public int getItemCount() {
        Log.v("Events Counted", "eventViewAdapter " + eventArray.size());
        return eventArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView eventName;
        TextView eventDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            eventName = (TextView) itemView.findViewById(R.id.txtEventListName);
            eventDate = (TextView) itemView.findViewById(R.id.txtEventListTime);
        }

        @Override
        public void onClick(View v) {
            eventDetailedActivity.setEventInfo(eventArray.get(getPosition()));
            context.startActivity(new Intent(context, eventDetailedActivity.class));
        }
    }

}
