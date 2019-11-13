package com.cba.provident.repository.network;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class Network {
    private static final String BASE_URL = "https://uat01.provident.co.uk/techtest/";

    private static HttpLoggingInterceptor getHttpLoggingInterceptor() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.level(HttpLoggingInterceptor.Level.BODY);
        return logging;
    }

    private static final OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(getHttpLoggingInterceptor())
            .build();

    private static final Retrofit retrofit = new Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public static final CustomerService customerService = retrofit.create(CustomerService.class);
}
