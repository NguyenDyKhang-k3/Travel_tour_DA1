package UI;

import DAO.NhanVienDAO;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.DungChung;
import DungChung.MsgBox;
import TravelEntity.NhanVien;
import java.util.List;
import javax.swing.JLabel;

/**
 * @author NHUTLQ
 */
public class ChangePassForm extends javax.swing.JDialog {

    NhanVienDAO nvDao = new NhanVienDAO();

    public ChangePassForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        init();
    }

    public void init() {
        Hide();
    }

    public void Hide() {
        new DungChung().hideLBLError(new JLabel[]{lblPassCuNull, lblPassCuError, lblPass1Null, lblPass1Error, lblPass2Null});
    }

    public boolean Check() {
        if (txtPassCu.getText().trim().isEmpty()) {
            lblPassCuNull.setVisible(true);
            txtPassCu.requestFocus();
            return true;
        }
        if (txtPassmoi.getText().trim().isEmpty()) {
            lblPass1Null.setVisible(true);
            txtPassmoi.requestFocus();
            return true;
        }
        if (!txtPassConfirm.getText().equals(txtPassmoi.getText())) {
            lblPass2Null.setVisible(true);
            txtPassConfirm.requestFocus();
            return true;
        }
        if (txtPassCu.getText().equals(txtPassmoi.getText())) {
            lblPass1Error.setVisible(true);
            txtPassConfirm.requestFocus();
            return true;
        }
        Hide();
        return false;
    }

    public void doiMatKhau() {
        String manv = LoginForm.txtUser.getText();
        List<NhanVien> nv = nvDao.selectMaNV(manv);

        for (NhanVien nhanVien : nv) {
            String mkCu = txtPassCu.getText();
            String mkMoi = txtPassmoi.getText();

            if (!mkCu.equals(nhanVien.getMatKhau())) {
                lblPassCuError.setVisible(true);
            } else {
                if (MsgBox.comfirm(this, "Bạn Có Chắc Muốn Đổi Mật Khẩu Không ?")) {
                    nhanVien.setMatKhau(mkMoi);
                    nvDao.update(nhanVien);
                    AddLichSuHD.addLSHD("Đổi Mật Khẩu Cá Nhân");
                    MsgBox.alert(this, "Đổi Mật Khẩu Thành Công");
                    dispose();
                }
                MsgBox.alert(this, "Xin Lỗi! Quý Khách Vui Lòng Đăng Nhập Lại");
                System.exit(0);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtPassCu = new javax.swing.JPasswordField();
        jLabel13 = new javax.swing.JLabel();
        txtPassmoi = new javax.swing.JPasswordField();
        jLabel14 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtPassConfirm = new javax.swing.JPasswordField();
        jLabel15 = new javax.swing.JLabel();
        btnLogin = new javax.swing.JButton();
        btnLogin1 = new javax.swing.JButton();
        lblPassCuError = new javax.swing.JLabel();
        lblPass1Null = new javax.swing.JLabel();
        lblPass2Null = new javax.swing.JLabel();
        lblPassCuNull = new javax.swing.JLabel();
        lblPass1Error = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createMatteBorder(2, 2, 2, 2, new java.awt.Color(0, 157, 154)));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/Changpass.png"))); // NOI18N
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 410));

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(0, 157, 154));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Thay Đổi Mật Khẩu");
        jPanel1.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 20, 315, -1));

        jLabel5.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(0, 157, 154));
        jLabel5.setText("Mật Khẩu Cũ");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 60, 110, -1));

        txtPassCu.setFont(new java.awt.Font("sansserif", 0, 12)); // NOI18N
        txtPassCu.setBorder(null);
        txtPassCu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassCuFocusLost(evt);
            }
        });
        jPanel1.add(txtPassCu, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 90, 229, 30));

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(0, 157, 154));
        jLabel13.setText("--------------------------------------------------");
        jPanel1.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, 247, 8));

        txtPassmoi.setFont(new java.awt.Font("sansserif", 0, 12)); // NOI18N
        txtPassmoi.setBorder(null);
        txtPassmoi.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassmoiFocusLost(evt);
            }
        });
        jPanel1.add(txtPassmoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 170, 229, 30));

        jLabel14.setBackground(new java.awt.Color(7, 136, 69));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(0, 157, 154));
        jLabel14.setText("--------------------------------------------------");
        jPanel1.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 200, 247, 8));

        jLabel6.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 157, 154));
        jLabel6.setText("Mật Khẩu Mới");
        jPanel1.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, 120, -1));

        jLabel8.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(0, 157, 154));
        jLabel8.setText("Nhập Lại Mật Khẩu");
        jPanel1.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 220, 330, -1));

        txtPassConfirm.setFont(new java.awt.Font("sansserif", 0, 12)); // NOI18N
        txtPassConfirm.setBorder(null);
        txtPassConfirm.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassConfirmFocusLost(evt);
            }
        });
        jPanel1.add(txtPassConfirm, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 250, 229, 30));

        jLabel15.setBackground(new java.awt.Color(7, 136, 69));
        jLabel15.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 157, 154));
        jLabel15.setText("--------------------------------------------------");
        jPanel1.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 280, 247, 8));

        btnLogin.setBackground(new java.awt.Color(0, 157, 154));
        btnLogin.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogin.setForeground(new java.awt.Color(0, 157, 154));
        btnLogin.setText("Lưu Thay Đổi");
        btnLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 350, -1, -1));

        btnLogin1.setBackground(new java.awt.Color(0, 157, 154));
        btnLogin1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        btnLogin1.setForeground(new java.awt.Color(0, 157, 154));
        btnLogin1.setText("Thoát");
        btnLogin1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLogin1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnLogin1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogin1ActionPerformed(evt);
            }
        });
        jPanel1.add(btnLogin1, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 350, 156, -1));

        lblPassCuError.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPassCuError.setForeground(new java.awt.Color(255, 0, 0));
        lblPassCuError.setText("Mật khẩu cũ không khớp !");
        jPanel1.add(lblPassCuError, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 220, -1));

        lblPass1Null.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPass1Null.setForeground(new java.awt.Color(255, 0, 0));
        lblPass1Null.setText("Mật khẩu mới không hợp lệ !");
        jPanel1.add(lblPass1Null, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 220, -1));

        lblPass2Null.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPass2Null.setForeground(new java.awt.Color(255, 0, 0));
        lblPass2Null.setText("Nhập lại mật khẩu không khớp !");
        jPanel1.add(lblPass2Null, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 220, -1));

        lblPassCuNull.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPassCuNull.setForeground(new java.awt.Color(255, 0, 0));
        lblPassCuNull.setText("Mật khẩu cũ không hợp lệ !");
        jPanel1.add(lblPassCuNull, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, 220, -1));

        lblPass1Error.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPass1Error.setForeground(new java.awt.Color(255, 0, 0));
        lblPass1Error.setText("Mật khẩu mới không được trùng với mật khẩu cũ !");
        jPanel1.add(lblPass1Error, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 300, -1, -1));

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (!Check()) {
            doiMatKhau();
        }
    }//GEN-LAST:event_btnLoginActionPerformed

    private void btnLogin1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogin1ActionPerformed
        dispose();
    }//GEN-LAST:event_btnLogin1ActionPerformed

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

    private void txtPassCuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassCuFocusLost
        if (!txtPassCu.getText().trim().isEmpty()) {
            Hide();
        }
    }//GEN-LAST:event_txtPassCuFocusLost

    private void txtPassmoiFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassmoiFocusLost
        if (!txtPassmoi.getText().trim().isEmpty()) {
            Hide();
        }
    }//GEN-LAST:event_txtPassmoiFocusLost

    private void txtPassConfirmFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassConfirmFocusLost
        if (!txtPassConfirm.getText().trim().isEmpty()) {
            Hide();
        }
    }//GEN-LAST:event_txtPassConfirmFocusLost

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
            java.util.logging.Logger.getLogger(ChangePassForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChangePassForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChangePassForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChangePassForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ChangePassForm dialog = new ChangePassForm(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnLogin;
    private javax.swing.JButton btnLogin1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblPass1Error;
    private javax.swing.JLabel lblPass1Null;
    private javax.swing.JLabel lblPass2Null;
    private javax.swing.JLabel lblPassCuError;
    private javax.swing.JLabel lblPassCuNull;
    private javax.swing.JPasswordField txtPassConfirm;
    private javax.swing.JPasswordField txtPassCu;
    private javax.swing.JPasswordField txtPassmoi;
    // End of variables declaration//GEN-END:variables
}
