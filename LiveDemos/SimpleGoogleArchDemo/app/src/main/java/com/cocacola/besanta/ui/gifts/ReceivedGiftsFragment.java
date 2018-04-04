package com.cocacola.besanta.ui.gifts;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.adapter.ReceivedGiftAdapter;
import com.cocacola.besanta.databinding.FragmentGiftsReceivedBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Gift;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class ReceivedGiftsFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ReceivedGiftsViewModel viewModel;
    FragmentGiftsReceivedBinding binding;
    private ReceivedGiftAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_gifts_received, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ReceivedGiftsViewModel.class);

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
        adapter = new ReceivedGiftAdapter(this::onGiftSelected);
        binding.rvGifts.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.rvGifts.setAdapter(adapter);
    }

    public void onGiftSelected(Gift gift) {
        //TODO:
    }
}
