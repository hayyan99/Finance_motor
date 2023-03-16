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

public class add_dealer extends AppCompatActivity {

    private Button dealer_regis;
    private EditText dealer_name, dealer_EmailAddress, dealer_Password, dealer_phone;
    final String DAPI = "http://192.168.10.74/MVFP/add_dealer.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);

        dealer_regis = findViewById(R.id.d_btn);
        dealer_name = findViewById(R.id.d_name);
        dealer_EmailAddress = findViewById(R.id.d_EmailAddress);
        dealer_Password = findViewById(R.id.d_Password);
        dealer_phone = findViewById(R.id.d_phone);

        dealer_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealer_reg(dealer_name.getText().toString(), dealer_EmailAddress.getText().toString(), dealer_Password.getText().toString(), dealer_phone.getText().toString());
            }
        });
    }

    public void dealer_reg(String name, String email, String password, String phone) {

        StringRequest DREQST = new StringRequest(Request.Method.POST, DAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if(response.equals("ok")){
                    Toast.makeText(getApplicationContext(), "Dealer Added", Toast.LENGTH_SHORT).show();
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

        RequestQueue my_d_req = Volley.newRequestQueue(getApplicationContext());
        my_d_req.add(DREQST);
    }
}