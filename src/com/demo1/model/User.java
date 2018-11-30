package com.demo1.model;

public class User {
    private int id;              //标识用户的id号
    private String name;         //账号名
    private String password;     //密码
    private String sex;          //性别
    private int dan = 1;        //段位，共九个段位，最低为初段（1段），最高为9段，默认为初段
    private int grade = 1;       //等级，每个段位10个等级，最高为10级，最低为1级，默认为1级
    private int status;          //登录状态，0为离线，1为在线未对战，2为在线匹配，3为在线对战中

    public User() {
    }

    public User(String name, String password, String sex) {
        this.name = name;
        this.password = password;
        this.sex = sex;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getDan() {
        return dan;
    }

    public void setDan(int dan) {
        this.dan = dan;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", sex='" + sex + '\'' +
                ", dan=" + dan +
                ", grade=" + grade +
                ", status=" + status +
                '}';
    }
}
