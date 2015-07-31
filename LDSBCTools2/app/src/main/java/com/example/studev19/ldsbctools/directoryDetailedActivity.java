package com.example.studev19.ldsbctools;

import android.app.Application;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.telephony.PhoneNumberUtils;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;


public class directoryDetailedActivity extends ActionBarActivity {
    Toolbar toolbar;
    private static DirectoryObject displayedInformation;
    private static final int DIALOG_ALERT = 10;
    private static String hyperlink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_detailed);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //Set Title of the AppBar
        toolbar.setTitle(displayedInformation.getName());

        //Applies the AppBar
        setSupportActionBar(toolbar);

        //Set the Navigation Up button and enables it
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This section fills the information of the detailed view
        TextView descText = (TextView) findViewById(R.id.txtDescription);                           //Find view for Description
        descText.setText(displayedInformation.getDescription());                                    //Sets Value for description from array
        TextView phoneText = (TextView) findViewById(R.id.txtPhone);                                //Find view for Phone Number
        phoneText.setOnClickListener(new View.OnClickListener() {            //Sets onClick listener for Phone Call
            @Override
            public void onClick(View view) {
                try {                                                                               //Start Call Activity
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + displayedInformation.getPhone()));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {                             //Exception handler for call activity
                    Toast.makeText(getApplicationContext(),
                            "Application has stopped, failed to make a call to the " +
                                    displayedInformation.getName(), Toast.LENGTH_SHORT).show();
                    Log.e("BC Tools", "Call failed", activityException);
                }
            }
        });
        phoneText.setText(displayedInformation.getPhone());                                         //Sets value for phone number from array
        TextView emailText = (TextView) findViewById(R.id.txtEmail);                                //Find view for Email
        emailText.setOnClickListener(new View.OnClickListener() {           //Sets onClick listener for Email
            @Override
            public void onClick(View view) {
                try {                                                                               //Start Email Activity
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + displayedInformation.getEmail()));
                    startActivity(emailIntent);
                } catch (ActivityNotFoundException activityException) {                             //Exception handler for email activity
                    Toast.makeText(getApplicationContext(),
                            "Application has stopped, failed to send an email to the " +
                                    displayedInformation.getName(), Toast.LENGTH_SHORT).show();
                    Log.e("BC Tools", "Email failed", activityException);
                }
            }
        });
        emailText.setText(displayedInformation.getEmail());                                         //Sets value for Email address from Array
        TextView locationText = (TextView) findViewById(R.id.txtLocation);                          //Find view for Location
        locationText.setText(displayedInformation.getLocation());                                   //Sets value for Location from Array
        TextView webText = (TextView) findViewById(R.id.txtWeb);                                    //Finds view for Website
        ImageView webIcon = (ImageView) findViewById(R.id.icoWeb);                                  //Finds view for Website Icon
        Log.v(displayedInformation.getName(), webText.getText().toString());
        if (displayedInformation.getWebSite() == "") {                                              //If website value is empty dismiss the views
            webIcon.setVisibility(View.GONE);
            webText.setVisibility(View.GONE);
        }
        else if (displayedInformation.getWebSite() != ""){                                          //If website value is not empty create hyperlink
            hyperlink = "<a href='" + displayedInformation.getWebSite() + "'>"+ "Website" +"</a>";  //Set hyperlink value
            webText.setClickable(true);                                                             //Sets Website text clickable
            webText.setMovementMethod(LinkMovementMethod.getInstance());                            //Enables hyperlink
            webText.setText(Html.fromHtml(hyperlink));                                              //Sets value for Website));
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_directory_detailed, menu);
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

        //Navigates up to MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    public static void setServiceInfo(DirectoryObject directoryObject) {
        displayedInformation = directoryObject;
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DIALOG_ALERT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("About this App");
                builder.setMessage("Version 1.0" + "\n\u00a92015 LDS Business College");
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
