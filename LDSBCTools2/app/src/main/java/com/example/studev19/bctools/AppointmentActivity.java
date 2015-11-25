package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentActivity extends AppCompatActivity {

    private static Toolbar toolbar;                                                                 //Declared Toolbar
    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);                                              //Layout and views come from activity_appointment.xml

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);                                             //Initialize toolbar as app_bar
        setSupportActionBar(toolbar);                                                               //Enables toolbar
        getSupportActionBar().setHomeButtonEnabled(true);                                           //Displays home/back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                      //Home button will show as back button

        final String PHONE_NUMBER = "801-524-1925";                                                 //Declare/Initialize final PHONE_NUMBER

        //TextView containing activity description
        TextView descriptionText = (TextView) findViewById(R.id.txtAptDescription);                 //TextView descriptionText is created and given a value
        String descText = "Call for help with any of the following:";
        descriptionText.setText(descText);                                                          //Set text for descriptionText

        TextView descriptionList = (TextView) findViewById(R.id.txtDescriptionList);
        String descList = "&#160;&#160;&#160;&#8226; Career Assessment <br />" +
                "&#160;&#160;&#160;&#8226; Internships <br />" +
                "&#160;&#160;&#160;&#8226; Interviews <br />" +
                "&#160;&#160;&#160;&#8226; Job Search <br />" +
                "&#160;&#160;&#160;&#8226; LinkedIn <br />" +
                "&#160;&#160;&#160;&#8226; Networking <br />" +
                "&#160;&#160;&#160;&#8226; Resume / Cover Letter <br />" +
                "&#160;&#160;&#160;&#8226; Strengths <br />" +
                "&#160;&#160;&#160;&#8226; Other";

        //Indentation &#160;&#160;
        //Bullet &#8226;
        descriptionList.setText(Html.fromHtml(descList));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {                                                                               //Try Call Activity
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));                           //Set number to be called from PHONE_NUMBER
                    startActivity(callIntent);                                                      //Start Call Activity Intent
                } catch (ActivityNotFoundException activityException) {                             //Exception handler for call activity
                    Toast.makeText(getApplicationContext(),
                            "Application has stopped, failed to make a call to Career Services"
                            , Toast.LENGTH_SHORT).show();
                    Log.e("BC Tools", "Call failed", activityException);
                }
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_appointment, menu);
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
            showDialog(DIALOG_ALERT);                                                               //When menu is selected call the About App Dialog
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {                                                        //Create About App Dialog
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About this App");
                builder.setMessage(getString(R.string.current_version) + "\n\u00a92015 LDS Business College");
                builder.setCancelable(true);
                builder.setPositiveButton("OK", new OkOnClickListener());
                AlertDialog dialog = builder.create();
                dialog.show();
        }
        return super.onCreateDialog(id);
    }

    private final class OkOnClickListener implements DialogInterface.OnClickListener {

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
