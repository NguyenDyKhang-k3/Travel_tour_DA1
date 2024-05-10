package TravelEntity;

import java.util.Date;

/**
 * @author NHUTLQ
 */
public class NhanVien {

    private String MaNV;
    private String MatKhau;
    private String HoTen;
    private Date NgayVaoLam;
    private Date NgaySinh;
    private String SDT;
    private String ChucVu;
    private String images;
    private String TrangThai;

    public NhanVien() {
    }

    public NhanVien(String MaNV, String MatKhau, String HoTen, Date NgayVaoLam, Date NgaySinh, String SDT, String ChucVu, String images, String TrangThai) {
        this.MaNV = MaNV;
        this.MatKhau = MatKhau;
        this.HoTen = HoTen;
        this.NgayVaoLam = NgayVaoLam;
        this.NgaySinh = NgaySinh;
        this.SDT = SDT;
        this.ChucVu = ChucVu;
        this.images = images;
        this.TrangThai = TrangThai;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String MaNV) {
        this.MaNV = MaNV;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String MatKhau) {
        this.MatKhau = MatKhau;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String HoTen) {
        this.HoTen = HoTen;
    }

    public Date getNgayVaoLam() {
        return NgayVaoLam;
    }

    public void setNgayVaoLam(Date NgayVaoLam) {
        this.NgayVaoLam = NgayVaoLam;
    }

    public Date getNgaySinh() {
        return NgaySinh;
    }

    public void setNgaySinh(Date NgaySinh) {
        this.NgaySinh = NgaySinh;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getChucVu() {
        return ChucVu;
    }

    public void setChucVu(String ChucVu) {
        this.ChucVu = ChucVu;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getTrangThai() {
        return TrangThai;
    }

    public void setTrangThai(String TrangThai) {
        this.TrangThai = TrangThai;
    }
    
    
}
