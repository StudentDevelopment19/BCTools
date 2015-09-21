package com.example.studev19.bctools;

import android.media.Image;

/**
 * Created by studev19 on 9/14/2015.
 */
public class NavigationObject {

    private String itemName;

    private int itemIcon;

    public void setName(String name){
        this.itemName = name;
    }

    public String getName(){
        return itemName;
    }

    public void setIcon(int image){
        this.itemIcon = image;
    }

    public int getIcon(){
        return itemIcon;
    }

}
