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

    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Statement stmt = null;
        ResultSet rs = null, rs1 = null;
        String sql = null, sql1 = null, sqlSC;

        if (obj == findButton) {
            if (findButton.getText().equals(""))
                JOptionPane.showMessageDialog(this, "请填写查询的学号！");
            else {

                sql1 = "select * from S where Sno='" + findSno.getText() + "'";
                try {
                    Connection dbConn1 = ConnectSql.CONN();
                    stmt = (Statement) dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs1 = stmt.executeQuery(sql1);
                    if (rs1.next()) {
                        Sno.setText(rs1.getString("Sno").trim());
                        Sname.setText(rs1.getString("Sname").trim());
                        Sage.setText(rs1.getString("Sa").trim());
                        Ssex.setText(rs1.getString("Sg").trim());
                        Sdept.setText(rs1.getString("Sx").trim());
                        save = findSno.getText();
                    } else {
                        JOptionPane.showMessageDialog(this, "没有这个学号的学生");
                    }
                    stmt.close();
                    rs1.close();
                } catch (SQLException e1) {
                    System.out.print("SQL Exception occur.Message is:" + e1.getMessage());
                }
            }
        } else {
            if (obj == updateButton) {
                if (save == null) {
                    JOptionPane.showMessageDialog(this, "还没查找需要修改的学生");
                } else {
                    if (Sno.getText().equals("") || Sname.getText().equals("") || Sage.getText().equals("")
                            || Ssex.getText().equals("") || Sdept.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "学生信息填满才能修改！");
                    } else {
                        sql = "update S set Sno='" + Sno.getText() + "',Sname='" + Sname.getText() + "',Sa='" + Sage.getText()
                                + "',Sg='" + Ssex.getText() + "',Sx='" + Sdept.getText() + "'" + "where Sno='" + save + "'";
                        if (save.trim().equals(Sno.getText().trim())) {
                            try {
                                Connection dbConn1 = ConnectSql.CONN();
                                stmt = (Statement) dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
                                stmt.executeUpdate(sql);
                                save = null;
                                JOptionPane.showMessageDialog(this, "修改完成");
                                Sno.setText("");
                                Sname.setText("");
                                Sage.setText("");
                                Ssex.setText("");
                                Sdept.setText("");
                                stmt.close();
                            } catch (SQLException e1) {
                                System.out.print("SQL Exception occur.Message is:" + e1.getMessage());
                            }
                        } else {
                            sql1 = "select * from S where Sno='" + Sno.getText() + "'";
                            try {
                                Connection dbConn1 = ConnectSql.CONN();
                                stmt = (Statement) dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY);
                                rs1 = stmt.executeQuery(sql1);
                                if (rs1.next()) {
                                    JOptionPane.showMessageDialog(this, "已存在此学号学生");
                                } else {
                                    sqlSC = "update SC set Sno='" + Sno.getText() + "' where Sno='" + save + "'";
                                    stmt.executeUpdate(sql);
                                    stmt.executeUpdate(sqlSC);
                                    save = null;
                                    JOptionPane.showMessageDialog(null, "修改完成");
                                    Sno.setText("");
                                    Sname.setText("");
                                    Sage.setText("");
                                    Ssex.setText("");
                                    Sdept.setText("");
                                }
                                stmt.close();
                                rs1.close();
                            } catch (SQLException e1) {
                                System.out.print("SQL Exception occur.Message is:" + e1.getMessage());
                            }

                        }

                    }
                }
            }
        }
    }

}
