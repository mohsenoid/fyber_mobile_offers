package com.fyber.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OfferResponse implements Parcelable {

    public static final Creator<OfferResponse> CREATOR = new Creator<OfferResponse>() {
        @Override
        public OfferResponse createFromParcel(Parcel source) {
            return new OfferResponse(source);
        }

        @Override
        public OfferResponse[] newArray(int size) {
            return new OfferResponse[size];
        }
    };
    @SerializedName("code")
    String code;
    @SerializedName("message")
    String message;
    @SerializedName("count")
    String count;
    @SerializedName("pages")
    int pages;
    @SerializedName("information")
    Information information;
    @SerializedName("offers")
    Offer[] offers;

    public OfferResponse() {
    }

    protected OfferResponse(Parcel in) {
        this.code = in.readString();
        this.message = in.readString();
        this.count = in.readString();
        this.pages = in.readInt();
        this.information = in.readParcelable(Information.class.getClassLoader());
        this.offers = in.createTypedArray(Offer.CREATOR);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public Information getInformation() {
        return information;
    }

    public void setInformation(Information information) {
        this.information = information;
    }

    public Offer[] getOffers() {
        return offers;
    }

    public void setOffers(Offer[] offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "OfferResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", count='" + count + '\'' +
                ", pages=" + pages +
                ", information=" + information +
                ", offers=" + Arrays.toString(offers) +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.code);
        dest.writeString(this.message);
        dest.writeString(this.count);
        dest.writeInt(this.pages);
        dest.writeParcelable(this.information, flags);
        dest.writeTypedArray(this.offers, flags);
    }
}
