package com.example.tevin.kamaqhekezalibrary.POJO;

import android.net.Uri;

public class Story {

    private String title;
    private Uri image;
    private String content;

    public Story() {
    }

    public Story(String title, Uri image, String content) {
        this.title = title;
        this.image = image;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public Uri getImage() {
        return image;
    }

    public String getContent() {
        return content;
    }
}
