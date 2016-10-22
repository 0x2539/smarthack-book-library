package com.example.alexbuicescu.smartlibraryandroid.rest;

import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoginRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoginResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public interface RestAPI {

    @POST("login")
    Call<LoginResponse> LOGIN_CALL(

            @Body LoginRequest requestBody
    );

    @GET("books")
    Call<ArrayList<MainBooksResponse>> BOOKS_CALL();

}
