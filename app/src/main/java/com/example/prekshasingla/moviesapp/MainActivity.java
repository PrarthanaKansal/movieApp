package com.example.prekshasingla.moviesapp;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ArrayList<movie> movieArrayList = new ArrayList<>();
    ArrayList<movie> BollywoodMovieArrayList = new ArrayList<>();

    RecyclerView recyclerView;
    RecyclerView recyclerView2;

    MovieRecyclerAdapter movieRecyclerAdapter;
    MovieRecyclerAdapter bollywoodMovieRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView2 = findViewById(R.id.recyclerView2);

        movieRecyclerAdapter = new MovieRecyclerAdapter(this,movieArrayList);
        bollywoodMovieRecyclerAdapter = new MovieRecyclerAdapter(this,BollywoodMovieArrayList);

        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setAdapter(movieRecyclerAdapter);

        recyclerView2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        recyclerView2.setAdapter(bollywoodMovieRecyclerAdapter);

        getBollyData();

        getMovieData();
    }

    private void getBollyData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://api.themoviedb.org/3/discover/movie?api_key=49f959c35c9422840360168ba4f8026c&language=en-US&sort_by=popularity.desc";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key","49f959c35c9422840360168ba4f8026c")
                .appendQueryParameter("region","IN")
                .appendQueryParameter("sort_by","vote_average.desc");
        String url = builder.build().toString();



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("MSG",response);
                        parseBollyData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseBollyData(String response) {

        try {
            JSONObject resultJSON = new JSONObject(response);
            JSONArray resultsArray = resultJSON.getJSONArray("results");

            for(int i=0;i<resultsArray.length();i++){
                JSONObject movieObject = resultsArray.getJSONObject(i);
                movie Movie = new movie();
                Movie.setId(movieObject.getInt("id"));
                Movie.setTitle(movieObject.getString("title"));
                Movie.setPosterPath(movieObject.getString("poster_path"));
                Movie.setOverview(movieObject.getString("overview"));
                BollywoodMovieArrayList.add(Movie);

            }
            bollywoodMovieRecyclerAdapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void getMovieData() {

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://api.themoviedb.org/3/discover/movie?api_key=49f959c35c9422840360168ba4f8026c&language=en-US&sort_by=popularity.desc";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
        .authority("api.themoviedb.org")
        .appendPath("3")
                .appendPath("discover")
                .appendPath("movie")
                .appendQueryParameter("api_key","49f959c35c9422840360168ba4f8026c")
                .appendQueryParameter("language","en-US")
                .appendQueryParameter("sort_by","popularity.desc");
        String url = builder.build().toString();



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("MSG",response);
                        parseData(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
               // mTextView.setText("That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void parseData(String response) {

        try {
            JSONObject resultJSON = new JSONObject(response);
            JSONArray resultsArray = resultJSON.getJSONArray("results");

            for(int i=0;i<resultsArray.length();i++){
                JSONObject movieObject = resultsArray.getJSONObject(i);
                movie Movie = new movie();
                Movie.setId(movieObject.getInt("id"));
                Movie.setTitle(movieObject.getString("title"));
                Movie.setPosterPath(movieObject.getString("poster_path"));
                Movie.setOverview(movieObject.getString("overview"));
                movieArrayList.add(Movie);

            }
            movieRecyclerAdapter.notifyDataSetChanged();


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
