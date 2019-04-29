package com.example.tevin.kamaqhekezalibrary.POJO;

import android.net.Uri;

public class Card {
    private Uri image;
    private  String title;
    private  String content;

    public Uri getImage() {
        return image;
    }

    public Card() {
    }

    public Card(String title, String content) {
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
