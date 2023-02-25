package com.example.fourmencoffee.classify;

import android.media.Image;

import java.io.Serializable;

public class Classify implements Serializable {
    private String name;
    private String sub_title;
    private String sub_title1;
    private String sub_title2;
    private String sub_title3;
    private String title;
    private String url;

    public Classify(String name, String sub_title, String sub_title1, String sub_title2, String sub_title3, String title, String url) {
        this.name = name;
        this.sub_title = sub_title;
        this.sub_title1 = sub_title1;
        this.sub_title2 = sub_title2;
        this.sub_title3 = sub_title3;
        this.title = title;
        this.url = url;
    }

    public Classify() {
    }

    public String getSub_title1() {
        return sub_title1;
    }

    public void setSub_title1(String sub_title1) {
        this.sub_title1 = sub_title1;
    }

    public String getSub_title2() {
        return sub_title2;
    }

    public void setSub_title2(String sub_title2) {
        this.sub_title2 = sub_title2;
    }

    public String getSub_title3() {
        return sub_title3;
    }

    public void setSub_title3(String sub_title3) {
        this.sub_title3 = sub_title3;
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
