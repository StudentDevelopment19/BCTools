package com.example.studev19.ldsbctools;

import android.media.Image;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;

import java.text.SimpleDateFormat;
import java.util.TimeZone;


public class dealDetailedActivity extends ActionBarActivity {

    Toolbar toolbar;
    private static DealObject displayedInformation;

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
        final ParseImageView dealImage = (ParseImageView) findViewById(R.id.imgDealImage);
        ParseFile imageFile = displayedInformation.getDealImage();
        dealImage.setParseFile(imageFile);
        dealImage.loadInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, ParseException e) {
                //The image is loaded and displayed
                int oldHeight = dealImage.getHeight();
                int oldWidth = dealImage.getWidth();
                Log.v("LOG!!!!!!", "imageView height = " + oldHeight);      // DISPLAYS 90 px
                Log.v("LOG!!!!!!", "imageView width = " + oldWidth);        // DISPLAYS 90 px
            }
        });

        TextView dealNameText = (TextView) findViewById(R.id.txtDealName);
        dealNameText.setText(displayedInformation.getDealTitle());
        TextView dealDescText = (TextView) findViewById(R.id.txtDealDescription);
        dealDescText.setText(displayedInformation.getDealDesciption());
        TextView dealCompanyText = (TextView) findViewById(R.id.txtDealCompany);
        dealCompanyText.setText(displayedInformation.getDealCompany());
        TextView dealAddressText = (TextView) findViewById(R.id.txtDealAddress);
        dealAddressText.setText(displayedInformation.getDealAddress());
        TextView dealStDateText = (TextView) findViewById(R.id.txtDealDates);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM, dd");
        dateFormat.setTimeZone(TimeZone.getTimeZone("MST"));
        dealStDateText.setText(dateFormat.format(displayedInformation.getDealStartDateOnMST()) + " to " + dateFormat.format(displayedInformation.getEndDateOnMTS()));

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
            Toast.makeText(getApplicationContext(), "This option is not available for now", Toast.LENGTH_SHORT).show();
            return true;
        }

        //Navigates up to MainActivity
        if (id == android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);

    }

    public static void setDealInfo(DealObject dealObject){
        displayedInformation = dealObject;
    }

}
