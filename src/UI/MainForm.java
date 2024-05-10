package UI;

import DAO.NhanVienDAO;
import DungChung.AddLichSuHD;
import DungChung.Clock;
import DungChung.MsgBox;
import DungChung.XImage;
import TravelEntity.NhanVien;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Desktop;
import static java.awt.Frame.HAND_CURSOR;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author NHUTLQ
 */
public class MainForm extends javax.swing.JFrame {

    int showQLLT = 1;
    int showQLTK = 1;
    int showQLTT = 1;
    int showQLDV = 1;
    NhanVienDAO nvDao = new NhanVienDAO();

    public MainForm() {
        initComponents();
        init();
        GlassPanePopup.installFrame(this);
    }

    public void init() {
        initClock();
        initDate();
        pnlSubMenu.setVisible(false);
        lblTitle.setText(LoginForm.txtUser.getText());
        fillImagesToMainForm();
        Frame_TrangChu tc = new Frame_TrangChu();
        tbpMenu.addTab("", null, tc.getContentPane().getComponentAt(0, 0), null);
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    private void initClock() {
        Clock th = new Clock(lblClock);
        th = new Clock(lblClock);
        th.start();
    }

    private void initDate() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = dateObj.format(formatter);
        lblDate.setText("| " + date);
    }

    private void showHideSubMenuSub(int so, JPanel panel) {
        so++;
        if (so % 2 == 0) {
            pnlSubMenu.setVisible(true);
        } else {
            pnlSubMenu.setVisible(false);
        }
        JPanel[] pnl = new JPanel[]{pnlSubQLDV, pnlSubTaiKhoan, pnlSubLichTrinh, pnlSubQLThanhToan};
        for (JPanel pnl1 : pnl) {
            pnl1.setVisible(false);
        }
        panel.setVisible(true);
    }

    private void fillImagesToMainForm() {
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            String url = nhanVien.getImages();
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
    }

    private void openHuongDan() {
        try {
            Desktop.getDesktop().browse(new File("HDSD/index.html").toURI());
            AddLichSuHD.addLSHD("Truy Cập Vào Hướng Dẫn");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Không Tìm Thấy File Hướng Dẫn !", "Thông Báo", JOptionPane.DEFAULT_OPTION);
        }
    }

    private void HoverButton(int so, JPanel pnl) {
        if (so == 1) {
            pnl.setBackground(new Color(153, 51, 0));
            pnl.setCursor(new Cursor(HAND_CURSOR));
        } else {
            pnl.setBackground(new Color(190, 79, 60));
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlSubMenu = new UI.pnlQLy();
        pnlSubLichTrinh = new javax.swing.JPanel();
        pnlTTLT = new UI.pnlQLy();
        btnTTLT = new javax.swing.JButton();
        pnlLTKH = new UI.pnlQLy();
        btnLTKH = new javax.swing.JButton();
        pnlLTKS = new UI.pnlQLy();
        btnLTKS = new javax.swing.JButton();
        pnlLTDD = new UI.pnlQLy();
        btnLTDD = new javax.swing.JButton();
        pnlLTPT = new UI.pnlQLy();
        btnLTPT = new javax.swing.JButton();
        pnlLTDChuyen = new UI.pnlQLy();
        btnLTDchuyen = new javax.swing.JButton();
        pnlSubQLDV = new javax.swing.JPanel();
        pnlKhachSan = new UI.pnlQLy();
        btnKhachSan = new javax.swing.JButton();
        pnlThamQuan = new UI.pnlQLy();
        btnThamQuan = new javax.swing.JButton();
        pnlLoaiPhuongTien = new UI.pnlQLy();
        btnLoaiPt = new javax.swing.JButton();
        pnlPhuongTien = new UI.pnlQLy();
        btnPhuongTien = new javax.swing.JButton();
        pnlSubQLThanhToan = new javax.swing.JPanel();
        pnlQLHopDong = new UI.pnlQLy();
        btnQLHopDong = new javax.swing.JButton();
        pnlQLHoaDon = new UI.pnlQLy();
        btnQLHoaDon = new javax.swing.JButton();
        pnlSubTaiKhoan = new javax.swing.JPanel();
        pnlTTNhanVien = new UI.pnlQLy();
        btnTTNhanVien = new javax.swing.JButton();
        pnlLSHoatDong = new UI.pnlQLy();
        btnLSHoatDong = new javax.swing.JButton();
        pnlThongKe = new UI.pnlQLy();
        btnThongKe = new javax.swing.JButton();
        pnlTop = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblTravelTour = new javax.swing.JLabel();
        lblTitle = new javax.swing.JLabel();
        btnThoat = new javax.swing.JButton();
        btnMini = new javax.swing.JButton();
        btnHoTro = new javax.swing.JButton();
        pnlParent = new javax.swing.JPanel();
        pnlCongCu = new javax.swing.JPanel();
        pnlImages = new UI.pnlQLy();
        lblImages = new javax.swing.JLabel();
        lblClock = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblKey = new javax.swing.JLabel();
        pnlTrangchu = new javax.swing.JPanel();
        btnTrangchu = new javax.swing.JButton();
        pnlQLTour = new javax.swing.JPanel();
        btnQLTour = new javax.swing.JButton();
        pnlQLKHang = new javax.swing.JPanel();
        btnQLKhang = new javax.swing.JButton();
        pnlQLDV = new javax.swing.JPanel();
        btnQLDichVu = new javax.swing.JButton();
        pnlDangXuat = new javax.swing.JPanel();
        btnDangxuat = new javax.swing.JButton();
        pnlDoiMK = new javax.swing.JPanel();
        btnDoiMK = new javax.swing.JButton();
        pnlTK = new javax.swing.JPanel();
        btnTaiKhoan = new javax.swing.JButton();
        pnlQLThanhToan = new javax.swing.JPanel();
        btnQLThanhToan = new javax.swing.JButton();
        pnlQLLichTrinh = new javax.swing.JPanel();
        btnQLLichTrinh = new javax.swing.JButton();
        pnlRight = new javax.swing.JPanel();
        tbpMenu = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1536, 824));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlSubMenu.setBackground(new java.awt.Color(190, 79, 60));
        pnlSubMenu.setPreferredSize(new java.awt.Dimension(230, 260));
        pnlSubMenu.setLayout(new java.awt.CardLayout());

