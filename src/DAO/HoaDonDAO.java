package DAO;

import SQL.JDBCHelper;
import TravelEntity.HoaDon;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class HoaDonDAO extends TravelTourDAO<HoaDon, String> {

    final String INSERT_SQL = "INSERT INTO dbo.HOADON VALUES (?,?,?,?,?,?,GETDATE(),GETDATE())";
    final String UPDATE_SQL = "UPDATE dbo.HOADON SET GhiChu = ? WHERE MaHoaDon = ?";
    final String DELETE_SQL = "DELETE FROM dbo.HOADON WHERE MaHoaDon = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.HOADON";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.HOADON WHERE MaHoaDon = ?";

    @Override
    public void insert(HoaDon entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaHoaDon(),
                entity.getMaHopDong(),
                entity.getTrangThai(),
                entity.getGhiChu(),
                entity.getTrangThaiHDon(),
                entity.getNguoiTao()
        );
    }

    @Override
    public void update(HoaDon entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getGhiChu(),
                entity.getMaHoaDon()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HoaDon> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HoaDon selectById(String id) {
        List<HoaDon> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HoaDon> selectBySql(String sql, Object... args) {
        List<HoaDon> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                HoaDon entity = new HoaDon();
                entity.setMaHoaDon(rs.getString(1));
                entity.setMaHopDong(rs.getString(2));
                entity.setTrangThai(rs.getString(3));
                entity.setGioLap(rs.getString(4));
                entity.setNgayLapHoaDon(rs.getString(5));
                entity.setGhiChu(rs.getString(6));
                entity.setTrangThaiHDon(rs.getString(7));
                entity.setNguoiTao(rs.getString(8));
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HoaDon> selecMaHD(String mahd) {
        return selectBySql(SELECT_BY_ID_SQL, mahd);
    }

    public void updatePrint(HoaDon entity) {
        JDBCHelper.update("UPDATE dbo.HOADON SET TrangThaiHDon = ? WHERE MaHoaDon = ?",
                entity.getTrangThaiHDon(),
                entity.getMaHoaDon()
        );
    }
}
