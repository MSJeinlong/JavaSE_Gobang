package com.demo1.view;

import com.demo1.model.UserDAO;
import com.demo1.model.UserDAOImpl;
import com.demo1.model.User;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author long
 * 用户登录界面
 */

public class LoginDialog extends JDialog implements ActionListener {
    //定义组件
    private JPanel jp1, jp2, jp3;
    private JLabel jlb1, jlb2;
    private JButton jb1, jb2;
    private JTextField jtf;
    private JPasswordField jpf;

    public LoginDialog() {
        //三个面板
        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();

        jlb1 = new JLabel("账号：");
        jlb2 = new JLabel("密码：");

        // 文本框和密码框
        jtf = new JTextField(16);
        jpf = new JPasswordField(16);

        // 2个按钮
        jb1 = new JButton("登录");
        jb2 = new JButton("注册");

        // 为2个按钮注册监听
        jb1.addActionListener(this);
        jb1.setActionCommand("Login");
        jb2.addActionListener(this);
        jb2.setActionCommand("SignUp");

        // 设置布局管理器
        this.setLayout(new GridLayout(3, 1));

        // 添加组件到面板
        jp1.add(jlb1);
        jp1.add(jtf);
        jp2.add(jlb2);
        jp2.add(jpf);
        jp3.add(jb1);
        jp3.add(jb2);

        //面板添加到Dialog
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);

        // 设置窗体属性
        setTitle("趣味五子棋登录界面");
        /*setBounds(500, 500, 300, 400);*/
        pack();
        setLocation(700, 400);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setVisible(true);
        setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 对用户不同的点击做出不同的反应
        switch (e.getActionCommand()){
            //登录
            case "Login":
                UserDAO uDAO = new UserDAOImpl();
                String name = jtf.getText().trim();
                String password = new String(jpf.getPassword()).trim();
                User u = new User();
                u.setName(name);
                u.setPassword(password);
                u = uDAO.Query(u);  //u不存在将返回null
                //如果登录成功
                if(u != null){
                    new SelectModel();   //生成游戏模式选择界面
                    this.dispose();     //关闭登录界面
                } else {
                    //登录失败，提示用户
                    JOptionPane.showMessageDialog(this, "登录失败!\n原因：账号或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
                break;
                //注册
            case "SignUp":
                new SignUpDialog(this,"用户注册");      //生成注册界面
                break;
        }
    }
}
