package TravelEntity;

/**
 *
 * @author NHUTLQ
 */
public class DoanhThuTheoNam {
    private String mahopdong;
    private int nam;
    private double thanhtien;

    public DoanhThuTheoNam(String mahopdong, int nam, double thanhtien) {
        this.mahopdong = mahopdong;
        this.nam = nam;
        this.thanhtien = thanhtien;
    }

    public DoanhThuTheoNam() {
    }

    public String getMahopdong() {
        return mahopdong;
    }

    public void setMahopdong(String mahopdong) {
        this.mahopdong = mahopdong;
    }

    public int getNam() {
        return nam;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public double getThanhtien() {
        return thanhtien;
    }

    public void setThanhtien(double thanhtien) {
        this.thanhtien = thanhtien;
    }
    
    
    
}
