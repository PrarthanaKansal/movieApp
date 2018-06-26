package com.example.prekshasingla.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by prekshasingla on 26/06/18.
 */

public class DetailActivity extends AppCompatActivity{
    ImageView imageView;
    TextView title;
    TextView overview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView = findViewById(R.id.detail_image);
        title = findViewById(R.id.detail_title);
        overview = findViewById(R.id.overview);

        if(getIntent().getStringExtra("overview")!=null){
            title.setText(getIntent().getStringExtra("title"));
            overview.setText(getIntent().getStringExtra("overview"));
        }


    }


}
