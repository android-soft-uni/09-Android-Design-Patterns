package com.cocacola.besanta.ui.profile;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cocacola.besanta.R;
import com.cocacola.besanta.adapter.ProfileStoryAdapter;
import com.cocacola.besanta.databinding.FragmentProfileBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Story;

import javax.inject.Inject;

public class ProfileFragment extends Fragment implements Injectable {

    public static final String EXTRA_PROFILE_ID = "com.cocacola.besanta.ui.profile.EXTRA_ID";

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ProfileViewModel viewModel;
    FragmentProfileBinding binding;
    private ProfileStoryAdapter adapter;
    private TextView txtSantaPercent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_profile, container, false);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ProfileViewModel.class);
        initRecycler();
        observeData();
        return binding.getRoot();
    }

    private void observeData() {
        viewModel.getReceivedGifts().observe(this, listResource -> binding.txtReceivedTimes.setText(getString(R.string.received_gifts, listResource.data().size())));
        viewModel.getSentGifts().observe(this, listResource -> binding.txtSentTimes.setText(getString(R.string.sent_gifts, listResource.data().size())));
        viewModel.getUser().observe(this, user -> {
            Glide.with(getContext()).load(user.avatarUrl).into(binding.imgAvatar);
            binding.txtFirstName.setText(user.firstName);
            binding.txtLastName.setText(user.lastName);
            txtSantaPercent.setText(getString(R.string.percent_format, user.santaState));
        });
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

        binding.fabGift.setOnClickListener(view -> startActivity(new Intent()));
    }

    private void initRecycler() {
        adapter = new ProfileStoryAdapter(this::onStorySelected);
        binding.rvStories.setLayoutManager(new GridLayoutManager(getContext(), 6));
        binding.rvStories.setAdapter(adapter);
    }

    public void onStorySelected(Story story) {
        //TODO:
        // Go to the details screen for the selected story
        /*Intent intent = new Intent(getContext(), StoryDetailsActivity.class);
        intent.putExtra(StoryDetailsActivity.KEY_STORY_ID, story.id);

        startActivity(intent);*/
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getArguments() != null) {
            viewModel.receivedUserId(getArguments().getString(EXTRA_PROFILE_ID));
        } else {
            viewModel.receivedUserId(null);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.profile_menu, menu);  // Use filter.xml from step 1
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem item = menu.findItem(R.id.action_santa);
        LinearLayout rootView = (LinearLayout) item.getActionView();
        rootView.setOnClickListener(view -> onSantaPercentPressed());
        txtSantaPercent = rootView.findViewById(R.id.txt_santa_percent);
    }

    private void onSantaPercentPressed() {
        //TODO open the santa percent screen
    }
}
