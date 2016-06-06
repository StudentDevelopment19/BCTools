package com.example.studev19.bctools;

import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studev19.bctools.R;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

public class NewsFeedDetailActivity extends ActionBarActivity {
    private ParseFile image;
    private TextView title;
    private TextView description;
    private TextView internetButton;
    private ImageView webIcon;
    private String hyperlink;

    private static NewsFeedObject currentInfo;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_feed_detail);

        //Creating and Setting the tool Bar
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        //----THE FOLLOWING SECTION FILLS THE INFORMATION OF THE DETAILED VIEW-----//

        //Settin∑g Image from Parse

        final ParseImageView newsImage = (ParseImageView) findViewById(R.id.imgNews);
        image = currentInfo.getImage();
        newsImage.setParseFile(image);
        newsImage.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {                                                      //This process makes the image visible from app
                //The image is loaded and displayed
                int oldHeight = newsImage.getHeight();
                int oldWidth = newsImage.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });

        //Setting Title
        title = (TextView) findViewById(R.id.txtNewsTitle);
        title.setText(currentInfo.getTitle());

        //Setting Description
        description = (TextView) findViewById(R.id.txtNewsDescription);
        description.setText(currentInfo.getDescription());

        //Setting WebSite HyperLink

        internetButton = (TextView) findViewById(R.id.txtNewsWeb);
        webIcon = (ImageView) findViewById(R.id.icoNewsWeb);
        if (currentInfo.getWebsite() == ""){
            internetButton.setVisibility(View.GONE);
            webIcon.setVisibility(View.GONE);
        }
        else if (currentInfo.getWebsite() != ""){
            hyperlink = "<a href='" + currentInfo.getWebsite() + "'>" + "Website" + "</a>";
            internetButton.setClickable(true);
            internetButton.setMovementMethod(LinkMovementMethod.getInstance());
            internetButton.setText(Html.fromHtml(hyperlink));
        }


    }



    public static void setNewsInfo(NewsFeedObject newsFeedObject){
        currentInfo = newsFeedObject;
    }


}
