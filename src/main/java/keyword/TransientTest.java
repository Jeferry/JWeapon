/**
 * YOUTU SOFTWARE Inc.
 * Copyright (c) 2016-2018 All Rights Reserved.
 */
package keyword;

import com.alibaba.fastjson.JSONObject;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author maojifeng
 * @version TransientTest.java, v 0.1 maojifeng
 * @date 2018/2/5 11:13
 * @comment transient关键字
 */
public class TransientTest {

    private static final String PATH = "E:/user.txt";

    static class User implements Serializable {

        private static final long serialVersionUID = -3769923109856701480L;

        private String userName;

        private transient String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

    }

    static class UserWitStatic implements Serializable {

        private static final long serialVersionUID = 4703627810582954755L;

        private static String userName;

        private transient String password;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            UserWitStatic.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static void main(String[] args) {
//        User user = new User();
        UserWitStatic user = new UserWitStatic();
        user.setUserName("JeFerry");
        user.setPassword("password");

        System.out.println("read before Serializable:");
        print(user);

        try {
            ObjectOutputStream os = new ObjectOutputStream(
                    new FileOutputStream(PATH));
            os.writeObject(user); // 将User对象写进文件
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            // 在反序列化之前改变username的值
            user.setUserName("hahaha");

            ObjectInputStream is = new ObjectInputStream(new FileInputStream(
                    PATH));
//            user = (User) is.readObject(); // 从流中读取User的数据
            user = (UserWitStatic) is.readObject(); // 从流中读取User的数据
            is.close();

            System.out.println("read after Serializable: ");
            print(user);

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        System.out.println(JSONObject.toJSONString(user));


    }


    private static void print(User user) {
        System.out.println("username:" + user.getUserName());
        System.out.println("password:" + user.getPassword());
    }

    private static void print(UserWitStatic user) {
        System.out.println("username:" + user.getUserName());
        System.out.println("password:" + user.getPassword());
    }


}
