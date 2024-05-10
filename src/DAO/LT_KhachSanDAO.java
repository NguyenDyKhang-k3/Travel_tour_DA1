package DAO;

import SQL.JDBCHelper;
import TravelEntity.LT_KhachSan;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LT_KhachSanDAO extends TravelTourDAO<LT_KhachSan, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LICHTRINH_KHACHSAN VALUES (?,?,?)";
    final String DELETE_SQL = "DELETE FROM dbo.LICHTRINH_KHACHSAN WHERE MaLT = ? AND MaKS = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM LICHTRINH_KHACHSAN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM LICHTRINH_KHACHSAN WHERE MaLT = ?";

    @Override
    public void insert(LT_KhachSan entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaLT(),
                entity.getMaKS(),
                entity.getNguoiTao()
        );
    }

    @Override
    public void update(LT_KhachSan entity) {
    }

    @Override
    public void delete(String id) {
    }
    
    public void deleteNew(String malt, String maks) {
        JDBCHelper.update(DELETE_SQL, malt, maks);
    }

    @Override
    public List<LT_KhachSan> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LT_KhachSan selectById(String id) {
        List<LT_KhachSan> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LT_KhachSan> selectBySql(String sql, Object... args) {
        List<LT_KhachSan> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LT_KhachSan entity = new LT_KhachSan();
                entity.setMaLT(rs.getString(1));
                entity.setMaKS(rs.getString(2));
                entity.setNguoiTao(rs.getString(3));
                list.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public List<LT_KhachSan> selectByIdKhachSan(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id);
    }
}
