package com.mirhoseini.fyber.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mirhoseini.fyber.FyberApplication;
import com.mirhoseini.fyber.di.component.ApplicationComponent;

/**
 * Created by Mohsen on 30/09/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        injectDependencies(FyberApplication.getComponent());

        // can be used for general purpose in all Activities of Application
    }

    protected abstract void injectDependencies(ApplicationComponent component);

}
