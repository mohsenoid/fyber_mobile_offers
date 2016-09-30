package com.fyber.api;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OfferResponse {

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

}
