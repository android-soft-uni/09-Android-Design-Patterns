package com.cocacola.besanta.repository;

import com.cocacola.besanta.api.BeSantaService;
import com.cocacola.besanta.api.models.BaseResponse;
import com.cocacola.besanta.api.models.ErrorMessage;
import com.cocacola.besanta.api.models.LikeRequest;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class ShareRepository {
    private final BeSantaService beSantaService;

    @Inject
    public ShareRepository(BeSantaService beSantaService) {
        this.beSantaService = beSantaService;
    }

    public void shareStory(String storyId, String userId, ShareCallback callback) {
        beSantaService.like(new LikeRequest(storyId, userId)).enqueue(new Callback<BaseResponse>() {
            @Override
            public void onResponse(Call<BaseResponse> call, Response<BaseResponse> response) {
                if (response != null && response.isSuccessful()) {
                    callback.onSuccess();
                } else {
                    callback.onFailure(new ErrorMessage(ErrorMessage.ErrorType.SERVER_ERROR));
                }
            }

            @Override
            public void onFailure(Call<BaseResponse> call, Throwable t) {
                ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.SERVER_ERROR);
                if (t instanceof IOException) {
                    errorMessage = new ErrorMessage(ErrorMessage.ErrorType.NO_INTERNET_ERROR);
                }
                callback.onFailure(errorMessage);
            }
        });
    }

    public interface ShareCallback {
        void onSuccess();

        void onFailure(ErrorMessage errorMessage);
    }
}
