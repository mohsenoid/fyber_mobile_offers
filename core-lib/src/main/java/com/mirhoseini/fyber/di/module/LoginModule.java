package com.mirhoseini.fyber.di.module;

import com.mirhoseini.fyber.Presentation.LoginPresenter;
import com.mirhoseini.fyber.Presentation.LoginPresenterImpl;
import com.mirhoseini.fyber.di.scope.LoginScope;
import com.mirhoseini.fyber.view.LoginView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 30/09/2016.
 */
@Module
public class LoginModule {

    private LoginView view;

    public LoginModule(LoginView view) {
        this.view = view;
    }

    @Provides
    public LoginView provideView() {
        return view;
    }

    @Provides
    @LoginScope
    public LoginPresenter providePresenter(LoginPresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }

}
