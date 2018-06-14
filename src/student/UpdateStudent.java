package student;

// 修改学生记录

import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateStudent extends JPanel implements ActionListener {
    String save = null;
    JTextField findSno, Sno, Sname, Sdept, Sage, Ssex;
    JButton findButton, updateButton;

    // 实现修改学生记录界面
    public UpdateStudent() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        findSno = new JTextField(12);
        Sno = new JTextField(12);
        Sname = new JTextField(12);
        Sage = new JTextField(12);
        Ssex = new JTextField(12);
        Sdept = new JTextField(12);
        findButton = new JButton("查找");
        updateButton = new JButton("修改");

        Box boxTitle = Box.createHorizontalBox();
        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createHorizontalBox();
        Box box7 = Box.createHorizontalBox();

        JLabel title = new JLabel("修改学生信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        boxTitle.add(title);
        box0.add(new JLabel("学号:", JLabel.CENTER));
        box0.add(findSno);
        box0.add(findButton);
        box1.add(new JLabel("学号:", JLabel.CENTER));
        box1.add(Sno);
        box2.add(new JLabel("姓名:", JLabel.CENTER));
        box2.add(Sname);
        box3.add(new JLabel("性别:", JLabel.CENTER));
        box3.add(Ssex);
        box4.add(updateButton);
        box5.add(new JLabel("年龄:", JLabel.CENTER));
        box5.add(Sage);
        box6.add(new JLabel("系别:", JLabel.CENTER));
        box6.add(Sdept);
        box7.add(updateButton);

        updateButton.addActionListener(this);
        findButton.addActionListener(this);

        Box boxH = Box.createVerticalBox();
        boxH.add(box0);
        boxH.add(box1);
        boxH.add(box2);
        boxH.add(box3);
        boxH.add(box4);
        boxH.add(box5);
        boxH.add(box6);
        boxH.add(box7);

        boxH.add(Box.createVerticalGlue());

        JPanel messPanel1 = new JPanel();
        JPanel messPanel2 = new JPanel();
        messPanel1.add(boxTitle);
        messPanel2.add(boxH);
        setLayout(new BorderLayout());
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);// 分割
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现修改学生记录事件响应
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Statement stmt = null;
        ResultSet rs = null;
        String sql1 = null, sql2 = null;


        // 执行SQL语句并返回结果集
        if (obj == findButton) {
            if (findButton.getText().equals(""))
                JOptionPane.showMessageDialog(this, "请填写查询的学号！");
            else {
                // 查找是否存在该学生记录
                sql1 = "select * from student where Sno='" + findSno.getText() + "'";
                System.out.print(sql1 + "\n");
                try {
                    Connection dbConn1 = ConnectSql.CONN();
                    stmt = dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs = stmt.executeQuery(sql1);
                    // 存在该学生记录并显示
                    if (rs.next()) {
                        Sno.setText(rs.getString("Sno").trim());
                        Sname.setText(rs.getString("Sname").trim());
                        Ssex.setText(rs.getString("Ssex").trim());
                        Sage.setText(rs.getString("Sage").trim());
                        Sdept.setText(rs.getString("Sdept").trim());
                        save = findSno.getText().trim();
                    } else {
                        Sno.setText("");
                        Sname.setText("");
                        Ssex.setText("");
                        Sage.setText("");
                        Sdept.setText("");
                        JOptionPane.showMessageDialog(this, "无该学号的学生记录！");
                    }
                    stmt.close();
                    rs.close();
                } catch (SQLException e1) {
                    System.out.print("SQL Exception occur.Message is:" + e1.getMessage());
                }
            }
        } else {
            if (obj == updateButton) {
                if (save == null) {
                    JOptionPane.showMessageDialog(this, "未查找到待修改的学生记录！");
                } else {
                    if (Sno.getText().equals("") || Sname.getText().equals("") || Ssex.getText().equals("") || Sage.getText().equals("")
                            || Sdept.getText().equals("")) {    // 保证student表中每个字段都是非空
                        JOptionPane.showMessageDialog(this, "请完善信息后再修改！");
                    } else {
                        // 修改该学生记录
                        sql2 = "update student set Sno='" + Sno.getText() + "',Sname='" + Sname.getText() + "',Ssex='" + Ssex.getText()
                                + "',Sage='" + Sage.getText() + "',Sdept='" + Sdept.getText() + "'" + "where Sno='" + save + "'";
                        System.out.print(sql2 + "\n");
                        try {
                            Connection dbConn1 = ConnectSql.CONN();
                            stmt = dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            stmt.executeUpdate(sql2);
                            save = null;
                            Sno.setText("");
                            Sname.setText("");
                            Sage.setText("");
                            Ssex.setText("");
                            Sdept.setText("");
                            JOptionPane.showMessageDialog(this, "修改完成!");

                            stmt.close();
                        } catch (SQLException e1) {
                            System.out.print("SQL Exception:" + e1.getMessage());
                        }
                    }
                }
            }
        }
    }

}
