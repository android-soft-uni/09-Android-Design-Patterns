package com.cocacola.besanta.adapter;

import android.app.Application;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cocacola.besanta.R;
import com.cocacola.besanta.ui.gifts.SentGiftsFragment;
import com.cocacola.besanta.ui.sendgift.PrizesFragment;

/**
 * Created by samui on 2/14/2018.
 */

public class GiftsPagerAdapter extends FragmentPagerAdapter {
    private static final int POS_SENT_GIFTS = 0;
    private static final int POS_RECEIVED_GIFTS = 1;

    private Application application;

    public GiftsPagerAdapter(Application application, FragmentManager fm) {
        super(fm);
        this.application = application;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment;
        switch (position) {
            case POS_SENT_GIFTS: {
                fragment = new SentGiftsFragment();
                break;
            }
            case POS_RECEIVED_GIFTS: {
                fragment = new PrizesFragment();
                break;
            }
            default: {
                fragment = new SentGiftsFragment();
            }
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title;
        switch (position) {
            case POS_SENT_GIFTS: {
                title = application.getString(R.string.sent);
                break;
            }
            case POS_RECEIVED_GIFTS: {
                title = application.getString(R.string.received);
                break;
            }
            default: {
                title = application.getString(R.string.sent);
            }
        }
        return title;
    }
}
