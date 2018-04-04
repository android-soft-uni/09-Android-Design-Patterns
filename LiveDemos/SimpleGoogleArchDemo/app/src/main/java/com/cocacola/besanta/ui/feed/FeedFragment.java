package com.cocacola.besanta.ui.feed;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.FragmentFeedBinding;


public class FeedFragment extends Fragment {

    FragmentFeedBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_feed, container, false);
        setupTabs();
        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        setupTabs();
    }

    public void setupTabs() {
        FeedPagerAdapter adapter = new FeedPagerAdapter(getContext(), getChildFragmentManager());
        binding.viewpager.setAdapter(adapter);
        binding.grpTabs.setupWithViewPager(binding.viewpager);

    }
}
