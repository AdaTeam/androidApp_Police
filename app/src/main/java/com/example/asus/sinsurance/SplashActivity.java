package com.example.asus.sinsurance;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (SessionManager.getUser(SplashActivity.this).length() ==0 )
                {
                    Intent i = new Intent(SplashActivity.this , LoginActivity.class);
                    startActivity(i);

                }
                else
                {
                    Intent i = new Intent(SplashActivity.this , MenuActivity.class);
                    startActivity(i);
                }


                finish();
            }
        } , 2000 );

    }
}
