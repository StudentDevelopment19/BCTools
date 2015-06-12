package com.example.studev19.ldsbctools;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by studev19 on 6/12/2015.
 */
public class parseApplicationSetup extends Application{
    @Override
    public void onCreate(){
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, getString(R.string.parseAppID), getString(R.string.parseClientID));
    }
}
