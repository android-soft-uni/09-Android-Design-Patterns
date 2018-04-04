package com.cocacola.besanta.repository;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.cocacola.besanta.api.models.ErrorMessage;
import com.cocacola.besanta.common.QueryLiveData;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.model.User;
import com.cocacola.besanta.ui.feed.Filters;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.joda.time.DateTime;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;


public final class StoryRepository {

    private static final String STORAGE_REF_URL = "gs://ko-hackathon-mentormate.appspot.com";
    private final FirebaseFirestore firestore;
    private CollectionReference stories;

    @Inject
    public StoryRepository(FirebaseFirestore store, @Named("stories") CollectionReference stories) {
        this.firestore = store;
        this.stories = stories;
    }

    public QueryLiveData<Story> getStories(@NonNull final Filters filters) {
        return new QueryLiveData<>(toQuery(filters), Story.class);
    }

    private Query toQuery(final Filters filters) {
        // Construct query basic query
        Query query = firestore.collection("stories");

        if (filters != null) {
            if (filters.hasUserName()) {
                query = query.whereEqualTo(Story.FIELD_USER_FULL_NAME, filters.getUserName());
            }
        }
        return query;
    }

    public void uploadStory(String url, String text, User user, StoryCallback callback) {
        Uri imagePath = Uri.fromFile(new File(url));
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl(STORAGE_REF_URL);
        StorageReference storiesRef = storageRef.child("stories/" + imagePath.getLastPathSegment());
        UploadTask uploadTask = storiesRef.putFile(imagePath);

        uploadTask
                .addOnFailureListener(exception -> {
                    callback.onUploadFailure(new ErrorMessage(ErrorMessage.ErrorType.SERVER_ERROR));
                })
                .addOnSuccessListener(
                        taskSnapshot -> {
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            Story story = new Story(downloadUrl.toString(), text, 0);
                            story.userId = user.id;
                            story.timestamp = DateTime.now().toDate();
                            story.userFirstName = user.firstName;
                            story.userLastName = user.lastName;
                            story.userImageUrl = user.avatarUrl;
                            stories.document().set(story).addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    callback.onUploadSuccess();
                                } else {
                                    callback.onUploadFailure(new ErrorMessage(ErrorMessage.ErrorType.SERVER_ERROR));
                                }
                            });
                        });
    }

    public interface StoryCallback {
        void onUploadSuccess();

        void onUploadFailure(ErrorMessage errorMessage);
    }
}
