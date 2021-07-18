package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateProfile extends AppCompatActivity {
EditText etname,etlocation,etprofession,etwebsite,etmail;
ProgressBar etprog;
Button etbutton,testbutt;
ImageView imageView;
Uri imageuri;
UploadTask uploadTask;
    FirebaseAuth auth;
StorageReference storageReference;
FirebaseDatabase database=FirebaseDatabase.getInstance();;
DatabaseReference databaseReference;//realtimedb
FirebaseFirestore db= FirebaseFirestore.getInstance();
DocumentReference documentReference;
private static final int PICK_IMAGE=1;
private static final String KEY_NAME="hii";
private static final String KEY_EMail="hiii";
private static final String KEY_Profision="hiii";
    private static final int STORAGE_PERMISSION_CODE = 123;
public AllUserMember member;
String Currentuserid;
    String currentuserid;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_profile);
        testbutt=findViewById(R.id.testbut);
        imageView=findViewById(R.id.iv_cp);
      //  etbutton=findViewById(R.id.btn_cp);
        etname=findViewById(R.id.et_name_cp);
        etlocation=findViewById(R.id.et_Location_cp);
        etprofession=findViewById(R.id.et_Profession_cp);
        etmail=findViewById(R.id.et_Email_cp);
        etwebsite=findViewById(R.id.et_WebSite_cp);
        etprog=findViewById(R.id.porg_cp);
        member=new AllUserMember();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        currentuserid =auth.getCurrentUser().getUid();
        //Currentuserid=user.getUid();
        //documentReference =db.collection("user").document(Currentuserid);
        storageReference= FirebaseStorage.getInstance().getReference("profile image");
        databaseReference=database.getReference("Allusers");
        etbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               ///uploadData();
                test();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent,PICK_IMAGE);
            }
        });

    }

    private void test() {
        String name=etname.getText().toString().trim();
        String location=etlocation.getText().toString().trim();
        String mail=etmail.getText().toString().trim();
        Map<String , Object > profile =new HashMap<>();
        profile.put(KEY_NAME,name);
        profile.put(KEY_EMail,mail);
        profile.put(KEY_Profision,location);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference("users");
        databaseReference.setValue(profile);
        db.collection("user").document("hi").set(profile);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode==PICK_IMAGE || resultCode ==RESULT_OK || data!=null ||data.getData()!= null){
                imageuri =data.getData();
                Picasso.with(getApplicationContext()).load(imageuri).into(imageView);}
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Errer"+e,Toast.LENGTH_SHORT).show();
        }

        }
    private String getFileEXt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    private void uploadData() {
        String name=etname.getText().toString().trim();
        String location=etlocation.getText().toString().trim();
        String mail=etmail.getText().toString();
        String  prof=etprofession.getText().toString().trim();
        String web=etwebsite.getText().toString().trim();
        if(!TextUtils.isEmpty(name)|| !TextUtils.isEmpty(location)|| !TextUtils.isEmpty(mail) ||  !TextUtils.isEmpty(prof) ||
                !TextUtils.isEmpty(web) || imageuri !=null){
            etprog.setVisibility(View.VISIBLE);
            final StorageReference reference=storageReference.child(System.currentTimeMillis()+"."+getFileEXt(imageuri));
            uploadTask=reference.putFile(imageuri);
            Task<Uri> uriTask=uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull  Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if(task.isSuccessful()){
                      throw Objects.requireNonNull(task.getException());
                        //Toast.makeText(CreateProfile.this, "it work", Toast.LENGTH_SHORT).show();
                    }
                    return  reference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull  Task<Uri> task) {
                    if(task.isSuccessful()){
                        Uri downloaduri=task.getResult();
                        Map<String , String > profile =new HashMap<>();
                        profile.put("name",name);
                       profile.put("prof",prof);
                       profile.put("location",location);
                        profile.put("web",web);
                        profile.put("url",downloaduri.toString());
                        profile.put("privacy","Public");
                        member.setName(name);
                      member.setProf(prof);
                       member.setLocation(location);
                        member.setUid(Currentuserid);
                        member.setUrl(downloaduri.toString());

                        databaseReference.child(Currentuserid).setValue(member);
                        databaseReference.push().setValue(profile);
                      databaseReference.child(Currentuserid).setValue(member);
                        documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                etprog.setVisibility(View.INVISIBLE);
                                Toast.makeText(getApplicationContext(),"Profile Created",Toast.LENGTH_LONG).show();
                                Handler handler=new Handler();
                                handler.postDelayed(() -> {
                                    Intent intent=new Intent(CreateProfile.this, Fragment1.class);
                                    startActivity(intent);
                                },2000);
                            }
                        });
                    }
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"please file all fields",Toast.LENGTH_SHORT).show();
        }

    }

    public void test(View view) {
        Intent intent=new Intent(getApplicationContext(), testfire.class);
        startActivity(intent);
    }
}