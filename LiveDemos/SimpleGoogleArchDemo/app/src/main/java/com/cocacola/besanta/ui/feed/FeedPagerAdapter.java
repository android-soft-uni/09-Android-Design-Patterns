package com.cocacola.besanta.ui.feed;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cocacola.besanta.R;
import com.cocacola.besanta.ui.feed.campaigns.CampaignsFragment;
import com.cocacola.besanta.ui.feed.stories.StoriesFragment;

public class FeedPagerAdapter extends FragmentPagerAdapter {

    private Context context;

    public FeedPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new StoriesFragment();
            case 1:
                return new CampaignsFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        switch (position) {
            case 0:
                return context.getString(R.string.stories);
            case 1:
                return context.getString(R.string.campaigns);
            default:
                return null;
        }
    }

}
