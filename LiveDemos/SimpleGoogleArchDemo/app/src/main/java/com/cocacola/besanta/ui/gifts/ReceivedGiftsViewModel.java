package com.cocacola.besanta.ui.gifts;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.common.Resource;
import com.cocacola.besanta.model.Gift;
import com.cocacola.besanta.repository.GiftRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class ReceivedGiftsViewModel extends ViewModel {

    private final LiveData<Resource<List<Gift>>> gifts;
    private final GiftRepository repository;

    @Inject
    ReceivedGiftsViewModel(GiftRepository repository) {
        this.repository = repository;
        gifts = repository.getReceivedGifts();
    }

    public LiveData<Resource<List<Gift>>> getGifts() {
        return gifts;
    }
}
