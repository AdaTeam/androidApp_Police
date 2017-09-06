package com.example.asus.sinsurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutionException;

public class ExplanationActivity extends AppCompatActivity {

    EditText explanation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explanation);
        explanation = (EditText) findViewById(R.id.explanation) ;

        Button btn = (Button) findViewById(R.id.finish);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://thingtalk.ir/update?key=BSGCBRGSLQE2OZMO&field1="+SerialClass.Serial
                        +"&field2="+ explanation.getText().toString() ;


                try {
                    new HttpPostRequest().execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext() , FinishActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
}
