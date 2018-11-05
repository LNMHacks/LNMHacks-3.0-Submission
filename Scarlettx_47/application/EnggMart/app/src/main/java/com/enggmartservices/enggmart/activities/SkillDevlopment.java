package com.enggmartservices.enggmart.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.enggmartservices.enggmart.adapers.VideoAdapter;
import com.enggmartservices.enggmart.models.YouTubeVideos;
import com.enggmartservices.enggmart.utility.Utils;
import com.example.user.enggmart.R;

import java.util.Vector;

public class SkillDevlopment extends AppCompatActivity {
    RecyclerView recyclerView;
    Vector<YouTubeVideos> youtubeVideos = new Vector<YouTubeVideos>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_devlopment);
        Utils.darkenStatusBar(this, R.color.textColorPrimary);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView11);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/KVVFEXMBHrw\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/_cyFQ8dotu0\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/LaupTr4Jro0\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/U3G5W2Kdr74\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/DfWkxpdOEP8\" frameborder=\"0\" allowfullscreen></iframe>"));
        youtubeVideos.add(new YouTubeVideos("<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/XlJm_x7dyLU\" frameborder=\"0\" allowfullscreen></iframe>"));

        VideoAdapter videoAdapter = new VideoAdapter(youtubeVideos);

        recyclerView.setAdapter(videoAdapter);
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}