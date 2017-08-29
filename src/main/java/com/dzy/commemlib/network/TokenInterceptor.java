package com.dzy.commemlib.network;

import android.util.Log;
import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/** 
 * Created by dengziyue on 2017/8/8.
 */

public abstract class TokenInterceptor implements Interceptor
{
    
    
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Log.e("okHTTP", "request url " + request.url().toString());
        Request.Builder builder = request.newBuilder();
        onBuildRequest(builder);
        if (shouldAddToken(request)) {
            builder.addHeader("Authorization", "Bearer "+getToken());
        }
        return chain.proceed(builder.build());
    }
    
    public void onBuildRequest(Request.Builder builder){
        
    }

    public abstract boolean shouldAddToken(Request request);
    
    public abstract String getToken();
}
