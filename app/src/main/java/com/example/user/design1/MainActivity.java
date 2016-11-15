package com.example.user.design1;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.design1.model.MovieModel;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    ListView listView;
    RecyclerView recyclerView;
    private ProgressDialog dialog;
    SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setOnRefreshListener(this);
        //Adding toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Executing the JsonParsing information
        new JsonTask().execute("http://surajbhandari.com.np/api.txt");

        // Create default options which will be used for every
//  displayImage(...) call if no options will be passed to this method
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config); // Do it on Application start

        listView = (ListView) findViewById(R.id.list);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
        // showNotification();
            Calendar calendar = Calendar.getInstance();

            calendar.set(Calendar.HOUR_OF_DAY,1);
            calendar.set(Calendar.MINUTE, 49);
            calendar.set(Calendar.SECOND,00);


            Intent intent = new Intent(MainActivity.this, Notification_Receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY ,pendingIntent);
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.latest) {
            // Handle the camera action
        } else if (id == R.id.top) {

        } else if (id == R.id.most_watched) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        new JsonTask().execute("http://surajbhandari.com.np/api.txt");
    }


    public class JsonTask extends AsyncTask<String, String, List<MovieModel>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<MovieModel> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line = "";
                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }
                String finalJson = buffer.toString();
                JSONObject parentObject = new JSONObject(finalJson);
                // StringBuffer finalBufferedData = new StringBuffer();
                JSONArray parentArray = parentObject.getJSONArray("movies");

                List<MovieModel> movieModelList = new ArrayList<>();

                //Gson Initialization
                Gson gson = new Gson();

                for (int i = 0; i < parentArray.length(); i++) {
                    JSONObject finalObject = parentArray.getJSONObject(i);

                    //Using Gson
                    MovieModel movie = gson.fromJson(finalObject.toString(), MovieModel.class);
                    //

                    // Using Json
                    MovieModel movieModel = new MovieModel();
                    movieModel.setMovie(finalObject.getString("movie"));
                    movieModel.setYear(finalObject.getInt("year"));
                    movieModel.setDuration(finalObject.getString("duration"));
                    movieModel.setDirector(finalObject.getString("director"));
                    movieModel.setRating((float) finalObject.getDouble("rating"));
                    movieModel.setStory(finalObject.getString("story"));
                    movieModel.setImage(finalObject.getString("image"));

                    List<MovieModel.cast> castList = new ArrayList<>();
                    for (int j = 0; j < finalObject.getJSONArray("cast").length(); j++) {
                        MovieModel.cast cast = new MovieModel.cast();
                        cast.setName(finalObject.getJSONArray("cast").getJSONObject(j).getString("name"));
                        castList.add(cast);
                    }
                    movieModel.setCastlist(castList); //

                    //Adding the final object
                    // movieModelList.add(movieModel);
                    movieModelList.add(movie);
                }
                return movieModelList;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(final List<MovieModel> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            Log.e("@reulst", String.valueOf(result));

            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
            if (result != null) {
//                LinearLayoutManager layout = new LinearLayoutManager(getApplicationContext());
//                recyclerView.setLayoutManager(layout);
//                RecyclerViewAdapter adapter = new RecyclerViewAdapter(getApplicationContext(),0,result);
//                recyclerView.setAdapter(adapter);

                //recyclerView.setAdapter(new RecyclerViewAdapter(getApplicationContext(),0, result));
                //listView.setAdapter(new ListAdapter(getApplicationContext(), 0, result));
                MovieAdapter movieAdapter = new MovieAdapter(getApplicationContext(), R.layout.listitem, result);
                listView.setAdapter(movieAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        MovieModel movieModel = result.get(position);
                        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                        intent.putExtra("movieModel", new Gson().toJson(movieModel));
                        startActivity(intent);
                    }
                });
            } else {
                Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
            }
        }
    }

    public class MovieAdapter extends ArrayAdapter {
        public List<MovieModel> movieModelList;
        private int resource;
        private LayoutInflater inflater;

        public MovieAdapter(Context context, int resource, List<MovieModel> objects) {
            super(context, resource, objects);
            movieModelList = objects;
            this.resource = resource;
            inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = inflater.inflate(resource, null);
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
            Log.e("@Info", String.valueOf(movieModelList));


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
        }

    }
//    public void showNotification(){
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
//        builder.setSmallIcon(android.R.drawable.alert_dark_frame);
//        builder.setContentTitle("Notification");
//        builder.setContentText("This is the.......");
//        Intent intent = new Intent(MainActivity.this, nexxt.class);
//        TaskStackBuilder stackBuilder = TaskStackBuilder.create(MainActivity.this);
//        stackBuilder.addParentStack(nexxt.class);
//        stackBuilder.addNextIntent(intent);
//        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT);
//        builder.setContentIntent(pendingIntent);
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//       // builder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
//        builder.setAutoCancel(true);
//        notificationManager.notify(0,builder.build());
//
//        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(Calendar.HOUR_OF_DAY, 12);
//        calendar.set(Calendar.MINUTE, 00);
//        calendar.set(Calendar.SECOND, 00);
//        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 10000 , pendingIntent);
//    }

}
