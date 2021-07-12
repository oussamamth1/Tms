package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText email,password;
    Button registeur,loginbutton;
    CheckBox checkBox;
    ProgressBar progressBar;
    FirebaseAuth muth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email_login_et);
        password=findViewById(R.id.pass_login_et);
        registeur=findViewById(R.id.button_to_singup);
        loginbutton=findViewById(R.id.login_to_singup);
        checkBox=findViewById(R.id.login_chek_box);
        progressBar=findViewById(R.id.prog_login);
        muth=FirebaseAuth.getInstance();
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                }else{password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    }
            }
        });
        registeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);

            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email1=email.getText().toString();
                String pass=password.getText().toString();
                if(!TextUtils.isEmpty(email1)||!TextUtils.isEmpty(pass)){
                    progressBar.setVisibility(View.VISIBLE);
                    muth.signInWithEmailAndPassword(email1,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                sendTomain();
                                progressBar.setVisibility(View.INVISIBLE);
                            }else{
                                String err=task.getException().getMessage();
                                Toast.makeText(getApplicationContext(),"Error "+err,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }else{  Toast.makeText(getApplicationContext(),"please file all fields",Toast.LENGTH_SHORT).show();}

            }
        });
    }

    private void sendTomain() {
        Intent intent=new Intent(LoginActivity.this,Splashscreen.class);
        startActivity(intent);
        finish();
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            sendTomain();
        }
}}