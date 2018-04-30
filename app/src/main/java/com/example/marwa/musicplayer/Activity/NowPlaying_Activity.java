package com.example.marwa.musicplayer.Activity;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marwa.musicplayer.Adapter.SongAdapter;
import com.example.marwa.musicplayer.R;

public class NowPlaying_Activity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    /**
     * Declares UI elements.
     */
    private TextView songName;
    private TextView singerName;
    private ImageView singerPhoto;
    private ImageButton play;
    private ImageButton skip;
    private ImageButton next;
    private SeekBar seekBar;

    /**
     * Handles playback of all the sound files.
     */
    private MediaPlayer mediaPlayer;

    /**
     * Handles audio focus when playing a sound file.
     */
    private AudioManager audioManager;

    /**
     * sets the start time of the song.
     */
    private double startTime = 0;

    /**
     * sets the final time of the song.
     */
    private double finalTime = 0;

    /**
     * Declares and initializes Handler object.
     */
    private Handler myHandler = new Handler();

    /**
     * Declares Runnable object.
     */
    Runnable UpdateSongTime;

    /**
     * Jumps forward Five Seconds.
     */
    private int forwardTime = 5000;

    /**
     * Jumps forward Five Seconds.
     */
    private int backwardTime = 5000;


    /**
     *  Checks if it first time to play.
     */
    public static int oneTimeOnly = 0;

    /**
     *  Checks if it a new song.
     */
    private boolean isNewSong = true;

    /**
     * Stores values inside these variables
     */
    private String singer;


    /**
     * This listener gets triggered whenever the audio focus changes
     * (i.e., we gain or lose audio focus because of another app or device).
     */
    private AudioManager.OnAudioFocusChangeListener mOnAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT ||
                    focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                // The AUDIOFOCUS_LOSS_TRANSIENT case means that we've lost audio focus for a
                // short amount of time. The AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK case means that
                // our app is allowed to continue playing sound but at a lower volume. We'll treat
                // both cases the same way because our app is playing short sound files.

                // Pause playback
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                // The AUDIOFOCUS_GAIN case means we have regained focus and can resume playback.
                mediaPlayer.start();
            } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                // The AUDIOFOCUS_LOSS case means we've lost audio focus and
                // Stop playback and clean up resources
                releaseMediaPlayer();
            }
        }
    };

    /**
     * This listener gets triggered when the {@link MediaPlayer} has completed
     * playing the audio file.
     */
    private MediaPlayer.OnCompletionListener mCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            // Reset Player
            play.setImageResource(R.drawable.ic_play_arrow_black);
            startTime = 0;
            finalTime = 0;
            seekBar.setProgress(0);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nowplaying);

        // Create and setup the {@link AudioManager} to request audio focus.
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        // Initialize all UI elements.
        initializeAllUiElements();

        // Get the name of singer, song and audio ID
        Intent intent = getIntent();
        singer = intent.getStringExtra(SongAdapter.SINGER);
        singerName.setText(singer);
        String song = intent.getStringExtra(SongAdapter.SONG);
        songName.setText(song);
        int audioID = intent.getIntExtra(SongAdapter.AUDIO, 0);

        // Request audio focus so in order to play the audio file. The app needs to play a
        // short audio file, so we will request audio focus with a short amount of time
        // with AUDIOFOCUS_GAIN_TRANSIENT.
        int result = audioManager.requestAudioFocus(mOnAudioFocusChangeListener,
                AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // We have audio focus now.

            // Create and setup the {@link MediaPlayer} for the audio resource associated
            // with the current song
            mediaPlayer = MediaPlayer.create(this, audioID);

            // Setup a listener on the media player, so that we can stop and release the
            // media player once the sound has finished playing.
            mediaPlayer.setOnCompletionListener(mCompletionListener);

        }

        // Find the the singer's photo.
        findPhotoOfSinger();


        // Play Button
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    play.setImageResource(R.drawable.ic_play_arrow_black);
                    // Pause the song
                    mediaPlayer.pause();
                } else {
                    play.setImageResource(R.drawable.ic_pause_black);
                    // Play the song
                    mediaPlayer.start();

                    // Get the whole duration of song
                    finalTime = mediaPlayer.getDuration();
                    // Get Which second in the song
                    startTime = mediaPlayer.getCurrentPosition();

                    // If it is the first time to play music, set the Maximum progress of the seekBar
                    // to the duration of the song.
                    if (oneTimeOnly == 0) {
                        seekBar.setMax((int) finalTime);
                        oneTimeOnly = 1;
                    }

                    // It is not a new song
                    isNewSong = false;

                    // Update SeekBar every 100 ms.
                    myHandler.postDelayed(UpdateSongTime, 100);

                    // Setup a listener on the media player, so that we can stop and release the
                    // media player once the sound has finished playing.
                    mediaPlayer.setOnCompletionListener(mCompletionListener);
                }
            }
        });

        // Skip Button
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentSeconds = (int) startTime;
                if ((currentSeconds - backwardTime) > 0) {
                    // Jump backward 5 seconds
                    startTime = startTime - backwardTime;
                    // Seek to specified time position.
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    Toast.makeText(NowPlaying_Activity.this, "Cannot jump backward 5 \n" +
                            "seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Next Button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentSeconds = (int) startTime;
                if ((currentSeconds + forwardTime) <= finalTime) {
                    // Jump forward 5 seconds
                    startTime = startTime + forwardTime;
                    // Seek to specified time position.
                    mediaPlayer.seekTo((int) startTime);
                } else {
                    Toast.makeText(NowPlaying_Activity.this, "Cannot jump forward 5\n" +
                            "seconds", Toast.LENGTH_SHORT).show();
                }
            }
        });


        // Implement interface Runnable to create and start a thread ,and the run method is called
        // in that separately executing thread.
        UpdateSongTime = new Runnable() {
            public void run() {
                if (mediaPlayer != null && startTime < finalTime) {
                    // Update the visual position of the progress indicator to the current Position.
                    startTime = mediaPlayer.getCurrentPosition();
                    seekBar.setProgress((int) startTime);
                    // Update SeekBar every 100 ms.
                    myHandler.postDelayed(this, 100);
                }
            }
        };


        // If it is a new song, reset everything.
        if (isNewSong) {
            startTime = 0;
            finalTime = 0;
            oneTimeOnly = 0;
            seekBar.setProgress(0);
        }

        // Set Listener to seekBar when is changed.
        seekBar.setOnSeekBarChangeListener(this);
    }


    /**
     * Initialize all Ui elements.
     */
    private void initializeAllUiElements() {
        songName = (TextView) findViewById(R.id.text_song);
        singerName = (TextView) findViewById(R.id.text_singer);
        singerPhoto = (ImageView) findViewById(R.id.singer_photo);
        play = (ImageButton) findViewById(R.id.play);
        skip = (ImageButton) findViewById(R.id.skip);
        next = (ImageButton) findViewById(R.id.next);
        seekBar = (SeekBar) findViewById(R.id.seekBar);
    }

    /**
     * Find the photo of the singer.
     */
    private void findPhotoOfSinger() {
        switch (singer) {
            case "Celine Dion":
                singerPhoto.setImageResource(R.drawable.celin_dion);
                break;
            case "James Blunt":
                singerPhoto.setImageResource(R.drawable.james_blunt);
                break;
            case "Enrique Iglesias":
                singerPhoto.setImageResource(R.drawable.enrique_iglesias);
                break;
            default:
                singerPhoto.setImageResource(R.drawable.bryan_adams);
                break;
        }
    }


    /**
     * Clean up the media player by releasing its resources.
     */
    private void releaseMediaPlayer() {
        // If the media player is not null, then it may be currently playing a sound.
        if (mediaPlayer != null) {
            // Regardless of the current state of the media player, release its resources
            // because we no longer need it.
            mediaPlayer.release();

            // Set the media player back to null. For our code, we've decided that
            // setting the media player to null is an easy way to tell that the media player
            // is not configured to play an audio file at the moment.
            mediaPlayer = null;

            // Regardless of whether or not we were granted audio focus, abandon it. This also
            // unregisters the AudioFocusChangeListener so we don't get anymore callbacks.
            audioManager.abandonAudioFocus(mOnAudioFocusChangeListener);
        }
    }


    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        try {
            if (mediaPlayer.isPlaying() || mediaPlayer != null) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            } else if (mediaPlayer == null) {
                Toast.makeText(getApplicationContext(), "Media is not running", Toast.LENGTH_SHORT).show();
                seekBar.setProgress(0);
            }
        } catch (Exception e) {
            Log.e("MediaActivity", "SeekBar not responding" + e);
        }
    }

    // Before the song is played.
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        // If it is the first time to play music, set the Maximum progress of the seekBar
        // to the duration of the song.
        if (oneTimeOnly == 0) {
            seekBar.setMax(mediaPlayer.getDuration());
            oneTimeOnly = 1;
        }
        // Get Current Progress
        int progress = seekBar.getProgress();
        // Seek to specified time position.
        mediaPlayer.seekTo(progress);
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu options from the res/menu/main.xml file.
        // This adds menu items to the app bar.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // User clicked on a menu option in the app bar overflow menu
        switch (item.getItemId()) {
            // Return Back to the MainActivity
            case R.id.home:
                Intent intent = new Intent(NowPlaying_Activity.this,MainActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
