package com.example.user.design1;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.example.user.design1.model.MovieModel;

import java.util.List;

/**
 * Created by User on 5/12/2016.
 */
public class SwipeAdapter extends BaseSwipeAdapter {

    public List<MovieModel> movieModelList;
    private Context context;
    int resource;
    public SwipeAdapter(Context context, int resource, List<MovieModel> objects) {
        this.resource = resource;
        this.context = context;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return 0;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        return null;
    }

    @Override
    public void fillValues(int position, View convertView) {

    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
