package com.mirhoseini.fyber.Presentation;

import com.mirhoseini.fyber.model.OffersInteractor;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.fyber.util.GoogleAds;
import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.OffersView;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.subscriptions.Subscriptions;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class OffersPresenterImpl implements OffersPresenter {

    @Inject
    OffersInteractor interactor;
    @Inject
    ValueManager valueManager;
    @Inject
    GoogleAds googleAds;

    private OffersView view;
    private Subscription subscription = Subscriptions.empty();

    @Inject
    public OffersPresenterImpl() {
    }

    @Override
    public void setView(OffersView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        if (subscription != null && !subscription.isUnsubscribed())
            subscription.unsubscribe();

        interactor.onDestroy();

        view = null;
        interactor = null;
    }

    @Override
    public void loadOffersData(boolean isConnected,
                               int page,
                               String locale,
                               String osVersion,
                               long timestamp,
                               String ip,
                               String device,
                               String apiKey) {

        if (null != view) {
            view.showProgress();
        }

        subscription = Observable.zip(
                googleAds.getAdIdObservable(),
                googleAds.getAdIdEnabledObservable(),
                (String googleAdId, Boolean googleAdIdLimitedTrackingEnabled) ->
                        interactor.loadOffers(
                                page,
                                Constants.FORMAT_JSON,
                                valueManager.getApplicationId(),
                                valueManager.getUserId(),
                                locale,
                                osVersion,
                                timestamp,
                                googleAdId,
                                googleAdIdLimitedTrackingEnabled,
                                ip,
                                valueManager.getPub0(),
                                Constants.OFFER_TYPES,
                                device,
                                apiKey))
                // subscribe for the zip result
                .subscribe(offerResponseObservable -> offerResponseObservable.subscribe(offerResponse ->
                        {
                            // check for API error message
                            if (Constants.CODE_OK.equalsIgnoreCase(offerResponse.getCode())) {
                                if (null != view) {
                                    view.hideProgress();
                                    view.setOffersValue(offerResponse.getOffers(), offerResponse.getPages());

                                    if (!isConnected)
                                        view.showOfflineMessage();
                                }
                            } else if (Constants.CODE_NO_CONTENT.equalsIgnoreCase(offerResponse.getCode())) {
                                if (null != view) {
                                    view.hideProgress();
                                    view.setNoOffers();

                                    if (!isConnected)
                                        view.showOfflineMessage();
                                }
                            } else {
                                if (null != view) {
                                    view.hideProgress();
                                    view.showMessage(offerResponse.getMessage());
                                    view.showRetryMessage();
                                }
                            }
                        },

                        //throw error on api errors
                        throwable -> {
                            if (null != view) {
                                view.hideProgress();
                            }

                            if (isConnected) {
                                if (null != view) {
                                    view.showRetryMessage();
                                }
                            } else {
                                if (null != view) {
                                    view.showOfflineMessage();
                                }
                            }
                        }),

                        //throw error on zip issues
                        throwable -> {
                            if (null != view) {
                                view.hideProgress();
                                view.showMessage(throwable.getMessage());
                                view.showRetryMessage();
                            }
                        });

    }
}
