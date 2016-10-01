package com.fyber.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class Thumbnail implements Parcelable {

    public static final Parcelable.Creator<Thumbnail> CREATOR = new Parcelable.Creator<Thumbnail>() {
        @Override
        public Thumbnail createFromParcel(Parcel source) {
            return new Thumbnail(source);
        }

        @Override
        public Thumbnail[] newArray(int size) {
            return new Thumbnail[size];
        }
    };
    @SerializedName("lowres")
    private String lowRes;
    @SerializedName("hires")
    private String highRes;

    public Thumbnail() {
    }

    protected Thumbnail(Parcel in) {
        this.lowRes = in.readString();
        this.highRes = in.readString();
    }

    public String getLowRes() {
        return lowRes;
    }

    public void setLowRes(String lowRes) {
        this.lowRes = lowRes;
    }

    public String getHighRes() {
        return highRes;
    }

    public void setHighRes(String highRes) {
        this.highRes = highRes;
    }

    @Override
    public String toString() {
        return "Thumbnail{" +
                "lowRes='" + lowRes + '\'' +
                ", highRes='" + highRes + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lowRes);
        dest.writeString(this.highRes);
    }

}
