package com.cocacola.besanta.ui.login;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cocacola.besanta.R;
import com.cocacola.besanta.di.Injectable;
import com.cocacola.besanta.repository.LoginManager;
import com.cocacola.besanta.ui.MainActivity;

import javax.inject.Inject;

public class LoginActivity extends AppCompatActivity implements Injectable {

    private static final String TAG = "LoginActivity";
    @Inject ViewModelProvider.Factory viewModelFactory;
    @Inject LoginManager loginManager;
    LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel.class);
        initListeners();
    }

    private void initListeners() {
        loginManager.loginWithFacebook(findViewById(R.id.login_button), new LoginManager.LoginListener() {
            @Override
            public void onSuccess() {
                viewModel.onLoginSuccessful();
            }

            @Override
            public void onError() {
                viewModel.onLoginFailed();
            }
        });
        viewModel.shouldRedirectToMain().observeForever(shouldRedirect -> {
            if(shouldRedirect) {
                redirectToMain();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.handleResult(requestCode, resultCode, data);
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.onUiReady();
    }

    public void redirectToMain() {
        startActivity(new Intent(this, MainActivity.class));
        viewModel.shouldRedirectToMain().setValue(false);
    }

    public void onLoginClicked(View view) {
        findViewById(R.id.login_button).performClick();
    }
}
