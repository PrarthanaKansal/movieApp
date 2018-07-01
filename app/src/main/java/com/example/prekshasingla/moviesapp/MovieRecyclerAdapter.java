package com.example.prekshasingla.moviesapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Movie;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by prekshasingla on 26/06/18.
 */

public class MovieRecyclerAdapter extends RecyclerView.Adapter<MovieRecyclerAdapter.movieViewHolder> {

    private final Context context;
    private final ArrayList<movie> movieArrayList;
    movieViewHolder holder;

    public MovieRecyclerAdapter(Context context, ArrayList<movie> movieArrayList){
        this.context=context;
        this.movieArrayList = movieArrayList;

    }

    @NonNull
    @Override
    public movieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_layout,parent,false);
        holder = new movieViewHolder(view);
        return holder;


    }

    @Override
    public int getItemCount() {
        return movieArrayList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull movieViewHolder holder, int position) {
        movie Movie = movieArrayList.get(position);
        holder.title.setText(Movie.getTitle());

        Picasso.with(context).load("https://image.tmdb.org/t/p/w500"+Movie.getPosterPath()).into(holder.image);

    }

    public class movieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView image;
        TextView title;

        public movieViewHolder(View itemView) {

            super(itemView);
            image= itemView.findViewById(R.id.image);
            title = itemView.findViewById(R.id.title);
            itemView.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context,DetailActivity.class);
            movie Movie = movieArrayList.get(getAdapterPosition());
            intent.putExtra("title", Movie.getTitle());
            intent.putExtra("image",Movie.getPosterPath());
            intent.putExtra("overview",Movie.getOverview());
            intent.putExtra("id",Movie.getId());

            if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) context,
                        view.findViewById(R.id.title),"textTransition");
                Bundle bundle = ActivityOptionsCompat.makeCustomAnimation(context,android.R.anim.fade_in,android.R.anim.fade_out).toBundle();
                context.startActivity(intent,activityOptionsCompat.toBundle());
            }
            else{
                context.startActivity(intent);
            }



        }
    }


}
