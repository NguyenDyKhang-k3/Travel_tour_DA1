package TravelEntity;

import java.util.Date;

/**
 * @author NHUTLQ
 */
public class ThongTinTour {

    private String MaTour;
    private String TenTour;
    private String DiemDi;
    private String DiemDen;
    private String GiaTour;
    private String ghiChu;
    private Date NgayTao;

    public ThongTinTour() {
    }

    public ThongTinTour(String MaTour, String TenTour, String DiemDi, String DiemDen, String GiaTour, String ghiChu, Date NgayTao) {
        this.MaTour = MaTour;
        this.TenTour = TenTour;
        this.DiemDi = DiemDi;
        this.DiemDen = DiemDen;
        this.GiaTour = GiaTour;
        this.ghiChu = ghiChu;
        this.NgayTao = NgayTao;
    }

    public String getMaTour() {
        return MaTour;
    }

    public void setMaTour(String MaTour) {
        this.MaTour = MaTour;
    }

    public String getTenTour() {
        return TenTour;
    }

    public void setTenTour(String TenTour) {
        this.TenTour = TenTour;
    }

    public String getDiemDi() {
        return DiemDi;
    }

    public void setDiemDi(String DiemDi) {
        this.DiemDi = DiemDi;
    }

    public String getDiemDen() {
        return DiemDen;
    }

    public void setDiemDen(String DiemDen) {
        this.DiemDen = DiemDen;
    }

    public String getGiaTour() {
        return GiaTour;
    }

    public void setGiaTour(String GiaTour) {
        this.GiaTour = GiaTour;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public Date getNgayTao() {
        return NgayTao;
    }

    public void setNgayTao(Date NgayTao) {
        this.NgayTao = NgayTao;
    }

}
