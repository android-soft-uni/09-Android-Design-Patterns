package com.cocacola.besanta.model;

import com.google.firebase.firestore.PropertyName;

public class LikedStoryModel extends Model{
    @PropertyName("story_id")
    public String storyId;
}
