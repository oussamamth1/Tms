package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        auth = FirebaseAuth.getInstance();
        String a=auth.getUid();
        Toast.makeText(this, ""+a, Toast.LENGTH_SHORT).show();
        BottomNavigationView bottomNavigationView = findViewById(R.id.button_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(onNav);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,new Fragment1()).commit();

    }
  private   BottomNavigationView.OnNavigationItemSelectedListener onNav =new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selected=null;
            switch (item.getItemId()){
                case R.id.profile_buttom:
                    selected=new Fragment1();
                    break;
                case R.id.ask_buttom:
                    selected=new Fragment2();
                    break;
                case R.id.queue_buttom:
                    selected=new Fragment3();
                    break;
                case R.id.Home_buttom:
                    selected=new Fragment4();
                    break;



            }
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,selected).commit();
            return true;
        }
    };
    public void logout(View view) {
        auth.signOut();
        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
        startActivity(intent);
    }


    protected void onStart() {
        super.onStart();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();

        }
    }}
