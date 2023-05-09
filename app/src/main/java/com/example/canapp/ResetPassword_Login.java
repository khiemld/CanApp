package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class ResetPassword_Login extends AppCompatActivity {

    EditText edt_email;
    TextView tv_noti_email,tv_next;
    ImageView img_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_login);
        AnhXa();
        GoiResetPasswordNext();
        SetThongBao();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void AnhXa(){
        edt_email=findViewById(R.id.edt_email);
        tv_next=findViewById(R.id.tv_next);
        img_back=findViewById(R.id.img_back);
        tv_noti_email=findViewById(R.id.tv_noti_email);
    }
    public void GoiResetPasswordNext(){
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPassword_Login.this,ResetPassword_Login2.class);
                startActivity(intent);
            }
        });
    }
    public void SetThongBao(){
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
    }
}