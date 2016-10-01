package com.mirhoseini.fyber.viewmodel;

import com.fyber.api.Offer;
import com.mirhoseini.fyber.client.FyberApi;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.HashGenerator;

import java.util.ArrayList;
import java.util.Arrays;

import javax.inject.Inject;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersViewModelImpl implements OffersViewModel {

    private FyberApi api;

    private BehaviorSubject<ArrayList<Offer>> offersSubject = BehaviorSubject.create();
    private BehaviorSubject<Integer> pagesSubject = BehaviorSubject.create();
    private BehaviorSubject<Boolean> isLoadingSubject = BehaviorSubject.create(false);

    @Inject
    public OffersViewModelImpl(FyberApi api) {
        this.api = api;
    }

    @Override
    public boolean loadOffersDataObservable(
            int page,
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

        // Don't try and load if we're already loading
        if (isLoadingSubject.getValue()) {
            return false;
        }

        //show loading progress
        isLoadingSubject.onNext(true);

        String hashKey = new HashGenerator()
                .addParam(FyberApi.FORMAT, Constants.FORMAT_JSON)
                .addParam(FyberApi.PAGE, page)
                .addParam(FyberApi.APP_ID, applicationId)
                .addParam(FyberApi.USER_ID, userId)
                .addParam(FyberApi.LOCALE, locale)
                .addParam(FyberApi.OS_VERSION, osVersion)
                .addParam(FyberApi.TIMESTAMP, timestamp)
                .addParam(FyberApi.GOOGLE_AD_ID, googleAdId)
                .addParam(FyberApi.GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED, googleAdIdLimitedTrackingEnabled)
                .addParam(FyberApi.IP, ip)
                .addParam(FyberApi.PUB0, pub0)
                .addParam(FyberApi.OFFER_TYPES, offerTypes)
                .addParam(FyberApi.DEVICE, device)
                .generate(apiKey);

        api.getOffers(
                Constants.FORMAT_JSON,
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
                .subscribe(offerResponse ->
                        {
                            // check for API error message
                            if (Constants.CODE_OK.equalsIgnoreCase(offerResponse.getCode())) {
                                // emit total pages via the pagesSubject
                                pagesSubject.onNext(offerResponse.getPages());

                                // convert Offers array to ArrayList
                                ArrayList<Offer> offerList = new ArrayList<>(Arrays.asList(offerResponse.getOffers()));

                                // Concatenate the new movies to the current posts list, then emit it via the offersSubject
                                ArrayList fullList;

                                if (page == 1)
                                    fullList = new ArrayList();
                                else
                                    fullList = new ArrayList(offersSubject.getValue());

                                fullList.addAll(offerList);
                                offersSubject.onNext(fullList);
                            } else {
                                // stop loading
                                isLoadingSubject.onNext(false);
                                // emit error via the offersSubject
                                offersSubject.onError(new Throwable(offerResponse.getMessage()));
                            }
                        },
                        throwable -> {
                            // stop loading
                            isLoadingSubject.onNext(false);
                            // emit error via the offersSubject
                            offersSubject.onError(throwable);
                        },
                        () -> isLoadingSubject.onNext(false)
                );

        return true;
    }

    @Override
    public Observable<ArrayList<Offer>> offersObservable() {
        return offersSubject.asObservable();
    }

    @Override
    public Observable<Integer> pagesObservable() {
        return pagesSubject.asObservable();
    }

    @Override
    public Observable<Boolean> isLoadingObservable() {
        return isLoadingSubject.asObservable();
    }
}
