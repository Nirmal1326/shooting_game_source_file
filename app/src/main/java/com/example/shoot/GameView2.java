package com.example.shoot;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameView2 extends SurfaceView implements Runnable {
    private Thread thread2;
    private boolean isPlaying2,isGameOver=false;
    private Paint paint;
    private List<Bullet2> bullets2;
    public static int hscore2;
    public static float screenRatioX,screenRatioY;
    private Background2 background3, background4;
    private int screenX,screenY,score2=0;
    private Witch witch;
    private SoundPool soundPool2;
    private SharedPreferences prefs2;
    private int sound2;
    private Random random;
    private Ghost[] ghosts;

    private GameActivity2 activity2;
    public GameView2(GameActivity2 activity2,int screenX,int screenY) {
        super(activity2);
        this.activity2=activity2;
        this.screenX=screenX;
        this.screenY=screenY;
        screenRatioX=1920f/screenX;
        screenRatioY=1080f/screenY;
        background3=new Background2(screenX,screenY,getResources());
        background4=new Background2(screenX,screenY,getResources());
        prefs2=activity2.getSharedPreferences("gamee",Context.MODE_PRIVATE);
        witch=new Witch(this,screenY,getResources());
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
            AudioAttributes audioAttributes=new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).setUsage(AudioAttributes.USAGE_GAME).build();
            soundPool2=new SoundPool.Builder().setAudioAttributes(audioAttributes).build();
        }else
            soundPool2=new SoundPool(1,AudioManager.STREAM_MUSIC,0);
        sound2=soundPool2.load(activity2,R.raw.shoot,1);
        bullets2=new ArrayList<>();
        background4.x=screenX;
        paint=new Paint();
        paint.setTextSize(128);
        paint.setColor(Color.BLACK);
        ghosts=new Ghost[4];

        for (int i = 0;i < 4;i++) {

            Ghost ghost = new Ghost(getResources());
            ghosts[i] = ghost;

        }

        random = new Random();
    }

    @Override
    public void run() {
        while (isPlaying2){
            update ();
            draw ();
            sleep ();
        }
    }
    private void update(){

        background3.x-=10*screenRatioX;
        background4.x-=10*screenRatioX;
        if(background3.x + background3.background2.getWidth()<0){
            background3.x= screenX;
        }
        if(background4.x + background4.background2.getWidth()<0){
            background4.x= screenX;
        }
        if(witch.isGoingUp)
            witch.y-=30*screenRatioY;
        else
            witch.y+=30*screenRatioX;
        if(witch.y<0)
            witch.y=0;
        if(witch.y>=screenY-witch.height)
            witch.y=screenY-witch.height;

        List<Bullet2> trash=new ArrayList<>();
        for(Bullet2 bullet2:bullets2){
            if(bullet2.x>screenX)
                trash.add(bullet2);
            bullet2.x+=50*screenRatioX;

            for(Ghost ghost:ghosts){
                if(Rect.intersects(ghost.getCollisionShape(),
                        bullet2.getCollisionShape())){
                    score2++;
                    ghost.x=-500;
                    bullet2.x=screenX+500;
                    ghost.wasShoot=true;
                }
            }

        }
        for(Bullet2 bullet2:trash)
            bullets2.remove(bullet2);

        for(Ghost ghost : ghosts){
            ghost.x-=ghost.speed;
            if(ghost.x+ghost.width<0){

                if(!ghost.wasShoot){
                    isGameOver=true;
                    return;
                }

                int bound= (int) (30*screenRatioX);
                ghost.speed=random.nextInt(bound);
                if(ghost.speed<10*screenRatioX);
                ghost.speed=(int)(10*screenRatioX);
                ghost.x=screenX;
                ghost.y=random.nextInt(screenY-ghost.height);
                ghost.wasShoot=false;
            }
            if(Rect.intersects(ghost.getCollisionShape(),witch.getCollisionShape())) {

                isGameOver = true;
                return;
            }
        }
    }
    private void draw (){

         if(getHolder().getSurface().isValid()){
             Canvas canvas=getHolder().lockCanvas();
             canvas.drawBitmap(background3.background2,background3.x,background3.y,paint);
             canvas.drawBitmap(background4.background2,background4.x,background4.y,paint);
             for(Ghost ghost:ghosts)
                 canvas.drawBitmap(ghost.getGhost(),ghost.x,ghost.y,paint);
             canvas.drawText(score2+"",screenX/2f,164,paint);
             if(isGameOver){
                 isPlaying2=false;
                 canvas.drawBitmap(witch.getWitchDead(),witch.x,witch.y,paint);
                 getHolder().unlockCanvasAndPost(canvas);
                 saveIfHighScore();
                 SaveIfHighScore();
                 waitBeforeExit();
                 return;
             }
             canvas.drawBitmap(witch.getWitch(),witch.x,witch.y,paint);

             for(Bullet2 bullet2:bullets2)
                 canvas.drawBitmap(bullet2.bullet,bullet2.x,bullet2.y,paint);

             getHolder().unlockCanvasAndPost(canvas);
         }

    }

    private void waitBeforeExit() {
        try {
            Thread.sleep(1500);
            activity2.startActivity(new Intent(activity2, MainActivity.class));
            activity2.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            activity2.finish();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void saveIfHighScore() {
        if(hscore2<score2){
            hscore2=score2;
        }
    }
    private void SaveIfHighScore(){
        if(prefs2.getInt("TopScore",0)<score2){
            SharedPreferences.Editor editor=prefs2.edit();
            editor.putInt("TopScore",score2);
            editor.apply();
        }
    }
    private void sleep(){
        try {
            Thread.sleep(17);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void resume(){
        isPlaying2=true;
        thread2=new Thread(this);
        thread2.start();
    }
    public void pause(){
        try {
            isPlaying2=false;
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch ((event.getAction())){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()<screenX/2){
                 witch.isGoingUp=true;
                }
                break;
            case MotionEvent.ACTION_UP:
                witch.isGoingUp=false;
                if(event.getX()>screenX/2)
                    witch.toShoot++;
                break;
        }
        return true;
    }

    public void newBullet2() {
        if(!prefs2.getBoolean("isMute2",false))
            soundPool2.play(sound2,1,1,0,0,1);
        Bullet2 bullet2=new Bullet2(getResources());
        bullet2.x=witch.x+witch.width;
        bullet2.y=witch.y+(witch.height/3);
        bullets2.add(bullet2);
    }
}

