package com.cocacola.besanta.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.firestore.PropertyName;

/**
 * Created by samui on 2/14/2018.
 */

public class Prize extends Model implements Parcelable {

    public static final String FIELD_PRICE = "price";

    public String name;
    @PropertyName("image_url")
    public String imageUrl;
    public int price;

    public Prize() {}

    protected Prize(Parcel in) {
        name = in.readString();
        imageUrl = in.readString();
        price = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(imageUrl);
        dest.writeInt(price);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Prize> CREATOR = new Creator<Prize>() {
        @Override
        public Prize createFromParcel(Parcel in) {
            return new Prize(in);
        }

        @Override
        public Prize[] newArray(int size) {
            return new Prize[size];
        }
    };
}
