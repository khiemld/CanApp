package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;

public class LoginActivity extends AppCompatActivity {

    private View loginLayout;

    private float deltaY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        // Đặt kích thước cho activity_login bằng với activity_welcome
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        loginLayout = findViewById(R.id.loginlayout);

        // Thêm TouchListener vào giao diện của LoginActivity
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
                        /*float endY = event.getY(); // Lấy tọa độ Y khi ngón tay rời khỏi màn hình
                        float betaY = event.getRawY() - startY; */// Tính khoảng cách di chuyển của ngón tay theo trục Y
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
}