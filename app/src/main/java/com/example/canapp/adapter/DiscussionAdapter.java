package com.example.canapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.canapp.Comment;
import com.example.canapp.DetailProjectActivity;
import com.example.canapp.DiscussionActivity;
import com.example.canapp.ProjectInfo;
import com.example.canapp.R;
import com.example.canapp.api.ApiService;
import com.example.canapp.api.DiscussionApi;
import com.example.canapp.api.LikePostApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.discussion.AllDiscussion;
import com.example.canapp.model.discussion.CommentInDiscuss;
import com.example.canapp.model.discussion.DetailDiscussion;
import com.example.canapp.model.discussion.Discussion;
import com.example.canapp.model.discussion.LikeInDiscuss;
import com.example.canapp.model.discussion.LikePost;
import com.example.canapp.ulti.SharedPrefManager;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionAdapter extends RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder>{

    private Context context;
    private LikePostApi api;
    private  int count=0;
    private int like=0;

    private List<DetailDiscussion> discussions;

    public DiscussionAdapter(Context context) {
        this.context = context;
    }

    public void setDiscussion(List<DetailDiscussion> list){
        this.discussions = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscussionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_discussion, parent, false);
        return new DiscussionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiscussionViewHolder holder, int position) {
        DetailDiscussion discussion = discussions.get(position);
        if (discussion == null){
            return;
        }
        holder.txt_titleDiscussion.setText(discussion.getTitle());
        holder.txt_nameownerDiscussion.setText(discussion.getName());
        holder.txt_dateDiscussion.setText(discussion.getDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   Dialog dialog = new Dialog(holder.itemView.getContext());
                   dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                   dialog.setContentView(R.layout.detail_discussion_dialog);
                   String uId=discussion.getUser();
                   //String uId= SharedPrefManager.getInstance(holder.itemView.getContext()).getUser().get_id();
                   String postId=discussion.get_id();

                   TextView tv_contentDetailDiscussion = dialog.findViewById(R.id.tv_contentDetailDiscussion);
                   tv_contentDetailDiscussion.setText(discussion.getText());
                   TextView tv_nameownerDetailDiscussion = dialog.findViewById(R.id.tv_nameownerDetailDiscussion);
                   tv_nameownerDetailDiscussion.setText(discussion.getName());
                   TextView tv_dateDetailDiscussion = dialog.findViewById(R.id.tv_dateDetailDiscussion);
                   tv_dateDetailDiscussion.setText(discussion.getDate());
                   TextView tv_titleDetailDiscussion = dialog.findViewById(R.id.tv_titleDetailDiscussion);
                   tv_titleDetailDiscussion.setText(discussion.getTitle());
                   ImageView img_avatar = dialog.findViewById(R.id.cr_avatarDetailDiscussion);
                   Glide.with(holder.itemView.getContext()).load(discussion.getAvatar()).into(img_avatar);
                   TextView tv_countLikeDetailDiscussion = dialog.findViewById(R.id.tv_countLikeDetailDiscussion);
                   LinearLayout linearLayout = dialog.findViewById(R.id.liner_like);
                   ImageView img_like = dialog.findViewById(R.id.like);
                   int count = discussion.getLikes().size();
                   tv_countLikeDetailDiscussion.setText(String.valueOf(count) +" lượt thích");
                   if(discussion.getLikes().size()>0){
                       for(LikeInDiscuss like:discussion.getLikes()){
                           if(uId.equals(like.getUser())){
                               Toast.makeText(context, "Đã like", Toast.LENGTH_SHORT).show();
                               Glide.with(holder.itemView.getContext()).load(R.drawable.ic_like_2).into(img_like);
                           }
                       }
                   }else {
                       img_like.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               api= RetrofitClient.getRetrofit().create(LikePostApi.class);
                               Call<LikePost> call = api.createLike(uId,postId);
                               call.enqueue(new Callback<LikePost>() {
                                   @Override
                                   public void onResponse(Call<LikePost> call, Response<LikePost> response) {
                                       if(response.isSuccessful()){
                                           Glide.with(holder.itemView.getContext()).load(R.drawable.ic_like_2).into(img_like);
                                           //Log.e("in like","thanh cong");
                                           Intent intent=new Intent(holder.itemView.getContext(), ProjectInfo.class);
                                           holder.itemView.getContext().startActivity(intent);
                                       }
                                       else {
                                           Log.e("in like","lỗi api");
                                       }
                                   }

                                   @Override
                                   public void onFailure(Call<LikePost> call, Throwable t) {
                                       Log.e("Loi trong like: ",t.getMessage());
                                   }
                               });
                           }
                       });
                   }

                   Window window = dialog.getWindow();
                   if (window == null){
                       return;
                   }
                   LinearLayout linearLayout_comment = dialog.findViewById(R.id.liner_layout_comment);
                   linearLayout_comment.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View view) {
                           Intent intent=new Intent(holder.itemView.getContext(),Comment.class);
                           ArrayList<CommentInDiscuss> comment = new ArrayList<>();
                           for(int i=0;i<discussion.getComments().size();i++){
                               comment.add(discussion.getComments().get(i));
                           }
                           Bundle bundle = new Bundle();
                           bundle.putString("uId", discussion.getUser());
                           bundle.putString("postId", discussion.get_id());
                           /*bundle.putString("date", discussion.getDate());*/
                           bundle.putSerializable("mylist",  comment);
                           intent.putExtras(bundle);
                           holder.itemView.getContext().startActivity(intent);
                       }
                   });

                   window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                   window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                   WindowManager.LayoutParams windowAttribute = window.getAttributes();
                   window.setAttributes(windowAttribute);
                   dialog.show();
               }
               catch (Exception e){
                   Log.e("error",e.getMessage());
               }

            }
        });
    }
    private void showDialog() {

    }

    @Override
    public int getItemCount() {
        if (discussions != null){
            return discussions.size();
        }
        return 0;
    }

    public class DiscussionViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_titleDiscussion, txt_nameownerDiscussion, txt_dateDiscussion;

        public DiscussionViewHolder(@NonNull View itemView) {
            super(itemView);

            txt_titleDiscussion = itemView.findViewById(R.id.tv_nameDiscussion);
            txt_nameownerDiscussion = itemView.findViewById(R.id.tv_nameownerDiscussion);
            txt_dateDiscussion = itemView.findViewById(R.id.tv_dateDiscussion);
        }
    }
}
