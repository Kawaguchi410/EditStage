package com.example.user.drawstage;

import android.util.Log;

/**
 * Created by user on 2017/10/10.
 */

public class PlayCharacterInformation {

    //キャラの左上が座標
    int x;
    int y;
    int stageWidth;
    int stageHeight;
    int sideSpeed = 0;
    int sideSpeedChanger = 0;
    int jump = 0;
    int jumpChanger = 0;
    float fallSpeed = 0;
    float fallSpeedChanger = 1;
    float gravity = 8 / 10;
    int[][] stage;
    int count = 0;
    int blockSize = 40;
    int[][] blockMap;
    int PCWidth = blockSize;
    int PCHeight = blockSize * 2;

    boolean noHitFlag = true;

    //
    //角の座標 [0][0]は[左上][ピクセル単位の座標]
    //         [2][1]は[左下][ブロック単位の座標]
    int[][] cornerX = new int[4][2];
    int[][] cornerY = new int[4][2];

    int[] sideState = new int[2];
    int[][] sideX = new int[2][2];
    int[][] sideY = new int[2][2];

    int leftTop;
    int rightTop;
    int leftCenter;
    int rightCenter;
    int leftBottom;
    int rightBottom;





    boolean generateChara(int X,int Y,int sWidth,int sHeight,int bSize, int bMap[][]) {

        x = X;
        y = Y;
        blockSize = bSize;
        blockMap = bMap;
        //blockMap[1][1] = blockMap[4][3];
        stageWidth = blockMap[1][1];
        stageWidth = sWidth;
        stageHeight = sHeight;

/*
        cornerX[0][0] = x;
        cornerY[0][0] = y;
        cornerX[0][1] = x / blockSize;
        cornerY[0][1] = y / blockSize;
        //cornerState[0] = blockMap[cornerX[0][1]][cornerY[0][1]]
        cornerX[1][0] = x + blockSize - 1;
        cornerY[1][0] = y;
        cornerX[1][1] = (x + blockSize - 1) / blockSize;
        cornerY[1][1] = y / blockSize;
        //cornerState[1] = blockMap[x + blockSize - 1][y];
        cornerX[2][0] = x;
        cornerY[2][0] = y + blockSize * 2 - 1;
        cornerX[2][1] = x / blockSize;
        cornerY[2][1] = (y + blockSize * 2 - 1) / blockSize;
        //cornerState[2] = blockMap[x][y + blockSize * 2];
        cornerX[3][0] = x + blockSize - 1;
        cornerY[3][0] = y + blockSize * 2;
        cornerX[3][1] = (x + blockSize - 1) / blockSize;
        cornerY[3][1] = (y + blockSize * 2 - 1) / blockSize;
        //cornerState[3] = blockMap[x + blockSize - 1][y + blockSize * 2];
*/
/*

        sideX[0] = x;
        sideY[0] = y + blockSize;
        sideState[0] = blockMap[x][y + blockSize];
        sideX[1] = x + blockSize - 1;
        sideY[1] = y + blockSize;
        sideState[1] = blockMap[x + blockSize - 1][y + blockSize];

        for (int i = X; i < width + X; i++) {
            for (int j = Y; j < height + Y; j++) {

                if (hitMap[i][j] != 0) {
                    //return true;
                }
            }
        }

        x = X;
        y = Y;

        stage = hitMap;
        return false;
    }

    int feetState(){

        boolean flag0 = false;
        boolean flag1 = false;
        boolean flag2 = false;
        boolean Default = false;

        for(int i = x; i < x + width;i++){
            switch (stage[i][y + height]){
                case 0://何も無し
                    flag0 = true;
                    break;
                case 1://普通のブロック
                    flag1 = true;
                    break;
                case 2://ダメージブロック
                    flag2 = true;
                    break;
                default://設定値が登録されていない
                    Default = true;
                    break;
            }
        }

        //優先順位で
        if (Default){//設定値が登録されていない
            return -1;//エラー
        }else if (flag2){//ダメージブロック
            return 2;//ダメージ
        }else if (flag1){//普通のブロック
            return 1;//地面
        }else if (flag0){//何も無し
            return 0;//空中
        }
        return -1;//エラー
    }


    int move(int state){
        //Y座標の動きを計算
        switch (state){
            case 0:
                fallSpeed = fallSpeed + gravity;
                break;
            case 1:
                fallSpeed = 0 - jump;
                break;
            case 2:
                return 2;
            default:
                break;
        }
        jump = 0;

        x = x + sideSpeed;
        y = y + (int)fallSpeed;

        return hitSearch();
        */
        return true;
    }

