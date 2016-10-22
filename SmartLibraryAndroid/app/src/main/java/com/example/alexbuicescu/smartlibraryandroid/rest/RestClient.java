package com.example.alexbuicescu.smartlibraryandroid.rest;

import android.content.Context;

import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.LoginCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.MainBooksCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.SearchCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoginRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoginResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class RestClient {
    private static final String BASE_URL = "http://192.168.1.45:5728/api/";
    public static Retrofit retrofit;
    private static RestClient instance;
    private static RestAPI apiService;
    private final String TAG = RestClient.class.getName();
    private Context context;

    private RestClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiService = retrofit.create(RestAPI.class);
        }
    }

    public static RestClient getInstance(Context context) {
        if (instance == null) {
            instance = new RestClient();
        }
        instance.setContext(context);
        return instance;
    }

    public static RestClient getInstance() {
        if (instance == null) {
            instance = new RestClient();
        }
        return instance;
    }

    public RestAPI getApiService() {
        return apiService;
    }


    public void LOGIN_CALL(LoginRequest body) {
        Call<LoginResponse> call = getApiService().LOGIN_CALL(
                body
        );
        //asynchronous call
        call.enqueue(new LoginCallback(context, body));
    }

    public void BOOKS_CALL() {
        Call<ArrayList<MainBooksResponse>> call = getApiService().BOOKS_CALL();
        //asynchronous call
        call.enqueue(new MainBooksCallback(context, new EmptyRequest()));
    }

    public void SEARCH_CALL(String searchQuery) {
        searchQuery = searchQuery.replace(' ', '_');

        Call<ArrayList<MainBooksResponse>> call = getApiService().SEARCH_CALL(searchQuery);
        //asynchronous call
        call.enqueue(new SearchCallback(context, new EmptyRequest()));
    }

    private void setContext(Context context) {
        this.context = context;
    }
}