package com.cocacola.besanta.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.common.DataListAdapter;
import com.cocacola.besanta.common.OnItemClickedListener;
import com.cocacola.besanta.databinding.ItemSentGiftBinding;
import com.cocacola.besanta.model.Gift;


public final class SentGiftAdapter extends DataListAdapter<Gift, ItemSentGiftBinding> {
    private OnItemClickedListener<Gift> likeListener;

    public SentGiftAdapter(OnItemClickedListener<Gift> listener) {
        super(listener);
    }

    @Override
    protected ItemSentGiftBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemSentGiftBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.item_sent_gift, parent, false);
        binding.getRoot()
            .setOnClickListener(v -> {
                final Gift gift = binding.getGift();
                if (gift != null) {
                    listener.onClicked(gift);
                }
            });

        return binding;
    }

    @Override
    protected void bind(ItemSentGiftBinding binding, Gift item) {
        binding.setGift(item);
    }
}
