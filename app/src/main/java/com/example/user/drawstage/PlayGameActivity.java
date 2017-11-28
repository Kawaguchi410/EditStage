package com.example.user.drawstage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Timer;
import java.util.TimerTask;
import android.os.Handler;

public class PlayGameActivity
        extends AppCompatActivity implements SurfaceHolder.Callback {

    //描写画面
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;


    //描写範囲
    int blockSize;

    //設置モード 0:削除,1:普通のブロック,2:ダメージブロック

    int editMode = 2;

    //基準とするディスプレイサイズとの比率
    DisplayRatio displayRatio;
    //
    StageMake stage;
    //
    PlayCharacterInformation PChara;

    //timerを使えるように
    Timer timer = new Timer();
    TimerTask timerTask = new MainTimerTask();
    Handler timerHandler = new Handler();

    //
    boolean stopDrawFlag1 = false;
    boolean stopDrawFlag2 = false;

    //プレイヤーの情報
    float touchX;
    float touchY;
    float touchedX;
    float touchedY;

    Bitmap bitmap0;
    Bitmap bitmap1;
    Bitmap bitmapPC;

    Rect blockRect;
    Rect PCrect;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_play_game);


        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        displayRatio = new DisplayRatio();
        stage = new StageMake();
        PChara = new PlayCharacterInformation();
        stageLoad();

        bitmap0 = BitmapFactory.decodeResource(getResources(),R.drawable.sky);
        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.tuti);
        bitmapPC = BitmapFactory.decodeResource(getResources(),R.drawable.pc_desu);

        blockRect = new Rect(0,0,10,10);
        PCrect = new Rect(0,0,10,20);

        //  10 / 1000秒ごとに処理を行わせる
        timer.schedule(timerTask, 0, 20);

    }


    //onCreateの中で定義したtimerのscheduleに沿って、一定時間ごとに処理を行う
    public class MainTimerTask extends TimerTask{
        @Override
        public void run() {

            if(stopDrawFlag1 && stopDrawFlag2) {

                timerHandler.post(new Runnable() {
                    @Override
                    public void run() {


                        if(PChara.tameshiYo() == 2){
                            stopDrawFlag2 = false;
                        }
                        drawCanvas();

                    }
                });
            }
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //Button button = (Button)findViewById(R.id.leftButtonId);
        surfaceView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        drawCanvas();
        stopDrawFlag2 = true;

    }


    private void drawCanvas(){

        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.scale(displayRatio.widthRatio, displayRatio.heightRatio);
        Paint paint = new Paint();
        //マップの描写
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.RED);

        for(int i = 0; i <= displayRatio.referenceWidth / blockSize; i++){
            for(int j = 0; j <= displayRatio.referenceHeight / blockSize; j++){

                switch (stage.blockMap[i][j]){
                    case 0://何もない
                        paint.setColor(Color.WHITE);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        //canvas.drawBitmap(bitmap0, i * blockSize, j * blockSize, paint);;
                        break;
                    case 1://普通のブロック
                        paint.setColor(Color.GREEN);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        //canvas.drawBitmap(bitmap1, i * blockSize, j * blockSize, paint);
                        break;
                    case 2://ダメージブロック
                        paint.setColor(Color.RED);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 3://ジャンプできない
                        paint.setColor(Color.BLACK);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 4://ジャンプ力up
                        paint.setColor(Color.YELLOW);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 5://左に流れる
                        paint.setColor(Color.LTGRAY);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 6://右に流れる
                        paint.setColor(Color.DKGRAY);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 7://遅くなる
                        paint.setColor(Color.BLUE);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 8://早くなる
                        paint.setColor(Color.MAGENTA);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 9://重力変更
                        paint.setColor(Color.CYAN);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                    case 10:
                        paint.setColor(Color.WHITE);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        break;
                }
            }
        }
        paint.setColor(Color.GRAY);
        PChara.PCHeight = PChara.PCHeight;
        PChara.PCWidth = PChara.PCWidth;
        canvas.drawRect(PChara.x,PChara.y,PChara.x + blockSize,PChara.y + blockSize + blockSize,paint);
        //canvas.drawBitmap(bitmapPC, PChara.x,PChara.y, paint);
        paint.setColor(Color.BLUE);
        canvas.drawPoint(PChara.cornerX[0][0],PChara.cornerY[0][0],paint);
        canvas.drawPoint(PChara.cornerX[0][0] + 1,PChara.cornerY[0][0] + 1,paint);
        for(int i = 3;i < 100;i++) {
            canvas.drawPoint(PChara.cornerX[0][0] + i, PChara.cornerY[0][0] + 2, paint);
        }
        canvas.drawPoint(PChara.cornerX[1][0],PChara.cornerY[1][0],paint);
        canvas.drawPoint(PChara.cornerX[2][0],PChara.cornerY[2][0],paint);
        canvas.drawPoint(PChara.cornerX[3][0],PChara.cornerY[3][0],paint);

        Paint tokutenPaint = new Paint();
        tokutenPaint.setColor(Color.rgb(255,100,30));
        tokutenPaint.setTextSize(50);
        canvas.drawText("blockY :" + PChara.cornerY[0][1],100,100,tokutenPaint);
        canvas.drawText("Y :" + PChara.y,100,150,tokutenPaint);
        canvas.drawText("y[0][0] :" + PChara.cornerY[0][0],100,200,tokutenPaint);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchedX = event.getX();
                touchedY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                touchX = event.getX();
                touchY = event.getY();
                flickCheck();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                break;
        }

       /* x = event.getX() / displayRatio.widthRatio;
        y = event.getY() / displayRatio.heightRatio;

*/


   //     if(event.getAction() == MotionEvent.ACTION_UP){
   //         PChara.sideSpeed = 0;
   //     }


        /*int touchPointX = (int)x;
        int touchPointY = (int)y;

        int touchBlackX = touchPointX / blockSize;
        int touchBlackY = touchPointY / blockSize;

        stage.editStage(touchBlackX,touchBlackY,blockSize,editMode);
        */

        drawCanvas();

        return true;
    }

    public void flickCheck(){

        int flick = 150;

        //右
        if (touchedX < touchX){
            if (touchX - touchedX > flick){
                PChara.sideSpeed = 2;
            }
        }
        //左
        if (touchedX > touchX){
            if (touchedX - touchX > flick){
                PChara.sideSpeed = -2;
            }
        }
        //下
        if (touchedY < touchY){
            if (touchY - touchedY > flick){
                PChara.sideSpeed = 0;
            }
        }
        //上
        if (touchedY > touchY){
            if (touchedY - touchY > flick){
                PChara.jump = 8;
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void stageLoad(){

        Intent intent = getIntent();
        int stageNum = intent.getIntExtra("StageNum",0);

        SharedPreferences prefer =
                getSharedPreferences("stage" + stageNum, Context.MODE_PRIVATE );
        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();

        display.getRealMetrics(displayMetrics);
        //displayRatio.referenceWidth = 1280;
        //displayRatio.referenceHeight = 720;

        displayRatio.referenceWidth = 640;
        displayRatio.referenceHeight = 360;
        displayRatio.displayRatioCast(displayMetrics.widthPixels,displayMetrics.heightPixels);

        String stringTypeStage = prefer.getString("stageData","die");
        int wi = prefer.getInt("stageWidth",0);//0 = die
        int he = prefer.getInt("stageHeight",0);
        blockSize = prefer.getInt("blockSize",0);


        stage.kaito(stringTypeStage,wi,he,blockSize);

        int X = 120;
        int Y = 200;

        if(PChara.generateChara(X,Y,wi,he,blockSize,stage.blockMap)){

            System.out.println("error");
        }
        stopDrawFlag1 = true;
    }
/*
    public void leftButton(View v){
        PChara.sideSpeed = -5;
    }

    public void rightButton(View v){
        PChara.sideSpeed = 5;
    }

    public void jumpButton(View v){
        PChara.jump = 10;
    }
*/

    public void dialogMake(){
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("ステージセレクト画面に戻りますか？");
        dialog.create();

        dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int whichButton){
                stopDrawFlag1 = true;
            }
        });
        dialog.show();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            stopDrawFlag1 = false;
            dialogMake();
            return true;
        }
        return false;
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

}