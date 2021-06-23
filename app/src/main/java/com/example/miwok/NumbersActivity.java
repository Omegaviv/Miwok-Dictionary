package com.example.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.UserDictionary;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class NumbersActivity extends AppCompatActivity {

    private MediaPlayer mMediaPlayer;

    private AudioManager mAudioManager;

    AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListner = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if(focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){

                mMediaPlayer.pause();
                mMediaPlayer.seekTo(0);
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_GAIN){
                mMediaPlayer.start();
            }
            else if(focusChange == AudioManager.AUDIOFOCUS_LOSS){
                releaseMediaPlayer();
            }
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_numbers);

        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);


        final ArrayList<Word> numbers = new ArrayList<Word>();
        numbers.add(new Word("one","lutti",R.drawable.number_one ,R.raw.number_one));
        numbers.add(new Word("two","otiiko", R.drawable.number_two,R.raw.number_two));
        numbers.add(new Word("three","tolookosu", R.drawable.number_three,R.raw.number_three));
        numbers.add(new Word("four","oyyisa", R.drawable.number_four,R.raw.number_four));
        numbers.add(new Word("five","massokka", R.drawable.number_five,R.raw.number_five));
        numbers.add(new Word("six","temmokka", R.drawable.number_six,R.raw.number_six));
        numbers.add(new Word("seven","kenekaku", R.drawable.number_seven,R.raw.number_seven));
        numbers.add(new Word("eight","kawinta", R.drawable.number_eight,R.raw.number_eight));
        numbers.add(new Word("nine","wo'e", R.drawable.number_nine,R.raw.number_nine));
        numbers.add(new Word("ten","na'aacha",R.drawable.number_ten,R.raw.number_ten));

        WordAdapter adapters = new WordAdapter(this, numbers, R.color.category_numbers);

        ListView    listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapters);

       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
              // Toast.makeText(NumbersActivity.this, "clicked", Toast.LENGTH_SHORT).show();
               Word word = numbers.get(position);

               releaseMediaPlayer();

               int result = mAudioManager.requestAudioFocus(mOnAudioFocusChangeListner ,
                       AudioManager.STREAM_MUSIC,
                       AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
               if(result==AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                   //mAudioManager.registerMediaButtonEventReceiver(RemoteControlReciver);
               mMediaPlayer = MediaPlayer.create(NumbersActivity.this, word.getmAudioResourceId());
               mMediaPlayer.start();

               mMediaPlayer.setOnCompletionListener(mCompletionListener);}
           }
       });
     }

    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mMediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mMediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mMediaPlayer = null;

            mAudioManager.abandonAudioFocus(mOnAudioFocusChangeListner);
        }
    }
}