package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.inspector.IntFlagMapping;

import com.example.canapp.adapter.DiscussionAdapter;
import com.example.canapp.model.discussion.Discussion;

import java.util.ArrayList;
import java.util.List;

public class DiscussionActivity extends AppCompatActivity {

    private RecyclerView rcv_personalDiscussion, rcv_memberDiscussion;

    private DiscussionAdapter discussionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);

        discussionAdapter = new DiscussionAdapter(getApplicationContext());

        Mapping();
        getAllMyDiscussion();
        getAllMemberDiscussion();
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
    }
}