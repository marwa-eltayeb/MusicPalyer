package com.example.marwa.musicplayer.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.marwa.musicplayer.Activity.NowPlaying_Activity;
import com.example.marwa.musicplayer.Model.Song;
import com.example.marwa.musicplayer.R;

import java.util.ArrayList;

/**
 * Created by Marwa on 4/3/2018.
 */

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongAdapterViewHolder> {

    // Declare an arrayList for songs
    private ArrayList<Song> songs;

    // Constant Variables
    public static String SINGER = "singer";
    public static String SONG = "song";
    public static String AUDIO = "audio";

    public SongAdapter(ArrayList<Song> songs){
        this.songs = songs;
    }

    @Override
    public SongAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.song_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new SongAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SongAdapterViewHolder holder, int position) {
        Song currentSong = songs.get(position);
        final String singer = currentSong.getNameOfSinger();
        final String song = currentSong.getNameOfSong();
        holder.nameOfSong.setText(song);
        final int audio = currentSong.getAudioResourceId();
        holder.play_music.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), NowPlaying_Activity.class);
                intent.putExtra(SINGER,singer);
                intent.putExtra(SONG,song);
                intent.putExtra(AUDIO,audio);
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (songs == null) {
            return 0;
        }
        return songs.size();
    }

    /**
     * Cache of the children views for a song list item.
     */
    public class SongAdapterViewHolder extends RecyclerView.ViewHolder {

        public TextView nameOfSong;
        public ImageView play_music;

        public SongAdapterViewHolder(View view) {
            super(view);
            nameOfSong = (TextView) view.findViewById(R.id.songName);
            play_music = (ImageView) view.findViewById(R.id.play_music);
        }
    }

}
