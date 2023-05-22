package com.example.canapp.api;

import com.example.canapp.model.discussion.AllDiscussion;
import com.example.canapp.model.discussion.CommentResponse;
import com.example.canapp.model.discussion.DetailDiscussion;
import com.example.canapp.model.project.ProjectDetailResponse;
import com.example.canapp.model.project.ProjectFull;
import com.example.canapp.model.project.ProjectResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DiscussionApi {

    @GET("post")
    Call<List<DetailDiscussion>> getAll();

    @GET("post/{id}")
    Call<AllDiscussion> getDiscussionDetail(@Path("id") String postID);

    @FormUrlEncoded
    @POST("post/create/{uid}/{planId}")
    Call<CommentResponse> createPost(
            @Path("uid") String userId,
            @Path("planId") String planId,
            @Field("title") String title,
            @Field("text") String text
    );
}
