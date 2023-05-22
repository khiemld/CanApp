package com.example.canapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.R;
import com.example.canapp.api.PlanApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.project.ProjectDetailResponse;
import com.example.canapp.model.project.ProjectFull;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.user.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.MyViewHolder> {

    Context mContext;
    List<User> mDatas;
    List<User> mUsers;

    ProjectInProjectDetail mProject;
    private Call<ProjectDetailResponse> planDetail;

    public SearchItemAdapter(Context mContext, List<User> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public SearchItemAdapter() {
    }

    public SearchItemAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<User> getmDatas() {
        return mDatas;
    }

    public void setmDatas(List<User> mDatas) {
        this.mDatas = mDatas;
    }

    public void setmProject(ProjectInProjectDetail mProject) {
        this.mProject = mProject;
    }

    public ProjectInProjectDetail getmProject() {
        return mProject;
    }

    @NonNull
    @Override
    public SearchItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                                             int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_search_result_item, parent, false);
        return new SearchItemAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemAdapter.MyViewHolder holder, int position) {
        User iUser = mDatas.get(position);
        if (iUser != null) {
            holder.resultEmail.setText(iUser.getEmail());
            holder.resultName.setText(iUser.getName());

            if (!iUser.getAvatar().equals("null")) {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_searchResult_replace)).setVisibility(View.GONE);
                Glide.with(mContext).load(iUser.getAvatar()).into(holder.imageView);
            } else {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_searchResult_replace)).setVisibility(View.VISIBLE);
                String userName = iUser.getName();
                holder.imageView.setImageDrawable(null);
                ((TextView) holder.itemView.findViewById(R.id.tv_searchResult_replace)).setText(
                        String.valueOf(userName.toUpperCase().charAt(0)));
            }

            if (isUser(iUser)) {
                holder.addButton.setVisibility(View.GONE);
            } else {
                holder.addButton.setVisibility(View.VISIBLE);
            }

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override

                public void onClick(View view) {

                    final Dialog confirmDialog = dialog(R.layout.layout_confirm_dialog);

                    ((TextView) confirmDialog.findViewById(R.id.tv_confim_describe)).setText(
                            "🎀 Bạn đồng ý thêm " +
                                    iUser.getName() + " vào dự án chứ?");

                    TextView cancelButton = confirmDialog.findViewById(R.id.tv_confim_cancel);
                    cancelButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            confirmDialog.dismiss();
                        }
                    });

                    TextView comfirm = confirmDialog.findViewById(R.id.tv_confim_ok);

                    comfirm.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            confirmDialog.dismiss();

                            final Dialog progressDialog = dialog(R.layout.layout_progress_dialop);

                            progressDialog.show();

                            if (isValid(iUser)) {
                                PlanApi planApi =
                                        RetrofitClient.getRetrofit().create(PlanApi.class);
                                Call<ProjectFull> call =
                                        planApi.addMember(mProject.getManager().get(0).get_id(),
                                                mProject.get_id(), iUser.getEmail());
                                call.enqueue(new Callback<ProjectFull>() {
                                    @Override
                                    public void onResponse(Call<ProjectFull> call,
                                                           Response<ProjectFull> response) {
                                        progressDialog.dismiss();
                                        if (response.isSuccessful()) {

                                            holder.addButton.setVisibility(View.GONE);
                                            Toast.makeText(mContext, "Thêm thành công!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                            resetProject(mProject.get_id());
                                        } else {
                                            Toast.makeText(mContext, "Thêm thất bại!",
                                                            Toast.LENGTH_SHORT)
                                                    .show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<ProjectFull> call, Throwable t) {
                                        progressDialog.dismiss();
                                        Toast.makeText(mContext, "Yêu cầu không thành công!",
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    }
                                });

                            } else {
                                Toast.makeText(mContext, "Thành viên đã trong dự án!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }

                        }
                    });
                    confirmDialog.show();
                }
            });
        }
    }

    private void resetProject(String projectID) {
        PlanApi planApi = RetrofitClient.getRetrofit().create(PlanApi.class);
        planDetail = planApi.getPlanDetail(projectID);
        planDetail.enqueue(new Callback<ProjectDetailResponse>() {
            @Override
            public void onResponse(Call<ProjectDetailResponse> call,
                                   Response<ProjectDetailResponse> response) {
                if (response.isSuccessful()) {
                    mProject = response.body().getPlan();
                } else {
                    try {
                        Log.e("Search Item Adapter",
                                "onResponse is not success: " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectDetailResponse> call, Throwable t) {
                Log.e("Search Item Adapter", "onResponse is failure: " + t.getMessage());
            }
        });
    }

    boolean isUser(User user) {
        List<User> mUsers = new ArrayList<>();
        mUsers.addAll(mProject.getMembers());
        mUsers.add(mProject.getManager().get(0));
        for (User item : mUsers) {
            if (user.get_id().equals(item.get_id())) {
                return true;
            }
        }
        return false;
    }

    boolean isValid(User user) {
        List<User> mUsers = new ArrayList<>();
        mUsers.addAll(mProject.getMembers());
        mUsers.add(mProject.getManager().get(0));
        for (User item : mUsers) {
            if (item.get_id().equals(user.get_id())) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView resultName, resultEmail, addButton;
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imv_searchResult_image);
            resultName = itemView.findViewById(R.id.tv_searchResult_name);
            resultEmail = itemView.findViewById(R.id.tv_searchResult_email);
            addButton = itemView.findViewById(R.id.tv_searchResult_addButton);
        }
    }

    Dialog dialog(int layout) {
        Dialog dialog = new Dialog(mContext);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(layout);
        dialog.setCancelable(false);

        Window window = dialog.getWindow();
        if (window == null) {
            return null;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;
    }
}
