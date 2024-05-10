package DAO;

import SQL.JDBCHelper;
import TravelEntity.LT_KhachHang;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LT_KhachHangDAO extends TravelTourDAO<LT_KhachHang, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LICHTRINH_KHACHHANG VALUES (?,?,?)";
    final String DELETE_SQL = "DELETE FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = ? AND MaKH = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.LICHTRINH_KHACHHANG";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = ?";

    @Override
    public void insert(LT_KhachHang entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMalt(),
                entity.getMakh(),
                entity.getNguoiTao()
        );
    }

    @Override
    public void update(LT_KhachHang entity) {

    }

    @Override
    public void delete(String id) {
    }
    
    public void deleteNew(String malt, String makh) {
        JDBCHelper.update(DELETE_SQL, malt, makh);
    }

    @Override
    public List<LT_KhachHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LT_KhachHang selectById(String id) {
        List<LT_KhachHang> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LT_KhachHang> selectBySql(String sql, Object... args) {
        List<LT_KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LT_KhachHang entity = new LT_KhachHang();
                entity.setMalt(rs.getString(1));
                entity.setMakh(rs.getString(2));
                entity.setNguoiTao(rs.getString(3));
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LT_KhachHang> selectSLKH(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id);
    }
}
