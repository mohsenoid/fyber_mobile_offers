package com.mirhoseini.fyber.test.dagger.di.component;

import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.di.module.AndroidModule;
import com.mirhoseini.fyber.di.module.ApiModule;
import com.mirhoseini.fyber.di.module.ApplicationModule;
import com.mirhoseini.fyber.di.module.ClientModule;
import com.mirhoseini.fyber.test.dagger.MainActivityDaggerTest;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ClientModule.class
})
public interface TestApplicationComponent extends ApplicationComponent {

    void inject(MainActivityDaggerTest test);

}