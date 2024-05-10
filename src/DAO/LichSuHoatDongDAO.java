package DAO;

import SQL.JDBCHelper;
import TravelEntity.LichSuHoatDong;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LichSuHoatDongDAO extends TravelTourDAO<LichSuHoatDong, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LICHSUHD VALUES(?,GETDATE(),GETDATE(),?)";
    final String DELETE_SQL = "DELETE FROM dbo.LICHSUHD";
    final String SELECT_ALL_SQL = "SELECT ls.MaNV, nv.HoTen, ls.NgayTruyCap, ls.ThoiGianTruyCap, ls.hoatdong FROM LICHSUHD ls INNER JOIN NHANVIEN nv ON ls.MaNV = nv.MaNV ORDER BY ls.NgayTruyCap, ls.ThoiGianTruyCap ASC";
    final String SELECT_BY_ID_SQL = "SELECT * FROM LICHSUHD WHERE MaNV = ?";

    @Override
    public void insert(LichSuHoatDong entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getHoatDong()
        );
    }

    @Override
    public void update(LichSuHoatDong entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void delete() {
        JDBCHelper.update(DELETE_SQL);
    }

    @Override
    public List<LichSuHoatDong> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LichSuHoatDong selectById(String id) {
        List<LichSuHoatDong> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LichSuHoatDong> selectBySql(String sql, Object... args) {
        List<LichSuHoatDong> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LichSuHoatDong entity = new LichSuHoatDong();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setNgayTruyCap(rs.getDate("NgayTruyCap"));
                entity.setTGTruyCap(rs.getString("ThoiGianTruyCap"));
                entity.setHoatDong(rs.getString("HoatDong"));
                list.add(entity);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}
