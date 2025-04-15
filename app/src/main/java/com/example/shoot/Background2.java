package com.example.shoot;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class Background2 {
    int x=0,y=0;
    Bitmap background2;
    Background2(int screenX1, int screenY1, Resources res)
    {
        background2= BitmapFactory.decodeResource(res, R.drawable.ghost_background1);
        background2=Bitmap.createScaledBitmap(background2,screenX1,screenY1,false);//resize
    }
}
