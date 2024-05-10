package UI;

import DAO.HoaDonDAO;
import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import TravelEntity.HoaDon;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class Dialog_PrintHoaDon extends javax.swing.JDialog {

    HoaDonDAO hdDAO = new HoaDonDAO();

    public Dialog_PrintHoaDon(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        successThongTin();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void successThongTin() {
        Frame_TTHoaDon.TTKhachHang(lblMaHdon,
                lblTenDV,
                lblNgayCoc,
                lblGiaTienDaCoc,
                lblGiaTien,
                lblConLai,
                lblTenNguoiMua,
                lblCCCD,
                lblTenKH,
                lblSDT,
                lblDiaChi,
                lblHinhThucTT,
                lblTongTien,
                lblGioLapHoaDon,
                lblAn
        );
        lblTongTienBangChu.setText(numberToWords(Double.parseDouble(lblAn.getText())));
    }

    public void setTrangThaiIn() {
        List<HoaDon> hd = hdDAO.selecMaHD(lblMaHdon.getText());
        for (HoaDon hoaDon : hd) {
            hoaDon.setTrangThaiHDon("Đã in");
            hdDAO.updatePrint(hoaDon);
        }
    }

    public void printRecord() {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(new Printable() {
            @Override
            public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
                if (pageIndex > 0) {
                    return Printable.NO_SUCH_PAGE;
                }

                Graphics2D g2 = (Graphics2D) graphics;
                g2.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
                g2.scale(0.85, 0.85);
                pnlPrint.print(g2);

                return Printable.PAGE_EXISTS;
            }
        });

        boolean returningResult = printerJob.printDialog();

        if (returningResult) {
            try {
                printerJob.print();
                setTrangThaiIn();
                MsgBox.alert(this, "In Hoá Đơn Thành Công");
                AddLichSuHD.addLSHD("In hoá đơn");
                Frame_TTHoaDon.fillTable();
                dispose();
            } catch (Exception e) {
                e.printStackTrace();
            }
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlPrint = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblMaHdon = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        lblConLai = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jLabel53 = new javax.swing.JLabel();
        lblTenDV = new javax.swing.JLabel();
        lblGiaTienDaCoc = new javax.swing.JLabel();
        lblNgayCoc = new javax.swing.JLabel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();
        lblGiaTien = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblHinhThucTT = new javax.swing.JLabel();
        lblDiaChi = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lblSDT = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        heloo1 = new javax.swing.JLabel();
        lblCCCD = new javax.swing.JLabel();
        lblGioLapHoaDon = new javax.swing.JLabel();
        lblTenKH = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblAn = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel56 = new javax.swing.JLabel();
        lblTongTien = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        lblTenNguoiMua = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel46 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        lblTongTienBangChu = new javax.swing.JLabel();
        jLabel62 = new javax.swing.JLabel();
        lblTenNhanVien = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thông Tin Hoá Đơn");
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        pnlPrint.setBackground(new java.awt.Color(255, 255, 255));
        pnlPrint.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 204)));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblMaHdon.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        lblMaHdon.setForeground(new java.awt.Color(227, 34, 45));
        lblMaHdon.setText("HDN001");
        jPanel2.add(lblMaHdon, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 30, 100, -1));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(98, 95, 142));
        jLabel1.setText("Số:");
        jPanel2.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 60, -1));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(98, 95, 142));
        jLabel2.setText("( Bản thể hiện của hoá đơn điện tử)");
        jPanel2.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 60, 230, -1));

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel3.setText("04699412");
        jPanel2.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 80, 90, -1));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(98, 95, 142));
        jLabel4.setText("Ký hiệu:");
        jPanel2.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 50, 60, -1));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(98, 95, 142));
        jLabel6.setText("Mẫu số: ");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 60, -1));

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setText("01GTKT0/001");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 20, 100, -1));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setText("CD/16E");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 90, -1));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/logo.png"))); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 1, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 10, 100, 90));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(227, 34, 45));
        jLabel23.setText(")");
        jPanel2.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 30, 10, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(227, 34, 45));
        jLabel24.setText("HOÁ ĐƠN");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 30, 80, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(227, 34, 45));
        jLabel26.setText("(");
        jPanel2.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 30, 10, -1));

        jLabel7.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(98, 95, 142));
        jLabel7.setText("Mẫu số: ");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 20, 60, -1));

        pnlPrint.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 700, 110));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 204)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(227, 34, 45));
        jLabel11.setText("CÔNG TY TNHH DU LỊCH LỮ HÀNH TRAVEL - TOUR");
        jPanel3.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 410, 30));

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(98, 95, 142));
        jLabel12.setText("Số tài khoản:");
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 90, -1));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(98, 95, 142));
        jLabel13.setText("1 0 8 6 7 8 9 8 7 5 4");
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 40, 130, -1));

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(98, 95, 142));
        jLabel14.setText("  0967 363 343                              ");
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 450, 20));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(98, 95, 142));
        jLabel15.setText("Điện Thoại:");
        jPanel3.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 80, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(98, 95, 142));
        jLabel16.setText("Email:");
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 80, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(98, 95, 142));
        jLabel18.setText("Giấy phép kinh doanh:");
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 150, -1));

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(98, 95, 142));
        jLabel19.setText("Địa Chỉ:");
        jPanel3.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, 50, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(98, 95, 142));
        jLabel17.setText("070112472485 Tại Ngân Hàng Sacombank Huyện Thoại Sơn, Tỉnh An Giang");
        jPanel3.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 120, 520, 20));

        jLabel20.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(98, 95, 142));
        jLabel20.setText("  888 Nguyễn Văn Linh, phường An Khánh, quận Ninh Kiều Cần Thơ");
        jPanel3.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 60, 430, 20));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(98, 95, 142));
        jLabel21.setText("  traveltourpass@gmail.com -  Web: traveltourfpoly.000webhost.com");
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 100, 440, 20));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel22.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/qr.jpg"))); // NOI18N
        jLabel22.setToolTipText("In Hoá Đơn");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel22.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel22MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel3.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 100, 100));

        pnlPrint.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 110, 700, 150));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 204)));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(98, 95, 142));
        jLabel49.setText("Còn Lại:");
        jPanel5.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 220, -1));

        lblConLai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblConLai.setForeground(new java.awt.Color(98, 95, 142));
        lblConLai.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblConLai.setText("138.000.000");
        jPanel5.add(lblConLai, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 240, -1));

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(98, 95, 142));
        jLabel50.setText("Tên Dịch Vụ:");
        jPanel5.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 220, -1));

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(98, 95, 142));
        jLabel52.setText("Đã Cọc: ");
        jPanel5.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 220, -1));

        jLabel53.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel53.setForeground(new java.awt.Color(98, 95, 142));
        jLabel53.setText("Ngày Cọc:");
        jPanel5.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 70, -1));

        lblTenDV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenDV.setForeground(new java.awt.Color(98, 95, 142));
        lblTenDV.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTenDV.setText("Cần Thơ - Đà Lạt");
        jPanel5.add(lblTenDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 10, 240, -1));

        lblGiaTienDaCoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGiaTienDaCoc.setForeground(new java.awt.Color(98, 95, 142));
        lblGiaTienDaCoc.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblGiaTienDaCoc.setText("138.000.000");
        jPanel5.add(lblGiaTienDaCoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 70, 200, 20));

        lblNgayCoc.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNgayCoc.setForeground(new java.awt.Color(98, 95, 142));
        lblNgayCoc.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblNgayCoc.setText("17-09-2022");
        jPanel5.add(lblNgayCoc, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 80, 20));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(98, 95, 142));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel60.setText("VNĐ");
        jPanel5.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 70, 30, -1));

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(98, 95, 142));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel61.setText("VNĐ");
        jPanel5.add(jLabel61, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, 30, -1));

        lblGiaTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGiaTien.setForeground(new java.awt.Color(98, 95, 142));
        lblGiaTien.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblGiaTien.setText("138.000.000");
        jPanel5.add(lblGiaTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 50, 240, -1));

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(98, 95, 142));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel55.setText("VNĐ");
        jPanel5.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, -1, -1));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(98, 95, 142));
        jLabel51.setText("Giá Tiền (Đã bao gồm thuế VAT): ");
        jPanel5.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 220, -1));

        pnlPrint.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 380, 700, 120));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 204)));
        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(98, 95, 142));
        jLabel25.setText("Hình Thức Thanh Toán: ");
        jPanel6.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 90, 150, -1));

        jLabel27.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(98, 95, 142));
        jLabel27.setText("Số Điện Thoại:");
        jPanel6.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 100, -1));

        lblHinhThucTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblHinhThucTT.setForeground(new java.awt.Color(98, 95, 142));
        lblHinhThucTT.setText("Thanh Toán Bằng Hình Thức Chuyển Khoản");
        jPanel6.add(lblHinhThucTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 90, 430, -1));

        lblDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDiaChi.setForeground(new java.awt.Color(98, 95, 142));
        lblDiaChi.setText("  888 Nguyễn Văn Linh, phường An Khánh, quận Ninh Kiều Cần Thơ");
        jPanel6.add(lblDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 70, 430, -1));

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(98, 95, 142));
        jLabel37.setText("Địa Chỉ:");
        jPanel6.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 50, -1));

        jLabel38.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(98, 95, 142));
        jLabel38.setText("CMND / CCCD:");
        jPanel6.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 100, -1));

        lblSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblSDT.setForeground(new java.awt.Color(98, 95, 142));
        lblSDT.setText("0905015900");
        jPanel6.add(lblSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 50, 240, -1));

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(98, 95, 142));
        jLabel40.setText("Họ và Tên người mua hàng:");
        jPanel6.add(jLabel40, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 180, -1));

        heloo1.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        heloo1.setForeground(new java.awt.Color(98, 95, 142));
        heloo1.setText("Giờ Lập Hoá Đơn:");
        jPanel6.add(heloo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 10, 120, -1));

        lblCCCD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCCCD.setForeground(new java.awt.Color(98, 95, 142));
        lblCCCD.setText("089002541125");
        jPanel6.add(lblCCCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 30, 240, -1));

        lblGioLapHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblGioLapHoaDon.setForeground(new java.awt.Color(98, 95, 142));
        lblGioLapHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGioLapHoaDon.setText("11:00:00");
        jPanel6.add(lblGioLapHoaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 10, 70, -1));

        lblTenKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenKH.setForeground(new java.awt.Color(98, 95, 142));
        lblTenKH.setText("Lê Quang Nhựt");
        jPanel6.add(lblTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 10, 240, -1));

        pnlPrint.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 260, 700, 120));

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));
        jPanel7.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(0, 153, 204)));
        jPanel7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        lblAn.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblAn.setForeground(new java.awt.Color(255, 255, 255));
        lblAn.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblAn.setText("1.818.182");
        jPanel7.add(lblAn, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 200, 40));

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(98, 95, 142));
        jLabel43.setText("Tổng tiền thanh toán:");
        jPanel7.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 0, 190, 40));

        jLabel56.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel56.setForeground(new java.awt.Color(98, 95, 142));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel56.setText("VNĐ");
        jPanel7.add(jLabel56, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 0, -1, 40));

        lblTongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTongTien.setForeground(new java.awt.Color(98, 95, 142));
        lblTongTien.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblTongTien.setText("1.818.182");
        jPanel7.add(lblTongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 0, 200, 40));

        pnlPrint.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 500, 700, 40));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 2, 10)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(98, 95, 142));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("Khởi tạo từ Phần mềm hoá đơn VACOM - Chi nhánh Công ty Cổ phần Công nghệ Phần mềm kế toán VACOM - MST: 0102236276-001 - SĐT: 0918 293098");
        pnlPrint.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 720, 720, 20));

        lblTenNguoiMua.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenNguoiMua.setForeground(new java.awt.Color(98, 95, 142));
        lblTenNguoiMua.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNguoiMua.setText("Lê Quang Nhựt");
        pnlPrint.add(lblTenNguoiMua, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 680, 250, 20));

        jLabel45.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(98, 95, 142));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("Người mua ");
        pnlPrint.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 580, 120, 20));

        jLabel47.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(98, 95, 142));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("Giám Đốc");
        pnlPrint.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 580, 120, 20));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(98, 95, 142));
        jLabel46.setText("Số tiền viết bằng chữ: ");
        pnlPrint.add(jLabel46, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, 150, 20));

        jLabel48.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel48.setForeground(new java.awt.Color(98, 95, 142));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setText("(Ký, ghi rõ họ, tên)");
        pnlPrint.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 600, 120, 20));

        lblTongTienBangChu.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        lblTongTienBangChu.setForeground(new java.awt.Color(98, 95, 142));
        lblTongTienBangChu.setText("một triệu đồng chẵn");
        pnlPrint.add(lblTongTienBangChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 550, 550, 20));

        jLabel62.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel62.setForeground(new java.awt.Color(98, 95, 142));
        jLabel62.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel62.setText("(Ký, ghi rõ họ, tên)");
        pnlPrint.add(jLabel62, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 600, 120, 20));

        lblTenNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTenNhanVien.setForeground(new java.awt.Color(98, 95, 142));
        lblTenNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenNhanVien.setText("Nguyễn Hoàng Đan Thuỵ");
        pnlPrint.add(lblTenNhanVien, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 680, 240, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrint, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlPrint, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel22MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel22MouseClicked

    }//GEN-LAST:event_jLabel22MouseClicked

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_P) {
            printRecord();
        }
    }//GEN-LAST:event_formKeyPressed

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
            java.util.logging.Logger.getLogger(Dialog_PrintHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog_PrintHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog_PrintHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog_PrintHoaDon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialog_PrintHoaDon dialog = new Dialog_PrintHoaDon(new javax.swing.JFrame(), true);
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
    private javax.swing.JLabel heloo1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JLabel jLabel62;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JLabel lblAn;
    private javax.swing.JLabel lblCCCD;
    private javax.swing.JLabel lblConLai;
    private javax.swing.JLabel lblDiaChi;
    private javax.swing.JLabel lblGiaTien;
    private javax.swing.JLabel lblGiaTienDaCoc;
    private javax.swing.JLabel lblGioLapHoaDon;
    private javax.swing.JLabel lblHinhThucTT;
    private javax.swing.JLabel lblMaHdon;
    private javax.swing.JLabel lblNgayCoc;
    private javax.swing.JLabel lblSDT;
    private javax.swing.JLabel lblTenDV;
    private javax.swing.JLabel lblTenKH;
    private javax.swing.JLabel lblTenNguoiMua;
    private javax.swing.JLabel lblTenNhanVien;
    private javax.swing.JLabel lblTongTien;
    private javax.swing.JLabel lblTongTienBangChu;
    private javax.swing.JPanel pnlPrint;
    // End of variables declaration//GEN-END:variables
}
