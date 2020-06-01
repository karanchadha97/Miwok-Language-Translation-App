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

public class FamilyMembersActivity extends AppCompatActivity {

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
        this.setTitle("Family Members");
        ArrayList<word> family = new ArrayList<word>();
        family.add(new word("father", "әpә", R.drawable.family_father, R.raw.family_father));
        family.add(new word("mother", "әṭa", R.drawable.family_mother, R.raw.family_mother));
        family.add(new word("son", "angsi", R.drawable.family_son, R.raw.family_son));
        family.add(new word("daughter", "tune", R.drawable.family_daughter, R.raw.family_daughter));
        family.add(new word("older brother", "taachi", R.drawable.family_older_brother, R.raw.family_older_brother));
        family.add(new word("younger brother", "chalitti", R.drawable.family_younger_brother, R.raw.family_younger_brother));
        family.add(new word("older sister", "teṭe", R.drawable.family_older_sister, R.raw.family_older_sister));
        family.add(new word("younger sister", "kolliti", R.drawable.family_younger_sister, R.raw.family_younger_sister));
        family.add(new word("grandmother", "ama", R.drawable.family_grandmother, R.raw.family_grandmother));
        family.add(new word("grandfather", "paapa", R.drawable.family_grandfather, R.raw.family_grandfather));


        final WordAdapter Adapter = new WordAdapter(this, family, R.color.category_family);

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
                    //  audioManager.r

                    mp = MediaPlayer.create(FamilyMembersActivity.this, obj.getAudioId());
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
