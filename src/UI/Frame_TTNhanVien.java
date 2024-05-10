package UI;

import DAO.HopDongDAO;
import DAO.KhachHangDAO;
import DAO.LT_DiaDiemDAO;
import DAO.LT_KhachHangDAO;
import DAO.LT_KhachSanDAO;
import DAO.LT_PhuongTienDAO;
import DAO.LichSuHoatDongDAO;
import DAO.LichTrinhDAO;
import DAO.NhanVienDAO;
import DAO.ThongTinTourDAO;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import DungChung.XDate;
import DungChung.XImage;
import TravelEntity.HopDong;
import TravelEntity.LichSuHoatDong;
import TravelEntity.LichTrinh;
import TravelEntity.NhanVien;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;
import swing.EventCallBack;
import swing.EventTextField;

/**
 * @author khang
 */
public class Frame_TTNhanVien extends javax.swing.JFrame {

    NhanVienDAO nvDao = new NhanVienDAO();
    LichSuHoatDongDAO lsDao = new LichSuHoatDongDAO();
    LichTrinhDAO ltDao = new LichTrinhDAO();
    KhachHangDAO khDao = new KhachHangDAO();
    ThongTinTourDAO tourDao = new ThongTinTourDAO();
    LT_PhuongTienDAO ltptDao = new LT_PhuongTienDAO();
    LT_KhachHangDAO ltkhDao = new LT_KhachHangDAO();
    LT_DiaDiemDAO ltddDao = new LT_DiaDiemDAO();
    LT_KhachSanDAO ltksDao = new LT_KhachSanDAO();
    HopDongDAO hdDao = new HopDongDAO();
    int row = -1;

    public Frame_TTNhanVien() {
        initComponents();
        init();
    }

    public void init() {
        HideError();
        sxTheoCot();
        fillTable();
        updateStatus();
        setDateInTextFilde();
        timKiem();
        initDate();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public boolean checkRegex() {
        if (txtMaNV.getText().trim().isEmpty()) {
            lblLoiMaNV.setVisible(true);
            txtMaNV.requestFocus();
            return false;
        }
        if (txtMatKhau.getText().trim().isEmpty()) {
            lblLoiMatKhau.setVisible(true);
            return false;
        }
        if (txtHoTen.getText().trim().isEmpty()) {
            lblLoiHoTen.setVisible(true);
            return false;
        }
        String ngaySinh = txtNgaySinh.getText();
        if (ngaySinh.trim().isEmpty()) {
            lblLoiNgaySinh.setVisible(true);
            return false;
        } else {
            try {
                lblLoiNgaySinh.setVisible(false);
                //Kiểm tra tuổi phải đủ 18 tuổi trở lên
                Calendar now = Calendar.getInstance();
                Calendar dob = Calendar.getInstance();
                Date date1 = new SimpleDateFormat("dd-MM-yyyy").parse(ngaySinh);
                dob.setTime(date1);
                int age = now.get(Calendar.YEAR) - dob.get(Calendar.YEAR);
                if (age < 18) {
                    lblLoiNgaySinh.setVisible(true);
                    lblLoiNgaySinh.setText("Nhân viên chưa đủ 18 tuổi !");
                    return false;
                }
            } catch (ParseException ex) {
                System.err.println(ex);
            }
        }
        String sdt = txtSDT.getText().trim();
        if (!sdt.matches("^\\d{10}$")) {
            lblLoiSDT.setVisible(true);
            return false;
        }
        if (rdo1.isSelected() == false && rdo2.isSelected() == false && rdo3.isSelected() == false) {
            lblLoiChucVu.setVisible(true);
            return false;
        }
        return true;
    }

    public void initDate() {
        NgayKHD.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    NgayKHD.hidePopup();
                }
            }
        });
        NgaySinh.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    NgaySinh.hidePopup();
                }
            }
        });
    }

    public void Record() {
        lblSTT.setText(String.valueOf(tblNV.getValueAt(row, 0)));
    }

    private void setDateInTextFilde() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtNgayKyHD.setText(String.valueOf(dtf.format(now)));
    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiMaNV,
            lblLoiMatKhau,
            lblLoiHoTen,
            lblLoiNgaySinh,
            lblLoiSDT,
            lblLoiChucVu
        });
    }

    //hàm này để sắp xếp bảng
    private void sxTheoCot() {
        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tblNV.setRowSorter(sorter);
    }

