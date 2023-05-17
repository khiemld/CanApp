package com.example.canapp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.canapp.adapter.AvtarAdapter;
import com.example.canapp.api.PlanApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.project.ProjectDetailResponse;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.task.Task;
import com.example.canapp.model.type.Type;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProjectInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProjectInfo extends Fragment implements AddMemBer.ISendProjFromAddMem {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";
    public static final String TAG = ProjectInfo.class.getName();

    private View mView;

    // TODO: Rename and change types of parameters
    private ProjectInProjectDetail mProject;
    private List<Type> mLists;
    private List<Task> mTasks;
    private User mManager;
    private List<User> mMembers;
    List<User> mUsers;
    private String mParam2;

    public ProjectInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProjectInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static ProjectInfo newInstance(ProjectInProjectDetail mProject) {
        ProjectInfo fragment = new ProjectInfo();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, mProject);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

    }

    @Override
    public void onStart() {
        super.onStart();
        handleParentView();
        loadAvatar();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                handleBackButton();
            }
        }, 300);
        handleAddMemberClick();
        handleMemberListClick();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProject = (ProjectInProjectDetail) getArguments().getSerializable(ARG_PARAM1);
            mLists = mProject.getColumns();
            mManager = mProject.getManager().get(0);
            mMembers = mProject.getMembers();
            mUsers = new ArrayList<>();
            mUsers.addAll(mMembers);
            mUsers.add(mManager);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_project_info, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View container = mView.findViewById(R.id.layout_moreInfo_mainContaniner);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_login);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        container.setAnimation(animation);
    }

    void loadAvatar() {
        RecyclerView recyclerView = (RecyclerView) mView.findViewById(R.id.rc_listAvatar);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(),
                        RecyclerView.HORIZONTAL, false);

        List<String> avatarList = new ArrayList<>();
        for (User iUser : mUsers) {
            if (!iUser.getAvatar().equals(""))
                avatarList.add(iUser.getAvatar());
        }
        AvtarAdapter adapter = new AvtarAdapter(mUsers, getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    void handleBackButton() {
        ((ImageView) mView.findViewById(R.id.img_projectMoreInfo_back)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View container = mView.findViewById(R.id.layout_moreInfo_mainContaniner);
                        Animation animation =
                                AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                        animation.setInterpolator(new DecelerateInterpolator());
                        animation.setDuration(300);
                        container.startAnimation(animation);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                getParentFragmentManager().popBackStack();
                                getChildFragmentManager().popBackStack();
                            }
                        }, 300);
                    }
                });
    }

    void handleAddMemberClick() {
        if (!getUserID().equals(mProject.getManager().get(0).get_id())) {
            ((TextView) mView.findViewById(R.id.tv_addMember_addButton)).setVisibility(View.GONE);
            return;
        }

        ((TextView) mView.findViewById(R.id.tv_addMember_addButton)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.fragment_container2,
                                AddMemBer.newInstance(mProject));
                        fragmentTransaction.addToBackStack(AddMemBer.TAG);
                        fragmentTransaction.commit();
                    }
                });
    }

    void handleParentView() {
        ((ConstraintLayout) mView.findViewById(
                R.id.layout_moreInfo_mainContaniner)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }

    void handleMemberListClick() {
        ((LinearLayout) mView.findViewById(R.id.layout_seeDetail)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FragmentTransaction fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.add(R.id.fragment_container,
                                MemberList.newInstance(mProject));
                        fragmentTransaction.addToBackStack(MemberList.TAG);
                        fragmentTransaction.commit();
                    }
                });
    }

    @Override
    public void sendProject(ProjectInProjectDetail project) {

    }

    public void receiveProject(ProjectInProjectDetail project) {
        mProject = project;
        mLists = mProject.getColumns();
        mManager = mProject.getManager().get(0);
        mMembers = mProject.getMembers();
        mUsers = new ArrayList<>();
        mUsers.addAll(mMembers);
        mUsers.add(mManager);
        loadAvatar();
    }

    String getUserID() {
        return "64511d75da6a2ab371790258a";
    }
}