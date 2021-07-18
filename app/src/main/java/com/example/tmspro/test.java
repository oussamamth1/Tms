package com.example.tmspro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class test extends AppCompatActivity {
ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        image=findViewById(R.id.imageView);



}

    @Override
    protected void onStart() {
        super.onStart();

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

        image.setImageBitmap(bmp );
        Toast.makeText(getApplicationContext(), ""+image, Toast.LENGTH_SHORT).show();
    }
}