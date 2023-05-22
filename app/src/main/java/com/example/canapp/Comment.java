package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.adapter.CommentAdapter;
import com.example.canapp.adapter.PlanAdapter;
import com.example.canapp.api.LikePostApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.discussion.CommentInDiscuss;
import com.example.canapp.model.discussion.CommentResponse;
import com.example.canapp.model.discussion.DetailDiscussion;
import com.example.canapp.model.discussion.LikePost;
import com.example.canapp.model.project.DetailProject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Comment extends AppCompatActivity {

    List<CommentInDiscuss> List_comment;
    RecyclerView rv_comment;
    EditText edt_comment;
    ImageView btn_send,btn_back;
    CommentAdapter commentAdapter;
    private LikePostApi api;
    String uId,postId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        Mapping();

        List_comment=new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        uId=bundle.getString("uId");
        postId=bundle.getString("postId");
        if (bundle != null && bundle.containsKey("mylist")) {
            // Nhận danh sách từ Bundle
            List<CommentInDiscuss> danhSach = (ArrayList<CommentInDiscuss>) bundle.getSerializable("mylist");

            int count = danhSach.size();
            if(count>0){
                for(int i=0;i<count;i++){
                    List_comment.add(danhSach.get(i));
                }
            }
            getAllComment(List_comment);
        }
        else {
            Log.e("lỗi","1");
        }
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddComment();
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    public void Mapping(){

        rv_comment = findViewById(R.id.rv_comment);
        edt_comment=findViewById(R.id.edt_comment);
        btn_send=findViewById(R.id.img_send);
        btn_back=findViewById(R.id.imageback);
    }
    private void getAllComment(List<CommentInDiscuss> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rv_comment.setLayoutManager(layoutManager);

        commentAdapter = new CommentAdapter(getApplicationContext());
        commentAdapter.setComment(list);
        rv_comment.setAdapter(commentAdapter);
    }
    public void AddComment(){
        String txt_comment=edt_comment.getText().toString();
        try{
            api = RetrofitClient.getRetrofit().create(LikePostApi.class);
            Call<CommentResponse> call = api.commentPost(uId, postId,txt_comment);
            call.enqueue(new Callback<CommentResponse>() {
                @Override
                public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                    if(response.isSuccessful()){
                        finish();
                       // Intent intent = new Intent(Comment.this,Comment.class);
                        //Intent intent = new Intent(Comment.this,ProjectInfo.class);
                        //startActivity(intent);
                    }
                    else {
                        Log.e("in comment","lỗi");
                    }
                }

                @Override
                public void onFailure(Call<CommentResponse> call, Throwable t) {
                    Log.e("Loi trong comment: ",t.getMessage());
                }
            });
        }catch (Exception e){
            Log.e("error",e.getMessage());
        }

    }
}