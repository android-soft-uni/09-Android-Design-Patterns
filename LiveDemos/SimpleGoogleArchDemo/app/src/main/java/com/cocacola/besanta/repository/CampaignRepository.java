package com.cocacola.besanta.repository;

import android.support.annotation.NonNull;

import com.cocacola.besanta.common.QueryLiveData;
import com.cocacola.besanta.model.Campaign;
import com.cocacola.besanta.ui.feed.Filters;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public final class CampaignRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public CampaignRepository(FirebaseFirestore store) {
        this.firestore = store;
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
