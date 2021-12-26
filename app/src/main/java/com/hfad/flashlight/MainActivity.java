package com.hfad.flashlight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageManager;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView burnHere;
    Switch switch2;
    ImageButton push;
    FlashClass flashClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Load the ImageView that will host the animation and
        // set its background to our AnimationDrawable XML resource.
        burnHere = (ImageView) findViewById(R.id.burnHere);
        burnHere.setBackgroundResource(R.drawable.flame);

        // Get the background, which has been compiled to an AnimationDrawable object.
        AnimationDrawable frameAnimation = (AnimationDrawable) burnHere.getBackground();

        // Start the animation (looped playback by default).
        frameAnimation.start();

        switch2 = (Switch) findViewById(R.id.switch2);
        push = (ImageButton) findViewById(R.id.push);

        // startLight, endLight, startStrobe, endStrobe, isStrobeOn
        init();

    }

    public void init() {
        boolean isCameraFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        flashClass = new FlashClass(this);
        switch2.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                        if (isCameraFlash) {
                            if(switch2.isChecked()){
                                burnHere.setVisibility(View.VISIBLE);
                                push.setVisibility(View.VISIBLE);
                                flashClass.startLight();
                            } else {
                                burnHere.setVisibility(View.GONE);
                                push.setVisibility(View.GONE);
                                flashClass.endLight();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,
                                    "No flash available on your device",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
        push.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flashClass.isStrobeOn()){
                    flashClass.startStrobe();
                } else {
                    flashClass.endStrobe();
                }
            }
        });
    }

}