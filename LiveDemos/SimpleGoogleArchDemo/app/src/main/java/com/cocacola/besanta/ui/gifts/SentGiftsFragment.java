package com.cocacola.besanta.ui.gifts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.adapter.SentGiftAdapter;
import com.cocacola.besanta.common.VerticalSpaceItemDecoration;
import com.cocacola.besanta.databinding.FragmentGiftsSentBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Gift;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class SentGiftsFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    SentGiftsViewModel viewModel;
    FragmentGiftsSentBinding binding;
    private SentGiftAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_gifts_sent, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SentGiftsViewModel.class);

        initRecycler();
        initSubscribers();
        return binding.getRoot();
    }

    private void initSubscribers() {
        viewModel.getGifts().observe(this, listResource -> {
            if (listResource.isSuccessful()) {
                adapter.replace(listResource.data());
            }
        });
    }

    private void initRecycler() {
        adapter = new SentGiftAdapter(this::onGiftSelected);
        binding.rvGifts.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.rvGifts.setAdapter(adapter);

        Context context = binding.rvGifts.getContext();
        int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, context.getResources()
            .getDisplayMetrics());
        binding.rvGifts.addItemDecoration(new VerticalSpaceItemDecoration(margin));
    }

    public void onGiftSelected(Gift gift) {
        //TODO:
    }
}
