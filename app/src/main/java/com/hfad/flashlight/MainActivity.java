package com.hfad.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView burnHere;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        burnHere = (ImageView)findViewById(R.id.burnHere);
        burnHere.setBackgroundResource(R.drawable.flame);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) burnHere.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();

    }

}