package com.example.app_music.Model;

/**
 * 🧩 Model đại diện cho 1 album (thể loại nhạc)
 */
public class FragmentHomeListMusicV3_Model {
    private final String albumName;
    private final int albumImageResId;

    public FragmentHomeListMusicV3_Model(String albumName, int albumImageResId) {
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
