package com.example.canapp.api;

import com.example.canapp.model.user.User;
import com.example.canapp.model.user.UserLogin;
import com.example.canapp.model.user.UserRegister;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

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


    @PUT("users/{id}")
    Call<UserLogin> putUser(@Path("id") String userId,
                            @Body User user);
    @Multipart
    @POST("users/upload/{id}")
    Call<UserLogin> upload_image(@Path("id") String id,
                            @Part MultipartBody.Part avatar);

    @FormUrlEncoded
    @PUT("users/reset/{id}")
    Call<UserLogin> resetPass(@Path("id") String userId,
                              @Field("oldPass") String oldPass,
                              @Field("newPass") String newPass);
}
