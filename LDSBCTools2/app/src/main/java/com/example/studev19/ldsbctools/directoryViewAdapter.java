package com.example.studev19.ldsbctools;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

/**
 * Created by studev19 on 6/3/2015.
 */
public class directoryViewAdapter extends RecyclerView.Adapter<directoryViewAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    public static List<DirectoryObject> directoryArray;

    public directoryViewAdapter(Context context, List<DirectoryObject> directory){
        inflater = LayoutInflater.from(context);
        this.directoryArray = directory;
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
        holder.title.setText(currentInfo.getName());
    }

    @Override
    public int getItemCount() {
        return directoryArray.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.listNameText);
        }
    }

}