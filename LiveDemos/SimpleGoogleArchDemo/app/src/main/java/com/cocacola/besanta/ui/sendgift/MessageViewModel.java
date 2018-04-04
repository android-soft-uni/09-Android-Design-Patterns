package com.cocacola.besanta.ui.sendgift;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.cocacola.besanta.R;
import com.cocacola.besanta.model.Prize;
import com.cocacola.besanta.repository.GiftRepository;
import com.cocacola.besanta.repository.LoginManager;

import javax.inject.Inject;

/**
 * Created by samui on 2/14/2018.
 */

public class MessageViewModel extends ViewModel {

    private final MutableLiveData<String> receiverId = new MutableLiveData<>();
    private final MutableLiveData<String> receiverFirstName = new MutableLiveData<>();
    private final MutableLiveData<String> receiverLastName = new MutableLiveData<>();
    private final MutableLiveData<String> receiverAvatarUrl = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> success = new MutableLiveData<>();
    private final MutableLiveData<Prize> prize = new MutableLiveData<>();

    private LoginManager loginManager;
    private GiftRepository giftRepository;
    private Application application;

    @Inject
    MessageViewModel(LoginManager loginManager, GiftRepository giftRepository,
        Application application) {
        this.loginManager = loginManager;
        this.giftRepository = giftRepository;
        this.application = application;
    }

    public MutableLiveData<Prize> getPrize() {
        return prize;
    }

    public void setPrize(Prize prize) {
        this.prize.postValue(prize);
    }

    public void setMessage(String message) {
        giftRepository.sendGift(loginManager.getUserId(), receiverId.getValue(),
            receiverFirstName.getValue(), receiverLastName.getValue(), receiverAvatarUrl.getValue(),
            message, prize.getValue(), documentReference -> {
                success.postValue(true);
            }, e -> {
                error.postValue(application.getString(R.string.send_gift_error));
            });
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

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }
}
