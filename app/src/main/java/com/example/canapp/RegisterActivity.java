package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.Html;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;
import com.example.canapp.model.UserRegister;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ImageView img_back;
    ApiService apiService;
    TextView tv_noti_login,tv_noti_name,tv_noti_pass,tv_noti_address,tv_noti_major,tv_noti_email,tv_noti_phone,tv_noti_birthday;
    EditText edt_username,edt_pass,edt_email,edt_address,edt_phone,edt_major,edt_birthday;
    User user;
    List<User> listUser;
    private View registerLayout;
    private float deltaY;
    Button btnRegister;
    UserRegister userRegister;
    protected FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);
        overridePendingTransition(R.anim.slide_up_login, R.anim.no_animation);
        String text = "<font color=#001E6C>Bạn đã có tài khoản CAN?</font> <font color=#FFAA4C>Đăng nhập ngay</font>";
        AnhXa();
        tv_noti_login.setText(Html.fromHtml(text));
        SetThongBao();
        tv_noti_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               DangKy();
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
        btnRegister=findViewById(R.id.btn_register);
        registerLayout=findViewById(R.id.registerLayout);
        edt_username=findViewById(R.id.edt_username_register);
        edt_email=findViewById(R.id.edt_old_password);
        edt_phone=findViewById(R.id.edt_phone);
        edt_major=findViewById(R.id.edt_major);
        edt_address=findViewById(R.id.edt_address);
        edt_pass=findViewById(R.id.edt_password_register);
        edt_birthday=findViewById(R.id.edt_birthday);
        tv_noti_email=findViewById(R.id.tv_noti_email);
        tv_noti_phone=findViewById(R.id.tv_noti_phone_register);
        tv_noti_birthday=findViewById(R.id.tv_noti_birthday);
        tv_noti_pass=findViewById(R.id.tv_noti_pass);
        tv_noti_major=findViewById(R.id.tv_noti_major);
        tv_noti_name=findViewById(R.id.tv_noti_name);
        tv_noti_address=findViewById(R.id.tv_noti_address);
        tv_noti_login=findViewById(R.id.tv_noti_login);
        img_back = findViewById(R.id.img_back);
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
                    Toast.makeText(RegisterActivity.this, "Lấy dữ liệu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

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
                        tv_noti_email.setText("Email không hợp lệ");

                    } else {
                      try {
                          for(User user: listUser)
                          {
                              if(user.getEmail().toString().equals(string)){
                                  tv_noti_email.setVisibility(View.VISIBLE);
                                  tv_noti_email.setText("Email đã tồn tại");
                                  break;
                              }
                              else {
                                  tv_noti_email.setVisibility(View.INVISIBLE);
                              }
                          }
                      }catch (Exception e){
                          Toast.makeText(RegisterActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                      }


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
                        tv_noti_phone.setText("Số điện thoại không hợp lệ");
                    } else {
                       try {
                           for(User user: listUser){
                               if(user.getPhone().toString().equals(string)){
                                   tv_noti_phone.setVisibility(View.VISIBLE);
                                   tv_noti_phone.setText("Số điện thoại đã tồn tại");
                                   break;
                               }
                               else {
                                   tv_noti_phone.setVisibility(View.INVISIBLE);
                               }
                           }
                       }catch (Exception e){
                           Toast.makeText(RegisterActivity.this, "Lỗi mạng", Toast.LENGTH_SHORT).show();
                       }
                    }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_pass.addTextChangedListener(new TextWatcher() {
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
        edt_username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                if (string.length() == 0 ){
                    tv_noti_name.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_name.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_major.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                if (string.length() == 0 ){
                    tv_noti_major.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_major.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String string = charSequence.toString();
                if (string.length() == 0 ){
                    tv_noti_address.setVisibility(View.VISIBLE);
                }
                else {
                    tv_noti_address.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }
    public void DangKy(){
        String email = edt_email.getText().toString();
        String username = edt_username.getText().toString();
        String address=edt_address.getText().toString();
        String phone = edt_phone.getText().toString();
        String major = edt_major.getText().toString();
        String birthday=edt_birthday.getText().toString();
        String pass = edt_pass.getText().toString();

        if(TextUtils.isEmpty(username))
        {
            tv_noti_name.setVisibility(View.VISIBLE);
        } else if (TextUtils.isEmpty(email)) {
            tv_noti_email.setVisibility(View.VISIBLE);
            tv_noti_email.setText("Email không được để trống");
        } else if (TextUtils.isEmpty(pass)) {
            tv_noti_pass.setVisibility(View.VISIBLE);
        } else if (TextUtils.isEmpty(birthday)) {
            tv_noti_birthday.setVisibility(View.VISIBLE);
            tv_noti_birthday.setText("Ngày tháng năm sinh không được để trống");
        } else if (TextUtils.isEmpty(address)) {
            tv_noti_address.setVisibility(View.VISIBLE);
        } else if (TextUtils.isEmpty(phone)) {
            tv_noti_phone.setVisibility(View.VISIBLE);
            tv_noti_phone.setText("Số điện thoại không được để trống");
        } else if (TextUtils.isEmpty(major)) {
            tv_noti_major.setVisibility(View.VISIBLE);
            tv_noti_major.setText("Chuyên ngành không được để trống");
        } else if (tv_noti_name.getVisibility()==View.INVISIBLE && tv_noti_email.getVisibility() == View.INVISIBLE
                && tv_noti_pass.getVisibility()==View.INVISIBLE && tv_noti_birthday.getVisibility()==View.INVISIBLE
                && tv_noti_address.getVisibility()==View.INVISIBLE && tv_noti_phone.getVisibility()==View.INVISIBLE
                && tv_noti_major.getVisibility()==View.INVISIBLE
        ) {
            mAuth.createUserWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            apiService = RetrofitClient.getRetrofit().create(ApiService.class);
                                            //Thuc hien API register
                                            Call<UserRegister> call = apiService.register(username,email,
                                                    pass,address, major, phone, birthday);
                                            call.enqueue(new Callback<UserRegister>() {
                                                @Override
                                                public void onResponse(Call<UserRegister> call, Response<UserRegister> response) {
                                                    try {
                                                        if (response.isSuccessful()) {
                                                            userRegister = response.body();
                                                            if (! userRegister.isError()){
                                                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                                                finish();
                                                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                                                startActivity(intent);

                                                            } else {
                                                                Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                                            }

                                                        } else {
                                                            Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                        }
                                                    } catch (Exception e) {
                                                        e.printStackTrace();
                                                    }
                                                }

                                                @Override
                                                public void onFailure(Call<UserRegister> call, Throwable t) {

                                                }
                                            });
                                        }
                                        else {
                                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });

                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        else{
            Toast.makeText(this, "Thông tin nhập vào hợp lệ, vui lòng nhập lại", Toast.LENGTH_SHORT).show();
        }

    }

}