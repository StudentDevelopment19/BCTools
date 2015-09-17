package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.studev19.bctools.R;

public class BCSAActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private static final int DIALOG_ALERT = 10;
    private static final int NO_INTERNET_DIALOG = 5;
    private String videoURL;
    private TextView hyperlinkBCMessenger;
    private TextView hyperlinkMentor;
    private TextView hyperlinkBCSAFB;
    private TextView hyperlinkClub;
    private TextView hyperlinkActivity;
    private TextView hyperlinkMarketing;
    private TextView hyperlinkFeedback;
    private ImageView hyperlinkVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bcsa);

        //Creating the Toolbar and setting it as the Toolbar for the Activity
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        videoURL = "http://bcmessenger.com/wp-content/uploads/2014/09/Student_Involvement_condesnsed_02.mp4?_=1";

        //SET HYPERLINK TO VIDEO
        hyperlinkVideo = (ImageView) findViewById(R.id.videoView);
        hyperlinkVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(videoURL));
                startActivity(intent);
            }
        });

        //SET HYPERLINK TO BC MESSENGER
        hyperlinkBCMessenger = (TextView) findViewById(R.id.txtBCMessengerLink);
        hyperlinkBCMessenger.setClickable(true);
        hyperlinkBCMessenger.setMovementMethod(LinkMovementMethod.getInstance());
        String BCMessengerText = "<a href='http://www.bcmessenger.com/'>BC Messenger</a>";
        hyperlinkBCMessenger.setText(Html.fromHtml(BCMessengerText));

        //SET HYPERLINK TO MENTOR APPLICATION
        hyperlinkMentor = (TextView) findViewById(R.id.txtMentorLink);
        hyperlinkMentor.setClickable(true);
        hyperlinkMentor.setMovementMethod(LinkMovementMethod.getInstance());
        String mentorText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/1m9kEzCOASM1agdmP4QqVyakg1KOTPn5EzoUdhhVuCAo/viewform'>Student Mentor Application</a>";
        hyperlinkMentor.setText(Html.fromHtml(mentorText));

        //SET HYPERLINK TO BCSA FACEBOOK
        hyperlinkBCSAFB = (TextView) findViewById(R.id.txtBCSAFacebook);
        hyperlinkBCSAFB.setClickable(true);
        hyperlinkBCSAFB.setMovementMethod(LinkMovementMethod.getInstance());
        String facebookText = "<a href='https://www.facebook.com/ldsbcsa'>(NEW) BCSA Facebook Page!</a>";
        hyperlinkBCSAFB.setText(Html.fromHtml(facebookText));

        //SET HYPERLINK TO CLUB APPLICATION
        hyperlinkClub = (TextView) findViewById(R.id.txtClubLink);
        hyperlinkClub.setClickable(true);
        hyperlinkClub.setMovementMethod(LinkMovementMethod.getInstance());
        String clubText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/1glk2fRRRkHvzIgalqhTFeA946BmTUluwul1cT5DVNfI/viewform'>Club Application Form</a>";
        hyperlinkClub.setText(Html.fromHtml(clubText));

        //SET HYPERLINK TO ACTIVITY APPLICATION
        hyperlinkActivity = (TextView) findViewById(R.id.txtActivityLink);
        hyperlinkActivity.setClickable(true);
        hyperlinkActivity.setMovementMethod(LinkMovementMethod.getInstance());
        String activityText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/18vzBKPuAjJgZYvlWvPJ2TLqUNBSu2GebEmNtfBL8V1c/viewform'>Activity Request Form</a>";
        hyperlinkActivity.setText(Html.fromHtml(activityText));

        //SET HYPERLINK TO MARKETING APPLICATION
        hyperlinkMarketing = (TextView) findViewById(R.id.txtMarketingLink);
        hyperlinkMarketing.setClickable(true);
        hyperlinkMarketing.setMovementMethod(LinkMovementMethod.getInstance());
        String marketingText = "<a href='https://app.smartsheet.com/b/form?EQBCT=673583d1ff9b41babe313d9a2f73d341'>Marketing Request Form</a>";
        hyperlinkMarketing.setText(Html.fromHtml(marketingText));

        //SET HYPERLINK TO FEEDBACK APPLICATION
        hyperlinkFeedback = (TextView) findViewById(R.id.txtFeedbackLink);
        hyperlinkFeedback.setClickable(true);
        hyperlinkFeedback.setMovementMethod(LinkMovementMethod.getInstance());
        String feedbackText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/1hzaCk2J-7u6Y0If-QTUQXPOIdQCbsYnWg-6lF5jKa1Y/viewform'>Ideas and Feedback</a>";
        hyperlinkFeedback.setText(Html.fromHtml(feedbackText));

        getSupportActionBar().setDisplayShowHomeEnabled(true);

        NavigationDrawerFragment drawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigationDrawer);

        drawerFragment.setUp(R.id.navigationDrawer, (DrawerLayout) findViewById(R.id.drawerLayout), toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bcsa, menu);
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