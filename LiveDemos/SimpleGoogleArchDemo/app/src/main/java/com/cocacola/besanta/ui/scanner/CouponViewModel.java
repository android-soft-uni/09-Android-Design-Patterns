package com.cocacola.besanta.ui.scanner;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.model.Coupon;

import javax.inject.Inject;

/**
 * Created by samui on 2/13/2018.
 */

public class CouponViewModel extends ViewModel {
    private final MutableLiveData<Coupon> coupon = new MutableLiveData<>();
    private Application application;

    @Inject
    CouponViewModel() {
    }

    public MutableLiveData<Coupon> getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon.setValue(coupon);
    }
}
