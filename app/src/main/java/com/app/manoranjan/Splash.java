package com.app.manoranjan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.app.manoranjan.activities.MainActivity;
import com.app.manoranjan.activities.SignInActivity;
import com.app.manoranjan.utils.SessionManager;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        ImageView logo = findViewById(R.id.logoImage);
        logo.setAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_splash_logo));
        openMainActivity();
    }

    private void openMainActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                if (SessionManager.isLoggedIn(Splash.this)) {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out);
                } else {
                    SessionManager.logoutUser();
                    startActivity(new Intent(Splash.this, SignInActivity.class));
                }
                finish();
            }
        }, 1500);
    }

}