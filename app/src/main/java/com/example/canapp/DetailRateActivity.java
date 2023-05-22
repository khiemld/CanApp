package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canapp.adapter.CommentAdapter;
import com.example.canapp.adapter.CommentRateAdapter;
import com.example.canapp.api.RateApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.rate.Comment;
import com.example.canapp.model.rate.PersonalRate;
import com.example.canapp.model.rate.Rate;
import com.example.canapp.model.user.User;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailRateActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private CommentRateAdapter commentAdapter;

    private ImageView img_avatar, img_backButton;

    private TextView txt_name, txt_mark1, txt_mark2, txt_mark3, txt_mark4, txt_mark5;

    private ScrollView scrollView;

    RatingBar rb_1, rb_2, rb_3, rb_4, rb_5;

    float rating_1, rating_2,rating_3,rating_4, rating_5;

    User user;

    String idPlan, idUser;

    int count;

    RateApi rateApi;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail_rate);

        Mapping();

        Animation animation =
                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_login);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(300);
        scrollView.startAnimation(animation);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        getWindow().setAttributes(params);

        idPlan = (String) getIntent().getExtras().get("idPlan");
        user = (User) getIntent().getExtras().get("member");
        idUser = user.get_id();

        if (!user.getAvatar().equals("null")){
            Glide.with(getApplicationContext()).load(user.getAvatar()).into(img_avatar);
        } else {
            img_avatar.setImageResource(R.drawable.avatar_profile);
        }

        txt_name.setText(user.getName());

        rateApi = RetrofitClient.getRetrofit().create(RateApi.class);

        Call<List<PersonalRate>> call = rateApi.getAllRateofPlan(idPlan);

        call.enqueue(new Callback<List<PersonalRate>>() {
            @Override
            public void onResponse(Call<List<PersonalRate>> call, Response<List<PersonalRate>> response) {
                List<PersonalRate> rates = response.body();
                List<Comment> comments = new ArrayList<>();
                if (response.isSuccessful()){
                    for (int i = 0; i < rates.size(); i++){
                        if (idUser.equals(rates.get(i).getIdMember())){
                            rating_1 = rating_1 + rates.get(i).getAttitude();
                            rating_2 = rating_2 + rates.get(i).getExpertise();
                            rating_3 = rating_3 + rates.get(i).getDiscipline();
                            rating_4 = rating_4 + rates.get(i).getCollaborate();
                            rating_5 = rating_5 + rates.get(i).getPerformance();
                            count++;
                            comments.add(new Comment(rates.get(i).getJudge().getAvatar(), rates.get(i).getJudge().getName(), rates.get(i).getComment()));
                        }
                    }
                    rating_1 = rating_1 / count;
                    rating_2 = rating_2 / count;
                    rating_3 = rating_3 / count;
                    rating_4 = rating_4 / count;
                    rating_5 = rating_5 / count;

                    txt_mark1.setText(String.valueOf(rating_1));
                    txt_mark2.setText(String.valueOf(rating_2));
                    txt_mark3.setText(String.valueOf(rating_3));
                    txt_mark4.setText(String.valueOf(rating_4));
                    txt_mark5.setText(String.valueOf(rating_5));

                    rb_1.setRating(rating_1);
                    rb_2.setRating(rating_2);
                    rb_3.setRating(rating_3);
                    rb_4.setRating(rating_4);
                    rb_5.setRating(rating_5);

                    getAllComment(comments);
                } else {
                    Toast.makeText(DetailRateActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PersonalRate>> call, Throwable t) {

            }
        });

        handleBack();
    }

    private void handleBack() {
        img_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getAllComment(List<Comment> list){
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);

        commentAdapter = new CommentRateAdapter(getApplicationContext());
        commentAdapter.setCommentRate(list);
        recyclerView.setAdapter(commentAdapter);
    }
    private void Mapping() {
        recyclerView = findViewById(R.id.rcv_commentDetailRate);
        scrollView = findViewById(R.id.scroll_DetailRate);
        img_avatar = findViewById(R.id.cr_avatarDetailRate);
        img_backButton = findViewById(R.id.img_backDetailRate);
        txt_name = findViewById(R.id.tv_namememberDetailRate);
        txt_mark1 = findViewById(R.id.tv_mark1);
        txt_mark2 = findViewById(R.id.tv_mark2);
        txt_mark3 = findViewById(R.id.tv_mark3);
        txt_mark4 = findViewById(R.id.tv_mark4);
        txt_mark5 = findViewById(R.id.tv_mark5);

        rb_1 = findViewById(R.id.ratingDetailRate_1);
        rb_2 = findViewById(R.id.ratingDetailRate_2);
        rb_3 = findViewById(R.id.ratingDetailRate_3);
        rb_4 = findViewById(R.id.ratingDetailRate_4);
        rb_5 = findViewById(R.id.ratingDetailRate_5);

        count = 0;
        rating_1 = 0;
        rating_2 = 0;
        rating_3 = 0;
        rating_4 = 0;
        rating_5 = 0;
    }
}