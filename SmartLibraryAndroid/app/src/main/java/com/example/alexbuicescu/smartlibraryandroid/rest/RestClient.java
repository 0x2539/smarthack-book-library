package com.example.alexbuicescu.smartlibraryandroid.rest;

import android.content.Context;

import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.BorrowCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.BorrowedCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.LoanDateCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.LoanedTogetherWithCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.LoginCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.MainBooksCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.ProfileCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.SearchCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.callbacks.UpdateProfileCallback;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.EmptyRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoggedInRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.LoginRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.requests.UpdateProfileRequest;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoanDateResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.LoginResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.MainBooksResponse;
import com.example.alexbuicescu.smartlibraryandroid.rest.responses.ProfileResponse;
import com.example.alexbuicescu.smartlibraryandroid.utils.UserPreferences;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by alexbuicescu on Oct 22 - 2016.
 */
public class RestClient {
    private static final String BASE_URL = "http://192.168.1.95:5728/api/";
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

    public void BORROWED_CALL() {
        LoggedInRequest request = new LoggedInRequest(UserPreferences.getLoginToken(context));
        Call<ArrayList<MainBooksResponse>> call = getApiService().BORROWED_CALL(request);
        //asynchronous call
        call.enqueue(new BorrowedCallback(context, request));
    }

    public void SEARCH_CALL(String searchQuery) {
        searchQuery = searchQuery.replace(' ', '_');

        Call<ArrayList<MainBooksResponse>> call = getApiService().SEARCH_CALL(searchQuery);
        //asynchronous call
        call.enqueue(new SearchCallback(context, new EmptyRequest()));
    }

    public void LOANED_TOGETHER_WITH_CALL(long bookId) {

        Call<ArrayList<MainBooksResponse>> call = getApiService().LOANED_TOGETHER_WITH_CALL(bookId);
        //asynchronous call
        call.enqueue(new LoanedTogetherWithCallback(context, new EmptyRequest()));
    }

    public void LOAN_DATE_CALL(long bookId) {

        LoggedInRequest request = new LoggedInRequest(UserPreferences.getLoginToken(context));
        Call<LoanDateResponse> call = getApiService().LOAN_DATE_CALL(bookId, request);
        //asynchronous call
        call.enqueue(new LoanDateCallback(context, new EmptyRequest()));
    }

    public void BORROW_CALL(long bookId) {

        LoggedInRequest request = new LoggedInRequest(UserPreferences.getLoginToken(context));
        Call<EmptyRequest> call = getApiService().BORROW_CALL(bookId, request);
        //asynchronous call
        call.enqueue(new BorrowCallback(context, new EmptyRequest()));
    }

    public void PROFILE_CALL() {

        LoggedInRequest request = new LoggedInRequest(UserPreferences.getLoginToken(context));
        Call<ProfileResponse> call = getApiService().PROFILE_CALL(request);
        //asynchronous call
        call.enqueue(new ProfileCallback(context, new EmptyRequest()));
    }

    public void UPDATE_PROFILE_CALL(String firstName, String lastName, String email) {

        UpdateProfileRequest request = new UpdateProfileRequest(
                UserPreferences.getLoginToken(context),
                firstName,
                lastName,
                email
        );
        Call<EmptyRequest> call = getApiService().UPDATE_PROFILE_CALL(request);
        //asynchronous call
        call.enqueue(new UpdateProfileCallback(context, request));
    }

    private void setContext(Context context) {
        this.context = context;
    }
}