package com.example.user.design1;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.design1.model.MovieModel;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class DetailActivity extends AppCompatActivity {
    ImageView image;
    TextView title, year, duration, director, cast, story;
    RatingBar rating;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        image = (ImageView) findViewById(R.id.image);
        title = (TextView) findViewById(R.id.title);
        year = (TextView) findViewById(R.id.year);
        duration = (TextView) findViewById(R.id.duration);
        director = (TextView) findViewById(R.id.director);
        cast = (TextView) findViewById(R.id.cast);
        rating = (RatingBar) findViewById(R.id.ratingBar);
        story = (TextView) findViewById(R.id.story);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);


        Bundle bundle = getIntent().getExtras();
        if(bundle != null){
            String json = bundle.getString("movieModel");
            MovieModel movieModel = new Gson().fromJson(json, MovieModel.class);

            ImageLoader.getInstance().displayImage(movieModel.getImage(), image, new ImageLoadingListener() {
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


            title.setText(movieModel.getMovie());
            year.setText("Year:- " + (movieModel.getYear()));
            duration.setText("Duration:- " + movieModel.getDuration());
            director.setText("Director:- " + movieModel.getDirector());
            story.setText(movieModel.getStory());
            rating.setRating(movieModel.getRating()/2);

            StringBuffer stringBuffer = new StringBuffer();
            for (MovieModel.cast cas : movieModel.getCastlist()) {
                stringBuffer.append(" - " + cas.getName());
            }
            cast.setText("Cast:" + stringBuffer);
        }

    }
}
