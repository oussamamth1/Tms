package com.example.tmspro;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class bottun_shit_menu extends BottomSheetDialogFragment {
    @Nullable
    FirebaseFirestore db=FirebaseFirestore.getInstance();
    DocumentReference reference;
    CardView cv_delet,cv_logout,cv_privcy;
    TextView cv_delett,cv_logoutt,cv_privcyt;
    FirebaseAuth mAuth;
    @Override
    public View onCreateView(@NonNull  LayoutInflater inflater, @Nullable  ViewGroup container, @Nullable  Bundle savedInstanceState) {
      View view=getLayoutInflater().inflate(R.layout.bottoun_sheet_menu,null);
      cv_delet=view.findViewById(R.id.cv_delet);
      cv_logout=view.findViewById(R.id.cv_Logout);
      cv_privcy=view.findViewById(R.id.cv_Privec);
        cv_delett=view.findViewById(R.id.cv_delettxt);
        cv_logoutt=view.findViewById(R.id.cv_logoutttxt);
        cv_privcyt=view.findViewById(R.id.cv_privcyttxt);
      mAuth=FirebaseAuth.getInstance();
        FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
        String currentuser=user.getUid();
        reference=db.collection("user").document(currentuser);
        cv_logoutt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
        cv_privcyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),Privecy.class));

            }
        });
        cv_delett.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delet();
            }
        });
      return view;
    }

    private void delet() {
    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
    builder.setTitle("Delete").setMessage("Are You Sur to Delet").setPositiveButton("yes", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            reference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(getActivity(), "Deleted succ", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {
                    Toast.makeText(getActivity(), "Errer To delet", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }).setNegativeButton("No", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();
        }
    });
        builder.create();
        builder.show();
    }

    private void Logout() {
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setTitle("Logout").setMessage("Are Sur To LogOut??").setPositiveButton("logOut", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                startActivity(new Intent(getActivity(),LoginActivity.class));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Hi", Toast.LENGTH_SHORT).show();

            }
        });
        builder.create();
        builder.show();
    }
}
