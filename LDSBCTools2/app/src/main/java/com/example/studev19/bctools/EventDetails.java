package com.example.studev19.bctools;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Adrian Lopez on 15/06/2015.
 */
public class EventDetails {

    private String eventName;
    private String description;
    private String location;
    private Date startDate;
    private Date startDateCalendar;
    private Date endDate;
    private Date endDateCalendar;

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

    public void setStartDate(Date myDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.HOUR, 7);
        this.startDate = calendar.getTime();
    }

    public Date getStartDateCalendar(){
        return startDateCalendar;
    }

    public void setStartDateCalendar(Date myDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.HOUR, 6);
        this.startDateCalendar = calendar.getTime();
    }

    public Date getEndDate(){
        return endDate;
    }

    public void setEndDate(Date myDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.HOUR, 7);
        this.endDate = calendar.getTime();
    }

    public Date getEndDateCalendar(){
        return endDateCalendar;
    }

    public void setEndDateCalendar(Date myDate){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(myDate);
        calendar.add(Calendar.HOUR, 6);
        this.endDateCalendar = calendar.getTime();
    }

}
