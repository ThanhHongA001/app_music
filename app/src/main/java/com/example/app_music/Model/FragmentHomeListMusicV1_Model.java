package com.example.app_music.Model;

public class FragmentHomeListMusicV1_Model {
    private String albumName;
    private int albumImageResId;

    public FragmentHomeListMusicV1_Model(String albumName, int albumImageResId) {
        this.albumName = albumName;
        this.albumImageResId = albumImageResId;
    }

    public String getAlbumName() {
        return albumName;
    }

    public int getAlbumImageResId() {
        return albumImageResId;
    }
}
