package com.cocacola.besanta.model;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;

import java.util.Date;

/**
 * Story POJO.
 */
@IgnoreExtraProperties
public class Story extends Model {

    public static final String FIELD_USER_FULL_NAME = "user_full_name";

    @PropertyName("user_id")
    public String userId;
    @PropertyName("user_first_name")
    public String userFirstName;
    @PropertyName("user_last_name")
    public String userLastName;
    @PropertyName("user_image_url")
    public String userImageUrl;
    @PropertyName("image_url")
    public String imageUrl;
    @PropertyName("timestamp")
    public Date timestamp;
    @PropertyName("text")
    public String text;
    @PropertyName("likes_count")
    public int likesCount;
    @Exclude
    public boolean isLiked;

    public Story() {
    }

    public Story(String imageUrl, String text, int numLikes) {
        this.imageUrl = imageUrl;
        this.text = text;
        this.likesCount = numLikes;
    }

    public String getUserFullName() {
        return userFirstName + " " + userLastName;
    }

    public String getLikesCountText() {
        return String.valueOf(likesCount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Story story = (Story) o;

        if (likesCount != story.likesCount) return false;
        if (isLiked != story.isLiked) return false;
        if (userId != null ? !userId.equals(story.userId) : story.userId != null) return false;
        if (userFirstName != null ? !userFirstName.equals(story.userFirstName) : story.userFirstName != null)
            return false;
        if (userLastName != null ? !userLastName.equals(story.userLastName) : story.userLastName != null)
            return false;
        if (userImageUrl != null ? !userImageUrl.equals(story.userImageUrl) : story.userImageUrl != null)
            return false;
        if (imageUrl != null ? !imageUrl.equals(story.imageUrl) : story.imageUrl != null)
            return false;
        if (timestamp != null ? !timestamp.equals(story.timestamp) : story.timestamp != null)
            return false;
        return text != null ? text.equals(story.text) : story.text == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (userFirstName != null ? userFirstName.hashCode() : 0);
        result = 31 * result + (userLastName != null ? userLastName.hashCode() : 0);
        result = 31 * result + (userImageUrl != null ? userImageUrl.hashCode() : 0);
        result = 31 * result + (imageUrl != null ? imageUrl.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + likesCount;
        result = 31 * result + (isLiked ? 1 : 0);
        return result;
    }
}
