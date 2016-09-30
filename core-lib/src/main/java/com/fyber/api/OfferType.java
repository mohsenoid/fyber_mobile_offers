package com.fyber.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OfferType {

    @SerializedName("readable")
    private String readable;
    @SerializedName("offer_type_id")
    private String offerTypeId;

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }

    public String getOfferTypeId() {
        return offerTypeId;
    }

    public void setOfferTypeId(String offerTypeId) {
        this.offerTypeId = offerTypeId;
    }

    @Override
    public String toString() {
        return "OfferType{" +
                "readable='" + readable + '\'' +
                ", offerTypeId='" + offerTypeId + '\'' +
                '}';
    }

}
