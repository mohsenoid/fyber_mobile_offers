package com.mirhoseini.fyber.di.component;

import com.mirhoseini.fyber.di.module.LoginModule;
import com.mirhoseini.fyber.di.scope.LoginScope;
import com.mirhoseini.fyber.view.activity.LoginActivity;

import dagger.Subcomponent;

/**
 * Created by Mohsen on 30/09/2016.
 */
@LoginScope
@Subcomponent(modules = {
        LoginModule.class
})
public interface LoginSubComponent {

    void inject(LoginActivity activity);

}
