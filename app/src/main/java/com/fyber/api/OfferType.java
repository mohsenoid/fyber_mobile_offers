package com.fyber.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OfferType implements Parcelable {

    public static final Parcelable.Creator<OfferType> CREATOR = new Parcelable.Creator<OfferType>() {
        @Override
        public OfferType createFromParcel(Parcel source) {
            return new OfferType(source);
        }

        @Override
        public OfferType[] newArray(int size) {
            return new OfferType[size];
        }
    };
    @SerializedName("readable")
    private String readable;
    @SerializedName("offer_type_id")
    private String offerTypeId;


    public OfferType() {
    }

    protected OfferType(Parcel in) {
        this.readable = in.readString();
        this.offerTypeId = in.readString();
    }

    @Override
    public String toString() {
        return "OfferType{" +
                "readable='" + readable + '\'' +
                ", offerTypeId='" + offerTypeId + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.readable);
        dest.writeString(this.offerTypeId);
    }
}
