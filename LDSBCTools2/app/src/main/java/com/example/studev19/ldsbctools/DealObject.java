package com.example.studev19.ldsbctools;

import com.parse.ParseClassName;
import com.parse.ParseObject;

/**
 * Created by studev19 on 7/8/2015.
 */
@ParseClassName("Deals")
public class DealObject extends ParseObject {
    public DealObject(){
        //Default constructor
    }

    public String getTitle(){
        return getString("Title");
    }

    public void setTitle(String title){
        put("Title", title);
    }

    public String getDescription(){
        return getString("Description");
    }

    public void setDescriptioin(String description){

    }

}
