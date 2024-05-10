package TravelEntity;

/**
 * @author NHUTLQ
 */
public class PhuongTien {

    private String MaPT;
    private String MaLoaiPT;
    private String SDT;
    private double Gia;
    private String SoChoNgoi;
    private String DiaChi;
    private String BienSo;
    private String GhiChu;

    public PhuongTien() {
    }

    public PhuongTien(String MaPT, String MaLoaiPT, String SDT, double Gia, String SoChoNgoi, String DiaChi, String BienSo, String GhiChu) {
        this.MaPT = MaPT;
        this.MaLoaiPT = MaLoaiPT;
        this.SDT = SDT;
        this.Gia = Gia;
        this.SoChoNgoi = SoChoNgoi;
        this.DiaChi = DiaChi;
        this.BienSo = BienSo;
        this.GhiChu = GhiChu;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }

    public String getMaLoaiPT() {
        return MaLoaiPT;
    }

    public void setMaLoaiPT(String MaLoaiPT) {
        this.MaLoaiPT = MaLoaiPT;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public double getGia() {
        return Gia;
    }

    public void setGia(double Gia) {
        this.Gia = Gia;
    }

    public String getSoChoNgoi() {
        return SoChoNgoi;
    }

    public void setSoChoNgoi(String SoChoNgoi) {
        this.SoChoNgoi = SoChoNgoi;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getBienSo() {
        return BienSo;
    }

    public void setBienSo(String BienSo) {
        this.BienSo = BienSo;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    
}
