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
import com.example.canapp.model.rate.Comment;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {

    private Context context;
    private List<Comment> comments;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    public void setComment(List<Comment> list){
        this.comments = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_rate, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = comments.get(position);
        if (comment == null){
            return;
        }

        holder.txt_name.setText(comment.getName());
        holder.txt_comment.setText(comment.getComment());
        if (!comment.getAvatar().equals("null")){
            Glide.with(context).load(comment.getAvatar()).into(holder.img_avatar);
        } else {
            holder.img_avatar.setImageResource(R.drawable.avatar_profile);
        }
    }

    @Override
    public int getItemCount() {
        if (comments != null){
            return comments.size();
        }
        return 0;
    }

    public class CommentViewHolder extends RecyclerView.ViewHolder{
        private ImageView img_avatar;
        private TextView txt_name, txt_comment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            img_avatar = itemView.findViewById(R.id.cr_avatarCommentRate);
            txt_name = itemView.findViewById(R.id.tv_namememberCommentRate);
            txt_comment = itemView.findViewById(R.id.tv_doRateComment);
        }
    }
}
