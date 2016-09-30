package com.mirhoseini.fyber.view;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface BaseView {

    void showMessage(String message);

    void showOfflineMessage();

    void showNetworkConnectionError(boolean isForce);

}
