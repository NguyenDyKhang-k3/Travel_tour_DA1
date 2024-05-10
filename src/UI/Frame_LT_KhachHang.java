package UI;

import DAO.LT_KhachHangDAO;
import DAO.LichTrinhDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import DungChung.XDate;
import SQL.JDBCHelper;
import TravelEntity.LT_KhachHang;
import TravelEntity.LichTrinh;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author khang
 */
public class Frame_LT_KhachHang extends javax.swing.JFrame {

    LT_KhachHangDAO ltkhDao = new LT_KhachHangDAO();
    LichTrinhDAO ltDao = new LichTrinhDAO();

    public Frame_LT_KhachHang() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        setLocationRelativeTo(null);
        fillCboxLichTrinh();
        checkLichTrinhDaDi();
        AutoCompleteDecorator.decorate(cboMaLT);
        tblKH.setDefaultEditor(Object.class, null);
        tblLT.setDefaultEditor(Object.class, null);
    }

    public void checkLichTrinhDaDi() {
        String cbo = (String) cboMaLT.getSelectedItem();
        String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();
        List<LichTrinh> list = ltDao.selectSLKH(MaLT);
        for (LichTrinh lt : list) {
            if (lt.getTrangThai().equals("Chưa đi")) {
                btnThem.setEnabled(true);
                return;
            }
            if (lt.getTrangThai().equals("Đang đi")) {
                btnThem.setEnabled(false);
                return;
            }
            if (lt.getTrangThai().equals("Đã đi")) {
                btnThem.setEnabled(false);
                return;
            }

        }
    }

    private void editColumnWidthLT() {
        int[] col = new int[]{50, 150, 350, 90, 100, 100, 100, 100, 100};
        new DungChung.DungChung().editColumnWidth(col, tblLT);
    }

    private void editColumnWidthKH() {
        int[] col = new int[]{50, 100, 200, 100, 150, 150, 100, 300};
        new DungChung.DungChung().editColumnWidth(col, tblKH);
    }

    public void fillCboxLichTrinh() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaLT.getModel();
        model.removeAllElements();
        String malt = "";
        try {
            String sql = "SELECT t.TenTour, kh.TenKH, MaLT FROM LICHTRINH lt INNER JOIN KHACHHANG kh ON kh.MaKH = lt.MaKH \n"
                    + "INNER JOIN dbo.TOUR t\n"
                    + "ON t.MaTour = lt.MaTour\n"
                    + "WHERE  lt.TrangThai = N'Chưa đi' OR  lt.TrangThai = N'Đang đi' OR lt.TrangThai = N'Đã đi'";
//            lt.TrangThai = N'Chưa đi' OR  lt.TrangThai = N'Đang đi' OR lt.TrangThai = N'Đã đi'
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                malt = rs.getString(3);
                model.addElement(rs.getString(1) + " - " + rs.getString(2) + " - " + rs.getString(3));
            }
            fillTableLichTrinh();
