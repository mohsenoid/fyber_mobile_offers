package com.mirhoseini.fyber.di.module;

import com.mirhoseini.fyber.Presentation.OffersPresenter;
import com.mirhoseini.fyber.Presentation.OffersPresenterImpl;
import com.mirhoseini.fyber.di.scope.OffersScope;
import com.mirhoseini.fyber.model.OffersInteractor;
import com.mirhoseini.fyber.model.OffersInteractorImpl;
import com.mirhoseini.fyber.view.OffersView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 30/09/2016.
 */
@Module
public class OffersModule {

    private OffersView view;

    public OffersModule(OffersView view) {
        this.view = view;
    }

    @Provides
    public OffersView provideView() {
        return view;
    }

    @Provides
    @OffersScope
    public OffersInteractor provideInteractor(OffersInteractorImpl interactor) {
        return interactor;
    }

    @Provides
    @OffersScope
    public OffersPresenter providePresenter(OffersPresenterImpl presenter) {
        presenter.setView(view);
        return presenter;
    }

}
