package DAO;

import TravelEntity.KhachSan;
import SQL.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

/**
 * @author NHUTLQ
 */
public class KhachSanDAO extends TravelTourDAO<KhachSan, String> {

    final String INSERT_SQL = "INSERT INTO dbo.KHACHSAN VALUES (?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.KHACHSAN SET TenKS = ?, SDT = ?, GiaThue = ?, DiaChi = ?, GhiChu = ? WHERE MaKS = ?";
    final String DELETE_SQL = "DELETE FROM dbo.KHACHSAN WHERE MaKS = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM KHACHSAN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM KHACHSAN WHERE MaKS = ?";

    @Override
    public void insert(KhachSan entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaks(),
                entity.getTenks(),
                entity.getSDT(),
                entity.getGia(),
                entity.getDiaChi(),
                entity.getGhiChu()
        );
    }

    @Override
    public void update(KhachSan entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getTenks(),
                entity.getSDT(),
                entity.getGia(),
                entity.getDiaChi(),
                entity.getGhiChu(),
                entity.getMaks()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachSan> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachSan selectById(String id) {
        List<KhachSan> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachSan> selectBySql(String sql, Object... args) {
        List<KhachSan> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                KhachSan entity = new KhachSan();
                entity.setMaks(rs.getString(1));
                entity.setTenks(rs.getString(2));
                entity.setSDT(rs.getString(3));
                entity.setGia(rs.getString(4));
                entity.setDiaChi(rs.getString(5));
                entity.setGhiChu(rs.getString(6));
                list.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<KhachSan> selectMaNV(String MaNV) {
        return selectBySql(SELECT_BY_ID_SQL, MaNV);
    }

    public List<KhachSan> selectByKeyword(String ma, String ten, String dienThoai, String diaChi) {
        String sql = "SELECT * FROM KHACHSAN WHERE MaKS LIKE ? OR TenKS LIKE ? OR SDT LIKE ? OR DiaChi LIKE ?";
        return this.selectBySql(sql, "%" + ma + "%", "%" + ten + "%", "%" + dienThoai + "%", "%" + diaChi + "%");
    }
}
