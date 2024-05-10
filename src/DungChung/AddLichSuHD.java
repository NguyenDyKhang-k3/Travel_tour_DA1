package DungChung;

import DAO.LichSuHoatDongDAO;
import DAO.NhanVienDAO;
import TravelEntity.LichSuHoatDong;
import UI.LoginForm;

/**
 * @author NHUTLQ
 */
public class AddLichSuHD {

    static LichSuHoatDongDAO lsDao = new LichSuHoatDongDAO();

    public static void addLSHD(String hoatDong) {
        LichSuHoatDong ls = AddLichSuHD.getLichSu(
                LoginForm.txtUser.getText(),
                hoatDong
        );

        try {
            lsDao.insert(ls);
        } catch (Exception e) {
        }
    }

    public static LichSuHoatDong getLichSu(String MaNV, String hoatDong) {
        LichSuHoatDong ls = new LichSuHoatDong();
        ls.setMaNV(MaNV);
        ls.setHoatDong(hoatDong);
        return ls;
    }
}
