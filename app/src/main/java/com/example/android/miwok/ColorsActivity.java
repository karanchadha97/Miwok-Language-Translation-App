package com.example.android.miwok;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ColorsActivity extends AppCompatActivity {

    MediaPlayer mp;
    AudioManager audioManager;
    AudioManager.OnAudioFocusChangeListener afChangeListener =
            new AudioManager.OnAudioFocusChangeListener() {
                public void onAudioFocusChange(int focusChange) {
                    if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                        releaseMediaPlayer();
                    }
                    else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                        mp.pause();
                        mp.seekTo(0);
                    }

                    else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                        mp.start();
                    }
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_colors);
        this.setTitle("Colors");
        ArrayList<word> colors = new ArrayList<word>();
        colors.add(new word("red", "weṭeṭṭi", R.drawable.color_red, R.raw.color_red));
        colors.add(new word("green", "chokokki", R.drawable.color_green, R.raw.color_green));
        colors.add(new word("brown", "ṭakaakki", R.drawable.color_brown, R.raw.color_brown));
        colors.add(new word("gray", "ṭopoppi", R.drawable.color_gray, R.raw.color_gray));
        colors.add(new word("black", "kululli", R.drawable.color_black, R.raw.color_black));
        colors.add(new word("white", "kelelli", R.drawable.color_white, R.raw.color_white));
        colors.add(new word("dusty yellow", "ṭopiisә", R.drawable.color_dusty_yellow, R.raw.color_dusty_yellow));
        colors.add(new word("mustard yellow", "chiwiiṭә", R.drawable.color_mustard_yellow, R.raw.color_mustard_yellow));

        final WordAdapter Adapter = new WordAdapter(this, colors, R.color.category_colors);

        ListView listView = (ListView) findViewById(R.id.list1);
        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                word obj = Adapter.getItem(position);

                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    //  audioManager.r

                    mp = MediaPlayer.create(ColorsActivity.this, obj.getAudioId());
                    mp.start();
                    mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            releaseMediaPlayer();
                        }
                    });
                }
            }
        });
    }
        private void releaseMediaPlayer() {
            if (mp != null) {
                mp.release();
                mp = null;
                audioManager.abandonAudioFocus(afChangeListener);
            }
        }
    @Override
    protected void onStop()
    {
        super.onStop();
        releaseMediaPlayer();
    }

}
