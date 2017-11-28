package com.example.user.drawstage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }


    public void playGame(View v){

        Intent intent = new Intent(getApplication(), PlayGameActivity.class);
        startActivity(intent);

    }


    public void editStage(View v){

        Intent intent = new Intent(getApplication(), DrawStageActivity.class);
        startActivity(intent);
    }


    public void buttonYo(View v){
        Intent intent = new Intent(getApplication(), StageSerectActivity.class);
        startActivity(intent);
    }

}
