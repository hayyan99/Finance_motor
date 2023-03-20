package com.example.finance_motor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;

import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class All_users extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    private static final String user_API_URL = "http://192.168.68.120/MVFP/customers.php";
    private TableLayout tableLayout3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_users);

        tableLayout3 = findViewById(R.id.tableLayout);

        // Fetch data from the API in a background thread
        new FetchDataTask().execute(user_API_URL);


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
                        Toast.makeText(All_users.this,"Home Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.users:{
                        Toast.makeText(All_users.this,"All users Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.form:{
                        Toast.makeText(All_users.this,"Forms Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.car:{
                        Toast.makeText(All_users.this,"Cars Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.dealers1:{
                        Toast.makeText(All_users.this,"Add dealers Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.profile:{
                        Toast.makeText(All_users.this,"Logout Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });
    }

    private class FetchDataTask extends AsyncTask<String, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(String... params) {

            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                connection.disconnect();

                // Parse the JSON response and return it as a JSONArray
                return new JSONArray(response.toString());
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                return null;
            }
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            if (result != null) {
                // Populate the table with the data
                for (int i = 0; i < result.length(); i++) {
                    try {
                        JSONObject obj = result.getJSONObject(i);

                        String name = obj.getString("name");
                        String email = obj.getString("email");
                        String phone = obj.getString("phone");

                        TableRow row = new TableRow(getApplicationContext());

                        TextView nameTextView = new TextView(getApplicationContext());
                        nameTextView.setText(name);
                        row.addView(nameTextView);

                        TextView emailTextView = new TextView(getApplicationContext());
                        emailTextView.setText(email);
                        row.addView(emailTextView);

                        TextView phoneTextView = new TextView(getApplicationContext());
                        phoneTextView.setText(phone);
                        row.addView(phoneTextView);

                        tableLayout3.addView(row);
                    } catch (JSONException e) {
                        Log.e("Error", e.getMessage());
                    }
                }
            }
        }
    }
}