package com.example.studev19.ldsbctools;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
 * Created by studev19 on 7/8/2015.
 */
public class dealViewAdapter extends RecyclerView.Adapter<dealViewAdapter.MyViewHolder>{

    private LayoutInflater inflater;
    public static List<DealObject> dealArray;
    private Context context;

    public dealViewAdapter(Context context, List<DealObject> dealList){
        this.context = context;
        inflater = LayoutInflater.from(context);
        dealArray = dealList;
        Log.v("Deals Received", "dealViewAdapter " + dealArray.size());
    }

    public void updateDealData(List<DealObject> deals){
        dealArray = deals;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.deal_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(final dealViewAdapter.MyViewHolder holder, int position) {

        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("MST"));

        DealObject currentInfo = dealArray.get(position);
        holder.dealDesc1.setText(currentInfo.getDealDesciption() + " @ " + currentInfo.getDealCompany());
        holder.dealDesc2.setText("Valid through " + dateFormat.format(currentInfo.getDealStartDate()) + " to " + dateFormat.format(currentInfo.getDealEndDate()));
        ParseFile imageFile = currentInfo.getDealImage();
        holder.parseImageView.setParseFile(imageFile);
        holder.parseImageView.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                //The image is loaded and displayed
                int oldHeight = holder.parseImageView.getHeight();
                int oldWidth = holder.parseImageView.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });
    }

    @Override
    public int getItemCount() {
        Log.v("Deals Counted", "dealViewAdapter " + dealArray.size());
        return dealArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dealDesc1, dealDesc2;
        ParseImageView parseImageView;
        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            dealDesc1 = (TextView) itemView.findViewById(R.id.txtDealRowDesc1);
            dealDesc2 = (TextView) itemView.findViewById(R.id.txtDealRowDesc2);
            parseImageView = (ParseImageView) itemView.findViewById(R.id.imgDealRowImage);
        }
        @Override
        public void onClick(View v) {
            dealDetailedActivity.setDealInfo(dealArray.get(getPosition()));
            context.startActivity(new Intent(context, dealDetailedActivity.class));
        }
    }

}
