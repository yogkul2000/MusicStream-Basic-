package com.yogeshkulkarni.musicstream;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements MusicStoppedListener {

    ImageView pt;
    String audiolink = "https://dl.dropbox.com/s/8atz9av3qxmx0k8/Imagine%20Dragons%20-%20Bad%20Liar%20%28Audio%29.mp3?dl=0";
    boolean musicplaying=false;
    Intent  serviceintent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pt = findViewById(R.id.pt);
        pt.setImageResource(R.drawable.play);
        serviceintent = new Intent(this,MyPlayService.class);
        ApplicationClass.context = (Context) MainActivity.this;

        pt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (!musicplaying)
                {

                    playAudio();
                    pt.setImageResource(R.drawable.stop);
                    musicplaying = true;

                }
                else
                    {
                        stopPlayService();
                        pt.setImageResource(R.drawable.play);
                        musicplaying = false;
                    }

            }
        });
    }

    private void playAudio() {
        serviceintent.putExtra("AudioLink",audiolink);

        try
        {
            startService(serviceintent);

        }catch (SecurityException e)
        {
            Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    private void stopPlayService()
    {
        try{

            stopService(serviceintent);

        }catch (SecurityException e)
        {
            Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }


    @Override
    public void onMusicStopped()

    {
        pt.setImageResource(R.drawable.play);
        musicplaying = false;
    }
}
