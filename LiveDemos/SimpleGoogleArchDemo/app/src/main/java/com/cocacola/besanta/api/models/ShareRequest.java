package com.cocacola.besanta.api.models;

import com.google.gson.annotations.SerializedName;

public class ShareRequest {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("story_id")
    private String storyId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoryId() {
        return storyId;
    }

    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
}