//    public boolean checkRegex() {
//        if (txtMaNV.getText().trim().isEmpty()) {
//            lblLoiMaNV.setVisible(true);
//            txtMaNV.requestFocus();
//            return false;
//        }
//        if (txtMatKhau.getText().trim().isEmpty()) {
//            lblLoiMatKhau.setVisible(true);
//            txtMatKhau.requestFocus();
//            return false;
//        }
//        if (txtHoTen.getText().trim().isEmpty()) {
//            lblLoiHoTen.setVisible(true);
//            txtHoTen.requestFocus();
//            return false;
//        }
//        if (txtNgaySinh.getText().trim().isEmpty()) {
//            lblLoiNgaySinh.setVisible(true);
//            txtNgaySinh.requestFocus();
//            return false;
//        }
//        if (txtNgayKyHD.getText().trim().isEmpty()) {
//            lblLoiNgayBD.setVisible(true);
//            txtNgayKyHD.requestFocus();
//            return false;
//        }
//        HideError();
//        return true;
//    }
    public void insert() {
        NhanVien nv = getForm();
        try {
            if (nvDao.selectById(nv.getMaNV()) != null) {
                MsgBox.alert(this, "Nhân Viên Đã Tồn Tại Trong CSDL");
            } else {
                nvDao.insert(nv);
                fillTable();
                clearForm();
                setDefault();
                AddLichSuHD.addLSHD("Thêm Nhân Viên");
                MsgBox.alertSuccess(this, "Thêm Mới Thành Công !");
            }
        } catch (Exception e) {
            MsgBox.alertWarning(this, "Upload Hình Ảnh Là Bắt Buộc !");
        }
    }

    public void update() {
        NhanVien nv = getForm();
        String manv = txtMaNV.getText();

        if (manv.equals(Auth.user.getMaNV())) {
            MsgBox.alertInfo(this, "Bạn Không Thể Cập Nhật Chính Bạn !");
            clearForm();
        } else {
            try {
                ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Nhân Viên " + nv.getMaNV() + " Không ?");
                obj.eventOK(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        nvDao.update(nv);
                        fillTable();
                        clearForm();
                        setDefault();
                        AddLichSuHD.addLSHD("Cập Nhật Nhân Viên");
                        btnThem.setEnabled(true);
                        btnSua.setEnabled(false);
                        btnXoa.setEnabled(false);
                        GlassPanePopup.closePopupLast();
                        MsgBox.alertSuccess(new Frame_TTNhanVien(), "Cập Nhật Nhân Viên Thành Công !");
                    }
                });
                GlassPanePopup.showPopup(obj);
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alertSuccess(this, "Cập Nhật Thất Bại");
            }
        }
    }

    public void delete() {
        int a = tblNV.getSelectedRow();
        String manv = tblNV.getValueAt(a, 1).toString();

        if (manv.equals(Auth.user.getMaNV())) {
            MsgBox.alertInfo(this, "Bạn Không Thể Xoá Chính Bạn !");
        } else {
            List<LichSuHoatDong> chk = lsDao.selectAll();
            List<NhanVien> nv = nvDao.selectMaNV(manv);
            List<LichTrinh> lichTrinh = ltDao.selectAll();
            List<HopDong> hopDong = hdDao.selectAll();

            for (NhanVien nhanVien : nv) {
                for (LichTrinh lt : lichTrinh) {
                    if (nhanVien.getMaNV().equals(lt.getMaNV())) {
                        MsgBox.alertWarning(this, "Nhân Viên Đang Thực Hiện Nghiệp Vụ !");
                        return;
                    }
                    if (nhanVien.getHoTen().equals(lt.getHuongHV())) {
                        MsgBox.alertWarning(this, "Hướng Dẫn Viên Đang Thực Hiện Nghiệp Vụ !");
                        return;
                    }
                }

                for (HopDong hd : hopDong) {
                    if (hd.getMaNV().equals(nhanVien.getMaNV())) {
                        MsgBox.alertWarning(this, "Nhân Viên Đang Thực Hiện Nghiệp Vụ !");
                        return;
                    }
                }

                for (LichSuHoatDong lshd : chk) {
                    if (nhanVien.getMaNV().equals(lshd.getMaNV())) {
                        MsgBox.alertWarning(this, "Nhân Viên Đang Thực Hiện Nghiệp Vụ !");
                        return;
                    }
                }

            }

            try {
                ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Nhân Viên " + manv + " Không ?");
                obj.eventOK(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent ae) {
                        nvDao.delete(manv);
                        fillTable();
                        clearForm();
                        AddLichSuHD.addLSHD("Cập Nhật Nhân Viên");
                        btnThem.setEnabled(true);
                        btnSua.setEnabled(false);
                        btnXoa.setEnabled(false);
                        GlassPanePopup.closePopupLast();
                        MsgBox.alertSuccess(new Frame_TTNhanVien(), "Xoá Nhân Viên Thành Công !");
                    }
                });
                GlassPanePopup.showPopup(obj);
            } catch (Exception e) {
                e.printStackTrace();
                MsgBox.alertSuccess(this, "Xoá Thất Bại");
            }
        }

    }

    //hàm này để upload ảnh
    private void UploadImg() {
        JFileChooser chooser = new JFileChooser("C:\\Users\\NHUTLQ\\Downloads\\logos");
        FileNameExtensionFilter filter = new FileNameExtensionFilter("*.IMAGE", "jpg", "png", "gif");
        chooser.addChoosableFileFilter(filter);
        int resulf = chooser.showOpenDialog(this);
        if (resulf == JFileChooser.APPROVE_OPTION) {
            File selectIMG = chooser.getSelectedFile();
            XImage.save(selectIMG);
            String imgPatch = selectIMG.getAbsolutePath();

            try {
                lblImages.setIcon(ResizeImage(imgPatch));
                lblImages.setToolTipText(selectIMG.getName());
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    public void setDefault() {
        String inputString = "";
        byte[] img = inputString.getBytes(StandardCharsets.UTF_8);
        lblImages.setIcon(viewResizeImages(null, img));
    }

    public void fillTable() {
        int i = 1;

        DefaultTableModel model = (DefaultTableModel) tblNV.getModel();
        model.setRowCount(0);

        try {
            NhanVien nhanVien = new NhanVien();
            if (nhanVien != null) {
                String keyWord = txtFind.getText();

                List<NhanVien> list = nvDao.selectByKeyword(keyWord, keyWord, keyWord);
                for (NhanVien nv : list) {
                    Object[] row = {
                        String.valueOf(i++),
                        nv.getMaNV(),
                        nv.getHoTen(),
                        XDate.toString(nv.getNgaySinh(), "dd-MM-yyyy"),
                        XDate.toString(nv.getNgayVaoLam(), "dd-MM-yyyy"),
                        nv.getSDT(),
                        nv.getChucVu(),
                        nv.getTrangThai()
                    };
                    model.addRow(row);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    NhanVien getForm() {
        NhanVien nv = new NhanVien();
        nv.setMaNV(txtMaNV.getText());
        nv.setMatKhau(txtMatKhau.getText());
        nv.setHoTen(txtHoTen.getText());
        nv.setNgaySinh(XDate.toDate(txtNgaySinh.getText(), "dd-MM-yyyy"));
        nv.setSDT(txtSDT.getText());
        nv.setNgayVaoLam(XDate.toDate(txtNgayKyHD.getText(), "dd-MM-yyyy"));
        nv.setTrangThai((String) cboTrangThai.getSelectedItem());
        nv.setImages(lblImages.getToolTipText());
        if (rdo1.isSelected()) {
            nv.setChucVu("Trưởng Phòng");
        }
        if (rdo2.isSelected()) {
            nv.setChucVu("Nhân Viên");
        }
        if (rdo3.isSelected()) {
            nv.setChucVu("Hướng Dẫn Viên");
        }
        return nv;
    }

    public void fillForm(NhanVien nv) {
        int a = tblNV.getSelectedRow();

        txtMaNV.setText(nv.getMaNV());
        txtMatKhau.setText(nv.getMatKhau());
        txtHoTen.setText(nv.getHoTen());
        txtNgayKyHD.setText(XDate.toString(nv.getNgayVaoLam(), "dd-MM-yyyy"));
        cboTrangThai.setSelectedItem(tblNV.getValueAt(a, 7).toString());
        txtNgaySinh.setText(XDate.toString(nv.getNgaySinh(), "dd-MM-yyyy"));
        txtSDT.setText(String.valueOf(nv.getSDT()).trim());

        String url = nv.getImages();
        if (url != null) {
            lblImages.setToolTipText(url);
            ImageIcon icon = XImage.read(url);
            Image img = XImage.resize(icon.getImage(), lblImages.getWidth(), lblImages.getHeight());
            icon = new ImageIcon(img);
            lblImages.setIcon(icon);
        } else {
            lblImages.setIcon(null);
        }

        String chucVu = tblNV.getValueAt(a, 6).toString();
        if (chucVu.equals("Trưởng Phòng")) {
            rdo1.setSelected(true);
        } else if (chucVu.equals("Nhân Viên")) {
            rdo2.setSelected(true);
        } else {
            rdo3.setSelected(true);
        }
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

    public void edit() {
        try {
            String manv = (String) tblNV.getValueAt(row, 1);
            NhanVien nv = nvDao.selectById(manv);
            if (nv != null) {
                fillForm(nv);
                updateStatus();
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);
        boolean first = (this.row == 0);
        boolean last = (this.row == tblNV.getRowCount() - 1);

        txtMaNV.setEditable(!edit);
        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public void first() {
        row = 0;
        tblNV.setRowSelectionInterval(row, row);
        scrollToVisible(tblNV, row, row);
        edit();
        Record();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblNV.setRowSelectionInterval(row, row);
            scrollToVisible(tblNV, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblNV.getRowCount() - 1) {
            this.row++;
            tblNV.setRowSelectionInterval(row, row);
            scrollToVisible(tblNV, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        this.row = tblNV.getRowCount() - 1;
        tblNV.setRowSelectionInterval(row, row);
        scrollToVisible(tblNV, row, row);
        edit();
        Record();
    }

    public void timKiem() {
        txtFind.addEvent(new EventTextField() {
            @Override
            public void onPressed(EventCallBack call) {
                try {
                    for (int i = 1; i <= 100; i++) {
                        lblDem.setText("Test " + i);
                        Thread.sleep(10);
                    }
                    call.done();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {
                fillTable();
                row = -1;
            }
        });
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtMaNV,
            txtMatKhau,
            txtHoTen,
            txtNgaySinh,
            txtNgayKyHD,
            txtSDT
        };
        new DungChung.DungChung().reset(txt);
        setDefault();
        updateStatus();
        setDateInTextFilde();
        HideError();
        lblSTT.setText("0");
        tblNV.getSelectionModel().clearSelection();
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG NHÂN VIÊN", "Nhân viên", tblNV, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG NHÂN VIÊN", tblNV);
    }

    //hàm này để sét kích thước image
    public ImageIcon viewResizeImages(String imgPath, byte[] imgByte) {
        int imagesX = 120;
        int imagesY = 130;
        lblImages.setSize(imagesX, imagesY);

        ImageIcon myIMG;

        if (imgPath != null) {
            myIMG = new ImageIcon(imgPath);
        } else {
            myIMG = new ImageIcon(imgByte);
        }

        Image img = myIMG.getImage();
        Image newImage = img.getScaledInstance(lblImages.getWidth(), lblImages.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnG = new javax.swing.ButtonGroup();
        NgaySinh = new com.raven.datechooser.DateChooser();
        NgayKHD = new com.raven.datechooser.DateChooser();
        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblNV = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtMaNV = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        txtHoTen = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        pnlImages = new UI.pnlQLy();
        lblImages = new javax.swing.JLabel();
        btnTaiAnh = new javax.swing.JButton();
        lblLoiMaNV = new javax.swing.JLabel();
        lblLoiMatKhau = new javax.swing.JLabel();
        lblLoiHoTen = new javax.swing.JLabel();
        txtMatKhau = new javax.swing.JPasswordField();
        jLabel23 = new javax.swing.JLabel();
        lblLoiSDT = new javax.swing.JLabel();
        lblLoiNgaySinh = new javax.swing.JLabel();
        rdo1 = new javax.swing.JRadioButton();
        rdo3 = new javax.swing.JRadioButton();
        btnFrist = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblShow = new javax.swing.JLabel();
        lblHide = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        txtSDT = new javax.swing.JTextField();
        cboTrangThai = new javax.swing.JComboBox<>();
        rdo2 = new javax.swing.JRadioButton();
        txtNgaySinh = new javax.swing.JTextField();
        btnNgaySinh = new javax.swing.JButton();
        txtNgayKyHD = new javax.swing.JTextField();
        btnNgayKHD = new javax.swing.JButton();
        txtFind = new swing.TextFieldAnimation();
        lblDem = new javax.swing.JLabel();
        lblLoiChucVu = new javax.swing.JLabel();
        lblSTT = new javax.swing.JLabel();

        NgaySinh.setTextRefernce(txtNgaySinh);

        NgayKHD.setTextRefernce(txtNgayKyHD);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblNV.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã", "Họ Tên", "Ngày Sinh", "Ngày Ký HĐ", "Số Điện Thoại", "Chức Vụ", "Trạng Thái"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblNV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblNVMouseClicked(evt);
            }
        });
        tblNV.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblNVKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblNV);
        if (tblNV.getColumnModel().getColumnCount() > 0) {
            tblNV.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblNV.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblNV.getColumnModel().getColumn(2).setPreferredWidth(100);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 422, 1145, 280));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN NHÂN VIÊN");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(46, 30, 270, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Nhân Viên");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 80, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Mật Khẩu");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 160, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(190, 79, 60));
        jLabel20.setText("Ngày Sinh");
        QLNV.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 80, -1, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Ngày Ký Hợp Đồng");
        QLNV.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 80, -1, -1));

        jLabel22.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(190, 79, 60));
        jLabel22.setText("Số Điện Thoại");
        QLNV.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 160, -1, -1));

        txtMaNV.setBackground(new java.awt.Color(242, 242, 242));
        txtMaNV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaNV.setForeground(new java.awt.Color(190, 79, 60));
        txtMaNV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtMaNV.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMaNVFocusLost(evt);
            }
        });
        QLNV.add(txtMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 100, 310, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(190, 79, 60));
        jLabel25.setText("Họ Tên ");
        QLNV.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, -1, -1));

        txtHoTen.setBackground(new java.awt.Color(242, 242, 242));
        txtHoTen.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtHoTen.setForeground(new java.awt.Color(190, 79, 60));
        txtHoTen.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtHoTen.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtHoTenFocusLost(evt);
            }
        });
        QLNV.add(txtHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 260, 310, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Chức Vụ");
        QLNV.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 240, -1, -1));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(190, 79, 60));
        jLabel28.setText("Hình");
        QLNV.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 240, -1, -1));

        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonMoi.png"))); // NOI18N
        btnLamMoi.setBorder(null);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setContentAreaFilled(false);
        btnLamMoi.setDefaultCapable(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setFocusable(false);
        btnLamMoi.setIconTextGap(0);
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseExited(evt);
            }
        });
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        QLNV.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(620, 340, -1, -1));

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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 340, -1, -1));

        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonCapNhat.png"))); // NOI18N
        btnSua.setBorder(null);
        btnSua.setBorderPainted(false);
        btnSua.setContentAreaFilled(false);
        btnSua.setDefaultCapable(false);
        btnSua.setFocusPainted(false);
        btnSua.setFocusable(false);
        btnSua.setIconTextGap(0);
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSuaMouseExited(evt);
            }
        });
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        QLNV.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 340, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 340, -1, -1));

        pnlImages.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlImagesLayout = new javax.swing.GroupLayout(pnlImages);
        pnlImages.setLayout(pnlImagesLayout);
        pnlImagesLayout.setHorizontalGroup(
            pnlImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImages, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlImagesLayout.setVerticalGroup(
            pnlImagesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlImagesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblImages, javax.swing.GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
                .addContainerGap())
        );

        QLNV.add(pnlImages, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 260, -1, 150));

        btnTaiAnh.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiAnh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgUploadIMG.png"))); // NOI18N
        btnTaiAnh.setBorder(null);
        btnTaiAnh.setBorderPainted(false);
        btnTaiAnh.setContentAreaFilled(false);
        btnTaiAnh.setDefaultCapable(false);
        btnTaiAnh.setFocusPainted(false);
        btnTaiAnh.setFocusable(false);
        btnTaiAnh.setIconTextGap(0);
        btnTaiAnh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTaiAnhMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTaiAnhMouseExited(evt);
            }
        });
        btnTaiAnh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiAnhActionPerformed(evt);
            }
        });
        QLNV.add(btnTaiAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 340, -1, -1));

        lblLoiMaNV.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiMaNV.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiMaNV.setText("Mã Nhân Viên Không Hợp Lệ !");
        QLNV.add(lblLoiMaNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 130, 170, -1));

        lblLoiMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiMatKhau.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiMatKhau.setText("Mật Khẩu Không Hợp Lệ !");
        QLNV.add(lblLoiMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 210, 170, -1));

        lblLoiHoTen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiHoTen.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiHoTen.setText("Họ Tên Không Hợp Lệ !");
        QLNV.add(lblLoiHoTen, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 290, 170, -1));

        txtMatKhau.setBackground(new java.awt.Color(242, 242, 242));
        txtMatKhau.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMatKhau.setForeground(new java.awt.Color(190, 79, 60));
        txtMatKhau.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtMatKhau.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtMatKhauFocusLost(evt);
            }
        });
        QLNV.add(txtMatKhau, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 180, 310, 30));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(190, 79, 60));
        jLabel23.setText("Trạng Thái");
        QLNV.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 160, -1, -1));

        lblLoiSDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiSDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiSDT.setText("Số Điện Thoại Không Hợp Lệ !");
        QLNV.add(lblLoiSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 210, 170, -1));

        lblLoiNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiNgaySinh.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiNgaySinh.setText("Ngày Sinh Không Hợp Lệ !");
        QLNV.add(lblLoiNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 130, 170, -1));

        btnG.add(rdo1);
        rdo1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdo1.setText("Trưởng Phòng");
        QLNV.add(rdo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 270, -1, -1));

        btnG.add(rdo3);
        rdo3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdo3.setText("Hướng Dẫn Viên");
        QLNV.add(rdo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 270, -1, -1));

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

        lblShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eye.png"))); // NOI18N
        lblShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblShowMousePressed(evt);
            }
        });
        QLNV.add(lblShow, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, -1, -1));

        lblHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eye-crossed.png"))); // NOI18N
        lblHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHideMousePressed(evt);
            }
        });
        QLNV.add(lblHide, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 190, -1, -1));

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

        txtSDT.setBackground(new java.awt.Color(242, 242, 242));
        txtSDT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSDT.setForeground(new java.awt.Color(190, 79, 60));
        txtSDT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtSDT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSDTFocusLost(evt);
            }
        });
        QLNV.add(txtSDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 320, 30));

        cboTrangThai.setBackground(new java.awt.Color(242, 242, 242));
        cboTrangThai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangThai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Đang Làm Việc", "Đang Nghỉ Việc" }));
        cboTrangThai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(cboTrangThai, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 180, 300, 30));

        btnG.add(rdo2);
        rdo2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        rdo2.setText("Nhân Viên");
        QLNV.add(rdo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 270, -1, -1));

        txtNgaySinh.setBackground(new java.awt.Color(242, 242, 242));
        txtNgaySinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgaySinh.setForeground(new java.awt.Color(190, 79, 60));
        txtNgaySinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 100, 320, 30));

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
        QLNV.add(btnNgaySinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 100, 30, 30));

        txtNgayKyHD.setBackground(new java.awt.Color(242, 242, 242));
        txtNgayKyHD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgayKyHD.setForeground(new java.awt.Color(190, 79, 60));
        txtNgayKyHD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtNgayKyHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(910, 100, 270, 30));

        btnNgayKHD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnNgayKHD.setBorder(null);
        btnNgayKHD.setBorderPainted(false);
        btnNgayKHD.setContentAreaFilled(false);
        btnNgayKHD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgayKHD.setDefaultCapable(false);
        btnNgayKHD.setFocusPainted(false);
        btnNgayKHD.setFocusable(false);
        btnNgayKHD.setIconTextGap(0);
        btnNgayKHD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgayKHDActionPerformed(evt);
            }
        });
        QLNV.add(btnNgayKHD, new org.netbeans.lib.awtextra.AbsoluteConstraints(1180, 100, 30, 30));

        txtFind.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindCaretUpdate(evt);
            }
        });
        QLNV.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        lblDem.setText("jLabel1");
        QLNV.add(lblDem, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 30, -1, -1));

        lblLoiChucVu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiChucVu.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiChucVu.setText("Vui lòng chọn chức vụ !");
        QLNV.add(lblLoiChucVu, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 290, 170, 30));

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        QLNV.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 720, 30, -1));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1379, Short.MAX_VALUE)
        );
        pnlCardNhanVienLayout.setVerticalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1517, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 1379, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 138, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 832, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 826, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 6, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThem, "bgButtonThemHover.png");
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        new DungChung.DungChung().hoverButton(2, btnThem, "bgButtonThem.png");
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkRegex()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnSua, "bgButtonCapNhatHover.png");
    }//GEN-LAST:event_btnSuaMouseEntered

    private void btnSuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseExited
        new DungChung.DungChung().hoverButton(2, btnSua, "bgButtonCapNhat.png");
    }//GEN-LAST:event_btnSuaMouseExited

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkRegex()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "bgButtonXoaHover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "bgButtonXoa.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnTaiAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiAnhActionPerformed
        UploadImg();
    }//GEN-LAST:event_btnTaiAnhActionPerformed

    private void btnLamMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseExited
        new DungChung.DungChung().hoverButton(2, btnLamMoi, "bgButtonMoi.png");
    }//GEN-LAST:event_btnLamMoiMouseExited

    private void btnLamMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLamMoi, "bgButtonMoiHover.png");
    }//GEN-LAST:event_btnLamMoiMouseEntered

    private void btnTaiAnhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaiAnhMouseEntered
        new DungChung.DungChung().hoverButton(1, btnTaiAnh, "bgUploadIMG-Hover.png");
    }//GEN-LAST:event_btnTaiAnhMouseEntered

    private void btnTaiAnhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaiAnhMouseExited
        new DungChung.DungChung().hoverButton(2, btnTaiAnh, "bgUploadIMG.png");
    }//GEN-LAST:event_btnTaiAnhMouseExited

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
        txtMaNV.setEditable(true);
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtMaNVFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMaNVFocusLost
        if (!txtMaNV.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtMaNVFocusLost

    private void txtMatKhauFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtMatKhauFocusLost
        if (!txtMatKhau.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtMatKhauFocusLost

    private void txtHoTenFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtHoTenFocusLost
        if (!txtHoTen.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtHoTenFocusLost

    private void tblNVMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblNVMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblNV.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblNVMouseClicked

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

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

    private void lblHideMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHideMousePressed
        this.lblShow.setVisible(true);
        this.lblHide.setVisible(false);
        txtMatKhau.setEchoChar('•');
    }//GEN-LAST:event_lblHideMousePressed

    private void lblShowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShowMousePressed
        this.lblHide.setVisible(true);
        this.lblShow.setVisible(false);
        txtMatKhau.setEchoChar((char) 0);
    }//GEN-LAST:event_lblShowMousePressed

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

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void txtSDTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSDTFocusLost
        if (!txtSDT.getText().trim().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtSDTFocusLost

    private void btnNgaySinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgaySinhActionPerformed
        NgaySinh.showPopup();
    }//GEN-LAST:event_btnNgaySinhActionPerformed

    private void btnNgayKHDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgayKHDActionPerformed
        NgayKHD.showPopup();
    }//GEN-LAST:event_btnNgayKHDActionPerformed

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        fillTable();
        row = -1;
    }//GEN-LAST:event_txtFindCaretUpdate

    private void tblNVKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblNVKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblNV.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblNVKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTNhanVien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTNhanVien().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.datechooser.DateChooser NgayKHD;
    private com.raven.datechooser.DateChooser NgaySinh;
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFrist;
    private javax.swing.ButtonGroup btnG;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNgayKHD;
    private javax.swing.JButton btnNgaySinh;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnTaiAnh;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JComboBox<String> cboTrangThai;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDem;
    private javax.swing.JLabel lblHide;
    private javax.swing.JLabel lblImages;
    private javax.swing.JLabel lblLoiChucVu;
    private javax.swing.JLabel lblLoiHoTen;
    private javax.swing.JLabel lblLoiMaNV;
    private javax.swing.JLabel lblLoiMatKhau;
    private javax.swing.JLabel lblLoiNgaySinh;
    private javax.swing.JLabel lblLoiSDT;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JLabel lblShow;
    private javax.swing.JPanel pnlCardNhanVien;
    private UI.pnlQLy pnlImages;
    private javax.swing.JRadioButton rdo1;
    private javax.swing.JRadioButton rdo2;
    private javax.swing.JRadioButton rdo3;
    private DungChung.Table tblNV;
    private swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtHoTen;
    private javax.swing.JTextField txtMaNV;
    private javax.swing.JPasswordField txtMatKhau;
    private javax.swing.JTextField txtNgayKyHD;
    private javax.swing.JTextField txtNgaySinh;
    private javax.swing.JTextField txtSDT;
    // End of variables declaration//GEN-END:variables

    private ImageIcon ResizeImage(String imgPath) {
        int imgeX = 120;
        int imgeY = 130;
        lblImages.setSize(imgeX, imgeY);

        ImageIcon myImage = new ImageIcon(imgPath);
        Image img = myImage.getImage();
        Image newImage = img.getScaledInstance(lblImages.getWidth(), lblImages.getHeight(), Image.SCALE_SMOOTH);
        ImageIcon image = new ImageIcon(newImage);
        return image;
    }
}
