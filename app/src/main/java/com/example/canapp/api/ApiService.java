package com.example.canapp.api;

import com.example.canapp.model.User;
import com.example.canapp.model.UserLogin;
import com.example.canapp.model.UserRegister;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {
    @FormUrlEncoded
    @POST("login")
    Call<UserLogin> login(@Field("email") String email,
                          @Field("password") String password);

    @FormUrlEncoded
    @POST("users")
    Call<UserRegister> register(@Field("name") String username,
                                @Field("email") String email,
                                @Field("password") String password,
                                @Field("address") String address,
                                @Field("major") String major,
                                @Field("phone") String phone,
                                @Field("birth_date") String birthday
                                );


    @GET("users")
    Call<List<User>> getAllUser();

    @GET("users/{_id}")
    Call<User> getUserbyID(@Path("_id") int userId);

    @PUT("users/{_id}")
    Call<User> putUser(@Field("_id") String userId,
                       @Field("name") String username,
                       @Field("email") String email,
                       @Field("address") String address,
                       @Field("major") String major,
                       @Field("phone") String phone,
                       @Field("birth_date") String birthday);
}
