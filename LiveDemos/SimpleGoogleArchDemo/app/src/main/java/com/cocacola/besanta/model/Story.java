package com.cocacola.besanta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.Exclude;
import com.google.firebase.firestore.IgnoreExtraProperties;
import com.google.firebase.firestore.PropertyName;

import java.util.Date;

/**
 * Story POJO.
 */
@IgnoreExtraProperties
public class Story extends Model implements Parcelable {

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
    @Exclude
    public String getLikesCount(){
        return String.valueOf(likesCount);
    }

    protected Story(Parcel in) {
        userId = in.readString();
        userFirstName = in.readString();
        userLastName = in.readString();
        userImageUrl = in.readString();
        imageUrl = in.readString();
        text = in.readString();
        likesCount = in.readInt();
        isLiked = in.readByte() != 0;
    }

    public static final Creator<Story> CREATOR = new Creator<Story>() {
        @Override
        public Story createFromParcel(Parcel in) {
            return new Story(in);
        }

        @Override
        public Story[] newArray(int size) {
            return new Story[size];
        }
    };

    @Exclude
    public String getUserFullName() {
        return userFirstName + " " + userLastName;
    }

    @Exclude
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userId);
        dest.writeString(userFirstName);
        dest.writeString(userLastName);
        dest.writeString(userImageUrl);
        dest.writeString(imageUrl);
        dest.writeString(text);
        dest.writeInt(likesCount);
        dest.writeByte((byte) (isLiked ? 1 : 0));
    }
}
