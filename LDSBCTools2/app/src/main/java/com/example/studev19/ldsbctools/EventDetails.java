package com.example.studev19.ldsbctools;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adrian Lopez on 15/06/2015.
 */
public class EventDetails {

    private String eventName;
    private String description;
    private String location;
    private String displayTime;
    private Date startDate;
    private Date endDate;

    public String getName(){
        return eventName;
    }

    public void setName(String name){
        this.eventName = name;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getLocation(){
        return location;
    }

    public void setLocation(String location){
        this.location = location;
    }

    public Date getStartDate(){
        return startDate;
    }

    public Date getStartDateOnMST(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.startDate);
        calendar.add(Calendar.HOUR, 7);
        this.startDate = calendar.getTime();
        return startDate;
    }

    public void setStartDate(Date myDate){
        this.startDate = myDate;
    }

    public Date getEndDate(){
        return endDate;
    }

    public Date getEndDateOnMST(){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.endDate);
        calendar.add(Calendar.HOUR, 7);
        this.endDate = calendar.getTime();
        return endDate;
    }

    public void setEndDate(Date myDate){
        this.endDate = myDate;
    }

}
