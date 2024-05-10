package DAO;

import SQL.JDBCHelper;
import TravelEntity.HopDong;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author NHUTLQ
 */
public class HopDongDAO extends TravelTourDAO<HopDong, String> {

    final String INSERT_SQL = "INSERT INTO dbo.HOPDONG VALUES (?,?,?,?,?,?,?,?)";
    final String DELETE_SQL = "DELETE FROM dbo.HOPDONG WHERE MaHopDong = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.HOPDONG";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.HOPDONG WHERE MaHopDong = ?";

    @Override
    public void insert(HopDong entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaHDong(),
                entity.getMaLT(),
                entity.getLoaiThanhToan(),
                entity.getTongTien(),
                entity.getTienCoc(),
                entity.getNgayLapHopDong(),
                entity.getMaNV(),
                entity.getTrangThaiThanhToan()
        );
    }

    @Override
    public void update(HopDong entity) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<HopDong> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public HopDong selectById(String id) {
        List<HopDong> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<HopDong> selectBySql(String sql, Object... args) {
        List<HopDong> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                HopDong entity = new HopDong();
                entity.setMaHDong(rs.getString(1));
                entity.setMaLT(rs.getString(2));
                entity.setLoaiThanhToan(rs.getString(3));
                entity.setTongTien(rs.getDouble(4));
                entity.setTienCoc(rs.getDouble(5));
                entity.setNgayLapHopDong(rs.getDate(6));
                entity.setMaNV(rs.getString(7));
                entity.setTrangThaiThanhToan(rs.getString(8));
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<HopDong> selectByMaLT(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id);
    }
}
