package com.example.finance_motor;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    ImageSlider imageSlider;

//
//    DrawerLayout drawerLayout;
//    NavigationView navigationView;
//    ActionBarDrawerToggle drawerToggle;

    Button sup, sin;
//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if(drawerToggle.onOptionsItemSelected(item)){
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sup = findViewById(R.id.sgnup);

        sup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent si = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(si);
            }
        });

        sin = findViewById(R.id.sgnin);

        sin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent su = new Intent(MainActivity.this, Login.class);
                startActivity(su);
            }
        });

////        imageSlider = findViewById(R.id.image_slider);
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//
////        ArrayList<SlideModel> arrayList = new ArrayList<>();
////        arrayList.add(new SlideModel(R.drawable.img, ScaleTypes.FIT));
////        arrayList.add(new SlideModel(R.drawable.img_1, ScaleTypes.FIT));
////        arrayList.add(new SlideModel(R.drawable.img_2, ScaleTypes.FIT));
////
////        imageSlider.setImageList(arrayList);
//
//
//        drawerToggle = new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.home:{
//                        Toast.makeText(MainActivity.this,"Home Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.contact:{
//                        Toast.makeText(MainActivity.this,"Contact Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.record:{
//                        Toast.makeText(MainActivity.this,"Dealer Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.insurance:{
//                        Toast.makeText(MainActivity.this,"Insurance Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.profile:{
//                        Toast.makeText(MainActivity.this,"Login Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.share:{
//                        Toast.makeText(MainActivity.this,"Share Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    case R.id.rate_us:{
//                        Toast.makeText(MainActivity.this,"Rate Us Selected",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                }
//                return false;
//            }
//        });
//    }
//
//    @Override
//    public void onBackPressed() {
//        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }else {
//            super.onBackPressed();
//        }
    }
}