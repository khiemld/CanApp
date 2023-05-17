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
import com.example.canapp.MemberList;
import com.example.canapp.R;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.user.User;

import java.util.List;

public class MemberListAdapter extends RecyclerView.Adapter<MemberListAdapter.MyViewHolder> {

    Context mContext;
    List<User> mDatas;

    ProjectInProjectDetail mProject;

    public MemberListAdapter(Context mContext, List<User> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    public void setProject(ProjectInProjectDetail project) {
        this.mProject = project;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_list_item, parent, false);
        return new MemberListAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        User iUser = mDatas.get(position);
        if (iUser != null) {
            if (!iUser.getAvatar().equals("null")) {
                ((TextView) holder.itemView.findViewById(
                        R.id.tv_memberList_replace)).setVisibility(View.GONE);
                Glide.with(mContext).load(iUser.getAvatar()).into(holder.imageView);
            } else {
                ((TextView) holder.itemView.findViewById(R.id.tv_memberList_replace)).setText(
                        String.valueOf(iUser.getName().toUpperCase().charAt(0)));
            }
            if (mProject.getManager().get(0).get_id().equals(iUser.get_id())) {
                holder.memberRole.setText("Trưởng nhóm");
            } else {
                holder.memberRole.setText("Thành viên");
            }
            holder.memberEmail.setText(iUser.getEmail());
            holder.memberName.setText(iUser.getName());
        }
    }

    @Override
    public int getItemCount() {
        if (mDatas != null) {
            return mDatas.size();
        }
        return 0;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView memberName, memberEmail, memberRole;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imv_memberList_avt);
            memberName = itemView.findViewById(R.id.tv_memberList_memberName);
            memberEmail = itemView.findViewById(R.id.tv_memberList_memberMail);
            memberRole = itemView.findViewById(R.id.tv_memberList_memberRole);
        }
    }
}
