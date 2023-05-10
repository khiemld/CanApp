package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {

    TextView tv_next,tv_noti_mssv,tv_noti_birthday,tv_noti_email,tv_noti_phone;
    EditText edt_password,edt_mssv,edt_email,edt_birthday,edt_address,edt_phone,edt_major;
    ImageView img_back;
    private View registerLayout;
    private float deltaY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        AnhXa();
        SetThongBao();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this,PassworkActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_up_login, R.anim.no_animation);
            }
        });
        registerLayout.setOnTouchListener(new View.OnTouchListener() {
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
                        registerLayout.setTranslationY(deltaY);

                        // Áp dụng hiệu ứng động lực (acceleration) cho chuyển động
                        registerLayout.animate()
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
        tv_next= findViewById(R.id.tv_next);
        registerLayout=findViewById(R.id.registerLayout);
        edt_password=findViewById(R.id.edt_password);
        edt_mssv=findViewById(R.id.edt_password_again);
        edt_email=findViewById(R.id.edt_email);
        edt_phone=findViewById(R.id.edt_phone);
        edt_major=findViewById(R.id.edt_major);
        edt_birthday=findViewById(R.id.edt_birthday);
        edt_address=findViewById(R.id.edt_address);
        tv_noti_mssv=findViewById(R.id.tv_noti_mssv);
        tv_noti_email=findViewById(R.id.tv_noti_email);
        tv_noti_birthday=findViewById(R.id.tv_noti_birthday);
        tv_noti_phone=findViewById(R.id.tv_noti_phone);
        img_back = findViewById(R.id.img_back);
    }
    public void SetThongBao(){
        edt_mssv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex = "^[1-9]\\d{7}$";
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti_mssv.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_mssv.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String regex = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$";
                String string = charSequence.toString();
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti_email.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_email.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_birthday.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex="\\b(0?[1-9]|[12]\\d|3[01])[\\/\\-.](0?[1-9]|[12]\\d|3[01])[\\/\\-.](\\d{2}|\\d{4})\\b";
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti_birthday.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_birthday.setVisibility(View.INVISIBLE);
                    /*SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    Date date = dateFormat.parse(string);*/
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex="^\\s*(?:\\+?(\\d{1,3}))?([-. (]*(\\d{3})[-. )]*)?((\\d{3})[-. ]*(\\d{2,4})(?:[-.x ]*(\\d+))?)\\s*$";
                if (string.length() == 0 || !string.matches(regex)){
                    tv_noti_phone.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_phone.setVisibility(View.INVISIBLE);

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}