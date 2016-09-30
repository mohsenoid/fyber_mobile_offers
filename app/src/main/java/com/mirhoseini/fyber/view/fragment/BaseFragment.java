package com.mirhoseini.fyber.view.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.mirhoseini.fyber.FyberApplication;
import com.mirhoseini.fyber.di.component.ApplicationComponent;

/**
 * Created by Mohsen on 30/09/2016.
 */
public abstract class BaseFragment extends Fragment {

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        injectDependencies(FyberApplication.getComponent());

        // can be used for general purpose in all Fragments of Application
    }

    protected abstract void injectDependencies(ApplicationComponent component);

}