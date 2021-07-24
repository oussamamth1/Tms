package com.example.tmspro;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class Viewholder_Question extends RecyclerView.ViewHolder {
    ImageView imageView;
        TextView time_result,name_result,question_result;
    public Viewholder_Question(@NonNull  View itemView) {
        super(itemView);
    }
    public void setitem(FragmentActivity activity,String name , String url , String userid, String key, String  question, String privacy, String time){
            imageView =itemView.findViewById(R.id.iv_question_f2);
            time_result=   itemView.findViewById(R.id.time_question_item);
            name_result=itemView.findViewById(R.id.name_question_item);
            question_result=itemView.findViewById(R.id.question_item);
        name_result.setText(name);
        Picasso.get().load(url).into(imageView);
        time_result.setText(time);

        question_result.setText(question);

    }
}
