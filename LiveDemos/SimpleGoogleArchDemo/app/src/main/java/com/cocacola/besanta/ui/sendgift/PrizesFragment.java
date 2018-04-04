package com.cocacola.besanta.ui.sendgift;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.adapter.PrizeAdapter;
import com.cocacola.besanta.databinding.FragmentPrizesBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Prize;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class PrizesFragment extends Fragment implements Injectable {

    public static PrizesFragment newInstance(String receiverId, String receiverFirstName,
        String receiverLastName, String receiverAvatarUrl) {
        PrizesFragment fragment = new PrizesFragment();
        fragment.receiverId = receiverId;
        fragment.receiverFirstName = receiverFirstName;
        fragment.receiverLastName = receiverLastName;
        fragment.receiverAvatarUrl = receiverAvatarUrl;
        return fragment;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    PrizesViewModel viewModel;
    FragmentPrizesBinding binding;
    private PrizeAdapter adapter;
    private String receiverId;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverAvatarUrl;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prizes, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PrizesViewModel.class);
        viewModel.setReceiverId(receiverId);
        viewModel.setReceiverFirstName(receiverFirstName);
        viewModel.setReceiverLastName(receiverLastName);
        viewModel.setReceiverAvatarUrl(receiverAvatarUrl);

        initRecycler();
        initSubscribers();
        return binding.getRoot();
    }

    private void initSubscribers() {
        viewModel.getPrizes()
            .observe(this, listResource -> {
                if (listResource.isSuccessful()) {
                    adapter.replace(listResource.data());
                }
            });
        viewModel.getReceiverId()
            .observe(this, s -> receiverId = s);
        viewModel.getReceiverFirstName()
            .observe(this, s -> receiverFirstName = s);
        viewModel.getReceiverLastName()
            .observe(this, s -> receiverLastName = s);
        viewModel.getReceiverAvatarUrl()
            .observe(this, s -> receiverAvatarUrl = s);
        viewModel.getCurrentUser()
            .observe(this, user -> adapter.setCurrentPoints(null != user ? user.points : 0));
    }

    private void initRecycler() {
        adapter = new PrizeAdapter(this::onPrizeSelected);
        binding.rvPrizes.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        binding.rvPrizes.setAdapter(adapter);
    }

    public void onPrizeSelected(Prize prize) {
        FragmentActivity activity = getActivity();
        startActivity(
            MessageActivity.createIntent(activity, receiverId, receiverFirstName, receiverLastName,
                receiverAvatarUrl, prize));
        activity.finish();
    }
}
