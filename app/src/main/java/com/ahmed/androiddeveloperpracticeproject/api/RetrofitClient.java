package com.ahmed.androiddeveloperpracticeproject.api;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static final String BASE_URL = "https://gadsapi.herokuapp.com/";
    private static final String BASE_FORM_URL = "https://docs.google.com/forms/d/e/";

    // create logger
    private static HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BODY);

    // create OkHttp client
    private static OkHttpClient okHttpClient = new OkHttpClient
            .Builder()
            .addInterceptor(loggingInterceptor)
            .build();


    // create retrofit
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build();


    // return retrofit client
    public static ApiInterface getInterface(){
        ApiInterface apiInterface = RetrofitClient.retrofit.create(ApiInterface.class);
        return apiInterface;
    }


    // return form retrofit client
    public static ApiInterface getFormInterface(){
        ApiInterface apiFormInterface = new Retrofit.Builder()
                .baseUrl(BASE_FORM_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(ApiInterface.class);

        return apiFormInterface;
    }


}
