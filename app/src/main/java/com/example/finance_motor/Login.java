package com.example.finance_motor;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class Login extends AppCompatActivity {

    private Button LOGIN;
    private EditText email, pass;
    final String LOGINAPI = "http://192.168.68.109/MVFP/login.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LOGIN = (Button) findViewById(R.id.mylogin);
        email = (EditText) findViewById(R.id.editTextTextEmailAddress2);
        pass = (EditText) findViewById(R.id.editTextTextPassword2);

        LOGIN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mylogin(email.getText().toString(), pass.getText().toString());
            }
        });

    }

    public void mylogin(String email, String pass){
        StringRequest REQ = new StringRequest(Request.Method.POST, LOGINAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response.equals("User Customer")) {
                    Intent mydash = new Intent(getApplicationContext(), Dashboard.class);
                    startActivity(mydash);
                }
                else if (response.equals("User Admin")) {
                    Intent admindash = new Intent(getApplicationContext(), Adminpanel.class);
                    startActivity(admindash);
                }
                else if (response.equals("User Dealer")) {
                    Intent dealerdash = new Intent(getApplicationContext(), dealerspanel.class);
                    startActivity(dealerdash);
                }
                else {
                    Toast.makeText(Login.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("email", email);
                map.put("pass", pass);
                return map;
            }
        };
        RequestQueue reqst = Volley.newRequestQueue(getApplicationContext());
        reqst.add(REQ);


    }
}