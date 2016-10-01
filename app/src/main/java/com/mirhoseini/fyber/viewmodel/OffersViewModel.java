package com.mirhoseini.fyber.viewmodel;

import com.fyber.api.Offer;
import com.fyber.api.OfferResponse;

import java.util.ArrayList;

import rx.Observable;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface OffersViewModel extends BaseViewModel {

    boolean loadOffersDataObservable(
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
            String apiKey
    );

    Observable<ArrayList<Offer>> offersObservable();

    Observable<Integer> pagesObservable();

}
