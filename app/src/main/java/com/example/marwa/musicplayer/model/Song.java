package com.example.marwa.musicplayer.model;

/**
 * Created by Marwa on 4/3/2018.
 */

public class Song {

    /** Name of the song */
    private String nameOfSong;

    /** Name of the singer */
    private String nameOfSinger;

    /** Audio resource ID for the song */
    private int audioResourceId;


    /**
     * Create a new Singer object.
     *
     * @param nameOfSong is the name of song.
     * @param nameOfSinger is the name of Singer.
     * @param audioResourceId is audio resource for the song.
     */
    public Song(String nameOfSong,String nameOfSinger ,int audioResourceId) {
        this.nameOfSong = nameOfSong;
        this.nameOfSinger = nameOfSinger;
        this.audioResourceId = audioResourceId;
    }

    /**
     * Get the name of song.
     */
    public String getNameOfSong() {
        return nameOfSong;
    }

    /**
     * Get the name of singer.
     */
    public String getNameOfSinger() {
        return nameOfSinger;
    }

    /**
     * Get the audio resource of the song.
     */
    public int getAudioResourceId() {
        return audioResourceId;
    }

}
