package course;

//查询课程实现类
import user.ConnectSql;

import javax.swing.*;
import java.sql.*;
import java.util.Vector;

public class ShowCourse extends JFrame {
    Vector rowData, columnNames;
    Statement stmt = null;
    String sql = null;
    JTable jt = null;
    JScrollPane jsp = null;
    ResultSet rs = null;

    public ShowCourse(String sql1) {
        super("查询结果");
        columnNames = new Vector();
        // 设置列名
        columnNames.add("课程号");
        columnNames.add("课程名");
        columnNames.add("先行课");
        columnNames.add("学  分");

        rowData = new Vector();
        sql = sql1;
        try {
            Connection dbConn = ConnectSql.CONN();
            stmt = dbConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            rs = stmt.executeQuery(sql);

            while (rs.next()) {
                Vector row = new Vector();
                row.add(rs.getString("Cno"));
                row.add(rs.getString("Cname"));
                row.add(rs.getString("Cpno"));
                row.add(rs.getString("Ccredit"));
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
