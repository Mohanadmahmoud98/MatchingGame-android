package com.example.matchinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    ImageButton frstBtn;
    Intent interestart;
    int finishcheck=0;
    ImageButton[] imgs=new ImageButton[8];
    SharedPreferences settings;
    String returnTxt;
    int counter=0;
    int x;
    int counterCheck=0;
    public static final String PREFS_NAME = "score";
    //public static final String PREFS_NAMETWO = "Score";
    public int counterTimer=0;
    int rndm [][] = {{0,0},{1,0},{2,0},{3,0}};
    int[] index=new int [8];
    int[] myImageList = new int[]{R.drawable.dog, R.drawable.cat, R.drawable.horse, R.drawable.duck};
    int[] soundsList = new int[]{R.raw.dog, R.raw.cat, R.raw.horse, R.raw.duck};
    String [] names= new String[]{"DOG","Cat","HORSE","DUCK"};
    int checking[]={0,0,0,0,0,0,0,0};
    int checkingTwo[]={0,0,0,0};
   // int raiseIndex[]={0,0,0,0};
    int lastClicked[]={0,0,0,0,0,0,0,0};
    int lastbuttonCliked=9;
    int isGreen[]={0,0,0,0,0,0,0,0};
    int random;
    int timeFinised=0;
    int results;
    public int finishing=0;
    CountDownTimer timers;
    TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        txt=findViewById(R.id.txtone);
        imgs[0]=findViewById(R.id.firstBtn);
        imgs[1]=findViewById(R.id.secondBtn);
        imgs[2]=findViewById(R.id.thirdBtn);
        imgs[3]=findViewById(R.id.forthBtn);
        imgs[4]=findViewById(R.id.fifthBtn);
        imgs[5]=findViewById(R.id.sixthBtn);
        imgs[6]=findViewById(R.id.seventhBtn);
        imgs[7]=findViewById(R.id.eightthBtn);
        settings = getSharedPreferences(PREFS_NAME, 0);
        String returnTxt=settings.getString("score", "Name Not Found");


        if(returnTxt.equals("Name Not Found"))
        {
            results=0;
        }
        else {
            results = Integer.parseInt(returnTxt);
        }

        while(counter<8)
        {
            while(counterCheck!=1) {
                random = new Random().nextInt(4);
                if (rndm[random][1]<2)
                {
                        index[counter]=myImageList[random];
                    Log.println(Log.ASSERT,"msg1",index[counter]+"");
                        checking[counter]=random;
                    counterCheck=1;
                    rndm[random][1]++;
                }
                else{
                    counterCheck=0;
                }
            }
            counter++;
            counterCheck=0;
        }

        final Handler handler = new Handler();
        final Runnable r = new Runnable() {
            public void run() {

                for(int i=0 ; i<imgs.length ; i++) {
                    imgs[i].setBackgroundColor(Color.GRAY);
                    timeFinised=0;
                }

            }
        };
        for(int i=0 ; i<imgs.length ; i++) {
            imgs[i].setBackgroundResource(index[i]);
        }
        timeFinised=1;
        handler.postDelayed(r, 1500);
         timers= new CountDownTimer(3000000, 1000){
            public void onTick(long millisUntilFinished){
                counterTimer++;
                txt.setText( Integer.toString(counterTimer) + " seconds");
            }
            public  void onFinish(){
                if(counterTimer < results || results==0) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("score", Integer.toString(counterTimer));
                    editor.commit();
                }
                finishing();
                handler.postDelayed(r, 1500);
                finishcheck=1;
            }
        }.start();
        for(int i=0 ; i<imgs.length ; i++) {
            imgs[i].setOnClickListener(this);
        }
    }
    public void onClick(View v) {
        final Handler handlerOne = new Handler();
        switch (v.getId()) {
            case R.id.firstBtn:
                if(lastClicked[0]!=1 && timeFinised==0 && isGreen[0]!=1) {
                    if (checkingTwo[checking[0]] == 0 && lastbuttonCliked==9) {
                        v.setBackgroundResource(index[0]);
                        Toast.makeText(getApplicationContext(), names[checking[0]]+ "", Toast.LENGTH_LONG).show();

                        checkingTwo[checking[0]]++;
                        lastbuttonCliked=0;
                        lastClicked[0] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[0])
                    {
                        final Runnable rOne = new Runnable() {
                        public void run() {
                            imgs[x].setBackgroundColor(Color.GRAY);
                            imgs[0].setBackgroundColor(Color.GRAY);
                            timeFinised=0;
                            }
                    };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[0]]+ "", Toast.LENGTH_LONG).show();
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[0].setBackgroundResource(index[0]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[0] && checkingTwo[checking[0]] != 0)
                    {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[0].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        Toast.makeText(getApplicationContext(), names[checking[0]]+ "", Toast.LENGTH_LONG).show();
                        imgs[0].setBackgroundResource(index[0]);
                        handlerOne.postDelayed(rOne, 2000);
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[0]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[0]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.secondBtn:
                if(lastClicked[1]!=1 && timeFinised==0&& isGreen[1]!=1) {
                    if (checkingTwo[checking[1]] == 0 && lastbuttonCliked==9) {
                        v.setBackgroundResource(index[1]);
                        Toast.makeText(getApplicationContext(), names[checking[1]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[1]]++;
                        lastbuttonCliked=1;
                        lastClicked[1] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[1] )
                    {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GRAY);
                                imgs[1].setBackgroundColor(Color.GRAY);
                                timeFinised=0;
                            }
                        };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[1]]+ "", Toast.LENGTH_LONG).show();

                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[1].setBackgroundResource(index[1]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[1] && checkingTwo[checking[1]] != 0) {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[1].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[1].setBackgroundResource(index[1]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[1]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[1]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[1]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.thirdBtn:
                if(lastClicked[2]!=1 && timeFinised==0 && isGreen[2]!=1) {
                    if (checkingTwo[checking[2]] == 0 && lastbuttonCliked==9) {
                        v.setBackgroundResource(index[2]);
                        Toast.makeText(getApplicationContext(), names[checking[2]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[2]]++;
                        lastbuttonCliked=2;
                        lastClicked[2] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[2])
                    {

                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GRAY);
                                imgs[2].setBackgroundColor(Color.GRAY);
                                timeFinised=0;
                            }
                        };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[2]]+ "", Toast.LENGTH_LONG).show();
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[2].setBackgroundResource(index[2]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[2]) {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[2].setBackgroundColor(Color.GREEN);

                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[2].setBackgroundResource(index[2]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[2]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[2]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[2]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.forthBtn:
                if(lastClicked[3]!=1 && timeFinised==0 && isGreen[3]!=1 ) {
                    if (checkingTwo[checking[3]] == 0&& lastbuttonCliked==9) {
                        v.setBackgroundResource(index[3]);
                        Toast.makeText(getApplicationContext(), names[checking[3]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[3]]++;
                        lastbuttonCliked=3;
                        lastClicked[3] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[3] )
                    {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GRAY);
                                imgs[3].setBackgroundColor(Color.GRAY);
                                timeFinised=0;
                            }
                        };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[3]]+ "", Toast.LENGTH_LONG).show();

                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[3].setBackgroundResource(index[3]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[3] && checkingTwo[checking[3]] != 0) {

                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[3].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[3].setBackgroundResource(index[3]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[3]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[3]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[3]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.fifthBtn:
                if(lastClicked[4]!=1 && timeFinised==0 && isGreen[4]!=1 ) {
                    if (checkingTwo[checking[4]] == 0&& lastbuttonCliked==9) {
                        v.setBackgroundResource(index[4]);
                        Toast.makeText(getApplicationContext(), names[checking[4]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[4]]++;
                        lastbuttonCliked=4;
                        lastClicked[4] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[4] )
                    {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GRAY);
                                imgs[4].setBackgroundColor(Color.GRAY);
                                timeFinised=0;
                            }
                        };
                        timeFinised=1;

                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[4]]+ "", Toast.LENGTH_LONG).show();
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[4].setBackgroundResource(index[4]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[4] && checkingTwo[checking[4]] != 0) {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[4].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[4].setBackgroundResource(index[4]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[4]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[4]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[4]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.sixthBtn:
                if(lastClicked[5]!=1 && timeFinised==0 && isGreen[5]!=1 ) {
                    if (checkingTwo[checking[5]] == 0&& lastbuttonCliked==9) {
                        v.setBackgroundResource(index[5]);
                        Toast.makeText(getApplicationContext(), names[checking[5]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[5]]++;
                        lastbuttonCliked=5;
                        lastClicked[5] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[5] )
                    {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GRAY);
                                imgs[5].setBackgroundColor(Color.GRAY);
                                timeFinised=0;
                            }
                        };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[5]]+ "", Toast.LENGTH_LONG).show();
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[5].setBackgroundResource(index[5]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[5] && checkingTwo[checking[5]] != 0) {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[5].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[5].setBackgroundResource(index[5]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[5]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[5]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[5]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.seventhBtn:
                if(lastClicked[6]!=1 && timeFinised==0 && isGreen[6]!=1 ) {
                    if (checkingTwo[checking[6]] == 0&& lastbuttonCliked==9) {
                        v.setBackgroundResource(index[6]);
                        Toast.makeText(getApplicationContext(), names[checking[6]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[6]]++;
                        lastbuttonCliked=6;
                        lastClicked[6] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[6] )
                    {
                        final Runnable rOne = new Runnable() {
                        public void run() {
                            imgs[x].setBackgroundColor(Color.GRAY);
                            imgs[6].setBackgroundColor(Color.GRAY);
                            timeFinised=0;
                        }
                    };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[6]]+ "", Toast.LENGTH_LONG).show();
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[6].setBackgroundResource(index[6]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[6] && checkingTwo[checking[6]] != 0) {

                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[6].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }
                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[6].setBackgroundResource(index[6]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[6]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[6]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[6]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
            case R.id.eightthBtn:
                if(lastClicked[7]!=1 && timeFinised==0 && isGreen[7]!=1 ) {
                    if (checkingTwo[checking[7]] == 0&& lastbuttonCliked==9) {
                        v.setBackgroundResource(index[7]);
                        Toast.makeText(getApplicationContext(), names[checking[7]]+ "", Toast.LENGTH_LONG).show();
                        checkingTwo[checking[7]]++;
                        lastbuttonCliked=7;
                        lastClicked[7] = 1;
                    }
                    else if(checking[lastbuttonCliked]!=checking[7] )
                    {
                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GRAY);
                                imgs[7].setBackgroundColor(Color.GRAY);
                                timeFinised=0;
                            }
                        };
                        timeFinised=1;
                        handlerOne.postDelayed(rOne, 500);
                        Toast.makeText(getApplicationContext(), names[checking[7]]+ "", Toast.LENGTH_LONG).show();
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[7].setBackgroundResource(index[7]);
                        x=lastbuttonCliked;
                        for(int i=0;i<8;i++) {
                            lastClicked[i] = 0;
                            checkingTwo[checking[i]]=0;
                        }
                        lastbuttonCliked=9;
                    }
                    else if (checking[lastbuttonCliked]==checking[7] && checkingTwo[checking[7]] != 0) {

                        final Runnable rOne = new Runnable() {
                            public void run() {
                                imgs[x].setBackgroundColor(Color.GREEN);
                                imgs[7].setBackgroundColor(Color.GREEN);
                                timeFinised=0;
                                if(finishing==8)
                                {
                                    timers.onFinish();
                                }

                            }
                        };
                        timeFinised=1;
                        imgs[lastbuttonCliked].setBackgroundResource(index[lastbuttonCliked]);
                        imgs[7].setBackgroundResource(index[7]);
                        handlerOne.postDelayed(rOne, 2000);
                        Toast.makeText(getApplicationContext(), names[checking[7]]+ "", Toast.LENGTH_LONG).show();
                        MediaPlayer ring = MediaPlayer.create(GameActivity.this, soundsList[checking[7]]);
                        ring.start();
                        x=lastbuttonCliked;
                        isGreen[7]=1;
                        isGreen[lastbuttonCliked]=1;
                        lastbuttonCliked=9;
                        finishing=finishing+2;
                    }
                }
                break;
        }
    }
    public void finishing()
    {
        interestart = new Intent (this, MainActivity.class);
        finish();
        startActivity(interestart);
    }




}