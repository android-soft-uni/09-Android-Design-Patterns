package com.cocacola.besanta.ui.sendgift;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.FragmentThankYouBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.ui.MainActivity;

/**
 * Created by samui on 2/15/2018.
 */

public class ThankYouFragment extends Fragment implements Injectable {

    FragmentThankYouBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_thank_you, container, false);

        initListeners();
        return binding.getRoot();
    }

    private void initListeners() {
        binding.btnOk.setOnClickListener(v -> {
            FragmentActivity activity = getActivity();
            Intent intent = new Intent(activity, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            activity.finish();
        });
    }
}
