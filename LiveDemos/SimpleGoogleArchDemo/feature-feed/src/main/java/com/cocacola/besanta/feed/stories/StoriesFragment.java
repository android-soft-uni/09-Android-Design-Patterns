package com.cocacola.besanta.feed.stories;


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

import com.cocacola.besanta.feed.StoryAdapter;
import com.cocacola.besanta.repos.StoryRepository;
import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.FragmentStoriesBinding;
import com.google.firebase.firestore.FirebaseFirestore;

public class StoriesFragment extends Fragment {

    StoriesViewModel viewModel;
    FragmentStoriesBinding binding;
    private StoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_stories, container, false);

        StoriesViewModel.Factory factory = new StoriesViewModel.Factory(new StoryRepository(
            FirebaseFirestore.getInstance()));

        viewModel = ViewModelProviders.of(this, factory)
            .get(StoriesViewModel.class);

        initRecycler();
        initListeners();
        initSubscribers();
        fetchAllStories();
        return binding.getRoot();
    }

    private void initListeners() {
    }

    private void fetchAllStories() {
        viewModel.setFilters(null);
    }

    private void initSubscribers() {
        viewModel.getStories().observe(this, listResource -> {
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
        adapter = new StoryAdapter();
        binding.rvStories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvStories.setAdapter(adapter);
    }

}
