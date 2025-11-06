package com.example.my_app_music.Utils_Api.model;

import com.google.gson.annotations.SerializedName;

public class Song {

    @SerializedName("id")
    public int id;

    @SerializedName("title")
    public String title;

    @SerializedName("album_name")
    public String album_name;

    @SerializedName("artist_name")
    public String artist_name;

    @SerializedName("genre")
    public String genre;

    @SerializedName("duration")
    public int duration;

    @SerializedName("mp3_url")
    public String mp3_url;

    @SerializedName("song_avatar_url")
    public String song_avatar_url;

    @SerializedName("album_avatar_url")
    public String album_avatar_url;

    @SerializedName("language")
    public String language;

    @SerializedName("mood")
    public String mood;

    @SerializedName("release_date")
    public String release_date;
}
