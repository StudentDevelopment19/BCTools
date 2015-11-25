package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class JobListActivity extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static final int NO_INTERNET_DIALOG = 5;                                                //ID for No Internet Connection Dialog
    private static Toolbar toolbar;                                                                 //Declared Toolbar
    private static SwipeRefreshLayout swipeRefreshLayout;                                           //Refresh Layout
    private static WebView webJobList;
    private static Boolean connection;
    private static Context context;                                                                 //Declared Context

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_list);                                                 //Layout and views come from activity_job_list.xml

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);                                             //Initialize toolbar as app_bar
        setSupportActionBar(toolbar);                                                               //Enable toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);                                      //Displays home/back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                      //Home button will show as back button
        context = this;

        webJobList = (WebView) findViewById(R.id.wbJobList);
        webJobList.getSettings().setJavaScriptEnabled(true);
        webJobList.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

        //CONTENT//\
        connection = internetConnection();
        if (connection == false){
            showDialog(NO_INTERNET_DIALOG);
            String html = "<html><body><p>You must be connected to the internet to display this tab correctly.</p></body></html>";
            String mime = "text/html";
            String encoding = "utf-8";
            webJobList.loadDataWithBaseURL(null, html, mime, encoding, null);
        }
        else {
            webJobList.loadUrl("https://app.smartsheet.com/b/mpublish?EQBCT=fbae046f0612479eadfdbebc2a77517e#sheet");
        }

        //SWIPE LISTENER
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeJobRefresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.primaryColor, R.color.accentColor);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        webJobList.loadUrl("https://app.smartsheet.com/b/mpublish?EQBCT=fbae046f0612479eadfdbebc2a77517e#sheet");
                    }
                }, 4000);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_job_list, menu);
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

    public boolean internetConnection(){
        ConnectivityManager cm  = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }
}
