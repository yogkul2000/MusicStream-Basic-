package com.yogeshkulkarni.musicstream;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyPlayService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener,
        MediaPlayer.OnErrorListener,MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener
{

    private MediaPlayer mediaPlayer;
    String link;
    private  MusicStoppedListener musicStoppedListener;




    public MyPlayService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();


        mediaPlayer = new MediaPlayer();

        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        link = intent.getStringExtra("AudioLink");
        mediaPlayer.reset();
        musicStoppedListener = (MusicStoppedListener) ApplicationClass.context;

        if(!mediaPlayer.isPlaying())
        {
            try{

                mediaPlayer.setDataSource(link);
                mediaPlayer.prepareAsync();

            }catch (Exception e)
            {
                Toast.makeText(this,"Error: " + e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }



        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();


        if(mediaPlayer!=null)
        {

            if(mediaPlayer.isPlaying())
            {

                mediaPlayer.stop();

            }
            mediaPlayer.release();

        }


    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {


        if (mp.isPlaying())
        {
            mp.stop();

        }
        musicStoppedListener.onMusicStopped();
        stopSelf();

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {

        switch (what)
        {

            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this,"MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK",Toast.LENGTH_LONG).show();
                break;


            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this,"MEDIA_ERROR_SERVER_DIED",Toast.LENGTH_LONG).show();
                break;

            case  MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this,"MEDIA_ERROR_UNKNOWN",Toast.LENGTH_LONG).show();
                break;
        }


        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp)
    {

        if(!mp.isPlaying())
        {
            mp.start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
