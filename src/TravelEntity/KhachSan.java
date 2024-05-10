package TravelEntity;

/**
 *
 * @author khang
 */
public class KhachSan {

    public String Maks;
    public String Tenks;
    public String Gia;
    public String SDT;
    public String DiaChi;
    public String GhiChu;

    public KhachSan() {
    }

    public KhachSan(String Maks, String Tenks, String Gia, String SDT, String DiaChi, String GhiChu) {
        this.Maks = Maks;
        this.Tenks = Tenks;
        this.Gia = Gia;
        this.SDT = SDT;
        this.DiaChi = DiaChi;
        this.GhiChu = GhiChu;

    }

    public String getMaks() {
        return Maks;
    }

    public void setMaks(String Maks) {
        this.Maks = Maks;
    }

    public String getTenks() {
        return Tenks;
    }

    public void setTenks(String Tenks) {
        this.Tenks = Tenks;
    }

    public String getGia() {
        return Gia;
    }

    public void setGia(String Gia) {
        this.Gia = Gia;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getDiaChi() {
        return DiaChi;
    }

    public void setDiaChi(String DiaChi) {
        this.DiaChi = DiaChi;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

}
