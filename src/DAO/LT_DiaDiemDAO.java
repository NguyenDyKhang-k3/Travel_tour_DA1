package DAO;

import SQL.JDBCHelper;
import TravelEntity.LT_DiaDiem;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class LT_DiaDiemDAO extends TravelTourDAO<LT_DiaDiem, String> {

    final String INSERT_SQL = "INSERT INTO dbo.LICHTRINH_THAMQUAN VALUES (?,?,?)";
    final String DELETE_SQL = "DELETE FROM dbo.LICHTRINH_THAMQUAN WHERE MaLT = ? AND MaDD = ?";
    final String SELECT_ALL_SQL = "SELECT * FROM LICHTRINH_THAMQUAN";
    final String SELECT_BY_ID_SQL = "SELECT * FROM LICHTRINH_THAMQUAN WHERE MaLT = ?";

    @Override
    public void insert(LT_DiaDiem entity) {
        JDBCHelper.update(INSERT_SQL,
                entity.getMaLT(),
                entity.getMaDD(),
                entity.getNguoiTao()
        );
    }

    @Override
    public void update(LT_DiaDiem entity) {
    }

    @Override
    public void delete(String id) {
    }

    public void deleteNew(String malt, String madd) {
        JDBCHelper.update(DELETE_SQL, malt, madd);
    }

    @Override
    public List<LT_DiaDiem> selectAll() {
        return selectBySql(SELECT_ALL_SQL);
    }

    @Override
    public LT_DiaDiem selectById(String id) {
        List<LT_DiaDiem> list = selectBySql(SELECT_BY_ID_SQL, id);
        if (list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    @Override
    public List<LT_DiaDiem> selectBySql(String sql, Object... args) {
        List<LT_DiaDiem> list = new ArrayList<>();
        try {
            ResultSet rs = JDBCHelper.query(sql, args);
            while (rs.next()) {
                LT_DiaDiem entity = new LT_DiaDiem();
                entity.setMaLT(rs.getString(1));
                entity.setMaDD(rs.getString(2));
                entity.setNguoiTao(rs.getString(3));
                list.add(entity);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }

    public List<LT_DiaDiem> selectByIdDiaDiem(String id) {
        return selectBySql(SELECT_BY_ID_SQL, id);
    }
}
