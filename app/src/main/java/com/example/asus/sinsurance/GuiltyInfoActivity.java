package com.example.asus.sinsurance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class GuiltyInfoActivity extends AppCompatActivity {

    EditText name ;
    EditText address ;
    EditText phone ;
    EditText lType ;
    EditText lNum ;
    EditText bINum ;
    EditText pINum ;
    EditText bIName;
    EditText pIName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guilty_info);
        Button next = (Button) findViewById(R.id.next);
        name = (EditText) findViewById(R.id.name);
        address = (EditText) findViewById(R.id.address);
        phone = (EditText) findViewById(R.id.phone);
        lType = (EditText) findViewById(R.id.l_type);
        lNum = (EditText) findViewById(R.id.l_num);
        bINum = (EditText) findViewById(R.id.b_i_num);
        pINum = (EditText) findViewById(R.id.p_i_num);
        bIName = (EditText) findViewById(R.id.b_i_name);
        pIName = (EditText) findViewById(R.id.p_i_name);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String url = "http://thingtalk.ir/update?key=K2RVPYEQ7YPVZYYF&field1="+SerialClass.Serial
                +"&field2="+ name.getText().toString() +"&field3="+address.getText().toString()+"&field4="+
                        phone.getText().toString()+"&field5="+lType.getText().toString()+"&field6="+lNum.getText().toString()
                        +"&field7="+bINum.getText().toString()+"&field8="+pINum.getText().toString();


                try {
                    new HttpPostRequest().execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext() , NonGuiltyInfoActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
