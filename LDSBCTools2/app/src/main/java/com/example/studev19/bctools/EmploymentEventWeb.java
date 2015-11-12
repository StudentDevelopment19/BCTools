package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.os.Handler;

public class EmploymentEventWeb extends AppCompatActivity {

    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static final int NO_INTERNET_DIALOG = 5;                                                //ID for No Internet Connection Dialog
    private Toolbar toolbar;
    private static SwipeRefreshLayout webSwipe;
    private static boolean connectionStatus;

    public static void setConnectionStatus(boolean status){
        connectionStatus = status;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_employment_event_web);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String mURL = "http://www.eventbrite.com/o/lds-business-college-5412722679";

        webSwipe = (SwipeRefreshLayout) findViewById(R.id.swipeEmployment);
        webSwipe.setColorSchemeColors(getResources().getColor(R.color.primaryColor), getResources().getColor(R.color.accentColor));

        final WebView WEB_VIEW = (WebView) findViewById(R.id.employmentWebView);
        if (connectionStatus ==  false){
            String html = "<html><body><p>You must be connected to the internet to display this tab correctly.</p></body></html>";
            String mime = "text/html";
            String encoding = "utf-8";

            WEB_VIEW.loadDataWithBaseURL(null, html, mime, encoding, null);
        } else {
            WEB_VIEW.loadUrl(mURL);
        }

        webSwipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                webSwipe.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        webSwipe.setRefreshing(false);
                        WEB_VIEW.loadUrl(mURL);
                    }
                }, 4000);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
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

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
