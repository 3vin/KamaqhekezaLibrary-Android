package com.example.tevin.kamaqhekezalibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import static com.example.tevin.kamaqhekezalibrary.RecyclerAdapter.FULL_STORY;
import static com.example.tevin.kamaqhekezalibrary.RecyclerAdapter.IMAGE_URL;

public class ReadMoreActivity extends AppCompatActivity {

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_more);

        intent = getIntent();
        String title = intent.getStringExtra(RecyclerAdapter.TITLE);
        String imageUrl = intent.getStringExtra(IMAGE_URL);
        String fullStory = intent.getStringExtra(FULL_STORY);

        TextView tvTitle = findViewById(R.id.tvReadMore);
        ImageView ivImage = findViewById(R.id.ivReadStory);
        TextView tvFullStory = findViewById(R.id.tvFullStory);

        tvTitle.setText(title);

        Glide.with(this)
                .load(imageUrl)
                .apply(new RequestOptions().centerInside())
                .into(ivImage);

        /*String[] words = fullStory.split(" ");
        fullStory="";
        for(int i=0;i<words.length;i++){

            if(i%5==0){
                fullStory+=words[i]+"\n";
            }else{
                fullStory+=words[i]+" ";
            }
        }*/

        tvFullStory.setText(fullStory);

    }
}
