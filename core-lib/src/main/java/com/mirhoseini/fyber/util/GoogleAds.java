package com.mirhoseini.fyber.util;

import rx.Observable;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface GoogleAds {

    Observable<String> getAdIdObservable();

    Observable<Boolean> getAdIdEnabledObservable();

}
