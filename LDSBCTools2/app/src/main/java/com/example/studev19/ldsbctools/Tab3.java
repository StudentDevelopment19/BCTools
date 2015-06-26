package com.example.studev19.ldsbctools;

import android.content.Context;
import android.net.Uri;
import android.widget.MediaController;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

/**
 * Created by studev19 on 5/18/2015.
 */
public class Tab3 extends Fragment {

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        this.context = getActivity().getApplicationContext();
        View v = inflater.inflate(R.layout.tab_3, container, false);
        /*VideoView videoView = (VideoView) v.findViewById(R.id.videoView);
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(videoView);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(Uri.parse("http://bcmessenger.com/wp-content/uploads/2014/09/Student_Involvement_condesnsed_02.mp4?_=1"));*/
        return v;
    }

}
