package UI;

import DAO.KhachHangDAO;
import DAO.LT_KhachHangDAO;
import DAO.LichTrinhDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import DungChung.XDate;
import TravelEntity.KhachHang;
import TravelEntity.LT_KhachHang;
import TravelEntity.LichTrinh;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author NHUTLQ
 */
public class Frame_TTKhachHang extends javax.swing.JFrame {
    
    KhachHangDAO dao = new KhachHangDAO();
    LichTrinhDAO ltDao = new LichTrinhDAO();
    LT_KhachHangDAO ltkhDao = new LT_KhachHangDAO();
    int row = -1;
    
    public Frame_TTKhachHang() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }
    
    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiCCCD,
            lblLoiNgaySinh,
            lblLoiSDT,
            lblLoiTenKH,
            lblLoiQueQuan,
            lblLoiEmail
        });
    }
    
    public void init() {
        HideError();
        fillTable();
        setGhiChu();
        setDateInTextFilde();
        setText();
        initDate();
        txtMaKH.setEditable(false);
        txtMaKH.setText(TruyVanSQL.MaKH());
        btnLSTour.setVisible(false);
        txtNgaySinh.setEditable(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
    }
    
    public void initDate() {
        dateChooser.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    dateChooser.hidePopup();
                }
            }
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
            txtQueQuan.addItemSuggestion(arr[i]);
        }
    }
    
    private void setDateInTextFilde() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("01-01-2000");
        txtNgaySinh.setText(String.valueOf(dtf.format(now)));
    }
    
    public void setGhiChu() {
        if (txtGhiChu.getText().isEmpty()) {
            txtGhiChu.setText("Không");
        }
    }
    
    public void Record() {
        lblSTT.setText(String.valueOf(tblKhachHang.getValueAt(row, 0)));
    }
    
    public boolean checkRegex() {
        // Kiểm tra tên khách hàng
        String tenKH = txtTenKH.getText().trim();
        if (tenKH.isEmpty()) {
            lblLoiTenKH.setVisible(true);
            return false;
        }

        // Kiểm tra số điện thoại
        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("^\\d{10}$")) {
            lblLoiSDT.setVisible(true);
            return false;
        }

        // Kiểm tra số CMND
        String cccd = txtCMND.getText().trim();
        if (!cccd.matches("^\\d{9,12}$")) {
            lblLoiCCCD.setVisible(true);
            return false;
        }

        // Kiểm tra quê quán
        String queQuan = txtQueQuan.getText().trim();
        if (queQuan.isEmpty()) {
            lblLoiQueQuan.setVisible(true);
            return false;
        }

        // Kiểm tra email
        String email = txtEmail.getText().trim();
        if (!email.matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b")) {
            lblLoiEmail.setVisible(true);
            return false;
        }

        // Kiểm tra ngày sinh
        String ngaySinh = txtNgaySinh.getText();
        if (ngaySinh.trim().isEmpty()) {
            lblLoiNgaySinh.setVisible(true);
            txtNgaySinh.requestFocus();
            return false;
        } else {
//            lblLoiNgaySinh.setVisible(false);
            // Kiểm tra tuổi phải đủ 18 tuổi trở lên
//            Calendar now = Calendar.getInstance();
//            Calendar dob = Calendar.getInstance();
//            dob.setTime(ngaySinh);
//            int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
//            if (age < 18) {
//                lblLoiNgaySinh.setVisible(true);
//                lblLoiNgaySinh.setText("Khách H");
//                txtNgaysinh.requestFocus();
//                isValid = false;
//            }
        }
        return true;
    }
    
    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtTenKH,
            txtCMND,
            txtQueQuan,
            txtSDT,
            txtEmail,
            txtGhiChu
        };
        txtMaKH.setEditable(false);
        updateStatus();
        btnG.clearSelection();
        txtNgaySinh.setText("");
        tblKhachHang.getSelectionModel().clearSelection();
        new DungChung.DungChung().reset(txt);
        HideError();
        txtMaKH.setText(TruyVanSQL.MaKH());
        setGhiChu();
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        setDateInTextFilde();
        btnLSTour.setVisible(false);
    }
    
    void fillTable() {
        int i = 1;
        DefaultTableModel model = (DefaultTableModel) tblKhachHang.getModel();
        model.setRowCount(0);
        try {
            String find = txtFind.getText();
            List<KhachHang> list = dao.selectByKeyword(find, find, find);
            for (KhachHang kh : list) {
                Object[] row = {
                    String.valueOf(i++),
                    kh.getMaKH(),
                    kh.getTenKH(),
                    kh.getGioiTinh() ? "Nam" : "Nữ",
                    XDate.toString(kh.getNS(), "dd/MM/yyyy"),
                    kh.getCCCD(),
                    kh.getEmail(),
                    kh.getSDT(),
                    kh.getGhiChu()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    void fillForm(KhachHang model) {
        txtMaKH.setText(model.getMaKH().trim());
        txtTenKH.setText(model.getTenKH().trim());
        rdoNam.setSelected(model.getGioiTinh());
        rdoNu.setSelected(!model.getGioiTinh());
        txtQueQuan.setText(model.getQueQuan().trim());
        txtNgaySinh.setText(XDate.toString(model.getNS(), "dd-MM-yyyy").trim());
        txtCMND.setText(model.getCCCD().trim());
        txtSDT.setText(model.getSDT().trim());
        txtEmail.setText(model.getEmail().trim());
        txtGhiChu.setText(model.getGhiChu().trim());
    }
    
    KhachHang getForm() {
        KhachHang model = new KhachHang();
        model.setMaKH(txtMaKH.getText().trim());
        model.setTenKH(txtTenKH.getText().trim());
        model.setGioiTinh(rdoNam.isSelected());
        model.setQueQuan(txtQueQuan.getText().trim());
        model.setNS(XDate.toDate(txtNgaySinh.getText(), "dd-MM-yyyy"));
        model.setCCCD(txtCMND.getText().trim());
        model.setSDT(txtSDT.getText().trim());
        model.setEmail(txtEmail.getText().trim());
        model.setGhiChu(txtGhiChu.getText().trim());
        return model;
    }
    
    public void insert() {
        KhachHang nv = getForm();
        
        try {
            if (dao.selectById(nv.getMaKH()) != null) {
                MsgBox.alertWarning(this, "Khách Hàng Đã Tồn Tại !");
            } else {
                dao.insert(nv);
                fillTable();
                clearForm();
                txtMaKH.setText(TruyVanSQL.MaKH());
                AddLichSuHD.addLSHD("Thêm Khách Hàng");
                MsgBox.alertSuccess(this, "Thêm Mới Thành Công !");
            }
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alertWarning(this, "Thêm Mới Thất Bại !");
        }
    }
    
    public void update() {
        KhachHang kh = getForm();
        
        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Khách Hàng " + kh.getMaKH() + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dao.update(kh);
                    fillTable();
                    clearForm();
                    txtMaKH.setText(TruyVanSQL.MaKH());
                    setGhiChu();
                    AddLichSuHD.addLSHD("Cập Nhật Khách Hàng");
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTKhachHang(), "Cập Nhật Thành Công !");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Cập Nhật Thất Bại");
        }
    }
    
    public void delete() {
        int a = tblKhachHang.getSelectedRow();
        String makh = tblKhachHang.getValueAt(a, 1).toString();
        
        try {
            if (Auth.user.getChucVu().equals("Nhân Viên")) {
                MsgBox.alertWarning(this, "Nhân Viên Không Đủ Thẩm Quyền Xoá");
                return;
            }
            
            List<LichTrinh> lt = ltDao.selectAll();
            List<LT_KhachHang> ltkh = ltkhDao.selectAll();
            for (LichTrinh kh : lt) {
                String khachHang = kh.getMaKH();
                if (khachHang.equals(makh)) {
                    MsgBox.alertWarning(this, "Khách Hàng Đang Sử Dụng Dịch Vụ !");
                    return;
                }
            }
            
            for (LT_KhachHang khachHang : ltkh) {
                if (khachHang.getMakh().equals(makh)) {
                    MsgBox.alertWarning(this, "Khách Hàng Đang Sử Dụng Dịch Vụ !");
                    return;
                }
            }
            
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Khách Hàng " + makh + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    dao.delete(makh);
                    txtMaKH.setText(TruyVanSQL.MaKH());
                    fillTable();
                    clearForm();
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTKhachHang(), "Xoá Khách Hàng Thành Công !");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Xoá Thất Bại");
        }
    }
    
    public void updateStatus() {
        boolean edit = (this.row >= 0);
        
        txtMaKH.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }
    
    public void edit() {
        try {
            String makh = (String) tblKhachHang.getValueAt(row, 1);
            KhachHang kh = dao.selectById(makh);
            if (kh != null) {
                fillForm(kh);
                updateStatus();
                btnLSTour.setVisible(true);
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }
    
    public void timKiem() {
        fillTable();
        row = -1;
    }
    
    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG KHÁCH HÀNG", "Nhân viên", tblKhachHang, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000, 6000});
    }
    
    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG KHÁCH HÀNG", tblKhachHang);
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
        row = 0;
        tblKhachHang.setRowSelectionInterval(row, row);
        scrollToVisible(tblKhachHang, row, row);
        edit();
        Record();
    }
    
    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblKhachHang.setRowSelectionInterval(row, row);
            scrollToVisible(tblKhachHang, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }
    
    public void next() {
        if (this.row < tblKhachHang.getRowCount() - 1) {
            this.row++;
            tblKhachHang.setRowSelectionInterval(row, row);
            scrollToVisible(tblKhachHang, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }
    
    public void last() {
        this.row = tblKhachHang.getRowCount() - 1;
        tblKhachHang.setRowSelectionInterval(row, row);
        scrollToVisible(tblKhachHang, row, row);
        edit();
        Record();
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnG = new javax.swing.ButtonGroup();
        dateChooser = new com.raven.datechooser.DateChooser();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblKhachHang = new DungChung.Table();
        jLabel19 = new javax.swing.JLabel();
        txtCMND = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        txtSDT = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtMaKH = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        lblLoiSDT = new javax.swing.JLabel();
        lblLoiCCCD = new javax.swing.JLabel();
        lblLoiNgaySinh = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        rdoNu = new javax.swing.JRadioButton();
        rdoNam = new javax.swing.JRadioButton();
        txtEmail = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lblLoiTenKH = new javax.swing.JLabel();
        lblLoiQueQuan = new javax.swing.JLabel();
        lblLoiEmail = new javax.swing.JLabel();
        btnFrist = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        lblSTT = new javax.swing.JLabel();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        txtNgaySinh = new javax.swing.JTextField();
        btnNgaySinh = new javax.swing.JButton();
        btnSinhNhat = new javax.swing.JButton();
        txtFind = new swing.TextFieldAnimation();
        txtQueQuan = new ComboBox.TextFieldSuggestion();
        btnLSTour = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnMoi = new javax.swing.JButton();

        dateChooser.setTextRefernce(txtNgaySinh);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1290, 820));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel2.setPreferredSize(new java.awt.Dimension(1290, 820));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblKhachHang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "Giới Tính", "Ngày Sinh", "CMND/CCCD", "Email", "Số Điện Thoại", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblKhachHang.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblKhachHang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblKhachHangMouseClicked(evt);
            }
        });
        tblKhachHang.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblKhachHangKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tblKhachHang);
        if (tblKhachHang.getColumnModel().getColumnCount() > 0) {
            tblKhachHang.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblKhachHang.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblKhachHang.getColumnModel().getColumn(2).setPreferredWidth(100);
            tblKhachHang.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblKhachHang.getColumnModel().getColumn(6).setPreferredWidth(180);
        }

        jPanel2.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, 1150, 290));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(190, 79, 60));
        jLabel19.setText("Ghi Chú");
        jPanel2.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 250, -1, 30));

        txtCMND.setBackground(new java.awt.Color(242, 242, 242));
        txtCMND.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtCMND.setForeground(new java.awt.Color(190, 79, 60));
        txtCMND.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtCMND.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtCMNDFocusLost(evt);
            }
        });
        jPanel2.add(txtCMND, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 370, 30));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(190, 79, 60));
        jLabel20.setText("Quê Quán");
        jPanel2.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 150, -1, 30));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Số Điện Thoại");
        jPanel2.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, -1, 30));

        txtSDT.setBackground(new java.awt.Color(242, 242, 242));
        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(190, 79, 60));
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSDTFocusLost(evt);
            }
        });
        jPanel2.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 100, 350, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Khách Hàng");
        jPanel2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 70, -1, 30));

        txtMaKH.setBackground(new java.awt.Color(242, 242, 242));
        txtMaKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaKH.setForeground(new java.awt.Color(190, 79, 60));
        txtMaKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        jPanel2.add(txtMaKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 370, 30));

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(190, 79, 60));
        jLabel10.setText("Tên Khách Hàng");
        jPanel2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 70, -1, 30));

        txtTenKH.setBackground(new java.awt.Color(242, 242, 242));
        txtTenKH.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenKH.setForeground(new java.awt.Color(190, 79, 60));
        txtTenKH.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtTenKH.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenKHFocusLost(evt);
            }
        });
        jPanel2.add(txtTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 100, 350, 30));

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(190, 79, 60));
        jLabel11.setText("Ngày Sinh");
        jPanel2.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, 30));

        lblLoiSDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiSDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiSDT.setText("Số Điện Thoại Không Hợp Lệ !");
        jPanel2.add(lblLoiSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 130, 170, -1));

        lblLoiCCCD.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiCCCD.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiCCCD.setText("CMND/CCCD Không Hợp Lệ !");
        jPanel2.add(lblLoiCCCD, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 170, -1));

        lblLoiNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiNgaySinh.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiNgaySinh.setText("Ngày Sinh Không Hợp Lệ !");
        jPanel2.add(lblLoiNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 300, 180, 20));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        jPanel2.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 280, 350, 30));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN KHÁCH HÀNG");
        jPanel2.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        btnG.add(rdoNu);
        rdoNu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNu.setText("Nữ");
        jPanel2.add(rdoNu, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 280, -1, 30));

        btnG.add(rdoNam);
        rdoNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        rdoNam.setText("Nam");
        jPanel2.add(rdoNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 280, -1, 30));

        txtEmail.setBackground(new java.awt.Color(242, 242, 242));
        txtEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtEmail.setForeground(new java.awt.Color(190, 79, 60));
        txtEmail.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtEmail.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtEmailFocusLost(evt);
            }
        });
        jPanel2.add(txtEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, 350, 30));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(190, 79, 60));
        jLabel22.setText("Số CMND/CCCD");
        jPanel2.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 150, 120, 30));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(190, 79, 60));
        jLabel24.setText("Giới Tính");
        jPanel2.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 280, -1, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Email");
        jPanel2.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 150, -1, 30));

        lblLoiTenKH.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenKH.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenKH.setText("Tên Khách Hàng Không Hợp Lệ !");
        jPanel2.add(lblLoiTenKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 130, 180, 20));

        lblLoiQueQuan.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiQueQuan.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiQueQuan.setText("Quê Quán Không Hợp Lệ !");
        jPanel2.add(lblLoiQueQuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 210, -1, -1));

        lblLoiEmail.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiEmail.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiEmail.setText("Email Không Hợp Lệ !");
        jPanel2.add(lblLoiEmail, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 210, -1, -1));

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
        jPanel2.add(btnFrist, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 720, -1, -1));

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
        jPanel2.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 720, -1, -1));

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        jPanel2.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 720, 30, -1));

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
        jPanel2.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 720, -1, -1));

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
        jPanel2.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 720, -1, -1));

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
        jPanel2.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(950, 710, -1, -1));

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
        jPanel2.add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 710, -1, -1));

        txtNgaySinh.setBackground(new java.awt.Color(242, 242, 242));
        txtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgaySinh.setForeground(new java.awt.Color(190, 79, 60));
        txtNgaySinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtNgaySinh.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgaySinhFocusLost(evt);
            }
        });
        jPanel2.add(txtNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 270, 340, 30));

        btnNgaySinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnNgaySinh.setBorder(null);
        btnNgaySinh.setBorderPainted(false);
        btnNgaySinh.setContentAreaFilled(false);
        btnNgaySinh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgaySinh.setDefaultCapable(false);
        btnNgaySinh.setFocusPainted(false);
        btnNgaySinh.setFocusable(false);
        btnNgaySinh.setIconTextGap(0);
        btnNgaySinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgaySinhActionPerformed(evt);
            }
        });
        jPanel2.add(btnNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 270, 30, 30));

        btnSinhNhat.setForeground(new java.awt.Color(255, 255, 255));
        btnSinhNhat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgSinhNhat.png"))); // NOI18N
        btnSinhNhat.setBorder(null);
        btnSinhNhat.setBorderPainted(false);
        btnSinhNhat.setContentAreaFilled(false);
        btnSinhNhat.setDefaultCapable(false);
        btnSinhNhat.setFocusPainted(false);
        btnSinhNhat.setFocusable(false);
        btnSinhNhat.setIconTextGap(0);
        btnSinhNhat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSinhNhatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSinhNhatMouseExited(evt);
            }
        });
        btnSinhNhat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSinhNhatActionPerformed(evt);
            }
        });
        jPanel2.add(btnSinhNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(800, 710, -1, -1));

        txtFind.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindCaretUpdate(evt);
            }
        });
        jPanel2.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        txtQueQuan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtQueQuan.setForeground(new java.awt.Color(190, 79, 60));
        txtQueQuan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel2.add(txtQueQuan, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 180, 350, 30));

        btnLSTour.setForeground(new java.awt.Color(255, 255, 255));
        btnLSTour.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgLSDiTour.png"))); // NOI18N
        btnLSTour.setBorder(null);
        btnLSTour.setBorderPainted(false);
        btnLSTour.setContentAreaFilled(false);
        btnLSTour.setDefaultCapable(false);
        btnLSTour.setFocusPainted(false);
        btnLSTour.setFocusable(false);
        btnLSTour.setIconTextGap(0);
        btnLSTour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLSTourMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLSTourMouseExited(evt);
            }
        });
        btnLSTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLSTourActionPerformed(evt);
            }
        });
        jPanel2.add(btnLSTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 710, -1, -1));

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
        jPanel2.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, -1));

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
        jPanel2.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 340, -1, -1));

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
        jPanel2.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, -1, -1));

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
        jPanel2.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 340, -1, -1));

        jPanel1.add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1517, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTenKHFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenKHFocusLost
        if (!txtTenKH.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtTenKHFocusLost

    private void txtCMNDFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtCMNDFocusLost
        if (!txtCMND.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtCMNDFocusLost

    private void txtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusLost
        if (!txtSDT.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtSDTFocusLost

    private void tblKhachHangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblKhachHangMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblKhachHang.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblKhachHangMouseClicked

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

    private void btnNgaySinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgaySinhActionPerformed
        dateChooser.showPopup();
    }//GEN-LAST:event_btnNgaySinhActionPerformed

    private void txtNgaySinhFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgaySinhFocusLost
        if (!txtNgaySinh.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtNgaySinhFocusLost

    private void txtEmailFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtEmailFocusLost
        if (!txtEmail.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtEmailFocusLost

    private void btnSinhNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSinhNhatMouseEntered
        new DungChung.DungChung().hoverButton(1, btnSinhNhat, "bgSinhNhat-Hover.png");
    }//GEN-LAST:event_btnSinhNhatMouseEntered

    private void btnSinhNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSinhNhatMouseExited
        new DungChung.DungChung().hoverButton(2, btnSinhNhat, "bgSinhNhat.png");
    }//GEN-LAST:event_btnSinhNhatMouseExited

    private void btnSinhNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSinhNhatActionPerformed
        new DiaLog_SinhNhat(this, true).setVisible(true);
    }//GEN-LAST:event_btnSinhNhatActionPerformed

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        fillTable();
        row = -1;
    }//GEN-LAST:event_txtFindCaretUpdate

    private void btnLSTourMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLSTourMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLSTour, "bgLSDiTour-Hover.png");
    }//GEN-LAST:event_btnLSTourMouseEntered

    private void btnLSTourMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLSTourMouseExited
        new DungChung.DungChung().hoverButton(2, btnLSTour, "bgLSDiTour.png");
    }//GEN-LAST:event_btnLSTourMouseExited

    private void btnLSTourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLSTourActionPerformed
        new DiaLog_SLKhachHangDaDi(this, true).setVisible(true);
    }//GEN-LAST:event_btnLSTourActionPerformed

    private void tblKhachHangKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblKhachHangKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblKhachHang.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblKhachHangKeyPressed

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThem, "bgButtonThemHover.png");
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        new DungChung.DungChung().hoverButton(2, btnThem, "bgButtonThem.png");
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (checkRegex()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseEntered
        new DungChung.DungChung().hoverButton(1, btnCapNhat, "bgButtonCapNhatHover.png");
    }//GEN-LAST:event_btnCapNhatMouseEntered

    private void btnCapNhatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnCapNhatMouseExited
        new DungChung.DungChung().hoverButton(2, btnCapNhat, "bgButtonCapNhat.png");
    }//GEN-LAST:event_btnCapNhatMouseExited

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (checkRegex()) {
            update();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "bgButtonXoaHover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "bgButtonXoa.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseEntered
        new DungChung.DungChung().hoverButton(1, btnMoi, "bgButtonMoiHover.png");
    }//GEN-LAST:event_btnMoiMouseEntered

    private void btnMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMoiMouseExited
        new DungChung.DungChung().hoverButton(2, btnMoi, "bgButtonMoi.png");
    }//GEN-LAST:event_btnMoiMouseExited

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed
    
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
            java.util.logging.Logger.getLogger(Frame_TTKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTKhachHang.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTKhachHang().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCapNhat;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFrist;
    private javax.swing.ButtonGroup btnG;
    private javax.swing.JButton btnLSTour;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnMoi;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNgaySinh;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSinhNhat;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblLoiCCCD;
    private javax.swing.JLabel lblLoiEmail;
    private javax.swing.JLabel lblLoiNgaySinh;
    private javax.swing.JLabel lblLoiQueQuan;
    private javax.swing.JLabel lblLoiSDT;
    private javax.swing.JLabel lblLoiTenKH;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JRadioButton rdoNam;
    private javax.swing.JRadioButton rdoNu;
    private DungChung.Table tblKhachHang;
    private javax.swing.JTextField txtCMND;
    private javax.swing.JTextField txtEmail;
    private swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGhiChu;
    public static javax.swing.JTextField txtMaKH;
    private javax.swing.JTextField txtNgaySinh;
    private ComboBox.TextFieldSuggestion txtQueQuan;
    private javax.swing.JTextField txtSDT;
    private javax.swing.JTextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
