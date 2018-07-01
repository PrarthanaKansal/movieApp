package com.example.prekshasingla.moviesapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by prekshasingla on 26/06/18.
 */

public class DetailActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{
    ImageView imageView;
    TextView title;
    TextView overview;
    YouTubePlayerView youTubePlayerView;
    String key;
    private YouTubePlayer player;
    ArrayList<ReviewModel> listOfReviews;
//    ArrayList<String> listOfReviews;
//    ArrayList<String> listOfNames;
    LinearLayout linearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //imageView = findViewById(R.id.detail_image);
        title = findViewById(R.id.detail_title);
        overview = findViewById(R.id.overview);
        youTubePlayerView = findViewById(R.id.youtubePlayer);
        linearLayout = findViewById(R.id.container);

        if(getIntent().getStringExtra("overview")!=null){
            title.setText(getIntent().getStringExtra("title"));
            overview.setText(getIntent().getStringExtra("overview"));
            getMovieVideo();
            getMovieReview();
            //Picasso.with(this).load("https://image.tmdb.org/t/p/w500"+getIntent().getStringExtra("image")).into(imageView);
        }
        youTubePlayerView.initialize("AIzaSyBs0f_klCz78MrPN6O4IeNGCh26xTtEK48",this);

    }

    private void getMovieReview() {

        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://api.themoviedb.org/3/discover/movie?api_key=49f959c35c9422840360168ba4f8026c&language=en-US&sort_by=popularity.desc";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(getIntent().getIntExtra("id",0)+"")
                .appendPath("reviews")
                .appendQueryParameter("api_key","49f959c35c9422840360168ba4f8026c")
                .appendQueryParameter("language","en-US");
        String url = builder.build().toString();



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("MSG",response);
                        parseReviewData(response);
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

    private void parseReviewData(String response) {
        try {
            JSONObject resultJSON = new JSONObject(response);
            JSONArray resultsArray = resultJSON.getJSONArray("results");
            listOfReviews = new ArrayList<>();


            for(int i=0;i<resultsArray.length();i++){
                JSONObject object =resultsArray.getJSONObject(i);
                ReviewModel reviewModel = new ReviewModel();
                String review= object.getString("content");
                String author= object.getString("author");
                reviewModel.setAuthor(author);
                reviewModel.setReview(review);
                listOfReviews.add(reviewModel);





                }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }
        updateReview();
    }

    private void updateReview() {
//        TextView textView = new TextView(this);
//        textView.setText(listOfReviews.get(0));
//        linearLayout.addView(textView);

        for(int i=0;i<listOfReviews.size();i++){
            ReviewModel reviewModel = listOfReviews.get(i);
            View view= LayoutInflater.from(this).inflate(R.layout.review_item,null);
            TextView name = view.findViewById(R.id.name);
            TextView theReview = view.findViewById(R.id.theReview);
            name.setText(reviewModel.getAuthor());
            theReview.setText(reviewModel.getReview());
            linearLayout.addView(view);
        }

    }


    private void getMovieVideo() {
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url ="https://api.themoviedb.org/3/discover/movie?api_key=49f959c35c9422840360168ba4f8026c&language=en-US&sort_by=popularity.desc";
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(getIntent().getIntExtra("id",0)+"")
                .appendPath("videos")
                .appendQueryParameter("api_key","49f959c35c9422840360168ba4f8026c")
                .appendQueryParameter("language","en-US");
         String url = builder.build().toString();



// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("MSG",response);
                        parseVideoData(response);
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

    private void parseVideoData(String response) {
        try {
            JSONObject resultJSON = new JSONObject(response);
            JSONArray resultsArray = resultJSON.getJSONArray("results");

            JSONObject object= (JSONObject) resultsArray.get(0);
            key= (String) object.getString("key");

            if(player!=null){
                player.cueVideo(key);
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        player = youTubePlayer;
        if(key!=null){
            player.cueVideo(key);
        }
        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.MINIMAL);

    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }
}



