package com.mirhoseini.fyber.util;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface ValueManager {

    String getApiKey();

    void setApiKey(String apiKey);

    String getUserId();

    void setUserId(String userId);

    Integer getApplicationId();

    void setApplicationId(int applicationId);

    String getPub0();

    void setPub0(String pub0);

    String getAdId();

    void setAdId(String adId);

    Boolean getAdIdEnabled();

    void setAdIdEnabled(boolean adIdEnabled);

    boolean isConnected();
}