        pnlSubLichTrinh.setBackground(new java.awt.Color(190, 79, 60));
        pnlSubLichTrinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSubLichTrinhMouseExited(evt);
            }
        });

        pnlTTLT.setBackground(new java.awt.Color(190, 79, 60));
        pnlTTLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTTLTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTTLTMouseExited(evt);
            }
        });

        btnTTLT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTTLT.setForeground(new java.awt.Color(255, 255, 255));
        btnTTLT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/KhachSan.png"))); // NOI18N
        btnTTLT.setText("Thông Tin Lịch Trình");
        btnTTLT.setBorder(null);
        btnTTLT.setBorderPainted(false);
        btnTTLT.setContentAreaFilled(false);
        btnTTLT.setDefaultCapable(false);
        btnTTLT.setFocusPainted(false);
        btnTTLT.setFocusable(false);
        btnTTLT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTTLT.setIconTextGap(13);
        btnTTLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTTLTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTTLTMouseExited(evt);
            }
        });
        btnTTLT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTTLTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTTLTLayout = new javax.swing.GroupLayout(pnlTTLT);
        pnlTTLT.setLayout(pnlTTLTLayout);
        pnlTTLTLayout.setHorizontalGroup(
            pnlTTLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTTLTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTTLT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTTLTLayout.setVerticalGroup(
            pnlTTLTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTTLTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTTLT, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlLTKH.setBackground(new java.awt.Color(190, 79, 60));
        pnlLTKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLTKHMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLTKHMouseExited(evt);
            }
        });

        btnLTKH.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLTKH.setForeground(new java.awt.Color(255, 255, 255));
        btnLTKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/road.png"))); // NOI18N
        btnLTKH.setText("Khách Hàng Theo Lịch Trình");
        btnLTKH.setBorder(null);
        btnLTKH.setBorderPainted(false);
        btnLTKH.setContentAreaFilled(false);
        btnLTKH.setDefaultCapable(false);
        btnLTKH.setFocusPainted(false);
        btnLTKH.setFocusable(false);
        btnLTKH.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLTKH.setIconTextGap(13);
        btnLTKH.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLTKHMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLTKHMouseExited(evt);
            }
        });
        btnLTKH.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLTKHActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLTKHLayout = new javax.swing.GroupLayout(pnlLTKH);
        pnlLTKH.setLayout(pnlLTKHLayout);
        pnlLTKHLayout.setHorizontalGroup(
            pnlLTKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLTKHLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLTKHLayout.setVerticalGroup(
            pnlLTKHLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLTKHLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLTKH, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlLTKS.setBackground(new java.awt.Color(190, 79, 60));
        pnlLTKS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLTKSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLTKSMouseExited(evt);
            }
        });

        btnLTKS.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLTKS.setForeground(new java.awt.Color(255, 255, 255));
        btnLTKS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/car-building.png"))); // NOI18N
        btnLTKS.setText("Khách Sạn Theo Lịch Trình");
        btnLTKS.setBorder(null);
        btnLTKS.setBorderPainted(false);
        btnLTKS.setContentAreaFilled(false);
        btnLTKS.setDefaultCapable(false);
        btnLTKS.setFocusPainted(false);
        btnLTKS.setFocusable(false);
        btnLTKS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLTKS.setIconTextGap(13);
        btnLTKS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLTKSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLTKSMouseExited(evt);
            }
        });
        btnLTKS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLTKSActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLTKSLayout = new javax.swing.GroupLayout(pnlLTKS);
        pnlLTKS.setLayout(pnlLTKSLayout);
        pnlLTKSLayout.setHorizontalGroup(
            pnlLTKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLTKSLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTKS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLTKSLayout.setVerticalGroup(
            pnlLTKSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLTKSLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLTKS, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlLTDD.setBackground(new java.awt.Color(190, 79, 60));
        pnlLTDD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLTDDMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLTDDMouseExited(evt);
            }
        });

        btnLTDD.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLTDD.setForeground(new java.awt.Color(255, 255, 255));
        btnLTDD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bench-tree.png"))); // NOI18N
        btnLTDD.setText("Địa Điểm Theo Lịch Trình");
        btnLTDD.setBorder(null);
        btnLTDD.setBorderPainted(false);
        btnLTDD.setContentAreaFilled(false);
        btnLTDD.setDefaultCapable(false);
        btnLTDD.setFocusPainted(false);
        btnLTDD.setFocusable(false);
        btnLTDD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLTDD.setIconTextGap(13);
        btnLTDD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLTDDMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLTDDMouseExited(evt);
            }
        });
        btnLTDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLTDDActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLTDDLayout = new javax.swing.GroupLayout(pnlLTDD);
        pnlLTDD.setLayout(pnlLTDDLayout);
        pnlLTDDLayout.setHorizontalGroup(
            pnlLTDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLTDDLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTDD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLTDDLayout.setVerticalGroup(
            pnlLTDDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLTDDLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLTDD, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlLTPT.setBackground(new java.awt.Color(190, 79, 60));
        pnlLTPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLTPTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLTPTMouseExited(evt);
            }
        });

        btnLTPT.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLTPT.setForeground(new java.awt.Color(255, 255, 255));
        btnLTPT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ThueTau.png"))); // NOI18N
        btnLTPT.setText("Phương Tiện Theo Lịch Trình");
        btnLTPT.setBorder(null);
        btnLTPT.setBorderPainted(false);
        btnLTPT.setContentAreaFilled(false);
        btnLTPT.setDefaultCapable(false);
        btnLTPT.setFocusPainted(false);
        btnLTPT.setFocusable(false);
        btnLTPT.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLTPT.setIconTextGap(13);
        btnLTPT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLTPTMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLTPTMouseExited(evt);
            }
        });
        btnLTPT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLTPTActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLTPTLayout = new javax.swing.GroupLayout(pnlLTPT);
        pnlLTPT.setLayout(pnlLTPTLayout);
        pnlLTPTLayout.setHorizontalGroup(
            pnlLTPTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLTPTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLTPTLayout.setVerticalGroup(
            pnlLTPTLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLTPTLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTPT, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlLTDChuyen.setBackground(new java.awt.Color(190, 79, 60));
        pnlLTDChuyen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLTDChuyenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLTDChuyenMouseExited(evt);
            }
        });

        btnLTDchuyen.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLTDchuyen.setForeground(new java.awt.Color(255, 255, 255));
        btnLTDchuyen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bus-alt.png"))); // NOI18N
        btnLTDchuyen.setText("Lịch Trình Di Chuyển");
        btnLTDchuyen.setBorder(null);
        btnLTDchuyen.setBorderPainted(false);
        btnLTDchuyen.setContentAreaFilled(false);
        btnLTDchuyen.setDefaultCapable(false);
        btnLTDchuyen.setFocusPainted(false);
        btnLTDchuyen.setFocusable(false);
        btnLTDchuyen.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLTDchuyen.setIconTextGap(13);
        btnLTDchuyen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLTDchuyenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLTDchuyenMouseExited(evt);
            }
        });
        btnLTDchuyen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLTDchuyenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLTDChuyenLayout = new javax.swing.GroupLayout(pnlLTDChuyen);
        pnlLTDChuyen.setLayout(pnlLTDChuyenLayout);
        pnlLTDChuyenLayout.setHorizontalGroup(
            pnlLTDChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLTDChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTDchuyen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLTDChuyenLayout.setVerticalGroup(
            pnlLTDChuyenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLTDChuyenLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLTDchuyen, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlSubLichTrinhLayout = new javax.swing.GroupLayout(pnlSubLichTrinh);
        pnlSubLichTrinh.setLayout(pnlSubLichTrinhLayout);
        pnlSubLichTrinhLayout.setHorizontalGroup(
            pnlSubLichTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubLichTrinhLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubLichTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlLTKH, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLTKS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLTDD, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLTPT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlTTLT, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLTDChuyen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSubLichTrinhLayout.setVerticalGroup(
            pnlSubLichTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSubLichTrinhLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlTTLT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLTKH, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLTKS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLTDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLTPT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLTDChuyen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSubMenu.add(pnlSubLichTrinh, "pnlLT");

        pnlSubQLDV.setBackground(new java.awt.Color(190, 79, 60));
        pnlSubQLDV.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSubQLDVMouseExited(evt);
            }
        });

        pnlKhachSan.setBackground(new java.awt.Color(190, 79, 60));
        pnlKhachSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlKhachSanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlKhachSanMouseExited(evt);
            }
        });

        btnKhachSan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnKhachSan.setForeground(new java.awt.Color(255, 255, 255));
        btnKhachSan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/TrangChu.png"))); // NOI18N
        btnKhachSan.setText("Khách Sạn - HomeStay");
        btnKhachSan.setBorder(null);
        btnKhachSan.setBorderPainted(false);
        btnKhachSan.setContentAreaFilled(false);
        btnKhachSan.setDefaultCapable(false);
        btnKhachSan.setFocusPainted(false);
        btnKhachSan.setFocusable(false);
        btnKhachSan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnKhachSan.setIconTextGap(13);
        btnKhachSan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnKhachSanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnKhachSanMouseExited(evt);
            }
        });
        btnKhachSan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKhachSanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlKhachSanLayout = new javax.swing.GroupLayout(pnlKhachSan);
        pnlKhachSan.setLayout(pnlKhachSanLayout);
        pnlKhachSanLayout.setHorizontalGroup(
            pnlKhachSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlKhachSanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnKhachSan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlKhachSanLayout.setVerticalGroup(
            pnlKhachSanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlKhachSanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnKhachSan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlThamQuan.setBackground(new java.awt.Color(190, 79, 60));
        pnlThamQuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlThamQuanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlThamQuanMouseExited(evt);
            }
        });

        btnThamQuan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThamQuan.setForeground(new java.awt.Color(255, 255, 255));
        btnThamQuan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/escalator.png"))); // NOI18N
        btnThamQuan.setText("Địa Điểm Tham Quan");
        btnThamQuan.setBorder(null);
        btnThamQuan.setBorderPainted(false);
        btnThamQuan.setContentAreaFilled(false);
        btnThamQuan.setDefaultCapable(false);
        btnThamQuan.setFocusPainted(false);
        btnThamQuan.setFocusable(false);
        btnThamQuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThamQuan.setIconTextGap(13);
        btnThamQuan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThamQuanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThamQuanMouseExited(evt);
            }
        });
        btnThamQuan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThamQuanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThamQuanLayout = new javax.swing.GroupLayout(pnlThamQuan);
        pnlThamQuan.setLayout(pnlThamQuanLayout);
        pnlThamQuanLayout.setHorizontalGroup(
            pnlThamQuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThamQuanLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThamQuan, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlThamQuanLayout.setVerticalGroup(
            pnlThamQuanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThamQuanLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnThamQuan, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlLoaiPhuongTien.setBackground(new java.awt.Color(190, 79, 60));
        pnlLoaiPhuongTien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLoaiPhuongTienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLoaiPhuongTienMouseExited(evt);
            }
        });

        btnLoaiPt.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLoaiPt.setForeground(new java.awt.Color(255, 255, 255));
        btnLoaiPt.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/charging-station.png"))); // NOI18N
        btnLoaiPt.setText("Loại Phương Tiện");
        btnLoaiPt.setBorder(null);
        btnLoaiPt.setBorderPainted(false);
        btnLoaiPt.setContentAreaFilled(false);
        btnLoaiPt.setDefaultCapable(false);
        btnLoaiPt.setFocusPainted(false);
        btnLoaiPt.setFocusable(false);
        btnLoaiPt.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLoaiPt.setIconTextGap(13);
        btnLoaiPt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoaiPtMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoaiPtMouseExited(evt);
            }
        });
        btnLoaiPt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoaiPtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLoaiPhuongTienLayout = new javax.swing.GroupLayout(pnlLoaiPhuongTien);
        pnlLoaiPhuongTien.setLayout(pnlLoaiPhuongTienLayout);
        pnlLoaiPhuongTienLayout.setHorizontalGroup(
            pnlLoaiPhuongTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLoaiPhuongTienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLoaiPt, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLoaiPhuongTienLayout.setVerticalGroup(
            pnlLoaiPhuongTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLoaiPhuongTienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnLoaiPt, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlPhuongTien.setBackground(new java.awt.Color(190, 79, 60));
        pnlPhuongTien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlPhuongTienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlPhuongTienMouseExited(evt);
            }
        });

        btnPhuongTien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnPhuongTien.setForeground(new java.awt.Color(255, 255, 255));
        btnPhuongTien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/school-bus.png"))); // NOI18N
        btnPhuongTien.setText("Phương Tiện");
        btnPhuongTien.setBorder(null);
        btnPhuongTien.setBorderPainted(false);
        btnPhuongTien.setContentAreaFilled(false);
        btnPhuongTien.setDefaultCapable(false);
        btnPhuongTien.setFocusPainted(false);
        btnPhuongTien.setFocusable(false);
        btnPhuongTien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnPhuongTien.setIconTextGap(13);
        btnPhuongTien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPhuongTienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPhuongTienMouseExited(evt);
            }
        });
        btnPhuongTien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPhuongTienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlPhuongTienLayout = new javax.swing.GroupLayout(pnlPhuongTien);
        pnlPhuongTien.setLayout(pnlPhuongTienLayout);
        pnlPhuongTienLayout.setHorizontalGroup(
            pnlPhuongTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlPhuongTienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnPhuongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlPhuongTienLayout.setVerticalGroup(
            pnlPhuongTienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlPhuongTienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPhuongTien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlSubQLDVLayout = new javax.swing.GroupLayout(pnlSubQLDV);
        pnlSubQLDV.setLayout(pnlSubQLDVLayout);
        pnlSubQLDVLayout.setHorizontalGroup(
            pnlSubQLDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubQLDVLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubQLDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlKhachSan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlThamQuan, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLoaiPhuongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlPhuongTien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSubQLDVLayout.setVerticalGroup(
            pnlSubQLDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubQLDVLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlKhachSan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlThamQuan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLoaiPhuongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlPhuongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        pnlSubMenu.add(pnlSubQLDV, "pnlQLDV");

        pnlSubQLThanhToan.setBackground(new java.awt.Color(190, 79, 60));
        pnlSubQLThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSubQLThanhToanMouseExited(evt);
            }
        });

        pnlQLHopDong.setBackground(new java.awt.Color(190, 79, 60));
        pnlQLHopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlQLHopDongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlQLHopDongMouseExited(evt);
            }
        });

        btnQLHopDong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLHopDong.setForeground(new java.awt.Color(255, 255, 255));
        btnQLHopDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/layer-plus.png"))); // NOI18N
        btnQLHopDong.setText("Quản Lý Hợp Đồng");
        btnQLHopDong.setBorder(null);
        btnQLHopDong.setBorderPainted(false);
        btnQLHopDong.setContentAreaFilled(false);
        btnQLHopDong.setDefaultCapable(false);
        btnQLHopDong.setFocusPainted(false);
        btnQLHopDong.setFocusable(false);
        btnQLHopDong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLHopDong.setIconTextGap(13);
        btnQLHopDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLHopDongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLHopDongMouseExited(evt);
            }
        });
        btnQLHopDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLHopDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLHopDongLayout = new javax.swing.GroupLayout(pnlQLHopDong);
        pnlQLHopDong.setLayout(pnlQLHopDongLayout);
        pnlQLHopDongLayout.setHorizontalGroup(
            pnlQLHopDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHopDongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnQLHopDong, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlQLHopDongLayout.setVerticalGroup(
            pnlQLHopDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLHopDongLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnQLHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlQLHoaDon.setBackground(new java.awt.Color(190, 79, 60));
        pnlQLHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlQLHoaDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlQLHoaDonMouseExited(evt);
            }
        });

        btnQLHoaDon.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLHoaDon.setForeground(new java.awt.Color(255, 255, 255));
        btnQLHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bank.png"))); // NOI18N
        btnQLHoaDon.setText("Quản Lý Hoá Đơn");
        btnQLHoaDon.setBorder(null);
        btnQLHoaDon.setBorderPainted(false);
        btnQLHoaDon.setContentAreaFilled(false);
        btnQLHoaDon.setDefaultCapable(false);
        btnQLHoaDon.setFocusPainted(false);
        btnQLHoaDon.setFocusable(false);
        btnQLHoaDon.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLHoaDon.setIconTextGap(13);
        btnQLHoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLHoaDonMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLHoaDonMouseExited(evt);
            }
        });
        btnQLHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLHoaDonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLHoaDonLayout = new javax.swing.GroupLayout(pnlQLHoaDon);
        pnlQLHoaDon.setLayout(pnlQLHoaDonLayout);
        pnlQLHoaDonLayout.setHorizontalGroup(
            pnlQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlQLHoaDonLayout.setVerticalGroup(
            pnlQLHoaDonLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQLHoaDonLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnQLHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSubQLThanhToanLayout = new javax.swing.GroupLayout(pnlSubQLThanhToan);
        pnlSubQLThanhToan.setLayout(pnlSubQLThanhToanLayout);
        pnlSubQLThanhToanLayout.setHorizontalGroup(
            pnlSubQLThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubQLThanhToanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubQLThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlQLHopDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlQLHoaDon, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSubQLThanhToanLayout.setVerticalGroup(
            pnlSubQLThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubQLThanhToanLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlQLHopDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlQLHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(143, Short.MAX_VALUE))
        );

        pnlSubMenu.add(pnlSubQLThanhToan, "pnlThanhToan");

        pnlSubTaiKhoan.setBackground(new java.awt.Color(190, 79, 60));
        pnlSubTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlSubTaiKhoanMouseExited(evt);
            }
        });

        pnlTTNhanVien.setBackground(new java.awt.Color(190, 79, 60));
        pnlTTNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlTTNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlTTNhanVienMouseExited(evt);
            }
        });

        btnTTNhanVien.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTTNhanVien.setForeground(new java.awt.Color(255, 255, 255));
        btnTTNhanVien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/NhanVien.png"))); // NOI18N
        btnTTNhanVien.setText("Thông Tin Nhân Viên");
        btnTTNhanVien.setBorder(null);
        btnTTNhanVien.setBorderPainted(false);
        btnTTNhanVien.setContentAreaFilled(false);
        btnTTNhanVien.setDefaultCapable(false);
        btnTTNhanVien.setFocusPainted(false);
        btnTTNhanVien.setFocusable(false);
        btnTTNhanVien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTTNhanVien.setIconTextGap(13);
        btnTTNhanVien.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTTNhanVienMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTTNhanVienMouseExited(evt);
            }
        });
        btnTTNhanVien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTTNhanVienActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTTNhanVienLayout = new javax.swing.GroupLayout(pnlTTNhanVien);
        pnlTTNhanVien.setLayout(pnlTTNhanVienLayout);
        pnlTTNhanVienLayout.setHorizontalGroup(
            pnlTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTTNhanVienLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnTTNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTTNhanVienLayout.setVerticalGroup(
            pnlTTNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTTNhanVienLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnTTNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pnlLSHoatDong.setBackground(new java.awt.Color(190, 79, 60));
        pnlLSHoatDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlLSHoatDongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlLSHoatDongMouseExited(evt);
            }
        });

        btnLSHoatDong.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnLSHoatDong.setForeground(new java.awt.Color(255, 255, 255));
        btnLSHoatDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/time-past.png"))); // NOI18N
        btnLSHoatDong.setText("Lịch Sử Hoạt Động");
        btnLSHoatDong.setBorder(null);
        btnLSHoatDong.setBorderPainted(false);
        btnLSHoatDong.setContentAreaFilled(false);
        btnLSHoatDong.setDefaultCapable(false);
        btnLSHoatDong.setFocusPainted(false);
        btnLSHoatDong.setFocusable(false);
        btnLSHoatDong.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnLSHoatDong.setIconTextGap(13);
        btnLSHoatDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLSHoatDongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLSHoatDongMouseExited(evt);
            }
        });
        btnLSHoatDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLSHoatDongActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlLSHoatDongLayout = new javax.swing.GroupLayout(pnlLSHoatDong);
        pnlLSHoatDong.setLayout(pnlLSHoatDongLayout);
        pnlLSHoatDongLayout.setHorizontalGroup(
            pnlLSHoatDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlLSHoatDongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLSHoatDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlLSHoatDongLayout.setVerticalGroup(
            pnlLSHoatDongLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlLSHoatDongLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnLSHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlThongKe.setBackground(new java.awt.Color(190, 79, 60));
        pnlThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pnlThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pnlThongKeMouseExited(evt);
            }
        });

        btnThongKe.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnThongKe.setForeground(new java.awt.Color(255, 255, 255));
        btnThongKe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/bank.png"))); // NOI18N
        btnThongKe.setText("Thống Kê - Doanh Thu");
        btnThongKe.setBorder(null);
        btnThongKe.setBorderPainted(false);
        btnThongKe.setContentAreaFilled(false);
        btnThongKe.setDefaultCapable(false);
        btnThongKe.setFocusPainted(false);
        btnThongKe.setFocusable(false);
        btnThongKe.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnThongKe.setIconTextGap(13);
        btnThongKe.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThongKeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThongKeMouseExited(evt);
            }
        });
        btnThongKe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThongKeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlThongKeLayout = new javax.swing.GroupLayout(pnlThongKe);
        pnlThongKe.setLayout(pnlThongKeLayout);
        pnlThongKeLayout.setHorizontalGroup(
            pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlThongKeLayout.setVerticalGroup(
            pnlThongKeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlThongKeLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlSubTaiKhoanLayout = new javax.swing.GroupLayout(pnlSubTaiKhoan);
        pnlSubTaiKhoan.setLayout(pnlSubTaiKhoanLayout);
        pnlSubTaiKhoanLayout.setHorizontalGroup(
            pnlSubTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubTaiKhoanLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSubTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTTNhanVien, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlThongKe, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlLSHoatDong, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlSubTaiKhoanLayout.setVerticalGroup(
            pnlSubTaiKhoanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSubTaiKhoanLayout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(pnlTTNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlLSHoatDong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlThongKe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(89, Short.MAX_VALUE))
        );

        pnlSubMenu.add(pnlSubTaiKhoan, "pnlTK");

        getContentPane().add(pnlSubMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(249, 376, 290, 400));

        jPanel2.setBackground(new java.awt.Color(210, 77, 66));

        lblTravelTour.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTravelTour.setForeground(new java.awt.Color(255, 255, 255));
        lblTravelTour.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/icon-plan.png"))); // NOI18N
        lblTravelTour.setText(" TravelTour  - ");
        lblTravelTour.setPreferredSize(new java.awt.Dimension(200, 15));

        lblTitle.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitle.setForeground(new java.awt.Color(255, 255, 255));
        lblTitle.setText("Admin");
        lblTitle.setPreferredSize(new java.awt.Dimension(200, 15));

        btnThoat.setBackground(new java.awt.Color(210, 77, 66));
        btnThoat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonClose.png"))); // NOI18N
        btnThoat.setBorder(null);
        btnThoat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThoatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThoatMouseExited(evt);
            }
        });
        btnThoat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThoatActionPerformed(evt);
            }
        });

        btnMini.setBackground(new java.awt.Color(210, 77, 66));
        btnMini.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/minus.png"))); // NOI18N
        btnMini.setBorder(null);
        btnMini.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnMiniMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnMiniMouseExited(evt);
            }
        });
        btnMini.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMiniActionPerformed(evt);
            }
        });

        btnHoTro.setBackground(new java.awt.Color(210, 77, 66));
        btnHoTro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/interrogation.png"))); // NOI18N
        btnHoTro.setBorder(null);
        btnHoTro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHoTroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHoTroMouseExited(evt);
            }
        });
        btnHoTro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHoTroActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(lblTravelTour, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lblTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1089, 1089, 1089)
                .addComponent(btnHoTro, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btnMini, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(7, 7, 7)
                .addComponent(btnThoat, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblTravelTour, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnThoat, javax.swing.GroupLayout.DEFAULT_SIZE, 40, Short.MAX_VALUE)
            .addComponent(btnMini, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(btnHoTro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout pnlTopLayout = new javax.swing.GroupLayout(pnlTop);
        pnlTop.setLayout(pnlTopLayout);
        pnlTopLayout.setHorizontalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        pnlTopLayout.setVerticalGroup(
            pnlTopLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        getContentPane().add(pnlTop, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        pnlParent.setBackground(new java.awt.Color(255, 255, 255));
        pnlParent.setPreferredSize(new java.awt.Dimension(1536, 780));
        pnlParent.setLayout(new java.awt.BorderLayout());

        pnlCongCu.setBackground(new java.awt.Color(190, 79, 60));
        pnlCongCu.setPreferredSize(new java.awt.Dimension(250, 800));
        pnlCongCu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

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
                .addComponent(lblImages, javax.swing.GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlCongCu.add(pnlImages, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 160));

        lblClock.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblClock.setForeground(new java.awt.Color(255, 255, 255));
        lblClock.setText("11:00:00 PM");
        pnlCongCu.add(lblClock, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 720, -1, -1));

        lblDate.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("  01/01/2002");
        pnlCongCu.add(lblDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 720, -1, -1));

        lblKey.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblKey.setForeground(new java.awt.Color(255, 255, 255));
        lblKey.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblKey.setText("Design By: Travel Tour");
        pnlCongCu.add(lblKey, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 750, 250, -1));

        pnlTrangchu.setBackground(new java.awt.Color(190, 79, 60));

        btnTrangchu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTrangchu.setForeground(new java.awt.Color(255, 255, 255));
        btnTrangchu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/TrangChu.png"))); // NOI18N
        btnTrangchu.setText("Trang Chủ");
        btnTrangchu.setBorder(null);
        btnTrangchu.setBorderPainted(false);
        btnTrangchu.setContentAreaFilled(false);
        btnTrangchu.setDefaultCapable(false);
        btnTrangchu.setFocusPainted(false);
        btnTrangchu.setFocusable(false);
        btnTrangchu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTrangchu.setIconTextGap(13);
        btnTrangchu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTrangchuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTrangchuMouseExited(evt);
            }
        });
        btnTrangchu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTrangchuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTrangchuLayout = new javax.swing.GroupLayout(pnlTrangchu);
        pnlTrangchu.setLayout(pnlTrangchuLayout);
        pnlTrangchuLayout.setHorizontalGroup(
            pnlTrangchuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTrangchuLayout.createSequentialGroup()
                .addContainerGap(25, Short.MAX_VALUE)
                .addComponent(btnTrangchu, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTrangchuLayout.setVerticalGroup(
            pnlTrangchuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTrangchu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlTrangchu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 200, 250, 50));

        pnlQLTour.setBackground(new java.awt.Color(190, 79, 60));

        btnQLTour.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLTour.setForeground(new java.awt.Color(255, 255, 255));
        btnQLTour.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Tour.png"))); // NOI18N
        btnQLTour.setText("Thông Tin Tour");
        btnQLTour.setBorder(null);
        btnQLTour.setBorderPainted(false);
        btnQLTour.setContentAreaFilled(false);
        btnQLTour.setDefaultCapable(false);
        btnQLTour.setFocusPainted(false);
        btnQLTour.setFocusable(false);
        btnQLTour.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLTour.setIconTextGap(13);
        btnQLTour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLTourMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLTourMouseExited(evt);
            }
        });
        btnQLTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLTourActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLTourLayout = new javax.swing.GroupLayout(pnlQLTour);
        pnlQLTour.setLayout(pnlQLTourLayout);
        pnlQLTourLayout.setHorizontalGroup(
            pnlQLTourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLTourLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(btnQLTour, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlQLTourLayout.setVerticalGroup(
            pnlQLTourLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLTour, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlQLTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 250, -1, 50));

        pnlQLKHang.setBackground(new java.awt.Color(190, 79, 60));

        btnQLKhang.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLKhang.setForeground(new java.awt.Color(255, 255, 255));
        btnQLKhang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/NameKH.png"))); // NOI18N
        btnQLKhang.setText("Thông Tin Khách Hàng");
        btnQLKhang.setBorder(null);
        btnQLKhang.setBorderPainted(false);
        btnQLKhang.setContentAreaFilled(false);
        btnQLKhang.setDefaultCapable(false);
        btnQLKhang.setFocusPainted(false);
        btnQLKhang.setFocusable(false);
        btnQLKhang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLKhang.setIconTextGap(13);
        btnQLKhang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLKhangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLKhangMouseExited(evt);
            }
        });
        btnQLKhang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLKhangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLKHangLayout = new javax.swing.GroupLayout(pnlQLKHang);
        pnlQLKHang.setLayout(pnlQLKHangLayout);
        pnlQLKHangLayout.setHorizontalGroup(
            pnlQLKHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLKHangLayout.createSequentialGroup()
                .addContainerGap(21, Short.MAX_VALUE)
                .addComponent(btnQLKhang, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlQLKHangLayout.setVerticalGroup(
            pnlQLKHangLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLKhang, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlQLKHang, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 300, -1, 50));

        pnlQLDV.setBackground(new java.awt.Color(190, 79, 60));

        btnQLDichVu.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLDichVu.setForeground(new java.awt.Color(255, 255, 255));
        btnQLDichVu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/ThueXe.png"))); // NOI18N
        btnQLDichVu.setText("Quản Lý Dịch Vụ");
        btnQLDichVu.setBorder(null);
        btnQLDichVu.setBorderPainted(false);
        btnQLDichVu.setContentAreaFilled(false);
        btnQLDichVu.setDefaultCapable(false);
        btnQLDichVu.setFocusPainted(false);
        btnQLDichVu.setFocusable(false);
        btnQLDichVu.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLDichVu.setIconTextGap(13);
        btnQLDichVu.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLDichVuMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLDichVuMouseExited(evt);
            }
        });
        btnQLDichVu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLDichVuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLDVLayout = new javax.swing.GroupLayout(pnlQLDV);
        pnlQLDV.setLayout(pnlQLDVLayout);
        pnlQLDVLayout.setHorizontalGroup(
            pnlQLDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLDVLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(btnQLDichVu, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlQLDVLayout.setVerticalGroup(
            pnlQLDVLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLDichVu, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlQLDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, -1, 50));

        pnlDangXuat.setBackground(new java.awt.Color(190, 79, 60));

        btnDangxuat.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDangxuat.setForeground(new java.awt.Color(255, 255, 255));
        btnDangxuat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/sign-out-alt.png"))); // NOI18N
        btnDangxuat.setText("Đăng Xuất");
        btnDangxuat.setBorder(null);
        btnDangxuat.setBorderPainted(false);
        btnDangxuat.setContentAreaFilled(false);
        btnDangxuat.setDefaultCapable(false);
        btnDangxuat.setFocusPainted(false);
        btnDangxuat.setFocusable(false);
        btnDangxuat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDangxuat.setIconTextGap(13);
        btnDangxuat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDangxuatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDangxuatMouseExited(evt);
            }
        });
        btnDangxuat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDangxuatActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDangXuatLayout = new javax.swing.GroupLayout(pnlDangXuat);
        pnlDangXuat.setLayout(pnlDangXuatLayout);
        pnlDangXuatLayout.setHorizontalGroup(
            pnlDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDangXuatLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(btnDangxuat, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlDangXuatLayout.setVerticalGroup(
            pnlDangXuatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDangxuat, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlDangXuat, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 660, 250, 50));

        pnlDoiMK.setBackground(new java.awt.Color(190, 79, 60));

        btnDoiMK.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnDoiMK.setForeground(new java.awt.Color(255, 255, 255));
        btnDoiMK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/key.png"))); // NOI18N
        btnDoiMK.setText("Đổi Mật Khẩu");
        btnDoiMK.setBorder(null);
        btnDoiMK.setBorderPainted(false);
        btnDoiMK.setContentAreaFilled(false);
        btnDoiMK.setDefaultCapable(false);
        btnDoiMK.setFocusPainted(false);
        btnDoiMK.setFocusable(false);
        btnDoiMK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnDoiMK.setIconTextGap(13);
        btnDoiMK.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDoiMKMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDoiMKMouseExited(evt);
            }
        });
        btnDoiMK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDoiMKActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDoiMKLayout = new javax.swing.GroupLayout(pnlDoiMK);
        pnlDoiMK.setLayout(pnlDoiMKLayout);
        pnlDoiMKLayout.setHorizontalGroup(
            pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDoiMKLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(btnDoiMK)
                .addGap(95, 95, 95))
        );
        pnlDoiMKLayout.setVerticalGroup(
            pnlDoiMKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnDoiMK, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlDoiMK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 250, 50));

        pnlTK.setBackground(new java.awt.Color(190, 79, 60));

        btnTaiKhoan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnTaiKhoan.setForeground(new java.awt.Color(255, 255, 255));
        btnTaiKhoan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/settings.png"))); // NOI18N
        btnTaiKhoan.setText("Tài Khoản");
        btnTaiKhoan.setBorder(null);
        btnTaiKhoan.setBorderPainted(false);
        btnTaiKhoan.setContentAreaFilled(false);
        btnTaiKhoan.setDefaultCapable(false);
        btnTaiKhoan.setFocusPainted(false);
        btnTaiKhoan.setFocusable(false);
        btnTaiKhoan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnTaiKhoan.setIconTextGap(13);
        btnTaiKhoan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnTaiKhoanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnTaiKhoanMouseExited(evt);
            }
        });
        btnTaiKhoan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTaiKhoanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTKLayout = new javax.swing.GroupLayout(pnlTK);
        pnlTK.setLayout(pnlTKLayout);
        pnlTKLayout.setHorizontalGroup(
            pnlTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTKLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(btnTaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, 226, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTKLayout.setVerticalGroup(
            pnlTKLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnTaiKhoan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlTK, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 500, 250, 50));

        pnlQLThanhToan.setBackground(new java.awt.Color(190, 79, 60));

        btnQLThanhToan.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLThanhToan.setForeground(new java.awt.Color(255, 255, 255));
        btnQLThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/barcode.png"))); // NOI18N
        btnQLThanhToan.setText("Quản Lý Thanh Toán");
        btnQLThanhToan.setBorder(null);
        btnQLThanhToan.setBorderPainted(false);
        btnQLThanhToan.setContentAreaFilled(false);
        btnQLThanhToan.setDefaultCapable(false);
        btnQLThanhToan.setFocusPainted(false);
        btnQLThanhToan.setFocusable(false);
        btnQLThanhToan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLThanhToan.setIconTextGap(13);
        btnQLThanhToan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLThanhToanMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLThanhToanMouseExited(evt);
            }
        });
        btnQLThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLThanhToanLayout = new javax.swing.GroupLayout(pnlQLThanhToan);
        pnlQLThanhToan.setLayout(pnlQLThanhToanLayout);
        pnlQLThanhToanLayout.setHorizontalGroup(
            pnlQLThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLThanhToanLayout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addComponent(btnQLThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlQLThanhToanLayout.setVerticalGroup(
            pnlQLThanhToanLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLThanhToan, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlQLThanhToan, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 450, -1, -1));

        pnlQLLichTrinh.setBackground(new java.awt.Color(190, 79, 60));

        btnQLLichTrinh.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnQLLichTrinh.setForeground(new java.awt.Color(255, 255, 255));
        btnQLLichTrinh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/flag.png"))); // NOI18N
        btnQLLichTrinh.setText("Quản Lý Lịch Trình");
        btnQLLichTrinh.setBorder(null);
        btnQLLichTrinh.setBorderPainted(false);
        btnQLLichTrinh.setContentAreaFilled(false);
        btnQLLichTrinh.setDefaultCapable(false);
        btnQLLichTrinh.setFocusPainted(false);
        btnQLLichTrinh.setFocusable(false);
        btnQLLichTrinh.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        btnQLLichTrinh.setIconTextGap(13);
        btnQLLichTrinh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnQLLichTrinhMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnQLLichTrinhMouseExited(evt);
            }
        });
        btnQLLichTrinh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnQLLichTrinhActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlQLLichTrinhLayout = new javax.swing.GroupLayout(pnlQLLichTrinh);
        pnlQLLichTrinh.setLayout(pnlQLLichTrinhLayout);
        pnlQLLichTrinhLayout.setHorizontalGroup(
            pnlQLLichTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLLichTrinhLayout.createSequentialGroup()
                .addContainerGap(23, Short.MAX_VALUE)
                .addComponent(btnQLLichTrinh, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlQLLichTrinhLayout.setVerticalGroup(
            pnlQLLichTrinhLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(btnQLLichTrinh, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );

        pnlCongCu.add(pnlQLLichTrinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 350, 250, -1));

        pnlParent.add(pnlCongCu, java.awt.BorderLayout.LINE_START);

        pnlRight.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        pnlRight.add(tbpMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, -30, 1290, 870));

        pnlParent.add(pnlRight, java.awt.BorderLayout.CENTER);

        getContentPane().add(pnlParent, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 40, 1537, 836));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        for (double i = 0.0; i <= 1; i = i + 0.1) {
            String val = i + "";
            float f = Float.parseFloat(val);
            this.setOpacity(f);
            try {
                Thread.sleep(20);
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_formWindowOpened

    private void btnTrangchuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrangchuMouseEntered
        HoverButton(1, pnlTrangchu);
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_btnTrangchuMouseEntered

    private void btnTrangchuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTrangchuMouseExited
        HoverButton(2, pnlTrangchu);
    }//GEN-LAST:event_btnTrangchuMouseExited

    private void btnTrangchuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTrangchuActionPerformed
        tbpMenu.removeAll();
        Frame_TrangChu tc = new Frame_TrangChu();
        tbpMenu.addTab("", null, tc.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Trang Chủ");
    }//GEN-LAST:event_btnTrangchuActionPerformed

    private void btnQLTourMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLTourMouseEntered
        HoverButton(1, pnlQLTour);
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_btnQLTourMouseEntered

    private void btnQLTourMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLTourMouseExited
        HoverButton(2, pnlQLTour);
    }//GEN-LAST:event_btnQLTourMouseExited

    private void btnQLKhangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLKhangMouseEntered
        HoverButton(1, pnlQLKHang);
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_btnQLKhangMouseEntered

    private void btnQLKhangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLKhangMouseExited
        HoverButton(2, pnlQLKHang);
    }//GEN-LAST:event_btnQLKhangMouseExited

    private void btnQLDichVuMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLDichVuMouseEntered
        HoverButton(1, pnlQLDV);
    }//GEN-LAST:event_btnQLDichVuMouseEntered

    private void btnQLDichVuMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLDichVuMouseExited
        HoverButton(2, pnlQLDV);
    }//GEN-LAST:event_btnQLDichVuMouseExited

    private void btnQLDichVuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLDichVuActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            showHideSubMenuSub(showQLDV, pnlSubQLDV);
        }
    }//GEN-LAST:event_btnQLDichVuActionPerformed

    private void btnKhachSanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhachSanMouseEntered
        HoverButton(1, pnlKhachSan);
    }//GEN-LAST:event_btnKhachSanMouseEntered

    private void btnKhachSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnKhachSanMouseExited
        HoverButton(2, pnlKhachSan);
    }//GEN-LAST:event_btnKhachSanMouseExited

    private void btnThamQuanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThamQuanMouseEntered
        HoverButton(1, pnlThamQuan);
    }//GEN-LAST:event_btnThamQuanMouseEntered

    private void btnThamQuanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThamQuanMouseExited
        HoverButton(2, pnlThamQuan);
    }//GEN-LAST:event_btnThamQuanMouseExited

    private void btnLoaiPtMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoaiPtMouseEntered
        HoverButton(1, pnlLoaiPhuongTien);
    }//GEN-LAST:event_btnLoaiPtMouseEntered

    private void btnLoaiPtMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoaiPtMouseExited
        HoverButton(2, pnlLoaiPhuongTien);
    }//GEN-LAST:event_btnLoaiPtMouseExited

    private void btnDangxuatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangxuatMouseEntered
        HoverButton(1, pnlDangXuat);
    }//GEN-LAST:event_btnDangxuatMouseEntered

    private void btnDangxuatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDangxuatMouseExited
        HoverButton(2, pnlDangXuat);
    }//GEN-LAST:event_btnDangxuatMouseExited

    private void btnDangxuatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDangxuatActionPerformed
        ThongBao1 obj = new ThongBao1("Bạn Có Chắc Chắn Muốn Đăng Xuất Không ?");
        obj.eventOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                AddLichSuHD.addLSHD("Đăng Xuất Khỏi Hệ Thống Travel Tour");
                dispose();
                new LoginForm().setVisible(true);
                GlassPanePopup.closePopupLast();
            }
        });
        GlassPanePopup.showPopup(obj);
    }//GEN-LAST:event_btnDangxuatActionPerformed

    private void btnDoiMKMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMKMouseEntered
        HoverButton(1, pnlDoiMK);
    }//GEN-LAST:event_btnDoiMKMouseEntered

    private void btnDoiMKMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDoiMKMouseExited
        HoverButton(2, pnlDoiMK);
    }//GEN-LAST:event_btnDoiMKMouseExited

    private void btnDoiMKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDoiMKActionPerformed
        new ChangePassForm(this, true).setVisible(true);
        AddLichSuHD.addLSHD("Truy Cập Vào Đổi Mật Khẩu");
    }//GEN-LAST:event_btnDoiMKActionPerformed

    private void btnTaiKhoanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaiKhoanMouseEntered
        HoverButton(1, pnlTK);
    }//GEN-LAST:event_btnTaiKhoanMouseEntered

    private void btnTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTaiKhoanMouseExited
        HoverButton(2, pnlTK);
    }//GEN-LAST:event_btnTaiKhoanMouseExited

    private void btnTaiKhoanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTaiKhoanActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên") || cVu.equals("Nhân Viên")) {
            MsgBox.AlerThongBao("Nhân Viên Và Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập !");
        } else {
            showHideSubMenuSub(showQLTK, pnlSubTaiKhoan);
        }
    }//GEN-LAST:event_btnTaiKhoanActionPerformed

    private void pnlKhachSanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachSanMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlKhachSanMouseEntered

    private void pnlKhachSanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlKhachSanMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlKhachSanMouseExited

    private void pnlThamQuanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThamQuanMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlThamQuanMouseEntered

    private void pnlThamQuanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThamQuanMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlThamQuanMouseExited

    private void pnlLoaiPhuongTienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLoaiPhuongTienMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLoaiPhuongTienMouseEntered

    private void pnlLoaiPhuongTienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLoaiPhuongTienMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLoaiPhuongTienMouseExited

    private void pnlSubQLDVMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSubQLDVMouseExited
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_pnlSubQLDVMouseExited

    private void btnTTNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTTNhanVienMouseEntered
        HoverButton(1, pnlTTNhanVien);
    }//GEN-LAST:event_btnTTNhanVienMouseEntered

    private void btnTTNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTTNhanVienMouseExited
        HoverButton(2, pnlTTNhanVien);
    }//GEN-LAST:event_btnTTNhanVienMouseExited

    private void btnTTNhanVienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTTNhanVienActionPerformed
        tbpMenu.removeAll();
        Frame_TTNhanVien nv = new Frame_TTNhanVien();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, nv.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Nhân Viên");
    }//GEN-LAST:event_btnTTNhanVienActionPerformed

    private void pnlTTNhanVienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTTNhanVienMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlTTNhanVienMouseEntered

    private void pnlTTNhanVienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTTNhanVienMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlTTNhanVienMouseExited

    private void pnlSubTaiKhoanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSubTaiKhoanMouseExited
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_pnlSubTaiKhoanMouseExited

    private void btnQLThanhToanMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLThanhToanMouseEntered
        HoverButton(1, pnlQLThanhToan);
    }//GEN-LAST:event_btnQLThanhToanMouseEntered

    private void btnQLThanhToanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLThanhToanMouseExited
        HoverButton(2, pnlQLThanhToan);
    }//GEN-LAST:event_btnQLThanhToanMouseExited

    private void btnQLKhangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLKhangActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            tbpMenu.removeAll();
            Frame_TTKhachHang kh = new Frame_TTKhachHang();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, kh.getContentPane().getComponentAt(0, 0), null);

            AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Khách Hàng");
        }
    }//GEN-LAST:event_btnQLKhangActionPerformed

    private void btnQLTourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLTourActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            tbpMenu.removeAll();
            Frame_TTTour tour = new Frame_TTTour();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, tour.getContentPane().getComponentAt(0, 0), null);

            AddLichSuHD.addLSHD("Truy Cập Thông Tin Tour");
        }
    }//GEN-LAST:event_btnQLTourActionPerformed

    private void pnlSubLichTrinhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSubLichTrinhMouseExited
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_pnlSubLichTrinhMouseExited

    private void pnlLTKHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTKHMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTKHMouseExited

    private void pnlLTKHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTKHMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTKHMouseEntered

    private void btnLTKHMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTKHMouseExited
        HoverButton(2, pnlLTKH);
    }//GEN-LAST:event_btnLTKHMouseExited

    private void btnLTKHMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTKHMouseEntered
        HoverButton(1, pnlLTKH);
    }//GEN-LAST:event_btnLTKHMouseEntered

    private void btnLTKSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTKSMouseEntered
        HoverButton(1, pnlLTKS);
    }//GEN-LAST:event_btnLTKSMouseEntered

    private void btnLTKSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTKSMouseExited
        HoverButton(2, pnlLTKS);
    }//GEN-LAST:event_btnLTKSMouseExited

    private void pnlLTKSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTKSMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTKSMouseEntered

    private void pnlLTKSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTKSMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTKSMouseExited

    private void btnLTDDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTDDMouseEntered
        HoverButton(1, pnlLTDD);
    }//GEN-LAST:event_btnLTDDMouseEntered

    private void btnLTDDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTDDMouseExited
        HoverButton(2, pnlLTDD);
    }//GEN-LAST:event_btnLTDDMouseExited

    private void pnlLTDDMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTDDMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTDDMouseEntered

    private void pnlLTDDMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTDDMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTDDMouseExited

    private void btnLTPTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTPTMouseEntered
        HoverButton(1, pnlLTPT);
    }//GEN-LAST:event_btnLTPTMouseEntered

    private void btnLTPTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTPTMouseExited
        HoverButton(2, pnlLTPT);
    }//GEN-LAST:event_btnLTPTMouseExited

    private void pnlLTPTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTPTMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTPTMouseEntered

    private void pnlLTPTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTPTMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTPTMouseExited

    private void btnQLLichTrinhMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLLichTrinhMouseEntered
        HoverButton(1, pnlQLLichTrinh);
    }//GEN-LAST:event_btnQLLichTrinhMouseEntered

    private void btnQLLichTrinhMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLLichTrinhMouseExited
        HoverButton(2, pnlQLLichTrinh);
    }//GEN-LAST:event_btnQLLichTrinhMouseExited

    private void btnQLLichTrinhActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLLichTrinhActionPerformed
        showHideSubMenuSub(showQLLT, pnlSubLichTrinh);
    }//GEN-LAST:event_btnQLLichTrinhActionPerformed

    private void btnThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseEntered
        HoverButton(1, pnlThongKe);
    }//GEN-LAST:event_btnThongKeMouseEntered

    private void btnThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThongKeMouseExited
        HoverButton(2, pnlThongKe);
    }//GEN-LAST:event_btnThongKeMouseExited

    private void pnlThongKeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongKeMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlThongKeMouseEntered

    private void pnlThongKeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlThongKeMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlThongKeMouseExited

    private void pnlLSHoatDongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLSHoatDongMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLSHoatDongMouseExited

    private void pnlLSHoatDongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLSHoatDongMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLSHoatDongMouseEntered

    private void btnLSHoatDongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLSHoatDongMouseExited
        HoverButton(2, pnlLSHoatDong);
    }//GEN-LAST:event_btnLSHoatDongMouseExited

    private void btnLSHoatDongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLSHoatDongMouseEntered
        HoverButton(1, pnlLSHoatDong);
    }//GEN-LAST:event_btnLSHoatDongMouseEntered

    private void btnLTKHActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLTKHActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            tbpMenu.removeAll();
            Frame_LT_KhachHang lichTrinh = new Frame_LT_KhachHang();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, lichTrinh.getContentPane().getComponentAt(0, 0), null);

            AddLichSuHD.addLSHD("Truy Cập Vào Xem Khách Hàng Theo Lịch Trình");
        }
    }//GEN-LAST:event_btnLTKHActionPerformed

    private void btnLTKSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLTKSActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            tbpMenu.removeAll();
            Frame_LT_KhachSan ltks = new Frame_LT_KhachSan();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

            AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Khách Sạn Theo Lịch Trình");
        }
    }//GEN-LAST:event_btnLTKSActionPerformed

    private void btnLTDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLTDDActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            tbpMenu.removeAll();
            Frame_LT_DiaDiem ltks = new Frame_LT_DiaDiem();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

            AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Địa Điểm Theo Lịch Trình");
        }
    }//GEN-LAST:event_btnLTDDActionPerformed

    private void btnLTPTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLTPTActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            tbpMenu.removeAll();
            Frame_LT_PhuongTien ltks = new Frame_LT_PhuongTien();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

            AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Phương Tiện Theo Lịch Trình");
        }


    }//GEN-LAST:event_btnLTPTActionPerformed

    private void btnKhachSanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKhachSanActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        tbpMenu.removeAll();
        Frame_TTKhachSan ltks = new Frame_TTKhachSan();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Khách Sạn - HomeStay");
    }//GEN-LAST:event_btnKhachSanActionPerformed

    private void btnThamQuanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThamQuanActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        tbpMenu.removeAll();
        Frame_TTDiaDiem ltks = new Frame_TTDiaDiem();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Địa Điểm Tham Quan");
    }//GEN-LAST:event_btnThamQuanActionPerformed

    private void btnLSHoatDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLSHoatDongActionPerformed
        tbpMenu.removeAll();
        Frame_LichSuHoatDong ltks = new Frame_LichSuHoatDong();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Lịch Sử Hoạt Động");
    }//GEN-LAST:event_btnLSHoatDongActionPerformed

    private void btnThongKeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThongKeActionPerformed
        tbpMenu.removeAll();
        Frame_ThongKeDoanhThu ltks = new Frame_ThongKeDoanhThu();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Thống Kê - Doanh Thu");
    }//GEN-LAST:event_btnThongKeActionPerformed

    private void btnTTLTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTTLTMouseEntered
        HoverButton(1, pnlTTLT);
    }//GEN-LAST:event_btnTTLTMouseEntered

    private void btnTTLTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnTTLTMouseExited
        HoverButton(2, pnlTTLT);
    }//GEN-LAST:event_btnTTLTMouseExited

    private void btnTTLTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTTLTActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            AddLichSuHD.addLSHD("Truy Cập Vào Thông Tin Lịch Trình");
            tbpMenu.removeAll();
            Frame_TTLichTrinh ltks = new Frame_TTLichTrinh();
            pnlSubMenu.setVisible(false);
            tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);
        }
    }//GEN-LAST:event_btnTTLTActionPerformed

    private void pnlTTLTMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTTLTMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlTTLTMouseEntered

    private void pnlTTLTMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlTTLTMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlTTLTMouseExited

    private void btnPhuongTienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPhuongTienMouseEntered
        HoverButton(1, pnlPhuongTien);
    }//GEN-LAST:event_btnPhuongTienMouseEntered

    private void btnPhuongTienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPhuongTienMouseExited
        HoverButton(2, pnlPhuongTien);
    }//GEN-LAST:event_btnPhuongTienMouseExited

    private void btnPhuongTienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPhuongTienActionPerformed
        tbpMenu.removeAll();
        Frame_TTPhuongTien ltks = new Frame_TTPhuongTien();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Phương Tiện");
    }//GEN-LAST:event_btnPhuongTienActionPerformed

    private void pnlPhuongTienMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPhuongTienMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlPhuongTienMouseEntered

    private void pnlPhuongTienMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlPhuongTienMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlPhuongTienMouseExited

    private void btnLoaiPtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoaiPtActionPerformed
        tbpMenu.removeAll();
        Frame_LoaiPhuongTien ltks = new Frame_LoaiPhuongTien();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Loại Phương Tiện");
    }//GEN-LAST:event_btnLoaiPtActionPerformed

    private void btnLTDchuyenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTDchuyenMouseEntered
        HoverButton(1, pnlLTDChuyen);
    }//GEN-LAST:event_btnLTDchuyenMouseEntered

    private void btnLTDchuyenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLTDchuyenMouseExited
        HoverButton(2, pnlLTDChuyen);
    }//GEN-LAST:event_btnLTDchuyenMouseExited

    private void btnLTDchuyenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLTDchuyenActionPerformed
        tbpMenu.removeAll();
        Frame_LichTrinhDiChuyen ltks = new Frame_LichTrinhDiChuyen();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Lịch Trình Di Chuyển");
    }//GEN-LAST:event_btnLTDchuyenActionPerformed

    private void pnlLTDChuyenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTDChuyenMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTDChuyenMouseEntered

    private void pnlLTDChuyenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlLTDChuyenMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlLTDChuyenMouseExited

    private void btnQLThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLThanhToanActionPerformed
        String cVu = null;
        String manv = lblTitle.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);
        for (NhanVien nhanVien : nv) {
            cVu = nhanVien.getChucVu();
        }

        if (cVu.equals("Hướng Dẫn Viên")) {
            MsgBox.AlerThongBao("Hướng Dẫn Viên Bị Giới Hạn Quyền Truy Cập  !");
        } else {
            showHideSubMenuSub(showQLTT, pnlSubQLThanhToan);
        }
    }//GEN-LAST:event_btnQLThanhToanActionPerformed

    private void btnQLHopDongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLHopDongMouseEntered
        HoverButton(1, pnlQLHopDong);
    }//GEN-LAST:event_btnQLHopDongMouseEntered

    private void btnQLHopDongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLHopDongMouseExited
        HoverButton(2, pnlQLHopDong);
    }//GEN-LAST:event_btnQLHopDongMouseExited

    private void btnQLHopDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLHopDongActionPerformed
        tbpMenu.removeAll();
        Frame_TTHopDong ltks = new Frame_TTHopDong();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Lịch Trình Di Chuyển");
    }//GEN-LAST:event_btnQLHopDongActionPerformed

    private void pnlQLHopDongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlQLHopDongMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlQLHopDongMouseEntered

    private void pnlQLHopDongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlQLHopDongMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlQLHopDongMouseExited

    private void btnQLHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLHoaDonMouseEntered
        HoverButton(1, pnlQLHoaDon);
    }//GEN-LAST:event_btnQLHoaDonMouseEntered

    private void btnQLHoaDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnQLHoaDonMouseExited
        HoverButton(2, pnlQLHoaDon);
    }//GEN-LAST:event_btnQLHoaDonMouseExited

    private void btnQLHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnQLHoaDonActionPerformed
        tbpMenu.removeAll();
        Frame_TTHoaDon ltks = new Frame_TTHoaDon();
        pnlSubMenu.setVisible(false);
        tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);

        AddLichSuHD.addLSHD("Truy Cập Vào Quản Lý Hoá Đơn");
    }//GEN-LAST:event_btnQLHoaDonActionPerformed

    private void pnlQLHoaDonMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlQLHoaDonMouseEntered
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlQLHoaDonMouseEntered

    private void pnlQLHoaDonMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlQLHoaDonMouseExited
        pnlSubMenu.setVisible(true);
    }//GEN-LAST:event_pnlQLHoaDonMouseExited

    private void pnlSubQLThanhToanMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlSubQLThanhToanMouseExited
        pnlSubMenu.setVisible(false);
    }//GEN-LAST:event_pnlSubQLThanhToanMouseExited

    private void btnThoatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoatMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThoat, "buttonClose-Hover.png");
    }//GEN-LAST:event_btnThoatMouseEntered

    private void btnThoatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThoatMouseExited
        new DungChung.DungChung().hoverButton(2, btnThoat, "buttonClose.png");
    }//GEN-LAST:event_btnThoatMouseExited

    private void btnThoatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThoatActionPerformed
        ThongBao1 obj = new ThongBao1("Bạn Có Chắc Chắn Muốn Thoát Không ?");
        obj.eventOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                AddLichSuHD.addLSHD("Thoát Khỏi Hệ Thống Travel Tour");
                System.exit(0);
            }
        });
        GlassPanePopup.showPopup(obj);
    }//GEN-LAST:event_btnThoatActionPerformed

    private void btnMiniMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniMouseEntered
        new DungChung.DungChung().hoverButton(1, btnMini, "minus-Hover.png");
    }//GEN-LAST:event_btnMiniMouseEntered

    private void btnMiniMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnMiniMouseExited
        new DungChung.DungChung().hoverButton(1, btnMini, "minus.png");
    }//GEN-LAST:event_btnMiniMouseExited

    private void btnMiniActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMiniActionPerformed
        setExtendedState(JFrame.ICONIFIED);
    }//GEN-LAST:event_btnMiniActionPerformed

    private void btnHoTroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHoTroActionPerformed
        openHuongDan();
    }//GEN-LAST:event_btnHoTroActionPerformed

    private void btnHoTroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHoTroMouseEntered
        new DungChung.DungChung().hoverButton(1, btnHoTro, "interrogation-Hover.png");
    }//GEN-LAST:event_btnHoTroMouseEntered

    private void btnHoTroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHoTroMouseExited
        new DungChung.DungChung().hoverButton(2, btnHoTro, "interrogation.png");
    }//GEN-LAST:event_btnHoTroMouseExited

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
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDangxuat;
    private javax.swing.JButton btnDoiMK;
    private javax.swing.JButton btnHoTro;
    private javax.swing.JButton btnKhachSan;
    private javax.swing.JButton btnLSHoatDong;
    private javax.swing.JButton btnLTDD;
    private javax.swing.JButton btnLTDchuyen;
    private javax.swing.JButton btnLTKH;
    private javax.swing.JButton btnLTKS;
    private javax.swing.JButton btnLTPT;
    private javax.swing.JButton btnLoaiPt;
    private javax.swing.JButton btnMini;
    private javax.swing.JButton btnPhuongTien;
    private javax.swing.JButton btnQLDichVu;
    private javax.swing.JButton btnQLHoaDon;
    private javax.swing.JButton btnQLHopDong;
    private javax.swing.JButton btnQLKhang;
    private javax.swing.JButton btnQLLichTrinh;
    private javax.swing.JButton btnQLThanhToan;
    private javax.swing.JButton btnQLTour;
    private javax.swing.JButton btnTTLT;
    private javax.swing.JButton btnTTNhanVien;
    private javax.swing.JButton btnTaiKhoan;
    private javax.swing.JButton btnThamQuan;
    private javax.swing.JButton btnThoat;
    private javax.swing.JButton btnThongKe;
    private javax.swing.JButton btnTrangchu;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblClock;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblImages;
    private javax.swing.JLabel lblKey;
    public static javax.swing.JLabel lblTitle;
    private javax.swing.JLabel lblTravelTour;
    private javax.swing.JPanel pnlCongCu;
    private javax.swing.JPanel pnlDangXuat;
    private javax.swing.JPanel pnlDoiMK;
    private UI.pnlQLy pnlImages;
    private UI.pnlQLy pnlKhachSan;
    private UI.pnlQLy pnlLSHoatDong;
    private UI.pnlQLy pnlLTDChuyen;
    private UI.pnlQLy pnlLTDD;
    private UI.pnlQLy pnlLTKH;
    private UI.pnlQLy pnlLTKS;
    private UI.pnlQLy pnlLTPT;
    private UI.pnlQLy pnlLoaiPhuongTien;
    private javax.swing.JPanel pnlParent;
    private UI.pnlQLy pnlPhuongTien;
    private javax.swing.JPanel pnlQLDV;
    private UI.pnlQLy pnlQLHoaDon;
    private UI.pnlQLy pnlQLHopDong;
    private javax.swing.JPanel pnlQLKHang;
    private javax.swing.JPanel pnlQLLichTrinh;
    private javax.swing.JPanel pnlQLThanhToan;
    private javax.swing.JPanel pnlQLTour;
    private javax.swing.JPanel pnlRight;
    private javax.swing.JPanel pnlSubLichTrinh;
    private UI.pnlQLy pnlSubMenu;
    private javax.swing.JPanel pnlSubQLDV;
    private javax.swing.JPanel pnlSubQLThanhToan;
    private javax.swing.JPanel pnlSubTaiKhoan;
    private javax.swing.JPanel pnlTK;
    private UI.pnlQLy pnlTTLT;
    private UI.pnlQLy pnlTTNhanVien;
    private UI.pnlQLy pnlThamQuan;
    private UI.pnlQLy pnlThongKe;
    private javax.swing.JPanel pnlTop;
    private javax.swing.JPanel pnlTrangchu;
    public static javax.swing.JTabbedPane tbpMenu;
    // End of variables declaration//GEN-END:variables
}
