package com.cocacola.besanta.ui.feed.campaigns;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cocacola.besanta.R;
import com.cocacola.besanta.adapter.CampaignAdapter;
import com.cocacola.besanta.databinding.FragmentCampaignsBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Campaign;

import javax.inject.Inject;

public class CampaignsFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    CampaignsViewModel viewModel;
    FragmentCampaignsBinding binding;
    private CampaignAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_campaigns, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CampaignsViewModel.class);

        initRecycler();
        initSubscribers();
        fetchAllStories();
        return binding.getRoot();
    }

    private void fetchAllStories() {
        viewModel.setFilters(null);
    }

    private void initSubscribers() {
        viewModel.getCampaigns().observe(this, listResource -> {
            if (listResource.isSuccessful()) {
                adapter.replace(listResource.data());
            }
        });

        viewModel.getShouldShowNoInternetError().observe(this, shouldShowError -> {
            if (shouldShowError != null && shouldShowError) {
                Snackbar.make(binding.grpContainer,
                        getString(R.string.no_internet_message), Toast.LENGTH_LONG).show();
                viewModel.getShouldShowNoInternetError().setValue(false);
            }
        });

        viewModel.getShouldShowServerError().observe(this, shouldShowError -> {
            if (shouldShowError != null && shouldShowError) {
                Snackbar.make(binding.grpContainer,
                        getString(R.string.something_went_wrong), Toast.LENGTH_LONG).show();
                viewModel.getShouldShowNoInternetError().setValue(false);
            }
        });
    }

    private void initRecycler() {
        adapter = new CampaignAdapter(this::onCampaignSelected);
        binding.rvCampaigns.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCampaigns.setAdapter(adapter);
    }

    public void onCampaignSelected(Campaign campaign) {
        //TODO:
    }
}
