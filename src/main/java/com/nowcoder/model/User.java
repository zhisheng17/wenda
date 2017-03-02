package com.nowcoder.model;

/**
 * Created by 10412 on 2016/6/26.
 * 用户模块
 */
public class User {
    /**
     * name 用户名
     * password 密码
     * salt 密码加盐（md5+salt）
     * headUrl 头像地址
     */
    private int id;
    private String name;
    private String password;
    private String salt;
    private String headUrl;

    //构造函数
    public User() {

    }
    public User(String name) {
        this.name = name;
        this.password = "";
        this.salt = "";
        this.headUrl = "";
    }

    //get and set function

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
