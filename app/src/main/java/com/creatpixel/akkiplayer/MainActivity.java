package com.creatpixel.akkiplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.DrmInitData;
import android.media.MediaPlayer;
import android.media.TimedMetaData;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.MediaController;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    MediaPlayer player;
    AudioManager audio;
    public void play(View view){
        player.start();
    }
    public void pause(View view){
        player.pause();
    }
    public void stop(View view){
        player.stop();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        player = MediaPlayer.create(this, R.raw.music);

        audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVol = audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int curVol = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        SeekBar seekVol = findViewById(R.id.volumeControl);
        seekVol.setMax(maxVol);
        seekVol.setProgress(curVol);

        seekVol.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this,
                        "Ahhh, You touch me", Toast.LENGTH_SHORT).show();
                ConstraintLayout constraintLayout = findViewById(R.id.backround);
                constraintLayout.setBackgroundColor(Color.RED);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(MainActivity.this,
                        "Heyyy, touch again naahhh!!", Toast.LENGTH_SHORT).show();
                ConstraintLayout constraintLayout = findViewById(R.id.backround);
                constraintLayout.setBackgroundColor(Color.WHITE);
            }
        });

        //Now song code for progress change seekbar according to song progress.
        final SeekBar seekProgress = findViewById(R.id.musicProgress);
        seekProgress.setMax(player.getDuration());

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                seekProgress.setProgress(player.getCurrentPosition());
            }
        }, 0, 900);

        seekProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    player.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
