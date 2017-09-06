package com.example.asus.sinsurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class LocationActivity extends AppCompatActivity {

    EditText address ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        address = (EditText) findViewById(R.id.address);
        Button okButton = (Button) findViewById(R.id.getAdd);
        Button btn = (Button) findViewById(R.id.location_button);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext() , MapsActivity.class);
                startActivity(i);
                finish();
            }
        });

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://thingtalk.ir/update?key=YMU7N9CO82ZRGFO7&field1="+SerialClass.Serial+
                        "&field4="+address.getText().toString();


                try {
                    new HttpPostRequest().execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext() , ExplanationActivity.class);
                startActivity(i);
                finish();

            }
        });


    }
}
