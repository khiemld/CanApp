package com.example.canapp.ulti;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.canapp.WelcomeActivity;
import com.example.canapp.model.User;

public class SharedPrefManager {
    private static final String SHARED_PREF_NAME = "volleyregisterlogin";

    private static final String KEY_ID = "key_id";

    private static final String KEY_NAME = "keyname";

    private static final String KEY_EMAIL = "keyemail";

    private static final String KEY_PASSWORD = "keypassword";

    private static final String KEY_AVATAR = "keyavatar";

    private static final String KEY_ADDRESS = "keyaddress";

    private static final String KEY_MAJOR = "keymajor";

    private static final String KEY_PHONE = "keyphone";

    private static final String KEY_ACTIVE = "keyactive";

    private static final String KEY_REMEMBER = "keyremember";

    private static SharedPrefManager mInstance;

    private static Context ctx;

    //khoi tao  constructor

    public SharedPrefManager(Context context) {
        ctx = context;
    }

    public static synchronized SharedPrefManager getInstance(Context context){
        if (mInstance == null){
            mInstance = new SharedPrefManager(context);
        }
        return mInstance;
    }

    //Luu thong tin dang nhap vao shared preferences
    public void userLogin(User user, boolean remember){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_ID, user.get_id());
        editor.putString(KEY_NAME, user.getName());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_PASSWORD, user.getPassword());
        editor.putString(KEY_AVATAR, user.getAvatar());
        editor.putString(KEY_ADDRESS, user.getAddress());
        editor.putString(KEY_MAJOR, user.getMajor());
        editor.putString(KEY_PHONE, user.getPhone());
        editor.putBoolean(KEY_ACTIVE, user.isActive());
        editor.putBoolean(KEY_REMEMBER, remember);

        editor.apply();
    }

    //Kiem tra trang thai dang nhap
    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean(KEY_REMEMBER, false)){
            return sharedPreferences.getString(KEY_NAME, null) != null;
        } else {
            clearUser();
        }
        return sharedPreferences.getString(KEY_NAME, null) != null;
    }

    //Lay thong tin user duoc luu trong shared preference
    public User getUser(){
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new User(
                sharedPreferences.getString(KEY_ID, null),
                sharedPreferences.getString(KEY_NAME, null),
                sharedPreferences.getString(KEY_EMAIL, null),
                sharedPreferences.getString(KEY_PASSWORD, null),
                sharedPreferences.getString(KEY_AVATAR, null),
                sharedPreferences.getString(KEY_ADDRESS, null),
                sharedPreferences.getString(KEY_MAJOR, null),
                sharedPreferences.getString(KEY_PHONE, null),
                sharedPreferences.getBoolean(KEY_ACTIVE, true)
        );
    }

    //Xoa thong tin
    public void clearUser() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(KEY_ID);
        editor.remove(KEY_NAME);
        editor.remove(KEY_EMAIL);
        editor.remove(KEY_PASSWORD);
        editor.remove(KEY_AVATAR);
        editor.remove(KEY_ADDRESS);
        editor.remove(KEY_MAJOR);
        editor.remove(KEY_PHONE);
        editor.remove(KEY_ACTIVE);
        editor.remove(KEY_REMEMBER);
        editor.apply();
    }
    //Dang xuat
    public void logout() {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        ctx.startActivity(new Intent(ctx, WelcomeActivity.class));
    }
}
