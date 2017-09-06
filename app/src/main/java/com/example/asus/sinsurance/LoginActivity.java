package com.example.asus.sinsurance;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class LoginActivity extends AppCompatActivity {

    private EditText userNameEditText;
    private EditText passwordEditText;
    private Button signInButton;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userNameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText) findViewById(R.id.password);
        signInButton = (Button) findViewById(R.id.Sign_in_button);

        //button Listener
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String myUrl = "http://thingtalk.ir/channels/532/feed.json?key=0BTNO1SCRIV7UN7O&results=1";
                String user , pass ;
                String jsonResult;

                user = userNameEditText.getText().toString();
                pass =   passwordEditText.getText().toString();

                if(checkLogin(view))
                {

                    //Instantiate new instance of our class
                    HttpGetRequest getRequest = new HttpGetRequest();

                    //Perform the doInBackground method, passing in our url

                    try {
                        jsonResult = getRequest.execute(myUrl).get();
                        if(checkUserPass(jsonResult , user , pass))
                        {
                            Toast.makeText(getApplicationContext() , "welcome " , Toast.LENGTH_LONG).show();
                            SessionManager.setUserPass(LoginActivity.this , user , pass);
                            Intent i = new Intent(getApplicationContext() , MenuActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else
                        {
                            Toast.makeText(getApplicationContext() , "invalid username or password" , Toast.LENGTH_LONG).show();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }

                }

            }
        });

    }

    public boolean checkUserPass(String json , String user , String pass)
    {
        try {

            JSONObject jsonObj = new JSONObject(json);
            JSONArray array = jsonObj.getJSONArray("feeds");
            JSONObject data = array.getJSONObject(0);
            String username = data.getString("field2");
            String password = data.getString("field3");
            if (username.equals(user) && password.equals(pass))
            {
                return true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }


    public boolean checkLogin(View arg0) {

        final String userName =  userNameEditText.getText().toString();
        if (!isValidUserName(userName)) {
            //Set error message for userName field
            userNameEditText.setError("Invalid userName");
            return false ;
        }

        final String pass = passwordEditText.getText().toString();
        if (!isValidPassword(pass)) {
            //Set error message for password field
            passwordEditText.setError("Password cannot be empty");
            return false ;
        }

        else
        {
            return true ;
        }

    }

    // validating u
    private boolean isValidUserName(String userName) {

        if (userName!= null ) {
            return true;
        }
        return false;
    }


    // validating password
    private boolean isValidPassword(String pass) {
        String regex = "\\d+";
        if (pass != null && pass.length() == 10) {
            if (pass.matches(regex))
                return true ;
        }
        return false;
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
}

