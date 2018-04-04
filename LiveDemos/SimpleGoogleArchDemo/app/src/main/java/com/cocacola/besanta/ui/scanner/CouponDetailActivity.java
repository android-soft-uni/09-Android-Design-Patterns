package com.cocacola.besanta.ui.scanner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cocacola.besanta.R;
import com.cocacola.besanta.model.Coupon;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by samui on 2/13/2018.
 */

public class CouponDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private final static String EXTRA_COUPON = "coupon";

    public static Intent createIntent(Context context, Coupon coupon) {
        Intent intent = new Intent(context, CouponDetailActivity.class);
        intent.putExtra(EXTRA_COUPON, coupon);
        return intent;
    }

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        if (null == savedInstanceState) {
            Coupon coupon = getIntent().getParcelableExtra(EXTRA_COUPON);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.setCustomAnimations(R.anim.fade_in_fast_transition, R.anim.fade_out_fast_transition);
                ft.replace(R.id.grp_container, CouponDetailFragment.newInstance(coupon));
                ft.commit();
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
