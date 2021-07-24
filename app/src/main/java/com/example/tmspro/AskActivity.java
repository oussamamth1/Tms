package com.example.tmspro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class AskActivity extends AppCompatActivity {
EditText editText;
Button buttonAsk;
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference documentReference ;
    FirebaseDatabase database=FirebaseDatabase.getInstance();
    DatabaseReference AllQuestion,userQuestion;
    QuestionMember member;
    String name;
    String url;
    String uid;
    String privacy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask);
        editText=findViewById(R.id.ask_question);
        buttonAsk=findViewById(R.id.btn_ask);
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String cureentid=user.getUid();
        documentReference=db.collection("user").document(cureentid);
        AllQuestion=database.getReference("All Question");
        userQuestion=database.getReference("user Question").child(cureentid);
        member=new QuestionMember();
        buttonAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String question=editText.getText().toString();
                Calendar cdate=Calendar.getInstance();
                SimpleDateFormat currentdate=new SimpleDateFormat("dd-MMMM-yyyy");
                final  String savedate=currentdate.format(cdate.getTime());
                Calendar calendartime=Calendar.getInstance();
                SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss");
                final String saveTime=currentTime.format(calendartime.getTime());
                String Time=savedate+ ":"+ saveTime;
                if(question !=null){
                    member.setQuestion(question);
                    member.setPrivacy(privacy);
                    member.setUrl(url);
                    member.setUserid(uid);
                    member.setTime(Time);
                    member.setName(name);
                    String id=userQuestion.push().getKey();
                    userQuestion.child(id) .setValue(member);
                    String child=AllQuestion.push().getKey();
                    member.setKey(id);
                    AllQuestion.child(child).setValue(member);
                    Toast.makeText(AskActivity.this, "add question sucsses", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(AskActivity.this, "Please Ask Question", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    protected void onStart() {
        super.onStart();

        documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.getResult().exists()){
                     name=task.getResult().getString("name");
                     privacy=task.getResult().getString("privacy");
                     uid=task.getResult().getString("uid");
                    url=task.getResult().getString("url");




                }else {
                    Toast.makeText(AskActivity.this, " errer", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}