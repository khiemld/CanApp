package com.example.canapp.api;

import com.example.canapp.model.project.ProjectDetailResponse;
import com.example.canapp.model.project.ProjectFull;
import com.example.canapp.model.project.ProjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface PlanApi {

    @FormUrlEncoded
    @POST("plan/{uid}")
    Call<ProjectResponse> createProject(
            @Path("uid") String managerID,
            @Field("name") String name,
            @Field("description") String description,
            @Field("start_date") String start_date,
            @Field("end_date") String end_date
    );

    @GET("plan")
    Call<List<ProjectFull>> getAll();

    @GET("plan/{id}")
    Call<ProjectDetailResponse> getPlanDetail(@Path("id") String planID);

    @FormUrlEncoded
    @PUT("plan/members/{uid}/{planid}")
    Call<ProjectFull> addMember(@Path("uid") String uID,
                                @Path("planid") String planID,
                                @Field("email") String email);


}
