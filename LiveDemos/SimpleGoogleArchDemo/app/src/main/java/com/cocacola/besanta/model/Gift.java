package com.cocacola.besanta.model;

import com.google.firebase.firestore.PropertyName;

import java.util.Date;

/**
 * Created by samui on 2/14/2018.
 */

public class Gift extends Model {

    public static final String FIELD_SENDER_ID = "sender_id";
    public static final String FIELD_RECEIVER_ID = "receiver_id";
    public static final String FIELD_USED = "used";
    public static final String FIELD_OPENED = "opened";
    public static final String FIELD_DATE = "date";

    @PropertyName(FIELD_SENDER_ID)
    public String senderId;
    @PropertyName(FIELD_RECEIVER_ID)
    public String receiverId;
    @PropertyName("receiver_first_name")
    public String receiverFirstName;
    @PropertyName("receiver_last_name")
    public String receiverLastName;
    @PropertyName("receiver_avatar_url")
    public String receiverAvatarUrl;
    public Date date;
    public boolean used;
    public boolean opened;
    public String message;
    @PropertyName("prize_name")
    public String prizeName;
    @PropertyName("prize_image_url")
    public String prizeImageUrl;

    public String getReceiverFullname() {
        return String.format("%s %s", null != receiverFirstName ? receiverFirstName : "",
            null != receiverLastName ? receiverLastName : "");
    }
}
