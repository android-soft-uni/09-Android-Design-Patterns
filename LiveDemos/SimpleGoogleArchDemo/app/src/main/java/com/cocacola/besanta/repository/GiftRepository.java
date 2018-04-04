package com.cocacola.besanta.repository;

import com.cocacola.besanta.common.QueryLiveData;
import com.cocacola.besanta.model.Gift;
import com.cocacola.besanta.model.Prize;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Date;

import javax.inject.Inject;

public final class GiftRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public GiftRepository(FirebaseFirestore store) {
        this.firestore = store;
    }

    public QueryLiveData<Gift> getReceivedGifts() {
        Query query = getGiftsQuery();

        FirebaseUser user = FirebaseAuth.getInstance()
            .getCurrentUser();
        query = query.whereEqualTo(Gift.FIELD_RECEIVER_ID, null != user ? user.getUid() : null)
            .orderBy(Gift.FIELD_OPENED)
            .orderBy(Gift.FIELD_USED)
            .orderBy(Gift.FIELD_DATE, Query.Direction.DESCENDING);

        return new QueryLiveData<>(query, Gift.class);
    }

    public QueryLiveData<Gift> getReceivedGifts(String userId) {
        Query query = getGiftsQuery();

        query = query.whereEqualTo(Gift.FIELD_RECEIVER_ID, userId);

        return new QueryLiveData<>(query, Gift.class);
    }

    public QueryLiveData<Gift> getSentGifts() {
        Query query = getGiftsQuery();

        FirebaseUser user = FirebaseAuth.getInstance()
            .getCurrentUser();
        query = query.whereEqualTo(Gift.FIELD_SENDER_ID, null != user ? user.getUid() : null)
            .orderBy(Gift.FIELD_DATE, Query.Direction.DESCENDING);
        return new QueryLiveData<>(query, Gift.class);
    }

    public QueryLiveData<Gift> getSentGifts(String userId) {
        Query query = getGiftsQuery();

        query = query.whereEqualTo(Gift.FIELD_SENDER_ID, userId)
                .orderBy(Gift.FIELD_DATE, Query.Direction.DESCENDING);
        return new QueryLiveData<>(query, Gift.class);
    }

    public void sendGift(String senderId, String receiverId, String receiverFirstName,
        String receiverLastName, String receiverAvatarUrl, String message, Prize prize,
        OnSuccessListener<DocumentReference> onSuccessListener,
        OnFailureListener onFailureListener) {
        Gift gift = new Gift();
        gift.used = false;
        gift.opened = false;
        gift.date = new Date();
        gift.message = message;
        gift.prizeImageUrl = prize.imageUrl;
        gift.prizeName = prize.name;
        gift.receiverAvatarUrl = receiverAvatarUrl;
        gift.receiverFirstName = receiverFirstName;
        gift.receiverLastName = receiverLastName;
        gift.receiverId = receiverId;
        gift.senderId = senderId;
        firestore.collection("gifts")
            .add(gift)
            .addOnSuccessListener(onSuccessListener)
            .addOnFailureListener(onFailureListener);
    }

    private Query getGiftsQuery() {
        return firestore.collection("gifts");
    }
}
