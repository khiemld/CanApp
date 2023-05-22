package com.example.canapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.canapp.R;
import com.example.canapp.model.discussion.CommentInDiscuss;
import com.example.canapp.model.project.DetailProject;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentiewHolder>{
    Context context;
    List<CommentInDiscuss> comment;

    public CommentAdapter(Context context) {
        this.context = context;
    }
    public void setComment(List<CommentInDiscuss> list) {
        this.comment = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentiewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_comment, parent, false);
        return new CommentiewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentiewHolder holder, int position) {
        CommentInDiscuss commentInDiscuss = comment.get(position);
        if(commentInDiscuss==null){
            return;
        }
        holder.comment.setText(commentInDiscuss.getText());
        holder.userName.setText(commentInDiscuss.getName());
        holder.date.setText(commentInDiscuss.getDate());
        //holder.imageView.setImageResource(commentInDiscuss.getAvatar());

    }

    @Override
    public int getItemCount() {
        if (comment != null){
            return comment.size();
        }
        return 0;
    }
    public class CommentiewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView userName, comment, date;

        public CommentiewHolder(@NonNull View itemView) {
            super(itemView);
            //imageView = itemView.findViewById(R.id.imv_memberList_avt);
            userName = itemView.findViewById(R.id.tv_name_comment);
            comment = itemView.findViewById(R.id.tv_context_comment);
            date = itemView.findViewById(R.id.tv_comment_date);
        }
    }
}
