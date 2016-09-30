package com.mirhoseini.fyber.di.module;

import com.mirhoseini.fyber.Presentation.MainPresenter;
import com.mirhoseini.fyber.Presentation.MainPresenterImpl;
import com.mirhoseini.fyber.di.scope.MainScope;
import com.mirhoseini.fyber.view.MainView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 30/09/2016.
 */
@Module
public class MainModule {

    private MainView view;

    public MainModule(MainView view) {
        this.view = view;
    }

    @Provides
    public MainView provideView() {
        return view;
    }

    @Provides
    @MainScope
    public MainPresenter providePresenter(MainPresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }

}
