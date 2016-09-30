package com.fyber.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class TimeToPayout {

    @SerializedName("amount")
    private String amount;
    @SerializedName("readable")
    private String readable;

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReadable() {
        return readable;
    }

    public void setReadable(String readable) {
        this.readable = readable;
    }

    @Override
    public String toString() {
        return "TimeToPayout{" +
                "amount='" + amount + '\'' +
                ", readable='" + readable + '\'' +
                '}';
    }

}