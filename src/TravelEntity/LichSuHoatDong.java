package TravelEntity;

import java.util.Date;

/**
 * @author NHUTLQ
 */
public class LichSuHoatDong {

    private String MaLT;
    private String MaNV;
    private Date NgayTruyCap;
    private String TGTruyCap;
    private String HoatDong;

    public LichSuHoatDong() {
    }

    public LichSuHoatDong(String MaLT, String MaNV, Date NgayTruyCap, String TGTruyCap, String HoatDong) {
        this.MaLT = MaLT;
        this.MaNV = MaNV;
        this.NgayTruyCap = NgayTruyCap;
        this.TGTruyCap = TGTruyCap;
        this.HoatDong = HoatDong;
    }

    public String getMaLT() {
        return MaLT;
    }

    public void setMaLT(String MaLT) {
        this.MaLT = MaLT;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public Date getNgayTruyCap() {
        return NgayTruyCap;
    }

    public void setNgayTruyCap(Date NgayTruyCap) {
        this.NgayTruyCap = NgayTruyCap;
    }

    public String getTGTruyCap() {
        return TGTruyCap;
    }

    public void setTGTruyCap(String TGTruyCap) {
        this.TGTruyCap = TGTruyCap;
    }

    public String getHoatDong() {
        return HoatDong;
    }

    public void setHoatDong(String HoatDong) {
        this.HoatDong = HoatDong;
    }

}