 /*   int hitSearch(){

        int sideState = 0;
        int sideHit = 0;
        int vericalHit = 0;
        int verticalHit = 0;
        //右への動き
        if(sideSpeed > 0){
            sideState = moveRight();
            sideHit = count;
        }else if(sideSpeed < 0){
            sideState = moveLeft();
            sideHit = count;
        }

        if(fallSpeed > 0){
            sideState = moveDown();
            sideHit = count;
        }else if(fallSpeed < 0){
            sideState = moveUp();
            sideHit = count;
        }


        for(int i = x; i < x + width;i++){
            switch (stage[i][y + height]){
                case 0://何も無し
                    flag0 = true;
                    break;
                case 1://普通のブロック
                    flag1 = true;
                    break;
                case 2://ダメージブロック
                    flag2 = true;
                    break;
                default://設定値が登録されていない
                    Default = true;
                    break;
            }
        }

        return sideState;
    }
*/
  /*  int moveRight(){

        boolean flag = false;
        count = 0;

        for(int i = x + width - sideSpeed; i < x + width;i++){
            for (int j = y; j < y + height;j++){
                switch (stage[i][j]){
                    case 0:
                        break;
                    case 1:
                        flag = true;
                        break;
                    case 2:
                        x = i - width;
                        return 2;
                    default:
                        break;
                    }
                }
                if (flag){
                    x = i - width;
                    return 1;
                }
                count++;
            }
        return 0;
    }

    int moveLeft(){

        boolean flag = false;
        count = 0;

        for(int i = x - sideSpeed; i > x;i--){
            for (int j = y; j < y + height; j++){
                switch (stage[i][j]){
                    case 0:
                        break;
                    case 1:
                        flag = true;
                        break;
                    case 2:
                        x = i + 1;
                        return 2;
                    default:
                        break;
                }
            }
            if (flag){
                x = i + 1;
                return 1;
            }
            count++;
        }
        return 0;

    }

    int moveDown(){

        boolean flag = false;
        count = 0;

        for (int j = y + height - (int)fallSpeed; j < y + height;j++){
            for(int i = x; i < x + width; i++){
                switch (stage[i][j]){
                    case 0:
                        break;
                    case 1:
                        flag = true;
                        break;
                    case 2:
                        y = j - height;
                        return 2;
                    default:
                        break;
                }
            }
            if (flag){
                y = j - height;
                return 1;
            }
            count++;
        }
        return 0;

    }

    int moveUp(){

        boolean flag = false;
        count = 0;
        for (int j = y - (int)fallSpeed; j > y;j--){
            for(int i = x; i < x + width; i++){
                switch (stage[i][j]){
                    case 0:
                        break;
                    case 1:
                        flag = true;
                        break;
                    case 2:
                        y = j + 1;
                        return 2;
                    default:
                        break;
                }
            }
            if (flag){
                y = j + 1;
                fallSpeed = -1;
                return 1;
            }
            count++;
        }
        return 0;

    }
*/
    int tameshiYo(){

        int[] X = new int[2];
        int[] Y = new int[2];

        X[0] = x / blockSize;
        Y[0] = (y + blockSize * 2) / blockSize;
        X[1] = (x + blockSize - 1) / blockSize;
        Y[1] = (y + blockSize * 2) / blockSize;
        boolean notFloting = false;

        for(int i = 0;i < 2;i++) {

            switch (blockMap[X[i]][Y[i]]) {
                case 0://何もない
                    break;
                case 1://普通のブロック
                    notFloting = true;
                    break;
                case 2://ダメージブロック
                    notFloting = true;
                    return 2;
                case 3://ジャンプできない
                    fallSpeed = 0;
                    break;
                case 4://ジャンプ力up
                    notFloting = true;
                    if (jump != 0){
                        jumpChanger = 3;
                    }
                    break;
                case 5://左に流れる
                    notFloting = true;
                    sideSpeedChanger = -1;
                    break;
                case 6://右に流れる
                    notFloting = true;
                    sideSpeedChanger = 1;
                    break;
                case 7://遅くなる
                    notFloting = true;
                    if (sideSpeed < 0){
                        sideSpeedChanger = 1;
                    }else if (sideSpeed > 0){
                        sideSpeedChanger = -1;
                    }
                    break;
                case 8:
                    notFloting = true;
                    if (sideSpeed < 0){
                        sideSpeedChanger = -1;
                    }else if (sideSpeed > 0){
                        sideSpeedChanger = 1;
                    }
                    break;
                case 9:
                    break;
                case 10:

                    break;
            }
        }
        if (notFloting){
            fallSpeed = 0;
            fallSpeed = fallSpeed - jump - jumpChanger;
        }else {
            fallSpeed = fallSpeed + gravity + fallSpeedChanger;
        }

        if (fallSpeed >= blockSize - 5){
            fallSpeed = blockSize - 5;
        }

        return tameshi();

    }

