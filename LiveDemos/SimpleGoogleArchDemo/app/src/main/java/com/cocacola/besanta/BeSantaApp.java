package com.cocacola.besanta;

import android.app.Activity;
import android.app.Application;

import com.cocacola.besanta.di.AppInjector;

import net.danlew.android.joda.JodaTimeAndroid;

import javax.inject.Inject;

import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import ly.img.android.PESDK;
import timber.log.Timber;


public final class BeSantaApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        JodaTimeAndroid.init(this);
        AppInjector.init(this);
        PESDK.init(this, "android_license");
    }

    @Override
    public DispatchingAndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
