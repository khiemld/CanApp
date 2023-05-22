package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.TextView;

import com.example.canapp.adapter.UserAdapter;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.user.User;
import com.example.canapp.ulti.SharedPrefManager;

import java.util.ArrayList;
import java.util.List;

public class RateActivity extends AppCompatActivity {

    private RecyclerView rcv_memberRate;

    private UserAdapter userAdapter;

    private ConstraintLayout container;

    private String idUser;

    private ProjectInProjectDetail mProject;

    private List<User> mUsers;

    private TextView txt_openRate;

    private boolean isOpen;

    private String idPlan = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rate);

        Mapping();

        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_login);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        container.startAnimation(animation);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);



        idUser = SharedPrefManager.getInstance(getApplicationContext()).getUser().get_id();

        handleGetmProject();
        getAllMember();
        //handleClickOpenRate();
    }

    private void handleClickOpenRate() {
        txt_openRate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_openRate.setVisibility(View.INVISIBLE);
                isOpen = true;
                getAllMember();
            }
        });
    }

    private void handleGetmProject() {
        mProject = (ProjectInProjectDetail) getIntent().getExtras().get("project");
        if (mProject != null){
            idPlan = mProject.get_id();
            if (idUser.equals(mProject.getManager().get(0).get_id())){
                txt_openRate.setVisibility(View.VISIBLE);
            }  else {
                mUsers.add(mProject.getManager().get(0));
            }

            for (int i = 0; i < mProject.getMembers().size(); i++){
                if (!idUser.equals(mProject.getMembers().get(i).get_id())){
                    mUsers.add(mProject.getMembers().get(i));
                }
            }
        }
    }

    private void getAllMember() {
        userAdapter = new UserAdapter(getApplicationContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rcv_memberRate.setLayoutManager(layoutManager);

        userAdapter.setUser(mUsers, isOpen, idPlan);
        rcv_memberRate.setAdapter(userAdapter);
    }


    private void Mapping() {
        container = findViewById(R.id.constraint_Rate);
        rcv_memberRate = findViewById(R.id.rv_ratememberRate);
        txt_openRate = findViewById(R.id.tv_openRate);

        /*Khoi tao*/
        txt_openRate.setVisibility(View.INVISIBLE);
        mUsers = new ArrayList<>();
        isOpen = true;
    }
}