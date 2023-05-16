package com.example.canapp.api;

import com.example.canapp.model.project.ProjectResponse;
import com.example.canapp.model.type.AddTypeResponse;
import com.example.canapp.model.type.Type;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface TypeApi {
    @FormUrlEncoded
    @POST("list/add/{uid}/{planID}")
    Call<AddTypeResponse> addType(
            @Path("uid") String managerID,
            @Path("planID") String planID,
            @Field("name") String name
    );
}
