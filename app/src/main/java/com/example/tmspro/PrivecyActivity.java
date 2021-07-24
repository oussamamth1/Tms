package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Transaction;

public class PrivecyActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
String[] status= {"choose any one","public","privet"};
TextView statustxt;
Button btnStatus;
Spinner spinner;
FirebaseFirestore db=FirebaseFirestore.getInstance();
DocumentReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privecy2);
        statustxt=findViewById(R.id.tv_status);
        btnStatus=findViewById(R.id.btn_privecy);
        spinner=findViewById(R.id.Spinner);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String cureentid=user.getUid();
        reference=db.collection("user").document(cureentid);
        ArrayAdapter arrayAdapter= new ArrayAdapter(this, android.R.layout.simple_spinner_item,status);
        arrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePrivacy();
            }
        });
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(this, "Please Selecte valueus", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onStart() {
        super.onStart();
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String privacy=task.getResult().getString("privacy");
                    statustxt.setText(privacy);
                }else{
                    Toast.makeText(PrivecyActivity.this, "errer", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void savePrivacy() {
        final String value=spinner.getSelectedItem().toString();
        if(value=="choose any one"){
            Toast.makeText(this, "Please Select Value", Toast.LENGTH_SHORT).show();
        }else{
            FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
            String cureentid=user.getUid();
            final DocumentReference sDoc=db.collection("user").document(cureentid);
            db.runTransaction(new Transaction.Function<Void>() {
                @Nullable

                @Override
                public Void apply(@NonNull Transaction transaction) throws FirebaseFirestoreException {
                    DocumentSnapshot snapshot = transaction.get(sDoc);

                    transaction.update(sDoc, "privacy", value);

                    return null;
                }
            }).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(PrivecyActivity.this, "Status Update", Toast.LENGTH_SHORT).show();

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(PrivecyActivity.this, "faild", Toast.LENGTH_SHORT).show();

                }
            }).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        startActivity(new Intent(PrivecyActivity.this,MainActivity.class));
                        Toast.makeText(PrivecyActivity.this, "updateed", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                }
            });
        }

    }

}