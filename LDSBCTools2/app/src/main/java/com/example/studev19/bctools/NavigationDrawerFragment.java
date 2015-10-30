package com.example.studev19.bctools;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

//import android.app.Fragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawerFragment extends Fragment {

    public static final String PREF_FILE_NAME = "testPref";
    public static final String KEY_USER_LEARNED_DRAWER = "user_learned_drawer";

    private static ActionBarDrawerToggle mDrawerToggle;
    private static DrawerLayout mDrawerLayout;
    private static boolean mUserLearnedDrawer;
    private static boolean mFromSavedInstanceState;
    private static navigationViewAdapter adapter;
    private View containerView;
    private RecyclerView navRecycler;

    public NavigationDrawerFragment() {
        // Required empty public constructor
    }

    public static List<NavigationObject> getData() {
        List<NavigationObject> data = new ArrayList<>();
        String[] titles = {"Home", "Directory",                                                     //Array of Item Titles on Navigation menu
                "Events", "BCSA", "Deals",
                "Career Services", "Feedback"};
        int[] images = {R.drawable.ic_home_gray, R.drawable.ic_phone_gray,                          //Array of Item Icons on Navigation menu
                R.drawable.ic_event_gray, R.drawable.ic_explore_gray, R.drawable.ic_shopping_gray,
                R.drawable.ic_company_gray, R.drawable.ic_feedback_gray};
        for (int i = 0; i < titles.length; i++) {                                                   //Add Titles and Icons to a NavigationObject array
            NavigationObject current = new NavigationObject();
            current.setName(titles[i]);
            current.setIcon(images[i]);
            data.add(current);
        }
        return data;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);      //Layout and views come from fragment_navigation_drawer.xml
        navRecycler = (RecyclerView) layout.findViewById(R.id.navRecyclerView);                     //Find view for RecyclerView
        adapter = new navigationViewAdapter(getActivity(), getData());                              //Initialize Adapter for RecyclerView
        navRecycler.setAdapter(adapter);                                                            //Set Adapter to RecyclerView
        navRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));                       //Give layout for RecyclerView
        return layout;
    }

    public void setUp(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {           //Setup NavDrawer from activities
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

        };
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            mDrawerLayout.openDrawer(containerView);
        }
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

    }

}
