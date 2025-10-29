package com.example.app_music.Model;

public class FragmentTopMusic_Model {
    private String title;
    private int imageResId;

    public FragmentTopMusic_Model(String title, int imageResId) {
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
