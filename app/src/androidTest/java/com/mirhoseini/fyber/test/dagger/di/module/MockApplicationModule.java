package com.mirhoseini.fyber.test.dagger.di.module;

import android.content.Context;
import android.support.test.espresso.core.deps.dagger.Module;

import com.mirhoseini.fyber.client.FyberApi;
import com.mirhoseini.fyber.di.module.ApplicationModule;
import com.mirhoseini.fyber.util.ValueManager;

import retrofit2.Retrofit;

import static org.mockito.Mockito.mock;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class MockApplicationModule extends ApplicationModule {

    @Override
    public ValueManager provideValueManager(Context context) {
        return mock(ValueManager.class);
    }

}
