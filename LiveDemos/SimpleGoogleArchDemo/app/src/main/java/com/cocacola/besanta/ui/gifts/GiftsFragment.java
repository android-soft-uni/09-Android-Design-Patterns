package com.cocacola.besanta.ui.gifts;

import android.app.Application;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.adapter.GiftsPagerAdapter;
import com.cocacola.besanta.databinding.FragmentGiftsBinding;
import com.cocacola.besanta.di.Injectable;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class GiftsFragment extends Fragment implements Injectable {

    @Inject
    Application application;
    FragmentGiftsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_gifts, container, false);
        
        initPager();
        return binding.getRoot();
    }

    private void initPager() {
        binding.pager.setAdapter(new GiftsPagerAdapter(application, getChildFragmentManager()));
        binding.tabs.setupWithViewPager(binding.pager);
    }
}
