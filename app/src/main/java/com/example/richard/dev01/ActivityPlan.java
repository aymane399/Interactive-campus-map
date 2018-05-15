package com.example.richard.dev01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.view.ScaleGestureDetector;
import android.view.MotionEvent;



public class ActivityPlan extends AppCompatActivity {

    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView mImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        /// Change le layout en fonction de la carte demandée ///
        if (MapsActivity.id_batiment == 3) {
            setContentView(R.layout.activity_plan);
        }

        else if (MapsActivity.id_batiment == 1) {
            setContentView(R.layout.activity_plani1);
        }

        mImageView= findViewById(R.id.imageView);
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
