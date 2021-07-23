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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
//QuestionMember  questionMember=new QuestionMember();

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
        recyclerView=getActivity().findViewById(R.id.rv_f2);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseReference=firebaseDatabase.getReference("All Question");
        FirebaseRecyclerOptions<QuestionMember> options;
        options = new FirebaseRecyclerOptions.Builder<QuestionMember>().setQuery(databaseReference,QuestionMember.class).build()
                ;
        FirebaseRecyclerAdapter<QuestionMember,Viewholder_Question>firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<QuestionMember, Viewholder_Question>(options){
            @Override
            protected void onBindViewHolder(@NonNull Viewholder_Question holder, int position, @NonNull  QuestionMember model) {
               holder.setitem(getActivity(),model.getName(),model.getUrl(),model.getUserid(), model.getKey(),model.getQuestion(),model.getPrivacy(),model.getTime());
            }

            @NonNull

            @Override
            public Viewholder_Question onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


                View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.guestion_item,parent,false);
                return  new Viewholder_Question(view);

            }
        };
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);

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
                    Picasso.get().load(url).into(circleImagef2);
                }else{
                    Toast.makeText(getActivity(), "errer to lode Image", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}
