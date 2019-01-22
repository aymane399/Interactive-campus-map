package com.example.richard.dev01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.view.ScaleGestureDetector;
import android.view.MotionEvent;

import com.bumptech.glide.Glide;


public class AffichageImage extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;

    private int res_chosen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_affichage);


        /// Change la ressource en fonction de l'image demandée ///

        //Vers la mairie
        if (MapsActivity.id_batiment == 999) {
            res_chosen = R.drawable.horaire_mairie_plouz_techno_1_2;
        }

        //Vers le Fort
        else if (MapsActivity.id_batiment == 998) {
            res_chosen = R.drawable.horaire_fort_monba_techno_2_2;


        }

        mImageView = findViewById(R.id.imageView);

        Glide.with(this)
                .load(res_chosen) // Uri of the picture
                .into(mImageView);





        mScaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        mScaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector){
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(1.0f,
                    Math.min(mScaleFactor, 5.0f));
            mImageView.setScaleX(mScaleFactor);
            mImageView.setScaleY(mScaleFactor);
            return true;
        }
    }

}
