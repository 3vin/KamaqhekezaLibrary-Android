package com.example.tevin.kamaqhekezalibrary;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tevin.kamaqhekezalibrary.model.Upload;

import java.util.List;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.CustomViewHolder> {

    private Context context;
    private List news;
    private LayoutInflater inflater;

    static final String TITLE ="title";
    static final String AUTHOR ="author";
    static final String IMAGE_URL ="imgUrl";
    static final String FULL_STORY ="fullStory";

    public RecyclerAdapter(Context context, List<Upload> news) {
        inflater = LayoutInflater.from(context);
        this.context = context;
        this.news = news;
    }

    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_card, parent, false);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final CustomViewHolder holder, final int position) {

        final Upload current = (Upload) news.get((news.size()-1)-position);

            Glide
               .with(context)
                .load(current.getImageUrl())
                .apply(new RequestOptions().centerCrop())
                .into(holder.ivImg);

        holder.tvTitle.setText(current.getName());
        holder.tvReadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReadMoreActivity.class);
                intent.putExtra(TITLE, current.getName());
                intent.putExtra(IMAGE_URL, current.getImageUrl());
                intent.putExtra(FULL_STORY, current.getContent());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return news.size();
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitle;
        private TextView tvReadMore;
        private ImageView ivImg;

        public CustomViewHolder(View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvCardTitle);
            ivImg = itemView.findViewById(R.id.ivCard);
            tvReadMore = itemView.findViewById(R.id.tvCardStory);
            tvReadMore.setClickable(true);
        }

    }
}
