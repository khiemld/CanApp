package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inspector.IntFlagMapping;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canapp.adapter.DiscussionAdapter;
import com.example.canapp.api.DiscussionApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.api.TaskApi;
import com.example.canapp.model.discussion.AllDiscussion;
import com.example.canapp.model.discussion.CommentResponse;
import com.example.canapp.model.discussion.DetailDiscussion;
import com.example.canapp.model.discussion.Discussion;
import com.example.canapp.model.project.DetailProject;
import com.example.canapp.ulti.SharedPrefManager;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DiscussionActivity extends AppCompatActivity {

    private RecyclerView rcv_personalDiscussion, rcv_memberDiscussion;

    private DiscussionAdapter discussionAdapter;

    private FloatingActionButton floatingActionButton;

    private DiscussionApi discussionApi;
    private List<DetailDiscussion> listAll;
    private List<DetailDiscussion> listMyPost;
    private List<DetailDiscussion> listOtherPost;

    private String planId;
    private String userId="64640b2c7387efac4b4ab391";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        Mapping();
        listAll=new ArrayList<>();
        listMyPost=new ArrayList<>();
        listOtherPost=new ArrayList<>();

        discussionAdapter = new DiscussionAdapter(getApplicationContext());
        getListSiDiscussion();

        //userId = SharedPrefManager.getInstance(this).getUser().get_id();
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAddDiscussionDialog(Gravity.CENTER);
            }
        });

    }

    private void openAddDiscussionDialog(int gravity) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_adddiscussion);

        Window window = dialog.getWindow();
        if (window == null){
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        if (Gravity.BOTTOM == gravity){
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        Button btn_Huy = dialog.findViewById(R.id.btn_canceladdDiscussion);

        EditText edt_title=dialog.findViewById(R.id.edt_titleaddDiscussion);
        EditText edt_content = dialog.findViewById(R.id.edt_contentaddDiscussion);
        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button btn_create=dialog.findViewById(R.id.btn_postaddDiscussion);
        btn_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edt_title.getText().toString();
                String content = edt_content.getText().toString();
                planId="6465c3656da61827dc5beea6";
                //ghep roi chuyen planId vao va userId vao
                discussionApi= RetrofitClient.getRetrofit().create(DiscussionApi.class);
                Call<CommentResponse> call = discussionApi.createPost(userId,planId,title,content);
                call.enqueue(new Callback<CommentResponse>() {
                    @Override
                    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
                        if(response.isSuccessful()){
                            recreate();
                            //Toast.makeText(DiscussionActivity.this, "Thanh cong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<CommentResponse> call, Throwable t) {
                        Log.e("loi",t.getMessage());
                    }
                });
            }
        });

        dialog.show();
    }

    private void getAllMemberDiscussion(List<DetailDiscussion> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcv_memberDiscussion.setLayoutManager(layoutManager);

        discussionAdapter = new DiscussionAdapter(getApplicationContext());
        discussionAdapter.setDiscussion(list);
        rcv_memberDiscussion.setAdapter(discussionAdapter);
        discussionAdapter.notifyDataSetChanged();

    }

    private void getAllMyDiscussion(List<DetailDiscussion> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcv_personalDiscussion.setLayoutManager(layoutManager);

        discussionAdapter = new DiscussionAdapter(getApplicationContext());
        discussionAdapter.setDiscussion(list);
        rcv_personalDiscussion.setAdapter(discussionAdapter);
        discussionAdapter.notifyDataSetChanged();
    }

    private List<Discussion> getListDiscussion() {
        List<Discussion> discussions = new ArrayList<>();
        discussions.add(new Discussion("101 dấu hiệu của trapboy", "Bà Tiên Mập", "02/04/2023"));
        discussions.add(new Discussion("101 dấu hiệu của trapboy", "Bà Tiên Mập", "02/04/2023"));
        discussions.add(new Discussion("101 dấu hiệu của trapboy", "Bà Tiên Mập", "02/04/2023"));
        discussions.add(new Discussion("101 dấu hiệu của trapboy", "Bà Tiên Mập", "02/04/2023"));
        discussions.add(new Discussion("101 dấu hiệu của trapboy", "Bà Tiên Mập", "02/04/2023"));

        return discussions;
    }


    private void Mapping() {
        rcv_memberDiscussion = findViewById(R.id.rcv_memberDiscussion);
        rcv_personalDiscussion = findViewById(R.id.rcv_personalDiscussion);
        floatingActionButton = findViewById(R.id.btn_floatingaddDiscussion);
    }
    public void getListSiDiscussion(){
        listMyPost = new ArrayList<>(); // Khởi tạo danh sách listMyPost
        listOtherPost = new ArrayList<>(); // Khởi tạo danh sách listOtherPost
       /* final Dialog dialog = createDialogFrom(R.layout.layout_progress_dialop);
        dialog.show();*/
        discussionApi= RetrofitClient.getRetrofit().create(DiscussionApi.class);
        Call<List<DetailDiscussion>> call = discussionApi.getAll();
        call.enqueue(new Callback<List<DetailDiscussion>>() {
            @Override
            public void onResponse(Call<List<DetailDiscussion>> call, Response<List<DetailDiscussion>> response) {
                if(response.isSuccessful()){
                    listAll=response.body();
                    try {
                        int count = listAll.size();
                        //Log.e("size",String.valueOf(count));
                        if(count>0){
                            for(int i=0;i<count;i++) {

                                String user=listAll.get(i).getUser();
                                if (user.equals(userId)) {
                                    listMyPost.add(listAll.get(i));
                                    //Log.e("userId",user +" " +userId);
                                } else {
                                    listOtherPost.add(listAll.get(i));
                                }
                            }
                        }
                    }catch (Exception e){
                        //Toast.makeText(DiscussionActivity.this, "error", Toast.LENGTH_SHORT).show();
                        Log.e("loi",e.getMessage());
                    }
                    getAllMyDiscussion(listMyPost);
                    getAllMemberDiscussion(listOtherPost);
                }
                else
                {
                    Toast.makeText(DiscussionActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailDiscussion>> call, Throwable t) {
                Toast.makeText(DiscussionActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}