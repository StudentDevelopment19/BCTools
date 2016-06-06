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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.random;

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
                "Events","News Feed", "BCSA",
                "Career Services", "Feedback","Brightspace","BCCafe"};
        int[] images = {R.drawable.ic_home_gray, R.drawable.ic_phone_gray,                          //Array of Item Icons on Navigation menu
                R.drawable.ic_event_gray, R.drawable.ic_news_gray , R.drawable.ic_explore_gray,
                R.drawable.ic_company_gray, R.drawable.ic_feedback_gray,
                R.drawable.ic_brightspace_gray, R.drawable.ic_bccafe_gray };
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
        //View tempView = null;
        TextView myTextview = (TextView) layout.findViewById(R.id.quoteTextView);
            int randomNumber = (int)(Math.random() * 35);
            switch (randomNumber){
                case 0: myTextview.setText(R.string.q0);
                    break;
                case 1: myTextview.setText(R.string.q1);
                    break;
                case 2: myTextview.setText(R.string.q2);
                    break;
                case 3: myTextview.setText(R.string.q3);
                    break;
                case 4: myTextview.setText(R.string.q4);
                    break;
                case 5: myTextview.setText(R.string.q5);
                    break;
                case 6: myTextview.setText(R.string.q6);
                    break;
                case 7: myTextview.setText(R.string.q7);
                    break;
                case 8: myTextview.setText(R.string.q8);
                    break;
                case 9: myTextview.setText(R.string.q9);
                    break;
                case 10: myTextview.setText(R.string.q10);
                    break;
                case 11: myTextview.setText(R.string.q11);
                    break;
                case 12: myTextview.setText(R.string.q12);
                    break;
                case 13: myTextview.setText(R.string.q13);
                    break;
                case 14: myTextview.setText(R.string.q14);
                    break;
                case 15: myTextview.setText(R.string.q15);
                    break;
                case 16: myTextview.setText(R.string.q16);
                    break;
                case 17: myTextview.setText(R.string.q17);
                    break;
                case 18: myTextview.setText(R.string.q18);
                    break;
                case 19: myTextview.setText(R.string.q19);
                    break;
                case 20: myTextview.setText(R.string.q20);
                    break;
                case 21: myTextview.setText(R.string.q21);
                    break;
                case 22: myTextview.setText(R.string.q22);
                    break;
                case 23: myTextview.setText(R.string.q23);
                    break;
                case 24: myTextview.setText(R.string.q24);
                    break;
                case 25: myTextview.setText(R.string.q25);
                    break;
                case 26: myTextview.setText(R.string.q26);
                    break;
                case 27: myTextview.setText(R.string.q27);
                    break;
                case 28: myTextview.setText(R.string.q28);
                    break;
                case 29: myTextview.setText(R.string.q29);
                    break;
                case 30: myTextview.setText(R.string.q30);
                    break;
                case 31: myTextview.setText(R.string.q31);
                    break;
                case 32: myTextview.setText(R.string.q32);
                    break;
                case 33: myTextview.setText(R.string.q33);
                    break;
                case 34: myTextview.setText(R.string.q34);
                    break;

            }





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
