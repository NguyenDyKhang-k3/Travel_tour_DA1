package DAO;

import SQL.JDBCHelper;
import TravelEntity.LoaiPhuongTien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LoaiPhuongTienDAO extends TravelTourDAO<LoaiPhuongTien, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LOAIPHUONGTIEN VALUES (?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.LOAIPHUONGTIEN SET TenLoaiPT = ?, Hinh = ?, GhiChu = ? WHERE MaLoaiPT = ?";
    final String DELETE_SQL = "DELETE FROM dbo.LOAIPHUONGTIEN WHERE MaLoaiPT = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.LOAIPHUONGTIEN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.LOAIPHUONGTIEN WHERE MaLoaiPT = ?";

    @Override
    public void insert(LoaiPhuongTien entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaLoaiPT(),
                entity.getTenLoaiPT(),
                entity.getHinh(),
                entity.getGhiChu()
        );
    }

    @Override
    public void update(LoaiPhuongTien entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getTenLoaiPT(),
                entity.getHinh(),
                entity.getGhiChu(),
                entity.getMaLoaiPT()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<LoaiPhuongTien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LoaiPhuongTien selectById(String id) {
        List<LoaiPhuongTien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LoaiPhuongTien> selectBySql(String sql, Object... args) {
        List<LoaiPhuongTien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LoaiPhuongTien entity = new LoaiPhuongTien();
                entity.setMaLoaiPT(rs.getString(1));
                entity.setTenLoaiPT(rs.getString(2));
                entity.setHinh(rs.getString(3));
                entity.setGhiChu(rs.getString(4));
                list.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<LoaiPhuongTien> selectByKeyword(String ma, String ten) {
        String sql = "SELECT * FROM LOAIPHUONGTIEN WHERE MaLoaiPT LIKE ? OR TenLoaiPT LIKE ?";
        return this.selectBySql(sql, "%" + ma + "%", "%" + ten + "%");
    }
}
