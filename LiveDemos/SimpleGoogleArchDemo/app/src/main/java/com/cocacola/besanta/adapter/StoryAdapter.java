package com.cocacola.besanta.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import com.cocacola.besanta.R;
import com.cocacola.besanta.common.DataListAdapter;
import com.cocacola.besanta.common.OnItemClickedListener;
import com.cocacola.besanta.databinding.ItemStoryBinding;
import com.cocacola.besanta.model.Story;


public final class StoryAdapter extends DataListAdapter<Story, ItemStoryBinding> {
    private OnItemClickedListener<Story> likeListener;
    private OnItemClickedListener<Story> giftListener;

    public StoryAdapter(OnItemClickedListener<Story> listener,
                        OnItemClickedListener<Story> likeListener,
                        OnItemClickedListener<Story> giftListener) {
        super(listener);
        this.likeListener = likeListener;
        this.giftListener = giftListener;
    }

    @Override
    protected ItemStoryBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemStoryBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_story, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final Story chosen = binding.getStory();
            if (chosen != null) {
                listener.onClicked(chosen);
            }
        });

        binding.btnThankYou.setOnClickListener(view -> {
            final Story chosen = binding.getStory();
            if (chosen != null) {
                Story storyItem = getItem(chosen);
                if (!storyItem.isLiked) {
                    likeListener.onClicked(chosen);
                    storyItem.isLiked = true;
                    storyItem.likesCount += 1;
                }
                notifyDataSetChanged();
            }
        });

        binding.btnOverflow.setOnClickListener(view -> {
            PopupMenu popup = new PopupMenu(binding.getRoot().getContext(), view);
            MenuInflater menuInflater = popup.getMenuInflater();
            menuInflater.inflate(R.menu.popup_menu, popup.getMenu());
            popup.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.item_report:
                        return true;
                    default:
                        return false;
                }
            });
            popup.show();
        });

        binding.btnGift.setOnClickListener(v -> {
            final Story chosen = binding.getStory();
            if (chosen != null) {
                giftListener.onClicked(chosen);
            }
        });

//        ProfileFragment fragment = new ProfileFragment();
//        Bundle bundle = new Bundle();
//        bundle.putString(ProfileFragment.EXTRA_PROFILE_ID, profileId);
//        fragment.setArguments(bundle);

        return binding;
    }

    @Override
    protected void bind(ItemStoryBinding binding, Story item) {
        binding.setStory(item);
    }
}
