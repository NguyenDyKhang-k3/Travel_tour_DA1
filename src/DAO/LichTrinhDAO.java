package DAO;

import SQL.JDBCHelper;
import TravelEntity.LichTrinh;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LichTrinhDAO extends TravelTourDAO<LichTrinh, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LICHTRINH VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    final String UPDATE_SQL = "UPDATE dbo.LICHTRINH SET GioDi = ?, NgayBD = ?, GioVe = ?, NgayKT = ?, HuongDV = ?, PhuThu = ?, SoKhach = ?, DiaDiemDD = ?, TrangThai = ? WHERE MaLT = ?";
    final String DELETE_SQL = "DELETE FROM dbo.LICHTRINH WHERE MaLT = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM LICHTRINH";
    final String SELECT_BY_ID_SQL = "SELECT * FROM dbo.LICHTRINH WHERE MaLT = ?";

    @Override
    public void insert(LichTrinh entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaLT(),
                entity.getMaNV(),
                entity.getMaKH(),
                entity.getMaTour(),
                entity.getGiodi(),
                entity.getNgayBD(),
                entity.getGiove(),
                entity.getNgayKT(),
                entity.getHuongHV(),
                entity.getPhuThu(),
                entity.getSoKhach(),
                entity.getDiaDiem(),
                entity.getTrangThai()
        );
    }

    @Override
    public void update(LichTrinh entity) {
        JDBCHelper.update(UPDATE_SQL,
                entity.getGiodi(),
                entity.getNgayBD(),
                entity.getGiove(),
                entity.getNgayKT(),
                entity.getHuongHV(),
                entity.getPhuThu(),
                entity.getSoKhach(),
                entity.getDiaDiem(),
                entity.getTrangThai(),
                entity.getMaLT()
        );
    }

    @Override
    public void delete(String id) {
        JDBCHelper.update(DELETE_SQL, id);
    }

    @Override
    public List<LichTrinh> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LichTrinh selectById(String id) {
        List<LichTrinh> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LichTrinh> selectBySql(String sql, Object... args) {
        List<LichTrinh> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LichTrinh entity = new LichTrinh();
                entity.setMaLT(rs.getString(1));
                entity.setMaNV(rs.getString(2));
                entity.setMaKH(rs.getString(3));
                entity.setMaTour(rs.getString(4));
                entity.setGiodi(rs.getString(5));
                entity.setNgayBD(rs.getDate(6));
                entity.setGiove(rs.getString(7));
                entity.setNgayKT(rs.getDate(8));
                entity.setHuongHV(rs.getString(9));
                entity.setPhuThu(rs.getInt(10));
                entity.setSoKhach(rs.getInt(11));
                entity.setDiaDiem(rs.getString(12));
                entity.setTrangThai(rs.getString(13));
                list.add(entity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LichTrinh> selectSLKH(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id);
    }
}
