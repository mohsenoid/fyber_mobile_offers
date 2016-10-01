package com.mirhoseini.fyber.client;


import com.fyber.api.OfferResponse;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Mohsen on 30/09/2016.
 */
public interface FyberApi {

    String PAGE = "page";
    String PUB0 = "pub0";
    String IP = "ip";
    String GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED = "google_ad_id_limited_tracking_enabled";
    String GOOGLE_AD_ID = "google_ad_id";
    String HASH_KEY = "hashkey";
    String TIMESTAMP = "timestamp";
    String OS_VERSION = "os_version";
    String LOCALE = "locale";
    String USER_ID = "uid";
    String APP_ID = "appid";
    String FORMAT = "format";
    String OFFER_TYPES = "offer_types";
    String DEVICE = "device";

    // http://api.fyber.com/feed/v1/offers.json?appid=[APP_ID]&uid=[USER_ID]&ip=[IP_ADDRESS]&locale=[LOCALE]&device_id=[DEVICE_ID]&ps_time=[TIMESTAMP]&pub0=[CUSTOM]&timestamp=[UNIX_TIMESTAMP]&offer_types=[OFFER_TYPES]&google_ad_id=[GAID]&google_ad_id_limited_tracking_enabled=[GAID ENABLED]&hashkey=[HASHKEY]
    @GET("feed/v1/offers.json")
    Observable<OfferResponse> getOffers(
            @Query(FORMAT) String format,
            @Query(APP_ID) int applicationID,
            @Query(USER_ID) String userId,
            @Query(LOCALE) String locale,
            @Query(OS_VERSION) String osVersion,
            @Query(TIMESTAMP) long timestamp,
            @Query(HASH_KEY) String hashKey,
            @Query(GOOGLE_AD_ID) String googleAdId,
            @Query(GOOGLE_AD_ID_LIMITED_TRACKING_ENABLED) boolean googleAdIdLimitedTrackingEnabled,
            @Query(IP) String ip,
            @Query(PUB0) String pub0,
            @Query(PAGE) int page,
            @Query(OFFER_TYPES) int offerTypes,
            @Query(DEVICE) String device);

}
