package SC;

//查询选课实现类

import user.ConnectSql;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class ShowSCByCou extends JFrame {
    Vector rowData, columnNames;
    Statement stmt = null;
    String sql = null;
    JTable jt = null;
    JScrollPane jsp = null;
    ResultSet rs = null;

    public ShowSCByCou(String sql1) {
        super("查询结果");
        columnNames = new Vector();
        // 设置列名
        columnNames.add("课程号");
        columnNames.add("课程名");
        columnNames.add("先行课");
        columnNames.add("学  分");
        columnNames.add("学  号");
        columnNames.add("姓  名");
        columnNames.add("成  绩");

        rowData = new Vector();
        sql = sql1;
        try {
            Connection dbConn = ConnectSql.CONN();
            stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("course.Cno"));
                row.add(rs.getString("Cname"));
                row.add(rs.getString("Cpno"));
                row.add(rs.getString("Ccredit"));
                row.add(rs.getString("sc.Sno"));
                row.add(rs.getString("student.Sname"));
                row.add(rs.getString("sc.Grade"));

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
