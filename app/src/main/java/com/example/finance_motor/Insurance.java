package com.example.finance_motor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import android.content.Intent;
import android.view.MenuItem;
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
import com.google.android.material.navigation.NavigationView;

import java.util.HashMap;
import java.util.Map;

public class Insurance extends AppCompatActivity {

    private Button form_btn;
    private EditText name, loan,down_payment,term,interest_rate,monthly_payment;
    final String FAPI = "http://192.168.68.120/MVFP/form.php";

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insurance);

        form_btn = (Button) findViewById(R.id.formbtn);
        name = (EditText) findViewById(R.id.name);
        loan = (EditText) findViewById(R.id.loan);
        down_payment = (EditText) findViewById(R.id.downpayment);
        term = (EditText) findViewById(R.id.term);
        interest_rate = (EditText) findViewById(R.id.interest);
        monthly_payment = (EditText) findViewById(R.id.monthlypayment);


        form_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myForm(name.getText().toString(), loan.getText().toString(), down_payment.getText().toString(), term.getText().toString(),interest_rate.getText().toString(),monthly_payment.getText().toString());
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
                switch (item.getItemId()) {
                    case R.id.home: {
                        Toast.makeText(Insurance.this,"Home Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.record: {
                        Toast.makeText(Insurance.this, "Dealers Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.insurance: {
                        Toast.makeText(Insurance.this, "Forms Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }

                    case R.id.logout: {
                        Toast.makeText(Insurance.this, "Logout Selected", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public void myForm(String name, String loan, String down_payment, String term, String interest_rate, String monthly_payment) {

        StringRequest REQST = new StringRequest(Request.Method.POST, FAPI, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Intent loginpage = new Intent(Insurance.this, Login.class);
                startActivity(loginpage);
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
                map.put("vehicle_model", name);
                map.put("loan_amount", loan);
                map.put("down_payment", down_payment);
                map.put("term", term);
                map.put("interest_rate", interest_rate);
                map.put("monthly_payment", monthly_payment);
                return map;
            }
        };

        RequestQueue myreq = Volley.newRequestQueue(getApplicationContext());
        myreq.add(REQST);
    }
}