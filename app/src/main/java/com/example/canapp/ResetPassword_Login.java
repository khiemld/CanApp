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

import com.example.canapp.model.user.User;
import com.example.canapp.ulti.SharedPrefManager;

public class ResetPassword_Login extends AppCompatActivity {

    EditText edt_old_pass;
    TextView tv_next;
    ImageView img_back;
    User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_login);
        AnhXa();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tv_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoiResetPasswordNext();
            }
        });
    }
    public void AnhXa(){
        edt_old_pass=findViewById(R.id.edt_old_password);
        tv_next=findViewById(R.id.tv_next);
        img_back=findViewById(R.id.img_back);

    }
    public void GoiResetPasswordNext(){
        if(SharedPrefManager.getInstance(this).getUser() != null){
            user = SharedPrefManager.getInstance(this).getUser();
            String id = user.get_id().toString();
            String oldpass = edt_old_pass.getText().toString();
            String email = user.getEmail().toString();
            Intent intent = new Intent(this,ResetPassword_Login2.class);
            intent.putExtra("id_key", id);
            intent.putExtra("old_pass",oldpass);
            intent.putExtra("email",email);
            startActivity(intent);
        }
    }
}