package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.os.Handler;
import com.parse.ParseAnalytics;

public class MainActivity extends ActionBarActivity {

    private static final int DIALOG_ALERT = 10;
    private static final int NO_INTERNET_DIALOG = 5;
    private static SwipeRefreshLayout webSwipe;
    private Toolbar toolbar;
    private boolean connection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        connection = internetConnection();
        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigationDrawer);

        drawerFragment.setUp(R.id.navigationDrawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

        webSwipe = (SwipeRefreshLayout) findViewById(R.id.swipeHome);
        webSwipe.setColorSchemeColors(R.color.primaryColor, R.color.accentColor);
        final WebView WEB_VIEW = (WebView) findViewById(R.id.webViewHome);

        if (connection == false) {
            showDialog(NO_INTERNET_DIALOG);
            String html = "<html><body><p>You must be connected to the internet to display this tab correctly.</p></body></html>";
            String mime = "text/html";
            String encoding = "utf-8";
            WEB_VIEW.loadDataWithBaseURL(null, html, mime, encoding, null);
        }
        else{
            WEB_VIEW.loadUrl("https://www.ldsbc.edu/");
        }

        webSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webSwipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webSwipe.setRefreshing(false);
                        WEB_VIEW.loadUrl("https://www.ldsbc.edu/");
                    }
                }, 4000);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showDialog(DIALOG_ALERT);
            //Toast.makeText(getApplicationContext(), "This option is not available for now", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About this App");
                builder.setMessage(getString(R.string.current_version) + "\n\u00a92015 LDS Business College");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
                break;
            case NO_INTERNET_DIALOG:
                AlertDialog.Builder internet = new AlertDialog.Builder(this);
                internet.setTitle("You are not connected to the internet");
                internet.setMessage("Some information cannot be displayed without internet connection");
                internet.setCancelable(true);
                internet.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog internetDialog = internet.create();
                internetDialog.show();
                break;
        }

        return super.onCreateDialog(id);

    }

    public boolean internetConnection() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}