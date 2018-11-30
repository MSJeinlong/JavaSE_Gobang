package com.demo1.view;

import com.demo2.view.PPMainBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectRival extends JDialog implements ActionListener {

    private JButton jb1, jb2, jb3;

    public SelectRival() {

        jb1 = new JButton("人人对战");
        jb2 = new JButton("人机对战");
        jb3 = new JButton("上一级菜单");

        this.setLayout(new GridLayout(3, 1));

        jb1.addActionListener(this);
        jb2.addActionListener(this);
        jb3.addActionListener(this);
        this.add(jb1);
        this.add(jb2);
        this.add(jb3);

        this.setTitle("选择对战模式");
        this.setBounds(800, 400, 200, 150);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //判断用户选择了哪种模式
        if(e.getSource() == jb1){
            //人人模式
            dispose();
            new PPMainBoard();
        } else if(e.getSource() == jb2){
            //人机对战模式
            dispose();
            new SelectLevel();      //进入电脑水平模式选择
        } else if(e.getSource() == jb3){
            //返回上一级菜单
            dispose();
            new SelectModel();
        }
    }
}
