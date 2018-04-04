package com.cocacola.besanta.ui.sendgift;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.FragmentMessageBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Prize;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class MessageFragment extends Fragment implements Injectable {

    public static MessageFragment newInstance(String receiverId, String receiverFirstName,
        String receiverLastName, String receiverAvatarUrl, Prize prize) {
        MessageFragment fragment = new MessageFragment();
        fragment.setPrize(prize);
        fragment.receiverId = receiverId;
        fragment.receiverFirstName = receiverFirstName;
        fragment.receiverLastName = receiverLastName;
        fragment.receiverAvatarUrl = receiverAvatarUrl;
        return fragment;
    }

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    MessageViewModel viewModel;
    FragmentMessageBinding binding;
    private String receiverId;
    private String receiverFirstName;
    private String receiverLastName;
    private String receiverAvatarUrl;
    private Prize prize;

    public void setPrize(Prize prize) {
        this.prize = prize;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_message, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(MessageViewModel.class);
        viewModel.setReceiverId(receiverId);
        viewModel.setReceiverFirstName(receiverFirstName);
        viewModel.setReceiverLastName(receiverLastName);
        viewModel.setReceiverAvatarUrl(receiverAvatarUrl);
        viewModel.setPrize(prize);

        initSubscribers();
        initViews();
        return binding.getRoot();
    }

    private void initViews() {
        binding.txtName.setText(getString(R.string.name_format, receiverFirstName));
    }

    private void initSubscribers() {
        viewModel.getReceiverId()
            .observe(this, s -> receiverId = s);
        viewModel.getReceiverFirstName()
            .observe(this, s -> {
                receiverFirstName = s;
                initViews();
            });
        viewModel.getReceiverLastName()
            .observe(this, s -> receiverLastName = s);
        viewModel.getReceiverAvatarUrl()
            .observe(this, s -> receiverAvatarUrl = s);
        viewModel.getPrize()
            .observe(this, this::setPrize);
        viewModel.getError().observe(this, this::showError);
        viewModel.getSuccess().observe(this, success -> {
            FragmentActivity activity = getActivity();
            Intent intent = new Intent(activity, ThankYouActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            activity.finish();
        });
    }

    private void showError(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.message_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean handled = false;
        switch (item.getItemId()) {
            case R.id.item_send: {
                handled = true;
                viewModel.setMessage(binding.message.getText().toString());
                break;
            }
            default:{
                handled = super.onOptionsItemSelected(item);
            }
        }
        return handled;
    }
}
