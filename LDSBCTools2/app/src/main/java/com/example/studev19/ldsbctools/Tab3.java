package com.example.studev19.ldsbctools;

import android.app.ProgressDialog;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.webkit.WebView;
import android.widget.MediaController;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab3 extends Fragment {

    Context context;
    TextView hyperlinkMentor;
    TextView hyperlinkBCSAFB;
    TextView hyperlinkClub;
    TextView hyperlinkActivity;
    TextView hyperlinkMarketing;
    TextView hyperlinkFeedback;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {



        this.context = getActivity().getApplicationContext();
        View v = inflater.inflate(R.layout.tab_3, container, false);

        //SET HYPERLINK TO MENTOR APPLICATION
        hyperlinkMentor = (TextView) v.findViewById(R.id.txtMentorLink);
        hyperlinkMentor.setClickable(true);
        hyperlinkMentor.setMovementMethod(LinkMovementMethod.getInstance());
        String mentorText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/1m9kEzCOASM1agdmP4QqVyakg1KOTPn5EzoUdhhVuCAo/viewform'>Student Mentor Application</a>";
        hyperlinkMentor.setText(Html.fromHtml(mentorText));

        //SET HYPERLINK TO BCSA FACEBOOK
        hyperlinkBCSAFB = (TextView) v.findViewById(R.id.txtBCSAFacebook);
        hyperlinkBCSAFB.setClickable(true);
        hyperlinkBCSAFB.setMovementMethod(LinkMovementMethod.getInstance());
        String facebookText = "<a href='https://www.facebook.com/ldsbcsa'>(NEW) BCSA Facebook Page!</a>";
        hyperlinkBCSAFB.setText(Html.fromHtml(facebookText));

        //SET HYPERLINK TO CLUB APPLICATION
        hyperlinkClub = (TextView) v.findViewById(R.id.txtClubLink);
        hyperlinkClub.setClickable(true);
        hyperlinkClub.setMovementMethod(LinkMovementMethod.getInstance());
        String clubText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/1glk2fRRRkHvzIgalqhTFeA946BmTUluwul1cT5DVNfI/viewform'>Club Application Form</a>";
        hyperlinkClub.setText(Html.fromHtml(clubText));

        //SET HYPERLINK TO ACTIVITY APPLICATION
        hyperlinkActivity = (TextView) v.findViewById(R.id.txtActivityLink);
        hyperlinkActivity.setClickable(true);
        hyperlinkActivity.setMovementMethod(LinkMovementMethod.getInstance());
        String activityText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/18vzBKPuAjJgZYvlWvPJ2TLqUNBSu2GebEmNtfBL8V1c/viewform'>Activity Request Form</a>";
        hyperlinkActivity.setText(Html.fromHtml(activityText));

        //SET HYPERLINK TO MARKETING APPLICATION
        hyperlinkMarketing = (TextView) v.findViewById(R.id.txtMarketingLink);
        hyperlinkMarketing.setClickable(true);
        hyperlinkMarketing.setMovementMethod(LinkMovementMethod.getInstance());
        String marketingText = "<a href='https://app.smartsheet.com/b/form?EQBCT=673583d1ff9b41babe313d9a2f73d341'>Marketing Request Form</a>";
        hyperlinkMarketing.setText(Html.fromHtml(marketingText));

        //SET HYPERLINK TO FEEDBACK APPLICATION
        hyperlinkFeedback = (TextView) v.findViewById(R.id.txtFeedbackLink);
        hyperlinkFeedback.setClickable(true);
        hyperlinkFeedback.setMovementMethod(LinkMovementMethod.getInstance());
        String feedbackText = "<a href='https://docs.google.com/a/ldsbc.edu/forms/d/1hzaCk2J-7u6Y0If-QTUQXPOIdQCbsYnWg-6lF5jKa1Y/viewform'>Ideas and Feedback</a>";
        hyperlinkFeedback.setText(Html.fromHtml(feedbackText));

        return v;
    }

}
