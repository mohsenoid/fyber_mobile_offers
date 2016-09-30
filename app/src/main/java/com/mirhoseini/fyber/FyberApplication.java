package com.mirhoseini.fyber;

import android.app.Application;

import com.mirhoseini.fyber.di.component.ApplicationComponent;
import com.mirhoseini.fyber.di.component.DaggerApplicationComponent;
import com.mirhoseini.fyber.di.module.AndroidModule;

/**
 * Created by Mohsen on 30/09/2016.
 */
public abstract class FyberApplication extends Application {

    private static ApplicationComponent component;

    public static ApplicationComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        initApplication();

        component = createComponent();
    }

    public ApplicationComponent createComponent() {
        return DaggerApplicationComponent.builder()
                .androidModule(new AndroidModule(this))
                .build();
    }

    public abstract void initApplication();
}
