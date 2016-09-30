package com.mirhoseini.fyber;

import timber.log.Timber;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class FyberApplicationImpl extends FyberApplication {

    @Override
    public void initApplication() {

        // initialize Timber in debug version to log
        Timber.plant(new Timber.DebugTree() {
            @Override
            protected String createStackElementTag(StackTraceElement element) {
                //adding line number to logs
                return super.createStackElementTag(element) + ":" + element.getLineNumber();
            }
        });

    }
}
