package DAO;

import SQL.JDBCHelper;
import TravelEntity.ThamQuan;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class ThamQuanDAO extends TravelTourDAO<ThamQuan, String> {

    final String INSERT_SQL = "INSERT INTO dbo.THAMQUAN VALUES (?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.THAMQUAN SET TenDD = ?, LoaiDV = ?, SDT = ?, Gia = ?, DiaChi = ?, GhiChu = ? WHERE MaDD = ?";
    final String DELETE_SQL = "DELETE FROM dbo.THAMQUAN WHERE MaDD = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM THAMQUAN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM THAMQUAN WHERE MaDD = ?";

    @Override
    public void insert(ThamQuan entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMadd(),
                entity.getTendd(),
                entity.getLoaidd(),
                entity.getSdt(),
                entity.getGia(),
                entity.getDiachi(),
                entity.getGhichu()
        );
    }

    @Override
    public void update(ThamQuan entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getTendd(),
                entity.getLoaidd(),
                entity.getSdt(),
                entity.getGia(),
                entity.getDiachi(),
                entity.getGhichu(),
                entity.getMadd()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<ThamQuan> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public ThamQuan selectById(String id) {
        List<ThamQuan> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<ThamQuan> selectBySql(String sql, Object... args) {
        List<ThamQuan> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                ThamQuan entity = new ThamQuan();
                entity.setMadd(rs.getString(1));
                entity.setTendd(rs.getString(2));
                entity.setLoaidd(rs.getString(3));
                entity.setSdt(rs.getString(4));
                entity.setGia(rs.getDouble(5));
                entity.setDiachi(rs.getString(6));
                entity.setGhichu(rs.getString(7));
                list.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<ThamQuan> selectByKeyword(String ma, String ten, String diadiem, String giave) {
        String sql = "SELECT * FROM THAMQUAN WHERE Madd LIKE ? OR Tendd LIKE ? OR DiaChi LIKE ? OR Gia = ?";
        return this.selectBySql(sql, "%" + ma + "%", "%" + ten + "%", "%" + diadiem + "%", giave);
    }
}
