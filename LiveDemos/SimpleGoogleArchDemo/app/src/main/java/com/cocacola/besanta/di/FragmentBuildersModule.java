package com.cocacola.besanta.di;

import com.cocacola.besanta.ui.feed.FeedFragment;
import com.cocacola.besanta.ui.feed.campaigns.CampaignsFragment;
import com.cocacola.besanta.ui.feed.stories.StoriesFragment;
import com.cocacola.besanta.ui.gifts.GiftsFragment;
import com.cocacola.besanta.ui.gifts.ReceivedGiftsFragment;
import com.cocacola.besanta.ui.gifts.SentGiftsFragment;
import com.cocacola.besanta.ui.profile.ProfileFragment;
import com.cocacola.besanta.ui.scanner.CouponDetailFragment;
import com.cocacola.besanta.ui.scanner.ScannerFragment;
import com.cocacola.besanta.ui.sendgift.MessageFragment;
import com.cocacola.besanta.ui.sendgift.PrizesFragment;
import com.cocacola.besanta.ui.sendgift.ThankYouFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract FeedFragment contributeFeedFragment();

    @ContributesAndroidInjector
    abstract GiftsFragment contributeGiftsFragment();

    @ContributesAndroidInjector
    abstract CampaignsFragment contributeCampaignsFragment();

    @ContributesAndroidInjector
    abstract SentGiftsFragment contributeSentGiftsFragment();

    @ContributesAndroidInjector
    abstract ReceivedGiftsFragment contributeReceivedGiftsFragment();

    @ContributesAndroidInjector
    abstract StoriesFragment contributeStoriesFragment();

    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();

    @ContributesAndroidInjector
    abstract ScannerFragment contributeScannerFragment();

    @ContributesAndroidInjector
    abstract CouponDetailFragment contributeCouponDetailFragment();

    @ContributesAndroidInjector
    abstract PrizesFragment contributePrizesFragment();

    @ContributesAndroidInjector
    abstract MessageFragment contributeMessageFragment();

    @ContributesAndroidInjector
    abstract ThankYouFragment contributeThankYouFragment();

}
