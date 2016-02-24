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

import java.util.List;

/**
 * Created by alejandrolopez on 2/3/16.
 */
public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.MyViewHolder> {

    public static List<NewsFeedObject> newsArray;
    private LayoutInflater inflater;
    private Context context;

    public NewsViewAdapter(Context mContext, List<NewsFeedObject>newsList){
        this.context = mContext;
        inflater = LayoutInflater.from(context);
        newsArray = newsList;
    }

    public void updatedNewsData(List<NewsFeedObject> news){
        newsArray = news;
    }


    @Override
    public NewsViewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.news_feed_row,parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder ;

    }

    @Override
    public void onBindViewHolder(final NewsViewAdapter.MyViewHolder holder, int position) {

        NewsFeedObject currentInfo = newsArray.get(position);
        holder.newsTitle.setText(currentInfo.getTitle());
        ParseFile imageFile = currentInfo.getImage();
        holder.newsImage.setParseFile(imageFile);                                         //Display ParseFile as image
        holder.newsImage.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                //The image is loaded and displayed
                int oldHeight = holder.newsImage.getHeight();
                int oldWidth = holder.newsImage.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });

    }

    @Override
    public int getItemCount() {
        return newsArray.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView newsTitle;
        ParseImageView newsImage;

        public MyViewHolder(View view){
            super(view);
            newsTitle = (TextView) view.findViewById(R.id.txtNewsTitle);
            newsImage = (ParseImageView) view.findViewById(R.id.imgNewsFeed);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        NewsFeedDetailActivity.setNewsInfo(newsArray.get(getPosition()));


            context.startActivity(new Intent(context, NewsFeedDetailActivity.class));




        }
    }
}
