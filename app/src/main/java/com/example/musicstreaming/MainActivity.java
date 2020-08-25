package com.example.musicstreaming;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements Musicstopped {
    ImageView play;

    String audiolink="https://dl.dropbox.com/s/26hfn6mxh6ga80g/bad-liar-imagine-dragons.mp3?dl=0";
    boolean musicplaying = false;
    Intent serviceintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        play=findViewById(R.id.play);


        play.setBackgroundResource(R.drawable.play);

        serviceintent  = new Intent(MainActivity.this,MyService.class);

        ApplicationClass.context  =(Context) MainActivity.this;
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!musicplaying)
                {
                    //userdefined function
                    playAudio();
                    play.setImageResource(R.drawable.pause);

                }
                else {
                    //user defined
                    stopPlayservice();
                    play.setImageResource(R.drawable.play);
                }
            }
        });
    }

    private void stopPlayservice() {
        try{
            stopService(serviceintent);
        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void playAudio() {
        serviceintent.putExtra("audiolink",audiolink);
        try{
            startService(serviceintent);
        }
        catch (Exception e)
        {
            Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void OnMusicstopped() {
        play.setImageResource(R.drawable.play);
        musicplaying = false;
    }
}