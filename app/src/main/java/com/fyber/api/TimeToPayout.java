package com.fyber.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class TimeToPayout implements Parcelable {

    public static final Parcelable.Creator<TimeToPayout> CREATOR = new Parcelable.Creator<TimeToPayout>() {
        @Override
        public TimeToPayout createFromParcel(Parcel source) {
            return new TimeToPayout(source);
        }

        @Override
        public TimeToPayout[] newArray(int size) {
            return new TimeToPayout[size];
        }
    };
    @SerializedName("amount")
    private String amount;
    @SerializedName("readable")
    private String readable;

    public TimeToPayout() {
    }

    protected TimeToPayout(Parcel in) {
        this.amount = in.readString();
        this.readable = in.readString();
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.amount);
        dest.writeString(this.readable);
    }
}