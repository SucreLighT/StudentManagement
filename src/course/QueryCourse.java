package course;

// 查询课程实现类

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QueryCourse extends JPanel implements ActionListener {
    JTextField Cno, Cname, Cpno, Ccredit;
    JButton findButton, cancelButton;

    // 实现查询课程界面
    public QueryCourse() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        Cno = new JTextField(12);
        Cname = new JTextField(12);
        Cpno = new JTextField(12);
        Ccredit = new JTextField(12);

        findButton = new JButton("查询");
        findButton.addActionListener(this);
        cancelButton = new JButton("取消");
        cancelButton.addActionListener(this);

        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();

        JLabel title = new JLabel("查询课程信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);
        box1.add(new JLabel("课程号:"));
        box1.add(Cno);
        box2.add(new JLabel("课程名:"));
        box2.add(Cname);
        box3.add(new JLabel("先行课:"));
        box3.add(Cpno);
        box4.add(new JLabel("学  分:"));
        box4.add(Ccredit);
        box5.add(findButton);
        box5.add(cancelButton);

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
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现查询课程功能
    // 预计实现查询功能：根据课程号查询唯一课程记录，根据课程名查询唯一课程记录，根据先行课和学分查询
    public void actionPerformed(ActionEvent c) {
        Object obj = c.getSource();
        String sql = null;

        if (obj == findButton) {
            // 查询所有记录
            if (Cno.getText().equals("") && Cname.getText().equals("") && Cpno.getText().equals("") && Ccredit.getText().equals("")) {
                sql = "select * from course ";
                System.out.print("查询所有记录：" + sql + "\n");
            } else {
                // 根据课程号查询唯一课程
                if (!Cno.getText().equals("")) {
                    if (Cname.getText().equals("") && Cpno.getText().equals("") && Ccredit.getText().equals("")) {    // 其他字段为空值
                        sql = "select * from course where Cno='" + Cno.getText() + "'";
                    } else {
                        Cno.setText("");
                        Cname.setText("");
                        Cpno.setText("");
                        Ccredit.setText("");
                        JOptionPane.showMessageDialog(this, "不支持此类查询！");
                    }
                    System.out.print("根据课程号查询课程信息：" + sql + "\n");
                } else {
                    if (!Cname.getText().equals("")) {  // 课程名不为空值
                        if (Cpno.getText().equals("") && Ccredit.getText().equals("")) {  // 其他字段为空值
                            // 根据课程名查询唯一课程
                            sql = "select * from course where Cname='" + Cname.getText() + "'";
                        } else {
                            Cno.setText("");
                            Cname.setText("");
                            Cpno.setText("");
                            Ccredit.setText("");
                            JOptionPane.showMessageDialog(this, "不支持此类查询！");
                        }
                        System.out.print("根据课程名查询课程信息：" + sql + "\n");
                    } else {
                        if (!Cpno.getText().equals("")) {    // 根据先行课查询
                            if (!Ccredit.getText().equals("")) {   // 学分不为空值
                                // 根据先行课和学分查询
                                sql = "select * from course where Cpno='" + Cpno.getText() + "' and Ccredit='" + Integer.parseInt(Ccredit.getText()) + "'";
                                System.out.print("根据先行课和学分查询课程：" + sql + "\n");
                            } else {     // 学分字段为空值
                                // 仅查询先行课
                                sql = "select * from course where Cpno='" + Cpno.getText() + "'";
                                System.out.print("根据先行课查询课程：" + sql + "\n");
                            }
                        } else {  // 仅有学分字段有值，根据学分字段查询课程
                            sql = "select * from course where Ccredit='" + Integer.parseInt(Ccredit.getText()) + "'";
                            System.out.print("根据学分查询课程：" + sql + "\n");
                        }
                    }

                }
            }
            ShowCourse showCourse = new ShowCourse(sql);
        } else {
            if (obj == cancelButton) {
                Cno.setText("");
                Cname.setText("");
                Cpno.setText("");
                Ccredit.setText("");
            }
        }
    }
}

