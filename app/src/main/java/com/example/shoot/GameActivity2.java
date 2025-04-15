package com.example.shoot;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.WindowManager;

public class GameActivity2 extends AppCompatActivity {
    AlertDialog.Builder builder;
    private GameView2 gameView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Point point=new Point();
        getWindowManager().getDefaultDisplay().getSize(point);

        gameView2=new GameView2(this,point.x,point.y);

        setContentView(gameView2);
        builder = new AlertDialog.Builder(this);
        builder.setMessage("Do you want to Quit the game!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                startActivity(new Intent(GameActivity2.this,MainActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
                onResume();
            }
        });
        builder.setCancelable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView2.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView2.resume();
    }
    @Override
    public void onBackPressed() {
        onPause();
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle("ARE YOU SURE ?");
        alertDialog.show();
    }
}