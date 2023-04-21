package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

public class PassworkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passwork);
        String text = "<font color=#001E6C>Bạn đã có tài khoản CAN?</font> <font color=#FFAA4C>Đăng nhập ngay</font>";
        TextView tv_login = (TextView)findViewById(R.id.tv_login);
        tv_login.setText(Html.fromHtml(text));
    }
}