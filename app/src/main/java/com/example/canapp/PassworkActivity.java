package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class PassworkActivity extends AppCompatActivity {

    private View passwordLayout;
    private float deltaY;
    EditText edt_pass,edt_pass_again;
    TextView tv_noti_pass,tv_noti_pass_again,tv_login;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwork);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        String text = "<font color=#001E6C>Bạn đã có tài khoản CAN?</font> <font color=#FFAA4C>Đăng nhập ngay</font>";
        AnhXa();
        SetThongBao();
        tv_login.setText(Html.fromHtml(text));
        tv_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PassworkActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        passwordLayout.setOnTouchListener(new View.OnTouchListener() {
            private float startY;
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY(); // Lấy tọa độ Y ban đầu của ngón tay
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        deltaY = event.getRawY() - startY;
                        // Áp dụng hiệu ứng chuyển động dựa trên khoảng cách chuyển động
                        passwordLayout.setTranslationY(deltaY);

                        // Áp dụng hiệu ứng động lực (acceleration) cho chuyển động
                        passwordLayout.animate()
                                .setInterpolator(new AccelerateInterpolator())
                                .setDuration(2000)
                                .translationY(0f)
                                .start();
                    case MotionEvent.ACTION_UP:
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
    public void AnhXa(){

        passwordLayout = findViewById(R.id.passwordLayout);
        edt_pass=findViewById(R.id.edt_password);
        edt_pass_again=findViewById(R.id.edt_password_again);
        tv_noti_pass=findViewById(R.id.tv_noti_pass);
        tv_noti_pass_again=findViewById(R.id.tv_noti_pass_again);
        img_back=findViewById(R.id.img_back);
        tv_login = findViewById(R.id.tv_login);
    }
    public void SetThongBao(){
        edt_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex = "^(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?])(?=.*[a-zA-Z]).{8,13}$";
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti_pass.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_pass.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_pass_again.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                if (string.length() == 0 || !string.equals(edt_pass)){
                    tv_noti_pass_again.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_pass_again.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}