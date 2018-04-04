package com.cocacola.besanta.adapter;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.common.DataListAdapter;
import com.cocacola.besanta.common.OnItemClickedListener;
import com.cocacola.besanta.databinding.ItemCampaignBinding;
import com.cocacola.besanta.model.Campaign;


public final class CampaignAdapter extends DataListAdapter<Campaign, ItemCampaignBinding> {

    public CampaignAdapter(OnItemClickedListener<Campaign> listener) {
        super(listener);
    }

    @Override
    protected ItemCampaignBinding createBinding(LayoutInflater inflater, ViewGroup parent) {
        final ItemCampaignBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_campaign, parent, false);
        binding.getRoot().setOnClickListener(v -> {
            final Campaign chosen = binding.getCampaign();
            if (chosen != null) {
                listener.onClicked(chosen);
            }
        });
        return binding;
    }

    @Override
    protected void bind(ItemCampaignBinding binding, Campaign item) {
        binding.setCampaign(item);
    }
}
