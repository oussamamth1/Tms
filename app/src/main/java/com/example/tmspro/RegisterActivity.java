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

public class RegisterActivity extends AppCompatActivity {
EditText email,password,conformpass,username;
Button registeur,loginbutton;
CheckBox checkBox;
ProgressBar progressBar;
FirebaseAuth muth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        muth=FirebaseAuth.getInstance();
        setContentView(R.layout.activity_register);
        email=findViewById(R.id.email_login_et);
        password=findViewById(R.id.pass_login_et);
        checkBox=findViewById(R.id.Regis_chek_box);
        conformpass=findViewById(R.id.confirm_pass_Reg_et);
        username=findViewById(R.id.RegisteurName);
        progressBar=findViewById(R.id.prog_Registeur);
        registeur=findViewById(R.id.button_Registeu);
        loginbutton=findViewById(R.id.button_login_singup);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    conformpass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else{password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    conformpass.setTransformationMethod(PasswordTransformationMethod.getInstance());}
            }
        });
        registeur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email1=email.getText().toString();
                String pass=password.getText().toString();
                String conf=conformpass.getText().toString();
                String userNa=username.getText().toString();
                if(!TextUtils.isEmpty(email1) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(conf)||!TextUtils.isEmpty(userNa)){
                   if(pass.equals(conf)){progressBar.setVisibility(View.VISIBLE);
                   muth.createUserWithEmailAndPassword(email1,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull  Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(RegisterActivity.this,"User Created",Toast.LENGTH_LONG).show();
                               sendusertomain();
                               progressBar.setVisibility(View.INVISIBLE);

                           }else{
                               String err=task.getException().getMessage();
                               Toast.makeText(getApplicationContext(),"Error "+err,Toast.LENGTH_SHORT).show();

                           }

                       }
                   });

                   }else{
                       progressBar.setVisibility(View.INVISIBLE);
                       Toast.makeText(getApplicationContext(),"password and conform password not match",Toast.LENGTH_SHORT).show();}
                }else {
                    Toast.makeText(getApplicationContext(),"please file all fields",Toast.LENGTH_SHORT).show();
                }
            }
        });
        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });
    }

    private void sendusertomain() {
        Intent intent=new Intent(RegisterActivity.this,Splashscreen.class);
        startActivity(intent);
        finish();
    }
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            sendusertomain();
        }

        }


}