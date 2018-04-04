package com.cocacola.besanta.ui.login;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.common.CompletionLiveData;
import com.cocacola.besanta.model.User;
import com.cocacola.besanta.repository.LoginManager;
import com.cocacola.besanta.repository.UserRepository;

import javax.inject.Inject;

public class LoginViewModel extends ViewModel {

    private final LoginManager loginManager;
    private UserRepository userRepository;
    private MediatorLiveData<Boolean> shouldRedirectToMain = new MediatorLiveData<>();

    @Inject
    LoginViewModel(LoginManager loginManager, UserRepository userRepository) {
        this.loginManager = loginManager;
        this.userRepository = userRepository;
        shouldRedirectToMain.setValue(false);
    }

    public void onUiReady() {
        if (loginManager.isUserLogged()) {
            shouldRedirectToMain.setValue(true);
        }
    }

    public void onLoginSuccessful() {
        if (loginManager.isUserLogged()) {
            User user = loginManager.getEmptyUser();
            userRepository.isUserRegistered(user, new UserRepository.IsUserRegisteredCallback() {
                @Override
                public void onFound() {
                    shouldRedirectToMain.setValue(true);
                }

                @Override
                public void onNotFound() {
                    CompletionLiveData addUserCompletion = addUser(user, loginManager.getUserId());
                    shouldRedirectToMain.addSource(addUserCompletion, in -> {
                        if (in != null) {
                            if (in.isSuccessful()) {
                                shouldRedirectToMain.setValue(true);
                            }
                        }
                    });
                }
            });
        }
    }


    CompletionLiveData addUser(User user, String userId) {
        return userRepository.addUser(user, userId);
    }

    public void onLoginFailed() {

    }

    public MutableLiveData<Boolean> shouldRedirectToMain() {
        return shouldRedirectToMain;
    }
}
