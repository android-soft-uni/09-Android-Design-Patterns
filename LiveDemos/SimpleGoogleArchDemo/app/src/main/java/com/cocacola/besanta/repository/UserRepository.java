package com.cocacola.besanta.repository;

import com.cocacola.besanta.common.CompletionLiveData;
import com.cocacola.besanta.common.QueryLiveData;
import com.cocacola.besanta.model.LikedStoryModel;
import com.cocacola.besanta.model.User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public final class UserRepository {
    private final CollectionReference users;

    @Inject
    public UserRepository(@Named("users") CollectionReference users) {
        this.users = users;
    }

    private Query queryLikedStories(final String userId) {
        return users.document(userId).collection("liked_stories");
    }

    public QueryLiveData<LikedStoryModel> getLikedStories(final String userId) {
        return new QueryLiveData<>(queryLikedStories(userId), LikedStoryModel.class);
    }

    private Task<QuerySnapshot> getUser(final User user) {
        return users.whereEqualTo(User.EMAIL_FIELD, user.email).get();
    }

    public void isUserRegistered(User user, IsUserRegisteredCallback isUserRegisteredCallback) {
        getUser(user).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot document = task.getResult();
                if (document != null && !document.isEmpty()) {
                    isUserRegisteredCallback.onFound();
                } else {
                    isUserRegisteredCallback.onNotFound();
                }
            }
        });
    }

    public void getUserById(String userId, OnReceivedListener<User> listener) {
        Timber.d("user id:" + userId);
        users.document(userId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful() && task.getResult().exists()) {
                User user = task.getResult().toObject(User.class);
                listener.onReceived(user);
            }
        });
    }

    public CompletionLiveData addUser(final User user, String userId) {
        final CompletionLiveData completion = new CompletionLiveData();
        addUser(users.document(), user, userId).addOnCompleteListener(completion);
        return completion;
    }

    private Task<Void> addUser(final DocumentReference userRef, final User user, String userId) {
        return userRef.getFirestore().collection("users").document(userId)
                .set(user, SetOptions.merge());
    }

    public interface IsUserRegisteredCallback {
        void onFound();

        void onNotFound();
    }

    public interface OnReceivedListener<T> {
        void onReceived(T data);
    }
}
