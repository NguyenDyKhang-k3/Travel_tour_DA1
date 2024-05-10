package TravelEntity;

/**
 *
 * @author khang
 */
public class ThamQuan {

    public String madd;
    public String tendd;
    public String loaidd;
    public String sdt;
    public double gia;
    public String diachi;
    public String ghichu;

    public ThamQuan() {
    }

    public ThamQuan(String madd, String tendd, String loaidd, String sdt, double gia, String diachi, String ghichu) {
        this.madd = madd;
        this.tendd = tendd;
        this.loaidd = loaidd;
        this.sdt = sdt;
        this.gia = gia;
        this.diachi = diachi;
        this.ghichu = ghichu;
    }

    public String getMadd() {
        return madd;
    }

    public void setMadd(String madd) {
        this.madd = madd;
    }

    public String getTendd() {
        return tendd;
    }

    public void setTendd(String tendd) {
        this.tendd = tendd;
    }

    public String getLoaidd() {
        return loaidd;
    }

    public void setLoaidd(String loaidd) {
        this.loaidd = loaidd;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }

    public String getDiachi() {
        return diachi;
    }

    public void setDiachi(String diachi) {
        this.diachi = diachi;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

}
