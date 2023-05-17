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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.canapp.adapter.PlanAdapter;
import com.example.canapp.adapter.ProjectAdapter;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.api.TaskApi;
import com.example.canapp.model.project.DetailProject;
import com.example.canapp.model.project.ListProjectofUser;
import com.example.canapp.model.project.Project;
import com.example.canapp.ulti.SharedPrefManager;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends Fragment {


    private RecyclerView rv_projectRecent, rv_myProject, rv_projectEnjoy;
    private PlanAdapter planAdapter;

    private TaskApi taskApi;

    private View view;

    private List<DetailProject> listMyPlan, listMemberPlan, listRecentPlan;

    private String myId;
    /*BottomNavigationItemView account;*/

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_home, container, false);

        myId = SharedPrefManager.getInstance(getContext()).getUser().get_id();

        listMyPlan = new ArrayList<>();
        listRecentPlan = new ArrayList<>();
        listMemberPlan = new ArrayList<>();

        Mapping();

        getListPlans();

        return view;
    }


    private void getAllprojectEnjoy(List<DetailProject> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv_projectEnjoy.setLayoutManager(layoutManager);

        planAdapter = new PlanAdapter(getContext());
        planAdapter.setProjects(list);
        rv_projectEnjoy.setAdapter(planAdapter);
    }

    private void getAllmyProject(List<DetailProject> list) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv_myProject.setLayoutManager(layoutManager);

        planAdapter = new PlanAdapter(getContext());
        planAdapter.setProjects(list);
        rv_myProject.setAdapter(planAdapter);
    }

    private void getAllprojectRecent(List<DetailProject> list) {

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        rv_projectRecent.setLayoutManager(layoutManager);

        planAdapter = new PlanAdapter(getContext());
        planAdapter.setProjects(list);
        rv_projectRecent.setAdapter(planAdapter);
    }

    private void getListPlans(){
        taskApi = RetrofitClient.getRetrofit().create(TaskApi.class);

        Call<ListProjectofUser> call = taskApi.getAllPlanofUser(myId);

        call.enqueue(new Callback<ListProjectofUser>() {
            @Override
            public void onResponse(Call<ListProjectofUser> call, Response<ListProjectofUser> response) {
                if (response.isSuccessful()){


                    int countplan = response.body().getPlan().size();

                    for (int i = 0; i < countplan; i++){
                        if (response.body().getPlan().get(i).getManager().get_id().equals(myId)){
                            listMyPlan.add(response.body().getPlan().get(i));

                        } else {
                            listMemberPlan.add(response.body().getPlan().get(i));

                        }
                    }

                    for (DetailProject project: response.body().getPlan()) {


                    }

                    listRecentPlan.add(response.body().getPlan().get(countplan - 1));

                    //Up data lên giao diện
                    getAllprojectRecent(listRecentPlan);

                    getAllprojectEnjoy(listMemberPlan);
                    getAllmyProject(listMyPlan);
                }
                else{
                    try {
                        Log.e("homeactivity: ",response.errorBody().string() );
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ListProjectofUser> call, Throwable t) {
                Log.e( "onFailure: ",t.getMessage() );
            }
        });
    }

    private void Mapping() {
        rv_projectRecent = view.findViewById(R.id.rcv_projectRecent);
        rv_myProject = view.findViewById(R.id.rcv_myProject);
        rv_projectEnjoy = view.findViewById(R.id.rcv_projectEnjoy);
        /*account=findViewById(R.id.ic_account);*/
    }
}