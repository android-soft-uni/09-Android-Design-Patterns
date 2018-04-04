package com.cocacola.besanta.di;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;


@Module
class FirebaseModule {

    @Singleton
    @Provides
    FirebaseFirestore providesFirestore() {
        FirebaseFirestore.setLoggingEnabled(true);
        return FirebaseFirestore.getInstance();
    }

    @Singleton
    @Provides
    @Named("stories")
    CollectionReference providesStories() {
        FirebaseFirestore.setLoggingEnabled(true);
        return FirebaseFirestore.getInstance().collection("stories");
    }

    @Singleton
    @Provides
    @Named("users")
    CollectionReference providesUsers() {
        FirebaseFirestore.setLoggingEnabled(true);
        return FirebaseFirestore.getInstance().collection("users");
    }
}
