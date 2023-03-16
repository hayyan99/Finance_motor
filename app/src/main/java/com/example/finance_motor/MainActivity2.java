package com.example.finance_motor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;


public class MainActivity2 extends AppCompatActivity {

    private Button regis, login;
    private EditText name, editTextTextEmailAddress, editTextTextPassword, phone;
    final String API = "http://192.168.10.74/MVFP/user_reg.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        regis = (Button) findViewById(R.id.btn);
        name = (EditText) findViewById(R.id.name);
        editTextTextEmailAddress = (EditText) findViewById(R.id.editTextTextEmailAddress);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);
        phone = (EditText) findViewById(R.id.phone);

        regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myReg(name.getText().toString(), editTextTextEmailAddress.getText().toString(), editTextTextPassword.getText().toString(), phone.getText().toString());
            }
        });

        login = (Button) findViewById(R.id.btn2);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginact();
            }
        });
    }

    public void loginact(){
        Intent loginobj = new Intent(this, Login.class);
        startActivity(loginobj);
    }

    public void myReg(String name, String email, String password, String phone) {

        StringRequest REQST = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("ok")){
                   Intent intent = new Intent(MainActivity2.this, Login.class);
                   startActivity(intent);
                }
                else {
                    Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("name", name);
                map.put("email", email);
                map.put("pass", password);
                map.put("phone", phone);
                return map;
            }
        };

        RequestQueue myreq = Volley.newRequestQueue(getApplicationContext());
        myreq.add(REQST);
    }
}