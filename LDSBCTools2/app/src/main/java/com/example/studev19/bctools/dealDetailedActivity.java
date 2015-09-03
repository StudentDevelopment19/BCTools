package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class dealDetailedActivity extends ActionBarActivity {

    Toolbar toolbar;
    private static DealObject displayedInformation;
    private static final int DIALOG_ALERT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deal_detailed);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);

        //Set Title of the AppBar
        toolbar.setTitle("");

        //Applies the AppBar
        setSupportActionBar(toolbar);

        //Set the Navigation Up button and enables it
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //This section fills the information of the detailed view
        final ParseImageView dealImage = (ParseImageView) findViewById(R.id.imgDealImage);          //Find image view
        ParseFile imageFile = displayedInformation.getDealImage();                                  //Set ParseFile as image from parse
        dealImage.setParseFile(imageFile);                                                          //Set dealImage as Image from parse
        dealImage.loadInBackground(new GetDataCallback() {
            @Override
public void done(byte[] bytes, ParseException e) {                                                  //This process makes the image visible from app
                //The image is loaded and displayed
                int oldHeight = dealImage.getHeight();
                int oldWidth = dealImage.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });

        TextView dealNameText = (TextView) findViewById(R.id.txtDealName);                          //Find TextView for dealName
        dealNameText.setText(displayedInformation.getDealTitle());                                  //Set value for dealName
        TextView dealDescText = (TextView) findViewById(R.id.txtDealDescription);                   //Find TextView for dealDescription
        dealDescText.setText(displayedInformation.getDealDescription());                            //Set value for dealDescription
        TextView dealCompanyText = (TextView) findViewById(R.id.txtDealCompany);                    //Find TextView for dealCompany
        dealCompanyText.setText(displayedInformation.getDealCompany());                             //Set value for dealCompany
        TextView dealAddressText = (TextView) findViewById(R.id.txtDealAddress);                    //Find TextView for dealAddress
        dealAddressText.setText(displayedInformation.getDealAddress());                             //Set value for dealAddress
        TextView dealStDateText = (TextView) findViewById(R.id.txtDealDates);                       //Find TextView for dealDate
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd");                              //Set dateFormat
        dateFormat.setTimeZone(TimeZone.getTimeZone("MST"));                                        //Set time zone to MST
        dealStDateText.setText(dateFormat.format(displayedInformation.
                getDealStartDateOnMST()) + " to " +
                dateFormat.format(displayedInformation.getEndDateOnMTS()));                         //Set date format and value for dealDate

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_deal_detailed, menu);
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
            //NavUtils.navigateUpFromSameTask(this);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public static void setDealInfo(DealObject dealObject) {
        displayedInformation = dealObject;
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
