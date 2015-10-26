package com.example.studev19.bctools;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class AppointmentActivity extends AppCompatActivity {

    private static Toolbar toolbar;
    private static final int DIALOG_ALERT = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        TextView descriptionText = (TextView) findViewById(R.id.txtApptDescription);
        descriptionText.setText("You can make an appointment with career services to get help about\n" +
                "Resume building\nMock interviews\nJob searching \nGet ice cream");

        final String PHONE_NUMBER = "801-524-1925";
        RelativeLayout btnCall = (RelativeLayout) findViewById(R.id.layoutCall);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {                                                                           //Start Call Activity
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + PHONE_NUMBER));
                    startActivity(callIntent);
                } catch (ActivityNotFoundException activityException) {                         //Exception handler for call activity
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
            showDialog(DIALOG_ALERT);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id){
        switch (id){
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

    private final class OkOnClickListener implements DialogInterface.OnClickListener{

        @Override
        public void onClick(DialogInterface dialog, int which) {

        }
    }

}
