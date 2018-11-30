package com.demo1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectLevel extends JDialog implements ActionListener {
    public static final int PRIMARY = 1; //初级
    public static final int MEDIUM = 2;  //中级
    public static final int SENIOR = 3;  //高级
    private JButton jb1, jb2, jb3, jb4;

    public SelectLevel() {

        jb1 = new JButton("初级");
        jb2 = new JButton("中级");
        jb3 = new JButton("高级");
        jb4 = new JButton("上一步");

        this.setLayout(new GridLayout(4, 1));

        //注册监听
        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        jb4.addActionListener(this);

        //添加组件
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);
        this.add(jb4);

        //设置属性
        this.setTitle("请选择电脑等级");
        this.setBounds(800, 400, 200, 250);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == jb1){           //用户选择了电脑初级
            dispose();      //关闭当前界面
            new PCMainBoard(PRIMARY);   //加载电脑初级游戏界面
        } else if(e.getSource() == jb2){    //用户选择了电脑中级
            dispose();      //关闭当前界面
            new PCMainBoard(MEDIUM);   //加载电脑中级游戏界面
        } else if(e.getSource() == jb3){    //用户选择了电脑高级
            dispose();      //关闭当前界面
            new PCMainBoard(SENIOR);   //加载电脑高级游戏界面
        } else if(e.getSource() == jb4){
            dispose();
            new SelectModel();          //直接回到SelectModel
        }
    }
}
