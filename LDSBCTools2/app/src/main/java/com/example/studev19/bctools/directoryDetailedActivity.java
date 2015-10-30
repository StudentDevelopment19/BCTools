package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


public class directoryDetailedActivity extends ActionBarActivity {
    private static final int DIALOG_ALERT = 10;                                                     //ID for About App Dialog
    private static DirectoryObject displayedInformation;                                            //DirectoryObject with info for Detailed View
    private static String hyperlink;                                                                //Hyperlink to service website
    Toolbar toolbar;                                                                                //Declared Toolbar

    public static void setServiceInfo(DirectoryObject directoryObject) {                            //Set Current Info for Detailed View
        displayedInformation = directoryObject;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_directory_detailed);                                       //Layout and views come from activity_directory_detailed.xml

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);                                             //Initialize toolbar as app_bar
        toolbar.setTitle(displayedInformation.getName());                                           //Set Title of the AppBar
        setSupportActionBar(toolbar);                                                               //Enables the AppBar
        getSupportActionBar().setHomeButtonEnabled(true);                                           //Displays home/back button on toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);                                      //Home button will show as back button

        //-----THE FOLLOWING SECTION FILLS THE INFORMATION OF THE DETAILED VIEW-----//

        //DESCRIPTION SECTION
        TextView descText = (TextView) findViewById(R.id.txtDescription);                           //Find view for Description
        descText.setText(displayedInformation.getDescription());                                    //Sets value for Description from array and displays it

        //LOCATION SECTION
        TextView locationText = (TextView) findViewById(R.id.txtLocation);                          //Find view for Location Text
        ImageView locationIcon = (ImageView) findViewById(R.id.icoLocation);                        //Finds view for Location Icon
        if (displayedInformation.getLocation() == "") {                                             //If Location value is empty dismiss the views
            locationIcon.setVisibility(View.GONE);
            locationText.setVisibility(View.GONE);
        } else if (displayedInformation.getLocation() != "") {                                      //If Location value is not empty show Location Text and Icon
            locationText.setText(displayedInformation.getLocation());                               //Sets value for Location from Array and displays it
        }

        //PHONE SECTION
        TextView phoneText = (TextView) findViewById(R.id.txtPhone);                                //Find view for Phone Number Text
        ImageView phoneIcon = (ImageView) findViewById(R.id.icoPhone);                              //Finds view for Phone Icon
        if (displayedInformation.getPhone() == "") {                                                //If phone value is empty dismiss the views
            phoneIcon.setVisibility(View.GONE);
            phoneText.setVisibility(View.GONE);
        } else if (displayedInformation.getPhone() != "") {                                         //If Phone value is not empty show Phone Text and Icon
            phoneText.setOnClickListener(new View.OnClickListener() {            //Sets onClick listener for Phone Call
                @Override
                public void onClick(View view) {
                    try {                                                                           //Start Call Activity
                        Intent callIntent = new Intent(Intent.ACTION_CALL);
                        callIntent.setData(Uri.parse("tel:" + displayedInformation.getPhone()));
                        startActivity(callIntent);
                    } catch (ActivityNotFoundException activityException) {                         //Exception handler for call activity
                        Toast.makeText(getApplicationContext(),
                                "Application has stopped, failed to make a call to the " +
                                        displayedInformation.getName(), Toast.LENGTH_SHORT).show();
                        Log.e("BC Tools", "Call failed", activityException);
                    }
                }
            });
            phoneText.setText(displayedInformation.getPhone());                                     //Sets value for phone number from array and displays it
            if (locationIcon.getVisibility() == View.GONE) {                                        //If info for Location is not available, Location Text and Icon will not be displayed
                ViewGroup.LayoutParams p = phoneIcon.getLayoutParams();                             //and the Phone Text and Icon will be moved up.
                if (p instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                    lp.addRule(RelativeLayout.BELOW, R.id.divider1);
                    phoneIcon.setLayoutParams(lp);
                }
            }
        }

        //EMAIL SECTION
        TextView emailText = (TextView) findViewById(R.id.txtEmail);                                //Find view for Email Text
        ImageView emailIcon = (ImageView) findViewById(R.id.icoEmail);                              //Finds view for Email Icon
        if (displayedInformation.getEmail() == "") {                                                //If email value is empty dismiss the views
            emailIcon.setVisibility(View.GONE);
            emailText.setVisibility(View.GONE);
        } else if (displayedInformation.getEmail() != "") {                                         //If website value is not empty show Email Text and Icon
            emailText.setOnClickListener(new View.OnClickListener() {                                   //Sets onClick listener for Email
                @Override
                public void onClick(View view) {
                    try {                                                                           //Start Email Activity
                        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                        emailIntent.setData(Uri.parse("mailto:" + displayedInformation.getEmail()));//Email destination
                        startActivity(emailIntent);
                    } catch (ActivityNotFoundException activityException) {                         //Exception handler for email activity
                        Toast.makeText(getApplicationContext(),
                                "Application has stopped, failed to send an email to the " +
                                        displayedInformation.getName(), Toast.LENGTH_SHORT).show();
                        Log.e("BC Tools", "Email failed", activityException);
                    }
                }
            });

            emailText.setText(displayedInformation.getEmail());                                     //Sets value for email address from array and displays it
            if (phoneIcon.getVisibility() == View.GONE) {                                           //If info for Phone is not available, Phone Text and Icon will not be displayed
                ViewGroup.LayoutParams p = emailIcon.getLayoutParams();                             //and the Email Text and Icon will be moved up.
                if (p instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                    lp.addRule(RelativeLayout.BELOW, R.id.icoLocation);
                    emailIcon.setLayoutParams(lp);
                }
            }
            if (phoneIcon.getVisibility() == View.GONE &&
                    locationIcon.getVisibility() == View.GONE) {                                    //If info for Location and Phone is not available, the Location and Phone
                ViewGroup.LayoutParams p = emailIcon.getLayoutParams();                             // Text and Icons will not be displayed and the Email Text and Icon will be moved up.
                if (p instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                    lp.addRule(RelativeLayout.BELOW, R.id.divider1);
                    emailIcon.setLayoutParams(lp);
                }
            }
        }
        //WEBSITE SECTION
        TextView webText = (TextView) findViewById(R.id.txtWeb);                                    //Finds view for Website Text
        ImageView webIcon = (ImageView) findViewById(R.id.icoWeb);                                  //Finds view for Website Icon
        Log.v(displayedInformation.getName(), webText.getText().toString());
        if (displayedInformation.getWebSite() == "") {                                              //If website value is empty dismiss the views
            webIcon.setVisibility(View.GONE);
            webText.setVisibility(View.GONE);
        } else if (displayedInformation.getWebSite() != "") {                                       //If website value is not empty show Website Text and Icon
            hyperlink = "<a href='" + displayedInformation.getWebSite() + "'>" + "Website" + "</a>";//Set hyperlink value
            webText.setClickable(true);                                                             //Sets Website text clickable
            webText.setMovementMethod(LinkMovementMethod.getInstance());                            //Enables hyperlink
            webText.setText(Html.fromHtml(hyperlink));                                              //Sets value for Website

            if (emailIcon.getVisibility() == View.GONE) {                                           //If info for Email is not available, Email Text and Icon will not be displayed
                ViewGroup.LayoutParams p = webIcon.getLayoutParams();                               //and the Website Text and Icon will be moved up.
                if (p instanceof RelativeLayout.LayoutParams) {
                    RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) p;
                    lp.addRule(RelativeLayout.BELOW, R.id.icoPhone);
                    emailIcon.setLayoutParams(lp);
                }
            }
            //REMEMBER TO ADD MORE RULES
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
            return true;
        }

        //Navigates up to MainActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {                                                       //Create About App Dialog
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
