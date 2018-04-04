package com.cocacola.besanta.feed.stories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.cocacola.besanta.feed.Filters;
import com.cocacola.besanta.model.Resource;
import com.cocacola.besanta.model.Story;
import com.cocacola.besanta.repos.StoryRepository;

import java.util.List;

public class StoriesViewModel extends ViewModel {
    private final LiveData<Resource<List<Story>>> stories;
    private final MutableLiveData<Filters> filters = new MutableLiveData<>();
    private final StoryRepository storyRepository;
    private final MutableLiveData<Boolean> shouldShowNoInternetError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldShowServerError = new MutableLiveData<>();

    StoriesViewModel(StoryRepository storyRepository) {
        this.storyRepository = storyRepository;
        this.stories = Transformations.switchMap(filters, storyRepository::getStories);
    }

    /* Getters and Setters */
    public LiveData<Resource<List<Story>>> getStories() {
        return stories;
    }

    void setFilters(final Filters filters) {
        this.filters.setValue(filters);
    }

    /* Getters and Setters */

    public MutableLiveData<Boolean> getShouldShowNoInternetError() {
        return shouldShowNoInternetError;
    }

    public MutableLiveData<Boolean> getShouldShowServerError() {
        return shouldShowServerError;
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory {

        private final StoryRepository repository;

        public Factory(@NonNull StoryRepository storyRepository) {
            this.repository = storyRepository;
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new StoriesViewModel(repository);
        }
    }
}
