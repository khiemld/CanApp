package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.canapp.adapter.ProjectAdapter;
import com.example.canapp.model.Project;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends Fragment {


    private RecyclerView rv_projectRecent, rv_myProject, rv_projectEnjoy;
    private ProjectAdapter projectAdapter;

    private View view;
    /*BottomNavigationItemView account;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);

        projectAdapter = new ProjectAdapter(getContext());

        Mapping();

        getAllprojectRecent();
        getAllmyProject();
        getAllprojectEnjoy();
        return view;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);

        projectAdapter = new ProjectAdapter(this);

        Mapping();
        LoadNavigation();

        getAllprojectRecent();
        getAllmyProject();
        getAllprojectEnjoy();

        *//*account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this,My_Profile.class);
                startActivity(intent);
            }
        });*//*
    }*/

    private void getAllprojectEnjoy() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv_projectEnjoy.setLayoutManager(layoutManager);

        projectAdapter.setData(getListProject());
        rv_projectEnjoy.setAdapter(projectAdapter);
    }

    private void getAllmyProject() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv_myProject.setLayoutManager(layoutManager);

        projectAdapter.setData(getListProject());
        rv_myProject.setAdapter(projectAdapter);
    }

    private void getAllprojectRecent() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
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
        rv_projectRecent = view.findViewById(R.id.rcv_projectRecent);
        rv_myProject = view.findViewById(R.id.rcv_myProject);
        rv_projectEnjoy = view.findViewById(R.id.rcv_projectEnjoy);
        /*account=findViewById(R.id.ic_account);*/
    }
}