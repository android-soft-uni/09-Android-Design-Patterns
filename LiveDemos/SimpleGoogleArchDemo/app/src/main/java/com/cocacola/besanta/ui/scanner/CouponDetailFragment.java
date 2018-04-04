package com.cocacola.besanta.ui.scanner;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.FragmentCouponDetailBinding;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.model.Coupon;

import java.util.Locale;

import javax.inject.Inject;


/**
 * Created by samui on 2/13/2018.
 */

public class CouponDetailFragment extends Fragment implements Injectable {

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    CouponViewModel viewModel;
    FragmentCouponDetailBinding binding;

    public static CouponDetailFragment newInstance(Coupon coupon) {
        CouponDetailFragment fragment = new CouponDetailFragment();
        fragment.setCoupon(coupon);
        return fragment;
    }

    private Coupon coupon;

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_coupon_detail, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(CouponViewModel.class);
        viewModel.setCoupon(coupon);

        initSubscribers();
        initListeners();
        return binding.getRoot();
    }

    private void initListeners() {
        binding.btnOk.setOnClickListener(v -> getActivity().finish());
    }

    private void initSubscribers() {
        viewModel.getCoupon()
            .observe(this, data -> binding.txtAmount.setText(
                String.format(Locale.getDefault(), "%d", coupon.amount)));
    }
}
