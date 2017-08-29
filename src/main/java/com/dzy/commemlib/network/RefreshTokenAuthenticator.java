package com.dzy.commemlib.network;

import android.util.Log;
import java.io.IOException;


import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

/** 
 * Created by dengziyue on 2017/8/15.
 */

public abstract class RefreshTokenAuthenticator implements Authenticator {

    @Override
    public Request authenticate(Route route, Response response) throws IOException {
        
        if (!shouldRefresh(response, response.request())) {
            return null;
        }
        Log.d("dzy", "Token expire，auto refresh token");
        String newToken = null;
        try {
            newToken = refreshToken();
        } catch (Exception e) {
            onError(e);
            return null;
        }
        Log.e("dzy", "Token refresh succeed");
        return response.request().newBuilder()
                .addHeader("Authorization", "Bearer " + newToken)
                .build();

    }


    public abstract boolean shouldRefresh(Response response, Request request);


    public abstract String refreshToken() throws IOException;


    public abstract void onError(Exception e);
}
