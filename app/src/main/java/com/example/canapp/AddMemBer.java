package com.example.canapp;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.canapp.adapter.SearchItemAdapter;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.api.UserApi;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMemBer#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMemBer extends Fragment {

    private View mView;
    private RecyclerView mSearchResult;
    SearchItemAdapter adapter;

    EditText inputEditText;
    public static final String TAG = AddMemBer.class.getName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "project";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    List<User> allUserList;

    ProjectInProjectDetail mProject;

    private ISendProjFromAddMem mISendProjFromAddMem;

    public interface ISendProjFromAddMem {
        void sendProject(ProjectInProjectDetail project);
    }

    public AddMemBer() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMemBer.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMemBer newInstance(ProjectInProjectDetail param1) {
        AddMemBer fragment = new AddMemBer();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        mISendProjFromAddMem = (ISendProjFromAddMem) getActivity();
        allUserList = new ArrayList<>();
        UserApi userApi = RetrofitClient.getRetrofit().create(UserApi.class);
        Call<List<User>> call = userApi.getAll();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    allUserList.addAll(response.body());
                    ProgressBar progressBar =
                            (ProgressBar) mView.findViewById(R.id.pb_searchResult_progressBar);
                    progressBar.setProgress(progressBar.getMax());
                    progressBar.setVisibility(View.GONE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getContext(), "Sẵn sàng!", Toast.LENGTH_SHORT).show();
                        }
                    }, 250);
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mSearchResult = mView.findViewById(R.id.rc_addMember_searchResult);
        inputEditText = mView.findViewById(R.id.edt_addMember_memberEmail);

        handleBackButton();
        handleParentView();
        handleFindTextChange();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProject = (ProjectInProjectDetail) getArguments().getSerializable(ARG_PARAM1);
            adapter = new SearchItemAdapter(getContext());
            adapter.setmProject(mProject);
        }

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View container = mView.findViewById(R.id.layout_addMember_mainContaniner);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.slide_up_login);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        container.setAnimation(animation);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_add_mem_ber, container, false);
        return mView;
    }

    void handleBackButton() {
        ((ImageView) mView.findViewById(R.id.img_projectAddMember_back)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        mProject = adapter.getmProject();
                        mISendProjFromAddMem.sendProject(mProject);

                        View container = mView.findViewById(R.id.layout_addMember_mainContaniner);
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

    void handleParentView() {
        ((ConstraintLayout) mView.findViewById(
                R.id.layout_addMember_mainContaniner)).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
    }

    void handleFindTextChange() {
        List<User> resultList = new ArrayList<>();
        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mSearchResult.setLayoutManager(layoutManager);

        inputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String inputString = inputEditText.getText().toString().trim();
                resultList.clear();
                for (User iUser : allUserList) {
                    if (iUser.getEmail().contains(inputString) && !inputString.isEmpty()) {
                        resultList.add(iUser);
                    }
                }
                adapter.setmDatas(resultList);
                adapter.setmProject(mProject);
                mSearchResult.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


}