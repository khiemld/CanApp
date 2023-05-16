package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.canapp.adapter.UserAdapter;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

public class RateActivity extends AppCompatActivity {

    private RecyclerView rcv_memberRate;

    private UserAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        userAdapter = new UserAdapter(getApplicationContext());

        Mapping();
        getAllMember();
    }

    private void getAllMember() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        rcv_memberRate.setLayoutManager(layoutManager);

        userAdapter.setUser(getListUser());
        rcv_memberRate.setAdapter(userAdapter);
    }

    private List<User> getListUser() {
        List<User> list = new ArrayList<>();
        list.add(new User("1", "Hoàng Vũ Trường Giang", "khiemld.0204@gmail.com", "12345678", "https://www.chuphinhsanpham.vn/wp-content/uploads/2018/09/chup-anh-cv-dep-2.jpg", "Hoàng Diệu 2", "Trưởng nhóm", "0818447234", true));
        list.add(new User("2", "Hoàng Vũ Trường Giang", "khiemld.0204@gmail.com", "12345678", "https://www.chuphinhsanpham.vn/wp-content/uploads/2018/09/chup-anh-cv-dep-2.jpg", "Hoàng Diệu 2", "Trưởng nhóm", "0818447234", true));
        list.add(new User("3", "Hoàng Vũ Trường Giang", "khiemld.0204@gmail.com", "12345678", "https://www.chuphinhsanpham.vn/wp-content/uploads/2018/09/chup-anh-cv-dep-2.jpg", "Hoàng Diệu 2", "Trưởng nhóm", "0818447234", true));
        list.add(new User("4", "Hoàng Vũ Trường Giang", "khiemld.0204@gmail.com", "12345678", "https://www.chuphinhsanpham.vn/wp-content/uploads/2018/09/chup-anh-cv-dep-2.jpg", "Hoàng Diệu 2", "Trưởng nhóm", "0818447234", true));

        return list;
    }

    private void Mapping() {
        rcv_memberRate = findViewById(R.id.rv_ratememberRate);
    }
}