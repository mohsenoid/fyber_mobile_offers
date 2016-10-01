package com.mirhoseini.fyber.util;

import android.content.Context;
import android.content.res.Configuration;

import com.mirhoseini.appsettings.AppSettings;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class ValueManager {

    public static String getApiKey(Context context) {
        return AppSettings.getString(context, Constants.KEY_API_KEY);
    }

    public static void setApiKey(Context context, String apiKey) {
        AppSettings.setValue(context, Constants.KEY_API_KEY, apiKey);
    }

    public static String getUserId(Context context) {
        return AppSettings.getString(context, Constants.KEY_USER_ID);
    }

    public static void setUserId(Context context, String userId) {
        AppSettings.setValue(context, Constants.KEY_USER_ID, userId);
    }

    public static Integer getApplicationId(Context context) {
        return AppSettings.getInt(context, Constants.KEY_APPLICATION_ID);
    }

    public static void setApplicationId(Context context, int applicationId) {
        AppSettings.setValue(context, Constants.KEY_APPLICATION_ID, applicationId);
    }

    public static String getPub0(Context context) {
        return AppSettings.getString(context, Constants.KEY_PUB0);
    }

    public static void setPub0(Context context, String pub0) {
        AppSettings.setValue(context, Constants.KEY_PUB0, pub0);
    }

    /**
     * Get IP address from first non-localhost interface
     *
     * @param useIPv4 true=return ipv4, false=return ipv6
     * @return address or empty string
     */
    public static String getIPAddress(boolean useIPv4) {
        try {
            List<NetworkInterface> interfaces = Collections.list(NetworkInterface.getNetworkInterfaces());
            for (NetworkInterface intf : interfaces) {
                List<InetAddress> addrs = Collections.list(intf.getInetAddresses());
                for (InetAddress addr : addrs) {
                    if (!addr.isLoopbackAddress()) {
                        String sAddr = addr.getHostAddress();
                        //boolean isIPv4 = InetAddressUtils.isIPv4Address(sAddr);
                        boolean isIPv4 = sAddr.indexOf(':') < 0;

                        if (useIPv4) {
                            if (isIPv4)
                                return sAddr;
                        } else {
                            if (!isIPv4) {
                                int delim = sAddr.indexOf('%'); // drop ip6 zone suffix
                                return delim < 0 ? sAddr.toUpperCase() : sAddr.substring(0, delim).toUpperCase();
                            }
                        }
                    }
                }
            }
        } catch (Exception ex) {
        } // for now eat exceptions
        return "";
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }


    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    public static long getCurrentTimestamp() {
        return System.currentTimeMillis() / 1000;
    }
}
