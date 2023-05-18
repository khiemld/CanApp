package com.example.canapp;

import android.animation.LayoutTransition;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.adapter.AvtarAdapter;
import com.example.canapp.api.PlanApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.api.TaskApi;
import com.example.canapp.api.UserApi;
import com.example.canapp.model.project.ProjectDetailResponse;
import com.example.canapp.model.project.ProjectInProjectDetail;
import com.example.canapp.model.task.AddTaskResponse;
import com.example.canapp.model.task.Task;
import com.example.canapp.model.type.Type;
import com.example.canapp.model.user.User;
import com.example.canapp.ulti.SharedPrefManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailTaskFragment extends Fragment {

    public static final String TAG = DetailTaskFragment.class.getName();

    DatePickerDialog.OnDateSetListener setListenerFrom;
    DatePickerDialog.OnDateSetListener setListenerTo;

    public interface IOnTaskUpdateListener {
        void onTaskUpdate(Task task, ProjectInProjectDetail project);
    }

    IOnTaskUpdateListener iOnTaskUpdateListener;

    static long date1 = 0, date2 = 0;

    ImageView backButton;
    TextView taskName, columnName, taskDesc, editMember, saveButton;
    EditText toDate, fromDate;
    RecyclerView memberAvatarList;
    AvtarAdapter adapter;

    private boolean isChanged = false;
    static boolean isDateError = false;

    List<User> userList;
    List<User> allUser;

    View mView;

    private static final String ARG_PARAM1 = "task";
    private static final String ARG_PARAM2 = "userID";

    private static final String ARG_PARAM3 = "project";

    // TODO: Rename and change types of parameters
    private Task mTask;
    private String userID;

    private ProjectInProjectDetail mProject;

    public DetailTaskFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static DetailTaskFragment newInstance(Task task,
                                                 ProjectInProjectDetail project) {
        DetailTaskFragment fragment = new DetailTaskFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, task);
        args.putSerializable(ARG_PARAM3, project);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iOnTaskUpdateListener = (IOnTaskUpdateListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = (Task) getArguments().getSerializable(ARG_PARAM1);
            userList = mTask.getMembers();
            mProject = (ProjectInProjectDetail) getArguments().getSerializable(ARG_PARAM3);
        }
        getUserID();
    }

    String getUserID() {
        if (SharedPrefManager.getInstance(getContext()).getUser() != null) {
            User user = SharedPrefManager.getInstance(getContext()).getUser();
            return user.get_id();
        }
        return "";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_detail_task, container, false);
        return mView;
    }

    @Override
    public void onStart() {
        super.onStart();

        View container = mView.findViewById(R.id.layout_taskDetail_mainContainer);
        Animation animation =
                AnimationUtils.loadAnimation(getContext(), R.anim.zoom_out);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        container.startAnimation(animation);


        adapter = new AvtarAdapter(userList, getContext());
        adapter.setAllUser(getAllUser());
        mapping();
        setupView();
        handleTaskNameChange();
        handleCalendar();
        handleSave();
    }

    private void handleSave() {
        if (!getUserID().equals(mProject.getManager().get(0).get_id())) {
            return;
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChanged == true) {
                    TaskApi taskApi = RetrofitClient.getRetrofit().create(TaskApi.class);
                    Call<AddTaskResponse> call =
                            taskApi.updateTask(
                                    getUserID(),
                                    mProject.get_id(),
                                    mTask.get_id(),
                                    taskDesc.getText().toString().trim(),
                                    fromDate.getText().toString(),
                                    toDate.getText().toString()
                            );

                    call.enqueue(new Callback<AddTaskResponse>() {
                        @Override
                        public void onResponse(Call<AddTaskResponse> call,
                                               Response<AddTaskResponse> response) {
                            if (response.isSuccessful()) {

                                mTask = response.body().getTask();
                                reloadProject();

                                Toast.makeText(getContext(), "Cập nhật thành công 🎈",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                Toast.makeText(getContext(), "Cập nhật thất bại 🥲",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddTaskResponse> call, Throwable t) {
                            Log.e(TAG, "onFailure: " + t.getMessage());
                        }
                    });
                }
            }
        });

    }

    void mapping() {
        backButton = (ImageView) mView.findViewById(R.id.imv_taskDetail_back);
        taskName = (TextView) mView.findViewById(R.id.tv_taskDetail_taskName);
        taskDesc = (EditText) mView.findViewById(R.id.edt_taskDetail_descText);
        columnName = (TextView) mView.findViewById(R.id.tv_taskDetail_columnName);
        toDate = (EditText) mView.findViewById(R.id.edt_taskDetail_toDate);
        fromDate = (EditText) mView.findViewById(R.id.edt_taskDetail_fromDate);
        memberAvatarList = (RecyclerView) mView.findViewById(R.id.rcv_taskDetail_members);
        editMember = (TextView) mView.findViewById(R.id.tv_taskDetail_memberEdit);
        saveButton = (TextView) mView.findViewById(R.id.tv_taskDetail_saveChange);
    }

    void setupView() {
        if (mTask == null) {
            return;
        }
        taskName.setText(mTask.getName());
        for (Type column : mProject.getColumns()) {
            if (column.get_id().equals(mTask.getColumn())) {
                columnName.setText(column.getName());
                break;
            }
        }
        if (mTask.getDescription() != null) {
            taskDesc.setText(mTask.getDescription());
        } else {
            taskDesc.setHint("Thêm mô tả cho task này nhé");
            taskDesc.setText("");
        }

        toDate.setText("date");
        fromDate.setText("date");

        if (mTask.getBeginTime() != null) {
            fromDate.setText(mTask.getBeginTime());
        }

        if (mTask.getEndTime() != null) {
            toDate.setText(mTask.getEndTime());
        }


        LinearLayoutManager layoutManager =
                new LinearLayoutManager(getActivity().getApplicationContext(),
                        RecyclerView.HORIZONTAL, false);
        memberAvatarList.setLayoutManager(layoutManager);
        memberAvatarList.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        if (getUserID().equals(mProject.getManager().get(0).get_id())) {
            editMember.setVisibility(View.VISIBLE);
            taskDesc.setEnabled(true);
            toDate.setEnabled(true);
            fromDate.setEnabled(true);


        } else {
            editMember.setVisibility(View.VISIBLE);
            editMember.setText("Chi tiết");
            taskDesc.setEnabled(false);
            toDate.setEnabled(false);
            fromDate.setEnabled(false);
        }

        editMember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction =
                        getActivity().getSupportFragmentManager()
                                .beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container2,
                        AddMemberToTaskFragment.newInstance(mProject, mTask));
                fragmentTransaction.addToBackStack(AddMemberToTaskFragment.TAG);
                fragmentTransaction.commit();
            }
        });

        saveButton.setVisibility(View.GONE);
        handleBackButton();
    }

    void handleBackButton() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View container = mView.findViewById(R.id.layout_taskDetail_mainContainer);
                Animation animation =
                        AnimationUtils.loadAnimation(getContext(), R.anim.zoom_in);
                animation.setInterpolator(new DecelerateInterpolator());
                animation.setDuration(300);
                container.startAnimation(animation);

                iOnTaskUpdateListener.onTaskUpdate(mTask, mProject);

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            getParentFragmentManager().popBackStack();
                            getChildFragmentManager().popBackStack();
                        } catch (Throwable throwable) {
                            Log.e("Detail Task Fragment", "back Button: " + throwable.getMessage());
                        }
                    }
                }, 300);
            }
        });

    }

    public void addMemberToTask(User user) {
        adapter.getmUserList().add(user);
        adapter.notifyDataSetChanged();
    }

    List<User> getAllUser() {
        List<User> allUser = new ArrayList<>();
        allUser.addAll(mProject.getMembers());
        allUser.add(mProject.getManager().get(0));

        return allUser;
    }

    void handleTaskNameChange() {

        if (!getUserID().equals(mProject.getManager().get(0).get_id())) {
            return;
        }

        taskDesc.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                isChanged = true;
                saveButton.setVisibility(View.VISIBLE);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void handleCalendar() {

        if (!getUserID().equals(mProject.getManager().get(0).get_id())) {
            return;
        }

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        fromDate.setInputType(InputType.TYPE_NULL);
        toDate.setInputType(InputType.TYPE_NULL);

        fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerFrom, year,
                        month,
                        day);
                datePickerDialog.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerFrom = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {
                month = month + 1;
                String date = dayofMonth + "/" + month + "/" + year;

                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(year, month, dayofMonth);
                date1 = mCalendar.getTime().getTime();

                if (date1 == 0 || date2 == 0 || date1 <= date2) {
                    isDateError = false;
                } else if (date1 > date2) {
                    isDateError = true;
                }
                if (isDateError == false) {
                    fromDate.setText(date);
                    isChanged = true;
                    saveButton.setVisibility(View.VISIBLE);
                }
            }
        };

        toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth, setListenerTo, year,
                        month,
                        day);
                datePickerDialog.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                datePickerDialog.show();
            }
        });

        setListenerTo = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayofMonth) {

                month = month + 1;
                String date = dayofMonth + "/" + month + "/" + year;

                Calendar mCalendar = Calendar.getInstance();
                mCalendar.set(year, month, dayofMonth);

                date2 = mCalendar.getTime().getTime();

                if (date1 == 0 || date2 == 0 || date1 <= date2) {
                    isDateError = false;
                } else if (date1 > date2) {
                    isDateError = true;
                }

                if (isDateError == false) {
                    toDate.setText(date);
                    isChanged = true;
                    saveButton.setVisibility(View.VISIBLE);
                }
            }
        };
    }


    void reloadProject() {
        PlanApi planApi = RetrofitClient.getRetrofit().create(PlanApi.class);

        Call<ProjectDetailResponse> call = planApi.getPlanDetail(mProject.get_id());
        call.enqueue(new Callback<ProjectDetailResponse>() {
            @Override
            public void onResponse(Call<ProjectDetailResponse> call,
                                   Response<ProjectDetailResponse> response) {
                if (response.isSuccessful()) {
                    mProject = response.body().getPlan();
                } else {
                    try {
                        Log.e(TAG, "onResponse: " + response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<ProjectDetailResponse> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void setmProject(ProjectInProjectDetail project) {
        mProject = project;
    }

    public void setmTask(Task task) {
        this.mTask = task;
    }
}