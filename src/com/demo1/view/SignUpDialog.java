package com.demo1.view;

import com.demo1.model.User;
import com.demo1.model.UserDAO;
import com.demo1.model.UserDAOImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SignUpDialog extends JDialog implements ActionListener {
    //定义组件

    private JLabel jlb1, jlb2, jlb3;
    private JPanel jp1, jp2, jp3, jp4;
    private JButton jb1, jb2;
    private JTextField jtf;
    private JPasswordField jpf;
    private JRadioButton jrb1, jrb2;
    private Box box;
    private String sex;

    public SignUpDialog(Dialog owner, String title) {
        super(owner, title);        //父类方法初始化

        //创建组件
        jlb1 = new JLabel("账号：");
        jlb2 = new JLabel("密码：");
        jlb3 = new JLabel("性别：");

        jtf = new JTextField(16);
        jpf = new JPasswordField(16);

        jb1 = new JButton("确认");
        jb2 = new JButton("取消");

        //两个单选框
        jrb1 = new JRadioButton("男", true);
        sex = "男";      //sex默认为男
        jrb2 = new JRadioButton("女");

        // 因为单选两个不能同时选择，所有加到一个组里可以达到效果
        ButtonGroup group = new ButtonGroup();
        group.add(jrb1);
        group.add(jrb2);
        box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(2));
        box.add(jrb1);
        box.add(jrb2);

       //设置网格布局
        this.setLayout(new GridLayout(4, 1));

        jp1 = new JPanel();
        jp2 = new JPanel();
        jp3 = new JPanel();
        jp4 = new JPanel();

        //注册监听
        jb1.addActionListener(this);
        jb1.setActionCommand("confirm");
        jb2.addActionListener(this);
        jb2.setActionCommand("cancel");

        // 用户选择不同的性别单选框时，sex也跟着改变
        jrb1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 用户选择男单选框
                sex = jrb1.getText();
                // System.out.println("sex:"+sex);
            }

        });

        // 匿名内部类 ActionListener
        jrb2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                // 用户选择女单选框
                sex = jrb2.getText();
                // System.out.println("sex:"+sex);
            };

        });

        //添加组件
        jp1.add(jlb1);
        jp1.add(jtf);
        jp2.add(jlb2);
        jp2.add(jpf);
        jp3.add(box);
        jp4.add(jb1);
        jp4.add(jb2);

        //添加面板
        this.add(jp1);
        this.add(jp2);
        this.add(jp3);
        this.add(jp4);

        //设置Dialog属性
        this.setLocation(950, 400);
        this.pack();        //自适应大小
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setResizable(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "confirm":
                String name = jtf.getText().trim();
                String password = new String(jpf.getPassword()).trim();
                if(name == null || name.equals("") || password == null || password.equals("")){
                    JOptionPane.showMessageDialog(this, "账号或密码为空!", "错误",JOptionPane.ERROR_MESSAGE);
                } else if(name.length() > 16 || password.length() > 16){
                    JOptionPane.showMessageDialog(this, "账号或密码长度过长（最大长度不超过16）！");
                } else {
                    User u = new User();
                    u.setName(name);
                    u.setPassword(password);
                    u.setSex(sex);
                    UserDAO uDAO = new UserDAOImpl();
                    if(uDAO.add(u)){
                        JOptionPane.showMessageDialog(this, "注册成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
                        this.dispose();
                    } else {
                        JOptionPane.showMessageDialog(this, "注册失败！\n原因：账号重复！", "错误", JOptionPane.ERROR_MESSAGE);
                    }
                }
                break;
            case "cancel":
                this.dispose();
                break;
        }
    }
}
