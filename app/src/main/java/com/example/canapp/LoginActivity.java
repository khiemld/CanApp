package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;
import com.example.canapp.model.UserLogin;
import com.example.canapp.ulti.SharedPrefManager;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private View loginLayout;

    private float deltaY;

    ApiService apiService;

    private EditText edt_email, edt_password;

    private CheckBox cb_remember;

    User user = new User();

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        // Đặt kích thước cho activity_login bằng với activity_welcome
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);


        Mapping();

        // Thêm TouchListener vào giao diện của LoginActivity


        findViewById(R.id.btn_login2).setOnClickListener(v -> Login());

        findViewById(R.id.tv_dangkyLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_login, R.anim.no_animation);
                finish();
            }
        });

        loginLayout.setOnTouchListener(new View.OnTouchListener() {
            private float startY; // Tọa độ Y ban đầu của ngón tay
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY(); // Lấy tọa độ Y ban đầu của ngón tay
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        deltaY = event.getRawY() - startY;
                        // Áp dụng hiệu ứng chuyển động dựa trên khoảng cách chuyển động
                        loginLayout.setTranslationY(deltaY);

                        // Áp dụng hiệu ứng động lực (acceleration) cho chuyển động
                        loginLayout.animate()
                                .setInterpolator(new AccelerateInterpolator())
                                .setDuration(2000)
                                .translationY(0f)
                                .start();
                    case MotionEvent.ACTION_UP:
                        float endY = event.getY(); // Lấy tọa độ Y khi ngón tay rời khỏi màn hình
                        float betaY = event.getRawY() - startY; // Tính khoảng cách di chuyển của ngón tay theo trục Y
                        // Kiểm tra nếu khoảng cách di chuyển lớn hơn một ngưỡng cho phép (ví dụ: 100px)
                        if (Math.abs(deltaY) > 500) {
                            finish();
                        }
                        return true;
                    default:
                        return false;
                }

            }
        });
    }

    private void Login() {
        String email = edt_email.getText().toString();
        String password = edt_password.getText().toString();

        //Kiem tra cac truong email vaf password da duoc nhap chua
        if (TextUtils.isEmpty(email)){
            edt_email.setError("Email không được để trống");
            edt_email.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)){
            edt_password.setError("Password không được để trống");
            edt_password.requestFocus();
            return;
        }

        //Khoi tao apiService
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        //Thuc hien API login
        Call<UserLogin> call = apiService.login(email, password);

        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                UserLogin userLogin = response.body();
                if (response.isSuccessful() && !userLogin.isError()){
                    user = response.body().getUser();
                    if (cb_remember.isChecked()){
                        SharedPrefManager.getInstance(getApplicationContext()).userLogin(user);
                    }
                    finish();
                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    startActivity(intent);
                } else {
                    try {
                        Toast.makeText(getApplicationContext(), "Email hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();

                    } catch (Exception e){

                    }
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.d("Error:", t.getMessage());
            }
        });

    }

    private void Mapping() {
        loginLayout = findViewById(R.id.loginlayout);
        edt_email = findViewById(R.id.edt_emaillogin);
        edt_password = findViewById(R.id.edt_passwordlogin);
        cb_remember = findViewById(R.id.cb_rememberlogin);
    }
}