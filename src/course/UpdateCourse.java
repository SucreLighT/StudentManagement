package course;

// 修改课程记录

import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class UpdateCourse extends JPanel implements ActionListener {
    String save = null;
    JTextField findCno, Cno, Cname, Cpno, Ccredit;
    JButton findButton, updateButton, cancelButton;

    // 实现修改课程记录界面
    public UpdateCourse() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        findCno = new JTextField(12);
        Cno = new JTextField(12);
        Cname = new JTextField(12);
        Cpno = new JTextField(12);
        Ccredit = new JTextField(12);
        findButton = new JButton("查找");
        updateButton = new JButton("修改");
        cancelButton = new JButton("取消");


        Box boxTitle = Box.createHorizontalBox();
        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();

        JLabel title = new JLabel("修改课程信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        boxTitle.add(title);

        box0.add(new JLabel("课程号:", JLabel.CENTER));
        box0.add(findCno);
        box0.add(findButton);
        box1.add(new JLabel("课程号:", JLabel.CENTER));
        box1.add(Cno);
        box2.add(new JLabel("课程名:", JLabel.CENTER));
        box2.add(Cname);
        box3.add(new JLabel("先行课:", JLabel.CENTER));
        box3.add(Cpno);
        box4.add(new JLabel("学  分:", JLabel.CENTER));
        box4.add(Ccredit);
        box5.add(updateButton);
        box5.add(cancelButton);
        updateButton.addActionListener(this);
        findButton.addActionListener(this);
        cancelButton.addActionListener(this);
        Box boxH = Box.createVerticalBox();
        boxH.add(box0);
        boxH.add(box1);
        boxH.add(box2);
        boxH.add(box3);
        boxH.add(box4);
        boxH.add(box5);

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

    // 实现修改课程记录事件响应
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Statement stmt = null;
        ResultSet rs = null;
        String sql1 = null, sql2 = null;

        // 执行SQL语句并返回结果集
        if (obj == findButton) {
            if (findCno.getText().equals(""))
                JOptionPane.showMessageDialog(this, "请填写查询的课程号！");
            else {
                sql1 = "select * from course where Cno='" + findCno.getText() + "'";
                System.out.print(sql1 + "\n");
                try {
                    Connection dbConn = ConnectSql.CONN();
                    stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs = stmt.executeQuery(sql1);
                    if (rs.next()) {
                        Cno.setText(rs.getString("Cno").trim());
                        Cname.setText(rs.getString("Cname").trim());
                        Cpno.setText(rs.getString("Cpno")); // 由于先行课可能为空，使用trim函数会出现异常
                        Ccredit.setText(rs.getString("Ccredit").trim());
                        save = findCno.getText().trim();
                    } else {
                        findCno.setText("");
                        Cno.setText("");
                        Cname.setText("");
                        Cpno.setText("");
                        Ccredit.setText("");
                        JOptionPane.showMessageDialog(this, "无该课程号的课程记录！");
                    }
                    stmt.close();
                    rs.close();
                } catch (SQLException e1) {
                    System.out.print("SQL Exception:" + e1.getMessage());
                }
            }
        } else {
            if (obj == updateButton) {
                if (save == null) {
                    JOptionPane.showMessageDialog(this, "未查找到待修改的课程记录！");
                } else {
                    if (Cno.getText().equals("") || Cname.getText().equals("") || Ccredit.getText().equals("")) {
                        JOptionPane.showMessageDialog(this, "请完善信息后再修改！");
                    } else {
                        if (Cpno.getText().equals("")) {  // 对先行课为空值的处理
                            sql2 = "update course set Cno='" + Cno.getText() + "',Cname='" + Cname.getText() + "'," +
                                    "Cpno = NULL,Ccredit='" + Ccredit.getText() + "' where Cno='" + save + "'";
                        } else {
                            sql2 = "update course set Cno='" + Cno.getText() + "',Cname='" + Cname.getText() + "',Cpno='"
                                    + Cpno.getText() + "',Ccredit='" + Ccredit.getText() + "' where Cno='" + save + "'";
                        }
                        System.out.print(sql2 + "\n");
                        try {
                            Connection dbConn = ConnectSql.CONN();
                            stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                    ResultSet.CONCUR_READ_ONLY);
                            stmt.executeUpdate(sql2);
                            save = null;
                            findCno.setText("");
                            Cno.setText("");
                            Cname.setText("");
                            Cpno.setText("");
                            Ccredit.setText("");
                            JOptionPane.showMessageDialog(this, "修改完成！");

                            stmt.close();
                        } catch (SQLException e1) {
                            System.out.print("SQL Exception:" + e1.getMessage());
                        }
                    }
                }
            }else {
                if(obj == cancelButton){
                    findCno.setText("");
                    Cno.setText("");
                    Cname.setText("");
                    Cpno.setText("");
                    Ccredit.setText("");
                }
            }
        }
    }
}
