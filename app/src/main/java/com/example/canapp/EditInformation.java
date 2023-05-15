package com.example.canapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;
import com.example.canapp.model.UserLogin;
import com.example.canapp.ulti.SharedPrefManager;

import java.io.File;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditInformation extends AppCompatActivity {

    ImageView img_upload,img_avatar;
    EditText edt_username,edt_email,edt_address,edt_major,edt_phone,edt_birthday;
    ConstraintLayout constraint_update;
    User user;
    UserLogin userLogin;
    String id;
    Uri uri;
    private static final int MY_REQUEST_CODE = 100;
    ApiService apiService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        AnhXa();
        if(SharedPrefManager.getInstance(this).getUser() != null){
            user = SharedPrefManager.getInstance(this).getUser();
            edt_username.setText(user.getName());
            edt_phone.setText(user.getPhone());
            edt_major.setText(user.getMajor());
            edt_email.setText(user.getEmail());
            edt_address.setText(user.getAddress());
            Glide.with(this).load(user.getAvatar()).into(img_avatar);

            //Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
            img_upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    onClickRequestPermission();
                }
            });
            constraint_update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    UploadImage();
                }
            });
        }
    }
    public void AnhXa(){
        edt_username=findViewById(R.id.edt_username_edit);
        edt_email=findViewById(R.id.edt_email_edit);
        edt_address=findViewById(R.id.edt_address_edit);
        edt_major=findViewById(R.id.edt_major_edit);
        edt_phone=findViewById(R.id.edt_phone_edit);
        edt_phone=findViewById(R.id.edt_phone_edit);
        img_avatar=findViewById(R.id.img_avatar_edit);
        img_upload=findViewById(R.id.img_upload);
        edt_birthday=findViewById(R.id.edt_birthday_edit);
        constraint_update=findViewById(R.id.constraint_update);
    }
    public void onClickRequestPermission(){
        if (Build.VERSION.SDK_INT<Build.VERSION_CODES.M){
            openGallery();
            return;
        }
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            openGallery();
        }
        else {
            String [] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permission,MY_REQUEST_CODE);
        }
    }
    public void openGallery(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLaucher.launch(Intent.createChooser(intent,"Select Picture"));
    }
    private ActivityResultLauncher<Intent> mActivityResultLaucher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>(){

                @Override
                public void onActivityResult(ActivityResult result) {
                    if(result.getResultCode()== Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data==null)
                            return;
                        uri = data.getData();
                        try{
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                            img_avatar.setImageBitmap(bitmap);
                        }
                        catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            }

    );
    public void UploadInformation(){
        String id = user.get_id();
        String username = edt_username.getText().toString();
        String email = edt_email.getText().toString();
        String address = edt_address.getText().toString();
        String major = edt_major.getText().toString();
        String phone = edt_phone.getText().toString();
        String birthday = edt_birthday.getText().toString();
        User user = new User(username,email,address,major,phone,birthday);

        apiService = RetrofitClient.getRetrofit().create(ApiService.class);

        Call<UserLogin> call = apiService.putUser(id,user);
        call.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                try {
                    if (response.isSuccessful()) {
                        userLogin = response.body();
                        if (! userLogin.isError()){
                            Toast.makeText(EditInformation.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            finish();
                            //Intent intent = new Intent(EditInformation.this, LoginActivity.class);
                            //startActivity(intent);

                        } else {
                            Toast.makeText(EditInformation.this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(EditInformation.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Log.e("tag",t.toString());
                Toast.makeText(EditInformation.this, "Thất bại", Toast.LENGTH_SHORT).show();
            }
        });

    }
    public void UploadImage(){
        if (uri != null) {
            // Do something with the Uri
            String pathFileImg = RealPathUtil.getRealPath(this,uri);
            File file = new File(pathFileImg);
            RequestBody requestBodyImg = RequestBody.create(MediaType.parse("image/*"),file);

            //RequestBody requestBodyId = RequestBody.create(MediaType.parse("multipart/form-data"),id);

            MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("avatar",file.getName(),requestBodyImg);

            String id = user.get_id();

            apiService = RetrofitClient.getRetrofit().create(ApiService.class);

            Call<UserLogin> call = apiService.upload_image(id,multipartBody);
            call.enqueue(new Callback<UserLogin>() {
                @Override
                public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                    try {
                        if (response.isSuccessful()) {
                            userLogin = response.body();
                            if (! userLogin.isError()){
                                Toast.makeText(EditInformation.this, "Cập nhật ảnh thành công", Toast.LENGTH_SHORT).show();
                                finish();
                                //Intent intent = new Intent(EditInformation.this, LoginActivity.class);
                                //startActivity(intent);

                            } else {
                                Toast.makeText(EditInformation.this, "Cập nhật ảnh thất bại", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(EditInformation.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<UserLogin> call, Throwable t) {
                    Log.e("tag_img",t.toString());
                    Toast.makeText(EditInformation.this, "Upload ảnh thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            // Uri is null, handle the error
            Toast.makeText(this, "Vui lòng chọn ảnh mới để upload ", Toast.LENGTH_SHORT).show();
        }

    }
}