package UI;

import DAO.HoaDonDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import DungChung.XDate;
import SQL.JDBCHelper;
import TravelEntity.HoaDon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author khang
 */
public class Frame_TTHoaDon extends javax.swing.JFrame {

    HoaDonDAO hdDao = new HoaDonDAO();
    static int row = -1;

    public Frame_TTHoaDon() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        txtMaHD.setText(TruyVanSQL.MaHoaDon());
        fillTable();
        fillCboMaHopDong();
        updateStatus();
        AutoCompleteDecorator.decorate(cboMaHD);
        txtMaHD.setEditable(false);
        tblHoaDon.setDefaultEditor(Object.class, null);
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        btnMoi.setEnabled(true);
    }

    public void fillCboMaHopDong() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboMaHD.getModel();
        model.removeAllElements();
        try {
            String sql = "SELECT CONCAT(MaHopDong, ' - ', tr.TenTour, ' - ', hdg.MaLT) FROM HOPDONG hdg\n"
                    + "INNER JOIN dbo.LICHTRINH lt ON lt.MaLT = hdg.MaLT\n"
                    + "INNER JOIN dbo.TOUR tr ON tr.MaTour = lt.MaTour\n"
                    + "WHERE CONCAT(MaHopDong, ' - ', tr.TenTour, ' - ', hdg.MaLT) NOT IN (SELECT CONCAT(hdg.MaHopDong, ' - ', tr.TenTour, ' - ', hdg.MaLT) \n"
                    + "FROM HOADON hdn\n"
                    + "INNER JOIN dbo.HOPDONG hdg ON hdg.MaHopDong = hdn.MaHopDong\n"
                    + "INNER  JOIN dbo.LICHTRINH lt ON lt.MaLT = hdg.MaLT \n"
                    + "INNER JOIN dbo.TOUR tr ON tr.MaTour = lt.MaTour)";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                model.addElement(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String selectTrangThaiCoc(String mahdong) {
        String tienCoc = "";
        try {
            String sql = "SELECT TrangThaiThanhToan FROM dbo.HOPDONG WHERE MaHopDong = ?";
            ResultSet rs = JDBCHelper.query(sql, mahdong);
            while (rs.next()) {
                tienCoc = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tienCoc;
    }

    public void insert() {
        HoaDon hd = getForm();
        try {
            hdDao.insert(hd);
            fillTable();
            txtMaHD.setText(TruyVanSQL.MaHoaDon());
            clearForm();
            AddLichSuHD.addLSHD("Thêm Hoá Đơn");
            MsgBox.alertSuccess(this, "Thêm Hoá Đơn Thành Công");
            btnCapNhat.setEnabled(false);
            btnXoa.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        HoaDon hd = getForm();
        String trangThaiPrint = tblHoaDon.getValueAt(row, 7).toString();
        try {
            if (trangThaiPrint.equals("Đã in")) {
                MsgBox.alertInfo(this, "Không Thể Cập Nhật Hoá Đơn Đã Được In");
                return;
            }

            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Muốn Cập Nhật Hoá Đơn " + hd.getMaHoaDon() + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    hdDao.update(hd);
                    fillTable();
                    clearForm();
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTKhachSan(), "Cập Nhật Hoá Đơn Thành Công");
                    AddLichSuHD.addLSHD("Cập nhật hoá đơn");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String hoaDon = tblHoaDon.getValueAt(row, 1).toString();
        String trangThaiPrint = tblHoaDon.getValueAt(row, 7).toString();

        try {
            if (trangThaiPrint.equals("Đã in")) {
                MsgBox.alertInfo(this, "Không Thể Xoá Hoá Đơn Đã Được In");
                return;
            } else if (Auth.user.getChucVu().equals("Nhân Viên")) {
                MsgBox.alertInfo(this, "Nhân Viên Không Đủ Thẩm Quyền Xoá");
                return;
            }

            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Hoá Đơn ' " + hoaDon + " ' Không ?");
            obj.eventOK((ActionEvent ae) -> {
                hdDao.delete(hoaDon);
                AddLichSuHD.addLSHD("Xoá Phương Tiện");
                txtMaHD.setText(TruyVanSQL.MaHoaDon());
                fillTable();
                clearForm();
                btnThem.setEnabled(true);
                btnCapNhat.setEnabled(false);
                btnXoa.setEnabled(false);
                GlassPanePopup.closePopupLast();
                MsgBox.alertSuccess(new Frame_TTPhuongTien(), "Xoá Hoá Đơn ' " + hoaDon + " Thành Công !");
                AddLichSuHD.addLSHD("Xoá hoá đơn");
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void TTKhachHang(
            JLabel lblMaHDon,
            JLabel lblTenDV,
            JLabel lblNgayCoc,
            JLabel lblDaCoc,
            JLabel lblGiaTien,
            JLabel lblConlai,
            JLabel lblNguoimua,
            JLabel lblCCCD,
            JLabel lblTenKH,
            JLabel lblSDT,
            JLabel lblQueQuan,
            JLabel lblLoaiThanhToan,
            JLabel lblTongTien,
            JLabel lblGioLap,
            JLabel lblAn
    ) {
        String MaHDon = tblHoaDon.getValueAt(row, 1).toString();
        try {
            String sql = "SELECT hdon.MaHoaDon, t.TenTour, hdong.NgayLapHopDong, hdong.TienCoc, lt.SoKhach,\n"
                    + "t.Gia, kh.TenKH , nv.HoTen, kh.GTTT , kh.SDT, kh.QueQuan, hdong.LoaiThanhToan, hdon.GioLapHoaDon\n"
                    + "FROM dbo.LICHTRINH lt\n"
                    + "JOIN dbo.NHANVIEN nv ON nv.MaNV = lt.MaNV\n"
                    + "JOIN dbo.TOUR t ON t.MaTour = lt.MaTour\n"
                    + "JOIN dbo.KHACHHANG kh ON kh.MaKH = lt.MaKH\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE hdon.MaHoaDon = ?";
            ResultSet rs = JDBCHelper.query(sql, MaHDon);
            while (rs.next()) {
                int sokhach = rs.getInt("SoKhach");
                Double tienCoc = rs.getDouble("TienCoc");
                Double giaTour = rs.getDouble("Gia") * sokhach;

                lblMaHDon.setText(rs.getString("MaHoaDon"));
                lblTenDV.setText(rs.getString("TenTour"));
                lblNgayCoc.setText(XDate.toString(rs.getDate("NgayLapHopDong"), "dd-MM-yyyy"));
                lblDaCoc.setText(new DungChung.DungChung().convertTien(String.valueOf(tienCoc)));
                lblGiaTien.setText(new DungChung.DungChung().convertTien(String.valueOf(giaTour)));
                lblNguoimua.setText(rs.getString("TenKH"));
                lblCCCD.setText(rs.getString("GTTT"));
                lblTenKH.setText(rs.getString("TenKH"));
                lblSDT.setText(rs.getString("SDT"));
                lblQueQuan.setText(rs.getString("QueQuan"));
                lblLoaiThanhToan.setText(rs.getString("LoaiThanhToan"));
                lblGioLap.setText(rs.getString("GioLapHoaDon"));

                long ConLai = (long) (giaTour - tienCoc);
                lblConlai.setText(new DungChung.DungChung().convertTien(String.valueOf(ConLai)));
                lblTongTien.setText(new DungChung.DungChung().convertTien(String.valueOf(ConLai)));
                lblAn.setText(String.valueOf(ConLai));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void editColumnWidth() {
        int[] col = new int[]{50, 100, 270, 130, 130, 130, 120, 100, 120};
        new DungChung.DungChung().editColumnWidth(col, tblHoaDon);
    }

    public static void fillTable() {
        try {
            String[] header = new String[]{"STT", "Mã Hoá Đơn", "TT Hợp Đồng", "Giờ Lập Hoá Đơn", "Ngày Lập Hoá Đơn", "Trạng Thái", "Ghi Chú", "Trạng Thái In", "Người Tạo"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY MaHoaDon),\n"
                    + "MaHoaDon, CONCAT(hd.MaHopDong, ' - ', t.TenTour, ' - ', lt.MaLT), GioLapHoaDon,\n"
                    + "NgayLapHoaDon, hd.TrangThai, hd.GhiChu, hd.TrangThaiHDon, NguoiTao \n"
                    + "FROM dbo.HOADON hd\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaHopDong = hd.MaHopDong\n"
                    + "JOIN dbo.LICHTRINH lt ON lt.MaLT = hdong.MaLT\n"
                    + "JOIN dbo.TOUR t ON t.MaTour = lt.MaTour\n"
                    + "WHERE hd.MaHoaDon LIKE ?\n"
                    + "OR CONCAT(hd.MaHopDong, ' - ', t.TenTour, ' - ', lt.MaLT) LIKE ?\n"
                    + "OR hd.NgayLapHoaDon LIKE ?\n"
                    + "OR hd.TrangThaiHDon LIKE ?";
            ResultSet rs = JDBCHelper.query(sql,
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%"
            );
            while (rs.next()) {
                Vector data = new Vector();
                data.add(rs.getString(1));
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(rs.getString(4));
                data.add(XDate.toString(rs.getDate(5), "dd-MM-yyyy"));
                data.add(rs.getString(6));
                data.add(rs.getString(7));
                data.add(rs.getString(8));
                data.add(rs.getString(9));
                model.addRow(data);
            }
            tblHoaDon.setModel(model);
            editColumnWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void breakGetForm() {
        if (cboMaHD.getSelectedItem() == null) {
            MsgBox.alertWarning(this, "Vui Lòng Chọn Hoá Đơn Để Xuất Hợp Đồng");
            return;
        }
    }

    public HoaDon getForm() {
        breakGetForm();
        String subString = (String) cboMaHD.getSelectedItem();
        String mahdong = subString.substring(0, subString.indexOf("-")).trim();

        HoaDon hd = new HoaDon();
        hd.setMaHoaDon(txtMaHD.getText());
        hd.setMaHopDong(mahdong);
        hd.setTrangThai("Đã Thanh Toán 100%");
        if (txtGhiChu.getText().equals("")) {
            hd.setGhiChu("Không");
        } else {
            hd.setGhiChu(txtGhiChu.getText());
        }
        hd.setTrangThaiHDon("Chưa in");
        hd.setNguoiTao(MainForm.lblTitle.getText());
        return hd;
    }

    public void fillForm() {
        txtMaHD.setText((String) tblHoaDon.getValueAt(this.row, 1));
        cboMaHD.setSelectedItem(tblHoaDon.getValueAt(this.row, 2));
        txtGhiChu.setText((String) tblHoaDon.getValueAt(this.row, 6));
    }

    public void clearForm() {
        txtGhiChu.setText("");
        txtMaHD.setText(TruyVanSQL.MaHoaDon());
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        tblHoaDon.getSelectionModel().clearSelection();
        fillCboMaHopDong();
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);

        txtMaHD.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public void Record() {
        lblSTT.setText(String.valueOf(tblHoaDon.getValueAt(this.row, 0)));
    }

    public void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                table.scrollRectToVisible(table.getCellRect(rowIndex, vColIndex, false));
            }
        });
    }

    public void first() {
        this.row = 0;
        tblHoaDon.setRowSelectionInterval(this.row, this.row);
        scrollToVisible(tblHoaDon, this.row, this.row);
        Record();
        fillForm();
    }

    public void prev() {
        if (this.row > 0) {
            this.row = this.row - 1;
            tblHoaDon.setRowSelectionInterval(this.row, this.row);
            scrollToVisible(tblHoaDon, this.row, this.row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
        fillForm();
    }

    public void next() {
        if (this.row < tblHoaDon.getRowCount() - 1) {
            this.row++;
            tblHoaDon.setRowSelectionInterval(this.row, this.row);
            scrollToVisible(tblHoaDon, this.row, this.row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
        fillForm();
    }

    public void last() {
        this.row = tblHoaDon.getRowCount() - 1;
        tblHoaDon.setRowSelectionInterval(this.row, this.row);
        scrollToVisible(tblHoaDon, this.row, this.row);
        Record();
        fillForm();
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH THÔNG TIN HOÁ ĐƠN", "Nhân viên", tblHoaDon, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG TOUR DU LỊCH", tblHoaDon);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser = new com.raven.datechooser.DateChooser();
        choseGioLapHD = new com.raven.swing.TimePicker();
        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHoaDon = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtMaHD = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnFrist = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        txtFind = new swing.TextFieldAnimation();
        cboMaHD = new CboAndTxtSugestion.ComboBoxSuggestion();
        lblSTT = new javax.swing.JLabel();

        choseGioLapHD.setForeground(new java.awt.Color(190, 79, 60));

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblHoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Mã Hợp Đồng", "Người Tạo", "Trạng Thái", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHoaDon.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHoaDonMouseClicked(evt);
            }
        });
        tblHoaDon.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblHoaDonKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHoaDon);
        if (tblHoaDon.getColumnModel().getColumnCount() > 0) {
            tblHoaDon.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblHoaDon.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblHoaDon.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 1145, 460));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ HÓA ĐƠN");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Hóa Đơn");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 90, -1, -1));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 110, 370, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Mã Hợp Đồng");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 90, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Ghi Chú");
        QLNV.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(840, 90, -1, -1));

        txtMaHD.setBackground(new java.awt.Color(242, 242, 242));
        txtMaHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaHD.setForeground(new java.awt.Color(190, 79, 60));
        txtMaHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 110, 300, 30));

        btnMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonMoi.png"))); // NOI18N
        btnMoi.setBorder(null);
        btnMoi.setBorderPainted(false);
        btnMoi.setContentAreaFilled(false);
        btnMoi.setDefaultCapable(false);
        btnMoi.setFocusPainted(false);
        btnMoi.setFocusable(false);
        btnMoi.setIconTextGap(0);
        btnMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMoiMouseExited(evt);
            }
        });
        btnMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMoiActionPerformed(evt);
            }
        });
        QLNV.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 170, -1, -1));

        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonXoa.png"))); // NOI18N
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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 170, -1, -1));

        btnCapNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnCapNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonCapNhat.png"))); // NOI18N
        btnCapNhat.setBorder(null);
        btnCapNhat.setBorderPainted(false);
        btnCapNhat.setContentAreaFilled(false);
        btnCapNhat.setDefaultCapable(false);
        btnCapNhat.setFocusPainted(false);
        btnCapNhat.setFocusable(false);
        btnCapNhat.setIconTextGap(0);
        btnCapNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnCapNhatMouseExited(evt);
            }
        });
        btnCapNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCapNhatActionPerformed(evt);
            }
        });
        QLNV.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 170, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 170, -1, -1));

        btnFrist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgDau.png"))); // NOI18N
        btnFrist.setBorder(null);
        btnFrist.setBorderPainted(false);
        btnFrist.setContentAreaFilled(false);
        btnFrist.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFrist.setDefaultCapable(false);
        btnFrist.setFocusPainted(false);
        btnFrist.setFocusable(false);
        btnFrist.setIconTextGap(0);
        btnFrist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFristMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFristMouseExited(evt);
            }
        });
        btnFrist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFristActionPerformed(evt);
            }
        });
        QLNV.add(btnFrist, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 720, -1, -1));

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgPre.png"))); // NOI18N
        btnPrev.setBorder(null);
        btnPrev.setBorderPainted(false);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.setDefaultCapable(false);
        btnPrev.setFocusPainted(false);
        btnPrev.setFocusable(false);
        btnPrev.setIconTextGap(0);
        btnPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrevMouseExited(evt);
            }
        });
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        QLNV.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 720, -1, -1));

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgNext.png"))); // NOI18N
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.setDefaultCapable(false);
        btnNext.setFocusPainted(false);
        btnNext.setFocusable(false);
        btnNext.setIconTextGap(0);
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNextMouseExited(evt);
            }
        });
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        QLNV.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 720, -1, -1));

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgCuoi.png"))); // NOI18N
        btnLast.setBorder(null);
        btnLast.setBorderPainted(false);
        btnLast.setContentAreaFilled(false);
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.setDefaultCapable(false);
        btnLast.setFocusPainted(false);
        btnLast.setFocusable(false);
        btnLast.setIconTextGap(0);
        btnLast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLastMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLastMouseExited(evt);
            }
        });
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        QLNV.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 720, -1, -1));

        btnExcel.setForeground(new java.awt.Color(255, 255, 255));
        btnExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonExcel.png"))); // NOI18N
        btnExcel.setBorder(null);
        btnExcel.setBorderPainted(false);
        btnExcel.setContentAreaFilled(false);
        btnExcel.setDefaultCapable(false);
        btnExcel.setFocusPainted(false);
        btnExcel.setFocusable(false);
        btnExcel.setIconTextGap(0);
        btnExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnExcelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnExcelMouseExited(evt);
            }
        });
        btnExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcelActionPerformed(evt);
            }
        });
        QLNV.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 710, -1, -1));

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
        QLNV.add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 710, -1, -1));

        txtFind.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindCaretUpdate(evt);
            }
        });
        QLNV.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        cboMaHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        QLNV.add(cboMaHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 350, 30));

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        QLNV.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 720, 30, -1));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 1403, Short.MAX_VALUE)
        );
        pnlCardNhanVienLayout.setVerticalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1517, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 832, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThem, "bgButtonThemHover.png");
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        new DungChung.DungChung().hoverButton(2, btnThem, "bgButtonThem.png");
    }//GEN-LAST:event_btnThemMouseExited

    private void btnCapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseEntered
        new DungChung.DungChung().hoverButton(1, btnCapNhat, "bgButtonCapNhatHover.png");
    }//GEN-LAST:event_btnCapNhatMouseEntered

    private void btnCapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseExited
        new DungChung.DungChung().hoverButton(2, btnCapNhat, "bgButtonCapNhat.png");
    }//GEN-LAST:event_btnCapNhatMouseExited

    private void btnMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseEntered
        new DungChung.DungChung().hoverButton(1, btnMoi, "bgButtonMoiHover.png");
    }//GEN-LAST:event_btnMoiMouseEntered

    private void btnMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseExited
        new DungChung.DungChung().hoverButton(2, btnMoi, "bgButtonMoi.png");
    }//GEN-LAST:event_btnMoiMouseExited

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "bgButtonXoaHover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "bgButtonXoa.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        insert();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnFristMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseEntered
        new DungChung.DungChung().hoverButton(1, btnFrist, "bgDauHover.png");
    }//GEN-LAST:event_btnFristMouseEntered

    private void btnFristMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseExited
        new DungChung.DungChung().hoverButton(2, btnFrist, "bgDau.png");
    }//GEN-LAST:event_btnFristMouseExited

    private void btnPrevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPrev, "bgPreHover.png");
    }//GEN-LAST:event_btnPrevMouseEntered

    private void btnPrevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseExited
        new DungChung.DungChung().hoverButton(2, btnPrev, "bgPre.png");
    }//GEN-LAST:event_btnPrevMouseExited

    private void btnNextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseEntered
        new DungChung.DungChung().hoverButton(1, btnNext, "bgNextHover.png");
    }//GEN-LAST:event_btnNextMouseEntered

    private void btnNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseExited
        new DungChung.DungChung().hoverButton(2, btnNext, "bgNext.png");
    }//GEN-LAST:event_btnNextMouseExited

    private void btnLastMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLast, "bgCuoiHover.png");
    }//GEN-LAST:event_btnLastMouseEntered

    private void btnLastMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseExited
        new DungChung.DungChung().hoverButton(2, btnLast, "bgCuoi.png");
    }//GEN-LAST:event_btnLastMouseExited

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPDF, "bgButtonFPTHover.png");
    }//GEN-LAST:event_btnPDFMouseEntered

    private void btnPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseExited
        new DungChung.DungChung().hoverButton(2, btnPDF, "bgButtonFPT.png");
    }//GEN-LAST:event_btnPDFMouseExited

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        fillTable();
        this.row = -1;
    }//GEN-LAST:event_txtFindCaretUpdate

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblHoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHoaDonMouseClicked
        if (evt.getClickCount() == 1) {
            this.row = tblHoaDon.getSelectedRow();
            fillForm();
            updateStatus();
        }
        if (evt.getClickCount() == 2) {
            AddLichSuHD.addLSHD("Xem thông tin hoá đơn");
            new Dialog_PrintHoaDon(this, true).setVisible(true);
        }
        Record();
    }//GEN-LAST:event_tblHoaDonMouseClicked

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        update();
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFristActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFristActionPerformed
        first();
    }//GEN-LAST:event_btnFristActionPerformed

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void tblHoaDonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblHoaDonKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            this.row = tblHoaDon.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblHoaDonKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTHoaDon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTHoaDon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTHoaDon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTHoaDon.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTHoaDon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFrist;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private CboAndTxtSugestion.ComboBoxSuggestion cboMaHD;
    private com.raven.swing.TimePicker choseGioLapHD;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JPanel pnlCardNhanVien;
    public static DungChung.Table tblHoaDon;
    public static swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtMaHD;
    // End of variables declaration//GEN-END:variables
}
