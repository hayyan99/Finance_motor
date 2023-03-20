package com.example.finance_motor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;


import android.os.AsyncTask;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;

public class Dealer_car extends AppCompatActivity {


    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_car);

        // Initialize and execute the AsyncTask to fetch data from the PHP API
        new Dealer_car.GetDataTask().execute();


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
                        Toast.makeText(Dealer_car.this,"Home Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.form:{
                        Toast.makeText(Dealer_car.this,"Forms Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.car:{
                        Toast.makeText(Dealer_car.this,"Cars Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case R.id.profile:{
                        Toast.makeText(Dealer_car.this,"Logout Selected",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                return false;
            }
        });
}

    private class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String result = "";
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL("http://192.168.68.120/MVFP/dashboard.php");
                urlConnection = (HttpURLConnection) url.openConnection();

                InputStream in = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Parse the JSON data and display it in a table layout
            try {
                JSONArray jsonArray = new JSONArray(result);
                TableLayout tableLayout = findViewById(R.id.table_layout);

                // Create table rows and add data to them
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    TableRow tableRow = new TableRow(Dealer_car.this);
                    tableRow.setLayoutParams(new TableRow.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                    // Iterate through the JSON object and add each value to a table cell
                    Iterator<String> keys = jsonObject.keys();
                    while (keys.hasNext()) {
                        String key = keys.next();
                        TextView textView = new TextView(Dealer_car.this);
                        textView.setLayoutParams(new TableRow.LayoutParams(
                                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                        textView.setPadding(16, 16, 16, 16);
                        textView.setText(jsonObject.getString(key));
                        tableRow.addView(textView);
                    }
                    tableLayout.addView(tableRow);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}