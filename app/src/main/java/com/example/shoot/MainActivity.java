package com.example.shoot;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import static com.example.shoot.GameView.hscore;
import static com.example.shoot.GameView2.hscore2;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.UriMatcher;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private Button button;
    private boolean isMute;
    private boolean isMute2;
    private AlertDialog.Builder builder;
    private FirebaseAuth auth;
    private Button btn;
    private Button feedBack;
    private TextView textview;
    private FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        auth=FirebaseAuth.getInstance();
        btn=findViewById(R.id.Logout);
        textview=findViewById(R.id.user_details);
        builder = new AlertDialog.Builder(this);
        user=auth.getCurrentUser();
        if(user==null){
            Intent intent =new Intent(getApplicationContext(),Login.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
        else{
            textview.setText("Your ID= "+user.getUid());
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent =new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        });
        builder.setMessage("Do you want to exit !");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(false);
        findViewById(R.id.play).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        feedBack=(Button) findViewById(R.id.feedBack);
        feedBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotourl("https://game-feedback-form.netlify.app/");
            }
        });

        findViewById(R.id.play2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, GameActivity2.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        button = (Button) findViewById(R.id.play3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MainActivity2.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        button = (Button) findViewById(R.id.exit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        ImageView reset2=findViewById(R.id.reset2);
        reset2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hscore=0;
                TextView ScoreTxt = findViewById(R.id.HighScores);
                ScoreTxt.setText("HighScore:" + hscore);
            }
        });
        ImageView reset1=findViewById(R.id.reset1);
        reset1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hscore2=0;
                TextView ScoreTxt2 = findViewById(R.id.HighScores2);
                ScoreTxt2.setText("HighScore:" + hscore2);
            }
        });
        TextView ScoreTxt = findViewById(R.id.HighScores);
        ScoreTxt.setText("HighScore:" + hscore);
        SharedPreferences prefs = getSharedPreferences("game", MODE_PRIVATE);
        TextView TopScore1=findViewById(R.id.TopScore1);
        TopScore1.setText("TopScore:"+prefs.getInt("TopScore0",0));
        isMute = prefs.getBoolean("isMute", false);
        ImageView volumeCtrl = findViewById(R.id.volumeCtrl);
        if (isMute)
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
        else
            volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
        volumeCtrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isMute = !isMute;
                if (isMute)
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_off_24);
                else
                    volumeCtrl.setImageResource(R.drawable.ic_baseline_volume_up_24);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("isMute", isMute);
                editor.apply();
            }
        });
        {
            TextView ScoreText2= findViewById(R.id.HighScores2);
            ScoreText2.setText("HighScore:" +hscore2);
            SharedPreferences prefs2 = getSharedPreferences("gamee", MODE_PRIVATE);
            TextView TopScore2=findViewById(R.id.TopScore2);
            TopScore2.setText("TopScore:"+prefs2.getInt("TopScore",0));
            isMute2 = prefs2.getBoolean("isMute2", false);
            ImageView volumeCtrl2 = findViewById(R.id.volumeCtrl2);
            if (isMute2)
                volumeCtrl2.setImageResource(R.drawable.baseline_volume2_off_24);
            else
                volumeCtrl2.setImageResource(R.drawable.baseline_volume2_up_24);
            volumeCtrl2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isMute2 = !isMute2;
                    if (isMute2)
                        volumeCtrl2.setImageResource(R.drawable.baseline_volume2_off_24);
                    else
                        volumeCtrl2.setImageResource(R.drawable.baseline_volume2_up_24);
                    SharedPreferences.Editor editor = prefs2.edit();
                    editor.putBoolean("isMute2", isMute2);
                    editor.apply();
                }
            });
        }
    }

    private void gotourl(String s) {
        Uri uri= Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW,uri));
    }

    public void openMainActivity2(){
        Intent intent=new Intent(MainActivity.this,MainActivity2.class);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        AlertDialog alertDialog=builder.create();
        alertDialog.setTitle("ARE YOU SURE ?");
        alertDialog.show();
    }
}