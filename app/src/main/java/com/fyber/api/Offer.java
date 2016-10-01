package com.fyber.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class Offer implements Parcelable {

    public static final Parcelable.Creator<Offer> CREATOR = new Parcelable.Creator<Offer>() {
        @Override
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        @Override
        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
    @SerializedName("title")
    private String title;
    @SerializedName("thumbnail")
    private Thumbnail thumbnail;
    @SerializedName("offer_id")
    private String offerId;
    @SerializedName("time_to_payout")
    private TimeToPayout timeToPayout;
    @SerializedName("link")
    private String link;
    @SerializedName("required_actions")
    private String requiredActions;
    @SerializedName("teaser")
    private String teaser;
    @SerializedName("store_id")
    private String storeId;
    @SerializedName("offer_types")
    private OfferType[] offerTypes;
    @SerializedName("payout")
    private String payout;

    public Offer() {
    }

    protected Offer(Parcel in) {
        this.title = in.readString();
        this.thumbnail = in.readParcelable(Thumbnail.class.getClassLoader());
        this.offerId = in.readString();
        this.timeToPayout = in.readParcelable(TimeToPayout.class.getClassLoader());
        this.link = in.readString();
        this.requiredActions = in.readString();
        this.teaser = in.readString();
        this.storeId = in.readString();
        this.offerTypes = in.createTypedArray(OfferType.CREATOR);
        this.payout = in.readString();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getOfferId() {
        return offerId;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public TimeToPayout getTimeToPayout() {
        return timeToPayout;
    }

    public void setTimeToPayout(TimeToPayout timeToPayout) {
        this.timeToPayout = timeToPayout;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getRequiredActions() {
        return requiredActions;
    }

    public void setRequiredActions(String requiredActions) {
        this.requiredActions = requiredActions;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public OfferType[] getOfferTypes() {
        return offerTypes;
    }

    public void setOfferTypes(OfferType[] offerTypes) {
        this.offerTypes = offerTypes;
    }

    public String getPayout() {
        return payout;
    }

    public void setPayout(String payout) {
        this.payout = payout;
    }

    @Override
    public String toString() {
        return "Offer{" +
                "title='" + title + '\'' +
                ", thumbnail=" + thumbnail +
                ", offerId='" + offerId + '\'' +
                ", timeToPayout=" + timeToPayout +
                ", link='" + link + '\'' +
                ", requiredActions='" + requiredActions + '\'' +
                ", teaser='" + teaser + '\'' +
                ", storeId='" + storeId + '\'' +
                ", offerTypes=" + Arrays.toString(offerTypes) +
                ", payout='" + payout + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeParcelable(this.thumbnail, flags);
        dest.writeString(this.offerId);
        dest.writeParcelable(this.timeToPayout, flags);
        dest.writeString(this.link);
        dest.writeString(this.requiredActions);
        dest.writeString(this.teaser);
        dest.writeString(this.storeId);
        dest.writeTypedArray(this.offerTypes, flags);
        dest.writeString(this.payout);
    }
}
