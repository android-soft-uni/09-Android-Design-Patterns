package com.cocacola.besanta.ui.profile;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.common.Resource;
import com.cocacola.besanta.model.Gift;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.model.User;
import com.cocacola.besanta.repository.GiftRepository;
import com.cocacola.besanta.repository.LoginManager;
import com.cocacola.besanta.repository.StoryRepository;
import com.cocacola.besanta.repository.UserRepository;
import com.cocacola.besanta.ui.feed.Filters;

import java.util.List;

import javax.inject.Inject;

public class ProfileViewModel extends ViewModel {

    private final GiftRepository giftRepository;
    private final UserRepository userRepository;
    private LiveData<Resource<List<Gift>>> receivedGifts;
    private MutableLiveData<User> user = new MutableLiveData<>();
    private LiveData<Resource<List<Gift>>> sentGifts;

    private LoginManager loginManager;
    private final LiveData<Resource<List<Story>>> stories;
    private final StoryRepository storyRepository;
    private final MutableLiveData<Filters> filters = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldShowNoInternetError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldShowServerError = new MutableLiveData<>();
    private String userId;

    @Inject
    ProfileViewModel(StoryRepository storyRepository, LoginManager loginManager,
                     GiftRepository giftRepository, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.userRepository = userRepository;
        this.loginManager = loginManager;
        this.giftRepository = giftRepository;
        this.receivedGifts = giftRepository.getReceivedGifts(null);
        this.sentGifts = giftRepository.getSentGifts(null);
        this.stories = Transformations.switchMap(filters, storyRepository::getStories);
    }

    public void receivedUserId(String userId) {
        if(userId == null) {
            this.userId = loginManager.getUserId();
        } else {
            this.userId = userId;
        }
        fetchUserData(this.userId);
    }

    public void fetchUserData(String userId) {
        this.receivedGifts = giftRepository.getReceivedGifts(userId);
        this.sentGifts = giftRepository.getSentGifts(userId);
        Filters filterUser = Filters.getDefault();
        filterUser.setUserName(null);
        filters.setValue(filterUser);
        userRepository.getUserById(userId, data -> user.setValue(data));
    }

    public LiveData<Resource<List<Gift>>> getReceivedGifts() {
        return receivedGifts;
    }

    public LiveData<Resource<List<Gift>>> getSentGifts() {
        return sentGifts;
    }

    /* Getters and Setters */
    public LiveData<Resource<List<Story>>> getStories() {
        return stories;
    }

    public MutableLiveData<Boolean> getShouldShowNoInternetError() {
        return shouldShowNoInternetError;
    }

    public MutableLiveData<User> getUser() {
        return user;
    }

    public MutableLiveData<Boolean> getShouldShowServerError() {
        return shouldShowServerError;
    }
}
