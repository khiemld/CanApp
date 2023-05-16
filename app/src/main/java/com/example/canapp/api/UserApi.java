package com.example.canapp.api;

import com.example.canapp.model.project.ProjectFull;
import com.example.canapp.model.user.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserApi {
    @GET("users")
    Call<List<User>> getAll();
}
