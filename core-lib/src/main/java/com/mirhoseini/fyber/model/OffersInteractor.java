package com.mirhoseini.fyber.model;

import com.fyber.api.OfferResponse;

import rx.Observable;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface OffersInteractor {

    Observable<OfferResponse> loadOffers(int page,
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
                                         String apiKey);

    void onDestroy();
}
