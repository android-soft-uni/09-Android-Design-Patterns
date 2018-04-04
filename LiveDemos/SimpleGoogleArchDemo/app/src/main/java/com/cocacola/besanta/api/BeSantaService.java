package com.cocacola.besanta.api;

import com.cocacola.besanta.api.models.BaseResponse;
import com.cocacola.besanta.api.models.LikeRequest;
import com.cocacola.besanta.api.models.RedeemCodeResponse;
import com.cocacola.besanta.api.models.ShareRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * API access points
 */
public interface BeSantaService {
    @POST("like")
    Call<BaseResponse> like(@Body LikeRequest likeRequest);

    @POST("share")
    Call<BaseResponse> share(@Body ShareRequest likeRequest);

    @POST("redeemCode")
    Call<RedeemCodeResponse> redeemCode(@Body RedeemCodeRequest redeemCodeRequest);
}
