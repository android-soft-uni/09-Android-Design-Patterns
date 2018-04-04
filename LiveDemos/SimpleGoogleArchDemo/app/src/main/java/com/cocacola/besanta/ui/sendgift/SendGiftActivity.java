package com.cocacola.besanta.ui.sendgift;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.cocacola.besanta.R;
import com.cocacola.besanta.databinding.ActivityCommonBinding;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by samui on 2/14/2018.
 */

public class SendGiftActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    private static final String EXTRA_RECEIVER_ID = "receiverId";
    private static final String EXTRA_RECEIVER_FIRST_NAME = "receverFirstName";
    private static final String EXTRA_RECEIVER_LAST_NAME = "receverLastName";
    private static final String EXTRA_RECEIVER_AVATAR_URL = "receverAvatarUrl";

    public static Intent createIntent(Context context, String receiverId, String receiverFirstName,
        String receiverLastName, String receiverAvatarUrl) {
        Intent intent = new Intent(context, SendGiftActivity.class);
        intent.putExtra(EXTRA_RECEIVER_ID, receiverId);
        intent.putExtra(EXTRA_RECEIVER_FIRST_NAME, receiverFirstName);
        intent.putExtra(EXTRA_RECEIVER_LAST_NAME, receiverLastName);
        intent.putExtra(EXTRA_RECEIVER_AVATAR_URL, receiverAvatarUrl);
        return intent;
    }

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityCommonBinding binding =
            DataBindingUtil.setContentView(this, R.layout.activity_common);


        if (null == savedInstanceState) {
            String receiverId = getIntent().getStringExtra(EXTRA_RECEIVER_ID);
            String receiverFirstName = getIntent().getStringExtra(EXTRA_RECEIVER_FIRST_NAME);
            String receiverLastName = getIntent().getStringExtra(EXTRA_RECEIVER_LAST_NAME);
            String receiverAvatarUrl = getIntent().getStringExtra(EXTRA_RECEIVER_AVATAR_URL);
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.setCustomAnimations(R.anim.fade_in_fast_transition, R.anim.fade_out_fast_transition);
                ft.replace(R.id.grp_container,
                    PrizesFragment.newInstance(receiverId, receiverFirstName, receiverLastName,
                        receiverAvatarUrl));
                ft.commit();
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
