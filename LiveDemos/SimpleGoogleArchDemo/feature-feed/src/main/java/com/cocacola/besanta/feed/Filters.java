package com.cocacola.besanta.feed;

import android.text.TextUtils;

import com.google.firebase.firestore.Query;

/**
 * Object for passing filters around.
 */
@SuppressWarnings("WeakerAccess")
public class Filters {

    private String userName = null;
    private Query.Direction sortDirection = null;

    public Filters() {
    }

    public static Filters getDefault() {
        Filters filters = new Filters();
        filters.setSortDirection(Query.Direction.DESCENDING);
        return filters;
    }

    public boolean hasUserName() {
        return !(TextUtils.isEmpty(userName));
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Query.Direction getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(Query.Direction sortDirection) {
        this.sortDirection = sortDirection;
    }
}
