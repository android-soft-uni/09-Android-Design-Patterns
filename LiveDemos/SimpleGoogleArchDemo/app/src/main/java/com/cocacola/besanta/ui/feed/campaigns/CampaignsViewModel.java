package com.cocacola.besanta.ui.feed.campaigns;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.common.Resource;
import com.cocacola.besanta.model.Campaign;
import com.cocacola.besanta.repository.CampaignRepository;
import com.cocacola.besanta.ui.feed.Filters;

import java.util.List;

import javax.inject.Inject;

public class CampaignsViewModel extends ViewModel {
    private final LiveData<Resource<List<Campaign>>> campaigns;
    private final MutableLiveData<Filters> filters = new MutableLiveData<>();
    private final CampaignRepository campaignRepository;
    private final MutableLiveData<Boolean> shouldShowNoInternetError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> shouldShowServerError = new MutableLiveData<>();

    @Inject
    CampaignsViewModel(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
        this.campaigns = Transformations.switchMap(filters, campaignRepository::getCampaigns);
    }

    /* Getters and Setters */
    public LiveData<Resource<List<Campaign>>> getCampaigns() {
        return campaigns;
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
}
