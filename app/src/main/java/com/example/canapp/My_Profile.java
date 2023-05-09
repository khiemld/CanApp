package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class My_Profile extends AppCompatActivity {

    Toolbar top_bar;
    ImageView img_menu;
    ConstraintLayout constrain_project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        AnhXa();
        setSupportActionBar(top_bar);
        getSupportActionBar().setTitle("");
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu();
            }
        });
        GetEditProfile();
    }
    public void AnhXa(){
        top_bar = findViewById(R.id.top_bar);
        img_menu=findViewById(R.id.img_menu);
        constrain_project=findViewById(R.id.constraint_project);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_navigation,menu);
        return super.onCreateOptionsMenu(menu);
    }
    public void popupMenu(){
        PopupMenu popup = new PopupMenu(this, this.img_menu);
        popup.inflate(R.menu.bottom_navigation);

        Menu menu = popup.getMenu();
        // Register Menu Item Click event.
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        // Show the PopupMenu.
        popup.show();
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuReset:
                Intent intent = new Intent(My_Profile.this,ResetPassword_Login.class);
                startActivity(intent);
                break;
            case R.id.menuResetProfile:
                intent = new Intent(My_Profile.this,EditInformation.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void GetEditProfile(){
        constrain_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(My_Profile.this,AllMyProject.class);
                startActivity(intent);
            }
        });
    }

}