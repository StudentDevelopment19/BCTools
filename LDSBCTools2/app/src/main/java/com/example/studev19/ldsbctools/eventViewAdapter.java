package com.example.studev19.ldsbctools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

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
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(eventViewAdapter.MyViewHolder holder, int position) {
        EventDetails currentInfo = eventArray.get(position);
        holder.title.setText(currentInfo.getName());
    }

    @Override
    public int getItemCount() {
        return eventArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listNameText);
        }
    }
}
