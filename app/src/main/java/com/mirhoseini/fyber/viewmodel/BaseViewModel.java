package com.mirhoseini.fyber.viewmodel;

import rx.Observable;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface BaseViewModel {

    Observable<Boolean> isLoadingObservable();

}
