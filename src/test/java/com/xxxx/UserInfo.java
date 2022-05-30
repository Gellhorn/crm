package com.xxxx;

import java.io.*;

public class UserInfo implements Serializable {
    @Override
    public String toString() {
        return "UserInfo{" +
                "name='" + name + '\'' +
                ", psw='" + psw + '\'' +
                ", male='" + male + '\'' +

                '}';
    }

    private static final long serialVersionUID = 936890129747019948L;
    private String name;
    private transient String psw;
    private String male;
//    private String ph;

    public UserInfo(String name, String psw, String male, String ph) {
        this.name = name;
        this.psw = psw;
        this.male = male;

    }

    public UserInfo(String name, String psw, String male) {
        this.name = name;
        this.psw = psw;
        this.male = male;
    }

    public UserInfo(String name, String psw) {
        this.name = name;

        this.psw = psw;
    }

}

class TestTransient {
    public static void main(String[] args) {
        UserInfo userInfo = new UserInfo("张三", "123456","male");
        System.out.println(userInfo);
        try {
            // 序列化，被设置为transient的属性没有被序列化
            ObjectOutputStream o = new ObjectOutputStream(new FileOutputStream("userInfo.out"));
            o.writeObject(userInfo);
            o.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        try {
            // 重新读取内容
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("UserInfo.out"));
            UserInfo readUserInfo = (UserInfo) in.readObject();
            //读取后psw的内容为null
            System.out.println(readUserInfo.toString());
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
