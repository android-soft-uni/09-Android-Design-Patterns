package com.cocacola.besanta.ui.feed.stories;


import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
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
import com.cocacola.besanta.adapter.StoryAdapter;
import com.cocacola.besanta.databinding.FragmentStoriesBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.LikedStoryModel;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.ui.createstory.CreateStoryActivity;
import com.cocacola.besanta.ui.sendgift.SendGiftActivity;

import javax.inject.Inject;

public class StoriesFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    StoriesViewModel viewModel;
    FragmentStoriesBinding binding;
    private StoryAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_stories, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(StoriesViewModel.class);

        initRecycler();
        initListeners();
        initSubscribers();
        fetchAllStories();
        return binding.getRoot();
    }

    private void initListeners() {
        binding.btnAddStory.setOnClickListener(view -> {
            openStoryCaptureScreen();
        });
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

        viewModel.getLikedStories().observe(this, listResource -> {
            if (listResource.isSuccessful()) {
                for (LikedStoryModel item : listResource.data()) {
                    if (adapter != null && adapter.getItems() != null) {
                        for (int i = 0; i < adapter.getItems().size(); i++) {
                            if (adapter.getItems().get(i).id.equals(item.id)) {
                                adapter.getItems().get(i).isLiked = true;
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
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
        adapter = new StoryAdapter(this::onStorySelected, this::onStoryLiked, this::onGiftClicked);
        binding.rvStories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvStories.setAdapter(adapter);
    }

    public void onStoryLiked(Story story) {
        viewModel.onStoryLiked(story);
    }

    public void onGiftClicked(Story story) {
        startActivity(
            SendGiftActivity.createIntent(getActivity(), story.userId, story.userFirstName,
                story.userLastName, story.userImageUrl));
    }

    public void onStorySelected(Story story) {
        //TODO:
        // Go to the details screen for the selected story
        /*Intent intent = new Intent(getContext(), StoryDetailsActivity.class);
        intent.putExtra(StoryDetailsActivity.KEY_STORY_ID, story.id);

        startActivity(intent);*/
    }

    public void openStoryCaptureScreen() {
        startActivity(new Intent(getContext(), CreateStoryActivity.class));
    }
}
