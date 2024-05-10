package TravelEntity;

/**
 * @author NHUTLQ
 */
public class HoaDon {

    private String maHoaDon;
    private String maHopDong;
    private String gioLap;
    private String ngayLapHoaDon;
    private String trangThai;
    private String ghiChu;
    private String nguoiTao;
    private String TrangThaiHDon;

    public HoaDon() {
    }

    public HoaDon(String maHoaDon, String maHopDong, String gioLap, String ngayLapHoaDon, String trangThai, String ghiChu, String nguoiTao, String TrangThaiHDon) {
        this.maHoaDon = maHoaDon;
        this.maHopDong = maHopDong;
        this.gioLap = gioLap;
        this.ngayLapHoaDon = ngayLapHoaDon;
        this.trangThai = trangThai;
        this.ghiChu = ghiChu;
        this.nguoiTao = nguoiTao;
        this.TrangThaiHDon = TrangThaiHDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHopDong() {
        return maHopDong;
    }

    public void setMaHopDong(String maHopDong) {
        this.maHopDong = maHopDong;
    }

    public String getGioLap() {
        return gioLap;
    }

    public void setGioLap(String gioLap) {
        this.gioLap = gioLap;
    }

    public String getNgayLapHoaDon() {
        return ngayLapHoaDon;
    }

    public void setNgayLapHoaDon(String ngayLapHoaDon) {
        this.ngayLapHoaDon = ngayLapHoaDon;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getGhiChu() {
        return ghiChu;
    }

    public void setGhiChu(String ghiChu) {
        this.ghiChu = ghiChu;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getTrangThaiHDon() {
        return TrangThaiHDon;
    }

    public void setTrangThaiHDon(String TrangThaiHDon) {
        this.TrangThaiHDon = TrangThaiHDon;
    }

}
