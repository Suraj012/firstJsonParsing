package com.example.user.design1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.user.design1.model.MovieModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by User on 5/12/2016.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    public List<MovieModel> movieModelList;
    LayoutInflater inflater;

    public RecyclerViewAdapter(Context context, int position, List<MovieModel> result){
        inflater = LayoutInflater.from(context);
    }
    public void setMovieModelList(ArrayList<MovieModel> movieModelList){
        this.movieModelList = movieModelList;
        notifyItemChanged(0, movieModelList.size());

    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.listitem, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MovieModel movie = movieModelList.get(position);
        holder.title.setText(movie.getMovie());
        holder.year.setText(movie.getYear());
        holder.duration.setText(movie.getDuration());
        holder.director.setText(movie.getDirector());
        holder.story.setText(movie.getStory());
        holder.rating.setRating(movie.getRating()/2);

        StringBuffer stringBuffer = new StringBuffer();
        for (MovieModel.cast cas : movie.getCastlist()) {
            stringBuffer.append(" - " + cas.getName());
        }
        holder.cast.setText("Cast:" + stringBuffer);
    }
    @Override
    public int getItemCount() {
        return movieModelList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title;
        TextView year;
        TextView duration;
        TextView director;
        TextView story;
        RatingBar rating;
        TextView cast;

        public MyViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            title = (TextView) itemView.findViewById(R.id.title);
            year = (TextView) itemView.findViewById(R.id.year);
            duration = (TextView) itemView.findViewById(R.id.duration);
            director = (TextView) itemView.findViewById(R.id.director);
            story = (TextView) itemView.findViewById(R.id.story);
            rating = (RatingBar) itemView.findViewById(R.id.rating);
            cast = (TextView) itemView.findViewById(R.id.cast);
        }
    }
}
