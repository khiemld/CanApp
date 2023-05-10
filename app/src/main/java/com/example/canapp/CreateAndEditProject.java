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
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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

    List<String> nameList;

    static long date1 = 0, date2 = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_create_and_edit_project);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        initialMapping();
        handleCalendar();
        initialAction();
        initialSettingActionLabel();

        fakeList();

        hanleNameTyping();

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

    void fakeList() {
        nameList = new ArrayList<>();
        nameList.add("Quản lý dự án phần mềm");
        nameList.add("Lập trình di động");
        nameList.add("ABC");
        nameList.add("123");
    }

    private void initialSettingActionLabel() {
        if (isCreat) {
            actionButton.setText("Tạo mới");
        } else {
            actionButton.setText("Lưu");
        }
    }

    private void initialAction() {
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String projectName = edt_projectName.getText().toString().trim();
                String projectDes = edt_projectDesc.getText().toString().trim();
                String fromDate = edt_fromDate.getText().toString().trim();
                String toDate = edt_toDate.getText().toString().trim();

                String data =
                        projectName + ", " + projectDes + ", from: " + fromDate + ", to: " + toDate;

                if (isCreat) {
                    Toast.makeText(CreateAndEditProject.this, data, Toast.LENGTH_SHORT)
                            .show();
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