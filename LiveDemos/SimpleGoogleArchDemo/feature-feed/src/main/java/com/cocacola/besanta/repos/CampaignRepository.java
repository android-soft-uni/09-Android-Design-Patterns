package com.cocacola.besanta.repos;

import android.support.annotation.NonNull;

import com.cocacola.besanta.feed.Filters;
import com.cocacola.besanta.model.Campaign;
import com.cocacola.besanta.model.QueryLiveData;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public final class CampaignRepository {

    private final FirebaseFirestore firestore;

    public CampaignRepository() {
        this.firestore = FirebaseFirestore.getInstance();
    }

    public QueryLiveData<Campaign> getCampaigns(@NonNull final Filters filters) {
        return new QueryLiveData<>(toQuery(filters), Campaign.class);
    }

    private Query toQuery(final Filters filters) {
        // Construct query basic query
        Query query = firestore.collection("campaigns");

        //TODO: add some filters if necessary
        return query;
    }
}
