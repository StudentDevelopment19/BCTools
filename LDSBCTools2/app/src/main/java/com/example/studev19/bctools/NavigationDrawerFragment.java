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
import android.util.Log;
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
        Log.v("NavDrawer", "NavDrawerFragment getData happens");
        List<NavigationObject> data = new ArrayList<>();
        String[] titles = {"Home", "Directory", "Events", "BCSA", "Deals", "Feedback", "Employment Services"};
        int[] images = {R.drawable.ic_contact, R.drawable.ic_phone_custom, R.drawable.ic_event, R.drawable.ic_explore, R.drawable.ic_shopping, R.drawable.ic_feedback, R.drawable.ic_company};
        for (int i = 0; i < titles.length; i++) {
            NavigationObject current = new NavigationObject();
            current.setName(titles[i]);
            current.setIcon(images[i]);
            data.add(current);
        }
        Log.v("NavDrawer", "NavDrawerFragment getData happened");
        return data;
    }

    public static void saveToPreferences(Context context, String preferenceName, String preferenceValue) {
        Log.v("NavDrawer", "NavDrawerFragment saveToPreferences happens");
        SharedPreferences sharedPreferences = context.getSharedPreferences
                (PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(preferenceName, preferenceValue);
        editor.apply();
        Log.v("NavDrawer", "NavDrawerFragment saveToPreferences happened");
    }

    public static String readFromPreferences(Context context, String preferenceName, String defaultValue) {
        Log.v("NavDrawer", "NavDrawerFragment readFromPreferences happens");
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        Log.v("NavDrawer", "NavDrawerFragment readFromPreferences happened");
        return sharedPreferences.getString(preferenceName, defaultValue);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v("NavDrawer", "NavDrawerFragment onCreate happens");
        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, "true"));
        if (savedInstanceState != null) {
            mFromSavedInstanceState = true;
        }
        Log.v("NavDrawer", "NavDrawerFragment onCreate happened");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.v("NavDrawer", "NavDrawerFragment onCreateView happens");
        View layout = inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        navRecycler = (RecyclerView) layout.findViewById(R.id.navRecyclerView);
        adapter = new navigationViewAdapter(getActivity(), getData());
        navRecycler.setAdapter(adapter);
        navRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.v("NavDrawer", "NavDrawerFragment onCreateView happened");
        return layout;
    }

    public void setUp(int fragmentID, DrawerLayout drawerLayout, final Toolbar toolbar) {
        Log.v("NavDrawer", "NavDrawerFragment setUp happens");
        containerView = getActivity().findViewById(fragmentID);
        mDrawerLayout = drawerLayout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                Log.v("NavDrawer", "NavDrawerFragment setUp onDrawerOpened happens");
                if (!mUserLearnedDrawer) {
                    mUserLearnedDrawer = true;
                    saveToPreferences(getActivity(), KEY_USER_LEARNED_DRAWER, mUserLearnedDrawer + "");
                }
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                Log.v("NavDrawer", "NavDrawerFragment setUp onDrawerClosed happens");
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

        Log.v("NavDrawer", "NavDrawerFragment setUp happened");
    }

}
