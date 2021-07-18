package com.example.tmspro;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

public class Fragment1 extends Fragment implements  View.OnClickListener{
    @Nullable
            DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    ImageView imageView,imageView2;
    TextView nameet,profitionet,webet,emailet;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        firebaseDatabase=FirebaseDatabase.getInstance();
        databaseReference=firebaseDatabase.getReference().child("Blog");
        databaseReference.keepSynced(true);
        imageView=getActivity().findViewById(R.id.iv_f1);
        //imageView2=getActivity().findViewById(R.id.imageView2);
        nameet=getActivity().findViewById(R.id.tv_name_f1);
        profitionet=getActivity().findViewById(R.id.tv_pro_f1);
        webet=getActivity().findViewById(R.id.tv_web_f1);
        emailet=getActivity().findViewById(R.id.tv_email_f1);
        ImageButton imageButton=getActivity().findViewById(R.id.ib_menu_f1);
        imageButton.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ib_menu_f1:
            Intent intent=new Intent(getActivity(), CreateProfile1.class);
            startActivity(intent);



        }
    }


   public void onStart() {
        super.onStart();
        FirebaseUser   user=FirebaseAuth.getInstance().getCurrentUser();
        String currentuser= user.getUid();
        DocumentReference reference;
       FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();

        reference=firebaseFirestore.collection("user").document(currentuser);
databaseReference.addChildEventListener(new ChildEventListener() {
    @Override
    public void onChildAdded(@NonNull  DataSnapshot snapshot,  String previousChildName) {
       // AllUserMember allUserMember=snapshot.getValue(AllUserMember.class);

      //  nameet.setText(allUserMember.name);
        //imageView.setImageURI(Uri.parse(allUserMember.url));
     //   emailet.setText(allUserMember.email);
       // Picasso.with(getContext()).load(allUserMember.url).into(imageView);


    }

    @Override
    public void onChildChanged(@NonNull  DataSnapshot snapshot, @Nullable  String previousChildName) {

    }

    @Override
    public void onChildRemoved(@NonNull  DataSnapshot snapshot) {

    }

    @Override
    public void onChildMoved(@NonNull  DataSnapshot snapshot, @Nullable String previousChildName) {

    }

    @Override
    public void onCancelled(@NonNull  DatabaseError error) {

    }
});
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull  Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String nameResulta=task.getResult().getString("name");
                    String profResulta=task.getResult().getString("prof");
                    String locationResulta=task.getResult().getString("location");
                    String emailResulta=task.getResult().getString("email");
                    String webResulta=task.getResult().getString("web");
                    String urlResulta=task.getResult().getString("url");
                    //Picasso.get().setLoggingEnabled(true);
                   // Glide.with(getActivity()).set
                   // Glide.with(getContext()).load(urlResulta).into(imageView);
                 // Bitmap bitmap;
                    String url="https://firebasestorage.googleapis.com/v0/b/tmspro-c363a.appspot.com/o/Blogimage%2F1626478110204.png?alt=media&token=6f53f96c-06fd-4e0c-a449-bea061b9f5f6";
                 //Picasso.get().load(urlResulta).into(imageView);
                   // Glide.with(getContext()).load(url).into(imageView);
                   Glide.with(getContext()).load(urlResulta).dontAnimate().into(imageView);

                    Toast.makeText(getContext(), ""+urlResulta, Toast.LENGTH_SHORT).show();

                    nameet.setText(nameResulta);
                    webet.setText(webResulta);
                    profitionet.setText(profResulta);
                    emailet.setText(emailResulta);
                    Picasso.with(getContext()).load(urlResulta).into(imageView);
                  //  Intent intent=new Intent(getActivity(),test.class);
                  //  intent.putExtra("1",urlResulta);
                  //  startActivity(intent);

                }else{
                    startActivity(new Intent(getActivity(),CreateProfile1.class));
                }
            }
        });
    }
}
