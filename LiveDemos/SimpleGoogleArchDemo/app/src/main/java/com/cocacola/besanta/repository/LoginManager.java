package com.cocacola.besanta.repository;

import android.content.Intent;

import com.cocacola.besanta.model.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class LoginManager {

    private CallbackManager callbackManager;
    private FirebaseAuth firebaseAuth;

    @Inject
    public LoginManager() {
        callbackManager = CallbackManager.Factory.create();
        firebaseAuth = FirebaseAuth.getInstance();
    }

    public boolean isUserLogged() {
        return null != firebaseAuth.getCurrentUser();
    }

    public String getCurrentUserId() {
        if (isUserLogged()) {
            return firebaseAuth.getCurrentUser().getUid();
        } else {
            return null;
        }
    }

    public User getEmptyUser() {
        if (isUserLogged()) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
            User user = new User();
            user.id = getUserId();
            user.email = firebaseUser.getEmail();
            user.santaState = 0;
            user.points = 0;
            user.avatarUrl = firebaseUser != null ? firebaseUser.getPhotoUrl().toString() : null;
            user.displayName = firebaseUser != null ? firebaseUser.getDisplayName() : null;
            user.firstName = user.displayName.split(" ")[0];
            user.lastName = user.displayName.split(" ")[1];
            return user;
        } else {
            return null;
        }
    }

    public void loginWithFacebook(LoginButton loginButton, LoginListener loginListener) {
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Timber.d("facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken(), loginListener);
            }

            @Override
            public void onCancel() {
                loginListener.onError();
            }

            @Override
            public void onError(FacebookException error) {
                loginListener.onError();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken token, LoginListener loginListener) {
        Timber.d("handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful() && firebaseAuth.getCurrentUser() != null) {
                        loginListener.onSuccess();
                    } else {
                        loginListener.onError();
                    }
                });
    }

    public void handleResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public String getUserId() {
        return firebaseAuth.getUid();
    }

    public interface LoginListener {
        void onSuccess();

        void onError();
    }
}
