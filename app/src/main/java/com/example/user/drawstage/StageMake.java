package com.example.user.drawstage;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by user on 2017/10/09.
 */

public class StageMake {

    public int[][] blockMap;
    public int[][] hitMap;
    int stageWidth;
    int stageHeight;
    int blockCount;
    int blockSize;


    //
    int makeStage(int width, int height, int bSize, int blockNumber) {

        width = width + (bSize - width % bSize);
        height = height + (bSize - height % bSize);

        blockCount = 0;
        stageWidth = width;
        stageHeight = height;
        blockSize = bSize;

        hitMap = new int[width][height];
        blockMap = new int[width / blockSize][height / blockSize];



        for (int i = 0; i < width / blockSize; i++) {
            for (int j = 0; j < height / blockSize; j++) {

                blockMap[i][j] = blockNumber;
                blockCount++;


                for (int k = i * blockSize; k < i * blockSize + blockSize; k++) {
                    for (int l = j * blockSize; l < j * blockSize + blockSize; l++) {
                        hitMap[k][l] = blockNumber;
                    }
                }
            }
        }
        return blockCount;
    }

    //
    void editStage(int x, int y, int bSize, int blockNumber) {

        if(bSize == 0){
            bSize = blockSize;
        }

        blockMap[x][y] = blockNumber;

        for (int i = x * blockSize; i < x * blockSize + blockSize; i++) {
            for (int j = y * blockSize; j < y * blockSize + blockSize; j++) {
                hitMap[i][j] = blockNumber;
            }
        }
    }

    //
    void extendStage(int width, int height, int bSize, int blockNumber, int addWidth, int addHeight) {

        if(bSize == 0){
            bSize = blockSize;
        }

        int[][] tempHitMap = new int[width][height];
        int[][] tempBlockMap = new int[width / blockSize][height / blockSize];

        for (int i = 0; i < width / blockSize; i++) {
            for (int j = 0; j < height / blockSize; j++) {

                tempBlockMap[i][j] = blockMap[i][j];

                for (int k = i * blockSize; k < i * blockSize + blockSize; k++) {
                    for (int l = j * blockSize; l < j * blockSize + blockSize; l++) {
                        tempHitMap[k][l] = hitMap[k][l];
                    }
                }
            }
        }

        blockCount = 0;
        hitMap = new int[width + addWidth][height + addHeight];
        blockMap = new int[(width + addWidth) / blockSize][(height + addHeight) / blockSize];

        for (int i = 0; i < width / blockSize; i++) {
            for (int j = 0; j < height / blockSize; j++) {

                blockMap[i][j] = tempBlockMap[i][j];
                blockCount++;

                for (int k = i * blockSize; k < i * blockSize + blockSize; k++) {
                    for (int l = j * blockSize; l < j * blockSize + blockSize; l++) {
                        hitMap[k][l] = tempHitMap[k][l];
                    }
                }
            }
        }


        for (int i = width / blockSize; i < (width + addWidth) / blockSize; i++) {
            for (int j = height / blockSize; j < (height + addHeight) / blockSize; j++) {

                blockMap[i][j] = blockNumber;
                blockCount++;

                for (int k = i * blockSize; k < i * blockSize + blockSize; k++) {
                    for (int l = j * blockSize; l < j * blockSize + blockSize; l++) {
                        hitMap[k][l] = blockNumber;
                    }
                }
            }
        }

    }

    String saveStageYo(){
        String saveMap = "";

        for (int i = 0; i < stageWidth / blockSize; i++) {
            for (int j = 0; j < stageHeight / blockSize; j++) {

                saveMap = saveMap + blockMap[i][j] + ",";
            }
        }
        saveMap = saveMap + "!";
        //セーブ内容
        //ブロックマップ
        //高さ(ブロック)
        //幅(ブロック)
        //ブロックサイズ
        //キーはマップの名前
        //ブロックの数(デバック用)
        //※まだ実装しない
        //立ち位置
        //
        return saveMap;
    }


    void kaito(String sString, int width, int height, int bSize){

        stageWidth = width;
        stageHeight = height;
        blockSize = bSize;
        int count = 0;

        blockMap = new int[width / bSize][height / bSize];
        hitMap = new int[width][height];


        for (int i = 0; i < width / bSize; i++) {
            for (int j = 0; j < height / bSize; j++) {

                while (true) {
                    if (sString.charAt(count) == '!'){
                        return;
                    }
                    if(sString.charAt(count) == ','){
                        count++;
                        break;
                    }else {
                        int num = Character.getNumericValue(sString.charAt(count));
                        blockMap[i][j] = num;

                        for (int k = i * bSize; k < i * bSize + bSize; k++) {
                            for (int l = j * bSize; l < j * bSize + bSize; l++) {
                                hitMap[k][l] = num;
                            }
                        }
                        count++;
                    }
                }
            }
        }
    }
}