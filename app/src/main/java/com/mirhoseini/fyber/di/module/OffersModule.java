package com.mirhoseini.fyber.di.module;

import com.mirhoseini.fyber.di.scope.OffersScope;
import com.mirhoseini.fyber.viewmodel.OffersViewModel;
import com.mirhoseini.fyber.viewmodel.OffersViewModelImpl;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Mohsen on 30/09/2016.
 */
@Module
public class OffersModule {

    @Provides
    @OffersScope
    public OffersViewModel providePresenter(OffersViewModelImpl viewModel) {
        return viewModel;
    }

}