//            
//            List<LichTrinh> lt = ltDao.selectSLKH(malt);
//            for (LichTrinh lichTrinh : lt) {
//                if (lichTrinh.getTrangThai().equals("Đã đi")) {
//                    btnThem.setEnabled(false);
//                }
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTableKhachHang() {
        String cbo = (String) cboMaLT.getSelectedItem();
        String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();
        try {
            String[] header = new String[]{"STT", "Mã KH", "Tên KH", "Giới Tính", "Ngày Sinh", "CMND", "SĐT", "Email"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY MaKH), MaKH , TenKH, GioiTinh, NgaySinh, GTTT, SDT, Email \n"
                    + "FROM dbo.KHACHHANG \n"
                    + "WHERE (MaKH LIKE ? OR TenKH LIKE ? OR GTTT LIKE ? OR SDT LIKE ? OR Email LIKE ?) \n"
                    + "AND MaKH NOT IN(SELECT MaKH FROM dbo.LICHTRINH_KHACHHANG WHERE MaLT = ?)";
            ResultSet rs = JDBCHelper.query(
                    sql,
                    "%" + txtFindKH.getText() + "%",
                    "%" + txtFindKH.getText() + "%",
                    "%" + txtFindKH.getText() + "%",
                    "%" + txtFindKH.getText() + "%",
                    "%" + txtFindKH.getText() + "%",
                    MaLT
            );
            while (rs.next()) {
                Vector data = new Vector();
                data.add(rs.getString(1));
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(rs.getBoolean(4) ? "Nam" : "Nữ");
                data.add(XDate.toString(rs.getDate(5), "dd-MM-yyyy"));
                data.add(rs.getString(6));
                data.add(rs.getString(7));
                data.add(rs.getString(8));
                model.addRow(data);
            }
            tblKH.setModel(model);
            editColumnWidthKH();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTableLichTrinh() {

        if (cboMaLT.getSelectedItem() == null) {
            return;
        } else {
            String cbo = (String) cboMaLT.getSelectedItem();
            String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();

            try {
                String[] header = new String[]{"STT", "TT Lịch Trình", "TT Khách Hàng", "Giới Tính", "Ngày Bắt Đầu", "Ngày Kết Thúc",
                    "Ngày Sinh", "SĐT", "Người Tạo"};
                DefaultTableModel model = new DefaultTableModel(header, 0);
                String sql = "SELECT ROW_NUMBER() OVER (ORDER BY ltkh.MaLT),\n"
                        + "t.TenTour, \n"
                        + "CONCAT(kh.MaKH, ' - ', kh.TenKH, ' - ', kh.GTTT),\n"
                        + "kh.GioiTinh,\n"
                        + "lt.NgayBD,\n"
                        + "lt.NgayKT,\n"
                        + "kh.NgaySinh,\n"
                        + "kh.SDT,\n"
                        + "ltkh.NguoiTao\n"
                        + "FROM dbo.LICHTRINH_KHACHHANG ltkh\n"
                        + "JOIN dbo.LICHTRINH lt\n"
                        + "ON lt.MaLT = ltkh.MaLT\n"
                        + "JOIN dbo.TOUR t \n"
                        + "ON t.MaTour = lt.MaTour\n"
                        + "JOIN dbo.KHACHHANG kh \n"
                        + "ON kh.MaKH = ltkh.MaKH\n"
                        + "WHERE ltkh.MaLT = ?";
                ResultSet rs = JDBCHelper.query(sql, MaLT);
                while (rs.next()) {
                    Vector data = new Vector();
                    data.add(rs.getString(1));
                    data.add(rs.getString(2));
                    data.add(rs.getString(3));
                    data.add(rs.getBoolean(4) ? "Nam" : "Nữ");
                    data.add(XDate.toString(rs.getDate(5), "dd-MM-yyyy"));
                    data.add(XDate.toString(rs.getDate(6), "dd-MM-yyyy"));
                    data.add(XDate.toString(rs.getDate(7), "dd-MM-yyyy"));
                    data.add(rs.getString(8));
                    data.add(rs.getString(9));
                    model.addRow(data);
                }
                tblLT.setModel(model);
                editColumnWidthLT();
                fillTableKhachHang();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean chkKhachHangTheoNgay() {
        String cbo = (String) cboMaLT.getSelectedItem();
        String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();
        int[] selectedRows = tblKH.getSelectedRows();
        try {
            String sql1 = "{call GetLichTrinh(?)}";
            ResultSet rs = JDBCHelper.query(sql1, MaLT);
            String sql = "{call GetLichTrinhKhachHang(?)}";
            while (rs.next()) {
                for (int i = 0; i < selectedRows.length; i++) {
                    String MaKH = ((String) tblKH.getValueAt(selectedRows[i], 1)).trim();
                    ResultSet rs1 = JDBCHelper.query(sql, MaKH);
                    if (!rs1.next()) {
                        return true;
                    }
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = LocalDate.parse(rs.getString(1), formatter);
                    LocalDate endDate = LocalDate.parse(rs.getString(2), formatter);
                    LocalDate rangeStartDate = LocalDate.parse(rs1.getString(1), formatter);
                    LocalDate rangeEndDate = LocalDate.parse(rs1.getString(2), formatter);

                    startDate.toString();
                    endDate.toString();
                    rangeStartDate.toString();
                    rangeEndDate.toString();

                    if (endDate.isBefore(rangeStartDate) || startDate.isAfter(rangeEndDate)) {
                    } else {
                        return false;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void ThemKhachHang() {
        if (!chkKhachHangTheoNgay()) {
            MsgBox.alertWarning(this, "Khách Hàng Đã Tồn Tại Ở Tour Khác !");
            return;
        }
        String cbo = (String) cboMaLT.getSelectedItem();
        String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();

        List<LichTrinh> list = ltDao.selectSLKH(MaLT);

        for (int row : tblKH.getSelectedRows()) {
            for (LichTrinh lt : list) {
                if (TruyVanSQL.chkSoluongKH(MaLT) >= lt.getSoKhach()) {
                    MsgBox.alertWarning(this, "Số Lượng Đã Vượt Mốc " + lt.getSoKhach() + " Khách Hàng");
                    return;
                }
            }
            LT_KhachHang ltkh = new LT_KhachHang();
            ltkh.setMalt(MaLT);
            ltkh.setMakh((String) tblKH.getValueAt(row, 1));
            ltkh.setNguoiTao(MainForm.lblTitle.getText());
            ltkhDao.insert(ltkh);
        }
        MsgBox.alertSuccess(this, "Thêm Khách Hàng Thành Công !");
        AddLichSuHD.addLSHD("Thêm Khách Hàng Vào Lịch Trình");
        fillTableLichTrinh();
        fillTableKhachHang();
        tabs.setSelectedIndex(0);
    }

    public void XoaKhachHang() {
        ThongBao1 obj = new ThongBao1("Bạn Có Chắc Muốn Xoá Khách Hàng Ra Khỏi Lịch Trình Không ?");
        obj.eventOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int row : tblLT.getSelectedRows()) {
                    String makh = tblLT.getValueAt(row, 2).toString();
                    String subTring = makh.substring(0, makh.indexOf("-")).trim();

                    String cbo = (String) cboMaLT.getSelectedItem();
                    String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();

                    ltkhDao.deleteNew(MaLT, subTring);
                }
                AddLichSuHD.addLSHD("Xoá Khách Hàng Khỏi Lịch Trình");
                fillTableLichTrinh();
                GlassPanePopup.closePopupLast();
                MsgBox.alertSuccess(new Frame_LT_KhachHang(), "Xoá Khách Hàng Thành Công !");
            }
        });
        GlassPanePopup.showPopup(obj);
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG KHÁCH HÀNG THEO LỊCH TRÌNH", "Nhân viên", tblLT, new int[]{1500, 6000, 8000, 5000, 6500, 5500, 6000, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new DungChung.ExportPDF().exportPDF("DANH SÁCH BẢNG KHÁCH HÀNG THEO LỊCH TRÌNH", tblLT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        lblLoiEmail1 = new javax.swing.JLabel();
        lblLoiGiaTour2 = new javax.swing.JLabel();
        tabs = new tabbed.MaterialTabbed();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLT = new DungChung.Table();
        btnXoa = new javax.swing.JButton();
        btnExcel1 = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblKH = new DungChung.Table();
        jPanel4 = new javax.swing.JPanel();
        txtFindKH = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        cboMaLT = new CboAndTxtSugestion.ComboBoxSuggestion();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN KHÁCH HÀNG THEO LỊCH TRÌNH");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        lblLoiEmail1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiEmail1.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiEmail1.setText(" ");
        QLNV.add(lblLoiEmail1, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 290, 150, 16));

        lblLoiGiaTour2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaTour2.setForeground(new java.awt.Color(255, 0, 0));
        QLNV.add(lblLoiGiaTour2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 220, 180, 20));

        tabs.setForeground(new java.awt.Color(190, 79, 60));
        tabs.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N

        tblLT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblLT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(tblLT);

        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonXoaMoi.png"))); // NOI18N
        btnXoa.setBorder(null);
        btnXoa.setBorderPainted(false);
        btnXoa.setContentAreaFilled(false);
        btnXoa.setDefaultCapable(false);
        btnXoa.setFocusPainted(false);
        btnXoa.setFocusable(false);
        btnXoa.setIconTextGap(0);
        btnXoa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXoaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXoaMouseExited(evt);
            }
        });
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnExcel1.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonExcel.png"))); // NOI18N
        btnExcel1.setBorder(null);
        btnExcel1.setBorderPainted(false);
        btnExcel1.setContentAreaFilled(false);
        btnExcel1.setDefaultCapable(false);
        btnExcel1.setFocusPainted(false);
        btnExcel1.setFocusable(false);
        btnExcel1.setIconTextGap(0);
        btnExcel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcel1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcel1MouseExited(evt);
            }
        });
        btnExcel1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcel1ActionPerformed(evt);
            }
        });

        btnPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonFPT.png"))); // NOI18N
        btnPDF.setBorder(null);
        btnPDF.setBorderPainted(false);
        btnPDF.setContentAreaFilled(false);
        btnPDF.setDefaultCapable(false);
        btnPDF.setFocusPainted(false);
        btnPDF.setFocusable(false);
        btnPDF.setIconTextGap(0);
        btnPDF.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPDFMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPDFMouseExited(evt);
            }
        });
        btnPDF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPDFActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnExcel1)
                .addGap(30, 30, 30)
                .addComponent(btnPDF)
                .addGap(30, 30, 30)
                .addComponent(btnXoa))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnXoa)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnExcel1)
                        .addComponent(btnPDF)))
                .addGap(11, 11, 11))
        );

        tabs.addTab("Danh Sách Khách Hàng Đi Tour", jPanel1);

        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonThem.png"))); // NOI18N
        btnThem.setBorder(null);
        btnThem.setBorderPainted(false);
        btnThem.setContentAreaFilled(false);
        btnThem.setDefaultCapable(false);
        btnThem.setFocusPainted(false);
        btnThem.setFocusable(false);
        btnThem.setIconTextGap(0);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThemMouseExited(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        tblKH.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        tblKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane1.setViewportView(tblKH);

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm Khách Hàng", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N

        txtFindKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtFindKH.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindKHCaretUpdate(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtFindKH)
                .addGap(9, 9, 9))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(txtFindKH, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 6, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1135, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnThem))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 413, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnThem)
                .addGap(26, 26, 26))
        );

        tabs.addTab("Danh Sách Khách Hàng", jPanel2);

        QLNV.add(tabs, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 1140, 610));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Mã Lịch Trình - Tên Lịch Trình - Tên Trưởng Đoàn", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 18), new java.awt.Color(190, 79, 60))); // NOI18N

        cboMaLT.setBorder(null);
        cboMaLT.setForeground(new java.awt.Color(190, 79, 60));
        cboMaLT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboMaLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboMaLTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(cboMaLT, javax.swing.GroupLayout.DEFAULT_SIZE, 1118, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(cboMaLT, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        QLNV.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 1140, 70));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 1403, Short.MAX_VALUE)
        );
        pnlCardNhanVienLayout.setVerticalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1517, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 114, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 832, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        new DungChung.DungChung().hoverButton(2, btnThem, "bgButtonThem.png");
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThem, "bgButtonThemHover.png");
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "buttonXoaMoi.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "buttonXoaMoi_Hover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void txtFindKHCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindKHCaretUpdate
        fillTableKhachHang();
    }//GEN-LAST:event_txtFindKHCaretUpdate

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        ThemKhachHang();
    }//GEN-LAST:event_btnThemActionPerformed

    private void cboMaLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboMaLTActionPerformed
        fillTableLichTrinh();
        checkLichTrinhDaDi();
        tabs.setSelectedIndex(0);
    }//GEN-LAST:event_cboMaLTActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        XoaKhachHang();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnExcel1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcel1MouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel1, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcel1MouseEntered

    private void btnExcel1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcel1MouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel1, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcel1MouseExited

    private void btnExcel1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcel1ActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcel1ActionPerformed

    private void btnPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPDF, "bgButtonFPTHover.png");
    }//GEN-LAST:event_btnPDFMouseEntered

    private void btnPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseExited
        new DungChung.DungChung().hoverButton(2, btnPDF, "bgButtonFPT.png");
    }//GEN-LAST:event_btnPDFMouseExited

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

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
            java.util.logging.Logger.getLogger(Frame_LT_KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_LT_KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_LT_KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_LT_KhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_LT_KhachHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnExcel1;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private CboAndTxtSugestion.ComboBoxSuggestion cboMaLT;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiEmail1;
    private javax.swing.JLabel lblLoiGiaTour2;
    private javax.swing.JPanel pnlCardNhanVien;
    private tabbed.MaterialTabbed tabs;
    private DungChung.Table tblKH;
    private DungChung.Table tblLT;
    private javax.swing.JTextField txtFindKH;
    // End of variables declaration//GEN-END:variables
}
