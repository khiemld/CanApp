package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.user.User;
import com.example.canapp.model.user.UserLogin;
import com.example.canapp.ulti.SharedPrefManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword_Login extends AppCompatActivity {

    EditText edt_old_pass,edt_new_pass;

    ImageView img_back;
    User user;

    Button btn_reset;
    ApiService apiService;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
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
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GoiResetPasswordNext();
            }
        });
    }
    public void AnhXa(){
        edt_old_pass=findViewById(R.id.edt_old_password);
        img_back=findViewById(R.id.img_back);
        btn_reset=findViewById(R.id.btn_reset_login);
        edt_new_pass=findViewById(R.id.edt_new_password);
    }
    public void GoiResetPasswordNext(){
        if(SharedPrefManager.getInstance(this).getUser() != null){
            user = SharedPrefManager.getInstance(this).getUser();
            String id = user.get_id().toString();
            String oldpass = edt_old_pass.getText().toString();
            String email = user.getEmail().toString();
            String newPass=edt_new_pass.getText().toString();
            //Toast.makeText(this, oldPass + newPass, Toast.LENGTH_SHORT).show();
            apiService = RetrofitClient.getRetrofit().create(ApiService.class);
            Call<UserLogin> call = apiService.resetPass( id,oldpass,newPass);
            call.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    try{
                        if(response.isSuccessful()){
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(ResetPassword_Login.this, "Mật khẩu cập nhật thành công, vui lòng truy cập mail để xác thực lại", Toast.LENGTH_SHORT).show();
                                        SharedPrefManager.getInstance(ResetPassword_Login.this).logout();
                                    }
                                    else{
                                        Toast.makeText(ResetPassword_Login.this, "Thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(ResetPassword_Login.this, "Thông tin sai", Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception e){
                        Toast.makeText(ResetPassword_Login.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {
                    Toast.makeText(ResetPassword_Login.this, "Lỗi api", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}