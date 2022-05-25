package com.xxxx;

public class Singleton{
    private Singleton(){
    }
    private static Singleton singleton = null;

    public static Singleton getInstance(){
        if(singleton == null){
            singleton = new Singleton();

        }
        return singleton;
    }
}