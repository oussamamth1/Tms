package com.example.tmspro;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class Fragment1 extends Fragment implements  View.OnClickListener{
    @Nullable
    FirebaseAuth auth;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable  Bundle savedInstanceState) {
        View view  =inflater.inflate(R.layout.fragment1,container,false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
}
