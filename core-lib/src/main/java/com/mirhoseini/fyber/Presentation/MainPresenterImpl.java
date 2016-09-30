package com.mirhoseini.fyber.Presentation;

import com.mirhoseini.fyber.util.ValueManager;
import com.mirhoseini.fyber.view.MainView;

import javax.inject.Inject;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class MainPresenterImpl implements MainPresenter {

    @Inject
    ValueManager valueManager;

    private MainView view;

    @Inject
    public MainPresenterImpl() {
    }

    @Override
    public void setView(MainView view) {
        this.view = view;
    }

    @Override
    public void destroy() {
        view = null;
    }

    @Override
    public void checkLogin() {
        String apiKey = valueManager.getApiKey();
        if (null == apiKey || apiKey.isEmpty()) {
            if (null != view) {
                view.showLoginMessage();
                view.doLogin();
            }
        } else {
            if (null != view)
                view.isLogin(apiKey);
        }
    }
}
