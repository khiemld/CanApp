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

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;

    private List<User> users;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public void setUser(List<User> list){
        this.users = list;
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
        Glide.with(context).load(user.getAvatar()).into(holder.img_avatarMember);
    }

    @Override
    public int getItemCount() {
        if (users != null){
            return users.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_nameMember, txt_emailMember;

        private ImageView img_avatarMember;
        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            txt_nameMember = itemView.findViewById(R.id.tv_name_user_comment);
            txt_emailMember = itemView.findViewById(R.id.tv_comment);
            img_avatarMember = itemView.findViewById(R.id.cr_avatarRate);
        }
    }
}
