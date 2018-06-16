package SC;

// 删除选课记录
import user.ConnectSql;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class DeleteSC extends JPanel implements ActionListener {
    String saveC = null;
    String saveS = null;
    JTextField findCno, findSno, Sno, Cno, Grade;
    JButton findButton,delButton;

    // 实现删除选课界面
    public DeleteSC() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Exception:" + e);
        }

        findSno = new JTextField(12);
        findCno = new JTextField(12);
        Cno = new JTextField(12);
        Sno = new JTextField(12);
        Grade = new JTextField(12);
        findButton = new JButton("查找");
        delButton = new JButton("删除");

        Box boxTitle = Box.createHorizontalBox();
        Box box1 = Box.createHorizontalBox();
        Box box2 = Box.createHorizontalBox();
        Box box3 = Box.createHorizontalBox();
        Box box4 = Box.createHorizontalBox();
        Box box5 = Box.createHorizontalBox();
        Box box6 = Box.createHorizontalBox();

        JLabel title = new JLabel("删除选课信息");
        title.setFont(new Font("宋体", Font.BOLD, 12));
        boxTitle.add(title);
        box1.add(new JLabel("学  号:", JLabel.CENTER));
        box1.add(findSno);
        box1.add(new JLabel("课程号:", JLabel.CENTER));
        box1.add(findCno);
        box2.add(findButton);
        box3.add(new JLabel("学  号:", JLabel.CENTER));
        box3.add(Sno);
        box4.add(new JLabel("课程名:", JLabel.CENTER));
        box4.add(Cno);
        box5.add(new JLabel("成  绩:", JLabel.CENTER));
        box5.add(Grade);
        box6.add(delButton);

        delButton.addActionListener(this);
        findButton.addActionListener(this);

        Box boxH = Box.createVerticalBox();

        boxH.add(box1);
        boxH.add(box2);
        boxH.add(box3);
        boxH.add(box4);
        boxH.add(box5);
        boxH.add(box6);

        boxH.add(Box.createVerticalGlue());

        JPanel messPanel1 = new JPanel();
        JPanel messPanel2 = new JPanel();
        messPanel1.add(boxTitle);
        messPanel2.add(boxH);
        setLayout(new BorderLayout());
        JSplitPane splitV = new JSplitPane(JSplitPane.VERTICAL_SPLIT, messPanel1, messPanel2);  // 分割
        splitV.setEnabled(false);
        add(splitV, BorderLayout.CENTER);
        validate();
    }

    // 实现删除选课记录事件响应
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        Statement stmt = null;
        ResultSet rs = null, rs1 = null;
        String sql1 = null, sql2 = null;

        if (obj == findButton) {
            if (findSno.getText().equals("") || findCno.getText().equals(""))
                JOptionPane.showMessageDialog(this, "学号和课程号为必填项！");
            else {
                sql1 = "select * from sc where Sno='" + findSno.getText() + "' and Cno='" + findCno.getText() + "'";
                System.out.print(sql1+"\n");
                try {
                    Connection dbConn = ConnectSql.CONN();
                    stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY);
                    rs1 = stmt.executeQuery(sql1);
                    if (rs1.next()) {
                        Sno.setText(rs1.getString("Sno").trim());
                        Cno.setText(rs1.getString("Cno").trim());
                        Grade.setText(rs1.getString("Grade"));
                        saveS = findSno.getText().trim();
                        saveC = findCno.getText().trim();
                    } else {
                        findSno.setText("");
                        findCno.setText("");
                        JOptionPane.showMessageDialog(this, "不存在该学号/课程的选课记录！");
                    }
                    stmt.close();
                    rs1.close();
                } catch (SQLException e1) {
                    System.out.print("SQL Exception:" + e1.getMessage());
                }
            }
        } else {
            if (obj == delButton) {
                if (saveC == null || saveS == null)
                    JOptionPane.showMessageDialog(this, "未查找到待删除的选课记录！");
                else {
                    sql2 = "delete from sc where Sno='" + saveS + "' and Cno='" + saveC + "'";
                    try {
                        Connection dbConn = ConnectSql.CONN();
                        stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_READ_ONLY);
                        stmt.executeUpdate(sql2);
                        saveS = null;
                        saveC = null;
                        JOptionPane.showMessageDialog(this, "删除完成！");
                        findSno.setText("");
                        findCno.setText("");
                        Cno.setText("");
                        Sno.setText("");
                        Grade.setText("");
                        stmt.close();
                    } catch (SQLException e1) {
                        System.out.print("SQL Exception:" + e1.getMessage());
                    }
                }
            }
        }
    }
}
