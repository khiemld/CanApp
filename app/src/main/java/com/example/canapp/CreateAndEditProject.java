package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.api.PlanApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.project.ProjectFull;
import com.example.canapp.model.project.ProjectResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateAndEditProject extends AppCompatActivity {
    EditText edt_fromDate, edt_toDate;

    /*Mô tả action tương ứng với nơi gọi nó (Edit | Create)*/
    TextView actionButton;

    TextView projectNameError, dateError;

    EditText edt_projectName, edt_projectDesc;

    /*Cờ kiểm tra xem có phải đang thực hiện thao tác thêm project hay không*/
    static boolean isCreat = true;

    DatePickerDialog.OnDateSetListener setListenerFrom;
    DatePickerDialog.OnDateSetListener setListenerTo;

    List<String> nameList = new ArrayList<>();

    static long date1 = 0, date2 = 0;

    PlanApi planApi;

    final String leaderID = "64511d75da6a2ab371790258";
    final static String TAG = "CreateAndEditProject";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_and_edit_project);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        //Khoi tao apiService
        planApi = RetrofitClient.getRetrofit().create(PlanApi.class);

        // Lấy tất cả các plan hiện có
        getAllPlan();

        initialMapping();

        // Xử lí cái ô chọn lịch
        handleCalendar();

        // Xử lí sự kiện ấn dô cái nút Thêm | Sửa
        handleAction();

        // Kiểm tra hành động là thêm hay sửa đẻ đánh label
        initialSettingActionLabel();

        // Xử lí sự kiện nhập chữ vào 2 ô edit text
        hanleNameTyping();

    }

    private void getAllPlan() {
        Call<List<ProjectFull>> call = planApi.getAll();
        call.enqueue(new Callback<List<ProjectFull>>() {
            @Override
            public void onResponse(Call<List<ProjectFull>> call,
                                   Response<List<ProjectFull>> response) {
                if (response.isSuccessful()) {
                    List<ProjectFull> projectFullList = response.body();
                    if (projectFullList.size() > 0) {
                        // Xử lý lấy dữ liệu thành công
                        Log.e(TAG, String.valueOf(projectFullList.size()));
                        for (ProjectFull item : projectFullList) {
                            nameList.add(item.getName());
                        }
                    } else {
                        // Xử lý dữ liệu trả về rỗng
                        Log.e(TAG, "Rỗng");
                    }
                } else {
                    // Xử lý khi status trả về không phải thành công (đầu khác 2)
                    try {
                        Log.e(TAG, response.errorBody().string());
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<ProjectFull>> call, Throwable t) {
                // Xử lý call API không thành công
                Log.e(TAG, t.getMessage());
            }
        });
    }

    void hanleNameTyping() {
        edt_projectName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String regex =
                        "^[a-zA-Z0-9\\p{L}\\s-]+$";
                String string = charSequence.toString().trim();
                if (string.length() == 0 || !string.matches(regex)) {
                    projectNameError.setText("Tên không phù hợp!");
                    projectNameError.setVisibility(View.VISIBLE);
                } else {
                    projectNameError.setVisibility(View.INVISIBLE);
                    for (String item : nameList) {
                        if (string.equals(item)) {
                            projectNameError.setText("Tên này đã tồn tại!");
                            projectNameError.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initialSettingActionLabel() {
        if (isCreat) {
            actionButton.setText("Tạo mới");
        } else {
            actionButton.setText("Lưu");
        }
    }

    private void handleAction() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Lấy dữ liệu từ giao diện
                String projectName = edt_projectName.getText().toString().trim();
                String projectDes = edt_projectDesc.getText().toString().trim();
                String fromDate = edt_fromDate.getText().toString().trim();
                String toDate = edt_toDate.getText().toString().trim();

                String data =
                        projectName + ", " + projectDes + ", from: " + fromDate + ", to: " + toDate;

                if (isCreat) {
                    // Xử lí tạo plan
                    if (
                        // Kiểm tra dữ liệu hợp lệ để tạo chưa
                            dateError.getVisibility() == View.INVISIBLE
                                    && projectNameError.getVisibility() == View.INVISIBLE
                                    && projectName.isEmpty() == false
                                    && fromDate.isEmpty() == false
                                    && toDate.isEmpty() == false
                    ) {
                        // Dữ liệu chuẩn -> Bắt đầu thêm
                        Call<ProjectResponse> call =
                                planApi.createProject(leaderID, projectName, projectDes, fromDate,
                                        toDate);
                        call.enqueue(new Callback<ProjectResponse>() {
                            @Override
                            public void onResponse(Call<ProjectResponse> call,
                                                   Response<ProjectResponse> response) {
                                if (response.isSuccessful()) {


                                    // Xử lí thêm thành công 201
                                    ProjectResponse projectResponse = response.body();
                                    ProjectFull projectFull = projectResponse.getPlan();

                                    // Thông báo khi thêm thành công
                                    Toast.makeText(CreateAndEditProject.this,
                                                    "Thêm thành công " + projectFull.getName(),
                                                    Toast.LENGTH_SHORT)
                                            .show();

                                } else {
                                    // Xử lí khi nhận 409
                                    Log.e("ADD PLAN", fromDate);
                                    try {
                                        Toast.makeText(CreateAndEditProject.this,
                                                        response.errorBody().string(),
                                                        Toast.LENGTH_SHORT)
                                                .show();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ProjectResponse> call, Throwable t) {
                                Toast.makeText(CreateAndEditProject.this, "Thêm không thành công!",
                                                Toast.LENGTH_SHORT)
                                        .show();
                            }
                        });
                    } else {
                        Toast.makeText(CreateAndEditProject.this, "Coi lại thông tin kìa!",
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                } else {
                    // Xử lí update plan
                }
            }
        });
    }

    @SuppressLint("ClickableViewAccessibility")
    private void handleCalendar() {
        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        edt_fromDate.setInputType(InputType.TYPE_NULL);
        edt_toDate.setInputType(InputType.TYPE_NULL);

        edt_fromDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAndEditProject.this,
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
                    dateError.setVisibility(View.INVISIBLE);
                } else if (date1 > date2) {
                    dateError.setVisibility(View.VISIBLE);
                }

                edt_fromDate.setText(date);
            }
        };

        edt_toDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateAndEditProject.this,
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
                    dateError.setVisibility(View.INVISIBLE);
                } else if (date1 > date2) {
                    dateError.setVisibility(View.VISIBLE);
                }

                edt_toDate.setText(date);
            }
        };
    }

    void initialMapping() {
        edt_fromDate = (EditText) findViewById(R.id.edt_fromDate);
        edt_toDate = (EditText) findViewById(R.id.edt_toDate);
        actionButton = findViewById(R.id.tv_actionProject);
        edt_projectName = findViewById(R.id.edt_projectName);
        edt_projectDesc = findViewById(R.id.edt_projectDescription);
        projectNameError = findViewById(R.id.tv_projectNameError);
        dateError = findViewById(R.id.tv_dateError);
    }
}