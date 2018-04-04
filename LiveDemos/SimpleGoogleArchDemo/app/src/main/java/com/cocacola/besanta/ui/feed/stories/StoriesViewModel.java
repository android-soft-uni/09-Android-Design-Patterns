package com.cocacola.besanta.ui.feed.stories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.api.models.ErrorMessage;
import com.cocacola.besanta.common.Resource;
import com.cocacola.besanta.model.LikedStoryModel;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.repository.LikeRepository;
import com.cocacola.besanta.repository.LoginManager;
import com.cocacola.besanta.repository.StoryRepository;
import com.cocacola.besanta.repository.UserRepository;
import com.cocacola.besanta.ui.feed.Filters;

import java.util.List;

import javax.inject.Inject;

public class StoriesViewModel extends ViewModel {
    private final LiveData<Resource<List<Story>>> stories;
    private LoginManager loginManager;
    private UserRepository userRepository;
    private final MutableLiveData<Filters> filters = new MutableLiveData<>();
    private final StoryRepository storyRepository;
    private final LikeRepository likeRepository;
    private LiveData<Resource<List<LikedStoryModel>>> likedStories = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldShowNoInternetError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldShowServerError = new MutableLiveData<>();

    @Inject
    StoriesViewModel(StoryRepository storyRepository, LikeRepository likeRepository,
                     LoginManager loginManager, UserRepository userRepository) {
        this.storyRepository = storyRepository;
        this.likeRepository = likeRepository;
        this.stories = Transformations.switchMap(filters, storyRepository::getStories);
        this.likedStories = userRepository.getLikedStories(loginManager.getCurrentUserId());
        this.loginManager = loginManager;
        this.userRepository = userRepository;
    }

    /* Getters and Setters */
    public LiveData<Resource<List<Story>>> getStories() {
        return stories;
    }

    void setFilters(final Filters filters) {
        this.filters.setValue(filters);
    }

    public void onStoryLiked(Story story) {
        likeRepository.likeStory(story.id, loginManager.getUserId(), new LikeRepository.LikesCallback() {
            @Override
            public void onSuccess() {
                //don't have to do anything, the LiveData will update the UI
            }

            @Override
            public void onFailure(ErrorMessage errorMessage) {
                if (errorMessage.getErrorType() == ErrorMessage.ErrorType.SERVER_ERROR) {
                    shouldShowServerError.setValue(true);
                } else {
                    if (errorMessage.getErrorType() == ErrorMessage.ErrorType.NO_INTERNET_ERROR) {
                        shouldShowNoInternetError.setValue(true);
                    }
                }
            }
        });
    }

    /* Getters and Setters */

    public MutableLiveData<Boolean> getShouldShowNoInternetError() {
        return shouldShowNoInternetError;
    }

    public MutableLiveData<Boolean> getShouldShowServerError() {
        return shouldShowServerError;
    }

    public LiveData<Resource<List<LikedStoryModel>>> getLikedStories() {
        return likedStories;
    }
}
