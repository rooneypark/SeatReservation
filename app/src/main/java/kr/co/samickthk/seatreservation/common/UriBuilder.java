package kr.co.samickthk.seatreservation.common;

import android.net.Uri;

public class UriBuilder {
    private static final String TAG = "UriBuilder";
    private static final String SCHEME = "http";
    private static final String AUTHORITY = "15.164.93.144:8080";
    private static final String API_PATH = "api";

    public static Uri getLoginUri(String userId, String password) {
        // sample= "http://15.164.93.144:8080/api/login?loginId=admin&loginPw=0000"
        Uri.Builder builder = getBaseUriBuilder();
        builder.appendPath("login")
                .appendQueryParameter("loginId", userId)
                .appendQueryParameter("loginPw", password);
        return builder.build();
    }

    private static Uri.Builder getBaseUriBuilder() {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme(SCHEME)
                .encodedAuthority(AUTHORITY)
                .appendPath(API_PATH);
        return builder;
    }
}
