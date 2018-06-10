package user;

// 用户登录

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;


public class User extends JFrame {
    private JLabel username, password;
    private JTextField usernametext;// 用户名输入框
    private JPasswordField passwordtext;// 密码输入框
    private JButton loginbutton, exitbutton;

    // 登录窗口
    public User(JFrame f) {
        super("系统登录");
        Container c = getContentPane();     // 设置容器
        c.setLayout(new FlowLayout());      // 设置流式布局

        username = new JLabel("用户名:");
        username.setFont(new Font("Serif", Font.PLAIN, 20));
        password = new JLabel("密   码:");
        password.setFont(new Font("Serif", Font.PLAIN, 20));
        usernametext = new JTextField(12);
        passwordtext = new JPasswordField(12);
        loginbutton = new JButton("登录");
        exitbutton = new JButton("退出");

        // 设置登录和退出方法
        BHandler l = new BHandler();
        EXIT e = new EXIT();
        loginbutton.addActionListener(l);
        exitbutton.addActionListener(e);

        // 添加控件
        c.add(username);
        c.add(usernametext);
        c.add(password);
        c.add(passwordtext);
        c.add(loginbutton);
        c.add(exitbutton);

        setBounds(600, 300, 250, 150);  // 设置窗体大小
        setVisible(true);
        setResizable(false);                               // 设置窗体不可拉伸
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // 设置窗体关闭操作
    }

    // 主函数
    public static void main(String[] args) {
        User f1 = new User(new JFrame());
    }

    // 登录方法
    private class BHandler implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            if (usernametext.getText().equals("") || String.valueOf(passwordtext.getPassword()).equals("")) {
                JOptionPane.showMessageDialog(User.this, "用户名和密码不能为空！");
            } else {
                Statement stmt = null;
                ResultSet rs = null;

                // 数据库sql查询并返回结果集
                String sql;
                sql = "select * from admin where username='" + usernametext.getText() + "'";

                try {
                    Connection dbConn = ConnectSql.CONN();
                    stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY); // 设置结果集游标可上下移动，对数据库表只读
                    rs = stmt.executeQuery(sql);         // 返回结果集

                    if (rs.next()) {
                        // 如果结果集中password字段存在和输入框中相同的记录，则登录成功，trim函数用于去除空格

                        if (String.valueOf(passwordtext.getPassword()).equals(rs.getString("password").trim())) {
                            JOptionPane.showMessageDialog(User.this, "登录成功!");
                            dispose();  // 释放当前屏幕
                            new Menu(); // 显示菜单
                        } else {
                            JOptionPane.showMessageDialog(User.this, "密码错误!");
                        }
                    } else {
                        JOptionPane.showMessageDialog(User.this, "用户名错误!");
                    }
                    rs.close();
                    stmt.close();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(User.this, "SQL Exception:" + e.getMessage());
                }
            }
        }
    }

    // 退出方法
    private class EXIT implements ActionListener {
        public void actionPerformed(ActionEvent even) {
            System.exit(0);
        }
    }
}

