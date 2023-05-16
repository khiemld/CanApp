package com.example.canapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canapp.api.ApiService;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.User;
import com.example.canapp.ulti.SharedPrefManager;

import retrofit2.Call;

public class My_Profile extends Fragment {

    ImageView top_bar;
    ImageView img_menu;
    ImageView img_avatar;
    ConstraintLayout constrain_project;
    TextView tv_username, tv_email,tv_address,tv_major,tv_phone,tv_birthday;
    User user;

    View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_my_profile, container, false);

        AnhXa();
        if(SharedPrefManager.getInstance(getContext()).getUser() != null){
            user = SharedPrefManager.getInstance(getContext()).getUser();
            tv_username.setText(user.getName());
            tv_phone.setText(user.getPhone());
            tv_major.setText(user.getMajor());
            tv_email.setText(user.getEmail());
            tv_address.setText(user.getAddress());
            Glide.with(this).load(user.getAvatar()).into(img_avatar);
        }

        top_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTopmenu();
            }
        });
        img_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupMenu();
            }
        });
        GetEditProfile();
        return view;
    }

    private void showTopmenu() {
        PopupMenu topmenu = new PopupMenu(getContext(), this.top_bar);
        topmenu.inflate(R.menu.top_navigation);

        topmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return onOptionsItemSelected(item);
            }
        });

        topmenu.show();
    }
    public void AnhXa(){
        top_bar = view.findViewById(R.id.img_topmenu);
        img_menu = view.findViewById(R.id.img_menu);
        constrain_project = view.findViewById(R.id.constraint_project);
        tv_address = view.findViewById(R.id.tv_address);
        tv_birthday = view.findViewById(R.id.tv_birthday);
        tv_email = view.findViewById(R.id.tv_email);
        tv_major = view.findViewById(R.id.tv_major);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_username = view.findViewById(R.id.tv_username);
        img_avatar=view.findViewById(R.id.img_avatar_edit);
    }

    public void popupMenu(){
        PopupMenu popup = new PopupMenu(getContext(), this.img_menu);
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
                Intent intent = new Intent(requireContext(),ResetPassword_Login.class);
                startActivity(intent);
                break;
            case R.id.menuLogout:
                SharedPrefManager.getInstance(requireContext()).logout();
                break;
            case R.id.menuResetProfile:
                intent = new Intent(requireContext(),EditInformation.class);
                startActivity(intent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
    public void GetEditProfile(){
        constrain_project.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(),AllMyProject.class);
                startActivity(intent);
            }
        });
    }


}