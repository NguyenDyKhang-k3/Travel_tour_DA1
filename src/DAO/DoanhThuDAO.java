package DAO;

import DungChung.XDate;
import SQL.JDBCHelper;
import TravelEntity.DoanhThuTheoNam;
import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

/**
 * @author NHUTLQ
 */
public class DoanhThuDAO extends JDBCHelper {

    public int[] checkQuy(int quy) {
        int[] so = new int[3];
        switch (quy) {
            case 1:
                // 1, 2, 3
                so[0] = quy;
                so[1] = quy + 1;
                so[2] = quy + 2;
                break;
            case 2:
                //4, 5, 6
                so[0] = quy + 2;
                so[1] = quy + 3;
                so[2] = quy + 4;
                break;
            case 3:
                //7, 8, 9
                so[0] = quy + 4;
                so[1] = quy + 5;
                so[2] = quy + 6;
                break;
            case 4:
                //10, 11, 12
                so[0] = quy + 6;
                so[1] = quy + 7;
                so[2] = quy + 8;
                break;
        }
        return so;
    }

    public int getTongNV() {
        try {
            String sql = "SELECT COUNT(*) FROM NHANVIEN";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTongKH() {
        try {
            String sql = "SELECT SUM(SoKhach) FROM LICHTRINH";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTongTour() {
        try {
            String sql = "SELECT COUNT(*) FROM TOUR";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

//    public double getDoanhThuTheoThang(int thang) {
//        try {
//            String sql = "SELECT hn.MaHoaDon, MONTH(hn.NgayLapHoaDon), SUM(hg.TongTien) FROM HOADON hn \n"
//                    + "INNER JOIN HOPDONG hg ON hg.MaHopDong=hn.MaHopDong\n"
//                    + "WHERE MONTH(hn.NgayLapHoaDon) = ?\n"
//                    + "GROUP BY hn.MaHoaDon, MONTH(hn.NgayLapHoaDon)";
//            ResultSet rs = JDBCHelper.query(sql, thang);
//            ArrayList<DoanhThuTheoNam> arr = new ArrayList<>();
//            while (rs.next()) {
//                String maHopDong = rs.getString(1);
//                int year = Integer.parseInt(String.valueOf(rs.getInt(2)));
//                double thanhTien = Double.parseDouble(String.valueOf(rs.getDouble(3)));
//                DoanhThuTheoNam dt = new DoanhThuTheoNam(maHopDong, year, thanhTien);
//                arr.add(dt);
//            }
//
//            double tongCong = 0.0;
//            for (int i = 0; i < arr.size(); i++) {
//                tongCong += arr.get(i).getThanhtien();
//            }
//            return Math.ceil(tongCong);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return 0.0;
//    }
    public long getDoanhThuTheoNam(int nam) {
        long doanhthu = 0;
        try {
            String sql = "SELECT YEAR(NgayLapHoaDon) AS nam, SUM(HOPDONG.TongTien) AS doanh_thu\n"
                    + "FROM HOADON inner join HOPDONG on HOADON.MaHopDong = HOPDONG.MaHopDong\n"
                    + "where YEAR(NgayLapHoaDon) = ?\n"
                    + "GROUP BY YEAR(NgayLapHoaDon)\n"
                    + "ORDER BY nam ASC;";
            ResultSet rs = JDBCHelper.query(sql, nam);
            while (rs.next()) {
                doanhthu = (rs.getLong(2));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return (long) Math.ceil(doanhthu);
    }

    public String loadTienHienThiTrenHome(int nam) {
        double doanhthu = 0.0;
        try {
            String sql = "SELECT YEAR(NgayLapHoaDon) AS nam, SUM(HOPDONG.TongTien) AS doanh_thu\n"
                    + "FROM HOADON inner join HOPDONG on HOADON.MaHopDong = HOPDONG.MaHopDong\n"
                    + "where YEAR(NgayLapHoaDon) = ?\n"
                    + "GROUP BY YEAR(NgayLapHoaDon)\n"
                    + "ORDER BY nam ASC;";
            ResultSet rs = JDBCHelper.query(sql, nam);
            while (rs.next()) {
                doanhthu = (rs.getDouble(2)) / 1000000;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String soTien = new DungChung.DungChung().convertTien(String.valueOf(Math.ceil(doanhthu)));
        return soTien;
    }

//    public String loadDoanhThuNam(int nam) {
//        double doanhthu = 0;
//        try {
//            String sql = "SELECT SUM(HOPDONG.TongTien) AS dt\n"
//                    + "FROM HOADON inner join HOPDONG on HOADON.MaHopDong = HOPDONG.MaHopDong\n"
//                    + "where YEAR(NgayLapHoaDon) = ?\n"
//                    + "GROUP BY YEAR(NgayLapHoaDon)";
//            ResultSet rs = JDBCHelper.query(sql, nam);
//            while (rs.next()) {
//                doanhthu = (rs.getDouble(1));
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        String soTien = new DungChung.DungChung().convertTien(String.valueOf(Math.ceil(doanhthu))) + " VNĐ";
//        return soTien;
//    }
//    public void loadDoanhThuThang(JTextField txtThang, JTextField txtTongDT, int thang, int nam) {
//        int soTour = 0;
//        long doanhThuThang = 0;
//        try {
//            String sql = "SELECT COUNT(lt.MaTour), SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
//                    + "FROM LICHTRINH lt \n"
//                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
//                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
//                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
//                    + "WHERE MONTH(hdon.NgayLapHoaDon) = ? AND YEAR(hdon.NgayLapHoaDon) = ?";
//            ResultSet rs = JDBCHelper.query(sql, thang, nam);
//            while (rs.next()) {
//                soTour = rs.getInt(1);
//                doanhThuThang = rs.getLong(2);
//            }
//            txtThang.setText(String.valueOf(soTour) + " Tour");
//            txtTongDT.setText(new DungChung.DungChung().convertTien(String.valueOf(doanhThuThang)) + " VNĐ");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
    public void loadTable_DoanhThu(JTable tbl, int quy, int nam) {
        try {
            String[] header = new String[]{"STT", "Mã Tour", "Tên Tour", "Ngày Lập Hoá Đơn", "Số Tour Đã Mở", "Lượng Khách", "Tổng Doanh Thu"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            tbl.setDefaultEditor(Object.class, null);
            int[] so = checkQuy(quy);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY tr.MaTour) STT,tr.MaTour, tr.TenTour, hdon.NgayLapHoaDon, COUNT(lt.MaTour) as 'So Tour Da Mo', \n"
                    + "SUM(lt.SoKhach) LuongKhach, SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE MONTH(hdon.NgayLapHoaDon) IN (?, ?, ?)  \n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?\n"
                    + "GROUP BY tr.MaTour, tr.TenTour, hdon.NgayLapHoaDon";
            ResultSet rs = JDBCHelper.query(sql,
                    so[0],
                    so[1],
                    so[2],
                    nam
            );
            while (rs.next()) {
                Vector data = new Vector();
                data.add(rs.getString(1));
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(XDate.toString(rs.getDate(4), "dd-MM-yyyy"));
                data.add(rs.getInt(5));
                data.add(rs.getInt(6));
                data.add(new DungChung.DungChung().convertTien(String.valueOf(rs.getString(7))) + " VNĐ");
                model.addRow(data);
            }
            tbl.removeAll();
            tbl.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void TongTienQuy(JLabel lblTongTour, JLabel lblTongDT, int quy, int nam) {
        int[] so = checkQuy(quy);
        int soTourMo = 0;
        long TongDT = 0;
        try {
            String sql = "SELECT COUNT(lt.MaTour), SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE MONTH(hdon.NgayLapHoaDon) IN (?, ?, ?)  \n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?\n"
                    + "GROUP BY tr.MaTour, tr.TenTour";
            ResultSet rs = JDBCHelper.query(sql,
                    so[0],
                    so[1],
                    so[2],
                    nam
            );
            while (rs.next()) {
                soTourMo += rs.getInt(1);
                TongDT += rs.getLong(2);
            }
            lblTongTour.setText(soTourMo + " Tour");
            lblTongDT.setText(new DungChung.DungChung().convertTien(String.valueOf(TongDT)) + " VNĐ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadChartDoanhThang(JPanel pnl, int quy, int nam) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        int[] q = checkQuy(quy);
        for (int i = 0; i < q.length; i++) {
            dataset.setValue("Tháng " + q[i], getTien(q[i], nam));
        }
        JFreeChart chart = ChartFactory.createPieChart("Doanh Thu Tháng Trong Quý", dataset, false, false, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        pnl.setLayout(new java.awt.BorderLayout());
        pnl.add(chartPanel, BorderLayout.CENTER);
        pnl.validate();
    }

    public void loadChartDoanhCaoNhat(JPanel pnl, int quy, int nam) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Năm 2020", getDoanhThuTheoNam(2020));
        dataset.setValue("Năm 2021", getDoanhThuTheoNam(2021));
        dataset.setValue("Năm 2022", getDoanhThuTheoNam(2022));
        dataset.setValue("Năm 2023", getDoanhThuTheoNam(2023));
        JFreeChart chart = ChartFactory.createPieChart("Doanh Thu Năm", dataset, false, false, false);
        ChartPanel chartPanel = new ChartPanel(chart);
        pnl.setLayout(new java.awt.BorderLayout());
        pnl.add(chartPanel, BorderLayout.CENTER);
        pnl.validate();
    }

    public int getTien(int quy, int nam) {
        try {
            int tong = 0;
            String sql = "SELECT hdon.MaHoaDon, SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE MONTH(hdon.NgayLapHoaDon) = ?\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?\n"
                    + "GROUP BY hdon.MaHoaDon";
            ResultSet rs = JDBCHelper.query(sql,
                    quy,
                    nam
            );
            while (rs.next()) {
                tong += rs.getInt(2);
            }
            return tong;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int loadTrangThaiNV(int thang) {
        int soLuong = 0;
        try {
            String sql = "SELECT COUNT(MaNV) FROM dbo.NHANVIEN\n"
                    + "WHERE MONTH(NgayVaoLam) = ?";
            ResultSet rs = JDBCHelper.query(sql, thang);
            while (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuong;
    }

    public int loadTrangThaiLT(int thang) {
        int soLuong = 0;
        try {
            String sql = "SELECT COUNT(MaLT) FROM dbo.LICHTRINH WHERE MONTH(NgayBD) = ?";
            ResultSet rs = JDBCHelper.query(sql, thang);
            while (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuong;
    }

    public int loadTrangThaiTour(int thang) {
        int soLuong = 0;
        try {
            String sql = "SELECT COUNT(MaTour) FROM dbo.TOUR WHERE MONTH(NgayTao) = ?";
            ResultSet rs = JDBCHelper.query(sql, thang);
            while (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuong;
    }

    public int loadTrangThaiDT(int thang) {
        int soLuong = 0;
        try {
            String sql = "SELECT SUM(HOPDONG.TongTien) AS DT\n"
                    + "FROM HOADON JOIN HOPDONG on HOADON.MaHopDong = HOPDONG.MaHopDong\n"
                    + "WHERE MONTH(NgayLapHoaDon) = ?\n"
                    + "GROUP BY MONTH(NgayLapHoaDon)";
            ResultSet rs = JDBCHelper.query(sql, thang);
            while (rs.next()) {
                soLuong = rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return soLuong;
    }
}
