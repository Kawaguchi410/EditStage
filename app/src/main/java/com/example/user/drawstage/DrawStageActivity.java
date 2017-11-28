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
import android.graphics.Point;
import android.graphics.PorterDuff;
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
import android.widget.Button;
import android.widget.ImageView;

public class DrawStageActivity
        extends AppCompatActivity implements SurfaceHolder.Callback {

    //描写画面
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;


    //描写範囲
    int blockSize = 20;

    //設置モード 0:削除,1:普通のブロック,2:ダメージブロック,3:ジャンプできない,4:ジャンプ力up,5:左に流れる,6:右に流れる,7:遅くなる,8:,9:,10:

    int editMode = 1;

    //基準とするディスプレイサイズとの比率
    DisplayRatio displayRatio;
    //
    StageMake stage;

    Bitmap bitmap1;
    Bitmap bitmap0;




    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_draw_stage);

        //Button save = (Button)findViewById(R.id.saveButtonId);
        //save.setVisibility(View.VISIBLE);

        surfaceView = (SurfaceView) findViewById(R.id.surfaceView);

        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setFormat(PixelFormat.TRANSLUCENT);

        displayRatio = new DisplayRatio();
        stage = new StageMake();Intent intent = getIntent();

        stageMake();

        bitmap0 = BitmapFactory.decodeResource(getResources(),R.drawable.sky);
        bitmap1 = BitmapFactory.decodeResource(getResources(),R.drawable.tuti);



        ImageView imageView = (ImageView)findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMenu();
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                stageLoad();
                return false;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        //Button button = (Button)findViewById(R.id.saveButtonId);
        surfaceView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        drawCanvas();
    }


    private void drawCanvas(){

        Canvas canvas = surfaceHolder.lockCanvas();
        canvas.scale(displayRatio.widthRatio, displayRatio.heightRatio);
        Paint paint = new Paint();
        //マップの描写
        canvas.drawColor(0, PorterDuff.Mode.CLEAR);
        canvas.drawColor(Color.RED);

        for(int i = 0; i <= displayRatio.referenceWidth / blockSize; i ++){
            for(int j = 0; j <= displayRatio.referenceHeight / blockSize; j++){

                switch (stage.blockMap[i][j]){
                    case 0://何もない
                        paint.setColor(Color.WHITE);
                        canvas.drawRect(i * blockSize, j * blockSize, i * blockSize + blockSize, j * blockSize + blockSize,paint);
                        //canvas.drawBitmap(bitmap0, i * blockSize, j * blockSize, paint);
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

        //Paint tokutenPaint = new Paint();
        //tokutenPaint.setColor(Color.rgb(255,100,30));
        //tokutenPaint.setTextSize(50);
        //canvas.drawCircle(touchPointX, touchPointY, hanke, zigunPaint);
        //canvas.drawText("touchPointX :" + touchPointX,100,100,tokutenPaint);
        //canvas.drawText("touchPointY :" + touchPointY,100,150,tokutenPaint);

        surfaceHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        float x;
        float y;

        x = event.getX() / displayRatio.widthRatio;
        y = event.getY() / displayRatio.heightRatio;

        int touchPointX = (int)x;
        int touchPointY = (int)y;

        int touchBlackX = touchPointX / blockSize;
        int touchBlackY = touchPointY / blockSize;

        stage.editStage(touchBlackX,touchBlackY,blockSize,editMode);


        drawCanvas();

        return true;
    }

    /*
    public void saveButton(View v){
        stageSave();
    }
    public void loadButton(View v){
        stageLoad();
    }
*/

    public void stageSave() {

        Intent intent = getIntent();
        int stageNum = intent.getIntExtra("StageNum",0);

        String stringTypeStage = stage.saveStageYo();
        SharedPreferences prefer = getSharedPreferences("stage" + stageNum, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefer.edit();

        editor.putString("stageData", stringTypeStage);
        editor.putInt("stageWidth", stage.stageWidth);
        editor.putInt("stageHeight", stage.stageHeight);
        editor.putInt("blockSize", blockSize);
        editor.apply();
    }

    public void stageLoad(){

        Intent intent = getIntent();
        int stageNum = intent.getIntExtra("StageNum",0);

        SharedPreferences prefer =
                getSharedPreferences("stage" + stageNum, Context.MODE_PRIVATE );

        String stringTypeStage = prefer.getString("stageData","die");
        int wi = prefer.getInt("stageWidth",0);//0 = die
        int he = prefer.getInt("stageHeight",0);
        int bSize = prefer.getInt("blockSize",0);

        stage.kaito(stringTypeStage,wi,he,bSize);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    void stageMake() {

        WindowManager wm = getWindowManager();
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getRealMetrics(displayMetrics);

        //displayRatio.referenceWidth = 1280;
        //displayRatio.referenceHeight = 720;
        displayRatio.referenceWidth = 640;
        displayRatio.referenceHeight = 360;
        displayRatio.displayRatioCast(displayMetrics.widthPixels, displayMetrics.heightPixels);

        Intent intent = getIntent();
        String mode = intent.getStringExtra("mode");
        if (mode.equals("new")) {
            //editMode設定に変える
            stage.makeStage(displayRatio.referenceWidth, displayRatio.referenceHeight, blockSize, 0);
        }else if (mode.equals("edit")){
            stageLoad();
        }else {
            String aaaaaaaaaaaa = "die";
        }

    }

    public void editMenu(){

        if (editMode == 0){
            editMode = 1;
        }else if (editMode == 1){
            editMode = 2;
        }else if (editMode == 2){
            editMode = 3;
        }else if (editMode == 3){
            editMode = 4;
        }else if (editMode == 4){
            editMode = 5;
        }else if (editMode == 5){
            editMode = 6;
        }else if (editMode == 6){
            editMode = 7;
        }else if (editMode == 7){
            editMode = 8;
        }else if (editMode == 8){
            editMode = 9;
        }else if (editMode == 9){
            editMode = 0;
        }/*else if (editMode == 10){
            editMode = 0;
        }*/
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode== KeyEvent.KEYCODE_BACK){
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("データを保存しますか？");
            dialog.create();

            dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int whichButton) {
                    stageSave();
                    finish();
                }
            });
            dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int whichButton){

                }
            });
            dialog.setNeutralButton("No Change",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int whichButton){
                    finish();
                }
            });

            dialog.show();

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
