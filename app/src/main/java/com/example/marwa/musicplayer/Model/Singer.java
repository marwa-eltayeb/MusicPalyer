package com.example.marwa.musicplayer.Model;

/**
 * Created by Marwa on 4/3/2018.
 */

public class Singer {

    /** Name of the singer */
    private String nameOfSinger;

    /** Photo of the singer */
    private int imageResourceId = NO_IMAGE_PROVIDED;

    /** Constant value that represents no image was provided for any singer */
    private static final int NO_IMAGE_PROVIDED = -1;



    /**
     * Create a new Singer object.
     *
     * @param nameOfSinger is the name of singer.
     * @param imageResourceId is the photo of singer.
     */
    public Singer(String nameOfSinger, int imageResourceId) {
        this.nameOfSinger = nameOfSinger;
        this.imageResourceId = imageResourceId;
    }


    /**
     * Get name of singer.
     */
    public String getNameOfSinger() {
        return nameOfSinger;
    }

    /**
     * Get the photo of singer.
     */
    public int getImageResourceId() {
        return imageResourceId;
    }

    /**
     * Returns whether or not there is an image for this word.
     */
    public boolean hasImage() {
        return imageResourceId != NO_IMAGE_PROVIDED;
    }

}
