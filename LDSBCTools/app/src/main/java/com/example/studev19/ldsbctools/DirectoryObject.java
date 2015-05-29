package com.example.studev19.ldsbctools;



/**
 * Created by studev19 on 5/18/2015.
 */
public class DirectoryObject {

    String oName;
    int oPhone;
    String oDescription;
    String oLocation;

    public String getName(){
        return oName;
    }

    public void setName(String name){
        this.oName = name;
    }

    public int getPhone(){
        return oPhone;
    }

    public void setPhone(int phone){
        this.oPhone = phone;
    }

    public String getDescription(){
        return oDescription;
    }

    public void setDescription(String description){
        this.oDescription = description;
    }

    public String getLocation(){
        return oLocation;
    }

    public void setLocation(String location){
        this.oDescription = location;
    }

}
