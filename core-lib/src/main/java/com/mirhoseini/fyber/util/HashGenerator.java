package com.mirhoseini.fyber.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Mohsen on 30/09/2016.
 */
public class HashGenerator {

    ArrayList<String> params;

    public HashGenerator() {
        this.params = new ArrayList<>();
    }

    private static String convertToHex(byte[] data) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            int halfByte = (data[i] >>> 4) & 0x0F;
            int twoHalf = 0;
            do {
                if ((0 <= halfByte) && (halfByte <= 9)) {
                    stringBuffer.append((char) ('0' + halfByte));
                } else {
                    stringBuffer.append((char) ('a' + (halfByte - 10)));
                }
                halfByte = data[i] & 0x0F;
            } while (twoHalf++ < 1);
        }
        return stringBuffer.toString();
    }

    private static String sha1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] sha1hash;
        messageDigest.update(text.getBytes("iso-8859-1"), 0, text.length());
        sha1hash = messageDigest.digest();
        return convertToHex(sha1hash);
    }

    public HashGenerator addParam(String name, String value) {
        params.add(String.format("%s=%s", name, value));
        return this;
    }

    public HashGenerator addParam(String name, int value) {
        params.add(String.format("%s=%d", name, value));
        return this;
    }

    public HashGenerator addParam(String name, long value) {
        params.add(String.format("%s=%d", name, value));
        return this;
    }

    public HashGenerator addParam(String name, boolean value) {
        params.add(String.format("%s=%s", name, value ? "true" : "false"));
        return this;
    }

    public String generate(String apiKey) {
        Collections.sort(params, String::compareTo);

        String concatResult = "";
        for (String param : params) {
            concatResult += param + "&";
        }
        concatResult += apiKey;

        try {
            return sha1(concatResult);
        } catch (Exception e) {
            return null;
        }
    }
}
