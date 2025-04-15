package com.example.shoot;

import static com.example.shoot.GameView2.screenRatioX;
import static com.example.shoot.GameView2.screenRatioY;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Witch {
    public int toShoot=0;

    public boolean isGoingUp=false;
    int x,y,width,height, broomCounter=0,shootCounter=1;
    Bitmap witch1,witch2,witch_shoot_1,witch_shoot_2,witch_shoot_3,witch_shoot_4,witch_shoot_5,witchDead;
    private GameView2 gameView2;
    Witch(GameView2 gameView2,int screenY, Resources res){
        witch1= BitmapFactory.decodeResource(res,R.drawable.whitch1);
        witch2= BitmapFactory.decodeResource(res,R.drawable.whitch2);
        this.gameView2=gameView2;
        width= witch1.getWidth();
        height=witch1.getHeight();

        width/=2.5;
        height/=2.5;

        width=(int) (width*screenRatioX);
        height=(int) (height*screenRatioY);

        witch1=Bitmap.createScaledBitmap(witch1,width,height,false);
        witch2=Bitmap.createScaledBitmap(witch2,width,height,false);

        witch_shoot_1=BitmapFactory.decodeResource(res,R.drawable.whitch_shoot_1);
        witch_shoot_2=BitmapFactory.decodeResource(res,R.drawable.whitch_shoot_2);
        witch_shoot_3=BitmapFactory.decodeResource(res,R.drawable.whitch_shoot_3);
        witch_shoot_4=BitmapFactory.decodeResource(res,R.drawable.whitch_shoot_4);
        witch_shoot_5=BitmapFactory.decodeResource(res,R.drawable.whitch_shoot_5);

        witch_shoot_1=Bitmap.createScaledBitmap(witch_shoot_1,width,height,false);
        witch_shoot_2=Bitmap.createScaledBitmap(witch_shoot_2,width,height,false);
        witch_shoot_3=Bitmap.createScaledBitmap(witch_shoot_3,width,height,false);
        witch_shoot_4=Bitmap.createScaledBitmap(witch_shoot_4,width,height,false);
        witch_shoot_5=Bitmap.createScaledBitmap(witch_shoot_5,width,height,false);

        witchDead=BitmapFactory.decodeResource(res,R.drawable.whitch_dead);
        witchDead=Bitmap.createScaledBitmap(witchDead,width,height,false);
        y=screenY/2;
        x= (int) (64*screenRatioX);
    }
    Bitmap getWitch() {

        if(toShoot!=0){
            if(shootCounter==1){
                shootCounter++;
                return witch_shoot_1;
            }
            if(shootCounter==2){
                shootCounter++;
                return witch_shoot_2;
            }
            if(shootCounter==3){
                shootCounter++;
                return witch_shoot_3;
            }
            if(shootCounter==4){
                shootCounter++;
                return witch_shoot_4;
            }
            shootCounter=1;
            toShoot--;
            gameView2.newBullet2();
            return witch_shoot_5;
        }
        if (broomCounter == 0) {
            broomCounter++;
            return witch1;
        }
        broomCounter --;

        return witch2;
    }
    Rect getCollisionShape () {
        return new Rect(x, y, x + width, y + height);
    }
    Bitmap getWitchDead(){ return witchDead;}
}
