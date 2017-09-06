package com.example.asus.sinsurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class KrookiLocationInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_krooki_location_info);

        Button next = (Button) findViewById(R.id.next);
        Button krooki = (Button) findViewById(R.id.kroki);
        Button location = (Button) findViewById(R.id.loca);


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(KrookiLocationInfoActivity.this, ExplanationActivity.class);
                startActivity(i);
                finish();
            }
        });

        krooki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(KrookiLocationInfoActivity.this, krookiActivity.class);
                startActivity(i);
            }
        });

         location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(KrookiLocationInfoActivity.this, LocationActivity.class);
                startActivity(i);

            }
        });
    }
}
