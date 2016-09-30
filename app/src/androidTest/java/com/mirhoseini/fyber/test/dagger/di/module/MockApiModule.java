package com.mirhoseini.fyber.test.dagger.di.module;

import com.mirhoseini.fyber.client.FyberApi;
import com.mirhoseini.fyber.di.module.ApiModule;

import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class MockApiModule extends ApiModule {

    @Override
    public FyberApi provideFyberApiService(Retrofit retrofit) {
        return mock(FyberApi.class);
    }

}
