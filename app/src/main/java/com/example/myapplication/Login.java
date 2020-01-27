package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    TextView registerUser;
    EditText username, password;
    Button loginButton;
    String user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerUser = (TextView)findViewById(R.id.register);
        username = (EditText)findViewById(R.id.username);
        password = (EditText)findViewById(R.id.password);
        loginButton = (Button)findViewById(R.id.loginButton);

        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user = username.getText().toString();
                pass = password.getText().toString();

                loginPostRequest();

                if(user.equals("")){
                    username.setError("can't be blank");
                }
                else if(pass.equals("")){
                    password.setError("can't be blank");
                }
                else{

                    String url = "https://chat-d2491.firebaseio.com/users.json";
                    final ProgressDialog pd = new ProgressDialog(Login.this);
                    pd.setMessage("Loading...");
                    pd.show();

                    StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                        @Override
                        public void onResponse(String s) {
                            if(s.equals("null")){
                                Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                            }
                            else{
                                try {
                                    JSONObject obj = new JSONObject(s);

                                    if(!obj.has(user)){
                                        Toast.makeText(Login.this, "user not found", Toast.LENGTH_LONG).show();
                                    }
                                    else if(obj.getJSONObject(user).getString("password").equals(pass)){
                                        UserDetails.username = user;
                                        UserDetails.password = pass;

                                        startActivity(new Intent(Login.this, MainActivity.class));
                                    }
                                    else {
                                        Toast.makeText(Login.this, "incorrect password", Toast.LENGTH_LONG).show();
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            pd.dismiss();
                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            System.out.println("" + volleyError);
                            pd.dismiss();
                        }
                    });

                    RequestQueue rQueue = Volley.newRequestQueue(Login.this);
                    rQueue.add(request);



                }





            }
        });
    }

    public void loginPostRequest() {

        String URL_BASE = "http://777d33b5.ngrok.io";
        String URL= URL_BASE + "/auth/login";

        final RequestQueue requestQueue = Volley.newRequestQueue(Login.this);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, URL,null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);


                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();

                        try {
                            editor.putString("token", response.getString("token"));
                            Log.i("Here", response.getString("token"));
                            editor.apply();

                        } catch (JSONException e) {
                            Log.i("EROROROROR", "erororoororor ");
                            e.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })

        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("TOKEN", "99KI9Gj68CgCf70deM22Ka64chef2C40Gm2lFJ2J0G9JkDaaDAcbFfd19MfacGf3FFm8CM1hG0eDiIk8");

                return headers;
            }

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("email",user);
                params.put("password", pass);

                return params;
            }
        };

        requestQueue.add(jsObjRequest);
    }
}