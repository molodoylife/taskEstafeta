package ua.hanasaka.testestafeta.model.api;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Oleksandr Molodykh on 17.07.2017.
 */

public class ApiModule {
    public static ApiInterface getApiInterface() {

        Retrofit.Builder builder = new Retrofit.Builder().
                baseUrl("https://api.gettyimages.com/")
                .addConverterFactory(GsonConverterFactory.create());

        ApiInterface apiInterface = builder.build().create(ApiInterface.class);
        return apiInterface;
    }
}
