package TravelEntity;

/**
 * @author NHUTLQ
 */
public class LT_DiaDiem {

    private String MaLT;
    private String MaDD;
    private String NguoiTao;

    public LT_DiaDiem() {
    }

    public LT_DiaDiem(String MaLT, String MaDD, String NguoiTao) {
        this.MaLT = MaLT;
        this.MaDD = MaDD;
        this.NguoiTao = NguoiTao;
    }

    public String getMaLT() {
        return MaLT;
    }

    public void setMaLT(String MaLT) {
        this.MaLT = MaLT;
    }

    public String getMaDD() {
        return MaDD;
    }

    public void setMaDD(String MaDD) {
        this.MaDD = MaDD;
    }

    public String getNguoiTao() {
        return NguoiTao;
    }

    public void setNguoiTao(String NguoiTao) {
        this.NguoiTao = NguoiTao;
    }

}
