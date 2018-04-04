package com.cocacola.besanta.ui.scanner;

import android.Manifest;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.FragmentScannerBinding;
import com.cocacola.besanta.di.Injectable;
import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

import javax.inject.Inject;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by samui on 2/13/2018.
 */

public class ScannerFragment extends Fragment implements Injectable {

    public static final int RC_CAMERA = 100;

    @Inject
    ViewModelProvider.Factory viewModelFactory;
    ScannerViewModel viewModel;
    FragmentScannerBinding binding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
        @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_scanner, container, false);

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ScannerViewModel.class);

        initSubscribers();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initReader();
    }

    @AfterPermissionGranted(RC_CAMERA)
    public void initReader() {
        FragmentActivity activity = getActivity();
        String[] perms = {Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(activity, perms)) {
            QRCodeReaderView readerView = new QRCodeReaderView(activity);
            readerView.setOnQRCodeReadListener((text, points) -> viewModel.setCode(text));
            readerView.setQRDecodingEnabled(true);
            readerView.setBackCamera();

            binding.grpScanner.addView(readerView, new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        } else {
            // Do not have permissions, request them now
            EasyPermissions.requestPermissions(this, getString(R.string.camera_rationale),
                RC_CAMERA, perms);
        }
    }

    private void initSubscribers() {
        viewModel.getErrors().observe(this, error -> {
            if (!TextUtils.isEmpty(error)) {
                showError(error);
            }
        });
        viewModel.getCoupon().observe(this, coupon -> {
            viewModel.getCoupon().removeObservers(this);
            startActivity(CouponDetailActivity.createIntent(getActivity(), coupon));
        });
    }

    private void showError(String error) {
        Snackbar.make(getView(), error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
