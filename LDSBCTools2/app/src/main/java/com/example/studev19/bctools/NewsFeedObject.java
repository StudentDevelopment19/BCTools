package com.example.studev19.bctools;
import com.parse.ParseFile;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by alejandrolopez on 2/1/16.
 */
public class NewsFeedObject {

    private String mTitle;
    private ParseFile mImage;
    private String mDescription;
    private String mWebsite;
    private Boolean mPublish;
    private Date mPostAt;

    public String getTitle(){
        return mTitle;
    }
    public void setTitle(String title){
        mTitle = title;
    }
    public ParseFile getImage(){
        return mImage;
    }
    public void setImage(ParseFile image){
        mImage = image;
    }
    public String getDescription(){
        return mDescription;
    }
    public void setDescription(String description){
        mDescription = description;
    }
    public String getWebsite(){
        return mWebsite;
    }
    public void setWebsite(String website){
        mWebsite = website;
    }
    public Boolean getPublish(){
        return mPublish;
    }
    public void setPublish(Boolean publish){
        mPublish = publish;
    }
    public Date getPostAt(){
        return mPostAt;
    }
    public void setPostAt(Date postAt){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(postAt);
        calendar.add(Calendar.HOUR, 6);
        mPostAt = calendar.getTime();


    }



}
