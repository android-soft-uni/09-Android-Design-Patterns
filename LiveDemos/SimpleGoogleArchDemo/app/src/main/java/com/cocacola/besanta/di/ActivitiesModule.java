package com.cocacola.besanta.di;

import com.cocacola.besanta.ui.MainActivity;
import com.cocacola.besanta.ui.createstory.CreateStoryActivity;
import com.cocacola.besanta.ui.createstory.ImageCaptureActivity;
import com.cocacola.besanta.ui.login.LoginActivity;
import com.cocacola.besanta.ui.scanner.CouponDetailActivity;
import com.cocacola.besanta.ui.sendgift.SendGiftActivity;
import com.cocacola.besanta.ui.sendgift.MessageActivity;
import com.cocacola.besanta.ui.sendgift.ThankYouActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Injects {@link android.app.Activity}s
 */

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributesMainActivity();

    @ContributesAndroidInjector
    abstract LoginActivity contributesLoginActivity();

    @ContributesAndroidInjector
    abstract CreateStoryActivity contributesCreateStoryActivity();

    @ContributesAndroidInjector
    abstract ImageCaptureActivity contributesImageCaptureActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract CouponDetailActivity contributesCouponDetailActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract SendGiftActivity contributesSendGiftActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MessageActivity contributesMessageActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract ThankYouActivity contributesThankYouActivity();
}
