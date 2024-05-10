package TravelEntity;

/**
 * @author NHUTLQ
 */
public class LT_PhuongTien {
    private String MaLT;
    private String MaPT;
    private String GhiChu;
    private String NguoiTao;

    public LT_PhuongTien() {
    }

    public LT_PhuongTien(String MaLT, String MaPT, String GhiChu, String NguoiTao) {
        this.MaLT = MaLT;
        this.MaPT = MaPT;
        this.GhiChu = GhiChu;
        this.NguoiTao = NguoiTao;
    }

    public String getMaLT() {
        return MaLT;
    }

    public void setMaLT(String MaLT) {
        this.MaLT = MaLT;
    }

    public String getMaPT() {
        return MaPT;
    }

    public void setMaPT(String MaPT) {
        this.MaPT = MaPT;
    }

    public String getGhiChu() {
        return GhiChu;
    }

    public void setGhiChu(String GhiChu) {
        this.GhiChu = GhiChu;
    }

    public String getNguoiTao() {
        return NguoiTao;
    }

    public void setNguoiTao(String NguoiTao) {
        this.NguoiTao = NguoiTao;
    }
    
    
}
