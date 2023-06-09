package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.user.UserLogin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.auth.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword2 extends AppCompatActivity {

    EditText edt_new_pass;
    TextView tv_noti_pass;
    ImageView img_back,img_hide;
    Button btn_reset;
    ApiService apiService;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password2);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        AnhXa();
        SetThongBao();
        img_hide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_new_pass.getTransformationMethod() == null){
                    edt_new_pass.setTransformationMethod(new PasswordTransformationMethod());
                    img_hide.setImageResource(R.drawable.eye_off_outline);

                }else{
                    edt_new_pass.setTransformationMethod(null);
                    img_hide.setImageResource(R.drawable.eye_outline);

                }
            }
        });
        //SetThongBao();
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reset_Pass();
            }
        });
    }
    public void AnhXa(){
        img_back=findViewById(R.id.img_back);
        btn_reset=findViewById(R.id.btn_forget_reset);
        edt_new_pass=findViewById(R.id.edt_newpass_reset);
        tv_noti_pass=findViewById(R.id.tv_noti_pass_reset);
        img_hide=findViewById(R.id.image_hide_reset);
    }
    public void SetThongBao(){
        edt_new_pass.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                String regex = "^.{8,13}$";
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
    }
    public void Reset_Pass() {
        String idValue = getIntent().getStringExtra("id_reset");
        String email = getIntent().getStringExtra("email_reset");
        String newPass = edt_new_pass.getText().toString();

       if(!TextUtils.isEmpty(idValue) && !TextUtils.isEmpty(newPass) && tv_noti_pass.getVisibility()==View.INVISIBLE){
            //Toast.makeText(this, idValue, Toast.LENGTH_SHORT).show();
          apiService = RetrofitClient.getRetrofit().create(ApiService.class);
            Call<UserLogin> call = apiService.forgotPass(idValue, newPass);
            call.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    try{
                        if(response.isSuccessful()){
                            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(ResetPassword2.this, "Email reset password đã được gửi đến gmail của bạn, vui lòng truy cập và đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ResetPassword2.this,LoginActivity.class);
                                        startActivity(intent);
                                    } else {
                                        // Gửi email reset password thất bại.
                                        Toast.makeText(ResetPassword2.this,
                                                "Gửi email reset password thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                        else {
                            Toast.makeText(ResetPassword2.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                        }

                    }catch (Exception e){
                        Toast.makeText(ResetPassword2.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {
                    Toast.makeText(ResetPassword2.this, "Thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Vui lòng nhập mật khẩu mới", Toast.LENGTH_SHORT).show();
        }

    }
}