package UI;

import DAO.HopDongDAO;
import DAO.LichTrinhDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import DungChung.XDate;
import SQL.JDBCHelper;
import TravelEntity.HopDong;
import TravelEntity.LichTrinh;
import static UI.Dialog_XuatHDong.MaLT;
import static UI.Dialog_XuatHDong.TenKhachHang;
import static UI.Dialog_XuatHDong.rdo1;
import static UI.Dialog_XuatHDong.rdo2;
import static UI.Dialog_XuatHDong.rdo3;
import static UI.Dialog_XuatHDong.rdo4;
import static UI.Dialog_XuatHDong.tongGiaTriHopDong;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class DiaLog_PrintHopDong extends javax.swing.JDialog {

    LichTrinhDAO ltDao = new LichTrinhDAO();
    HopDongDAO hdDao = new HopDongDAO();

    public DiaLog_PrintHopDong(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        fillHopDong();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public String ngayHienTai() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = dateObj.format(formatter);
        return date;
    }

    public String ngayDi() {
        String ngayDi = null;
        List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
        for (LichTrinh lichTrinh : lt) {
            ngayDi = XDate.toString(lichTrinh.getNgayBD(), "dd-MM-yyyy");
        }
        return ngayDi;
    }

    public String DiaDiemDuaDon() {
        String diemDuaDon = null;
        List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
        for (LichTrinh lichTrinh : lt) {
            diemDuaDon = lichTrinh.getDiaDiem();
        }
        return diemDuaDon;
    }

    public String GioDi() {
        String gioDi = null;
        List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
        for (LichTrinh lichTrinh : lt) {
            gioDi = lichTrinh.getGiodi();
        }
        return gioDi;
    }

    public String GioVe() {
        String gioVe = null;
        List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
        for (LichTrinh lichTrinh : lt) {
            gioVe = lichTrinh.getGiove();
        }
        return gioVe;
    }

    public String ngayVe() {
        String ngayVe = null;
        List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
        for (LichTrinh lichTrinh : lt) {
            ngayVe = XDate.toString(lichTrinh.getNgayKT(), "dd-MM-yyyy");
        }
        return ngayVe;
    }

    public int soKhach() {
        int soKhach = 0;
        List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
        for (LichTrinh lichTrinh : lt) {
            soKhach = lichTrinh.getSoKhach();
        }
        return soKhach;
    }

    public String DiaChiKH(String tenkh) {
        String diachi = "";
        try {
            String sql = "SELECT QueQuan FROM dbo.KHACHHANG WHERE TenKH = ?";
            ResultSet rs = JDBCHelper.query(sql, tenkh);
            while (rs.next()) {
                diachi = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return diachi;
    }

    public String sdtKh(String tenkh) {
        String sdt = "";
        try {
            String sql = "SELECT SDT FROM dbo.KHACHHANG WHERE TenKH = ?";
            ResultSet rs = JDBCHelper.query(sql, tenkh);
            while (rs.next()) {
                sdt = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sdt;
    }

    public long TongtienConLai() {
        return (long) tongGiaTriHopDong(MaLT(TenKhachHang())) - Dialog_XuatHDong.TongTienTourDaCoc();
    }

    public String LoaiPT(String tenkh) {
        String loaipt = "";
        try {
            String sql = "SELECT TOP 1 lpt.TenLoaiPT FROM dbo.LOAIPHUONGTIEN lpt\n"
                    + "JOIN dbo.PHUONGTIEN pt\n"
                    + "ON pt.MaLoaiPT = lpt.MaLoaiPT\n"
                    + "JOIN dbo.LICHTRINH_PHUONGTIEN ltpt \n"
                    + "ON ltpt.MaPT = pt.MaPT\n"
                    + "JOIN dbo.LICHTRINH lt\n"
                    + "ON lt.MaLT = ltpt.MaLT\n"
                    + "JOIN dbo.KHACHHANG kh\n"
                    + "ON kh.MaKH = lt.MaKH\n"
                    + "WHERE kh.TenKH LIKE ?";
            ResultSet rs = JDBCHelper.query(sql, tenkh);
            while (rs.next()) {
                loaipt = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return loaipt;
    }

    public String NhanVien(String manv) {
        String tennv = "";
        try {
            String sql = "SELECT CONCAT(HoTen, ' - ', MaNV) FROM dbo.NHANVIEN WHERE MaNV = ?";
            ResultSet rs = JDBCHelper.query(sql, manv);
            while (rs.next()) {
                tennv = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tennv;
    }

    public HopDong getHopDong() {
        String subString = NhanVien(MainForm.lblTitle.getText());
        String manv = subString.substring(subString.lastIndexOf("-") + 1).trim();

        HopDong hd = new HopDong();
        hd.setMaHDong(TruyVanSQL.MaHopDong());
        hd.setMaLT(MaLT(TenKhachHang()));
        hd.setLoaiThanhToan((String) Dialog_XuatHDong.cboHHTT.getSelectedItem());
        hd.setTongTien(tongGiaTriHopDong(MaLT(TenKhachHang())));
        hd.setTienCoc(Dialog_XuatHDong.TongTienTourDaCoc());
        hd.setNgayLapHopDong(XDate.toDate(ngayHienTai(), "dd-MM-yyyy"));
        hd.setMaNV(manv);
        hd.setTrangThaiThanhToan(Dialog_XuatHDong.trangThaiCoc());
        return hd;
    }

    public void insert() {
        HopDong hd = getHopDong();
        try {
            hdDao.insert(hd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String numberToWords(double number) {
        String[] thousands = {"", "nghìn", "triệu", "tỷ"};

        if (number == 0) {
            return "không";
        }

        String prefix = "";
        if (number < 0) {
            prefix = "âm";
            number = -number;
        }

        String current = "";
        int place = 0;

        do {
            int n = (int) (number % 1000);
            if (n != 0) {
                String s = convertLessThanOneThousand(n);
                if (place > 0 && !s.isEmpty()) {
                    s += " " + thousands[place];
                }
                current = s + " " + current;
            }
            place++;
            number /= 1000;
        } while (number > 0);

        return (prefix + current).trim();
    }

    private static String convertLessThanOneThousand(int number) {
        String[] tens = {"", "", "hai mươi", "ba mươi", "bốn mươi", "năm mươi", "sáu mươi", "bảy mươi", "tám mươi", "chín mươi"};
        String[] units = {"", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín", "mười", "mười một", "mười hai", "mười ba", "mười bốn", "mười lăm", "mười sáu", "mười bảy", "mười tám", "mười chín"};
        String current;

        if (number % 100 < 20) {
            current = units[number % 100];
            number /= 100;
        } else {
            current = units[number % 10];
            number /= 10;

            current = tens[number % 10] + " " + current;
            number /= 10;
        }
        if (number == 0) {
            return current;
        }
        return units[number] + " trăm " + current;
    }

    public String giaTriCoc() {
        String giaTricoc = "";
        if (rdo1.isSelected()) {
            giaTricoc = rdo1.getText();
        }
        if (rdo2.isSelected()) {
            giaTricoc = rdo2.getText();
        }
        if (rdo3.isSelected()) {
            giaTricoc = rdo3.getText();
        }
        if (rdo4.isSelected()) {
            giaTricoc = rdo4.getText();
        }
        return giaTricoc;

    }

    public void fillHopDong() {
        String tennv = null;
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = dateObj.format(formatter);
        String ngay = date.substring(0, date.indexOf("-")).trim();
        String thang = date.substring(3, date.lastIndexOf("-")).trim();
        String nam = date.substring(6).trim();

        txtAria.setEditable(false);
        txtAria.append("\n"
                + "   CÔNG TY LỮ HÀNH TRAVEL TOUR \t\t\tCỘNG HÒA XÃ HỘI CHỦ NGHĨA VIỆT NAM\n"
                + "       Số:145/NĐ/HĐKT \t\t\t               Độc lập - Tự do - Hạnh phúc\n"
                + "                \t\t\t\t\t --------------------\n"
                + "\t\t\t\t\t\tCần Thơ Ngày " + ngay + " Tháng " + thang + " Năm " + nam + " \n"
                + "\t             ==============================================\n\n"
                + "\t\t\t         HỢP ĐỒNG DỊCH VỤ LỮ HÀNH \n"
                + "\t\t    (TỔ CHỨC CHƯƠNG TRÌNH THAM QUAN DU LỊCH NỘI ĐỊA) \n\n"
                + "   - Căn cứ Luật thương mại được Quốc hội nước Cộng hòa xã hội chủ nghĩa Việt Nam \n   khóa XI, kỳ họp thứ VII thông qua ngày 14 tháng 06 năm 2005.\n"
                + "   - Căn cứ Bộ luật dân sự được Quốc hội nước cộng hòa xã hội chủ nghĩa Việt Nam \n   khóa XIII thông qua ngày 24 tháng 11 năm 2015.\n"
                + "   - Căn cứ nhu cầu và khả năng của các bên.\n"
                + "   Hôm nay, ngày " + ngay + " tháng " + thang + " năm " + nam + ", tại văn phòng Công ty TRAVEL TOUR. Chúng tôi gồm có:\n\n"
                + "   Bên A: Khách Du Lịch Đi Tour \n"
                + "   Người đại diện: " + TenKhachHang() + "\n"
                + "   Địa chỉ: " + DiaChiKH(TenKhachHang()) + "\n"
                + "   Điện thoại: " + sdtKh(TenKhachHang()) + "\n"
                + "   Hình Thức Thanh Toán: " + Dialog_XuatHDong.cboHHTT.getSelectedItem() + "\n"
                + "   BÊN B: CÔNG TY LỮ HÀNH TRAVEL TOUR\n"
                + "   Địa chỉ: 288, Nguyễn Văn Linh, phường An Khánh, quận Ninh Kiều, Cần Thơ\n"
                + "   Người đại diện theo pháp luật công ty: Nguyễn Hoàng Đan Thụy\n"
                + "   Chức vụ: Giám Đốc\n"
                + "   Điện thoại: (0292) 665122 - 0967363343\n"
                + "   Giấy phép kinh doanh số: 0 8 7 6 4 1 2 3 6 4 – Nơi cấp: Cần Thơ\n"
                + "   Tài khoản ngân hàng : 070112472485 - Sacombank chi nhánh An Giang\n"
                + "   Hai bên thống nhất ký một số điều khoản phục vụ khách du lịch như sau:                 \n"
                + "   Điều 1: Chương trình tham quan du lịch                                                 \n"
                + "   Bên B tổ chức cho bên A chương trình tham quan Theo Phụ Lục 1 :                        \n"
                + "   (Có thêm chương trình chi tiết và là một phần không thể tách rời của hợp đồng sẽ được  \n"
                + "   gửi file kèm theo hợp đồng.)                                                           \n"
                + "   Phương tiện: Xe ô tô loại " + LoaiPT(TenKhachHang()) + " đời mới đầy dủ tiện nghi có máy lạnh hiện \n"
                + "   đại, tivi, ghế nằm..., tài xế nhiệt tình vui vẻ, chu đáo và an toàn.                   \n"
                + "   Mức ăn chính: 100.000đ/người/bữa chính theo chương trình và ăn sáng 50.000đ/người/bữa  \n"
                + "   được công ty hỗ trợ(nếu có).                                                           \n"
                + "   Phòng nghỉ tiêu chuẩn 3 sao, từ 2 - 4 người/phòng.                                     \n"
                + "   Hướng dẫn viên: Chuyên nghiệp, phục vụ nhiệt tình, thành thạo, chu đáo suốt chuyến đi. \n"
                + "   Vé thăm quan: Khách được mua tiền vé vào cửa các thắng cảnh có trong tour.             \n"
                + "   Bảo hiểm du lịch theo quy định của Tổng Cục Du Lịch.                                   \n"
                + "   Nước uống trên xe, nón, thuốc chống say...                                             \n"
                + "   Nếu khách hàng muốn thêm dịch vụ ngoài hợp đồng thì sẽ đóng thêm phí.                  \n"
                + "   Điều 2: Số lượng khách                                                                 \n"
                + "   * Nếu bên A giảm quá số lượng khách tối thiểu như trên hợp đồng đã ký " + soKhach() + " khách, bên A  \n"
                + "   chịu 50% đơn giá mỗi khách giảm theo hợp đồng. Số lượng khách tăng được tính phát sinh \n"
                + "   theo đơn giá trên hợp đồng.                                                            \n"
                + "   Điều 3: Thời gian thực hiện công việc                                                  \n"
                + "   1. Thời gian thực hiện công việc từ ngày " + ngayDi() + " đến ngày " + ngayVe() + " \n"
                + "   2. Điểm đón: 01 điểm                                                                   \n"
                + "   Cụ thể tại: " + DiaDiemDuaDon() + " \n"
                + "   Đón khách: Vào lúc " + GioDi() + " " + ngayDi() + " \n"
                + "   3. Liên hệ trưởng đoàn: " + TenKhachHang() + " Số điện thoại: " + sdtKh(TenKhachHang()) + " \n"
                + "   * Để đảm bảo tài sản và sự an toàn của Quý Khách, lái xe của công ty sẽ có trách nhiệm.\n"
                + "   trả khách tại điểm mà xe đón khách lúc đầu.                                            \n"
                + "   Điều 4: Giá trị hợp đồng                                                               \n"
                + "   Giá cho 01 khách: " + new DungChung.DungChung().convertTien(String.valueOf(Dialog_XuatHDong.TienChoMoiKhach(MaLT(TenKhachHang())))) + " VNĐ  \n"
                + "   (Bằng Chữ: " + numberToWords(Dialog_XuatHDong.TienChoMoiKhach(MaLT(TenKhachHang()))) + ")\n"
                + "   Tổng số khách theo hợp đồng: " + soKhach() + " người\n"
                + "   Tổng giá trị hợp đồng: (đã bao gồm 10% VAT): " + new DungChung.DungChung().convertTien(String.valueOf(Dialog_XuatHDong.tongGiaTriHopDong(MaLT(TenKhachHang())))) + " VNĐ.\n"
                + "   (Bằng chữ: " + numberToWords(Dialog_XuatHDong.tongGiaTriHopDong(MaLT(TenKhachHang()))) + " )\n"
                + "   Bảo hiểm du lịch: Mức đền bù tối đa 99% VNĐ/người/vụ việc.     \n"
                + "   Điều 5: Phương thức thanh toán                                                         \n"
                + "   Bên A tạm ứng cho bên B số tiền bằng " + giaTriCoc() + " tổng giá trị hợp đồng.\n"
                + "   Bên A Tạm ứng cho bên B số tiền là: " + new DungChung.DungChung().convertTien(String.valueOf(Dialog_XuatHDong.TongTienTourDaCoc())) + " VNĐ. \n"
                + "   (Bằng chữ: " + numberToWords(Dialog_XuatHDong.TongTienTourDaCoc()) + ") \n"
                + "   Còn lại: " + new DungChung.DungChung().convertTien(String.valueOf(TongtienConLai())) + " VNĐ. \n"
                + "   (Bằng chữ: " + numberToWords(TongtienConLai()) + ") \n"
                + "   Hình thức thanh toán:                                                                  \n"
                + "   Sẽ thanh toán sau khi kí kết hợp đồng, kèm theo Thanh lý hợp đồng. \n"
                + "   - Chuyển vào tài khoản của công ty số : 070112472485 \n"
                + "   Tại ngân hàng: SACOMBANK chi nhánh: PGD Số 59 - Đ.Nguyễn Huệ, Thoại Sơn, An Giang \n"
                + "   - Sau khi bên B thực hiện xong hợp đồng. Bên A có trách nhiệm thanh toán đầy đủ số tiền\n"
                + "   còn lại theo số lượng thực tế cho bên B.                                               \n"
                + "   Điều 6: Điều kiện phạt hủy hợp đồng                                                    \n"
                + "   1. Hai bên cam kết phải thực hiện hợp đồng, nếu một trong hai bên thay đổi hoặc hủy bỏ \n"
                + "   phải báo trước cho bên kia 10 ngày trước khi khởi hành.                                \n"
                + "   2. Trong trường hợp báo huỷ trước 8 đến 10 ngày trước khi khởi hành, bên báo hủy phải  \n"
                + "   chịu phạt 30% tổng giá trị hợp đồng; báo hủy trước 5 đến 7 ngày, thì phải chịu phạt 50%\n"
                + "   tổng giá trị hợp đồng; báo hủy trước 2 đến 4 ngày thì phải chịu phạt 70% tổng giá trị  \n"
                + "   hợp đồng; báo hủy trong vòng 24h trước giờ khởi hành thì phải chịu phạt 100% tổng giá  \n"
                + "   trị hợp đồng. Mọi thay đổi, báo hủy phải được thông báo bằng văn bản và được sự chấp   \n"
                + "   thuận của bên kia.                                                                     \n"
                + "   3. Trong trường hợp vì một lý do bất khả kháng nào đó (bão lụt, hoả hoạn, thiên tai,   \n"
                + "   chiến tranh,...) hợp đồng không thể thực hiện thì các bên cùng nhau bàn bạc giải quyết \n"
                + "   trên tinh thần bình đẳng giữa hai bên.                                                 \n"
                + "   Điều 7: Trách nhiệm của các bên                                                        \n"
                + "   1. Bên A có trách nhiệm thông báo chi tiết và xác nhận về lượng khách kèm theo danh    \n"
                + "   sách trích ngang, địa điểm, thời gian, và thông tin liên quan của đoàn khách trước 03  \n"
                + "   ngày khởi hành cho bên B. Bên B có trách nhiệm đưa đón, phục vụ đoàn khách của bên A   \n"
                + "   đúng như trong lộ trình chi tiết của phụ lục kèm theo hợp đồng, bảo đảm chất lượng về  \n"
                + "   dịch vụ theo hợp đồng.                                                                 \n"
                + "   2. Bên A thanh toán đầy đủ, đúng hạn cho bên B tổng giá trị hợp đồng theo phương thức  \n"
                + "   đã nêu trên. Nếu phát sinh chi phí cho việc làm hay yêu cầu của bên A thì bên A phải   \n"
                + "   thanh toán thêm khoản chi phí đó cho bên B.                                            \n"
                + "   3. Trong quá trình thực hiện hợp đồng, mọi phát sinh tranh chấp đều được hai bên cùng  \n"
                + "   nhau bàn bạc và giải quyết trên tinh thần bình đẳng hai bên đều có lợi.                \n"
                + "   4. Những phụ lục hợp đồng kèm theo có giá trị pháp lý như bản hợp đồng này.            \n"
                + "   5. Hai bên cam kết sẽ thực hiện đúng những điều khoản như trong hợp đồng, bên nào thực \n"
                + "   hiện sai gây tổn hại về thời gian, vật chất cho bên kia thì phải chịu trách nhiệm bồi  \n"
                + "   hoàn phần tổn hại đó cho bên kia theo quy định trước pháp luật.                        \n"
                + "   Hợp đồng này gồm có 02 trang và được lập 02 bản, mỗi bên giữ 01 bản có giá trị pháp lý \n"
                + "   như nhau và có hiệu lực kể từ ngày ký.                                                 \n"
                + "\n\n"
                + "            ĐẠI DIỆN BÊN A \t\t\t\t\t  ĐẠI DIỆN BÊN B\n\n\n\n\n"
                + "         " + TenKhachHang() + "\t\t\t\t\tNguyễn Hoàng Đan Thuỵ"
                + "\n\n\n\n\n"
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrint = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAria = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        chkDK = new javax.swing.JCheckBox();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Kiểm Tra Thông Tin Hợp Đồng");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlPrint.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txtAria.setColumns(20);
        txtAria.setRows(5);
        jScrollPane1.setViewportView(txtAria);

        pnlPrint.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 796, 710));

        getContentPane().add(pnlPrint, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 710));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        chkDK.setBackground(new java.awt.Color(255, 255, 255));
        chkDK.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        chkDK.setText("Tôi đồng ý với tất cả những điều khoản, điều kiện trên");
        jPanel1.add(chkDK, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 12, -1, 30));

        jButton1.setBackground(new java.awt.Color(51, 104, 234));
        jButton1.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        jButton1.setForeground(new java.awt.Color(51, 104, 234));
        jButton1.setText("Đồng Ý");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.setOpaque(true);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 8, 120, 35));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 710, 796, 50));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if (!chkDK.isSelected()) {
                MsgBox.alert(this, "Vui Lòng Đồng Ý Các Điều Khoản");
            } else {
                insert();
                dispose();
                MsgBox.alertSuccess(new Frame_TTLichTrinh(), "Lập Hợp Đồng Thành Công");
                AddLichSuHD.addLSHD("Lập hợp đồng thanh toán");
                txtAria.print();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    public static void main(String args[]) {
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DiaLog_PrintHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiaLog_PrintHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiaLog_PrintHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiaLog_PrintHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DiaLog_PrintHopDong dialog = new DiaLog_PrintHopDong(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox chkDK;
    private javax.swing.JButton jButton1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlPrint;
    public static javax.swing.JTextArea txtAria;
    // End of variables declaration//GEN-END:variables
}
