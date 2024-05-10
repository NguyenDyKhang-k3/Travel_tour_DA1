package TravelEntity;

import java.util.Date;

/**
 * @author NHUTLQ
 */
public class LichTrinh {

    private String MaLT;
    private String MaNV;
    private String MaKH;
    private String MaTour;
    private String Giodi;
    private Date NgayBD;
    private String Giove;
    private Date NgayKT;
    private String HuongHV;
    private int PhuThu;
    private int SoKhach;
    private String DiaDiem;
    private String TrangThai;

    public LichTrinh() {
    }

    public LichTrinh(String MaLT, String MaNV, String MaKH, String MaTour, String Giodi, Date NgayBD, String Giove, Date NgayKT, String HuongHV, int PhuThu, int SoKhach, String DiaDiem, String TrangThai) {
        this.MaLT = MaLT;
        this.MaNV = MaNV;
        this.MaKH = MaKH;
        this.MaTour = MaTour;
        this.Giodi = Giodi;
        this.NgayBD = NgayBD;
        this.Giove = Giove;
        this.NgayKT = NgayKT;
        this.HuongHV = HuongHV;
        this.PhuThu = PhuThu;
        this.SoKhach = SoKhach;
        this.DiaDiem = DiaDiem;
        this.TrangThai = TrangThai;
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

    public String getMaKH() {
        return MaKH;
    }

    public void setMaKH(String MaKH) {
        this.MaKH = MaKH;
    }

    public String getMaTour() {
        return MaTour;
    }

    public void setMaTour(String MaTour) {
        this.MaTour = MaTour;
    }

    public String getGiodi() {
        return Giodi;
    }

    public void setGiodi(String Giodi) {
        this.Giodi = Giodi;
    }

    public Date getNgayBD() {
        return NgayBD;
    }

    public void setNgayBD(Date NgayBD) {
        this.NgayBD = NgayBD;
    }

    public String getGiove() {
        return Giove;
    }

    public void setGiove(String Giove) {
        this.Giove = Giove;
    }

    public Date getNgayKT() {
        return NgayKT;
    }

    public void setNgayKT(Date NgayKT) {
        this.NgayKT = NgayKT;
    }

    public String getHuongHV() {
        return HuongHV;
    }

    public void setHuongHV(String HuongHV) {
        this.HuongHV = HuongHV;
    }

    public int getPhuThu() {
        return PhuThu;
    }

    public void setPhuThu(int PhuThu) {
        this.PhuThu = PhuThu;
    }

    public int getSoKhach() {
        return SoKhach;
    }

    public void setSoKhach(int SoKhach) {
        this.SoKhach = SoKhach;
    }

    public String getDiaDiem() {
        return DiaDiem;
    }

    public void setDiaDiem(String DiaDiem) {
        this.DiaDiem = DiaDiem;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }

    
}
