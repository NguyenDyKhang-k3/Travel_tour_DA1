package DAO;

import SQL.JDBCHelper;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author NHUTLQ
 */
public class TruyVanSQL extends JDBCHelper {

    public static String MaTour() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaTour) FROM dbo.TOUR";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "TR0001";
                } else {
                    String numPart = maTour.substring(2);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "TR" + String.format("%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaKH() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaKH) FROM dbo.KHACHHANG";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "KH0001";
                } else {
                    String numPart = maTour.substring(2);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "KH" + String.format("%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaPT() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaPT) FROM dbo.PHUONGTIEN";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "PT0001";
                } else {
                    String numPart = maTour.substring(2);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "PT" + String.format("%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaLT() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaLT) FROM dbo.LICHTRINH";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "LT0001";
                } else {
                    String numPart = maTour.substring(2);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "LT" + String.format("%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaKS() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaKS) FROM dbo.KHACHSAN";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "KS0001";
                } else {
                    String numPart = maTour.substring(2);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "KS" + String.format("%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaLPT() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaLoaiPT) FROM dbo.LOAIPHUONGTIEN";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "LPT001";
                } else {
                    String numPart = maTour.substring(3);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "LPT" + String.format("%03d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaTQ() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaDD) FROM dbo.THAMQUAN";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "TQ0001";
                } else {
                    String numPart = maTour.substring(2);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "TQ" + String.format("%04d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String MaHopDong() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaHopDong) FROM dbo.HOPDONG";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "HDG001";
                    } else {
                    String numPart = maTour.substring(3);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "HDG" + String.format("%03d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }
    
    public static String MaHoaDon() {
        String maks = "";
        try {
            String sql = "SELECT MAX(MaHoaDon) FROM dbo.HOADON";
            ResultSet rs = JDBCHelper.query(sql);
            if (rs.next()) {
                String maTour = rs.getString(1);
                if (maTour == null) {
                    maks = "HDN001";
                    } else {
                    String numPart = maTour.substring(3);
                    int num = Integer.parseInt(numPart) + 1;
                    maks = "HDN" + String.format("%03d", num);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return maks;
    }

    public static String selectMaKH(String cccd) {
        String tenTour = "";
        try {
            String sql = "SELECT MaKH FROM dbo.KHACHHANG WHERE GTTT = ?";
            ResultSet rs = JDBCHelper.query(sql, cccd);
            while (rs.next()) {
                tenTour = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tenTour;
    }

    public static String selectMaLoaiPT(String MaLoaiPT) {
        String tenTour = "";
        try {
            String sql = "SELECT MaLoaiPT FROM dbo.LOAIPHUONGTIEN WHERE TenLoaiPT = ?";
            ResultSet rs = JDBCHelper.query(sql, MaLoaiPT);
            while (rs.next()) {
                tenTour = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tenTour;
    }

    public static String selectMaPT(String SoChoNgoi) {
        String tenTour = "";
        try {
            String sql = "SELECT MaPT FROM dbo.PHUONGTIEN WHERE SoChoNgoi = ?";
            ResultSet rs = JDBCHelper.query(sql, SoChoNgoi);
            while (rs.next()) {
                tenTour = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tenTour;
    }

    public static String selectMaKHUserLT_KH(String malt) {
        String makh = "";
        try {
            String sql = "SELECT MaKH FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = ?";
            ResultSet rs = JDBCHelper.query(sql, malt);
            while (rs.next()) {
                makh = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return makh;
    }

    public static Integer chkSoluongKH(String malt) {
        int soLuongKH = 0;
        try {
            String sql = "SELECT COUNT(MaKH) FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = ?";
            ResultSet rs = JDBCHelper.query(sql, malt);
            while (rs.next()) {
                soLuongKH = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuongKH;
    }

    public static String fillTTLichTrinhInTTHopDong(String malt) {
        String ttlt = "";
        try {
            String sql = "SELECT CONCAT(t.TenTour, ' - ' , kh.TenKH) FROM LICHTRINH lt INNER JOIN KHACHHANG kh ON kh.MaKH = lt.MaKH \n"
                    + "INNER JOIN dbo.TOUR t\n"
                    + "ON t.MaTour = lt.MaTour\n"
                    + "WHERE lt.MaLT = ?";
            ResultSet rs = JDBCHelper.query(sql, malt);
            while (rs.next()) {
                ttlt = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ttlt;
    }

    public static long selectGiaTour(String matour) {
        long giaTour = 0;
        try {
            String sql = "SELECT Gia FROM TOUR WHERE MaTour = ?";
            ResultSet rs = JDBCHelper.query(sql, matour);
            while (rs.next()) {
                giaTour = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return giaTour;
    }
}
