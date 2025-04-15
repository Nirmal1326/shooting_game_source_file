package com.example.shoot;

import static com.example.shoot.GameView2.screenRatioX;
import static com.example.shoot.GameView2.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Bullet2 {
    int x, y,width,height;
    Bitmap bullet;

    Bullet2(Resources res) {

        bullet = BitmapFactory.decodeResource(res, R.drawable.bullet);

        width = bullet.getWidth();
        height = bullet.getHeight();

        width /= 3;
        height /= 3;

        width=(int) (width*screenRatioX);
        height=(int) (height*screenRatioY);

        bullet = Bitmap.createScaledBitmap(bullet, width, height, false);
    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
}
