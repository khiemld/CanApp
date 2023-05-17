package com.example.canapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.R;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.api.UserApi;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvtarAdapter extends RecyclerView.Adapter<AvtarAdapter.MyViewHolder> {
    List<User> mUserList;
    Context mContext;

    List<User> allUser;

    public AvtarAdapter(List<User> mUserList, Context mContext) {
        this.mUserList = mUserList;
        this.mContext = mContext;
    }

    public List<User> getmUserList() {
        return mUserList;
    }

    public void setmUserList(List<User> mUserList) {
        this.mUserList = mUserList;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public List<User> getAllUser() {
        return allUser;
    }

    public void setAllUser(List<User> allUser) {
        this.allUser = allUser;
    }

    @NonNull
    @Override
    public AvtarAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_avatar, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvtarAdapter.MyViewHolder holder, int position) {
        if (mUserList.get(position) != null) {
            User iUser = mUserList.get(position);
            User alterUser = iUser;

            /**
             * Bởi vì adapter này sẽ sủ dụng cho 2 list input khác nhau
             * - Nếu list user đầy đủ thì chỉ cần đổ data vào
             * - Nếu list user chỉ chứa id thì phải lấy user khác thay thế để lấy avatar
             * */
            if (iUser.getAvatar() == null) {
                for (User user : allUser) {
                    if (user.get_id().equals(iUser.get_id())) {
                        alterUser = user;
                    }
                }
            }
            if (!alterUser.getAvatar().equals("null")) {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_avatar_replace)).setVisibility(View.GONE);
                Glide.with(mContext).load(alterUser.getAvatar())
                        .into(holder.imageView);
            } else {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_avatar_replace)).setText(
                        String.valueOf(alterUser.getName().toUpperCase().charAt(0)));
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mUserList != null) {
            return mUserList.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imv_avatar);
        }
    }
}
