package com.cocacola.besanta.repository;

import com.cocacola.besanta.api.BeSantaService;
import com.cocacola.besanta.api.RedeemCodeRequest;
import com.cocacola.besanta.api.models.ErrorMessage;
import com.cocacola.besanta.api.models.RedeemCodeResponse;
import com.cocacola.besanta.model.Coupon;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Singleton
public class CouponRepository {
    private final BeSantaService beSantaService;

    @Inject
    public CouponRepository(BeSantaService beSantaService) {
        this.beSantaService = beSantaService;
    }

    public void redeemCode(String userId, String code, CouponCallback callback) {
        beSantaService.redeemCode(new RedeemCodeRequest(userId, code)).enqueue(new Callback<RedeemCodeResponse>() {
            @Override
            public void onResponse(Call<RedeemCodeResponse> call, Response<RedeemCodeResponse> response) {
                if (response != null && response.isSuccessful()) {
                    callback.onSuccess(new Coupon(response.body().getPointsWon()));
                } else {
                    callback.onFailure(new ErrorMessage(ErrorMessage.ErrorType.SERVER_ERROR));
                }
            }

            @Override
            public void onFailure(Call<RedeemCodeResponse> call, Throwable t) {
                ErrorMessage errorMessage = new ErrorMessage(ErrorMessage.ErrorType.SERVER_ERROR);
                if (t instanceof IOException) {
                    errorMessage = new ErrorMessage(ErrorMessage.ErrorType.NO_INTERNET_ERROR);
                }
                callback.onFailure(errorMessage);
            }
        });
    }

    public interface CouponCallback {
        void onSuccess(Coupon coupon);

        void onFailure(ErrorMessage errorMessage);
    }
}
