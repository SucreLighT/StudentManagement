package student;

// 删除学生记录

import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeleteStudent extends JPanel implements ActionListener {
    String save = null;
    JTextField findSno, Sno, Sname, Ssex, Sage, Sdept;
    JButton findButton, delButton;

    // 实现删除学生界面设计
    public DeleteStudent() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        findSno = new JTextField(12);
        Sno = new JTextField(12);
        Sname = new JTextField(12);
        Ssex= new JTextField(12);
        Sage = new JTextField(12);
        Sdept = new JTextField(12);
        findButton = new JButton("查找");
        delButton = new JButton("删除");

        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createHorizontalBox();
        Box box7 = Box.createHorizontalBox();
        Box box8 = Box.createHorizontalBox();


        JLabel title = new JLabel("删除学生信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);
        box1.add(new JLabel("学号:", JLabel.CENTER));
        box1.add(findSno);
        box2.add(findButton);
        box3.add(new JLabel("学号:"));
        box3.add(Sno);
        box4.add(new JLabel("姓名:"));
        box4.add(Sname);
        box5.add(new JLabel("性别:"));
        box5.add(Ssex);
        box6.add(new JLabel("年龄:"));
        box6.add(Sage);
        box7.add(new JLabel("系别:"));
        box7.add(Sdept);
        box8.add(delButton);

        Box boxH1 = Box.createVerticalBox();
        boxH1.add(box0);
        boxH1.add(box1);
        boxH1.add(box2);
        Box boxH2 = Box.createVerticalBox();
        boxH2.add(box3);
        boxH2.add(box4);
        boxH2.add(box5);
        boxH2.add(box6);
        boxH2.add(box7);
        boxH2.add(box8);
        boxH2.add(Box.createVerticalGlue());

        delButton.addActionListener(this);
        findButton.addActionListener(this);

        JPanel messPanel2 = new JPanel();
        JPanel messPanel1 = new JPanel();
        messPanel1.add(boxH1);

        messPanel2.add(boxH2);
        setLayout(new BorderLayout());
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);// 分割
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现删除学生事件响应
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Statement stmt = null;
        ResultSet rs = null;
        String sql1 = null, sql2 = null, sql3 = null;


        // 执行SQL语句并返回结果集
        if (obj == findButton) {
            if (findSno.getText().equals(""))
                JOptionPane.showMessageDialog(this, "请输入待删除的学生学号！");
            else {
                sql1 = "select * from student where Sno='" + findSno.getText() + "'";
                try {
                    Connection dbConn = ConnectSql.CONN();
                    stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs = stmt.executeQuery(sql1);
                    // 将查询到的结果输出到显示框中
                    if (rs.next()) {
                        Sno.setText(rs.getString("Sno").trim());
                        Sname.setText(rs.getString("Sname").trim());
                        Ssex.setText(rs.getString("Ssex").trim());
                        Sage.setText(rs.getString("Sage").trim());
                        Sdept.setText(rs.getString("Sdept").trim());
                        save = findSno.getText().trim();
                    } else {
                        JOptionPane.showMessageDialog(this, "无该学号的学生记录！");
                    }
                    stmt.close();
                    rs.close();
                } catch (SQLException e1) {
                    System.out.print("SQL Exception:" + e1.getMessage());
                }
            }
        } else {
            if (obj == delButton) {
                if (save == null)
                    JOptionPane.showMessageDialog(this, "未查找到待删除的学生记录！");
                else {
                    // 删除该学生记录，由于外键约束先删除sc表中该学生记录
                    sql2 = "delete from sc where Sno='" + save + "'";
                    sql3 = "delete from student where Sno='" + save + "'";
                    try {
                        Connection dbConn = ConnectSql.CONN();
                        stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        stmt.executeUpdate(sql2);
                        stmt.executeUpdate(sql3);
                        save = null;
                        JOptionPane.showMessageDialog(this, "删除完成！");
                        Sno.setText("");
                        Sname.setText("");
                        Ssex.setText("");
                        Sage.setText("");
                        Sdept.setText("");

                        stmt.close();
                    } catch (SQLException e1) {
                        System.out.print("SQL Exception occur.Message is:" + e1.getMessage());
                    }
                }
            }
        }
    }
}
