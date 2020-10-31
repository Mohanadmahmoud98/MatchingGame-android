package com.example.matchinggame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button srtBtn;
    Intent inteone;
    TextView scortxt;
    int results;
    public static final String PREFS_NAME = "score";
    //public static final String PREFS_NAMETWO = "Score";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scortxt=findViewById(R.id.scrTxt);

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String returnTxt=settings.getString("score", "Name Not Found");

        if(returnTxt.equals("Name Not Found"))
        {
            results=0;
        }
        else {
            results = Integer.parseInt(returnTxt);
        }
        scortxt.setText(settings.getString("score", "Name Not Found"));

        srtBtn=findViewById(R.id.startBtn);
        inteone = new Intent (this,GameActivity.class);
        srtBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(inteone);
            }
        });
    }
}