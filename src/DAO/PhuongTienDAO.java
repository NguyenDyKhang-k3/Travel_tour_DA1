package DAO;

import SQL.JDBCHelper;
import TravelEntity.PhuongTien;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class PhuongTienDAO extends TravelTourDAO<PhuongTien, String> {

    final String INSERT_SQL = "INSERT INTO dbo.PHUONGTIEN VALUES (?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.PHUONGTIEN SET SDT = ?, Gia = ?, DiaChi = ?, BienSo = ?, GhiChu = ?, SoChoNgoi = ? WHERE MaPT = ?";
    final String DELETE_SQL = "DELETE FROM dbo.PHUONGTIEN WHERE MaPT = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM PHUONGTIEN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM PHUONGTIEN WHERE MaPT = ?";

    @Override
    public void insert(PhuongTien entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaPT(),
                entity.getMaLoaiPT(),
                entity.getGia(),
                entity.getBienSo(),
                entity.getSoChoNgoi(),
                entity.getSDT(),
                entity.getDiaChi(),
                entity.getGhiChu()
        );
    }

    @Override
    public void update(PhuongTien entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getSDT(),
                entity.getGia(),
                entity.getDiaChi(),
                entity.getBienSo(),
                entity.getGhiChu(),
                entity.getSoChoNgoi(),
                entity.getMaPT()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);

    }

    @Override
    public List<PhuongTien> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public PhuongTien selectById(String id) {
        List<PhuongTien> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<PhuongTien> selectBySql(String sql, Object... args) {
        List<PhuongTien> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                PhuongTien entity = new PhuongTien();
                entity.setMaPT(rs.getString(1));
                entity.setMaLoaiPT(rs.getString(2));
                entity.setGia(rs.getDouble(3));
                entity.setBienSo(rs.getString(4));
                entity.setSoChoNgoi(rs.getString(5));
                entity.setSDT(rs.getString(6));
                entity.setDiaChi(rs.getString(7));
                entity.setGhiChu(rs.getString(8));
                list.add(entity);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
}
