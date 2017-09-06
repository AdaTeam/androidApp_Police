package com.example.asus.sinsurance;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class GeneralInfoActivity extends AppCompatActivity {

    TextView serial ;
    EditText accidentDate ;
    EditText accidentTime ;
    EditText attendDate;
    EditText attendTime;
    RadioGroup radioGroup;
    RadioButton radioTypeButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general_info);
        serial = (TextView) findViewById(R.id.serial);
        Log.e("a" , "1");
        getSerialNumber();
        Log.e("a" , "2");
        serial.setText(SerialClass.Serial);
        accidentDate = (EditText) findViewById(R.id.accident_date);
        accidentTime= (EditText) findViewById(R.id.accident_time);
        attendDate = (EditText) findViewById(R.id.attend_date);
        attendTime = (EditText) findViewById(R.id.attend_time);
        radioGroup = (RadioGroup) findViewById(R.id.group);


        Button nextButton = (Button) findViewById(R.id.next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int selectedId = radioGroup.getCheckedRadioButtonId();
                System.out.println(selectedId);

                int t;
                // find the radiobutton by returned id
                radioTypeButton = (RadioButton) findViewById(selectedId);
                String type = (String) radioTypeButton.getText();
                if (type.equals("جرحی"))
                {
                    t=2;
                }
                else if (type.equals("فوتی")){
                    t=3;
                }
                else {
                    t=1;
                }


                String url = "http://thingtalk.ir/update?key=F77C7M69OZHFO4SS&field1="+SerialClass.Serial+"&field2="+
                        accidentDate.getText().toString()+"&field3="+accidentTime.getText().toString()+"&field4="+
                        attendDate.getText().toString()+"&field5="+attendTime.getText().toString()+"&field6="+t;

                try {
                    new HttpPostRequest().execute(url).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Intent i = new Intent(GeneralInfoActivity.this , GuiltyInfoActivity.class);
                startActivity(i);
                finish();
            }
        });


    }

    public void getSerialNumber()
    {
        String url = "http://thingtalk.ir/channels/515/feed.json?key=HER9J6H518ONQRVW&results=1";
        HttpGetRequest get = new HttpGetRequest();
        try {
            String json = get.execute(url).get();
            String num = getSerial(json);
            SerialClass.Serial=num;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }

    private class HttpGetRequest extends AsyncTask<String, Void, String> {
        public static final String REQUEST_METHOD = "GET";
        public static final int READ_TIMEOUT = 15000;
        public static final int CONNECTION_TIMEOUT = 15000;

        @Override
        protected String doInBackground(String... params){
            String stringUrl = params[0];
            String result;
            String inputLine;

            try {
                //Create a URL object holding our url
                URL myUrl = new URL(stringUrl);

                //Create a connection
                HttpURLConnection connection =(HttpURLConnection)
                        myUrl.openConnection();

                //Set methods and timeouts
                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                //Connect to our url
                connection.connect();

                //Create a new InputStreamReader
                InputStreamReader streamReader = new
                        InputStreamReader(connection.getInputStream());

                //Create a new buffered reader and String Builder
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                //Check if the line we are reading is not null
                while((inputLine = reader.readLine()) != null){
                    stringBuilder.append(inputLine);
                }

                //Close our InputStream and Buffered reader
                reader.close();
                streamReader.close();

                //Set our result equal to our stringBuilder
                result = stringBuilder.toString();
            }
            catch(IOException e){
                e.printStackTrace();
                result = null;
            }

            return result;
        }

        protected void onPostExecute(String result){
            super.onPostExecute(result);
        }

        //checking connectivity
    }

    public String getSerial(String json)
    {
        try {

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("feeds");
            JSONObject data = array.getJSONObject(0);
            String serial = data.getString("field1");
            int num = Integer.parseInt(serial)+1;
            String url = "http://thingtalk.ir/update?key=HER9J6H518ONQRVW&field1="+String.valueOf(num);
            new HttpPostRequest().execute(url).get();
            return serial;



        } catch (JSONException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }


}
