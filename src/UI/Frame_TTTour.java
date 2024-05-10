package UI;

import DAO.LichTrinhDAO;
import DAO.ThongTinTourDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import DungChung.XDate;
import TravelEntity.LichTrinh;
import TravelEntity.ThongTinTour;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
 * @author NHUTLQ
 */
public class Frame_TTTour extends javax.swing.JFrame {

    ThongTinTourDAO tourDao = new ThongTinTourDAO();
    LichTrinhDAO ltDao = new LichTrinhDAO();
    int row = -1;

    public Frame_TTTour() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        HideError();
        sxTheoCot();
        fillTable();
        updateStatus();
        setDateInTextFilde();
        setGhiChu();
        setText();
        initDate();
        txtMaTour.setText(TruyVanSQL.MaTour());
        txtNguoiTao.setText(LoginForm.txtUser.getText());
        txtNguoiTao.setEditable(false);
        txtMaTour.setEditable(false);
        txtNgayTao.setEditable(false);
    }

    public void initDate() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = dateObj.format(formatter);
        txtNgayTao.setText(date);
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
            txtDiemDi.addItemSuggestion(arr[i]);
            txtDiemDen.addItemSuggestion(arr[i]);
        }
    }

    public void setGhiChu() {
        if (txtGhiChu.getText().isEmpty()) {
            txtGhiChu.setText("Không");
        }
    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiDiemDi,
            lblLoiDiemDen,
            lblLoiGiaTour,
            lblLoiDiemDen,
            lblLoiDiemDi,
            lblLoiTenTour
        });
    }

    public boolean checkRegex() {
        if (txtGiaTour.getText().trim().isEmpty()) {
            lblLoiGiaTour.setVisible(true);
            return false;
        }
        try {
            Double a = Double.parseDouble(txtGiaTour.getText());
            if (a < 0) {
                lblLoiGiaTour.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            lblLoiGiaTour.setVisible(true);
            return false;
        }

        if (txtTenTour.getText().trim().isEmpty()) {
            lblLoiTenTour.setVisible(true);
            return false;
        }

        if (txtDiemDi.getText().trim().isEmpty()) {
            lblLoiDiemDi.setVisible(true);
            return false;
        }
        if (txtDiemDen.getText().trim().isEmpty()) {
            lblLoiDiemDen.setVisible(true);
            return false;
        }

        return true;
    }

    public void insert() {
        ThongTinTour tour = getForm();
        try {
            tourDao.insert(tour);
            fillTable();
            clearForm();
            txtMaTour.setText(TruyVanSQL.MaTour());
            btnThem.setEnabled(true);
            btnCapNhat.setEnabled(false);
            btnXoa.setEnabled(false);
            AddLichSuHD.addLSHD("Thêm Tour Du Lịch");
            MsgBox.alertSuccess(this, "Thêm Mới Thành Công !");
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alertWarning(this, "Thêm Mới Thất Bại !");
        }
    }

    public void update() {
        ThongTinTour tour = getForm();
        String maTour = txtMaTour.getText();

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Mã Tour ' " + maTour + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    tourDao.update(tour);
                    fillTable();
                    clearForm();
                    setGhiChu();
                    txtMaTour.setText(TruyVanSQL.MaTour());
                    AddLichSuHD.addLSHD("Cập Nhật Tour Du Lịch");
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
        int a = tblTour.getSelectedRow();
        String maTour = tblTour.getValueAt(a, 1).toString();

        try {
            if (Auth.user.getChucVu().equals("Nhân Viên")) {
                MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
                return;
            }

            List<LichTrinh> chk = ltDao.selectAll();
            for (LichTrinh nv : chk) {
                String nhanVien = nv.getMaTour();
                if (nhanVien.equals(maTour)) {
                    MsgBox.alertWarning(this, "Tour Đang Được Vận Hành !");
                    return;
                }
            }

            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Mã Tour ' " + maTour + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    tourDao.delete(maTour);
                    txtMaTour.setText(TruyVanSQL.MaTour());
                    AddLichSuHD.addLSHD("Xoá Tour Du Lịch");
                    fillTable();
                    clearForm();
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTTour(), "Xoá Tour ' " + maTour + " Thành Công !");

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

        DefaultTableModel model = (DefaultTableModel) tblTour.getModel();
        model.setRowCount(0);

        try {
            ThongTinTour tour = new ThongTinTour();
            if (tour != null) {
                String keyWord = txtFind.getText();

                List<ThongTinTour> list = tourDao.selectByKeyword(keyWord, keyWord, keyWord, keyWord);
                for (ThongTinTour touris : list) {
                    Object[] row = {
                        String.valueOf(i++),
                        touris.getMaTour(),
                        touris.getTenTour(),
                        touris.getDiemDi(),
                        touris.getDiemDen(),
                        new DungChung.DungChung().convertTien(touris.getGiaTour()) + " VNĐ",
                        XDate.toString(touris.getNgayTao(), "dd-MM-yyyy"),
                        touris.getGhiChu()
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu TBL !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtTenTour,
            txtDiemDi,
            txtGiaTour,
            txtGhiChu,
            txtDiemDen
        };
        setGhiChu();
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
        txtMaTour.setText(TruyVanSQL.MaTour());
        tblTour.getSelectionModel().clearSelection();
        new DungChung.DungChung().reset(txt);
        HideError();
        setDateInTextFilde();
    }

    ThongTinTour getForm() {
        ThongTinTour tour = new ThongTinTour();
        tour.setMaTour(txtMaTour.getText());
        tour.setTenTour(txtTenTour.getText());
        tour.setDiemDi(txtDiemDi.getText());
        tour.setDiemDen(txtDiemDen.getText());
        tour.setGiaTour(txtGiaTour.getText());
        tour.setGhiChu(txtGhiChu.getText());
        return tour;
    }

    public void fillForm(ThongTinTour tour) {
        txtMaTour.setText(tour.getMaTour());
        txtTenTour.setText(tour.getTenTour());
        txtDiemDi.setText(tour.getDiemDi());
        txtDiemDen.setText(tour.getDiemDen());
        txtGiaTour.setText(String.valueOf(tour.getGiaTour()));
        txtNgayTao.setText(XDate.toString(tour.getNgayTao(), "dd-MM-yyyy"));
        txtGhiChu.setText(tour.getGhiChu());
    }

    public void edit() {
        try {
            int a = tblTour.getSelectedRow();
            String maTour = (String) tblTour.getValueAt(a, 1);
            ThongTinTour tour = tourDao.selectById(maTour);
            if (tour != null) {
                fillForm(tour);
                updateStatus();
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void timKiem() {
        fillTable();
        row = -1;
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);

        txtMaTour.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    //hàm này để scroll bảng theo index
    public void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                table.scrollRectToVisible(table.getCellRect(rowIndex, vColIndex, false));
            }
        });
    }

    public void Record() {
        lblSTT.setText(String.valueOf(tblTour.getValueAt(row, 0)));
    }

    private void setDateInTextFilde() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtNgayTao.setText(String.valueOf(dtf.format(now)));
    }

    private void sxTheoCot() {
        DefaultTableModel model = (DefaultTableModel) tblTour.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblTour.setRowSorter(sorter);
    }

    public void first() {
        row = 0;
        tblTour.setRowSelectionInterval(row, row);
        scrollToVisible(tblTour, row, row);
        edit();
        Record();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblTour.setRowSelectionInterval(row, row);
            scrollToVisible(tblTour, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblTour.getRowCount() - 1) {
            this.row++;
            tblTour.setRowSelectionInterval(row, row);
            scrollToVisible(tblTour, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        this.row = tblTour.getRowCount() - 1;
        tblTour.setRowSelectionInterval(row, row);
        scrollToVisible(tblTour, row, row);
        edit();
        Record();
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG TOUR DU LỊCH", "Nhân viên", tblTour, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG TOUR DU LỊCH", tblTour);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblTour = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txtGiaTour = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtMaTour = new javax.swing.JTextField();
        txtTenTour = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        txtNguoiTao = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        lblLoiDiemDi = new javax.swing.JLabel();
        lblLoiDiemDen = new javax.swing.JLabel();
        lblLoiGiaTour = new javax.swing.JLabel();
        btnFrist = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        lblLoiTenTour = new javax.swing.JLabel();
        txtNgayTao = new javax.swing.JTextField();
        txtFind = new swing.TextFieldAnimation();
        txtDiemDen = new ComboBox.TextFieldSuggestion();
        txtDiemDi = new ComboBox.TextFieldSuggestion();
        lblSTT = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblTour.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Tour", "Tên Tour", "Điểm Đi", "Điểm Đến", "Giá Tour", "Ngày Tạo", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblTour.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblTour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblTourMouseClicked(evt);
            }
        });
        tblTour.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblTourKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblTour);

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 412, 1145, 290));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN TOUR");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Tour");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, -1, -1));

        txtGiaTour.setBackground(new java.awt.Color(242, 242, 242));
        txtGiaTour.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiaTour.setForeground(new java.awt.Color(190, 79, 60));
        txtGiaTour.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtGiaTour.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtGiaTourFocusLost(evt);
            }
        });
        QLNV.add(txtGiaTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, 360, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Tên Tour");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 80, -1, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(190, 79, 60));
        jLabel19.setText("Điểm Đến");
        QLNV.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 240, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Giá Tour (VNĐ)");
        QLNV.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 160, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(190, 79, 60));
        jLabel22.setText("Ngày Tạo");
        QLNV.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 160, -1, -1));

        txtMaTour.setBackground(new java.awt.Color(242, 242, 242));
        txtMaTour.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaTour.setForeground(new java.awt.Color(190, 79, 60));
        txtMaTour.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtMaTour.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaTourFocusLost(evt);
            }
        });
        QLNV.add(txtMaTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 100, 340, 30));

        txtTenTour.setBackground(new java.awt.Color(242, 242, 242));
        txtTenTour.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtTenTour.setForeground(new java.awt.Color(190, 79, 60));
        txtTenTour.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtTenTour.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtTenTourFocusLost(evt);
            }
        });
        QLNV.add(txtTenTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 100, 340, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(190, 79, 60));
        jLabel25.setText("Điểm Đi");
        QLNV.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Người Tạo");
        QLNV.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 160, -1, -1));

        txtNguoiTao.setBackground(new java.awt.Color(242, 242, 242));
        txtNguoiTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNguoiTao.setForeground(new java.awt.Color(190, 79, 60));
        txtNguoiTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtNguoiTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 180, 340, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(190, 79, 60));
        jLabel28.setText("Ghi Chú");
        QLNV.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 80, -1, -1));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 100, 340, 30));

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
        QLNV.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 340, -1, -1));

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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 340, -1, -1));

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
        QLNV.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 340, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 340, -1, -1));

        lblLoiDiemDi.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiDiemDi.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiDiemDi.setText("Điểm Đi Không Hợp Lệ !");
        QLNV.add(lblLoiDiemDi, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 290, 180, 20));

        lblLoiDiemDen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiDiemDen.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiDiemDen.setText("Điểm Đến Không Hợp Lệ !");
        QLNV.add(lblLoiDiemDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 290, 180, 20));

        lblLoiGiaTour.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaTour.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiGiaTour.setText("Giá Tour Không Hợp Lệ !");
        QLNV.add(lblLoiGiaTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 210, 180, 20));

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

        lblLoiTenTour.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenTour.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenTour.setText("Tên Tour Không Hợp Lệ !");
        QLNV.add(lblLoiTenTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 130, 180, 20));

        txtNgayTao.setBackground(new java.awt.Color(242, 242, 242));
        txtNgayTao.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgayTao.setForeground(new java.awt.Color(190, 79, 60));
        txtNgayTao.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtNgayTao, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 180, 340, 30));

        txtFind.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindCaretUpdate(evt);
            }
        });
        QLNV.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        txtDiemDen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtDiemDen.setForeground(new java.awt.Color(190, 79, 60));
        txtDiemDen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiemDen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiemDenFocusLost(evt);
            }
        });
        QLNV.add(txtDiemDen, new org.netbeans.lib.awtextra.AbsoluteConstraints(460, 260, 340, 30));

        txtDiemDi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtDiemDi.setForeground(new java.awt.Color(190, 79, 60));
        txtDiemDi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiemDi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiemDiFocusLost(evt);
            }
        });
        QLNV.add(txtDiemDi, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 260, 340, 30));

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
        if (checkRegex()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnCapNhatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCapNhatActionPerformed
        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }
        if (checkRegex()) {
            update();
        }
    }//GEN-LAST:event_btnCapNhatActionPerformed

    private void btnMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMoiActionPerformed
        clearForm();
    }//GEN-LAST:event_btnMoiActionPerformed

    private void txtMaTourFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaTourFocusLost
        if (!txtMaTour.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtMaTourFocusLost

    private void txtTenTourFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtTenTourFocusLost
        if (!txtTenTour.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtTenTourFocusLost

    private void txtGiaTourFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtGiaTourFocusLost
        if (!txtGiaTour.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtGiaTourFocusLost

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

    private void tblTourMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblTourMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblTour.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblTourMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        fillTable();
        row = -1;
    }//GEN-LAST:event_txtFindCaretUpdate

    private void txtDiemDiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemDiFocusLost
        if (!txtDiemDi.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtDiemDiFocusLost

    private void txtDiemDenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiemDenFocusLost
        if (!txtDiemDen.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtDiemDenFocusLost

    private void tblTourKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblTourKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblTour.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblTourKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTTour.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTTour().setVisible(true);
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
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiDiemDen;
    private javax.swing.JLabel lblLoiDiemDi;
    private javax.swing.JLabel lblLoiGiaTour;
    private javax.swing.JLabel lblLoiTenTour;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblTour;
    private ComboBox.TextFieldSuggestion txtDiemDen;
    private ComboBox.TextFieldSuggestion txtDiemDi;
    private swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGhiChu;
    private javax.swing.JTextField txtGiaTour;
    private javax.swing.JTextField txtMaTour;
    private javax.swing.JTextField txtNgayTao;
    private javax.swing.JTextField txtNguoiTao;
    private javax.swing.JTextField txtTenTour;
    // End of variables declaration//GEN-END:variables
}
