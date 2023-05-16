package com.example.canapp.api;

import com.example.canapp.model.task.AddTaskResponse;
import com.example.canapp.model.type.AddTypeResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TaskApi {

    @FormUrlEncoded
    @POST("task/add/{uid}/{planID}/{typeID}")
    Call<AddTaskResponse> addTask(
            @Path("uid") String managerID,
            @Path("planID") String planID,
            @Path(("typeID")) String typeID,
            @Field("title") String name
    );

    @FormUrlEncoded
    @PUT("list/move")
    Call<AddTaskResponse> moveTask(
            @Field("indexMove") int toRow,
            @Field("fromId") String fromColumnID,
            @Field("toId") String toColumnID,
            @Field("idTask") String taskID
    );
}
