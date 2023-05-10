package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.canapp.ulti.SharedPrefManager;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        if (SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            Log.d("message", "Success");
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                // Thiết lập animation cho LoginActivity
                overridePendingTransition(R.anim.slide_up_login, R.anim.no_animation);
            }
        });
        findViewById(R.id.btn_signin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(WelcomeActivity.this, RegisterActivity.class);
                startActivity(intent);
                // Thiết lập animation cho LoginActivity
                overridePendingTransition(R.anim.slide_up_login, R.anim.no_animation);
            }
        });
    }

}