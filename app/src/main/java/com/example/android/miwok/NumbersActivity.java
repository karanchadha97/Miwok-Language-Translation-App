package com.example.android.miwok;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RemoteControlClient;
import android.media.RemoteController;
import android.media.session.MediaSession;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NumbersActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_numbers);

        final ArrayList<word> words = new ArrayList<word>();
        words.add(new word("one", "lutti", R.drawable.number_one, R.raw.number_one));
        words.add(new word("two", "otiiko", R.drawable.number_two, R.raw.number_two));
        words.add(new word("three", "tolookosu", R.drawable.number_three, R.raw.number_three));
        words.add(new word("four", "oyyisa", R.drawable.number_four, R.raw.number_four));
        words.add(new word("five", "massokka", R.drawable.number_five, R.raw.number_five));
        words.add(new word("six", "temmokka", R.drawable.number_six, R.raw.number_six));
        words.add(new word("seven", "kenekaku", R.drawable.number_seven, R.raw.number_seven));
        words.add(new word("eight", "kawinta", R.drawable.number_eight, R.raw.number_eight));
        words.add(new word("nine", "wo'e", R.drawable.number_nine, R.raw.number_nine));
        words.add(new word("ten", "na'aacha", R.drawable.number_ten, R.raw.number_ten));

        final WordAdapter Adapter = new WordAdapter(this, words, R.color.category_numbers);

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(Adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                releaseMediaPlayer();
                word obj = Adapter.getItem(position);

                audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
                int result = audioManager.requestAudioFocus(afChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN);
                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    mp = MediaPlayer.create(NumbersActivity.this, obj.getAudioId());
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
