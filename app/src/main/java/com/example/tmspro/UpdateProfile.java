package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;
import com.squareup.picasso.Picasso;

import static android.service.controls.ControlsProviderService.TAG;

public class UpdateProfile extends AppCompatActivity {
    private TextInputEditText Email,Location,Name,Profission,web;
    Button upbutton;
    ImageView userimage;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_update_profile);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentuserid=user.getUid();
        documentReference=db.collection("user").document(currentuserid);
        userimage=findViewById(R.id.authimageid);
        Email=findViewById(R.id.emailup);
        Location=findViewById(R.id.et_Location_up);
        Name=findViewById(R.id.nameup);
        Profission=findViewById(R.id.et_Profession_up);
        web=findViewById(R.id.webup);
        upbutton=findViewById(R.id.buttonup);

        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updadeprofile();

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                String nameResulta=task.getResult().getString("name");
                String profResulta=task.getResult().getString("prof");
                String locationResulta=task.getResult().getString("location");
                String emailResulta=task.getResult().getString("email");
                String webResulta=task.getResult().getString("web");
                String urlResulta=task.getResult().getString("url");
                Name.setText(nameResulta);
                web.setText(webResulta);
                Profission.setText(profResulta);
                Email.setText(emailResulta);
                Location.setText(locationResulta);
                    Picasso.with(getApplicationContext()).load(urlResulta).into(userimage);
                }else {
                    Toast.makeText(UpdateProfile.this, "no profile detacted", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updadeprofile() {
        String name=Name.getText().toString();
        String prof=Profission.getText().toString();
        String Web=web.getText().toString();
        String email=Email.getText().toString();
        String loc=Location.getText().toString();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentuserid=user.getUid();
        final DocumentReference sDoc=db.collection("user").document(currentuserid);
        db.runTransaction(new Transaction.Function<Void>() {
            @Nullable

            @Override
            public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                DocumentSnapshot snapshot = transaction.get(sDoc);

                transaction.update(sDoc, "name", name);
                transaction.update(sDoc, "prof", prof);
                transaction.update(sDoc, "web", Web);
                transaction.update(sDoc, "email", email);
                transaction.update(sDoc, "location", loc);
                return null;
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateProfile.this, "faild", Toast.LENGTH_SHORT).show();

            }
        }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(UpdateProfile.this,MainActivity.class));
                    Toast.makeText(UpdateProfile.this, "updateed", Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });



    }
}