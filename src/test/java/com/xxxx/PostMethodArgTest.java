package com.xxxx;

import java.io.*;

class PostMethodArgTest {
    public static void main(String[] args) throws java.lang.Exception{

    Integer[] datas = {12, 23, 34, 45};
    String ids = "ids=";
    for(int i = 0;i<datas.length;i++){
        if (i < datas.length - 1) {
            ids = ids + datas[i] + "&ids=";
        } else {
            ids = ids + datas[i];
        }


    }        System.out.println(ids);

    }
    }