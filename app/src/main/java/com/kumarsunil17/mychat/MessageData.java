package com.kumarsunil17.mychat;

import com.google.firebase.database.PropertyName;

public class MessageData {
    @PropertyName("id")
    private String id;

    public MessageData(String id) {
        this.id = id;
    }

    public MessageData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
