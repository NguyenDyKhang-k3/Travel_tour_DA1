package SQL;

import java.awt.Cursor;
import static java.awt.Cursor.HAND_CURSOR;
import java.awt.Font;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * @author NHUTLQ
 */
public class JDBCHelper {

    public static String url = "jdbc:sqlserver://localhost:1433;database=TravelTour;user=sa;password=123";

    public Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (Exception e) {
            System.out.println("Chua co Driver!" + e.toString());
        }
        try {
            conn = DriverManager.getConnection(url);
            System.out.println("Thanh Cong");
            return conn;
        } catch (Exception e) {
            //lỗi sai tên database
            //lỗi tên dn và mk;
            System.out.println("Loi connect" + e.toString());
        }
        return null;
    }

    public void statement(String sql, JTable tbl, String[] header) throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        DefaultTableModel model = new DefaultTableModel(header, 0);
        tbl.setDefaultEditor(Object.class, null);
        tbl.getTableHeader().setCursor(new Cursor(HAND_CURSOR));
        tbl.getTableHeader().setFont(new Font("Segoe UI", 1, 13));
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        while (rs.next()) {
            Vector data = new Vector();
            for (int i = 0; i < header.length; i++) {
                data.add(rs.getObject(i + 1));
            }
            model.addRow(data);
        }
        tbl.setModel(model);
        conn.close();
    }

    //Xây dựng hàm PreparedStatement
    public static PreparedStatement getSttm(String sql, Object... args) throws SQLException {
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement sttm = null;
        if (sql.trim().startsWith("{")) {
            sttm = conn.prepareCall(sql);
        } else {
            sttm = conn.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            sttm.setObject(i + 1, args[i]);
        }
        return sttm;
    }

    //xây dựng hàm để thực hiện thao tác insert update delete
    public static int update(String sql, Object... args) {
        try {
            PreparedStatement sttm = getSttm(sql, args);
            try {
                return sttm.executeUpdate();
            } finally {
                sttm.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    //xây dựng hàm để thực hiện thao tác truy vấn SELECT
    public static ResultSet query(String sql, Object... args) {
        try {
            PreparedStatement sttm = getSttm(sql, args);
            return sttm.executeQuery();
        } catch (SQLException e) {
            System.out.println(e);
            throw new RuntimeException();
        }
    }

    public static Object value(String sql, Object... args) {
        try {
            ResultSet rs = query(sql, args);
            if (rs.next()) {
                return rs.getObject(0);
            }
            rs.getStatement().getConnection().close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
