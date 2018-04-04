package com.cocacola.besanta.api.models;

import com.google.gson.annotations.SerializedName;

public class RedeemCodeResponse extends BaseResponse {

    @SerializedName("points_won")
    private int pointsWon;

    public int getPointsWon() {
        return pointsWon;
    }

    public void setPointsWon(int pointsWon) {
        this.pointsWon = pointsWon;
    }
}
