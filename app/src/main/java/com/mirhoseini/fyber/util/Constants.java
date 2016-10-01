package com.mirhoseini.fyber.util;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class Constants {

    public static final String BASE_URL = "http://api.fyber.com/";

    public static final int NETWORK_CONNECTION_TIMEOUT = 30; // 30 sec
    public static final long CACHE_SIZE = 10 * 1024 * 1024; // 10 MB
    public static final int CACHE_MAX_AGE = 2; // 2 min
    public static final int CACHE_MAX_STALE = 7; // 7 day

    public static final String CODE_OK = "OK";
    public static final String FORMAT_JSON = "json";
    public static final int OFFER_TYPES = 112;

    public static final String SAMPLE_USER_ID = "spiderman";
    public static final String SAMPLE_API_KEY = "1c915e3b5d42d05136185030892fbb846c278927";
    public static final String SAMPLE_APPLICATION_ID = "2070";
    public static final String SAMPLE_PUB0 = "campaignl2";

    public static final String KEY_API_KEY = "api_key";
    public static final String KEY_APPLICATION_ID = "app_id";
    public static final String KEY_USER_ID = "uid";
    public static final String KEY_PUB0 = "pub0";

    public static final int RECYCLER_VIEW_ITEM_SPACE = 48;
}
