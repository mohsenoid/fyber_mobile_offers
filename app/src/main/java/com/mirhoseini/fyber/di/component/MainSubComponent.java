package com.mirhoseini.fyber.di.component;

import com.mirhoseini.fyber.di.module.MainModule;
import com.mirhoseini.fyber.di.scope.MainScope;
import com.mirhoseini.fyber.view.activity.MainActivity;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 30/09/2016.
 */
@MainScope
@Subcomponent(modules = {
        MainModule.class
})
public interface MainSubComponent {

    void inject(MainActivity activity);

}
