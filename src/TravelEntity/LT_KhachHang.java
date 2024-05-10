package TravelEntity;

/**
 * @author NHUTLQ
 */
public class LT_KhachHang {

    private String malt;
    private String makh;
    private String nguoiTao;

    public LT_KhachHang() {
    }

    public LT_KhachHang(String malt, String makh, String nguoiTao) {
        this.malt = malt;
        this.makh = makh;
        this.nguoiTao = nguoiTao;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getMalt() {
        return malt;
    }

    public void setMalt(String malt) {
        this.malt = malt;
    }

    public String getMakh() {
        return makh;
    }

    public void setMakh(String makh) {
        this.makh = makh;
    }

}
