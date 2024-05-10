package UI;

import DAO.LichTrinhDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import SQL.JDBCHelper;
import TravelEntity.LichTrinh;
import static UI.Frame_TTLichTrinh.txtMaLT;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author NHUTLQ
 */
public class Dialog_XuatHDong extends javax.swing.JDialog {

    static LichTrinhDAO ltDao = new LichTrinhDAO();

    public Dialog_XuatHDong(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        txtLichTrinh.setEditable(false);
        txtLichTrinh.setText(TruyVanSQL.fillTTLichTrinhInTTHopDong(txtMaLT.getText()));
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public boolean check() {
        if (cboHHTT.getSelectedIndex() == 0) {
            MsgBox.alert(this, "Vui Lòng Chọn Hình Thức Thanh Toán");
            return false;
        }
        if (!rdo1.isSelected() && !rdo2.isSelected() && !rdo3.isSelected() && !rdo4.isSelected()) {
            MsgBox.alert(this, "Vui Lòng Chọn Giá Trị Muốn Cọc");
            return false;
        }
        return true;
    }

    public static String MaLT(String tenkh) {
        String malt = "";
        try {
            String sql = "SELECT MaLT FROM dbo.LICHTRINH lt \n"
                    + "JOIN dbo.KHACHHANG kh\n"
                    + "ON kh.MaKH = lt.MaKH\n"
                    + "WHERE kh.TenKH LIKE ?";
            ResultSet rs = JDBCHelper.query(sql, tenkh);
            while (rs.next()) {
                malt = rs.getString(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return malt;
    }

    public static long tongGiaTriHopDong(String malt) {
        int soKhach = 0;
        long giatien = (long) 0.0;
        long tonggiatrihd = (long) 0.0;
        long phuthu = Long.parseLong(Frame_TTLichTrinh.txtPhuThu.getText());

        try {
            List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
            for (LichTrinh lichTrinh : lt) {
                soKhach = lichTrinh.getSoKhach();
            }

            String sql = "SELECT Gia FROM dbo.TOUR t\n"
                    + "JOIN dbo.LICHTRINH lt\n"
                    + "ON lt.MaTour = t.MaTour\n"
                    + "WHERE lt.MaLT = ?";
            ResultSet rs = JDBCHelper.query(sql, malt);
            while (rs.next()) {
                giatien = rs.getLong(1);
            }
            tonggiatrihd = (giatien * soKhach) + phuthu;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tonggiatrihd;
    }

    public static String TenKhachHang() {
        String tenKh = Dialog_XuatHDong.txtLichTrinh.getText();
        String subString = tenKh.substring(tenKh.lastIndexOf("-") + 1).trim();
        return subString;
    }

    public static long TienChoMoiKhach(String malt) {
        long tienTour = 0;
        try {
            List<LichTrinh> lt = ltDao.selectSLKH(MaLT(TenKhachHang()));
            for (LichTrinh lichTrinh : lt) {
                lichTrinh.getSoKhach();
            }

            String sql = "SELECT Gia FROM dbo.TOUR t\n"
                    + "JOIN dbo.LICHTRINH lt\n"
                    + "ON lt.MaTour = t.MaTour\n"
                    + "WHERE lt.MaLT = ?";
            ResultSet rs = JDBCHelper.query(sql, malt);
            while (rs.next()) {
                tienTour = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tienTour;
    }

    public static long TongTienTourDaCoc() {
        long giaTour = tongGiaTriHopDong(MaLT(TenKhachHang()));
        Double phuThu = Double.valueOf(Frame_TTLichTrinh.txtPhuThu.getText());
        long tongTien = 0;
        if (rdo1.isSelected()) {
            tongTien = (long) ((giaTour + phuThu) * 0.4);
            return tongTien;
        }
        if (rdo2.isSelected()) {
            tongTien = (long) ((giaTour + phuThu) * 0.5);
            return tongTien;
        }
        if (rdo3.isSelected()) {
            tongTien = (long) ((giaTour + phuThu) * 0.6);
            return tongTien;
        }
        if (rdo4.isSelected()) {
            tongTien = (long) ((giaTour + phuThu) * 0.7);
            return tongTien;
        }
        return tongTien;
    }

    public static String trangThaiCoc() {
        String[] str = {
            "Đã Thanh Toán 40%",
            "Đã Thanh Toán 50%",
            "Đã Thanh Toán 60%",
            "Đã Thanh Toán 70%"
        };
        String trangThai = "";
        if (rdo1.isSelected()) {
            trangThai = str[0];
        }
        if (rdo2.isSelected()) {
            trangThai = str[1];
        }
        if (rdo3.isSelected()) {
            trangThai = str[2];
        }
        if (rdo4.isSelected()) {
            trangThai = str[3];
        }
        return trangThai;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        coc = new javax.swing.ButtonGroup();
        pnlCardLayout = new javax.swing.JPanel();
        pnlCard1 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        cboHHTT = new CboAndTxtSugestion.ComboBoxSuggestion();
        rdo1 = new javax.swing.JRadioButton();
        rdo3 = new javax.swing.JRadioButton();
        rdo4 = new javax.swing.JRadioButton();
        rdo2 = new javax.swing.JRadioButton();
        btnHuybo = new javax.swing.JButton();
        btnDongy = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtLichTrinh = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thông Tin Hợp Đồng");
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlCardLayout.setLayout(new java.awt.CardLayout());

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("THÔNG TIN HỢP ĐỒNG ");
        jPanel1.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 20, 830, 30));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(190, 79, 60));
        jLabel25.setText("Thông Tin Lịch Trình:");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 80, 250, 30));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(190, 79, 60));
        jLabel26.setText("Giá Trị Cọc:");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 260, 90, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Chọn Loại Hình Thức Thanh Toán:");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 170, 250, 30));

        cboHHTT.setForeground(new java.awt.Color(190, 79, 60));
        cboHHTT.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Vui Lòng Chọn Hình Thức Thanh Toán:", "Thanh Toán Qua Tiền Mặt", "Thanh Toán Qua Hình Thức Chuyển Khoản", " " }));
        cboHHTT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jPanel1.add(cboHHTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 200, 360, 40));

        coc.add(rdo1);
        rdo1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo1.setForeground(new java.awt.Color(190, 79, 60));
        rdo1.setText("40 %");
        jPanel1.add(rdo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 300, -1, 30));

