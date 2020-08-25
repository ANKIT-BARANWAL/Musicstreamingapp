package com.example.musicstreaming;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;

public class MyService extends Service implements  MediaPlayer.OnCompletionListener,MediaPlayer.OnPreparedListener,MediaPlayer.OnErrorListener,MediaPlayer.OnSeekCompleteListener,MediaPlayer.OnInfoListener,MediaPlayer.OnBufferingUpdateListener
{
    private MediaPlayer mediaplayer;
    String link;
    private Musicstopped musicstoppedlistener;
    Context context;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mediaplayer = new MediaPlayer();
        mediaplayer.setOnCompletionListener(this);
        mediaplayer.setOnPreparedListener(this);
        mediaplayer.setOnErrorListener(this);
        mediaplayer.setOnSeekCompleteListener(this);
        mediaplayer.setOnInfoListener(this);
        mediaplayer.setOnBufferingUpdateListener(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //here we get our audio link
        link = intent.getStringExtra("audiolink");
        mediaplayer.reset();
        musicstoppedlistener  = (Musicstopped) ApplicationClass.context;
        if(!mediaplayer.isPlaying())
        {
            try{
                mediaplayer.setDataSource(link);
                mediaplayer.prepareAsync();
            }
            catch (Exception e)
            {
                Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }

        //return super.onStartCommand(intent, flags, startId);
        return START_STICKY;

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //here we release our resources from memory after player is played
        if(mediaplayer != null)
        {
            if(mediaplayer.isPlaying())
            {
                mediaplayer.stop();
            }
            mediaplayer.release();

        }
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mp.isPlaying())
        {
            mp.stop();
        }
        musicstoppedlistener.OnMusicstopped();
        stopSelf();
        //here we play one song so when it finishes ,player is stopped
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what)
        {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Toast.makeText(this, "MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK", Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Toast.makeText(this, "MEDIA_ERROR_SERVER_DIED", Toast.LENGTH_SHORT).show();
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Toast.makeText(this, "MEDIA_ERROR_UNKNOWN", Toast.LENGTH_SHORT).show();
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if(!mp.isPlaying())
        {
            mp.start();
        }
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

}
