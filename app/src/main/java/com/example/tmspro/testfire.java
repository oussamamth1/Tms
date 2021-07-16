package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import static android.service.controls.ControlsProviderService.TAG;

public class testfire extends AppCompatActivity {
EditText name,email,prof,web;
Button test;

    FirebaseDatabase database;
    DatabaseReference databaseReference;//realtimedb
    public AllUserMember member;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfire);
        name=findViewById(R.id.testname);
        email=findViewById(R.id.testmail);
        prof=findViewById(R.id.testprof);
        web=findViewById(R.id.testweb);
        test=findViewById(R.id.testbut1);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("test");

        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email=email.getText().toString();
                String Name=name.getText().toString();
                String Prof=prof.getText().toString();
                String Web=web.getText().toString();
                Map<String, Object> user = new HashMap<>();
                user.put("first", Name);
                user.put("prof", Prof);
                user.put("email", Email);
                user.put("web", Web);
                databaseReference.setValue("testttt.");



            }
        });
    }

}