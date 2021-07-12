package com.example.tmspro;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class CreateProfile extends AppCompatActivity {
EditText etname,etlocation,etprofession,etwebsite,etmail;
ProgressBar etprog;
Button etbutton;
ImageView imageView;
Uri imageuri;
UploadTask uploadTask;
StorageReference storageReference;
FirebaseDatabase database;
DatabaseReference databaseReference;
FirebaseFirestore db;
DocumentReference documentReference;
private static final int PICK_IMAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);
        imageView=findViewById(R.id.iv_cp);
        etbutton=findViewById(R.id.btn_cp);
        etname=findViewById(R.id.et_name_cp);
        etlocation=findViewById(R.id.et_Location_cp);
        etprofession=findViewById(R.id.et_Profession_cp);
        etmail=findViewById(R.id.et_Email_cp);
        etwebsite=findViewById(R.id.et_WebSite_cp);
        etprog=findViewById(R.id.porg_cp);

    }
}