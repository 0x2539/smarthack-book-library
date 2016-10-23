package com.example.alexbuicescu.smartlibraryandroid.rest;

import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoggedInRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoginRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.UpdateProfileRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoanDateResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoginResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.ProfileResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

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

    @POST("loaned_by")
    Call<ArrayList<MainBooksResponse>> BORROWED_CALL(
            @Body LoggedInRequest request
    );

    @GET("search/{search_query}")
    Call<ArrayList<MainBooksResponse>> SEARCH_CALL(
            @Path("search_query") String searchQuery
    );

    @GET("loaned_together_with/{book_id}")
    Call<ArrayList<MainBooksResponse>> LOANED_TOGETHER_WITH_CALL(
            @Path("book_id") long bookId
    );

    @POST("loan_date/{book_id}")
    Call<LoanDateResponse> LOAN_DATE_CALL(
            @Path("book_id") long bookId,
            @Body LoggedInRequest request
    );

    @POST("place_loan/{book_id}")
    Call<EmptyRequest> BORROW_CALL(
            @Path("book_id") long bookId,
            @Body LoggedInRequest request
    );

    @POST("profile")
    Call<ProfileResponse> PROFILE_CALL(
            @Body LoggedInRequest request
    );

    @POST("update_profile")
    Call<EmptyRequest> UPDATE_PROFILE_CALL(
            @Body UpdateProfileRequest request
    );

}
