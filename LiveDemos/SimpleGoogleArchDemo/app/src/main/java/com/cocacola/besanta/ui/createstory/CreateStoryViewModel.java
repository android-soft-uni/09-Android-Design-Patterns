package com.cocacola.besanta.ui.createstory;

import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.api.models.ErrorMessage;
import com.cocacola.besanta.repository.LoginManager;
import com.cocacola.besanta.repository.StoryRepository;
import com.cocacola.besanta.repository.UserRepository;

import javax.inject.Inject;

public class CreateStoryViewModel extends ViewModel {

    private MediatorLiveData<Boolean> shouldRedirectToMain = new MediatorLiveData<>();
    private MutableLiveData<Boolean> progressBarVisibility = new MutableLiveData<>();
    private StoryRepository storyRepository;
    private UserRepository userRepository;
    private LoginManager loginManager;

    @Inject
    CreateStoryViewModel(StoryRepository storyRepository,
                         UserRepository userRepository, LoginManager loginManager) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.loginManager = loginManager;
        shouldRedirectToMain.setValue(false);
    }

    public void onBtnTellYourStoryClicked(String text, String photoPath) {
        progressBarVisibility.setValue(true);

        userRepository.getUserById(loginManager.getUserId(), data -> {
            storyRepository.uploadStory(photoPath, text,
                    data, new StoryRepository.StoryCallback() {
                        @Override
                        public void onUploadSuccess() {
                            shouldRedirectToMain.setValue(true);
                            progressBarVisibility.setValue(false);
                        }

                        @Override
                        public void onUploadFailure(ErrorMessage errorMessage) {
                            //TODO: show some error message
                            shouldRedirectToMain.setValue(true);
                            progressBarVisibility.setValue(false);
                        }
                    });
        });

    }

    public MutableLiveData<Boolean> shouldRedirectToMain() {
        return shouldRedirectToMain;
    }

    public MediatorLiveData<Boolean> getShouldRedirectToMain() {
        return shouldRedirectToMain;
    }

    public MutableLiveData<Boolean> getProgressBarVisibility() {
        return progressBarVisibility;
    }
}
