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

public class PhrasesActivity extends AppCompatActivity {

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
        this.setTitle("Phrases");
        ArrayList<word> phrases = new ArrayList<word>();
        phrases.add(new word("Where are you going?", "minto wuksus", R.raw.phrase_where_are_you_going));
        phrases.add(new word("What is your name?", "tinnә oyaase'nә", R.raw.phrase_what_is_your_name));
        phrases.add(new word("My name is...", "oyaaset...", R.raw.phrase_my_name_is));
        phrases.add(new word("How are you feeling?", "michәksәs?", R.raw.phrase_how_are_you_feeling));
        phrases.add(new word("I’m feeling good.", "kuchi achit", R.raw.phrase_im_feeling_good));
        phrases.add(new word("Are you coming?", "әәnәs'aa?", R.raw.phrase_are_you_coming));
        phrases.add(new word("Yes, I’m coming.", "hәә’ әәnәm", R.raw.phrase_yes_im_coming));
        phrases.add(new word("I’m coming.", "әәnәm", R.raw.phrase_im_coming));
        phrases.add(new word("Let’s go.", "yoowutis", R.raw.phrase_lets_go));
        phrases.add(new word("Come here.", "әnni'nem", R.raw.phrase_come_here));

        final WordAdapter Adapter = new WordAdapter(this, phrases, R.color.category_phrases);

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

                    mp = MediaPlayer.create(PhrasesActivity.this, obj.getAudioId());
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
