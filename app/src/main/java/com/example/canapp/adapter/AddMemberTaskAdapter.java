package com.example.canapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.R;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.api.TaskApi;
import com.example.canapp.api.UserApi;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.task.AddTaskResponse;
import com.example.canapp.model.task.Task;
import com.example.canapp.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddMemberTaskAdapter extends RecyclerView.Adapter<AddMemberTaskAdapter.MyViewHolder> {

    Context mContext;
    ProjectInProjectDetail mProject;

    // Các thành viên hiện tại của task
    List<User> userList;

    Task mTask;

    // Thành viên vừa được thêm vào, dùng cho mấy cái imple interface nghe sự kiện thêm thành viên
    User addedUser;

    @NonNull
    @Override
    public AddMemberTaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                                int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_list_item, parent, false);
        return new AddMemberTaskAdapter.MyViewHolder(view);
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public ProjectInProjectDetail getmProject() {
        return mProject;
    }

    public void setmProject(ProjectInProjectDetail mProject) {
        this.mProject = mProject;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public Task getmTask() {
        return mTask;
    }

    public void setmTask(Task mTask) {
        this.mTask = mTask;
    }

    public User getAddedUser() {
        return addedUser;
    }

    @Override
    public void onBindViewHolder(@NonNull AddMemberTaskAdapter.MyViewHolder holder, int position) {
        User iUser = userList.get(position);
        if (iUser != null) {

            if (!iUser.getAvatar().equals("null")) {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_memberList_replace)).setVisibility(View.GONE);
                Glide.with(mContext).load(iUser.getAvatar()).into(holder.imageView);
            } else {
                ((TextView) holder.itemView.findViewById(R.id.tv_memberList_replace)).setText(
                        String.valueOf(iUser.getName().toUpperCase().charAt(0)));
            }

            holder.memberEmail.setText(iUser.getEmail());
            holder.memberName.setText(iUser.getName());

            if (getUserID().equals(mProject.getManager().get(0).get_id())) {
                if (isInTask(iUser)) {
                    holder.memberAction.setVisibility(View.GONE);
                } else {
                    holder.memberAction.setVisibility(View.VISIBLE);
                    holder.memberAction.setText("Thêm");
                    holder.memberAction.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            holder.memberAction.setVisibility(View.GONE);
                            TaskApi taskApi = RetrofitClient.getRetrofit().create(TaskApi.class);
                            Call<AddTaskResponse> call =
                                    taskApi.addMemberToTask(getUserID(), mProject.get_id(),
                                            mTask.get_id(), iUser.getEmail());

                            call.enqueue(new Callback<AddTaskResponse>() {
                                @Override
                                public void onResponse(Call<AddTaskResponse> call,
                                                       Response<AddTaskResponse> response) {
                                    if (response.isSuccessful()) {

                                        // Gán thành viên vừa được thêm là thành viên tại vị trí mới nhấn dô á
                                        addedUser = iUser;

                                        Toast.makeText(mContext, "Thêm thành công",
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    } else {
                                        Toast.makeText(mContext, "Thêm không thành công",
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                        holder.memberAction.setVisibility(View.VISIBLE);
                                        holder.memberAction.setText("Thêm");
                                        try {
                                            Log.e("Add Member Task Adapter",
                                                    "onResponse is not successfull " +
                                                            response.errorBody().string());
                                        } catch (IOException e) {
                                            throw new RuntimeException(e);
                                        }
                                    }
                                }

                                @Override
                                public void onFailure(Call<AddTaskResponse> call, Throwable t) {
                                    Log.e("Add Member Task Adapter",
                                            "onResponse is failure " +
                                                    t.getMessage());
                                    holder.memberAction.setVisibility(View.VISIBLE);
                                    holder.memberAction.setText("Thêm");
                                }
                            });
                        }
                    });
                }
            } else {
                if (isInTask(iUser)) {
                    holder.memberAction.setVisibility(View.VISIBLE);
                    holder.memberAction.setText("Đã giao");
                } else {
                    holder.memberAction.setVisibility(View.GONE);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        if (userList != null) {
            return userList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView memberName, memberEmail, memberAction;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imv_memberList_avt);
            memberName = itemView.findViewById(R.id.tv_memberList_memberName);
            memberEmail = itemView.findViewById(R.id.tv_memberList_memberMail);
            memberAction = itemView.findViewById(R.id.tv_memberList_memberRole);
        }
    }

    boolean isInTask(User user) {
        for (User i : mTask.getMembers()) {
            if (i.get_id().equals(user.get_id())) {
                return true;
            }
        }
        return false;
    }

    String getUserID() {
        return "64640b2c7387efac4b4ab391";
    }

}
