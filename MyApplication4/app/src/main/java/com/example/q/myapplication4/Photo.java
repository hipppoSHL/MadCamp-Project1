package com.example.q.myapplication4;

import android.net.Uri;

public class Photo{
    private String imageURI;
    private String thumbnailURI;

    public Photo(String imageURI, String thumbnailURI){
        this.imageURI = imageURI;
        this.thumbnailURI = thumbnailURI;
    }

    public String getImageURI(){
        return imageURI;
    }

    public String getThumbnailURI(){
        return thumbnailURI;
    }



}
