package com.example.marwa.musicplayer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.marwa.musicplayer.R;
import com.example.marwa.musicplayer.model.Song;
import com.example.marwa.musicplayer.adapter.SongAdapter;

import java.util.ArrayList;

public class Songs_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_songs);

        // Get the name of singer
        Intent intent = getIntent();
        String nameOfSinger = intent.getStringExtra(MainActivity.NAME_OF_SINGER);

        /*
         * Using findViewById, we get a reference to our RecyclerView from xml. This allows us to
         * do things like set the adapter of the RecyclerView and toggle the visibility.
         */
        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_songs);

        // LinearLayoutManager
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        // Set the layoutManager on mRecyclerView
        mRecyclerView.setLayoutManager(layoutManager);

        /*
         * Use this setting to improve performance if you know that changes in content do not
         * change the child layout size in the RecyclerView
         */
        mRecyclerView.setHasFixedSize(true);

        // Create a list of songs
        final ArrayList<Song> songs = new ArrayList<>();
        songs.add(new Song("My Heart Will Go On",getString(R.string.celine_dion),R.raw.my_heart_will_go_on));
        songs.add(new Song("Think Twice",getString(R.string.celine_dion),R.raw.think_twice));
        songs.add(new Song("I Am Your Angel",getString(R.string.celine_dion),R.raw.i_am_your_angel));
        songs.add(new Song("Because You Love Me",getString(R.string.celine_dion),R.raw.my_heart_will_go_on));
        songs.add(new Song("All By Myself",getString(R.string.celine_dion),R.raw.think_twice));
        songs.add(new Song("I Am Alive",getString(R.string.celine_dion),R.raw.i_am_your_angel));
        songs.add(new Song("Immortality",getString(R.string.celine_dion),R.raw.i_am_your_angel));
        songs.add(new Song("Goodbye My Lover",getString(R.string.james_blunt),R.raw.goodbye_my_lover));
        songs.add(new Song("WiseMan",getString(R.string.james_blunt),R.raw.wisemen));
        songs.add(new Song("You Are Beautiful",getString(R.string.james_blunt),R.raw.you_are_beautiful));
        songs.add(new Song("Heart To Heart",getString(R.string.james_blunt),R.raw.goodbye_my_lover));
        songs.add(new Song("Love Me Better",getString(R.string.james_blunt),R.raw.wisemen));
        songs.add(new Song("I Really Want You",getString(R.string.james_blunt),R.raw.you_are_beautiful));
        songs.add(new Song("Bonfire Heart",getString(R.string.james_blunt),R.raw.you_are_beautiful));
        songs.add(new Song("Hero",getString(R.string.enrique_iglesias),R.raw.hero));
        songs.add(new Song("Ring My Bells",getString(R.string.enrique_iglesias),R.raw.ring_my_bells));
        songs.add(new Song("You Are My Mumber One",getString(R.string.enrique_iglesias),R.raw.you_are_my_no_one));
        songs.add(new Song("Heartbeat",getString(R.string.enrique_iglesias),R.raw.hero));
        songs.add(new Song("Escape",getString(R.string.enrique_iglesias),R.raw.ring_my_bells));
        songs.add(new Song("Do You Know?",getString(R.string.enrique_iglesias),R.raw.you_are_my_no_one));
        songs.add(new Song("Tonight",getString(R.string.enrique_iglesias),R.raw.you_are_my_no_one));
        songs.add(new Song("On a Day Like Today",getString(R.string.bryan_adams),R.raw.on_a_day_like_today));
        songs.add(new Song("I Think About You",getString(R.string.bryan_adams),R.raw.i_think_about_you));
        songs.add(new Song("I will Always Be Right There",getString(R.string.bryan_adams),R.raw.i_will_always_be_right_there));
        songs.add(new Song("Run To You",getString(R.string.bryan_adams),R.raw.on_a_day_like_today));
        songs.add(new Song("Heaven",getString(R.string.bryan_adams),R.raw.i_think_about_you));
        songs.add(new Song("All For Love",getString(R.string.bryan_adams),R.raw.i_will_always_be_right_there));
        songs.add(new Song("Cloud Number Nine",getString(R.string.bryan_adams),R.raw.i_will_always_be_right_there));

        // Store the required songs in an ArrayList
        ArrayList<Song> requiredSongs = filterSongs(songs,nameOfSinger);

        // Add Item Decoration
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(),
                layoutManager.getOrientation());
        mRecyclerView.addItemDecoration(dividerItemDecoration);

        // Set mSongAdapter equal to a new SongAdapter
        SongAdapter mSongAdapter = new SongAdapter(requiredSongs);

        // Use mRecyclerView.setAdapter and pass in mSongAdapter
        mRecyclerView.setAdapter(mSongAdapter);
    }

    /**
     *  Filter all the songs.
     */
    private ArrayList<Song> filterSongs(ArrayList<Song> allSongs,String singerName){
        // Declare and Initialize empty ArrayList
        ArrayList<Song> songsRequired = new ArrayList<>();
        // Loop through all the songs
        for(int i = 0; i< allSongs.size(); i++){
            // Get the current song
            Song currentSong = allSongs.get(i);
            // Get the singer's name of the current song
            String nameOfSinger = currentSong.getNameOfSinger();
            // I they are the same, add the song
            if(nameOfSinger.equals(singerName)){
                songsRequired.add(currentSong);
            }
        }
        // return the required songs
        return songsRequired;
    }

}
