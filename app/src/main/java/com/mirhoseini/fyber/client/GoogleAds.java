package com.mirhoseini.fyber.client;

import android.content.Context;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import rx.Observable;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */

public class GoogleAds {

    // Observable that emits Google Advertising ID
    public static Observable<String> getAdIdObservable(Context context) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(AdvertisingIdClient.getAdvertisingIdInfo(context).getId());
            } catch (Exception e) {
                Timber.e(e, "Error getting google Advertising ID!");
                subscriber.onError(e);
            }
        });
    }

    // Observable that emits Google Tracking Enabled
    public static Observable<Boolean> getAdIdEnabledObservable(Context context) {
        return Observable.create(subscriber -> {
            try {
                subscriber.onNext(AdvertisingIdClient.getAdvertisingIdInfo(context).isLimitAdTrackingEnabled());
            } catch (Exception e) {
                Timber.e(e, "Error getting google Tracking Enabled!");
                subscriber.onError(e);
            }
        });
    }


}
