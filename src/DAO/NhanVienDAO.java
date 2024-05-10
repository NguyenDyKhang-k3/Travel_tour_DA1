package DAO;

import TravelEntity.NhanVien;
import SQL.JDBCHelper;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author NHUTLQ
 */
public class NhanVienDAO extends TravelTourDAO<NhanVien, String> {

    final String INSERT_SQL = "INSERT INTO dbo.NHANVIEN VALUES (?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.NHANVIEN SET MatKhau = ?, HoTen = ?, NgayVaoLam = ?, NgaySinh = ?, SDT = ?, ChucVu = ?, Hinh = ?, TrangThai = ? WHERE MaNV = ?";
    final String DELETE_SQL = "DELETE FROM dbo.NHANVIEN WHERE MaNV = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM dbo.NHANVIEN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM NHANVIEN WHERE MaNV = ?";

    @Override
    public void insert(NhanVien entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaNV(),
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.getNgayVaoLam(),
                entity.getNgaySinh(),
                entity.getSDT(),
                entity.getChucVu(),
                entity.getImages(),
                entity.getTrangThai()
        );
    }

    @Override
    public void update(NhanVien entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getMatKhau(),
                entity.getHoTen(),
                entity.getNgayVaoLam(),
                entity.getNgaySinh(),
                entity.getSDT(),
                entity.getChucVu(),
                entity.getImages(),
                entity.getTrangThai(),
                entity.getMaNV()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<NhanVien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public NhanVien selectById(String id) {
        List<NhanVien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<NhanVien> selectBySql(String sql, Object... args) {
        List<NhanVien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                NhanVien entity = new NhanVien();
                entity.setMaNV(rs.getString("MaNV"));
                entity.setMatKhau(rs.getString("MatKhau"));
                entity.setHoTen(rs.getString("HoTen"));
                entity.setNgayVaoLam(rs.getDate("NgayVaoLam"));
                entity.setNgaySinh(rs.getDate("NgaySinh"));
                entity.setSDT(rs.getString("SDT"));
                entity.setChucVu(rs.getString("ChucVu"));
                entity.setImages(rs.getString("Hinh"));
                entity.setTrangThai(rs.getString("TrangThai"));
                list.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public List<NhanVien> selectMaNV(String MaNV) {
        return selectBySql(SELECT_BY_ID_SQL, MaNV);
    }

    public List<NhanVien> selectByKeyword(String ma, String ten, String dienThoai) {
        String sql = "SELECT * FROM NhanVien WHERE MaNV LIKE ? OR HoTen LIKE ? OR SDT LIKE ?";
        return this.selectBySql(sql, "%" + ma + "%", "%" + ten + "%", "%" + dienThoai + "%");
    }

    public List<NhanVien> selectTenHDV() {
        String sql = "SELECT * FROM NHANVIEN WHERE ChucVu = N'Hướng Dẫn Viên' AND TrangThai = N'Đang Làm Việc'";
        return selectBySql(sql);
    }
}
