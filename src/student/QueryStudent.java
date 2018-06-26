package student;

// 查询学生类

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryStudent extends JPanel implements ActionListener {
    JTextField Sno, Sname, Ssex, SageMin, SageMax, Sdept;
    JButton findButton;

    // 实现查询学生界面
    public QueryStudent() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        Sno = new JTextField(12);
        Sname = new JTextField(12);
        Ssex = new JTextField(12);
        SageMin = new JTextField(12);
        SageMax = new JTextField(12);
        Sdept = new JTextField(12);
        findButton = new JButton("查询");
        findButton.addActionListener(this);

        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createHorizontalBox();
        Box box7 = Box.createHorizontalBox();

        JLabel title = new JLabel("查询学生信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);
        box1.add(new JLabel("学    号:"));
        box1.add(Sno);
        box2.add(new JLabel("姓    名:"));
        box2.add(Sname);
        box3.add(new JLabel("性    别:"));
        box3.add(Ssex);
        box4.add(new JLabel("年龄下限:"));
        box4.add(SageMin);
        box5.add(new JLabel("年龄上限:"));
        box5.add(SageMax);
        box6.add(new JLabel("系    别:"));
        box6.add(Sdept);
        box7.add(findButton);

        Box boxH = Box.createVerticalBox();
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
        messPanel1.add(box0);
        messPanel2.add(boxH);
        setLayout(new BorderLayout());
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现查询学生功能
    // 预计可实现：查询所有记录，根据学号查询唯一学生，根据姓名查询唯一学生，根据性别查询所有学生，根据系别查询所有，根据系别、年龄范围范围所有
    public void actionPerformed(ActionEvent c) {
        Object obj = c.getSource();

        String sql = null;

        if (obj == findButton) {
            // 查询所有记录
            if (Sno.getText().equals("") && Sname.getText().equals("") && Ssex.getText().equals("") &&
                    SageMin.getText().equals("") && SageMax.getText().equals("") && Sdept.getText().equals("")) {
                sql = "select * from student ";
                System.out.print("查询所有记录：" + sql + "\n");
            } else {
                // 根据学号查询唯一学生
                if (!Sno.getText().equals("")) {  // 学号不为空，查询指定该学号的学生
                    if (Sname.getText().equals("") && Ssex.getText().equals("") && SageMin.getText().equals("") &&
                            SageMax.getText().equals("") && Sdept.getText().equals("")) {   // 其他字段没有值
                        sql = "select * from student where Sno='" + Sno.getText() + "'";
                    } else {
                        Sno.setText("");
                        Sname.setText("");
                        Ssex.setText("");
                        SageMin.setText("");
                        SageMax.setText("");
                        Sdept.setText("");
                        JOptionPane.showMessageDialog(this, "不支持此类查询！");
                    }
                    System.out.print("根据学号查询学生信息：" + sql + "\n");
                } else {
                    if (!Sname.getText().equals("")) {  // 姓名不为空，查询指定该姓名的学生
                        if (Ssex.getText().equals("") && SageMin.getText().equals("") && SageMax.getText().equals("") &&
                                Sdept.getText().equals("")) {   // 其他字段没有值
                            sql = "select * from student where Sname='" + Sname.getText() + "'";
                        } else {
                            Sno.setText("");
                            Sname.setText("");
                            Ssex.setText("");
                            SageMin.setText("");
                            SageMax.setText("");
                            Sdept.setText("");
                            JOptionPane.showMessageDialog(this, "不支持此类查询！");
                        }
                        System.out.print("根据姓名查询学生信息：" + sql + "\n");
                    } else {
                        if (!Ssex.getText().equals("")) { // 性别字段有值
                            if (SageMin.getText().equals("") && SageMax.getText().equals("") && Sdept.getText().equals("")) {   // 其他字段没有值
                                // 根据性别查询所有学生
                                sql = "select * from student where Ssex='" + Ssex.getText() + "'";
                            } else {
                                Sno.setText("");
                                Sname.setText("");
                                Ssex.setText("");
                                SageMin.setText("");
                                SageMax.setText("");
                                Sdept.setText("");
                                JOptionPane.showMessageDialog(this, "不支持此类查询！");
                            }
                            System.out.print("根据性别查询学生信息：" + sql + "\n");
                        } else {
                            if (!Sdept.getText().equals("")) {    // 系别字段有值
                                if (SageMin.getText().equals("") && SageMax.getText().equals("")) {  // 年龄上下限字段没有值
                                    // 根据系别查询所在系所有学生
                                    sql = "select * from student where Sdept='" + Sdept.getText() + "'";
                                    System.out.print("根据系别查询所在系所有学生：" + sql + "\n");
                                } else {
                                    if (SageMin.getText().equals("")) {  // 年龄上限有值
                                        sql = "select * from student where Sdept='" + Sdept.getText() + "' and Sage<'" + Integer.parseInt(SageMax.getText()) + "'";
                                    } else if (SageMax.getText().equals("")) { // 年龄下限有值
                                        sql = "select * from student where Sdept='" + Sdept.getText() + "' and Sage>'" + Integer.parseInt(SageMin.getText()) + "'";
                                    } else { // 年龄上下限都有值
                                        sql = "select * from student where Sdept='" + Sdept.getText() + "' and Sage between " +
                                                Integer.parseInt(SageMin.getText()) + " and " + Integer.parseInt(SageMax.getText()) + "";
                                    }
                                    System.out.print("根据系别、年龄查询学生：" + sql + "\n");
                                }

                            }
                        }
                    }
                }
            }
            ShowStudent ShowStudent = new ShowStudent(sql);
        }
    }
}