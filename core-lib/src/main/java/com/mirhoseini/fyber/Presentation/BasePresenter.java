package com.mirhoseini.fyber.Presentation;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface BasePresenter<T> {

    void setView(T view);

    void destroy();

}
