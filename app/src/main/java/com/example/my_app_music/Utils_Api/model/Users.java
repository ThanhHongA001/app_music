package com.example.my_app_music.Utils_Api.model;

import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("id")
    private String id;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("email")
    private String email;

    // (Không nên lưu plaintext password ở production,
    // ở đây chỉ phục vụ demo học tập)
    @SerializedName("password")
    private String password;

    @SerializedName("created_at")
    private String createdAt;

    public Users() {
    }

    public Users(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
    }

    // ===== Getter/Setter =====

    public String getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
