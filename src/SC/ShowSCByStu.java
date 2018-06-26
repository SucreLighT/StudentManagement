package SC;

//查询选课实现类

import user.ConnectSql;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class ShowSCByStu extends JFrame {
    Vector rowData, columnNames;
    Statement stmt = null;
    String sql = null;
    JTable jt = null;
    JScrollPane jsp = null;
    ResultSet rs = null;

    public ShowSCByStu(String sql1) {
        super("查询结果");
        columnNames = new Vector();
        // 设置列名
        columnNames.add("学  号");
        columnNames.add("姓  名");
        columnNames.add("性  别");
        columnNames.add("年  龄");
        columnNames.add("系  别");
        columnNames.add("课程号");
        columnNames.add("成  绩");

        rowData = new Vector();
        sql = sql1;
        try {
            Connection dbConn = ConnectSql.CONN();
            stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("student.Sno"));
                row.add(rs.getString("Sname"));
                row.add(rs.getString("Ssex"));
                row.add(rs.getString("Sage"));
                row.add(rs.getString("Sdept"));
                row.add(rs.getString("sc.Cno"));
                row.add(rs.getString("Grade"));

                rowData.add(row);
            }
            if (rowData.isEmpty())
                JOptionPane.showMessageDialog(this, "不存在记录！");
            else {
                jt = new JTable(rowData, columnNames);
                jsp = new JScrollPane(jt);
                this.add(jsp);
                this.setSize(500, 400);
                this.setVisible(true);
                this.setLocationRelativeTo(null);
            }
        } catch (SQLException e1) {
            System.out.print("SQL Exception:" + e1.getMessage());
        }
    }
}
