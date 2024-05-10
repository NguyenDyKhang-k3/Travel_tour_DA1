package UI;

import DAO.KhachSanDAO;
import DAO.LT_KhachSanDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import TravelEntity.KhachSan;
import TravelEntity.LT_KhachSan;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author khang
 */
public class Frame_TTKhachSan extends javax.swing.JFrame {

    KhachSanDAO dao = new KhachSanDAO();
    LT_KhachSanDAO ltksDao = new LT_KhachSanDAO();
    int row = -1;

    public Frame_TTKhachSan() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        fillTable();
        HideError();
        sxTheoCot();
        setText();
        txtMaKS.setEditable(false);
        txtMaKS.setText(TruyVanSQL.MaKS());
    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiTenKS,
            lblLoiGiaDV,
            lblLoiSDT,
            lblLoiDC
        });
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

        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtTenKS,
            txtGia,
            txtSDT,
            txtDiaChi,
            txtGhiChu
        };
        txtMaKS.setText(TruyVanSQL.MaKS());
        new DungChung.DungChung().reset(txt);
        HideError();
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
    }

    private boolean check() {
        boolean flag = true;
        String TenKS = txtTenKS.getText().trim();
        if (TenKS.length() < 5) {
            lblLoiTenKS.setVisible(true);
            return false;
        }
        if (txtGia.getText().trim().isEmpty()) {
            lblLoiGiaDV.setVisible(true);
            return false;
        }
        try {
            Double a = Double.valueOf(txtGia.getText());
            if (a < 0) {
                lblLoiGiaDV.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            lblLoiGiaDV.setVisible(true);
            txtGia.requestFocus();
            return false;
        }
        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("^\\d{10}$")) {
            lblLoiSDT.setVisible(true);
            return false;
        }
        if (txtDiaChi.getText().trim().isEmpty()) {
            lblLoiDC.setVisible(true);
            return false;
        }
        return flag;
    }

    private void sxTheoCot() {
        DefaultTableModel model = (DefaultTableModel) tblKhachSan.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblKhachSan.setRowSorter(sorter);
    }

    void fillTable() {
        DefaultTableModel model = (DefaultTableModel) tblKhachSan.getModel();
        model.setRowCount(0);
        int i = 1;
        try {
            String find = txtFind.getText();
            List<KhachSan> list = dao.selectByKeyword(find, find, find, find);

            for (KhachSan ks : list) {
                Object[] row = {
                    i++,
                    ks.getMaks(),
                    ks.getTenks(),
                    ks.getSDT(),
                    new DungChung.DungChung().convertTien(ks.getGia()) + " VNĐ",
                    ks.getDiaChi(),
                    ks.getGhiChu()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void setModel(KhachSan model) {
        txtMaKS.setText(model.getMaks().trim());
        txtTenKS.setText(model.getTenks().trim());
        txtGia.setText(model.getGia().trim());
        txtSDT.setText(model.getSDT().trim());
        txtDiaChi.setText(model.getDiaChi().trim());
        txtGhiChu.setText(model.getGhiChu().trim());

    }

    KhachSan getModel() {
        KhachSan model = new KhachSan();
        model.setMaks(txtMaKS.getText().trim());
        model.setTenks(txtTenKS.getText().trim());
        model.setGia(txtGia.getText().trim());
        model.setSDT(txtSDT.getText().trim());
        model.setDiaChi(txtDiaChi.getText().trim());
        model.setGhiChu(txtGhiChu.getText().trim());
        return model;
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);

        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public void edit() {
        try {
            String maks = (String) tblKhachSan.getValueAt(row, 1);
            KhachSan ks = dao.selectById(maks);
            if (ks != null) {
                setModel(ks);
                updateStatus();
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void insert() {
        try {
            KhachSan nv = getModel();
            dao.insert(nv);
            fillTable();
            txtMaKS.setText(TruyVanSQL.MaKS());
            clearForm();
            btnCapNhat.setEnabled(false);
            btnXoa.setEnabled(false);
            MsgBox.alertSuccess(this, "Thêm Mới Thành Công");
            AddLichSuHD.addLSHD("Thêm khách sạn");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertWarning(this, "Thêm Mới Thất Bại");
        }
    }

    public void update() {
        KhachSan kh = getModel();

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Muốn Cập Nhật Khách Sạn " + kh.getMaks() + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dao.update(kh);
                    fillTable();
                    clearForm();
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTKhachSan(), "Cập Nhật Thành Công");
                    AddLichSuHD.addLSHD("Cập nhật khách sạn");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Cập Nhật Thất Bại");
        }
    }

    public void delete() {
        String maks = txtMaKS.getText();

        try {
            if (Auth.user.getChucVu().equals("Nhân Viên")) {
                MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
                return;
            }

            List<LT_KhachSan> ltks = ltksDao.selectAll();
            for (LT_KhachSan ks : ltks) {
                if (ks.getMaKS().equals(maks)) {
                    MsgBox.alertWarning(this, "Khách Sạn Đang Được Sử Dụng !");
                    return;
                }
            }

            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Khách Sạn " + maks + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dao.delete(maks);
                    txtMaKS.setText(TruyVanSQL.MaKS());
                    fillTable();
                    clearForm();
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTKhachSan(), "Xoá Thành Công");
                    AddLichSuHD.addLSHD("Xoá khách sạn");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Xoá Thất Bại");
        }
    }

    public void timKiem() {
        fillTable();
        row = -1;
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

    public void Record() {
        lblSTT.setText(String.valueOf(tblKhachSan.getValueAt(row, 0)));
    }

    public void first() {
        row = 0;
        tblKhachSan.setRowSelectionInterval(row, row);
        scrollToVisible(tblKhachSan, row, row);
        Record();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblKhachSan.setRowSelectionInterval(row, row);
            scrollToVisible(tblKhachSan, row, row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblKhachSan.getRowCount() - 1) {
            this.row++;
            tblKhachSan.setRowSelectionInterval(row, row);
            scrollToVisible(tblKhachSan, row, row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        this.row = tblKhachSan.getRowCount() - 1;
        tblKhachSan.setRowSelectionInterval(row, row);
        scrollToVisible(tblKhachSan, row, row);
        Record();
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG THÔNG TIN KHÁCH SẠN", "Nhân viên", tblKhachSan, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG THÔNG TIN KHÁCH SẠN", tblKhachSan);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKhachSan = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtMaKS = new javax.swing.JTextField();
        txtTenKS = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        lblLoiTenKS = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        txtGia = new javax.swing.JTextField();
        lblLoiSDT = new javax.swing.JLabel();
        lblLoiGiaDV = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnFrist = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        txtFind = new swing.TextFieldAnimation();
        txtDiaChi = new ComboBox.TextFieldSuggestion();
        lblLoiDC = new javax.swing.JLabel();
        lblSTT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setPreferredSize(new java.awt.Dimension(1537, 702));
        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblKhachSan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Khách Sạn", "Tên Khách Sạn", "Số Điện Thoại", "Giá", "Địa Chỉ", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachSan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblKhachSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachSanMouseClicked(evt);
            }
        });
        tblKhachSan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblKhachSanKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblKhachSan);
        if (tblKhachSan.getColumnModel().getColumnCount() > 0) {
            tblKhachSan.getColumnModel().getColumn(0).setResizable(false);
            tblKhachSan.getColumnModel().getColumn(0).setPreferredWidth(5);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 400, 1145, 300));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ DỊCH VỤ KHÁCH SẠN - NHÀ NGHĨ - HOMESTAY");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Khách Sạn");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Tên Khách Sạn");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 110, -1, 20));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(190, 79, 60));
        jLabel20.setText("Địa Chỉ");
        QLNV.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 110, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Ghi Chú");
        QLNV.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 200, -1, -1));

        txtMaKS.setBackground(new java.awt.Color(242, 242, 242));
        txtMaKS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKS.setForeground(new java.awt.Color(190, 79, 60));
        txtMaKS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtMaKS, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 320, 30));

        txtTenKS.setBackground(new java.awt.Color(242, 242, 242));
        txtTenKS.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKS.setForeground(new java.awt.Color(190, 79, 60));
        txtTenKS.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtTenKS.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKSFocusLost(evt);
            }
        });
        QLNV.add(txtTenKS, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 130, 340, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText(" Giá (VNĐ)");
        QLNV.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 200, 80, -1));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtGhiChu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtGhiChuActionPerformed(evt);
            }
        });
        QLNV.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 220, 360, 30));

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
        QLNV.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 310, -1, -1));

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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(710, 310, -1, -1));

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
        QLNV.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 310, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 310, -1, -1));

        lblLoiTenKS.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenKS.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenKS.setText("Tên Khách Sạn Không Hợp Lệ !");
        QLNV.add(lblLoiTenKS, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 160, 180, 20));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(190, 79, 60));
        jLabel22.setText("Số Điện Thoại");
        QLNV.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 200, -1, 20));

        txtSDT.setBackground(new java.awt.Color(242, 242, 242));
        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(190, 79, 60));
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSDTFocusLost(evt);
            }
        });
        QLNV.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 220, 340, 30));

        txtGia.setBackground(new java.awt.Color(242, 242, 242));
        txtGia.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGia.setForeground(new java.awt.Color(190, 79, 60));
        txtGia.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtGia.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiaFocusLost(evt);
            }
        });
        QLNV.add(txtGia, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 320, 30));

        lblLoiSDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiSDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiSDT.setText("Số Điện Thoại Không Hợp Lệ !");
        QLNV.add(lblLoiSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 250, 180, 20));

        lblLoiGiaDV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaDV.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiGiaDV.setText("Giá Dịch Vụ Không Hợp Lệ !");
        QLNV.add(lblLoiGiaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, 180, 20));

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

        txtDiaChi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtDiaChi.setForeground(new java.awt.Color(190, 79, 60));
        txtDiaChi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiaChi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiaChiFocusLost(evt);
            }
        });
        QLNV.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 130, 360, 30));

        lblLoiDC.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiDC.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiDC.setText("Địa Chỉ Không Hợp Lệ !");
        QLNV.add(lblLoiDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 160, 180, 20));

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        QLNV.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 720, 30, -1));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 1517, Short.MAX_VALUE)
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
                .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1517, Short.MAX_VALUE))
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
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (check()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (check()) {
            update();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblKhachSanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachSanMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblKhachSan.getSelectedRow();
            edit();
        }
    }//GEN-LAST:event_tblKhachSanMouseClicked

    private void txtGhiChuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtGhiChuActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtGhiChuActionPerformed

    private void btnPrevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPrev, "bgPreHover.png");
    }//GEN-LAST:event_btnPrevMouseEntered

    private void btnPrevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseExited
        new DungChung.DungChung().hoverButton(2, btnPrev, "bgPre.png");
    }//GEN-LAST:event_btnPrevMouseExited

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnFristMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseEntered
        new DungChung.DungChung().hoverButton(1, btnFrist, "bgDauHover.png");
    }//GEN-LAST:event_btnFristMouseEntered

    private void btnFristMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseExited
        new DungChung.DungChung().hoverButton(2, btnFrist, "bgDau.png");
    }//GEN-LAST:event_btnFristMouseExited

    private void btnFristActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFristActionPerformed
        first();
    }//GEN-LAST:event_btnFristActionPerformed

    private void btnNextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseEntered
        new DungChung.DungChung().hoverButton(1, btnNext, "bgNextHover.png");
    }//GEN-LAST:event_btnNextMouseEntered

    private void btnNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseExited
        new DungChung.DungChung().hoverButton(2, btnNext, "bgNext.png");
    }//GEN-LAST:event_btnNextMouseExited

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLast, "bgCuoiHover.png");
    }//GEN-LAST:event_btnLastMouseEntered

    private void btnLastMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseExited
        new DungChung.DungChung().hoverButton(2, btnLast, "bgCuoi.png");
    }//GEN-LAST:event_btnLastMouseExited

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPDF, "bgButtonFPTHover.png");
    }//GEN-LAST:event_btnPDFMouseEntered

    private void btnPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseExited
        new DungChung.DungChung().hoverButton(2, btnPDF, "bgButtonFPT.png");
    }//GEN-LAST:event_btnPDFMouseExited

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtFindCaretUpdate

    private void txtTenKSFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKSFocusLost
        if (!txtTenKS.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtTenKSFocusLost

    private void txtDiaChiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaChiFocusLost
        if (!txtDiaChi.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtDiaChiFocusLost

    private void txtGiaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaFocusLost
        if (!txtGia.getText().trim().isEmpty()) {
            HideError();
        }    }//GEN-LAST:event_txtGiaFocusLost

    private void txtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusLost
        if (!txtSDT.getText().trim().isEmpty()) {
            HideError();
        }    }//GEN-LAST:event_txtSDTFocusLost

    private void tblKhachSanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblKhachSanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblKhachSan.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblKhachSanKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTKhachSan.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTKhachSan().setVisible(true);
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
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiDC;
    private javax.swing.JLabel lblLoiGiaDV;
    private javax.swing.JLabel lblLoiSDT;
    private javax.swing.JLabel lblLoiTenKS;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblKhachSan;
    private ComboBox.TextFieldSuggestion txtDiaChi;
    private swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGia;
    private javax.swing.JTextField txtMaKS;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKS;
    // End of variables declaration//GEN-END:variables
}
