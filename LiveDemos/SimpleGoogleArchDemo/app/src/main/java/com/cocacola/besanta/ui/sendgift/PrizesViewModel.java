package com.cocacola.besanta.ui.sendgift;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.common.Resource;
import com.cocacola.besanta.model.Prize;
import com.cocacola.besanta.model.User;
import com.cocacola.besanta.repository.LoginManager;
import com.cocacola.besanta.repository.PrizeRepository;
import com.cocacola.besanta.repository.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class PrizesViewModel extends ViewModel {

    private final MutableLiveData<String> receiverId = new MutableLiveData<>();
    private final MutableLiveData<String> receiverFirstName = new MutableLiveData<>();
    private final MutableLiveData<String> receiverLastName = new MutableLiveData<>();
    private final MutableLiveData<String> receiverAvatarUrl = new MutableLiveData<>();
    private final MutableLiveData<User> currentUser = new MutableLiveData<>();
    private final LiveData<Resource<List<Prize>>> prizes;
    private final PrizeRepository repository;
    private final LoginManager loginManager;
    private final UserRepository userRepository;

    @Inject
    PrizesViewModel(PrizeRepository repository, LoginManager loginManager, UserRepository userRepository) {
        this.repository = repository;
        this.loginManager = loginManager;
        this.userRepository = userRepository;

        prizes = repository.getPrizes();
        userRepository.getUserById(this.loginManager.getUserId(), currentUser::postValue);
    }

    public LiveData<Resource<List<Prize>>> getPrizes() {
        return prizes;
    }

    public MutableLiveData<String> getReceiverAvatarUrl() {
        return receiverAvatarUrl;
    }

    public MutableLiveData<String> getReceiverFirstName() {
        return receiverFirstName;
    }

    public MutableLiveData<String> getReceiverId() {
        return receiverId;
    }

    public MutableLiveData<String> getReceiverLastName() {
        return receiverLastName;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId.postValue(receiverId);
    }

    public void setReceiverFirstName(String receiverFirstName) {
        this.receiverFirstName.postValue(receiverFirstName);
    }

    public void setReceiverLastName(String receiverLastName) {
        this.receiverLastName.postValue(receiverLastName);
    }

    public void setReceiverAvatarUrl(String receiverAvatarUrl) {
        this.receiverAvatarUrl.postValue(receiverAvatarUrl);
    }

    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }
}
