package TravelEntity;

/**
 * @author NHUTLQ
 */
public class LT_KhachSan {

    private String MaLT;
    private String MaKS;
    private String NguoiTao;

    public LT_KhachSan() {
    }

    public LT_KhachSan(String MaLT, String MaKS, String NguoiTao) {
        this.MaLT = MaLT;
        this.MaKS = MaKS;
        this.NguoiTao = NguoiTao;
    }

    public String getMaLT() {
        return MaLT;
    }

    public void setMaLT(String MaLT) {
        this.MaLT = MaLT;
    }

    public String getMaKS() {
        return MaKS;
    }

    public void setMaKS(String MaKS) {
        this.MaKS = MaKS;
    }

    public String getNguoiTao() {
        return NguoiTao;
    }

    public void setNguoiTao(String NguoiTao) {
        this.NguoiTao = NguoiTao;
    }

}
