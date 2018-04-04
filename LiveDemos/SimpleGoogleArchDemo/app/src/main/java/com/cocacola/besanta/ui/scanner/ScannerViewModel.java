package com.cocacola.besanta.ui.scanner;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.R;
import com.cocacola.besanta.api.models.ErrorMessage;
import com.cocacola.besanta.model.Coupon;
import com.cocacola.besanta.repository.CouponRepository;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;

/**
 * Created by samui on 2/13/2018.
 */

public class ScannerViewModel extends ViewModel {
    private final MutableLiveData<String> errors = new MutableLiveData<>();
    private final MutableLiveData<Coupon> coupon = new MutableLiveData<>();
    private Application application;
    private CouponRepository couponRepository;

    @Inject
    ScannerViewModel(Application application, CouponRepository couponRepository) {
        this.application = application;
        this.couponRepository = couponRepository;
    }

    public MutableLiveData<String> getErrors() {
        return errors;
    }

    public MutableLiveData<Coupon> getCoupon() {
        return coupon;
    }

    public void setCode(String code) {
        FirebaseUser currentUser = FirebaseAuth.getInstance()
            .getCurrentUser();
        if (null != currentUser) {
            String userId = currentUser.getUid();
            couponRepository.redeemCode(userId, code, new CouponRepository.CouponCallback() {
                @Override
                public void onSuccess(Coupon reward) {
                    coupon.postValue(reward);
                }

                @Override
                public void onFailure(ErrorMessage errorMessage) {
                    errors.postValue(application.getString(R.string.scanner_error));
                }
            });
        }
    }
}
