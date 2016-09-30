package com.mirhoseini.fyber.view;

import com.fyber.api.Offer;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface OffersView extends BaseView {

    void showProgress();

    void hideProgress();

    void setOffersValue(Offer[] offers, int pages);

    void showRetryMessage();

    void setNoOffers();

}
