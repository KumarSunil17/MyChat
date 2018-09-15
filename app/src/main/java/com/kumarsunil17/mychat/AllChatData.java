package com.kumarsunil17.mychat;

import com.google.firebase.database.PropertyName;

public class AllChatData {
    @PropertyName("id")
    private String id;

    public AllChatData() {
    }

    public AllChatData(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
