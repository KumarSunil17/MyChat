package com.kumarsunil17.mychat;

import com.google.firebase.database.PropertyName;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserData {
    @PropertyName("Name")
    private String name;
    @PropertyName("Email")
    private String email;
    @PropertyName("dp")
    private String dp;

    public UserData(String name, String email, String dp) {
        this.name = name;
        this.email = email;
        this.dp = dp;
    }

    public UserData() {
    }

    public String getDp() {
        return dp;
    }

    public void setDp(String dp) {
        this.dp = dp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
