package com.cocacola.besanta.adapter;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.cocacola.besanta.R;
import com.cocacola.besanta.common.DataListAdapter;
import com.cocacola.besanta.common.OnItemClickedListener;
import com.cocacola.besanta.databinding.ItemProfileStoryBinding;
import com.cocacola.besanta.databinding.ItemStoryBinding;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.ui.profile.ProfileFragment;

public final class ProfileStoryAdapter extends DataListAdapter<Story, ItemProfileStoryBinding> {

    public ProfileStoryAdapter(OnItemClickedListener<Story> listener) {
        super(listener);
    }

    @Override
    protected ItemProfileStoryBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemProfileStoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_profile_story, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final Story chosen = binding.getStory();
            if (chosen != null) {
                listener.onClicked(chosen);
            }
        });

        return binding;
    }

    @Override
    protected void bind(ItemProfileStoryBinding binding, Story item) {
        binding.setStory(item);
    }
}
