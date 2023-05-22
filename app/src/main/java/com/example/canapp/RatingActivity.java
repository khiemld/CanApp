package com.example.canapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.canapp.api.RateApi;
import com.example.canapp.api.RetrofitClient;
import com.example.canapp.model.rate.MessageRate;
import com.example.canapp.model.user.User;
import com.example.canapp.ulti.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RatingActivity extends AppCompatActivity {

    int ratingCount;

    RateApi rateApi;

    TextView txt_nameMember, txt_save;

    EditText edit_comment;

    ImageView img_avatarMember, img_backButton;

    RatingBar rb_1, rb_2, rb_3, rb_4, rb_5;

    Integer rating1, rating2, rating3, rating4, rating5;

    String comment;

    ScrollView scrollView;

    User user;

    String idPlan, idMember, idJudget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_rating);

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

        if (!user.getAvatar().equals("null")){
            Glide.with(getApplicationContext()).load(user.getAvatar()).into(img_avatarMember);
        } else {
            img_avatarMember.setImageResource(R.drawable.avatar_profile);
        }

        txt_nameMember.setText(user.getName());
        idMember = user.get_id();

        idJudget = SharedPrefManager.getInstance(getApplicationContext()).getUser().get_id();

        getRating();

        handleBackButton();

    }

    private void handleBackButton() {
        img_backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void getRating() {


            rb_1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser){
                        /*rating1 = rating;*/
                        rating1 = Math.round(rating);
                        ratingCount++;
                        checkRatingCompletion();
                    }
                }


            });

            rb_2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser){
                        rating2 = Math.round(rating);
                        ratingCount++;
                        checkRatingCompletion();
                    }

                }
            });

            rb_3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser){
                        rating3 = Math.round(rating);
                        ratingCount++;
                        checkRatingCompletion();
                    }

                }
            });

            rb_4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser){
                        rating4 = Math.round(rating);
                        ratingCount++;
                        checkRatingCompletion();
                    }

                }
            });

            rb_5.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (fromUser){
                        rating5 = Math.round(rating);
                        ratingCount++;
                        checkRatingCompletion();
                    }

                }
            });


    }

    private void checkRatingCompletion() {
        if (ratingCount == 5){
            txt_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CallRating();
                }
            });
        }
    }

    private void CallRating() {
        String comment = edit_comment.getText().toString();
        rateApi = RetrofitClient.getRetrofit().create(RateApi.class);

        Call<MessageRate> call = rateApi.addRate(idMember, idJudget, idPlan, rating1, rating2, rating3, rating4, rating5, comment);
        call.enqueue(new Callback<MessageRate>() {
            @Override
            public void onResponse(Call<MessageRate> call, Response<MessageRate> response) {
                MessageRate messageRate = response.body();
                if (response.isSuccessful()){

                    Toast.makeText(RatingActivity.this, messageRate.getMessage(), Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(RatingActivity.this, "Đã đánh giá người này rồi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MessageRate> call, Throwable t) {
                Log.e("Erorr: ", t.getMessage());
            }
        });
    }

    private void Mapping() {

        scrollView = findViewById(R.id.scroll_addRating);
        img_avatarMember = findViewById(R.id.cr_avatarRating);
        img_backButton = findViewById(R.id.img_backRating);
        txt_nameMember = findViewById(R.id.tv_namememberRating);
        txt_save = findViewById(R.id.tv_saveRating);
        edit_comment = findViewById(R.id.edt_commentRating);

        rb_1 = findViewById(R.id.ratingRating1);
        rb_2 = findViewById(R.id.ratingRating2);
        rb_3 = findViewById(R.id.ratingRating3);
        rb_4 = findViewById(R.id.ratingRating4);
        rb_5 = findViewById(R.id.ratingRating5);

        ratingCount = 0;
        rating1 = Math.round(rb_1.getRating());
        rating2 = Math.round(rb_2.getRating());
        rating3 = Math.round(rb_3.getRating());
        rating4 = Math.round(rb_3.getRating());
        rating5 = Math.round(rb_5.getRating());

    }
}