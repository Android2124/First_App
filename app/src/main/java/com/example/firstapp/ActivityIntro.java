package com.example.firstapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityIntro extends AppCompatActivity {

    TextView txtSkip;
    ImageView imdNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        txtSkip = findViewById(R.id.txtSkip);
        imdNext = findViewById(R.id.imgNext);

        txtSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityIntro.this, ActivityLogin.class);
                startActivity(intent);
            }
        });

        imdNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityIntro.this, ActivityIntro1.class);
                startActivity(intent);
            }
        });
    }
}