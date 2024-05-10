package TravelEntity;

/**
 * @author NHUTLQ
 */
public class LoaiPhuongTien {

    private String MaLoaiPT;
    private String TenLoaiPT;
    private String Hinh;
    private String GhiChu;

    public LoaiPhuongTien() {
    }

    public LoaiPhuongTien(String MaLoaiPT, String TenLoaiPT, String Hinh, String GhiChu) {
        this.MaLoaiPT = MaLoaiPT;
        this.TenLoaiPT = TenLoaiPT;
        this.Hinh = Hinh;
        this.GhiChu = GhiChu;
    }

    public String getMaLoaiPT() {
        return MaLoaiPT;
    }

    public void setMaLoaiPT(String MaLoaiPT) {
        this.MaLoaiPT = MaLoaiPT;
    }

    public String getTenLoaiPT() {
        return TenLoaiPT;
    }

    public void setTenLoaiPT(String TenLoaiPT) {
        this.TenLoaiPT = TenLoaiPT;
    }

    public String getHinh() {
        return Hinh;
    }

    public void setHinh(String Hinh) {
        this.Hinh = Hinh;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    

}
