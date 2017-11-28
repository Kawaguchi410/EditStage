package com.example.user.drawstage;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class StageSerectActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play_game);

        setContentView(R.layout.activity_stage_serect);
        aaaa();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        CardView cardView = (CardView)findViewById(R.id.cardView);
        cardView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        aaaa();
    }


    void aaaa(){

        LinearLayout cardLinear = (LinearLayout)this.findViewById(R.id.cardLinear);
        cardLinear.removeAllViews();
        final boolean[] stageNull = new boolean[10];

        for(int i = 0; i< 10; i++) {
            LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
            LinearLayout linearLayout = (LinearLayout) inflater.inflate(R.layout.stage_serect_card, null);
            CardView cardView = (CardView) linearLayout.findViewById(R.id.cardView);
            TextView textBox = (TextView) linearLayout.findViewById(R.id.textBox);
            ImageView imageView = (ImageView) linearLayout.findViewById(R.id.editImageView);

            SharedPreferences prefer =
                    getSharedPreferences("stage" + i, Context.MODE_PRIVATE );
            stageNull[i] = false;
            String stageName = prefer.getString("stageData","Null");
            if (stageName == "Null"){
                stageNull[i] = true;
                stageName = "NoDate";
            }else {
                stageName = i + "";
            }

            textBox.setText(stageName);
            cardView.setTag(i);
            imageView.setTag(i);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (stageNull[(int)v.getTag()]){
                        Intent intent = new Intent(getApplication(), DrawStageActivity.class);
                        intent.putExtra("StageNum", (int)v.getTag());
                        intent.putExtra("mode","new");
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(getApplication(), PlayGameActivity.class);
                        intent.putExtra("StageNum", (int) v.getTag());
                        startActivity(intent);
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplication(), DrawStageActivity.class);
                    if (stageNull[(int)v.getTag()]){
                        intent.putExtra("mode","new");
                    }else {
                        intent.putExtra("mode","edit");
                    }
                    intent.putExtra("StageNum", (int)v.getTag());
                    startActivity(intent);
                }
            });
            cardLinear.addView(linearLayout,i);
        }


    }

}
