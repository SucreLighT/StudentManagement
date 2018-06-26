package SC;

// 查询选课实现类

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class QuerySC extends JPanel implements ActionListener {
    JTextField Sno, Cno, Grade;
    JButton findButton;

    // 实现选课查询界面
    public QuerySC() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        Sno = new JTextField(12);
        Cno = new JTextField(12);
        Grade = new JTextField(12);

        findButton = new JButton("查询");
        findButton.addActionListener(this);

        Box box0 = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();

        JLabel title = new JLabel("查询选课信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        box0.add(title);
        box1.add(new JLabel("学  号:"));
        box1.add(Sno);
        box2.add(new JLabel("课程号:"));
        box2.add(Cno);
        box3.add(new JLabel("成  绩:"));
        box3.add(Grade);
        box4.add(findButton);

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
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现选课查询功能
    // 预计实现功能：查询所有记录，根据学号查询某学生所有选课记录，根据课程号查询选修该课程的所有学生(按成绩降序排列)
    public void actionPerformed(ActionEvent c) {
        Object obj = c.getSource();
        String sql = null;

        if (obj == findButton) {
            if (Sno.getText().equals("") && Cno.getText().equals("") && Grade.getText().equals("")) {
                // 查询所有记录
                sql = "select student.*,sc.* from student,sc where student.Sno=sc.Sno";
                System.out.print("查询所有记录" + sql + "\n");
                ShowSCAll ShowSCAll = new ShowSCAll(sql);
            } else {
                if (!Sno.getText().equals("")) {  // 学号字段不为空值
                    if (Cno.getText().equals("") && Grade.getText().equals("")) {    // 其余字段为空值
                        // 根据学号查询某学生所有选课记录
                        sql = "select student.*,sc.* from student,sc where student.Sno='" + Sno.getText() + "' and student.Sno=sc.Sno ";
                        System.out.print("根据学号查询某学生所有选课记录：" + sql + "\n");
                        ShowSCByStu ShowSCByStu = new ShowSCByStu(sql);
                    } else {
                        Sno.setText("");
                        Cno.setText("");
                        Grade.setText("");
                        JOptionPane.showMessageDialog(this, "不支持此类查询！");
                    }
                } else {  // 学号字段为空值
                    if (!Cno.getText().equals("")) {  // 课程号不为空
                        if (Grade.getText().equals("")) { // 成绩字段为空值
                            // 根据课程号查询选修该课程的所有学生(按成绩降序排列)
                            sql = "select sc.*,course.*,student.Sname from course,sc,student where course.Cno='" + Cno.getText() +
                                    "' and course.Cno=sc.Cno and sc.Sno=student.Sno order by Grade desc";
                            System.out.print("根据课程号查询选修该课程的学生：" + sql + "\n");
                            ShowSCByCou ShowSCByCou = new ShowSCByCou(sql);
                        } else {
                            Sno.setText("");
                            Cno.setText("");
                            Grade.setText("");
                            JOptionPane.showMessageDialog(this, "不支持此类查询！");
                        }
                    } else {
                        Sno.setText("");
                        Cno.setText("");
                        Grade.setText("");
                        JOptionPane.showMessageDialog(this, "不支持此类查询！");
                    }
                }
            }

        }
    }
}

