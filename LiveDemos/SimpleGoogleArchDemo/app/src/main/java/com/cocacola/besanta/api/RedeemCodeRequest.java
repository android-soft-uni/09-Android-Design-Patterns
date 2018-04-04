package com.cocacola.besanta.api;

import com.google.gson.annotations.SerializedName;

public class RedeemCodeRequest {
    @SerializedName("user_id")
    private String userId;
    @SerializedName("code")
    private String code;

    public RedeemCodeRequest(String userId, String code) {
        this.userId = userId;
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
