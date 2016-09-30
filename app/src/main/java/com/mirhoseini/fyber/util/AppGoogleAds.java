package com.mirhoseini.fyber.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.subjects.ReplaySubject;
import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class AppGoogleAds implements GoogleAds {

    Context context;
    SchedulerProvider scheduler;
    ValueManager valueManager;

    String adId;
    private Subscription adIdSubscription;
    private ReplaySubject<String> adIdSubject;

    private Boolean adIdEnabled;
    private Subscription adIdEnabledSubscription;
    private ReplaySubject<Boolean> adIdEnabledSubject;

    public AppGoogleAds(Context context, SchedulerProvider scheduler, ValueManager valueManager) {
        this.context = context;
        this.scheduler = scheduler;
        this.valueManager = valueManager;
    }

    @Override
    public Observable<String> getAdIdObservable() {
        // Observable that emits Google Advertising adIdSubscription
        if (adIdSubscription == null || adIdSubscription.isUnsubscribed()) {
            adIdSubject = ReplaySubject.create();

            adIdSubscription = Observable.concat(
                    getAdIdFromMemoryObservable(),
                    getAdIdFromGoogleObservable())
                    .first(entity -> entity != null)
                    .subscribe(adIdSubject);
        }

        return adIdSubject.asObservable();
    }

    @NonNull
    private Observable<String> getAdIdFromGoogleObservable() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    subscriber.onNext(AdvertisingIdClient.getAdvertisingIdInfo(context).getId());
                } catch (Exception e) {
                    Timber.e(e, "Error getting google Advertising ID!");

                    String diskValue = valueManager.getAdId();
                    if (null == diskValue || diskValue.isEmpty())
                        subscriber.onError(e);
                    else
                        subscriber.onNext(diskValue);
                }
            }
        })
                .doOnNext(adId -> this.adId = adId)
                .doOnNext(adId -> valueManager.setAdId(adId))
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    @NonNull
    private Observable<String> getAdIdFromMemoryObservable() {
        return Observable.just(adId);
    }


    @Override
    public Observable<Boolean> getAdIdEnabledObservable() {
        // Observable that emits Google Tracking Enabled
        if (adIdEnabledSubscription == null || adIdEnabledSubscription.isUnsubscribed()) {
            adIdEnabledSubject = ReplaySubject.create();

            adIdEnabledSubscription = Observable.concat(getAdIdEnabledFromMemoryObservable(), getAdIdEnabledFromGoogleObservable())
                    .first(entity -> entity != null)
                    .subscribe(adIdEnabledSubject);
        }

        return adIdEnabledSubject.asObservable();
    }

    @NonNull
    private Observable<Boolean> getAdIdEnabledFromGoogleObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    subscriber.onNext(AdvertisingIdClient.getAdvertisingIdInfo(context).isLimitAdTrackingEnabled());
                } catch (Exception e) {
                    Timber.e(e, "Error getting google Tracking Enabled!");

                    Boolean diskValue = valueManager.getAdIdEnabled();
                    if (null == diskValue)
                        subscriber.onError(e);
                    else
                        subscriber.onNext(diskValue);
                }
            }
        })
                .doOnNext(adIdEnabled -> this.adIdEnabled = adIdEnabled)
                .doOnNext(adIdEnabled -> valueManager.setAdIdEnabled(adIdEnabled))
                .subscribeOn(scheduler.backgroundThread())
                .observeOn(scheduler.mainThread());
    }

    @NonNull
    private Observable<Boolean> getAdIdEnabledFromMemoryObservable() {
        return Observable.just(adIdEnabled);
    }

}
