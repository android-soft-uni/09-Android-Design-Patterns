package com.cocacola.besanta.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.cocacola.besanta.ui.createstory.CreateStoryViewModel;
import com.cocacola.besanta.ui.feed.campaigns.CampaignsViewModel;
import com.cocacola.besanta.ui.feed.stories.StoriesViewModel;
import com.cocacola.besanta.ui.gifts.ReceivedGiftsViewModel;
import com.cocacola.besanta.ui.gifts.SentGiftsViewModel;
import com.cocacola.besanta.ui.login.LoginViewModel;
import com.cocacola.besanta.ui.profile.ProfileViewModel;
import com.cocacola.besanta.ui.scanner.CouponViewModel;
import com.cocacola.besanta.ui.scanner.ScannerViewModel;
import com.cocacola.besanta.ui.sendgift.MessageViewModel;
import com.cocacola.besanta.ui.sendgift.PrizesViewModel;
import com.cocacola.besanta.util.ViewModelFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@SuppressWarnings("WeakerAccess")
@Module
public abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(StoriesViewModel.class)
    abstract ViewModel bindStoriesViewModel(StoriesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ReceivedGiftsViewModel.class)
    abstract ViewModel bindReceivedGiftsViewModel(ReceivedGiftsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SentGiftsViewModel.class)
    abstract ViewModel bindSentGiftsViewModel(SentGiftsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CampaignsViewModel.class)
    abstract ViewModel bindCampaignsViewModel(CampaignsViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel.class)
    abstract ViewModel bindLoginViewModel(LoginViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ScannerViewModel.class)
    abstract ViewModel bindScannerViewModel(ScannerViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CouponViewModel.class)
    abstract ViewModel bindCouponViewModel(CouponViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(CreateStoryViewModel.class)
    abstract ViewModel bindCreateStoryViewModel(CreateStoryViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PrizesViewModel.class)
    abstract ViewModel bindPrizesViewModel(PrizesViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MessageViewModel.class)
    abstract ViewModel bindMessageViewModel(MessageViewModel viewModel);
    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
