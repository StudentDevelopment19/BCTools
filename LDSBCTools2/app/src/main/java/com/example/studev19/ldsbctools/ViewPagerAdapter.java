package com.example.studev19.ldsbctools;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by studev19 on 6/3/2015.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; //This section stores the Titles of the tabs
    int NumbOfTabs; //Store the number of Tabs

    //Pass values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {        //Return first tab "Directory"
            Tab1 tab1 = new Tab1();
            return tab1;
        }
        else if (position == 1){    //Return second tab "Events"
            Tab2 tab2 = new Tab2();
            return tab2;
        }
        else if (position == 2) {   //Return third tab "BCSA"
            Tab3 tab3 = new Tab3();
            return tab3;
        }
        else if (position == 3) {   // Return forth tab "Food"
            Tab4 tab4 = new Tab4();
            return tab4;
        }
        else{                       // Return fifth tab "Feedback"
            Tab5 tab5 = new Tab5();
            return tab5;
        }
    }

    //This method returns the titles for the tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    //This method return the Number of Tabs for the tab strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}