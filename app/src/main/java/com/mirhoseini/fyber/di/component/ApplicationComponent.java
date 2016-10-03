package com.mirhoseini.fyber.di.component;

import com.mirhoseini.fyber.di.module.AndroidModule;
import com.mirhoseini.fyber.di.module.ApiModule;
import com.mirhoseini.fyber.di.module.AppOffersModule;
import com.mirhoseini.fyber.di.module.ApplicationModule;
import com.mirhoseini.fyber.di.module.ClientModule;
import com.mirhoseini.fyber.di.module.LoginModule;
import com.mirhoseini.fyber.di.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Mohsen on 30/09/2016.
 */
@Singleton
@Component(modules = {
        AndroidModule.class,
        ApplicationModule.class,
        ApiModule.class,
        ClientModule.class
})
public interface ApplicationComponent {

    MainSubComponent plus(MainModule module);

    LoginSubComponent plus(LoginModule module);

    OffersSubComponent plus(AppOffersModule module);


}