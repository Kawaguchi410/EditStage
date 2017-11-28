package com.example.user.drawstage;

import android.view.Display;

/**
 * Created by user on 2017/10/08.
 */

public class DisplayRatio {


    public int referenceWidth = 780;
    public int referenceHeight = 1280;

    public float ratio;
    public float widthRatio;
    public float heightRatio;
    public int stageWidth;
    public int stageHeight;

    public boolean displayRatioCast(int width,int height){

        stageWidth = width;
        stageHeight = height;


        widthRatio = (float) width / referenceWidth;
        heightRatio = (float) height / referenceHeight;
        if (widthRatio < heightRatio){
            ratio = widthRatio;
        }else {
            ratio = heightRatio;
        }

        return true;
    }

}
