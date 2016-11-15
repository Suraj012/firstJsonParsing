package com.example.user.design1;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.design1.model.MovieModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

public class ListAdapter extends ArrayAdapter {
    public List<MovieModel> movieModelList;
    private int resource;
    private LayoutInflater inflater;
    Context context;

    public ListAdapter(Context context, int resource, List<MovieModel> objects) {
        super(context, resource, objects);
        movieModelList = objects;
        this.resource = resource;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.listitem, null);
            holder.image = (ImageView) convertView.findViewById(R.id.image);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.year = (TextView) convertView.findViewById(R.id.year);
            holder.duration = (TextView) convertView.findViewById(R.id.duration);
            holder.director = (TextView) convertView.findViewById(R.id.director);
            holder.story = (TextView) convertView.findViewById(R.id.story);
            holder.rating = (RatingBar) convertView.findViewById(R.id.rating);
            holder.cast = (TextView) convertView.findViewById(R.id.cast);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ProgressBar progressBar = (ProgressBar) convertView.findViewById(R.id.progressBar);
        ImageLoader.getInstance().displayImage(movieModelList.get(position).getImage(), holder.image, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {
                progressBar.setVisibility(View.GONE);
            }
        });


        holder.title.setText(movieModelList.get(position).getMovie());
        holder.year.setText("Year:- " + (movieModelList.get(position).getYear()));
        holder.duration.setText("Duration:- " + movieModelList.get(position).getDuration());
        holder.director.setText("Director:- " + movieModelList.get(position).getDirector());
        holder.story.setText(movieModelList.get(position).getStory());
        holder.rating.setRating(movieModelList.get(position).getRating() / 2);

        StringBuffer stringBuffer = new StringBuffer();
        for (MovieModel.cast cas : movieModelList.get(position).getCastlist()) {
            stringBuffer.append(" - " + cas.getName());
        }
        holder.cast.setText("Cast:" + stringBuffer);

        return convertView;

    }

    class ViewHolder {
        ImageView image;
        TextView title;
        TextView year;
        TextView duration;
        TextView director;
        TextView story;
        RatingBar rating;
        TextView cast;
    }}