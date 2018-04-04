package com.cocacola.besanta.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.cocacola.besanta.R;
import com.cocacola.besanta.common.DataListAdapter;
import com.cocacola.besanta.common.OnItemClickedListener;
import com.cocacola.besanta.databinding.ItemReceivedGiftBinding;
import com.cocacola.besanta.model.Gift;


public final class ReceivedGiftAdapter extends DataListAdapter<Gift, ItemReceivedGiftBinding> {
    private OnItemClickedListener<Gift> likeListener;

    public ReceivedGiftAdapter(OnItemClickedListener<Gift> listener) {
        super(listener);
    }

    @Override
    protected ItemReceivedGiftBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemReceivedGiftBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.item_received_gift, parent, false);
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
    protected void bind(ItemReceivedGiftBinding binding, Gift item) {
        binding.setGift(item);

        if (item.opened) {
            binding.ivIcon.setBackgroundResource(R.drawable.oval_background);
            Glide.with(binding.ivIcon.getContext())
                .load(item.prizeImageUrl)
                .into(binding.ivIcon);
            binding.txtItem.setText(item.prizeName);
        } else {
            binding.ivIcon.setBackgroundResource(R.drawable.fullcircle_background);
            binding.ivIcon.setImageResource(R.drawable.ic_unopen_gift);
            binding.txtItem.setText(binding.txtItem.getContext().getString(R.string.open_your_gift));
        }

        if (item.used) {
            binding.txtItem.setAlpha(0.34f);
            binding.ivIcon.setAlpha(0.34f);
        } else {
            binding.txtItem.setAlpha(1f);
            binding.ivIcon.setAlpha(1f);
        }
    }
}
