package UI;

import DAO.LT_PhuongTienDAO;
import DAO.LoaiPhuongTienDAO;
import DAO.PhuongTienDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import SQL.JDBCHelper;
import TravelEntity.LT_PhuongTien;
import TravelEntity.LoaiPhuongTien;
import TravelEntity.PhuongTien;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author khang
 */
public class Frame_TTPhuongTien extends javax.swing.JFrame {

    PhuongTienDAO ptDao = new PhuongTienDAO();
    LoaiPhuongTienDAO lptDao = new LoaiPhuongTienDAO();
    LT_PhuongTienDAO ltptDAO = new LT_PhuongTienDAO();
    int row = -1;

    public Frame_TTPhuongTien() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        HideError();
        updateStatus();
        setGhiChu();
        fillTable();
        editColumnWidth();
        fillCboxTenPhuongTien();
        setText();
        tblPT.setDefaultEditor(Object.class, null);
    }

    private void editColumnWidth() {
        int[] col = new int[]{100, 100, 250, 100, 100, 100, 150, 150, 150};
        new DungChung.DungChung().editColumnWidth(col, tblPT);
    }

    public void setText() {
        String[] arr = new String[]{
            "An Giang", "Bắc Kạn", "Bến Tre", "Bình Phước", "Cao Bằng", "Đắk Lắk", "Đồng Nai", "Hà Giang", "Hà Tây", "Hải Phòng",
            "Hậu Giang", "Kiên Giang", "Lào Cai", "Long An", "Ninh Bình", "Phú Yên", "Quảng Ngãi", "Sóc Trăng", "Thái Bình", "Huế",
            "Tuyên Quang", "Yên Bái", "Vũng Tàu", "Bắc Giang", "Bình Dương", "Bình Thuận", "Cần Thơ", "Đắk Nông", "Đồng Tháp", "Hà Nam",
            "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hòa Bình", "Hưng Yên", "Lai Châu", "Lạng Sơn", "Nam Định", "Quảng Bình", "Quảng Ninh",
            "Sơn La", "Tây Ninh", "Thái Nguyên", "Tiền Giang", "Vĩnh Long", "Quy Nhơn", "Bạc Liêu", "Bắc Ninh", "Bình Định", "Cà Mau",
            "Đà Nẵng", "Điện Biên", "Gia Lai", "Hồ Chí Minh", "Khánh Hòa", "Lâm Đồng", "Nghệ An", "Phú Thọ", "Quảng Nam", "Quảng Trị",
            "Thanh Hóa", "Trà Vinh", "Vĩnh Phúc"
        };

        for (int i = 0; i < arr.length; i++) {
            txtDiaChi.addItemSuggestion(arr[i]);
        }
        AutoCompleteDecorator.decorate(cboTenPT);
//        txtNguoiTao.setText(LoginForm.txtUser.getText());
        txtNguoiTao.setEditable(false);
        txtMaPT.setEditable(false);
        txtMaPT.setText(TruyVanSQL.MaPT());
    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiDiachi,
            lblLoiGiaDV,
            lblLoiSDT,
            lblLoiBienSo
        });
    }

    public void fillCboxTenPhuongTien() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenPT.getModel();
        model.removeAllElements();
        List<LoaiPhuongTien> pt = lptDao.selectAll();
        for (LoaiPhuongTien PhuongTien : pt) {
            model.addElement(PhuongTien.getTenLoaiPT() + " - " + PhuongTien.getMaLoaiPT());
        }
    }

    private boolean check() {
        boolean flag = true;

        String bienso = txtBienSo.getText().trim();
        if (bienso.length() < 7) {
            lblLoiBienSo.setVisible(true);
            return false;
        }
        if (txtGiaDV.getText().trim().isEmpty()) {
            lblLoiGiaDV.setVisible(flag);
            return false;
        }

        try {
            Double a = Double.parseDouble(txtGiaDV.getText());
            if (a < 0) {
                lblLoiGiaDV.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            lblLoiGiaDV.setVisible(true);
            return false;
        }
        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("^\\d{10}$")) {
            lblLoiSDT.setVisible(true);
            return false;
        }
        if (txtDiaChi.getText().trim().isEmpty()) {
            lblLoiDiachi.setVisible(flag);
            return false;
        }

        return flag;
    }

    public void insert() {
        PhuongTien pt = getForm();
        try {
            ptDao.insert(pt);
            fillTable();
            editColumnWidth();
            clearForm();
            txtMaPT.setText(TruyVanSQL.MaPT());
            AddLichSuHD.addLSHD("Thêm Phương Tiện");
            MsgBox.alertSuccess(this, "Thêm Mới Thành Công !");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertWarning(this, "Thêm Mới Thất Bại !");
        }
    }

    public void update() {
        PhuongTien pt = getForm();
        String phuongTien = txtMaPT.getText();

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Mã Phương Tiện ' " + phuongTien + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ptDao.update(pt);
                    fillTable();
                    editColumnWidth();
                    clearForm();
                    setGhiChu();
                    txtMaPT.setText(TruyVanSQL.MaPT());
                    AddLichSuHD.addLSHD("Cập Nhật Phương Tiện");
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTPhuongTien(), "Cập Nhật Thành Công !");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Cập Nhật Thất Bại");
        }
    }

    public void delete() {
        int a = tblPT.getSelectedRow();
        String phuongTien = tblPT.getValueAt(a, 1).toString();

        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }

        List<LT_PhuongTien> ltpt = ltptDAO.selectByIdPhuongtien(phuongTien);
        for (LT_PhuongTien lT_PhuongTien : ltpt) {
            if (phuongTien.equals(lT_PhuongTien.getMaPT())) {
                MsgBox.alertWarning(this, "Phương Tiện Đang Được Sử Dụng !");
                return;
            }
        }

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Phương Tiện ' " + phuongTien + " ' Không ?");
            obj.eventOK((ActionEvent ae) -> {
                ptDao.delete(phuongTien);
                AddLichSuHD.addLSHD("Xoá Phương Tiện");
                txtMaPT.setText(TruyVanSQL.MaPT());
                fillTable();
                editColumnWidth();
                clearForm();
                btnThem.setEnabled(true);
                btnCapNhat.setEnabled(false);
                btnXoa.setEnabled(false);
                GlassPanePopup.closePopupLast();
                MsgBox.alertSuccess(new Frame_TTPhuongTien(), "Xoá Phương Tiện ' " + phuongTien + " Thành Công !");
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Xoá Thất Bại");
        }
    }

    public void fillTable() {
        try {
            String[] header = new String[]{"STT", "Mã PT", "TT Phương Tiện", "Giá", "Biển Số", "Số Chổ Ngồi", "SĐT", "Địa Chỉ", "Ghi Chú"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY pt.MaPT), \n"
                    + "pt.MaPT,\n"
                    + "CONCAT(lpt.TenLoaiPT, ' - ', lpt.MaLoaiPT) AS TTPT,\n"
                    + "pt.Gia,\n"
                    + "pt.BienSo,\n"
                    + "pt.SoChoNgoi,\n"
                    + "pt.SDT,\n"
                    + "pt.DiaChi,\n"
                    + "pt.GhiChu \n"
                    + "FROM dbo.PHUONGTIEN pt\n"
                    + "INNER JOIN  dbo.LOAIPHUONGTIEN lpt\n"
                    + "ON lpt.MaLoaiPT = pt.MaLoaiPT\n"
                    + "WHERE pt.MaPT LIKE ?\n"
                    + "OR CONCAT(lpt.TenLoaiPT, ' - ', lpt.MaLoaiPT) LIKE ?\n"
                    + "OR pt.BienSo LIKE ?\n"
                    + "OR pt.SDT LIKE ?";
            ResultSet rs = JDBCHelper.query(
                    sql,
                    "%" + txtFind1.getText() + "%",
                    "%" + txtFind1.getText() + "%",
                    "%" + txtFind1.getText() + "%",
                    "%" + txtFind1.getText() + "%"
            );
            while (rs.next()) {
                Vector data = new Vector();
                data.add(rs.getString(1));
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(new DungChung.DungChung().convertTien(String.valueOf(rs.getDouble(4))) + " VNĐ");
                data.add(rs.getString(5));
                data.add(rs.getString(6));
                data.add(rs.getString(7));
                data.add(rs.getString(8));
                data.add(rs.getString(9));
                model.addRow(data);
            }
            tblPT.setModel(model);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void timKiem() {
        fillTable();
        editColumnWidth();
        row = -1;
    }

    PhuongTien getForm() {
        String cbo = (String) cboTenPT.getSelectedItem();
        String subTring = cbo.substring(0, cbo.indexOf("-")).trim();

        PhuongTien pt = new PhuongTien();
        pt.setMaPT(txtMaPT.getText());
        pt.setMaLoaiPT(TruyVanSQL.selectMaLoaiPT(subTring));
        pt.setSDT(txtSDT.getText());
        pt.setGia(Double.parseDouble(txtGiaDV.getText()));
        pt.setDiaChi(txtDiaChi.getText());
        pt.setBienSo(txtBienSo.getText());
        pt.setGhiChu(txtGhiChu.getText());
        pt.setSoChoNgoi((String) cboSoChoNgoi.getSelectedItem());
        return pt;
    }

    public void fillForm(PhuongTien pt) {
        int a = tblPT.getSelectedRow();
        txtMaPT.setText(pt.getMaPT());
        cboTenPT.setSelectedItem(tblPT.getValueAt(a, 2));
        txtSDT.setText(pt.getSDT());
        txtGiaDV.setText(String.valueOf(pt.getGia()));
        txtDiaChi.setText(pt.getDiaChi());
        txtBienSo.setText(pt.getBienSo());
        txtGhiChu.setText(pt.getGhiChu());
        cboSoChoNgoi.setSelectedItem(pt.getSoChoNgoi());
    }

    public void edit() {
        try {
            String maTour = (String) tblPT.getValueAt(row, 1);
            PhuongTien pt = ptDao.selectById(maTour);
            if (pt != null) {
                fillForm(pt);
                updateStatus();
                cboTenPT.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);

        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public void Record() {
        lblSTT.setText(String.valueOf(tblPT.getValueAt(row, 0)));
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtGiaDV,
            txtSDT,
            txtGhiChu,
            txtDiaChi,
            txtBienSo
        };
        txtMaPT.setText(TruyVanSQL.MaPT());
        tblPT.getSelectionModel().clearSelection();
        new DungChung.DungChung().reset(txt);
        HideError();
        setGhiChu();
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        cboTenPT.setEnabled(true);
    }

    public void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                table.scrollRectToVisible(table.getCellRect(rowIndex, vColIndex, false));
            }
        });
    }

    public void setGhiChu() {
        if (txtGhiChu.getText().isEmpty()) {
            txtGhiChu.setText("Không");
        }
    }

    public void first() {
        row = 0;
        tblPT.setRowSelectionInterval(row, row);
        scrollToVisible(tblPT, row, row);
        Record();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblPT.setRowSelectionInterval(row, row);
            scrollToVisible(tblPT, row, row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblPT.getRowCount() - 1) {
            this.row++;
            tblPT.setRowSelectionInterval(row, row);
            scrollToVisible(tblPT, row, row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        this.row = tblPT.getRowCount() - 1;
        tblPT.setRowSelectionInterval(row, row);
        scrollToVisible(tblPT, row, row);
        Record();
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG THÔNG TIN PHƯƠNG TIỆN", "Nhân viên", tblPT, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG THÔNG TIN PHƯƠNG TIỆN", tblPT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblPT = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtMaPT = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtGiaDV = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        lblLoiGiaDV = new javax.swing.JLabel();
        lblLoiDiachi = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblLoiSDT = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtBienSo = new javax.swing.JTextField();
        lblLoiBienSo = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnFrist = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jLabel19 = new javax.swing.JLabel();
        cboTenPT = new CboAndTxtSugestion.ComboBoxSuggestion();
        jLabel24 = new javax.swing.JLabel();
        cboSoChoNgoi = new CboAndTxtSugestion.ComboBoxSuggestion();
        txtFind1 = new swing.TextFieldAnimation();
        txtDiaChi = new ComboBox.TextFieldSuggestion();
        lblSTT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setPreferredSize(new java.awt.Dimension(1240, 702));
        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblPT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Nhà Xe", "Tên Nhà Xe", "Loại Xe", "Số Điện Thoại", "Giá Thuê", "Địa Chỉ", "Biển Số", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblPT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblPTMouseClicked(evt);
            }
        });
        tblPT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblPTKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblPT);
        if (tblPT.getColumnModel().getColumnCount() > 0) {
            tblPT.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblPT.getColumnModel().getColumn(1).setPreferredWidth(5);
            tblPT.getColumnModel().getColumn(3).setPreferredWidth(100);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, 1145, 280));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ DỊCH VỤ PHƯƠNG TIỆN");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Phương Tiện");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setToolTipText("Mã Xe - SĐT Tài Xế");
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 270, 350, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(190, 79, 60));
        jLabel20.setText("Địa Chỉ");
        QLNV.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 170, 60, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Ghi Chú");
        QLNV.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 250, 70, -1));

        txtMaPT.setBackground(new java.awt.Color(242, 242, 242));
        txtMaPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaPT.setForeground(new java.awt.Color(190, 79, 60));
        txtMaPT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtMaPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 320, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Giá Dịch Vụ (VNĐ) / Ngày");
        QLNV.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 90, -1, -1));

        txtGiaDV.setBackground(new java.awt.Color(242, 242, 242));
        txtGiaDV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaDV.setForeground(new java.awt.Color(190, 79, 60));
        txtGiaDV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtGiaDV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiaDVFocusLost(evt);
            }
        });
        QLNV.add(txtGiaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 110, 350, 30));

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
        QLNV.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 340, -1, -1));

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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 340, -1, -1));

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
        QLNV.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 340, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 340, -1, -1));

        lblLoiGiaDV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaDV.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiGiaDV.setText("Giá Dịch Vụ Không Hợp Lệ !");
        QLNV.add(lblLoiGiaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 140, 180, 20));

        lblLoiDiachi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiDiachi.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiDiachi.setText("Địa Chỉ Không Hợp Lệ !");
        QLNV.add(lblLoiDiachi, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 220, 180, 20));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(190, 79, 60));
        jLabel26.setText("Người Tạo");
        QLNV.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 250, 80, -1));

        txtNguoiTao.setBackground(new java.awt.Color(242, 242, 242));
        txtNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNguoiTao.setForeground(new java.awt.Color(190, 79, 60));
        txtNguoiTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 270, 360, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(190, 79, 60));
        jLabel22.setText("Số Điện Thoại (Tài Xế / Chủ Xe)");
        QLNV.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, 20));

        txtSDT.setBackground(new java.awt.Color(242, 242, 242));
        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(190, 79, 60));
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSDTFocusLost(evt);
            }
        });
        QLNV.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 270, 320, 30));

        lblLoiSDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiSDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiSDT.setText("Số Điện Thoại Không Hợp Lệ !");
        QLNV.add(lblLoiSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 300, 180, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(190, 79, 60));
        jLabel23.setText("Biển Số");
        QLNV.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, 20));

        txtBienSo.setBackground(new java.awt.Color(242, 242, 242));
        txtBienSo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBienSo.setForeground(new java.awt.Color(190, 79, 60));
        txtBienSo.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtBienSo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBienSoFocusLost(evt);
            }
        });
        QLNV.add(txtBienSo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 190, 320, 30));

        lblLoiBienSo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiBienSo.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiBienSo.setText("Biển Số Không Hợp Lệ !");
        QLNV.add(lblLoiBienSo, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 180, 20));

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

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(190, 79, 60));
        jLabel19.setText("Tên Loại Phương Tiện");
        QLNV.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 90, -1, 20));

        cboTenPT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        cboTenPT.setForeground(new java.awt.Color(190, 79, 60));
        cboTenPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        QLNV.add(cboTenPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 110, 360, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(190, 79, 60));
        jLabel24.setText("Số Chổ Ngồi ");
        QLNV.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, -1, 20));

        cboSoChoNgoi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        cboSoChoNgoi.setForeground(new java.awt.Color(190, 79, 60));
        cboSoChoNgoi.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "16 Chỗ", "29 Chỗ", "45 Chỗ", "52 Chỗ" }));
        cboSoChoNgoi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        QLNV.add(cboSoChoNgoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 190, 360, 30));

        txtFind1.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFind1CaretUpdate(evt);
            }
        });
        QLNV.add(txtFind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtDiaChi.setForeground(new java.awt.Color(190, 79, 60));
        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiaChi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiaChiFocusLost(evt);
            }
        });
        QLNV.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 190, 350, 30));

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

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void btnPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseExited
        new DungChung.DungChung().hoverButton(2, btnPDF, "bgButtonFPT.png");
    }//GEN-LAST:event_btnPDFMouseExited

    private void btnPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPDF, "bgButtonFPTHover.png");
    }//GEN-LAST:event_btnPDFMouseEntered

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnLastMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseExited
        new DungChung.DungChung().hoverButton(2, btnLast, "bgCuoi.png");
    }//GEN-LAST:event_btnLastMouseExited

    private void btnLastMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLast, "bgCuoiHover.png");
    }//GEN-LAST:event_btnLastMouseEntered

    private void btnFristActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFristActionPerformed
        first();
    }//GEN-LAST:event_btnFristActionPerformed

    private void btnFristMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseExited
        new DungChung.DungChung().hoverButton(2, btnFrist, "bgDau.png");
    }//GEN-LAST:event_btnFristMouseExited

    private void btnFristMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseEntered
        new DungChung.DungChung().hoverButton(1, btnFrist, "bgDauHover.png");
    }//GEN-LAST:event_btnFristMouseEntered

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnPrevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseExited
        new DungChung.DungChung().hoverButton(2, btnPrev, "bgPre.png");
    }//GEN-LAST:event_btnPrevMouseExited

    private void btnPrevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPrev, "bgPreHover.png");
    }//GEN-LAST:event_btnPrevMouseEntered

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseExited
        new DungChung.DungChung().hoverButton(2, btnNext, "bgNext.png");
    }//GEN-LAST:event_btnNextMouseExited

    private void btnNextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseEntered
        new DungChung.DungChung().hoverButton(1, btnNext, "bgNextHover.png");
    }//GEN-LAST:event_btnNextMouseEntered

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (check()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        new DungChung.DungChung().hoverButton(2, btnThem, "bgButtonThem.png");
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThem, "bgButtonThemHover.png");
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (check()) {
            update();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnCapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseExited
        new DungChung.DungChung().hoverButton(2, btnCapNhat, "bgButtonCapNhat.png");
    }//GEN-LAST:event_btnCapNhatMouseExited

    private void btnCapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseEntered
        new DungChung.DungChung().hoverButton(1, btnCapNhat, "bgButtonCapNhatHover.png");
    }//GEN-LAST:event_btnCapNhatMouseEntered

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "bgButtonXoa.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "bgButtonXoaHover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void btnMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseExited
        new DungChung.DungChung().hoverButton(2, btnMoi, "bgButtonMoi.png");
    }//GEN-LAST:event_btnMoiMouseExited

    private void btnMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseEntered
        new DungChung.DungChung().hoverButton(1, btnMoi, "bgButtonMoiHover.png");
    }//GEN-LAST:event_btnMoiMouseEntered

    private void tblPTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblPTMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblPT.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblPTMouseClicked

    private void txtFind1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFind1CaretUpdate
        timKiem();
    }//GEN-LAST:event_txtFind1CaretUpdate

    private void txtBienSoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtBienSoFocusLost
        if (!txtBienSo.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtBienSoFocusLost

    private void txtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusLost
        if (!txtSDT.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtSDTFocusLost

    private void txtGiaDVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaDVFocusLost
        if (!txtGiaDV.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtGiaDVFocusLost

    private void txtDiaChiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaChiFocusLost
        if (!txtDiaChi.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtDiaChiFocusLost

    private void tblPTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblPTKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblPT.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblPTKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTPhuongTien().setVisible(true);
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
    private CboAndTxtSugestion.ComboBoxSuggestion cboSoChoNgoi;
    private CboAndTxtSugestion.ComboBoxSuggestion cboTenPT;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiBienSo;
    private javax.swing.JLabel lblLoiDiachi;
    private javax.swing.JLabel lblLoiGiaDV;
    private javax.swing.JLabel lblLoiSDT;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblPT;
    private javax.swing.JTextField txtBienSo;
    private ComboBox.TextFieldSuggestion txtDiaChi;
    private swing.TextFieldAnimation txtFind1;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGiaDV;
    private javax.swing.JTextField txtMaPT;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables
}
