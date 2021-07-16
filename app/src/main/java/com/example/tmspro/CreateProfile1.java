package com.example.tmspro;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

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
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class CreateProfile1 extends Activity {
    EditText etname,etlocation,etprofession,etwebsite,etmail;
    ProgressBar etprog;
    Button etbutton,testbutton;
    ImageView imageView;
    Uri imageuri;
    DocumentReference documentReference;
    FirebaseFirestore db;
    private static final int PICK_IMAGE=1;
    private static final String KEY_NAME="name";
    private static final String KEY_pof="prof";
    FirebaseDatabase database;
    DatabaseReference databaseReference;//realtimedb
    StorageReference storageReference;
    UploadTask uploadTask;
   // private FirebaseFirestore db=FirebaseFirestore.getInstance();
    FirebaseAuth auth;
    FirebaseUser  user;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database=FirebaseDatabase.getInstance();
        databaseReference=database.getReference().child("Blog");
        databaseReference.keepSynced(true);
        auth=FirebaseAuth.getInstance();
        db=FirebaseFirestore.getInstance();


        user=auth.getCurrentUser();
        documentReference=db.collection("user").document(auth.getCurrentUser().getUid());
        storageReference= FirebaseStorage.getInstance().getReference();

        setContentView(R.layout.activity_create_profile);
        imageView=findViewById(R.id.iv_cp);
      //  etbutton=findViewById(R.id.btn_cp);
        etname=findViewById(R.id.et_name_cp);
        etlocation=findViewById(R.id.et_Location_cp);
        etprofession=findViewById(R.id.et_Profession_cp);
        etmail=findViewById(R.id.et_Email_cp);
        etwebsite=findViewById(R.id.et_WebSite_cp);
        testbutton=findViewById(R.id.testbut);
        etprog=findViewById(R.id.porg_cp);
       testbutton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
              // databaseReference.setValue(System.currentTimeMillis()+"."+getFileEXt(imageuri));

               String name=etname.getText().toString().trim();
               String location=etlocation.getText().toString().trim();
               String mail=etmail.getText().toString();
               String  prof=etprofession.getText().toString().trim();
               String web=etwebsite.getText().toString().trim();
              // String =etmail.getText().toString().trim();
               AllUserMember allUserMember=new AllUserMember();
               if(!TextUtils.isEmpty(name)&& !TextUtils.isEmpty(location)&& !TextUtils.isEmpty(mail) &&  !TextUtils.isEmpty(prof) &&
                       !TextUtils.isEmpty(web) && imageuri !=null ){
                   etprog.setVisibility(View.VISIBLE);
                   StorageReference filepath=storageReference.child("Blogimage").child(imageuri.getLastPathSegment());
                   filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           Uri downloduri=taskSnapshot.getUploadSessionUri();

                           DatabaseReference newpost=databaseReference.push();
                           Map<String , String > profile =new HashMap<>();
                           profile.put("name",name);
                           profile.put("prof",prof);
                           profile.put("location",location);
                           profile.put("email",mail);
                           profile.put("web",web);
                           profile.put("uid",user.getUid());
                          profile.put("url",downloduri.toString());
                          allUserMember.setName(name);
                          allUserMember.setUid(auth.getUid());

                           allUserMember.setProf(prof);
                           allUserMember.setLocation(location);

                           allUserMember.setUrl(downloduri.toString());
                           newpost.setValue(profile);
                           newpost.child(user.getUid()).setValue(allUserMember);
                           documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void unused) {
                                   Toast.makeText(CreateProfile1.this, "it work", Toast.LENGTH_SHORT).show();
                               }
                           });
                           Toast.makeText(getApplicationContext(),"Profile Created",Toast.LENGTH_LONG).show();
                           Handler handler=new Handler();
                           handler.postDelayed(() -> {
                               Intent intent=new Intent(CreateProfile1.this, Fragment1.class);
                               startActivity(intent);
                           },2000);
etprog.setVisibility(View.INVISIBLE);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull  Exception e) {

                       }
                   });
                   


               }else{
                   Toast.makeText(CreateProfile1.this, "fail the fildes", Toast.LENGTH_SHORT).show();
               }
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
    private String getFileEXt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if(requestCode==PICK_IMAGE && resultCode ==RESULT_OK){
                imageuri =data.getData();
                Picasso.get().load(imageuri).into(imageView);}
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),"Errer"+e,Toast.LENGTH_SHORT).show();
        }

    }

    public void savenote(View view){
        String name=etname.getText().toString();
        String prof=etprofession.getText().toString();
        Map<String,Object> note=new HashMap<>();
        note.put(KEY_NAME,name);
        note.put(KEY_pof,prof);


    }


}