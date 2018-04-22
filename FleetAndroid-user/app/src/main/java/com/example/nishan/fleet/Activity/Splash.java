package com.example.nishan.fleet.Activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.example.nishan.fleet.R;
import com.google.firebase.auth.FirebaseAuth;

public class Splash extends AppCompatActivity {
    final int DELAY_TIME = 300;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (FirebaseAuth.getInstance().getCurrentUser() == null) {
                    startActivity(new Intent(Splash.this, SignInActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(Splash.this, MainActivity.class));
                    finish();

                }
            }
        }, DELAY_TIME);


    }

}


