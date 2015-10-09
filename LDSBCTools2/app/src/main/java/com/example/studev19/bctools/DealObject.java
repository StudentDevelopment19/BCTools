package com.example.studev19.bctools;

import com.parse.ParseFile;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by studev19 on 7/8/2015.
 */
public class DealObject {
    private String dealTitle;
    private String dealDescription;
    private String dealCompany;
    private String dealAddress;
    private Date dealStartDate;
    private Date dealEndDate;
    private ParseFile dealImage;

    public String getDealTitle() {
        return dealTitle;
    }

    public void setDealTitle(String title) {
        this.dealTitle = title;
    }

    public String getDealDescription() {
        return dealDescription;
    }

    public void setDealDescription(String description) {
        this.dealDescription = description;
    }

    public String getDealCompany() {
        return dealCompany;
    }

    public void setDealCompany(String company) {
        this.dealCompany = company;
    }

    public String getDealAddress() {
        return dealAddress;
    }

    public void setDealAddress(String address) {
        this.dealAddress = address;
    }

    public Date getDealStartDate() {
        return dealStartDate;
    }

    public void setDealStartDate(Date startDate) {
        this.dealStartDate = startDate;
    }

    public Date getDealStartDateOnMST() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dealStartDate);
        calendar.add(Calendar.HOUR, 7);
        this.dealStartDate = calendar.getTime();
        return dealStartDate;
    }

    public Date getDealEndDate() {
        return dealEndDate;
    }

    public void setDealEndDate(Date endDate) {
        this.dealEndDate = endDate;
    }

    public Date getEndDateOnMTS() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.dealEndDate);
        calendar.add(Calendar.HOUR, 7);
        this.dealEndDate = calendar.getTime();
        return dealEndDate;
    }

    public ParseFile getDealImage() {
        return dealImage;
    }

    public void setDealImage(ParseFile image) {
        this.dealImage = image;
    }

}
