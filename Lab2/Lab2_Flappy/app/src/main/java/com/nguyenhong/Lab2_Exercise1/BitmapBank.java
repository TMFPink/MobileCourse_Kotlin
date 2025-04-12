package com.nguyenhong.Lab2_Exercise1;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class BitmapBank {

    Bitmap background_game;
    Bitmap[] bird;

    public BitmapBank(Resources resources){
        background_game= BitmapFactory.decodeResource(resources, R.drawable.background_game);

        background_game = scaleImage(background_game);

        bird = new  Bitmap[4];
        bird [0]=BitmapFactory.decodeResource(resources, R.drawable.avt_removebg);
        bird [1]=BitmapFactory.decodeResource(resources, R.drawable.avt_removebg);
        bird [2]=BitmapFactory.decodeResource(resources, R.drawable.avt_removebg);
        bird [3]=BitmapFactory.decodeResource(resources, R.drawable.avt_removebg);
    }

    public Bitmap getBird(int frame) {
        return bird[frame];
    }

    public int getBirdWidth(){
        return bird[0].getWidth();
    }

    public int getBirdHeight(){
        return bird [0].getHeight();
    }

    //return background bitmap
    public Bitmap getBackground_game(){
        return background_game;
    }

    public int getBackgroundWidth(){
        return background_game.getWidth();
    }

    public int getBackgroundHeight(){
        return background_game.getHeight();
    }

    public Bitmap scaleImage(Bitmap bitmap){
        float widthHeightRatio = getBackgroundWidth() / getBackgroundHeight();

        int backgroundScaleWidth = (int) (widthHeightRatio * AppConstants.SCREEN_HEIGHT);
        return Bitmap.createScaledBitmap(bitmap, backgroundScaleWidth, AppConstants.SCREEN_HEIGHT, false);
    }
}
