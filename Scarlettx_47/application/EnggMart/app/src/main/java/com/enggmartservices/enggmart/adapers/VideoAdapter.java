package com.enggmartservices.enggmart.adapers;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.enggmartservices.enggmart.models.YouTubeVideos;
import com.enggmartservices.enggmart.R;

import java.util.List;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.VideoViewHolder> {

    List<YouTubeVideos> youtubeVideosList;

    public VideoAdapter() {

    }

    public VideoAdapter(List<YouTubeVideos> youtubeVideosList) {
        this.youtubeVideosList = youtubeVideosList;
    }

    @Override

    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_view, parent, false);
        return new VideoViewHolder(view);
    }


    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.videoWeb.loadData(youtubeVideosList.get(position).getVideoUrl(), "text/html", "utf-8");
    }


    @Override
    public int getItemCount() {
        return youtubeVideosList.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder {

        WebView videoWeb;

        public VideoViewHolder(View itemView) {
            super(itemView);

            videoWeb = (WebView) itemView.findViewById(R.id.videoWebiew);

            videoWeb.getSettings().setJavaScriptEnabled(true);
            videoWeb.setWebChromeClient(new WebChromeClient() {

            });
        }
    }
}
