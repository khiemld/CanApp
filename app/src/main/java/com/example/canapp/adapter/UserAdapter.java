package com.example.canapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.DetailRateActivity;
import com.example.canapp.R;
import com.example.canapp.RatingActivity;
import com.example.canapp.model.user.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;

    private List<User> users;

    private boolean isOpen;

    private String idPlan;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setUser(List<User> list, boolean is, String idPlan){
        this.users = list;
        this.isOpen = is;
        this.idPlan = idPlan;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_personal_rate, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = users.get(position);
        if (user == null){
            return;
        }
        holder.txt_nameMember.setText(user.getName());
        holder.txt_emailMember.setText(user.getEmail());
        if (!isOpen){
            holder.txt_danhgia.setVisibility(View.INVISIBLE);
        } else {
            holder.txt_danhgia.setVisibility(View.VISIBLE);
            holder.txt_danhgia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent createRatingIntent = new Intent(context, RatingActivity.class);
                    createRatingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    Bundle bundle = new Bundle();
                    bundle.putString("idPlan", idPlan);
                    bundle.putSerializable("member", user);
                    createRatingIntent.putExtras(bundle);
                    context.startActivity(createRatingIntent);
                }
            });
        }
        if (!user.getAvatar().equals("null")){
            Glide.with(context).load(user.getAvatar()).into(holder.img_avatarMember);
        } else {
            holder.img_avatarMember.setImageResource(R.drawable.avatar_profile);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createDetailRateIntent = new Intent(context, DetailRateActivity.class);
                createDetailRateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Bundle bundle = new Bundle();
                bundle.putString("idPlan", idPlan);
                bundle.putSerializable("member", user);
                createDetailRateIntent.putExtras(bundle);
                context.startActivity(createDetailRateIntent);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (users != null){
            return users.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_nameMember, txt_emailMember, txt_danhgia;

        private ImageView img_avatarMember;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameMember = itemView.findViewById(R.id.tv_namememberRate);
            txt_emailMember = itemView.findViewById(R.id.tv_emailmemberRate);
            img_avatarMember = itemView.findViewById(R.id.cr_avatarRate);
            txt_danhgia = itemView.findViewById(R.id.tv_doRate);
        }
    }
}
