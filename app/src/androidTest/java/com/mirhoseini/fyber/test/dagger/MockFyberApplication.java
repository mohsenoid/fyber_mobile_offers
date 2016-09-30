package com.mirhoseini.fyber.test.dagger;

import com.mirhoseini.fyber.FyberApplicationImpl;
import com.mirhoseini.fyber.di.module.AndroidModule;
import com.mirhoseini.fyber.test.dagger.di.component.DaggerTestApplicationComponent;
import com.mirhoseini.fyber.test.dagger.di.component.TestApplicationComponent;
import com.mirhoseini.fyber.test.dagger.di.module.MockApiModule;
import com.mirhoseini.fyber.test.dagger.di.module.MockApplicationModule;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class MockFyberApplication extends FyberApplicationImpl {

    @Override
    public TestApplicationComponent createComponent() {
        return DaggerTestApplicationComponent
                .builder()
                .androidModule(new AndroidModule(this))
                .applicationModule(new MockApplicationModule())
                .apiModule(new MockApiModule())
                .build();
    }

}
