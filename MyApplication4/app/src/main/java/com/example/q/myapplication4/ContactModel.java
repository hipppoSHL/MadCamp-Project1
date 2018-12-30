package com.example.q.myapplication4;

// Fragment1의 연락처 클래스

import android.graphics.Bitmap;

public class ContactModel {

    private String name, number;
    private Bitmap image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    // 새로 추가한 것(image)
    public Bitmap getImage() { return image; }
    // 새로 추가한 것(image)
    public void setImage(Bitmap bp) { this.image = bp; }
}
