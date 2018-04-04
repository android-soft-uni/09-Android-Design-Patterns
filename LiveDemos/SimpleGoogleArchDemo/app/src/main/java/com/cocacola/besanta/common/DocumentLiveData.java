package com.cocacola.besanta.common;

import android.arch.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class DocumentLiveData<T> extends LiveData<Resource<T>>
        implements EventListener<DocumentSnapshot> {
    private final Class<T> type;
    private final DocumentReference ref;
    private ListenerRegistration registration;

    public DocumentLiveData(DocumentReference ref, Class<T> type) {
        this.ref = ref;
        this.type = type;
    }

    @Override
    public void onEvent(DocumentSnapshot snapshot, FirebaseFirestoreException e) {
        if (e != null) {
            setValue(new Resource<>(e));
            return;
        }
        if (snapshot.exists()) {
            setValue(new Resource<>(snapshot.toObject(type)));
        }
    }

    @Override
    protected void onActive() {
        super.onActive();
        registration = ref.addSnapshotListener(this);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        if (registration != null) {
            registration.remove();
            registration = null;
        }
    }
}