package com.cocacola.besanta.ui;


import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.cocacola.besanta.R;
import com.cocacola.besanta.ui.feed.FeedFragment;
import com.cocacola.besanta.ui.gifts.GiftsFragment;
import com.cocacola.besanta.ui.profile.ProfileFragment;
import com.cocacola.besanta.ui.scanner.ScannerFragment;
import com.cocacola.besanta.util.BottomNavigationHelper;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    BottomNavigationView bottomNavigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        bottomNavigationView = findViewById(R.id.bottom_nav);
        setupToolbar();
        setupBottomNavigation();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
    }

    private void setupBottomNavigation() {
        BottomNavigationHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.item_stories);

        Menu menu = bottomNavigationView.getMenu();
        selectFragment(menu.getItem(0));
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (getSupportActionBar() != null) {
                        getSupportActionBar().setTitle(item.getTitle());
                    }
                    selectFragment(item);
                    return false;
                });
    }

    protected void selectFragment(MenuItem item) {
        item.setChecked(true);
        toolbar.setElevation(4);

        switch (item.getItemId()) {
            case R.id.item_stories:
                pushFragment(new FeedFragment());
                toolbar.setElevation(0);
                break;
            case R.id.item_gifts:
                pushFragment(new GiftsFragment());
                toolbar.setElevation(0);
                break;
            case R.id.item_scanner:
                pushFragment(new ScannerFragment());
                break;
            case R.id.item_profile:
                pushFragment(new ProfileFragment());
                break;
        }
    }

    protected void pushFragment(Fragment fragment) {
        hideKeyboard();
        if (fragment == null)
            return;

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null) {
            FragmentTransaction ft = fragmentManager.beginTransaction();
            if (ft != null) {
                ft.setCustomAnimations(R.anim.fade_in_fast_transition, R.anim.fade_out_fast_transition);
                ft.replace(R.id.grp_container, fragment);
                ft.commit();
            }
        }
    }

    public void hideKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            if (inputMethodManager != null) {
                inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }
}
