package UI;

import DAO.LT_DiaDiemDAO;
import DAO.ThamQuanDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.MsgBox;
import TravelEntity.LT_DiaDiem;
import TravelEntity.ThamQuan;
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
public class Frame_TTDiaDiem extends javax.swing.JFrame {

    ThamQuanDAO tqDao = new ThamQuanDAO();
    LT_DiaDiemDAO ltddDao = new LT_DiaDiemDAO();
    int row = -1;

    public Frame_TTDiaDiem() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        sxTheoCot();
        HideError();
        setGhiChu();
        fillTable();
        updateStatus();
        setText();
        txtMaDD.setText(TruyVanSQL.MaTQ());
        txtMaDD.setEditable(false);
        txtNguoiTao.setEditable(false);
    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiDC,
            lblLoiGiaDV,
            lblLoiSDT,
            lblLoiTenDT

        });
    }

    private boolean check() {
        boolean flag = true;
        String tenDD = txtTenDT.getText().trim();
        if (tenDD.length() < 5) {
            lblLoiDC.setVisible(true);
            txtTenDT.requestFocus();
            return false;
        }
        if (txtTenDT.getText().trim().length() < 5) {
            lblLoiDC.setVisible(true);
            txtTenDT.requestFocus();
            return false;
        }
        String queQuan = txtDiaChi.getText().trim();
        if (queQuan.isEmpty()) {
            lblLoiDC.setVisible(true);
            return false;
        }
        if (txtGiaDV.getText().trim().isEmpty()) {
            lblLoiGiaDV.setVisible(true);
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

        return flag;
    }

    private void sxTheoCot() {
        DefaultTableModel model = (DefaultTableModel) tblThamQuan.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblThamQuan.setRowSorter(sorter);
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
    }

    public void insert() {
        ThamQuan tq = getForm();
        try {
            tqDao.insert(tq);
            fillTable();
            clearForm();
            btnThem.setEnabled(true);
            btnCapNhat.setEnabled(false);
            btnXoa.setEnabled(false);
            AddLichSuHD.addLSHD("Thêm Địa Điểm Du Lịch");
            MsgBox.alertSuccess(this, "Thêm Mới Thành Công !");
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertWarning(this, "Thêm Mới Thất Bại !");
        }
    }

    public void update() {
        ThamQuan tq = getForm();

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Địa Điểm Tham Quan ' " + tq.getTendd() + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    tqDao.update(tq);
                    fillTable();
                    clearForm();
                    setGhiChu();
                    AddLichSuHD.addLSHD("Cập Nhật Địa Điểm Du Lịch");
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTTour(), "Cập Nhật Thành Công !");

                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertWarning(this, "Cập Nhật Thất Bại");
        }
    }

    public void delete() {
        int a = tblThamQuan.getSelectedRow();
        String matq = txtMaDD.getText();
        String tendd = tblThamQuan.getValueAt(a, 2).toString();

        try {
            if (Auth.user.getChucVu().equals("Nhân Viên")) {
                MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
                return;
            }

            List<LT_DiaDiem> ltdd = ltddDao.selectAll();
            for (LT_DiaDiem dd : ltdd) {
                if (dd.getMaDD().equals(matq)) {
                    MsgBox.alertWarning(this, "Địa Điểm Đang Được Sử Dụng !");
                    return;
                }
            }

            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Địa Điểm ' " + tendd + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    tqDao.delete(matq);
                    AddLichSuHD.addLSHD("Xoá Địa Điểm Du Lịch");
                    fillTable();
                    clearForm();
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTTour(), "Xoá Địa Điểm ' " + tendd + " Thành Công !");

                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertWarning(this, "Xoá Thất Bại");
        }
    }

    public void fillTable() {
        int i = 1;

        DefaultTableModel model = (DefaultTableModel) tblThamQuan.getModel();
        model.setRowCount(0);

        try {
            ThamQuan tq = new ThamQuan();
            if (tq != null) {
                String keyWord = txtFind.getText();

                List<ThamQuan> list = tqDao.selectByKeyword(keyWord, keyWord, keyWord, keyWord);
                for (ThamQuan tquan : list) {
                    Object[] row = {
                        String.valueOf(i++),
                        tquan.getMadd(),
                        tquan.getTendd(),
                        tquan.getLoaidd(),
                        tquan.getSdt(),
                        new DungChung.DungChung().convertTien(String.valueOf(tquan.getGia())) + " VNĐ",
                        tquan.getDiachi(),
                        tquan.getGhichu()
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu TBL !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    ThamQuan getForm() {
        ThamQuan tq = new ThamQuan();
        tq.setMadd(txtMaDD.getText().trim());
        tq.setTendd(txtTenDT.getText().trim());
        tq.setLoaidd((String) cboLoaiDV.getSelectedItem());
        tq.setSdt(txtSDT.getText().trim());
        tq.setGia(Double.parseDouble(txtGiaDV.getText()));
        tq.setDiachi(txtDiaChi.getText().trim());
        tq.setGhichu(txtGhiChu.getText().trim());
        return tq;
    }

    public void fillForm(ThamQuan tq) {
        txtMaDD.setText(tq.getMadd().trim());
        txtTenDT.setText(tq.getTendd().trim());
        cboLoaiDV.setSelectedItem(tq.getLoaidd());
        txtSDT.setText(tq.getSdt().trim());
        txtGiaDV.setText(String.valueOf(tq.getGia()));
        txtDiaChi.setText(tq.getDiachi().trim());
        txtGhiChu.setText(tq.getGhichu().trim());
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtTenDT,
            txtDiaChi,
            txtGiaDV,
            txtSDT
        };
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        txtMaDD.setText(TruyVanSQL.MaTQ());
        tblThamQuan.getSelectionModel().clearSelection();
        new DungChung.DungChung().reset(txt);
        HideError();
        setGhiChu();
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);

        txtMaDD.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public void edit() {
        try {
            int a = tblThamQuan.getSelectedRow();
            String matq = (String) tblThamQuan.getValueAt(a, 1);
            ThamQuan kh = tqDao.selectById(matq);
            if (kh != null) {
                fillForm(kh);
                updateStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH THÔNG TIN ĐỊA ĐIỂM", "Nhân viên", tblThamQuan, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new DungChung.ExportPDF().exportPDF("DANH SÁCH BẢNG THAM QUAN", tblThamQuan);
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
        lblSTT.setText(String.valueOf(tblThamQuan.getValueAt(row, 0)));
    }

    public void first() {
        row = 0;
        tblThamQuan.setRowSelectionInterval(row, row);
        scrollToVisible(tblThamQuan, row, row);
        edit();
        Record();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblThamQuan.setRowSelectionInterval(row, row);
            scrollToVisible(tblThamQuan, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblThamQuan.getRowCount() - 1) {
            this.row++;
            tblThamQuan.setRowSelectionInterval(row, row);
            scrollToVisible(tblThamQuan, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        this.row = tblThamQuan.getRowCount() - 1;
        tblThamQuan.setRowSelectionInterval(row, row);
        scrollToVisible(tblThamQuan, row, row);
        edit();
        Record();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblThamQuan = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtMaDD = new javax.swing.JTextField();
        txtTenDT = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtGiaDV = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        lblLoiGiaDV = new javax.swing.JLabel();
        lblLoiDC = new javax.swing.JLabel();
        cboLoaiDV = new javax.swing.JComboBox<>();
        jLabel26 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        lblLoiSDT = new javax.swing.JLabel();
        btnFrist = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lblSTT = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        txtFind = new swing.TextFieldAnimation();
        txtDiaChi = new ComboBox.TextFieldSuggestion();
        lblLoiTenDT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblThamQuan.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Đối Tác", "Tên Đối Tác", "Loại Dịch Vụ", "Số Điện Thoại", "Giá Dịch vụ", "Địa Chỉ", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblThamQuan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblThamQuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblThamQuanMouseClicked(evt);
            }
        });
        tblThamQuan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblThamQuanKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblThamQuan);
        if (tblThamQuan.getColumnModel().getColumnCount() > 0) {
            tblThamQuan.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblThamQuan.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblThamQuan.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 412, 1145, 290));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ DỊCH VỤ THAM QUAN - VUI CHƠI");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Địa Điểm");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, -1, -1));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 240, 250, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Tên Địa Điểm");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 100, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(190, 79, 60));
        jLabel20.setText("Địa Chỉ");
        QLNV.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 100, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Ghi Chú");
        QLNV.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 220, -1, -1));

        txtMaDD.setBackground(new java.awt.Color(242, 242, 242));
        txtMaDD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaDD.setForeground(new java.awt.Color(190, 79, 60));
        txtMaDD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtMaDD, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 250, 30));

        txtTenDT.setBackground(new java.awt.Color(242, 242, 242));
        txtTenDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenDT.setForeground(new java.awt.Color(190, 79, 60));
        txtTenDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtTenDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenDTFocusLost(evt);
            }
        });
        QLNV.add(txtTenDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 120, 260, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(190, 79, 60));
        jLabel25.setText("Loại dịch vụ");
        QLNV.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 100, -1, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Giá Dịch Vụ (VNĐ)");
        QLNV.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 220, -1, -1));

        txtGiaDV.setBackground(new java.awt.Color(242, 242, 242));
        txtGiaDV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaDV.setForeground(new java.awt.Color(190, 79, 60));
        txtGiaDV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtGiaDV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiaDVFocusLost(evt);
            }
        });
        QLNV.add(txtGiaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, 250, 30));

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
        QLNV.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 330, -1, -1));

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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 330, -1, -1));

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
        QLNV.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 330, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 330, -1, -1));

        lblLoiGiaDV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaDV.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiGiaDV.setText("Giá Dịch Vụ Không Hợp Lệ !");
        QLNV.add(lblLoiGiaDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 180, 20));

        lblLoiDC.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiDC.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiDC.setText("Địa Chỉ Không Hợp Lệ !");
        QLNV.add(lblLoiDC, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 150, 240, 20));

        cboLoaiDV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboLoaiDV.setForeground(new java.awt.Color(190, 79, 60));
        cboLoaiDV.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Vé Người Lớn", "Vé Trẻ Em" }));
        cboLoaiDV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(cboLoaiDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 120, 250, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(190, 79, 60));
        jLabel26.setText("Người Tạo");
        QLNV.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 220, -1, -1));

        txtNguoiTao.setBackground(new java.awt.Color(242, 242, 242));
        txtNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNguoiTao.setForeground(new java.awt.Color(190, 79, 60));
        txtNguoiTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 240, 250, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(190, 79, 60));
        jLabel28.setText("Số Điện Thoại");
        QLNV.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 220, -1, -1));

        txtSDT.setBackground(new java.awt.Color(242, 242, 242));
        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(190, 79, 60));
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSDTFocusLost(evt);
            }
        });
        QLNV.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 240, 260, 30));

        lblLoiSDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiSDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiSDT.setText("Số Điện Thoại Không Hợp Lệ !");
        QLNV.add(lblLoiSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 270, 180, 20));

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

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        QLNV.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 720, 30, -1));

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
        QLNV.add(txtDiaChi, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 120, 260, 30));

        lblLoiTenDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenDT.setText("Tên Địa Điểm Tham Quan Không Hợp Lệ !");
        QLNV.add(lblLoiTenDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 150, 240, 20));

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

    private void btnFristMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseEntered
        new DungChung.DungChung().hoverButton(1, btnFrist, "bgDauHover.png");
    }//GEN-LAST:event_btnFristMouseEntered

    private void btnFristMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseExited
        new DungChung.DungChung().hoverButton(2, btnFrist, "bgDau.png");
    }//GEN-LAST:event_btnFristMouseExited

    private void btnFristActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFristActionPerformed
        first();
    }//GEN-LAST:event_btnFristActionPerformed

    private void btnPrevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPrev, "bgPreHover.png");
    }//GEN-LAST:event_btnPrevMouseEntered

    private void btnPrevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseExited
        new DungChung.DungChung().hoverButton(2, btnPrev, "bgPre.png");
    }//GEN-LAST:event_btnPrevMouseExited

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

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
        fillTable();
        row = -1;
    }//GEN-LAST:event_txtFindCaretUpdate

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void tblThamQuanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblThamQuanMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblThamQuan.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblThamQuanMouseClicked

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

    private void txtTenDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenDTFocusLost
        if (!txtTenDT.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtTenDTFocusLost

    private void txtGiaDVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaDVFocusLost
        if (!txtGiaDV.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtGiaDVFocusLost

    private void txtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusLost
        if (!txtSDT.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtSDTFocusLost

    private void txtDiaChiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaChiFocusLost
        if (!txtDiaChi.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtDiaChiFocusLost

    private void tblThamQuanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblThamQuanKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblThamQuan.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblThamQuanKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTDiaDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTDiaDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTDiaDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTDiaDiem.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTDiaDiem().setVisible(true);
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
    private javax.swing.JComboBox<String> cboLoaiDV;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiDC;
    private javax.swing.JLabel lblLoiGiaDV;
    private javax.swing.JLabel lblLoiSDT;
    private javax.swing.JLabel lblLoiTenDT;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblThamQuan;
    private ComboBox.TextFieldSuggestion txtDiaChi;
    private swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGiaDV;
    private javax.swing.JTextField txtMaDD;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenDT;
    // End of variables declaration//GEN-END:variables
}
