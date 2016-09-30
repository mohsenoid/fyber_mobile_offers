package com.mirhoseini.fyber.test.dagger;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class MockFyberTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context)
            throws InstantiationException, IllegalAccessException, ClassNotFoundException {
        return super.newApplication(cl, MockFyberApplication.class.getName(), context);
    }
}