    int tameshi(){

        jump = 0;
        jumpChanger = 0;
        fallSpeedChanger = 1;
        cornerX[0][0] = x;
        cornerY[0][0] = y;
        cornerX[0][1] = x / blockSize;
        cornerY[0][1] = y / blockSize;

        cornerX[1][0] = x + blockSize - 1;
        cornerY[1][0] = y;
        cornerX[1][1] = (x + blockSize - 1) / blockSize;
        cornerY[1][1] = y / blockSize;

        cornerX[2][0] = x;
        cornerY[2][0] = y + blockSize * 2 - 1;
        cornerX[2][1] = x / blockSize;
        cornerY[2][1] = (y + blockSize * 2 - 1) / blockSize;

        cornerX[3][0] = x + blockSize - 1;
        cornerY[3][0] = y + blockSize * 2 - 1;
        cornerX[3][1] = (x + blockSize - 1) / blockSize;
        cornerY[3][1] = (y + blockSize * 2 - 1) / blockSize;

        sideX[0][0] = x;
        sideX[0][1] =  x / blockSize;

        sideX[1][0] = x + blockSize - 1;
        sideX[1][1] = (x + blockSize - 1) / blockSize;

        ///この中で座標に足す
        for(int i = 0;i < 4;i++) {
            cornerX[i][0] = cornerX[i][0] + sideSpeed + sideSpeedChanger;
            cornerY[i][0] = cornerY[i][0] + (int)fallSpeed;
        }
        for(int i = 0;i < 2;i++){
            sideX[i][0] = sideX[i][0] + sideSpeed + sideSpeedChanger;
        }
        sideSpeedChanger = 0;
        //flagをfalseで設定
        boolean[] flagX = {false,false,false};
        boolean[] flagY = {false,false,false};

        boolean return2flag = false;
        noHitFlag = true;


        //四つ角用
        for(int i = 0;i < 4;i++){
            //ブロックの境界線を越えてたらflagを立てる
            if(cornerX[i][1] != cornerX[i][0] / blockSize){
                //越えた先のブロックに設定された数字番目の配列をtrueに
                flagX[ distributesSwitch( blockMap[cornerX[i][0] / blockSize][cornerY[i][1]] ) ] = true;
            }
            if(cornerY[i][1] != cornerY[i][0] / blockSize){
                flagY[ distributesSwitch( blockMap[cornerX[i][1]][cornerY[i][0] / blockSize] ) ] = true;
            }
        }

        //横用
        for(int i = 0;i < 2;i++){
            //ブロックの境界線を越えたらflagを立てる
            if(sideX[i][1] != sideX[i][0] / blockSize){
                //越えた先のブロックに設定された数字番目の配列をtrueに
                flagX[ distributesSwitch( blockMap[sideX[i][0] / blockSize][cornerY[0][1] + 1] ) ] = true;
            }
        }

        if (noHitFlag){
            if (sideSpeed < 0 && fallSpeed < 0) {
                //flagX[ distributesSwitch( blockMap[cornerX[0][0] / blockSize][cornerY[0][0] / blockSize] )  ] = true;
                flagY[ distributesSwitch( blockMap[cornerX[0][0] / blockSize][cornerY[0][0] / blockSize] ) ] = true;
            } else if (sideSpeed > 0 && fallSpeed < 0){
                flagX[ distributesSwitch( blockMap[cornerX[1][0] / blockSize][cornerY[1][0] / blockSize] )  ] = true;
                //flagY[ distributesSwitch( blockMap[cornerX[1][0] / blockSize][cornerY[1][0] / blockSize] ) ] = true;
            }else if (sideSpeed < 0 && fallSpeed > 0){
                //flagX[ distributesSwitch( blockMap[cornerX[1][0] / blockSize][cornerY[1][0] / blockSize] )  ] = true;
                flagY[ distributesSwitch( blockMap[cornerX[2][0] / blockSize][cornerY[2][0] / blockSize] ) ] = true;
            }else if (sideSpeed > 0 && fallSpeed > 0){
                flagX[ distributesSwitch( blockMap[cornerX[3][0] / blockSize][cornerY[3][0] / blockSize] )  ] = true;
                //flagY[ distributesSwitch( blockMap[cornerX[3][0] / blockSize][cornerY[3][0] / blockSize] ) ] = true;
            }
        }


        if(flagX[2]){
            //左に動いているなら
            if (sideSpeed < 0) {
                x = cornerX[0][1] * blockSize;
                return2flag = true;
            } else{
                x = cornerX[1][0] / blockSize * blockSize - blockSize;
                return2flag = true;
            }
        }else if (flagX[1]) {
            if (sideSpeed < 0) {
                x = cornerX[0][1] * blockSize;
            } else{
                x = cornerX[1][0] / blockSize * blockSize - blockSize;
            }
        }else if(flagX[0]){
            x = cornerX[0][0];
        }else {
            x = cornerX[0][0];
        }

        if(flagY[2]){
            //上に動いているなら
            if (fallSpeed < 0) {
                y = cornerY[0][1] * blockSize;
                return2flag = true;
            } else {
                y = cornerY[2][0] / blockSize * blockSize - (2 * blockSize);
                return2flag = true;
            }
        }else if(flagY[1]){
            //上に動いているなら
            if (fallSpeed < 0) {
                y = cornerY[0][1] * blockSize;
            } else {
                y = (cornerY[2][0] / blockSize) * blockSize - (2 * blockSize);
            }
        }else if(flagY[0]){
            y = cornerY[0][0];
        }else {
            y = cornerY[0][0];
        }

        if (flagX[2] || flagX[1]){
            if (flagY[2] || flagY[1]){

            }
        }

        if (return2flag){
            return 2;
        }
        if (wall()){
            return 2;
        }
        return 0;
    }

    boolean wall(){

        if (cornerX[0][0] < 0){
            x = 0;
        }else if (stageWidth < x + blockSize * 2){
            x = stageWidth - blockSize * 2;
        }

        if (cornerY[0][0] < 0){
            y = 0;
            fallSpeed = 0;
        }else if (stageHeight < y + blockSize * 3){
            y = stageHeight - blockSize * 3;
            return true;
        }

        return false;
    }

    int distributesSwitch(int caseNum){

        if (!(caseNum == 0 || caseNum == 9)){
            noHitFlag = false;
        }

        //0:何もない,1:障害物判定,2:ダメージ判定
        switch (caseNum) {
            case 0://何もない
                return 0;
            case 1://普通のブロック
                return 1;
            case 2://ダメージブロック
                return 2;
            case 3://ジャンプできない
                return 1;
            case 4://ジャンプ力up
                return 1;
            case 5://左に流れる
                return 1;
            case 6://右に流れる
                return 1;
            case 7://遅くなる
                return 1;
            case 8://早くなる
                return 1;
            case 9://重力
                double temp = 1;
                fallSpeedChanger = (float) temp;
                return 0;
            case 10:
                break;
        }
        return -1;
    }
}