package com.mirhoseini.fyber.di.component;

import com.mirhoseini.fyber.di.module.AndroidModule;
import com.mirhoseini.fyber.di.module.ApiModule;
import com.mirhoseini.fyber.di.module.ApplicationModule;
import com.mirhoseini.fyber.di.module.ClientModule;
import com.mirhoseini.fyber.di.module.OffersModule;
import com.mirhoseini.fyber.view.activity.LoginActivity;
import com.mirhoseini.fyber.view.activity.MainActivity;

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

    OffersSubComponent plus(OffersModule module);

    void inject(MainActivity activity);

    void inject(LoginActivity activity);

}