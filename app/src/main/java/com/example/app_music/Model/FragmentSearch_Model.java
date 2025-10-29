package com.example.app_music.Model;

public class FragmentSearch_Model {
    private String title;
    private int imageResId;

    public FragmentSearch_Model(String title, int imageResId) {
        this.title = title;
        this.imageResId = imageResId;
    }

    public String getTitle() {
        return title;
    }

    public int getImageResId() {
        return imageResId;
    }
}
