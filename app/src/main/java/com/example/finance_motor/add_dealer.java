package com.example.finance_motor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class add_dealer extends AppCompatActivity {

    private Button dealer_regis;
    private EditText dealer_name, dealer_EmailAddress, dealer_Password, dealer_phone;
    final String DAPI = "http://192.168.68.120/MVFP/add_dealer.php";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

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

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:{
                        Toast.makeText(add_dealer.this,"Home Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.users:{
                        Toast.makeText(add_dealer.this,"All users Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.form:{
                        Toast.makeText(add_dealer.this,"Forms Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.car:{
                        Toast.makeText(add_dealer.this,"Cars Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.dealers1:{
                        Toast.makeText(add_dealer.this,"Add dealers Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.profile:{
                        Toast.makeText(add_dealer.this,"Logout Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
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