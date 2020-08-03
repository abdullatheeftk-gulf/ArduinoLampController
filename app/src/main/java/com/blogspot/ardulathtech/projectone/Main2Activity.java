package com.blogspot.ardulathtech.projectone;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
//import android.util.Log;
//import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import uk.co.senab.photoview.PhotoViewAttacher;

public class Main2Activity extends AppCompatActivity {

    PhotoViewAttacher photoViewAttacher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            ImageView imageView;

            setContentView(R.layout.activity_main2);
            imageView = findViewById(R.id.imageView2);
            photoViewAttacher = new PhotoViewAttacher(imageView);

            photoViewAttacher.update();
            photoViewAttacher.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    //Log.i("hi","കഗഹപ");
                    //Toast.makeText(Main2Activity.this, "Downloading....", Toast.LENGTH_LONG).show();
                    return false;
                }
            });
        }catch (Exception e){
            Toast.makeText(Main2Activity.this,e.getMessage(),Toast.LENGTH_LONG).show();
            //Log.i("error",e.getMessage());
        }


    }
}
