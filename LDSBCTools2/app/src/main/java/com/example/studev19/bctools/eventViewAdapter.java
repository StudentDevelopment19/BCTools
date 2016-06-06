package com.example.studev19.bctools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by Adrian Lopez on 15/06/2015.
 */
public class eventViewAdapter extends RecyclerView.Adapter<eventViewAdapter.MyViewHolder> {

    public static List<EventDetails> eventArray;                                                    //List of Events Objects
    private LayoutInflater inflater;                                                                //Declared Inflater
    private Context context;                                                                        //Declared Context

    public eventViewAdapter(Context context, List<EventDetails> eventList) {                        //Initialized from EventListActivity.java
        this.context = context;
        inflater = LayoutInflater.from(context);
        eventArray = eventList;
    }

    public void updatedEventData(List<EventDetails> events) {                                       //Updates data in eventArray
        eventArray = events;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.event_custom_row, parent, false);                     //Inflates the event_custom_row.xml layout.
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final eventViewAdapter.MyViewHolder holder, int position) {        //Inflate rows with data
        EventDetails currentInfo = eventArray.get(position);
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, hh:mm a");                              //Set date format
        df.setTimeZone(TimeZone.getTimeZone("MST"));                                                //Set time zone on MST
        holder.eventName.setText(currentInfo.getName());                                            //Set value for Event Name
        holder.eventDate.setText(df.format(currentInfo.getStartDate()));                            //Set value for Event Time
        ParseFile imageFile = currentInfo.getEventImage();                                          //Get ParseFile
        holder.eventParseImageView.setParseFile(imageFile);                                         //Display ParseFile as image
        holder.eventParseImageView.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                //The image is loaded and displayed
                int oldHeight = holder.eventParseImageView.getHeight();
                int oldWidth = holder.eventParseImageView.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v("Events Counted", "eventViewAdapter " + eventArray.size());
        return eventArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView eventName;
        TextView eventDate;
        ParseImageView eventParseImageView;

        public MyViewHolder(View itemView) {
            super(itemView);
            eventName = (TextView) itemView.findViewById(R.id.txtEventListName);                    //Find TextView for Event Name
            eventDate = (TextView) itemView.findViewById(R.id.txtEventListTime);                    //Find TextView for Event Time
            eventParseImageView = (ParseImageView) itemView.findViewById(R.id.imgEventRowImage);    //Find ParseImageView for Event Image
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {                                                               //Set onClickListener
            eventDetailedActivity.setEventInfo(eventArray.get(getPosition()));
            context.startActivity(new Intent(context, eventDetailedActivity.class));
        }
    }

}
