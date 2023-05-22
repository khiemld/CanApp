package com.example.canapp.api;

import com.example.canapp.model.rate.MessageRate;
import com.example.canapp.model.rate.PersonalRate;
import com.example.canapp.model.rate.Rate;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RateApi {

    @FormUrlEncoded
    @POST("rate/add")
    Call<MessageRate> addRate(@Field("memberId") String memberId,
                              @Field("judgeId") String judgeId,
                              @Field("planId") String planId,
                              @Field("attitude") Integer attitude,
                              @Field("expertise") Integer expertise,
                              @Field("discipline") Integer discipline,
                              @Field("collaborate") Integer collaborate,
                              @Field("performance") Integer performance,
                              @Field("comment") String comment);

    @GET("rate/{idPlan}")
    Call<List<PersonalRate>> getAllRateofPlan(@Path("idPlan") String idPlan);
}
