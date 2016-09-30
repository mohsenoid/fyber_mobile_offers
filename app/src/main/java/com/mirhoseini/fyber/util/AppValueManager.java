package com.mirhoseini.fyber.util;

import android.content.Context;

import com.mirhoseini.appsettings.AppSettings;
import com.mirhoseini.utils.Utils;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class AppValueManager implements ValueManager {

    Context context;

    public AppValueManager(Context context) {
        this.context = context;
    }

    @Override
    public String getApiKey() {
        return AppSettings.getString(context, AppConstants.KEY_API_KEY);
    }

    @Override
    public void setApiKey(String apiKey) {
        AppSettings.setValue(context, AppConstants.KEY_API_KEY, apiKey);
    }

    @Override
    public String getUserId() {
        return AppSettings.getString(context, AppConstants.KEY_USER_ID);
    }

    @Override
    public void setUserId(String userId) {
        AppSettings.setValue(context, AppConstants.KEY_USER_ID, userId);
    }

    @Override
    public Integer getApplicationId() {
        return AppSettings.getInt(context, AppConstants.KEY_APPLICATION_ID);
    }

    @Override
    public void setApplicationId(int applicationId) {
        AppSettings.setValue(context, AppConstants.KEY_APPLICATION_ID, applicationId);
    }

    @Override
    public String getPub0() {
        return AppSettings.getString(context, AppConstants.KEY_PUB0);
    }

    @Override
    public void setPub0(String pub0) {
        AppSettings.setValue(context, AppConstants.KEY_PUB0, pub0);
    }

    @Override
    public String getAdId() {
        return AppSettings.getString(context, AppConstants.KEY_AD_ID);
    }

    @Override
    public void setAdId(String adId) {
        AppSettings.setValue(context, AppConstants.KEY_AD_ID, adId);
    }

    @Override
    public Boolean getAdIdEnabled() {
        return AppSettings.getBoolean(context, AppConstants.KEY_AD_ID_ENABLED);
    }

    @Override
    public void setAdIdEnabled(boolean adIdEnabled) {
        AppSettings.setValue(context, AppConstants.KEY_AD_ID_ENABLED, adIdEnabled);
    }

    @Override
    public boolean isConnected() {
        return Utils.isConnected(context);
    }

}
