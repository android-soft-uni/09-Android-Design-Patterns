package com.cocacola.besanta.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.common.DataListAdapter;
import com.cocacola.besanta.common.OnItemClickedListener;
import com.cocacola.besanta.databinding.ItemPrizeBinding;
import com.cocacola.besanta.model.Prize;


public final class PrizeAdapter extends DataListAdapter<Prize, ItemPrizeBinding> {
    private OnItemClickedListener<Prize> likeListener;
    private int currentPoints;

    public PrizeAdapter(OnItemClickedListener<Prize> listener) {
        super(listener);
    }

    public void setCurrentPoints(int currentPoints) {
        this.currentPoints = currentPoints;
        notifyDataSetChanged();
    }

    @Override
    protected ItemPrizeBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemPrizeBinding binding =
            DataBindingUtil.inflate(inflater, R.layout.item_prize, parent, false);
        binding.getRoot()
            .setOnClickListener(v -> {
                final Prize chosen = binding.getPrize();
                if (chosen != null) {
                    listener.onClicked(chosen);
                }
            });

        return binding;
    }

    @Override
    protected void bind(ItemPrizeBinding binding, Prize item) {
        binding.setPrize(item);
        if (item.price > currentPoints) {
            binding.getRoot().setAlpha(.34f);
            binding.getRoot().setClickable(false);
        } else {
            binding.getRoot().setAlpha(1);
            binding.getRoot().setClickable(true);
        }
    }
}
