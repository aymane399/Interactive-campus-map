package com.example.richard.dev01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.GradientDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityLoadingScreen extends AppCompatActivity {
    private TextView textloading;
    private ImageView imageloading;
    public static boolean mapready;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_loading_screen);
        super.onCreate(savedInstanceState);

        textloading = (TextView) findViewById(R.id.textView);
        imageloading = (ImageView) findViewById(R.id.imageView4);
        Animation anim = AnimationUtils.loadAnimation(this,R.anim.loadingscreen);
        textloading.startAnimation(anim);
        imageloading.startAnimation(anim);



        while (mapready == false){
            return;
        }


        finish();

    }
}
