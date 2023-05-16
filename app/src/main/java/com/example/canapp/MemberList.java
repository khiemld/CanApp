package com.example.canapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
import android.widget.TextView;

import com.example.canapp.adapter.AvtarAdapter;
import com.example.canapp.adapter.MemberListAdapter;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MemberList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MemberList extends Fragment {

    public static final String TAG = MemberList.class.getName();
    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private ProjectInProjectDetail mProject;
    private String mParam2;
    List<User> mUsers;

    public MemberList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MemberList.
     */
    // TODO: Rename and change types and number of parameters
    public static MemberList newInstance(ProjectInProjectDetail mProject) {
        MemberList fragment = new MemberList();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, mProject);
        fragment.setArguments(args);
        return fragment;
    }


    // được gọi khi Fragment hiển thị trên màn hình.
    @Override
    public void onStart() {
        super.onStart();
        loadData();
        handleBackButton();
    }


    // được gọi khi Fragment được tạo ra.
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProject = (ProjectInProjectDetail) getArguments().getSerializable(ARG_PARAM1);
            mUsers = new ArrayList<>();
            mUsers.addAll(mProject.getMembers());
            mUsers.add(0, mProject.getManager().get(0));
        }
    }


    //  được gọi khi Fragment tạo ra giao diện (View).
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_member_list, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View container = mView.findViewById(R.id.layout_memberList_mainContaniner);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_login);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        container.setAnimation(animation);
    }

    void loadData() {
        RecyclerView recyclerView =
                (RecyclerView) mView.findViewById(R.id.rcv_memberList_searchResult);
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(),
                        RecyclerView.VERTICAL, false);

        MemberListAdapter adapter = new MemberListAdapter(getContext(), mUsers);
        recyclerView.setLayoutManager(layoutManager);
        adapter.setProject(mProject);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        ((TextView) mView.findViewById(R.id.tv_memberList_count)).setText(
                Integer.toString(mUsers.size()));
    }

    void handleBackButton() {
        ((ImageView) mView.findViewById(R.id.img_memberList_back)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        View container = mView.findViewById(R.id.layout_memberList_mainContaniner);
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

}