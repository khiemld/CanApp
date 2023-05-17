package com.example.canapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.canapp.adapter.AddMemberTaskAdapter;
import com.example.canapp.adapter.SearchItemAdapter;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.task.Task;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMemberToTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMemberToTaskFragment extends Fragment {

    public static String TAG = AddMemberToTaskFragment.class.getName();

    ImageView backButton;
    TextView memberCount;
    RecyclerView recyclerView;
    AddMemberTaskAdapter adapter;
    View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";
    private static final String ARG_PARAM2 = "task";

    // TODO: Rename and change types of parameters
    private ProjectInProjectDetail mProject;

    private Task mTask;

    private IAddMemberToTaskListener iAddMemberToTaskListener;

    public interface IAddMemberToTaskListener {
        void addMemberToTask(User user, Task task);
    }

    public AddMemberToTaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static AddMemberToTaskFragment newInstance(ProjectInProjectDetail project, Task task) {
        AddMemberToTaskFragment fragment = new AddMemberToTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, project);
        args.putSerializable(ARG_PARAM2, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iAddMemberToTaskListener = (IAddMemberToTaskListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProject = (ProjectInProjectDetail) getArguments().getSerializable(ARG_PARAM1);
            mTask = (Task) getArguments().getSerializable(ARG_PARAM2);

            List<User> userList = new ArrayList<>();
            userList.addAll(mProject.getMembers());
            userList.add(mProject.getManager().get(0));

            adapter = new AddMemberTaskAdapter();
            adapter.setmTask(mTask);
            adapter.setmContext(getContext());
            adapter.setUserList(userList);
            adapter.setmProject(mProject);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_member_to_task, container, false);
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        View container = mView.findViewById(R.id.layout_addMemberList_mainContaniner);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_login);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        container.setAnimation(animation);
    }

    @Override
    public void onStart() {
        super.onStart();

        backButton = mView.findViewById(R.id.img_addMemberList_back);
        memberCount = mView.findViewById(R.id.tv_addMemberList_count);
        recyclerView = mView.findViewById(R.id.rcv_addMemberList_searchResult);
        memberCount = mView.findViewById(R.id.tv_addMemberList_count);
        memberCount.setText((Integer.toString(mTask.getMembers().size())));
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        handleBackButton();
        handleParentView();
    }

    private void handleParentView() {
        ((ConstraintLayout) mView.findViewById(
                R.id.layout_addMemberList_mainContaniner)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }

    private void handleBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (adapter.getAddedUser() != null) {
                    iAddMemberToTaskListener.addMemberToTask(adapter.getAddedUser(), mTask);
                }

                View container = mView.findViewById(R.id.layout_addMemberList_mainContaniner);
                Animation animation =
                        AnimationUtils.loadAnimation(getContext(), R.anim.slide_down);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.setDuration(300);
                container.startAnimation(animation);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getParentFragmentManager().popBackStack();
                            getChildFragmentManager().popBackStack();
                        } catch (Throwable t) {
                            Log.e(TAG, "run: " + t.getMessage());
                        }
                    }
                }, 300);

            }
        });
    }
}