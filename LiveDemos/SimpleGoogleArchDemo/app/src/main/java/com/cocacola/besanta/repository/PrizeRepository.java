package com.cocacola.besanta.repository;

import com.cocacola.besanta.common.QueryLiveData;
import com.cocacola.besanta.model.Prize;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import javax.inject.Inject;

public final class PrizeRepository {

    private final FirebaseFirestore firestore;

    @Inject
    public PrizeRepository(FirebaseFirestore store) {
        this.firestore = store;
    }

    public QueryLiveData<Prize> getPrizes() {
        Query query = firestore.collection("prizes");

        query = query.orderBy(Prize.FIELD_PRICE, Query.Direction.ASCENDING);
        return new QueryLiveData<>(query, Prize.class);
    }
}
