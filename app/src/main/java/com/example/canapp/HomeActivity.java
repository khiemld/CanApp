package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.canapp.adapter.ProjectAdapter;
import com.example.canapp.model.project.Project;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView rv_projectRecent, rv_myProject, rv_projectEnjoy;
    private ProjectAdapter projectAdapter;
    BottomNavigationItemView account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        projectAdapter = new ProjectAdapter(this);

        Mapping();

        getAllprojectRecent();
        getAllmyProject();
        getAllprojectEnjoy();
        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,My_Profile.class);
                startActivity(intent);
            }
        });
    }

    private void getAllprojectEnjoy() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rv_projectEnjoy.setLayoutManager(layoutManager);

        projectAdapter.setData(getListProject());
        rv_projectEnjoy.setAdapter(projectAdapter);
    }

    private void getAllmyProject() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rv_myProject.setLayoutManager(layoutManager);

        projectAdapter.setData(getListProject());
        rv_myProject.setAdapter(projectAdapter);
    }

    private void getAllprojectRecent() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rv_projectRecent.setLayoutManager(layoutManager);

        projectAdapter.setData(getListProject());
        rv_projectRecent.setAdapter(projectAdapter);
    }

    private List<Project> getListProject(){
        List<Project> list = new ArrayList<>();
        list.add(new Project("Yorn and Alice", "Đi rồng phải ăn con chim", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum", R.drawable.avatar_profile, "01.01.2023"));
        list.add(new Project("Yorn and Alice", "Đi rồng phải ăn con chim", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum", R.drawable.avatar_profile, "01.01.2023"));
        list.add(new Project("Yorn and Alice", "Đi rồng phải ăn con chim", "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum", R.drawable.avatar_profile, "01.01.2023"));

        return list;
    }

    private void Mapping() {
        rv_projectRecent = findViewById(R.id.rcv_projectRecent);
        rv_myProject = findViewById(R.id.rcv_myProject);
        rv_projectEnjoy = findViewById(R.id.rcv_projectEnjoy);
        account=findViewById(R.id.ic_account);
    }
}