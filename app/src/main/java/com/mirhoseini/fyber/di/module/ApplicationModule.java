package com.mirhoseini.fyber.di.module;

import android.content.Context;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.fyber.BuildConfig;
import com.mirhoseini.fyber.util.Constants;
import com.mirhoseini.utils.Utils;

import java.io.File;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.HttpUrl;


/**
 * Created by Mohsen on 30/09/2016.
 */
@Module
public class ApplicationModule {
    @Provides
    @Singleton
    @Named("isDebug")
    public boolean provideIsDebug() {
        return BuildConfig.DEBUG;
    }

    @Provides
    @Singleton
    @Named("networkTimeoutInSeconds")
    public int provideNetworkTimeoutInSeconds() {
        return Constants.NETWORK_CONNECTION_TIMEOUT;
    }

    @Provides
    @Singleton
    public HttpUrl provideEndpoint() {
        return HttpUrl.parse(Constants.BASE_URL);
    }

    @Provides
    @Singleton
    @Named("cacheSize")
    public long provideCacheSize() {
        return Constants.CACHE_SIZE;
    }

    @Provides
    @Singleton
    @Named("cacheMaxAge")
    public int provideCacheMaxAgeMinutes() {
        return Constants.CACHE_MAX_AGE;
    }

    @Provides
    @Singleton
    @Named("cacheMaxStale")
    public int provideCacheMaxStaleDays() {
        return Constants.CACHE_MAX_STALE;
    }

    @Provides
    @Singleton
    @Named("cacheDir")
    public File provideCacheDir(Context context) {
        return context.getCacheDir();
    }

    @Provides
    @Named("isConnected")
    public boolean provideIsConnected(Context context) {
        return Utils.isConnected(context);
    }

    @Provides
    @Named("api_key")
    public String provideApiKey(Context context) {
        return AppSettings.getString(context, Constants.KEY_API_KEY, "");
    }
}
