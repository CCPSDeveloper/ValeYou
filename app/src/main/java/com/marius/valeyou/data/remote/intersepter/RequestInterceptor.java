package com.marius.valeyou.data.remote.intersepter;

import java.io.IOException;

import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Arvind Poonia on 12/12/2018.
 */
public class RequestInterceptor implements Interceptor {

    String credentials;

    public RequestInterceptor() {
        this.credentials = Credentials.basic("security_key", "JHNlcnZpY2VAdmFsZXlvdUBwcm92aWRlciQ=");
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        /*Request original = chain.request();
        Request.Builder builder = original.newBuilder().
                *//*header("x-api-key",BuildConfig.x_api_key).*//*
                method(original.method(), original.body());
        Request newRequest = builder.build();*/
        Request original = chain.request();
        Request.Builder builder = original.newBuilder().
                header("Authorization", credentials);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }

}
