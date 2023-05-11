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
import android.widget.Toast;

import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    ApiService apiService;
    TextView tv_next,tv_noti_email,tv_noti_phone,tv_noti_birthday;
    EditText edt_username,edt_email,edt_address,edt_phone,edt_major,edt_birthday;
    ImageView img_back;
    User user;
    List<User> listUser;
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
                overridePendingTransition(R.anim.slide_up_login, R.anim.no_animation);
                String email = edt_email.getText().toString();
                String username = edt_username.getText().toString();
                String address=edt_address.getText().toString();
                String phone = edt_phone.getText().toString();
                String major = edt_major.getText().toString();
                String birthday=edt_birthday.getText().toString();
                user = new User(username,email,address,major,phone,birthday);
                Bundle bundle = new Bundle();
                bundle.putSerializable("user1",user);
                intent.putExtras(bundle);
                startActivity(intent);
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
        edt_username=findViewById(R.id.edt_username_register);
        edt_email=findViewById(R.id.edt_email);
        edt_phone=findViewById(R.id.edt_phone);
        edt_major=findViewById(R.id.edt_major);
        edt_address=findViewById(R.id.edt_address);
        edt_birthday=findViewById(R.id.edt_birthday);
        tv_noti_email=findViewById(R.id.tv_noti_email);
        tv_noti_phone=findViewById(R.id.tv_noti_phone_register);
        tv_noti_birthday=findViewById(R.id.tv_noti_birthday);
        img_back = findViewById(R.id.img_back);
    }
    public void SetThongBao(){

        apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        Call<List<User>> call = apiService.getAllUser();
        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    //Toast.makeText(RegisterActivity.this, "Lấy dữ liệu thành công", Toast.LENGTH_SHORT).show();
                    listUser=response.body();
                    /*for(User user:listUser){
                        Toast.makeText(RegisterActivity.this, user.getName().toString(), Toast.LENGTH_SHORT).show();
                    }*/

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
                String regex = "^([A-Z|a-z|0-9](\\.|_){0,1})+[A-Z|a-z|0-9]\\@([A-Z|a-z|0-9])+((\\.){0,1}[A-Z|a-z|0-9]){2}\\.[a-z]{2,3}$";
                String string = charSequence.toString();
                    //System.out.println(user.getEmail());
                   if (string.length() == 0 || !string.matches(regex) ){
                        tv_noti_email.setVisibility(View.VISIBLE);
                        tv_noti_email.setText("Email không hợp lệ");

                    } else {
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
                    }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }
}