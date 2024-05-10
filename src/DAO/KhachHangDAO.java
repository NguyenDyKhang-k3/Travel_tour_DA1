package DAO;

import SQL.JDBCHelper;
import TravelEntity.KhachHang;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author NHUTLQ
 */
public class KhachHangDAO extends TravelTourDAO<KhachHang, String> {

    final String INSERT_SQL = "INSERT INTO dbo.KHACHHANG VALUES (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.KHACHHANG SET TenKH = ?, GioiTinh = ?, QueQuan = ?, NgaySinh = ?, GTTT = ?, SDT = ?, Email = ?, GhiChu= ? WHERE MaKH = ?";
    final String DELETE_SQL = "DELETE FROM dbo.KHACHHANG WHERE MaKH = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.KHACHHANG";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.KHACHHANG WHERE MaKH = ?";
    final String SELECT_NotInCout = "SELECT * FROM dbo.KHACHHANG WHERE MaKH \n"
            + "NOT IN(SELECT MaKH FROM dbo.LICHTRINH_KHACHHANG WHERE MaKH = ?)";

    @Override
    public void insert(KhachHang entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaKH(),
                entity.getTenKH(),
                entity.getGioiTinh(),
                entity.getQueQuan(),
                entity.getNS(),
                entity.getCCCD(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getGhiChu()
        );
    }

    @Override
    public void update(KhachHang entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getTenKH(),
                entity.getGioiTinh(),
                entity.getQueQuan(),
                entity.getNS(),
                entity.getCCCD(),
                entity.getSDT(),
                entity.getEmail(),
                entity.getGhiChu(),
                entity.getMaKH()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<KhachHang> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public KhachHang selectById(String id) {
        List<KhachHang> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<KhachHang> selectBySql(String sql, Object... args) {
        List<KhachHang> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                KhachHang entity = new KhachHang();
                entity.setMaKH(rs.getString("MaKH"));
                entity.setTenKH(rs.getString("TenKH"));
                entity.setGioiTinh(rs.getBoolean("GioiTinh"));
                entity.setQueQuan(rs.getString("QueQuan"));
                entity.setNS(rs.getDate("NgaySinh"));
                entity.setCCCD(rs.getString("GTTT"));
                entity.setSDT(rs.getString("SDT"));
                entity.setEmail(rs.getString("Email"));
                entity.setGhiChu(rs.getString("GhiChu"));
                list.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<KhachHang> selectByKeyword(String ma, String ten, String dienThoai) {
        String sql = "SELECT * FROM KHACHHANG WHERE MaKH LIKE ? OR TenKH LIKE ? OR SDT LIKE ?";
        return this.selectBySql(sql, "%" + ma + "%", "%" + ten + "%", "%" + dienThoai + "%");
    }

    public List<KhachHang> selectNotInCout(String ma) {
        return this.selectBySql(SELECT_NotInCout, "%" + ma + "%");
    }

    public List<KhachHang> fillTableBriday() {
        String sql = "SELECT * FROM dbo.KHACHHANG WHERE DAY(NgaySinh) = DAY(GETDATE()) AND MONTH(NgaySinh) = MONTH(GETDATE())";
        return selectBySql(sql);
    }
}
