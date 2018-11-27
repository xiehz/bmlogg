package com.szbm.wh.x.bmlogg.api;

import android.net.Uri;

import com.szbm.wh.x.bmlogg.util.LiveDataCallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BmloggApi {
    private static BmloggApi sInstance;
    private BmloggService bmloggService;
    private static final Object sLock = new Object();

    public static BmloggApi getInstance() {
        synchronized (sLock) {
            if (sInstance == null) {
                sInstance = new BmloggApi();
            }
        }
        return sInstance;
    }

    public BmloggService getBmloggService(){
        return  bmloggService;
    }

    private BmloggApi() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .writeTimeout(10,TimeUnit.SECONDS)
                .build();
        bmloggService = new Retrofit.Builder()
                .baseUrl("http://192.168.0.106/bmlgser/api/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(BmloggService.class);
    }
}
