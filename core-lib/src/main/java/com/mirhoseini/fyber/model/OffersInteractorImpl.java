package com.mirhoseini.fyber.model;


import com.fyber.api.OfferResponse;
import com.mirhoseini.fyber.client.FyberApi;
import com.mirhoseini.fyber.di.scope.OffersScope;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.HashGenerator;
import com.mirhoseini.fyber.util.SchedulerProvider;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subjects.ReplaySubject;

/**
 * Created by Mohsen on 30/09/2016.
 */
@OffersScope
public class OffersInteractorImpl implements OffersInteractor {

    private FyberApi api;
    private SchedulerProvider scheduler;

    private ReplaySubject<OfferResponse> offersSubject;
    private Subscription offersSubscription;

    @Inject
    public OffersInteractorImpl(FyberApi api, SchedulerProvider scheduler) {
        this.api = api;
        this.scheduler = scheduler;
    }

    @Override
    public Observable<OfferResponse> loadOffers(int page,
                                                String format,
                                                int applicationId,
                                                String userId,
                                                String locale,
                                                String osVersion,
                                                long timestamp,
                                                String googleAdId,
                                                boolean googleAdIdLimitedTrackingEnabled,
                                                String ip,
                                                String pub0,
                                                int offerTypes,
                                                String device,
                                                String apiKey) {
        if (offersSubscription == null || offersSubscription.isUnsubscribed()) {
            offersSubject = ReplaySubject.create();

            String hashKey = new HashGenerator()
                    .addParam(FyberApi.PAGE, page)
                    .addParam(FyberApi.FORMAT, Constants.FORMAT_JSON)
                    .addParam(FyberApi.APP_ID, applicationId)
                    .addParam(FyberApi.USER_ID, userId)
                    .addParam(FyberApi.LOCALE, locale)
                    .addParam(FyberApi.OS_VERSION, osVersion)
                    .addParam(FyberApi.TIMESTAMP, timestamp)
                    .addParam(FyberApi.GOOGLE_AD_ID, googleAdId)
                    .addParam(FyberApi.GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED, googleAdIdLimitedTrackingEnabled)
                    .addParam(FyberApi.IP, ip)
                    .addParam(FyberApi.PUB0, pub0)
                    .addParam(FyberApi.OFFER_TYPES, Constants.OFFER_TYPES)
                    .addParam(FyberApi.DEVICE, device)
                    .generate(apiKey);

            offersSubscription = api.getOffers(
                    format,
                    applicationId,
                    userId,
                    locale,
                    osVersion,
                    timestamp,
                    hashKey,
                    googleAdId,
                    googleAdIdLimitedTrackingEnabled,
                    ip,
                    pub0,
                    page,
                    offerTypes,
                    device)
                    .subscribeOn(scheduler.backgroundThread())
                    .observeOn(scheduler.mainThread())
                    .subscribe(offersSubject);
        }

        return offersSubject.asObservable();
    }


    @Override
    public void onDestroy() {
        if (offersSubscription != null && !offersSubscription.isUnsubscribed())
            offersSubscription.unsubscribe();
    }

}
