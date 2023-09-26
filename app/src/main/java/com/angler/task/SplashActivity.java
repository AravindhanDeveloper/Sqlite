package com.angler.task;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;


public class SplashActivity extends AppCompatActivity {
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        token = PreferencesHelper.getPreference(SplashActivity.this, PreferencesHelper.PREF_TOKEN);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (token.length() != 0) {
                    Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

        }, 3000);
    }
}