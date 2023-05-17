package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.user.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResetPassword extends AppCompatActivity {

    TextView tv_noti_email,tv_next;
    EditText edt_email;
    ImageView img_back;
    ApiService apiService;
    List<User> listUser;
    String user_id;
    String user_email;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
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
                XacThucEmail();

            }
        });
    }
    public void AnhXa(){
        edt_email=findViewById(R.id.edt_newpass_reset);
        tv_noti_email=findViewById(R.id.tv_noti_pass_reset);
        img_back=findViewById(R.id.img_back);
        tv_next=findViewById(R.id.tv_next);
    }
    public void SetThongBao(){
        apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        Call<List<User>> call = apiService.getAllUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    listUser=response.body();

                }
                else {
                    Toast.makeText(ResetPassword.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(ResetPassword.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
        edt_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //String regex = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$";
                String string = charSequence.toString();
                //System.out.println(user.getEmail());
                if (string.length() == 0 /*|| !string.matches(regex) */){
                    tv_noti_email.setVisibility(View.VISIBLE);
                    tv_noti_email.setText("Email không được để trống");

                } else {
                    try{
                        for(User user: listUser)
                        {
                            if(user.getEmail().toString().equals(string)){
                                tv_noti_email.setVisibility(View.INVISIBLE);
                                user_email=string;
                                user_id=user.get_id();
                                break;
                            }
                            else {
                                tv_noti_email.setVisibility(View.VISIBLE);
                                tv_noti_email.setText("Email không tồn tại trong cơ sở dữ liệu");
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(ResetPassword.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void XacThucEmail(){
        String email = edt_email.getText().toString();
        if(TextUtils.isEmpty(email)){
            tv_noti_email.setVisibility(View.VISIBLE);
            tv_noti_email.setText("Email không được để trống");
        }
        else if(tv_noti_email.getVisibility()==View.INVISIBLE && !user_email.isEmpty()){
            //Toast.makeText(ResetPassword.this, "Thành công", Toast.LENGTH_SHORT).show();
            mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(ResetPassword.this, "Email reset password đã được gửi đến gmail của bạn, vui lòng truy cập và đặt lại mật khẩu", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ResetPassword.this,ResetPassword2.class);
                        intent.putExtra("id_reset", user_id);
                        startActivity(intent);
                    } else {
                        // Gửi email reset password thất bại.
                        Toast.makeText(ResetPassword.this,
                                "Gửi email reset password thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            //GuiEmail(email);
        }
        else {
            Toast.makeText(this, "Thông tin không hợp lệ, vui lòng kiểm tra lại", Toast.LENGTH_SHORT).show();
        }
    }

}