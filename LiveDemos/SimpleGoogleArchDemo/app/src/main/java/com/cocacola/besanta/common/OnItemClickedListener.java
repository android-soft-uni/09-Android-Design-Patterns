package com.cocacola.besanta.common;

import android.support.v7.widget.RecyclerView;

/**
 * General interface callback for handling clicks inside {@link RecyclerView}
 */

public interface OnItemClickedListener<T> {
    void onClicked(T item);
}
