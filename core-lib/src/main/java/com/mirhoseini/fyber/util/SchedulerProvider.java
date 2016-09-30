package com.mirhoseini.fyber.util;

import rx.Scheduler;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface SchedulerProvider {

    Scheduler mainThread();

    Scheduler backgroundThread();

}
