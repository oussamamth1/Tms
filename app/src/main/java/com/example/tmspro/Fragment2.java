package com.example.tmspro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment2 extends Fragment implements View.OnClickListener{
    @Nullable
    CircleImageView circlefloot;
    CircleImageView circleImagef2;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference reference;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment2,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        circleImagef2=getActivity().findViewById(R.id.iv_f2);
        circlefloot=getActivity().findViewById(R.id.floot_f2);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String cureentuserId=user.getUid();
        reference=db.collection("user").document(cureentuserId);
        circlefloot.setOnClickListener(this);
        circleImagef2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.floot_f2:
                ///Intent intent=new Intent(getActivity(), CreateProfile1.class);
                // startActivity(intent);
                Intent intent1=new Intent(getActivity(), AskActivity.class);
                startActivity(intent1);
                break;
            case R.id.iv_f2:

                break;



        }

    }

    @Override
    public void onStart() {
        super.onStart();
        reference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                    String url=task.getResult().getString("url");
                    Picasso.with(getActivity()).load(url).into(circleImagef2);
                }else{
                    Toast.makeText(getActivity(), "errer to lode Image", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