        coc.add(rdo3);
        rdo3.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo3.setForeground(new java.awt.Color(190, 79, 60));
        rdo3.setText("60 %");
        jPanel1.add(rdo3, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 300, -1, 30));

        coc.add(rdo4);
        rdo4.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo4.setForeground(new java.awt.Color(190, 79, 60));
        rdo4.setText("70 %");
        jPanel1.add(rdo4, new org.netbeans.lib.awtextra.AbsoluteConstraints(700, 300, -1, 30));

        coc.add(rdo2);
        rdo2.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        rdo2.setForeground(new java.awt.Color(190, 79, 60));
        rdo2.setText("50 %");
        jPanel1.add(rdo2, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 300, -1, 30));

        btnHuybo.setBackground(new java.awt.Color(242, 242, 242));
        btnHuybo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonHuyBo.png"))); // NOI18N
        btnHuybo.setBorder(null);
        btnHuybo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnHuyboMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnHuyboMouseExited(evt);
            }
        });
        btnHuybo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyboActionPerformed(evt);
            }
        });
        jPanel1.add(btnHuybo, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 380, -1, -1));

        btnDongy.setBackground(new java.awt.Color(242, 242, 242));
        btnDongy.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonDongY.png"))); // NOI18N
        btnDongy.setBorder(null);
        btnDongy.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnDongyMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnDongyMouseExited(evt);
            }
        });
        btnDongy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDongyActionPerformed(evt);
            }
        });
        jPanel1.add(btnDongy, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 380, -1, -1));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/contract.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 397, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(7, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 338, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 70, 410, 350));

        txtLichTrinh.setBackground(new java.awt.Color(242, 242, 242));
        txtLichTrinh.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtLichTrinh.setForeground(new java.awt.Color(190, 79, 60));
        txtLichTrinh.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        jPanel1.add(txtLichTrinh, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 110, 360, 40));

        javax.swing.GroupLayout pnlCard1Layout = new javax.swing.GroupLayout(pnlCard1);
        pnlCard1.setLayout(pnlCard1Layout);
        pnlCard1Layout.setHorizontalGroup(
            pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 830, Short.MAX_VALUE)
            .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlCard1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnlCard1Layout.setVerticalGroup(
            pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 470, Short.MAX_VALUE)
            .addGroup(pnlCard1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlCard1Layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 470, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pnlCardLayout.add(pnlCard1, "card1");

        getContentPane().add(pnlCardLayout, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 830, 470));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnHuyboMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyboMouseEntered
        new DungChung.DungChung().hoverButton(1, btnHuybo, "buttonHuyBo-Hover.png");
    }//GEN-LAST:event_btnHuyboMouseEntered

    private void btnHuyboMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnHuyboMouseExited
        new DungChung.DungChung().hoverButton(2, btnHuybo, "buttonHuyBo.png");
    }//GEN-LAST:event_btnHuyboMouseExited

    private void btnHuyboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyboActionPerformed
        dispose();
    }//GEN-LAST:event_btnHuyboActionPerformed

    private void btnDongyMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDongyMouseEntered
        new DungChung.DungChung().hoverButton(1, btnDongy, "buttonDongY-Hover.png");
    }//GEN-LAST:event_btnDongyMouseEntered

    private void btnDongyMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnDongyMouseExited
        new DungChung.DungChung().hoverButton(2, btnDongy, "buttonDongY.png");
    }//GEN-LAST:event_btnDongyMouseExited

    private void btnDongyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDongyActionPerformed
        if (check()) {
            dispose();
            TongTienTourDaCoc();
            AddLichSuHD.addLSHD("Xuất hợp đồng");
            new DiaLog_PrintHopDong(new Frame_TTLichTrinh(), true).setVisible(true);
        }
    }//GEN-LAST:event_btnDongyActionPerformed

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
            java.util.logging.Logger.getLogger(Dialog_XuatHDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Dialog_XuatHDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Dialog_XuatHDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Dialog_XuatHDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Dialog_XuatHDong dialog = new Dialog_XuatHDong(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDongy;
    private javax.swing.JButton btnHuybo;
    public static CboAndTxtSugestion.ComboBoxSuggestion cboHHTT;
    private javax.swing.ButtonGroup coc;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel pnlCard1;
    private javax.swing.JPanel pnlCardLayout;
    public static javax.swing.JRadioButton rdo1;
    public static javax.swing.JRadioButton rdo2;
    public static javax.swing.JRadioButton rdo3;
    public static javax.swing.JRadioButton rdo4;
    public static javax.swing.JTextField txtLichTrinh;
    // End of variables declaration//GEN-END:variables
}
