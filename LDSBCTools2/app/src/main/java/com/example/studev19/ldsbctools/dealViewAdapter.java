package com.example.studev19.ldsbctools;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.deal_custom_row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(dealViewAdapter.MyViewHolder holder, int position) {
        DealObject currentInfo = dealArray.get(position);
        holder.dealTitle.setText(currentInfo.getDealTitle());
    }

    @Override
    public int getItemCount() {
        return dealArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView dealTitle;
        public MyViewHolder(View itemView){
            super(itemView);
            itemView.setOnClickListener(this);
            dealTitle = (TextView) itemView.findViewById(R.id.txtDealTitle);
        }
        @Override
        public void onClick(View v) {
            dealDetailedActivity.setDealInfo(dealArray.get(getPosition()));
            context.startActivity(new Intent(context, dealDetailedActivity.class));
        }
    }
}
