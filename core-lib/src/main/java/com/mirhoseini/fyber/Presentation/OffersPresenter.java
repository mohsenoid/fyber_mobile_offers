package com.mirhoseini.fyber.Presentation;

import com.mirhoseini.fyber.view.OffersView;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface OffersPresenter extends BasePresenter<OffersView> {

    void loadOffersData(boolean isConnected,
                        int page,
                        String locale,
                        String osVersion,
                        long timestamp,
                        String ip,
                        String device,
                        String apiKey);

}
