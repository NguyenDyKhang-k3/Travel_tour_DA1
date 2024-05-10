package UI;

import DAO.LoaiPhuongTienDAO;
import DAO.PhuongTienDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import DungChung.XImage;
import TravelEntity.LoaiPhuongTien;
import TravelEntity.PhuongTien;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.nio.charset.StandardCharsets;
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
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author khang
 */
public class Frame_LoaiPhuongTien extends javax.swing.JFrame {

    PhuongTienDAO ptDao = new PhuongTienDAO();
    LoaiPhuongTienDAO lptDao = new LoaiPhuongTienDAO();
    int row = -1;

    public Frame_LoaiPhuongTien() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        HideError();
        updateStatus();
        setGhiChu();
        fillTable();
        setText();
        txtMaLPT.setEditable(false);
        txtMaLPT.setText(TruyVanSQL.MaLPT());
    }

    public void setText() {
        String[] str = new String[]{
            "Phương Trang",
            "Hoàng Long",
            "Mai Linh Express",
            "Thành Bưởi",
            "Minh Thành Phát",
            "Văn Minh",
            "An Thiên Phúc",
            "Huệ Nghĩa",
            "Hùng Cường",
            "Xe Giường Nằm",
            "Xe Ghế Ngồi",
            "Xe Giường Đôi",
            "Xe Trung Chuyển"
        };
        for (int i = 0; i < str.length; i++) {
            txtLoaiPhuongTien.addItemSuggestion(str[i]);
        }

    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblLoiTenDT
        });
    }

    private boolean check() {
//        boolean flag = true;

        String ten = txtLoaiPhuongTien.getText().trim();
        if (ten.length() < 5 && ten.isEmpty()) {
            lblLoiTenDT.setVisible(true);
            return false;
        }
        return true;
    }

    public void insert() {
        LoaiPhuongTien pt = getForm();
        try {
            lptDao.insert(pt);
            fillTable();
            clearForm();
            txtMaLPT.setText(TruyVanSQL.MaLPT());
            setDefault();
            AddLichSuHD.addLSHD("Thêm Loại Phương Tiện");
            MsgBox.alertSuccess(this, "Thêm Mới Thành Công");
        } catch (Exception e) {
            System.out.println(e);
            MsgBox.alertWarning(this, "Upload Hình Ảnh Là Bắt Buộc !");
        }
    }

    public void update() {
        LoaiPhuongTien pt = getForm();
        String phuongTien = txtMaLPT.getText();

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Mã Loại Phương Tiện ' " + phuongTien + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    lptDao.update(pt);
                    fillTable();
                    clearForm();
                    setGhiChu();
                    setDefault();
                    txtMaLPT.setText(TruyVanSQL.MaLPT());
                    AddLichSuHD.addLSHD("Cập Nhật Loại Phương Tiện");
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_LoaiPhuongTien(), "Cập Nhật Thành Công");

                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Cập Nhật Thất Bại");
        }
    }

    public void delete() {
        int a = tblLPT.getSelectedRow();
        String phuongTien = tblLPT.getValueAt(a, 1).toString();
        List<PhuongTien> chk = ptDao.selectAll();

        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Quyền Thực Hiện Thao Tác");
            return;
        }

        for (PhuongTien pt : chk) {
            if (pt.getMaLoaiPT().equals(tblLPT.getValueAt(a, 1))) {
                MsgBox.alertWarning(this, "Phương Tiện Đang Vận Hành, Không Thể Xoá !");
                return;
            }
        }

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Loại Phương Tiện ' " + phuongTien + " ' Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    lptDao.delete(phuongTien);
                    AddLichSuHD.addLSHD("Xoá Loại Phương Tiện");
                    txtMaLPT.setText(TruyVanSQL.MaLPT());
                    setDefault();
                    fillTable();
                    clearForm();
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertWarning(new Frame_LoaiPhuongTien(), "Xoá Loại Phương Tiện ' " + phuongTien + " Thành Công !");
                    btnThem.setEnabled(true);
                    btnCapNhat.setEnabled(false);
                    btnXoa.setEnabled(false);
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Xoá Thất Bại");
        }
    }

    public void fillTable() {
        int i = 1;

        DefaultTableModel model = (DefaultTableModel) tblLPT.getModel();
        model.setRowCount(0);

        try {
            LoaiPhuongTien pt = new LoaiPhuongTien();
            if (pt != null) {
                String keyWord = txtFind.getText();

                List<LoaiPhuongTien> list = lptDao.selectByKeyword(keyWord, keyWord);
                for (LoaiPhuongTien phuongTien : list) {
                    Object[] row = {
                        String.valueOf(i++),
                        phuongTien.getMaLoaiPT(),
                        phuongTien.getTenLoaiPT(),
                        phuongTien.getGhiChu()
                    };
                    model.addRow(row);
                }
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

    LoaiPhuongTien getForm() {
        LoaiPhuongTien pt = new LoaiPhuongTien();
        pt.setMaLoaiPT(txtMaLPT.getText());
        pt.setTenLoaiPT(txtLoaiPhuongTien.getText());
        pt.setHinh(lblImages.getToolTipText());
        pt.setGhiChu(txtGhiChu.getText());
        return pt;
    }

    public void fillForm(LoaiPhuongTien pt) {
        txtMaLPT.setText(pt.getMaLoaiPT());
        txtLoaiPhuongTien.setText(pt.getTenLoaiPT());
        txtGhiChu.setText(pt.getGhiChu());
        String url = pt.getHinh();
        if (url != null) {
            lblImages.setToolTipText(url);
            ImageIcon icon = XImage.read(url);
            Image img = XImage.resize(icon.getImage(), lblImages.getWidth(), lblImages.getHeight());
            icon = new ImageIcon(img);
            lblImages.setIcon(icon);
        } else {
            lblImages.setIcon(null);
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

    public void edit() {
        try {
            String maTour = (String) tblLPT.getValueAt(row, 1);
            LoaiPhuongTien pt = lptDao.selectById(maTour);
            if (pt != null) {
                fillForm(pt);
                updateStatus();
            }
        } catch (Exception e) {
            System.out.println(e);
            JOptionPane.showMessageDialog(this, "Lỗi Truy Vấn Dữ Liệu !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    public void updateStatus() {
        boolean edit = (this.row >= 0);

        btnThem.setEnabled(!edit);
        btnCapNhat.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public void setDefault() {
        String inputString = "";
        byte[] img = inputString.getBytes(StandardCharsets.UTF_8);
        lblImages.setIcon(viewResizeImages(null, img));
    }

    public void Record() {
        lblSTT.setText(String.valueOf(tblLPT.getValueAt(row, 0)));
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtLoaiPhuongTien,
            txtGhiChu,
            txtGhiChu
        };
        setDefault();
        txtMaLPT.setText(TruyVanSQL.MaLPT());
        tblLPT.getSelectionModel().clearSelection();
        new DungChung.DungChung().reset(txt);
        HideError();
        setGhiChu();
        btnThem.setEnabled(true);
        btnCapNhat.setEnabled(false);
        btnXoa.setEnabled(false);
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
        tblLPT.setRowSelectionInterval(row, row);
        scrollToVisible(tblLPT, row, row);
        edit();
        Record();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblLPT.setRowSelectionInterval(row, row);
            scrollToVisible(tblLPT, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblLPT.getRowCount() - 1) {
            this.row++;
            tblLPT.setRowSelectionInterval(row, row);
            edit();
            scrollToVisible(tblLPT, row, row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        this.row = tblLPT.getRowCount() - 1;
        tblLPT.setRowSelectionInterval(row, row);
        edit();
        scrollToVisible(tblLPT, row, row);
        Record();
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH LOẠI PHƯƠNG TIỆN", "Nhân viên", tblLPT, new int[]{1500, 3000, 8000, 5000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH BẢNG TOUR DU LỊCH", tblLPT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLPT = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtMaLPT = new javax.swing.JTextField();
        btnMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnCapNhat = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        lblLoiTenDT = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        txtGhiChu = new javax.swing.JTextField();
        btnNext = new javax.swing.JButton();
        lblSTT = new javax.swing.JLabel();
        btnPrev = new javax.swing.JButton();
        btnFrist = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        pnlImages = new UI.pnlQLy();
        lblImages = new javax.swing.JLabel();
        btnTaiAnh = new javax.swing.JButton();
        txtFind = new swing.TextFieldAnimation();
        txtLoaiPhuongTien = new ComboBox.TextFieldSuggestion();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setPreferredSize(new java.awt.Dimension(1240, 702));
        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLPT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Loại PT", "Tên Loại PT", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLPT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblLPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLPTMouseClicked(evt);
            }
        });
        tblLPT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblLPTKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblLPT);
        if (tblLPT.getColumnModel().getColumnCount() > 0) {
            tblLPT.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblLPT.getColumnModel().getColumn(1).setPreferredWidth(5);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 280, 1145, 420));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ LOẠI PHƯƠNG TIỆN");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Loại Phương Tiện");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 90, -1, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Tên Loại Phương Tiện");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 90, -1, 20));

        txtMaLPT.setBackground(new java.awt.Color(242, 242, 242));
        txtMaLPT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaLPT.setForeground(new java.awt.Color(190, 79, 60));
        txtMaLPT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtMaLPT, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 110, 290, 30));

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
        QLNV.add(btnMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 200, -1, -1));

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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 200, -1, -1));

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
        QLNV.add(btnCapNhat, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, -1, -1));

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
        QLNV.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, -1, -1));

        lblLoiTenDT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenDT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenDT.setText("Tên Nhà Xe Không Hợp Lệ !");
        QLNV.add(lblLoiTenDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 140, 180, 20));

        jLabel23.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(190, 79, 60));
        jLabel23.setText("Ghi Chú");
        QLNV.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 90, -1, 20));

        txtGhiChu.setBackground(new java.awt.Color(242, 242, 242));
        txtGhiChu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGhiChu.setForeground(new java.awt.Color(190, 79, 60));
        txtGhiChu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        QLNV.add(txtGhiChu, new org.netbeans.lib.awtextra.AbsoluteConstraints(740, 110, 300, 30));

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

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        QLNV.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(158, 720, 30, -1));

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

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(190, 79, 60));
        jLabel28.setText("Ảnh Phương Tiện");
        QLNV.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 90, -1, -1));

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

        QLNV.add(pnlImages, new org.netbeans.lib.awtextra.AbsoluteConstraints(1080, 110, -1, 150));

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
        QLNV.add(btnTaiAnh, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 200, -1, 40));

        txtFind.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindCaretUpdate(evt);
            }
        });
        QLNV.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        txtLoaiPhuongTien.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtLoaiPhuongTien.setForeground(new java.awt.Color(190, 79, 60));
        txtLoaiPhuongTien.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLoaiPhuongTien.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtLoaiPhuongTienFocusLost(evt);
            }
        });
        QLNV.add(txtLoaiPhuongTien, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 110, 310, 30));

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

    private void tblLPTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLPTMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblLPT.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblLPTMouseClicked

    private void btnTaiAnhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaiAnhMouseEntered
        new DungChung.DungChung().hoverButton(1, btnTaiAnh, "bgUploadIMG-Hover.png");
    }//GEN-LAST:event_btnTaiAnhMouseEntered

    private void btnTaiAnhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaiAnhMouseExited
        new DungChung.DungChung().hoverButton(2, btnTaiAnh, "bgUploadIMG.png");
    }//GEN-LAST:event_btnTaiAnhMouseExited

    private void btnTaiAnhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiAnhActionPerformed
        UploadImg();
    }//GEN-LAST:event_btnTaiAnhActionPerformed

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        fillTable();
        row = -1;
    }//GEN-LAST:event_txtFindCaretUpdate

    private void txtLoaiPhuongTienFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtLoaiPhuongTienFocusLost
        if (!txtLoaiPhuongTien.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtLoaiPhuongTienFocusLost

    private void tblLPTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblLPTKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblLPT.getSelectedRow();
            delete();
        }
    }//GEN-LAST:event_tblLPTKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_LoaiPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_LoaiPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_LoaiPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_LoaiPhuongTien.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_LoaiPhuongTien().setVisible(true);
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
    private javax.swing.JButton btnTaiAnh;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblImages;
    private javax.swing.JLabel lblLoiTenDT;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JPanel pnlCardNhanVien;
    private UI.pnlQLy pnlImages;
    private DungChung.Table tblLPT;
    private swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGhiChu;
    private ComboBox.TextFieldSuggestion txtLoaiPhuongTien;
    private javax.swing.JTextField txtMaLPT;
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
}
