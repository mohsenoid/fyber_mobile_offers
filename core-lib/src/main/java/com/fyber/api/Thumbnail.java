package com.fyber.api;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class Thumbnail {

    @SerializedName("lowres")
    private String lowRes;
    @SerializedName("hires")
    private String highRes;

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

}
