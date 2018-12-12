package com.example.tevin.kamaqhekezalibrary.model;

import android.net.Uri;

public class CardModel {
    private Uri image;
    private  String title;
    private  String content;

    public Uri getImage() {
        return image;
    }

    public CardModel() {
    }

    public CardModel(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void setImage(Uri image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
