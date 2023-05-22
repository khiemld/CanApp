package com.example.canapp.api;

import com.example.canapp.model.discussion.AllDiscussion;
import com.example.canapp.model.discussion.CommentResponse;
import com.example.canapp.model.discussion.LikePost;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface LikePostApi {
    @POST("post/like/{uid}/{postId}")
    Call<LikePost> createLike(
            @Path("uid") String userId,
            @Path("postId") String postId
    );

    @DELETE("post/like/{uid}/{postId}")
    Call<LikePost> unLike(
            @Path("uid") String userId,
            @Path("postId") String postId
    );
    @FormUrlEncoded
    @POST("post/comment/{uid}/{postId}")
    Call<CommentResponse> commentPost(
            @Path("uid") String userId,
            @Path("postId") String postId,
            @Field("text") String text
    );
}
