package student;

// 显示查询学生结果类

import user.ConnectSql;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class ShowStudent extends JFrame {
    Vector rowData, columnNames;
    Statement stmt = null;

    String sql = null;
    JTable jt = null;
    JScrollPane jsp = null;
    ResultSet rs = null;

    public ShowStudent(String sql1) {
        super("查询结果");
        columnNames = new Vector();
        // 设置列名
        columnNames.add("学号");
        columnNames.add("姓名");
        columnNames.add("年龄");
        columnNames.add("性别");
        columnNames.add("系别");

        rowData = new Vector();
        sql = sql1;
        try {
            Connection dbConn1 = ConnectSql.CONN();
            stmt = dbConn1.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);

                while (rs.next()) {
                    Vector row = new Vector();
                    row.add(rs.getString("Sno"));
                    row.add(rs.getString("Sname"));
                    row.add(rs.getString("Ssex"));
                    row.add(rs.getString("Sage"));
                    row.add(rs.getString("Sdept"));
                    rowData.add(row);
                }
                if(rowData.isEmpty())
                    JOptionPane.showMessageDialog(this, "不存在记录！");
                else {
                    jt = new JTable(rowData, columnNames);
                    jsp = new JScrollPane(jt);
                    this.add(jsp);
                    this.setSize(400, 300);
                    this.setVisible(true);
                    this.setLocationRelativeTo(null);
                }
        } catch (SQLException e1) {
            System.out.print("SQL Exception:" + e1.getMessage());
        }
    }


}
