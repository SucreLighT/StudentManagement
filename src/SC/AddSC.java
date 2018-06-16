package SC;

// 增加选课记录

import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddSC extends JPanel implements ActionListener {
    JTextField Sno, Cno, Grade;
    JButton addButton;

    public AddSC() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        Sno = new JTextField(12);
        Cno = new JTextField(12);
        Grade = new JTextField(12);
        addButton = new JButton("录入");
        addButton.addActionListener(this);

        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();

        JLabel title = new JLabel("增加选课信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);

        box1.add(new JLabel("学  号:", JLabel.CENTER));
        box1.add(Sno);
        box2.add(new JLabel("课程名:", JLabel.CENTER));
        box2.add(Cno);
        box3.add(new JLabel("成  绩:", JLabel.CENTER));
        box3.add(Grade);
        box4.add(addButton);

        Box boxH = Box.createVerticalBox();
        boxH.add(box1);
        boxH.add(box2);
        boxH.add(box3);
        boxH.add(box4);
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

    public void actionPerformed(ActionEvent c) {
        Object obj = c.getSource();
        if (obj == addButton) {
            if (Sno.getText().equals("") || Cno.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "学号和课程号为必填项！");
            } else {
                Statement stmt = null;
                ResultSet rs1 = null, rs2 = null,rs3 = null ;
                String sql1, sql2, sql3, sql4;

                sql1 = "select * from student where Sno='" + Sno.getText() + "'";
                sql2 = "select * from course where Cno='" + Cno.getText() + "'";
                sql3 = "select * from sc where Sno='" + Sno.getText() + "' and Cno='" + Cno.getText() + "'";
                System.out.print(sql1+"\n");
                System.out.print(sql2+"\n");
                System.out.print(sql3+"\n");
                try {
                    Connection dbConn = ConnectSql.CONN();
                    stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs1 = stmt.executeQuery(sql1);
                    if (rs1.next()) {           // 存在该学生记录
                        rs2 = stmt.executeQuery(sql2);
                        if (rs2.next()) {       // 存在该课程记录
                            rs3 = stmt.executeQuery(sql3);
                            if (rs3.next()) {   // 存在该选课记录
                                Sno.setText("");
                                Cno.setText("");
                                Grade.setText("");
                                JOptionPane.showMessageDialog(this, "该选课记录已存在，无法添加！");
                            } else {            // 不存在该选课记录
                                if(Grade.getText().equals("")){ // 对成绩为空值的处理
                                    sql4 = "insert into sc values('" + Sno.getText() + "','" + Cno.getText() + "',NULL)";
                                }
                                else {
                                    sql4 = "insert into sc values('" + Sno.getText() + "','" + Cno.getText() + "','" + Grade.getText() + "')";
                                }
                                System.out.print(sql4+"\n");

                                stmt.executeUpdate(sql4);
                                Sno.setText("");
                                Cno.setText("");
                                Grade.setText("");
                                JOptionPane.showMessageDialog(this, "添加成功！");
                            }
                            rs3.close();
                        } else {
                            Sno.setText("");
                            Cno.setText("");
                            Grade.setText("");
                            JOptionPane.showMessageDialog(this, "该课程记录不存在，无法添加！");
                        }
                        rs2.close();
                    } else {
                        Sno.setText("");
                        Cno.setText("");
                        Grade.setText("");
                        JOptionPane.showMessageDialog(this, "该学生记录不存在，无法添加！");
                    }
                    rs1.close();
                    stmt.close();
                } catch (SQLException e) {
                    System.out.print("SQL Exception:" + e.getMessage());
                }
            }
        }
    }
}
