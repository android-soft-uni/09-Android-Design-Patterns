package com.cocacola.besanta.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by samui on 2/13/2018.
 */

public class Coupon extends Model implements Parcelable {

    public int amount;

    public Coupon() {}

    public Coupon(int amount) {
        this.amount = amount;
    }

    protected Coupon(Parcel in) {
        amount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(amount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Coupon> CREATOR = new Creator<Coupon>() {
        @Override
        public Coupon createFromParcel(Parcel in) {
            return new Coupon(in);
        }

        @Override
        public Coupon[] newArray(int size) {
            return new Coupon[size];
        }
    };
}
