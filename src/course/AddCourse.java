package course;

// 增加课程记录

import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddCourse extends JPanel implements ActionListener {
    JTextField Cno, Cname, Cpno, Ccredit;
    JButton addButton;

    // 实现增加课程记录界面
    public AddCourse() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        Cno = new JTextField(12);
        Cname = new JTextField(12);
        Cpno = new JTextField(12);
        Ccredit = new JTextField(12);
        addButton = new JButton("录入");
        addButton.addActionListener(this);

        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();

        JLabel title = new JLabel("增加课程信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);
        box1.add(new JLabel("课程号:", JLabel.CENTER));
        box1.add(Cno);
        box2.add(new JLabel("课程名:", JLabel.CENTER));
        box2.add(Cname);
        box3.add(new JLabel("先行课:", JLabel.CENTER));
        box3.add(Cpno);
        box4.add(new JLabel("学  分:", JLabel.CENTER));
        box4.add(Ccredit);
        box5.add(addButton);

        Box boxH = Box.createVerticalBox();
        boxH.add(box1);
        boxH.add(box2);
        boxH.add(box3);
        boxH.add(box4);
        boxH.add(box5);
        boxH.add(Box.createVerticalGlue());
        JPanel messPanel1 = new JPanel();
        JPanel messPanel2 = new JPanel();
        messPanel1.add(box0);
        messPanel2.add(boxH);
        setLayout(new BorderLayout());
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);// 分割
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现增加课程记录事件响应
    public void actionPerformed(ActionEvent c) {
        Object obj = c.getSource();
        if (obj == addButton) {
            if (Cno.getText().equals("") || Cname.getText().equals("") || Ccredit.getText().equals("")) {  // 先行课可以不存在
                JOptionPane.showMessageDialog(this, "请完善信息后再添加！");
            }
            // 执行SQL语句并返回结果集
            Statement stmt = null;
            ResultSet rs = null;
            String sql1, sql2;
            sql1 = "select * from course where Cno='" + Cno.getText() + "'";
            if (Cpno.getText().equals("")) {  // 对先行课为空值的处理
                sql2 = "insert into course values('" + Cno.getText() + "','" + Cname.getText() + "',NULL," + Ccredit.getText() + ")";
            } else {
                sql2 = "insert into course values('" + Cno.getText() + "','" + Cname.getText() + "','" + Cpno.getText() + "'," + Ccredit.getText() + ")";
            }
            System.out.print(sql1 + "\n");
            System.out.print(sql2 + "\n");
            try {
                Connection dbConn = ConnectSql.CONN();
                stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                        ResultSet.CONCUR_READ_ONLY);
                rs = stmt.executeQuery(sql1);
                if (rs.next()) {
                    JOptionPane.showMessageDialog(this, "该课程号已存在，无法添加！");
                } else {
                    stmt.executeUpdate(sql2);
                    Cno.setText("");
                    Cname.setText("");
                    Cpno.setText("");
                    Ccredit.setText("");
                    JOptionPane.showMessageDialog(this, "添加完成！");
                }
                rs.close();

                stmt.close();
            } catch (SQLException e) {
                System.out.print("SQL Exception:" + e.getMessage());
            }
        }
    }
}
