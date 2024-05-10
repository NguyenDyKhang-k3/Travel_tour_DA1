package DAO;

import SQL.JDBCHelper;
import TravelEntity.LT_PhuongTien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LT_PhuongTienDAO extends TravelTourDAO<LT_PhuongTien, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LICHTRINH_PHUONGTIEN VALUES (?,?,?)";
    final String DELETE_SQL = "DELETE FROM dbo.LICHTRINH_PHUONGTIEN WHERE MaLT = ? AND MaPT = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.LICHTRINH_PHUONGTIEN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.LICHTRINH_PHUONGTIEN WHERE MaPT = ?";

    @Override
    public void insert(LT_PhuongTien entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaLT(),
                entity.getMaPT(),
                entity.getNguoiTao()
        );
    }

    @Override
    public void update(LT_PhuongTien entity) {

    }

    @Override
    public void delete(String id) {
    }

    public void deleteNew(String malt, String mapt) {
        JDBCHelper.update(DELETE_SQL, malt, mapt);
    }

    @Override
    public List<LT_PhuongTien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LT_PhuongTien selectById(String id) {
        List<LT_PhuongTien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LT_PhuongTien> selectBySql(String sql, Object... args) {
        List<LT_PhuongTien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LT_PhuongTien entity = new LT_PhuongTien();
                entity.setMaLT(rs.getString(1));
                entity.setMaPT(rs.getString(2));
                entity.setNguoiTao(rs.getString(3));
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LT_PhuongTien> selectByIdPhuongtien(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id);
    }
}
