package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inspector.IntFlagMapping;
import android.widget.Button;

import com.example.canapp.adapter.DiscussionAdapter;
import com.example.canapp.model.discussion.Discussion;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity {

    private RecyclerView rcv_personalDiscussion, rcv_memberDiscussion;

    private DiscussionAdapter discussionAdapter;

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        discussionAdapter = new DiscussionAdapter(getApplicationContext());

        Mapping();
        getAllMyDiscussion();
        getAllMemberDiscussion();
        
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

        btn_Huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private void getAllMemberDiscussion() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcv_memberDiscussion.setLayoutManager(layoutManager);

        discussionAdapter.setDiscussion(getListDiscussion());
        rcv_memberDiscussion.setAdapter(discussionAdapter);
    }

    private void getAllMyDiscussion() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        rcv_personalDiscussion.setLayoutManager(layoutManager);

        discussionAdapter.setDiscussion(getListDiscussion());
        rcv_personalDiscussion.setAdapter(discussionAdapter);
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
}