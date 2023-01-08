package com.example.fourmencoffee.classify;

import android.media.Image;

public class Classify {
    private String name;
    private String sub_title;
    private String title;
    private String url;

    public Classify() {
    }

    public Classify(String name, String sub_title, String title, String url) {
        this.name = name;
        this.sub_title = sub_title;
        this.title = title;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
