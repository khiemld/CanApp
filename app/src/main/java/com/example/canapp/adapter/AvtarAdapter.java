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
import com.example.canapp.model.user.User;

import java.util.List;

public class AvtarAdapter extends RecyclerView.Adapter<AvtarAdapter.MyViewHolder> {
    List<User> mUserList;
    Context mContext;

    public AvtarAdapter(List<User> mUserList, Context mContext) {
        this.mUserList = mUserList;
        this.mContext = mContext;
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
        if (!(mUserList.get(position) == null)) {
            User iUser = mUserList.get(position);
            if (!iUser.getAvatar().equals("null")) {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_avatar_replace)).setVisibility(View.GONE);
                Glide.with(mContext).load(iUser.getAvatar())
                        .into(holder.imageView);
            } else {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_avatar_replace)).setText(
                        String.valueOf(iUser.getName().toUpperCase().charAt(0)));
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
