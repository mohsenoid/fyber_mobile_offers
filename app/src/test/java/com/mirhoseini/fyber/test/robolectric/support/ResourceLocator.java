package com.mirhoseini.fyber.test.robolectric.support;

import android.support.annotation.StringRes;

import org.robolectric.RuntimeEnvironment;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class ResourceLocator {
    public static String getString(@StringRes int id) {
        return RuntimeEnvironment.application.getString(id);
    }
}
