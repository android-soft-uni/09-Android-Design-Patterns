package com.cocacola.besanta.repos;

import android.support.annotation.NonNull;

import com.cocacola.besanta.feed.Filters;
import com.cocacola.besanta.model.QueryLiveData;
import com.cocacola.besanta.model.Story;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public final class StoryRepository {

    private final FirebaseFirestore firestore;

    public StoryRepository(FirebaseFirestore store) {
        this.firestore = store;
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

}
