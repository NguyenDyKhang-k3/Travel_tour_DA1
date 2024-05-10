package TravelEntity;

import java.util.Date;

/**
 * @author khang
 */
public class KhachHang {

    public String MaKH;
    public String TenKH;
    public boolean GioiTinh;
    public String QueQuan;
    public Date NS;
    public String CCCD;
    public String SDT;
    public String Email;
    public String GhiChu;

    public KhachHang() {
    }

    public KhachHang(String MaKH, String TenKH, boolean GioiTinh, String QueQuan, Date NS, String CCCD, String SDT, String Email, String GhiChu) {
        this.MaKH = MaKH;
        this.TenKH = TenKH;
        this.GioiTinh = GioiTinh;
        this.QueQuan = QueQuan;
        this.NS = NS;
        this.CCCD = CCCD;
        this.SDT = SDT;
        this.Email = Email;
        this.GhiChu = GhiChu;
    }

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getTenKH() {
        return TenKH;
    }

    public void setTenKH(String TenKH) {
        this.TenKH = TenKH;
    }

    public boolean getGioiTinh() {
        return GioiTinh;
    }

    public void setGioiTinh(boolean GioiTinh) {
        this.GioiTinh = GioiTinh;
    }

    public String getQueQuan() {
        return QueQuan;
    }

    public void setQueQuan(String QueQuan) {
        this.QueQuan = QueQuan;
    }

    public Date getNS() {
        return NS;
    }

    public void setNS(Date NS) {
        this.NS = NS;
    }

    public String getCCCD() {
        return CCCD;
    }

    public void setCCCD(String CCCD) {
        this.CCCD = CCCD;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    
}
