package com.example.tmspro;

import android.app.Activity;
import android.app.ProgressDialog;
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
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
        storageReference= FirebaseStorage.getInstance().getReference("profile image");

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
                   final ProgressDialog pd=new ProgressDialog(CreateProfile1.this);
                   pd.setTitle("create Profile for "+name);
                   pd.setIcon(R.drawable.ic_baseline_sync_24);

                   pd.show();
                   pd.setCanceledOnTouchOutside(false);

                  // pd.setInverseBackgroundForced(true);
                   final StorageReference reference=storageReference.child(System.currentTimeMillis()+ "." +getFileEXt(imageuri));
                   StorageReference filepath=storageReference.child(System.currentTimeMillis()+ "." +getFileEXt(imageuri));
                   //uploadTask=filepath.putFile(imageuri);

                   filepath.putFile(imageuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                         // String downlod = taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();
                           if (taskSnapshot.getMetadata() != null) {
                               if (taskSnapshot.getMetadata().getReference() != null) {
                                   Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                                   result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                       @Override
                                       public void onSuccess(Uri uri) {
                                           String imageUrl = uri.toString();
                                           DatabaseReference newpost=databaseReference.push();
                                           Map<String , String > profile =new HashMap<>();
                                           profile.put("name",name);
                                           profile.put("prof",prof);
                                           profile.put("location",location);
                                           profile.put("email",mail);
                                           profile.put("web",web);
                                           profile.put("uid",user.getUid());
                                           profile.put("url",imageUrl);
                                           profile.put("privacy","Public");

                                           allUserMember.setName(name);
                                           allUserMember.setUid(auth.getUid());

                                           allUserMember.setProf(prof);
                                           allUserMember.setLocation(location);

                                           allUserMember.setUrl(imageUrl);
                                           newpost.setValue(profile);
                                           newpost.child(user.getUid()).setValue(allUserMember);
                                           documentReference.set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                                               @Override
                                               public void onSuccess(Void unused) {

                                                   Snackbar.make(findViewById(android.R.id.content),"Image uplode",Snackbar.LENGTH_LONG).show();
                                                   pd.dismiss();
                                                   Toast.makeText(CreateProfile1.this, "it work", Toast.LENGTH_SHORT).show();
                                               }
                                           });
                                           //createNewPost(imageUrl);
                                       }
                                   });
                               }
                           }


                          // String profileImageUrl = filepath.getDownloadUrl().toString();
                         //  Task<Uri> downloduri=taskSnapshot.getStorage().getDownloadUrl();

                           //   String downloduri=taskSnapshot.getUploadSessionUri().toString();



                           Toast.makeText(getApplicationContext(),"Profile Created",Toast.LENGTH_LONG).show();
                           Handler handler=new Handler();
                           handler.postDelayed(() -> {
                               Intent intent=new Intent(CreateProfile1.this, MainActivity.class);
                               startActivity(intent);
                           },2000);
etprog.setVisibility(View.INVISIBLE);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull  Exception e) {

                       }
                   }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                       @Override
                       public void onProgress(@NonNull  UploadTask.TaskSnapshot snapshot) {
                           double per=(100.00* snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                           pd.setMessage("Progress : "+(int)per +"%");
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
  /*  private String getFileEXt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }*/

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
    private String getFileEXt(Uri uri){
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }

   /* public void savenote(View view){
        String name=etname.getText().toString();
        String prof=etprofession.getText().toString();
        Map<String,Object> note=new HashMap<>();
        note.put(KEY_NAME,name);
        note.put(KEY_pof,prof);


    }*/


}
