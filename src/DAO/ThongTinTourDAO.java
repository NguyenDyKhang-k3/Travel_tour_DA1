package DAO;

import SQL.JDBCHelper;
import TravelEntity.ThongTinTour;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class ThongTinTourDAO extends TravelTourDAO<ThongTinTour, String> {

    final String INSERT_SQL = "INSERT INTO TOUR (MaTour ,TenTour, DiemDi, DiemDen, Gia, GhiChu, NgayTao) VALUES (?,?,?,?,?,?,GETDATE())";
    final String UPDATE_SQL = "UPDATE TOUR SET TenTour = ?, DiemDi = ?, DiemDen = ?, Gia = ?, GhiChu = ? WHERE MaTour = ?";
    final String DELETE_SQL = "DELETE FROM TOUR WHERE MaTour = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM TOUR";
    final String SELECT_BY_ID_SQL = "SELECT * FROM TOUR WHERE MaTour = ?";

    @Override
    public void insert(ThongTinTour entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaTour(),
                entity.getTenTour(),
                entity.getDiemDi(),
                entity.getDiemDen(),
                entity.getGiaTour(),
                entity.getGhiChu()
        );
    }

    @Override
    public void update(ThongTinTour entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getTenTour(),
                entity.getDiemDi(),
                entity.getDiemDen(),
                entity.getGiaTour(),
                entity.getGhiChu(),
                entity.getMaTour()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<ThongTinTour> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ThongTinTour selectById(String id) {
        List<ThongTinTour> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ThongTinTour> selectBySql(String sql, Object... args) {
        List<ThongTinTour> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                ThongTinTour entity = new ThongTinTour();
                entity.setMaTour(rs.getString("MaTour"));
                entity.setTenTour(rs.getString("TenTour"));
                entity.setDiemDi(rs.getString("DiemDi"));
                entity.setDiemDen(rs.getString("DiemDen"));
                entity.setGiaTour(rs.getString("Gia"));
                entity.setGhiChu(rs.getString("GhiChu"));
                entity.setNgayTao(rs.getDate("NgayTao"));
                list.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ThongTinTour> selectByKeyword(String ma, String tentour, String diemDi, String diemDen) {
        String sql = "SELECT * FROM TOUR WHERE MaTour LIKE ? OR TenTour LIKE ? OR DiemDi LIKE ? OR DiemDen LIKE ?";
        return this.selectBySql(sql, "%" + ma + "%", "%" + tentour + "%", "%" + diemDi + "%", "%" + diemDen + "%");
    }

    public List<ThongTinTour> selectMaTour(String tenTour) {
        String sql = "SELECT MaTour FROM TOUR WHERE TenTour = ?";
        return selectBySql(sql, "%" + tenTour + "%");
    }
}
