package com.example.studev19.ldsbctools;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

/**
 * Created by studev19 on 6/1/2015.
 */
public class DirectoryApplication extends Application{
    public void onCreate(){
        //PARSE DECLARATION//
        //Register subclass "serviceDirectory"
        //ParseObject.registerSubclass(serviceDirectory.class);

        // Enable Local Datastore
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "y4mrxs1MVgSv9qv5L79fui8d4ElGs7iQH6mJOWha", "9Y6aanyHQdDjxzpx6QYsBYxgMUb8tfFsB95caB3c");
    }
}
