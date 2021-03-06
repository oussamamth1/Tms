package com.example.tmspro;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Map;
import java.util.logging.LogRecord;

public class Splashscreen extends AppCompatActivity {
ImageView SphlashImag;
TextView spllashtv,splashname;
long animaTime=3500;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splashscreen);
        splashname=findViewById(R.id.tv_seplash_name);
        spllashtv=findViewById(R.id.tv_seplash);
        SphlashImag=findViewById(R.id.logo_splash);
        ObjectAnimator animatory= ObjectAnimator.ofFloat(SphlashImag,"y",400f);
        ObjectAnimator animatorname= ObjectAnimator.ofFloat(spllashtv,"x",150f);
        animatory.setDuration(animaTime);
        animatorname.setDuration(animaTime);
        AnimatorSet animaterset=new AnimatorSet();
        animaterset.playTogether(animatory,animatorname);
        animaterset.start();


    }

    @Override
    protected void onStart() {
        super.onStart();
        final FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(user!=null){
                    startActivity(new Intent(Splashscreen.this,MainActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(Splashscreen.this,LoginActivity.class));
                }
            }
        },4000);
    }
}