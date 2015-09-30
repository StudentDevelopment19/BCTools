package com.example.studev19.bctools;

import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by studev19 on 9/14/2015.
 */
public class navigationViewAdapter extends RecyclerView.Adapter<navigationViewAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    private static List<NavigationObject> navList =  Collections.emptyList();
    private DrawerLayout mDrawerLayout;
    public Context context;

    public navigationViewAdapter(Context context, List<NavigationObject> navigationList){
        Log.v("NavDrawer", "navViewAdapter calls values");
        this.context = context;
        inflater = LayoutInflater.from(context);
        navList = navigationList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("NavDrawer", "navViewAdapter onCreateViewHolder happens");
        View view = inflater.inflate(R.layout.nav_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.v("NavDrawer", "navViewAdapter onBindViewHolder happens");
        NavigationObject currentData = navList.get(position);
        holder.title.setText(currentData.getName());
        holder.icon.setImageResource(currentData.getIcon());
        Log.v("NavDrawer", "navViewAdapter onBindViewHolder happened");
    }

    @Override
    public int getItemCount() {
        Log.v("NavDrawer", "navViewAdapter navList items are counted");
        return navList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView icon;
        public  MyViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.txtNavItem);
            icon = (ImageView) view.findViewById(R.id.icoNavItem);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (getPosition()){
                case 0: context.startActivity(new Intent(context, MainActivity.class)); break;
                case 1: context.startActivity(new Intent(context, DirectoryListActivity.class)); break;
                case 2: context.startActivity(new Intent(context, EventListActivity.class));break;
                case 3: context.startActivity(new Intent(context, BCSAActivity.class));break;
                case 4: context.startActivity(new Intent(context, DealListActivity.class));break;
                case 5: context.startActivity(new Intent(context, FeedbackActivity.class));break;
                case 6: context.startActivity(new Intent(context, JobServicesActivity.class));break;
            }
        }
    }

}
