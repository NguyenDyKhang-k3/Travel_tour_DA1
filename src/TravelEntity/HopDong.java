package TravelEntity;

import java.util.Date;

/**
 * @author NHUTLQ
 */
public class HopDong {

    private String maHDong;
    private String MaLT;
    private String LoaiThanhToan;
    private double tongTien;
    private double tienCoc;
    private Date ngayLapHopDong;
    private String maNV;
    private String trangThaiThanhToan;

    public HopDong() {
    }

    public HopDong(String maHDong, String MaLT, String LoaiThanhToan, double tongTien, double tienCoc, Date ngayLapHopDong, String maNV, String trangThaiThanhToan) {
        this.maHDong = maHDong;
        this.MaLT = MaLT;
        this.LoaiThanhToan = LoaiThanhToan;
        this.tongTien = tongTien;
        this.tienCoc = tienCoc;
        this.ngayLapHopDong = ngayLapHopDong;
        this.maNV = maNV;
        this.trangThaiThanhToan = trangThaiThanhToan;
    }
 
    public String getMaHDong() {
        return maHDong;
    }

    public void setMaHDong(String maHDong) {
        this.maHDong = maHDong;
    }

    public String getMaLT() {
        return MaLT;
    }

    public void setMaLT(String MaLT) {
        this.MaLT = MaLT;
    }

    public String getLoaiThanhToan() {
        return LoaiThanhToan;
    }

    public void setLoaiThanhToan(String LoaiThanhToan) {
        this.LoaiThanhToan = LoaiThanhToan;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    public Date getNgayLapHopDong() {
        return ngayLapHopDong;
    }

    public void setNgayLapHopDong(Date ngayLapHopDong) {
        this.ngayLapHopDong = ngayLapHopDong;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTrangThaiThanhToan() {
        return trangThaiThanhToan;
    }

    public void setTrangThaiThanhToan(String trangThaiThanhToan) {
        this.trangThaiThanhToan = trangThaiThanhToan;
    } 
}
