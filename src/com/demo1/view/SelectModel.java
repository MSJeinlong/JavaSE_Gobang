package com.demo1.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SelectModel extends JDialog implements ActionListener {

    private JButton jb1, jb2;

    public SelectModel() {

        jb1 = new JButton("对弈模式");
        jb2 = new JButton("训练模式");

        this.setLayout(new GridLayout(2, 1));

        jb1.addActionListener(this);
        jb2.addActionListener(this);
        this.add(jb1);
        this.add(jb2);

        this.setTitle("模式选择");
        this.setBounds(800, 400, 200, 100);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //用户选择了对弈模式
        if(e.getSource() == jb1){
            dispose();          //关闭当前界面
            new SelectRival();          //进入对手选择模式
        } else if(e.getSource() == jb2){
            //用户选择了训练模式，直接进入人机训练
            dispose();
            new SelectLevel();          //进入电脑等级选择
        }
    }
}
