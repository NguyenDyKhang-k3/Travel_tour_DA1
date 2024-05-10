package UI;

import DAO.LichSuHoatDongDAO;
import DAO.NhanVienDAO;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import TravelEntity.NhanVien;
import java.awt.Toolkit;
import java.util.List;
import java.util.prefs.Preferences;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * @author NHUTLQ
 */
public class LoginForm extends javax.swing.JFrame {

    LichSuHoatDongDAO lsDao = new LichSuHoatDongDAO();
    NhanVienDAO dao = new NhanVienDAO();
    Preferences preferences;
    boolean rememberPreferences;
    int dem;
    int i = 60;

    public LoginForm() {
        initComponents();
        init();
    }

    private void rememberMe() {
        preferences = Preferences.userNodeForPackage(this.getClass());
        rememberPreferences = preferences.getBoolean("rememberMe", Boolean.valueOf(""));
        if (rememberPreferences) {
            txtUser.setText(preferences.get("User", ""));
            txtPass.setText(preferences.get("Password", ""));
            chkRMB.setSelected(rememberPreferences);
        }
    }

    public void init() {
        lblHide.setVisible(false);
        Hide();
        rememberMe();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void Hide() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{lblLoiTenDn1, lblErrorNhanVien, lblLoiTenDN1, lblLoiTenDN2, lblLoiTenDN3, lblSaiPass2});
    }

    public boolean check() {
        if (txtUser.getText().trim().isEmpty() || txtUser.getText().equals("Tên Đăng Nhập") || txtUser.getText().length() < 5) {
            lblLoiTenDn1.setVisible(true);
            txtUser.requestFocus();
            return true;
        }
        if (txtPass.getText().trim().isEmpty() || txtPass.getText().equals("Mật Khẩu") || txtPass.getText().length() < 5) {
            lblSaiPass2.setVisible(true);
            txtPass.requestFocus();
            return true;
        }
        Hide();
        return false;
    }

    public void dangNhap() {

        NhanVien nhanVien = dao.selectById(txtUser.getText());
        List<NhanVien> nv = dao.selectMaNV(txtUser.getText());
        String trangThai = null;

        if (nhanVien != null) {
            if (!chkRMB.isSelected() && rememberPreferences) {
                preferences.put("User", "");
                preferences.put("Password", "");
                preferences.putBoolean("rememberMe", false);
            } else {
                preferences.put("User", txtUser.getText());
                preferences.put("Password", txtPass.getText());
                preferences.putBoolean("rememberMe", true);
            }

            if (!txtUser.getText().equals(nhanVien.getMaNV())) {
                lblLoiTenDN2.setVisible(true);
            } else if (!txtPass.getText().equals(nhanVien.getMatKhau())) {
                lblLoiTenDN2.setVisible(true);
            } else {
                for (NhanVien nhanVien1 : nv) {
                    trangThai = nhanVien1.getTrangThai();
                }

                if (trangThai.equals("Đang Làm Việc")) {
//                    new Loading(this, true).setVisible(true);
                    new MainForm().setVisible(true);
                    Auth.user = nhanVien;
                    AddLichSuHD.addLSHD("Đăng Nhập Vào Hệ Thống Travel Tour");
                    dispose();
                } else {
                    lblErrorNhanVien.setVisible(true);
                }
            }
        } else {
            dem++;
            if (dem == 1) {
                lblLoiTenDN2.setVisible(true);
                return;
            }
            if (dem == 2) {
                lblLoiTenDN2.setVisible(true);
                return;
            }
            if (dem == 3) {
                lblLoiTenDN2.setVisible(true);
                return;
            }
            if (dem == 4) {
                lblLoiTenDN3.setVisible(true);
                btnLogin.setEnabled(false);
                checked60s();
            }
        }
    }

    private void checked60s() {
        Thread t = new Thread() {
            public void run() {
                while (true) {
                    try {
                        btnLogin.setText("Vui Lòng Chờ " + i + " Giây!");
                        Thread.sleep(1000);
                        i--;
                        if (i == 0) {
                            btnLogin.setEnabled(true);
                            btnLogin.setText("Đăng Nhập");
                            return;
                        }
                    } catch (Exception ex) {
                        break;
                    }
                }
            }
        };
        t.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Body = new javax.swing.JPanel();
        left = new javax.swing.JPanel();
        animationLogin = new javax.swing.JLabel();
        btnLogin = new rojerusan.RSMaterialButtonRectangle();
        jLabel1 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        lms_icon = new javax.swing.JLabel();
        padding = new javax.swing.JLabel();
        txtPass = new rojerusan.RSPasswordTextPlaceHolder();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        chkRMB = new javax.swing.JCheckBox();
        lblHide = new javax.swing.JLabel();
        lblShow = new javax.swing.JLabel();
        lblLoiTenDn1 = new javax.swing.JLabel();
        lblErrorNhanVien = new javax.swing.JLabel();
        lblLoiTenDN1 = new javax.swing.JLabel();
        lblLoiTenDN2 = new javax.swing.JLabel();
        lblLoiTenDN3 = new javax.swing.JLabel();
        lblSaiPass2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        Body.setBackground(new java.awt.Color(255, 255, 255));

        left.setBackground(new java.awt.Color(229, 77, 66));
        left.setLayout(null);

        animationLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Images/red login form.gif"))); // NOI18N
        left.add(animationLogin);
        animationLogin.setBounds(-170, 0, 620, 480);

        btnLogin.setBackground(new java.awt.Color(229, 77, 66));
        btnLogin.setText("Đăng Nhập");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Đăng Nhập");

        txtUser.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtUser.setForeground(new java.awt.Color(169, 169, 169));
        txtUser.setText("Tên Đăng Nhập");
        txtUser.setToolTipText("Nhập Tên Đăng Nhập");
        txtUser.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtUser.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtUserFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtUserFocusLost(evt);
            }
        });

        lms_icon.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(204, 204, 204)));

        padding.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 0, new java.awt.Color(204, 204, 204)));

        txtPass.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 0, new java.awt.Color(204, 204, 204)));
        txtPass.setForeground(new java.awt.Color(169, 169, 169));
        txtPass.setText("Mật Khẩu");
        txtPass.setToolTipText("Nhập Mật Khẩu");
        txtPass.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        txtPass.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtPassFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPassFocusLost(evt);
            }
        });

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/pass.png"))); // NOI18N
        jLabel2.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(204, 204, 204)));
        jLabel2.setOpaque(true);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/user.png"))); // NOI18N
        jLabel3.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 0, 1, 1, new java.awt.Color(204, 204, 204)));
        jLabel3.setOpaque(true);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 30)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("-");
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 17)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("X");
        jLabel7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel7MouseClicked(evt);
            }
        });

        chkRMB.setBackground(new java.awt.Color(255, 255, 255));
        chkRMB.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        chkRMB.setForeground(new java.awt.Color(169, 169, 169));
        chkRMB.setText("Nhớ Mật Khẩu");

        lblHide.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eye-crossed.png"))); // NOI18N
        lblHide.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblHideMousePressed(evt);
            }
        });

        lblShow.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/eye.png"))); // NOI18N
        lblShow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                lblShowMousePressed(evt);
            }
        });

        lblLoiTenDn1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenDn1.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenDn1.setText("Tên đăng nhập phải nhiều hơn 5 kí tự !");

        lblErrorNhanVien.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblErrorNhanVien.setForeground(new java.awt.Color(255, 0, 0));
        lblErrorNhanVien.setText("Tài Khoản Đã Bị Tước Quyền Hoạt Động");

        lblLoiTenDN1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenDN1.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenDN1.setText("Tên đăng nhập không tồn tại trong CSDL !");

        lblLoiTenDN2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenDN2.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenDN2.setText("Sai tên đăng nhập hoặc mật khẩu !");

        lblLoiTenDN3.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiTenDN3.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiTenDN3.setText("Nhập sai thông tin quá nhiều lần !");

        lblSaiPass2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSaiPass2.setForeground(new java.awt.Color(255, 0, 0));
        lblSaiPass2.setText("Mật khẩu phải nhiều hơn 5 kí tự !");

        javax.swing.GroupLayout BodyLayout = new javax.swing.GroupLayout(Body);
        Body.setLayout(BodyLayout);
        BodyLayout.setHorizontalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 439, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(BodyLayout.createSequentialGroup()
                        .addGap(336, 336, 336)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(BodyLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(lms_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(BodyLayout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblSaiPass2)
                            .addGroup(BodyLayout.createSequentialGroup()
                                .addComponent(padding, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(6, 6, 6)
                                .addComponent(lblShow)
                                .addGap(0, 0, 0)
                                .addComponent(lblHide))
                            .addComponent(lblLoiTenDn1)
                            .addComponent(lblLoiTenDN2)
                            .addComponent(lblErrorNhanVien)
                            .addComponent(lblLoiTenDN1)
                            .addComponent(lblLoiTenDN3)
                            .addComponent(chkRMB)
                            .addGroup(BodyLayout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))))))
        );
        BodyLayout.setVerticalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(left, javax.swing.GroupLayout.PREFERRED_SIZE, 480, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(BodyLayout.createSequentialGroup()
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lms_icon, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(padding, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtPass, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblShow, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblHide, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(lblSaiPass2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoiTenDn1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoiTenDN2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblErrorNhanVien)
                .addGap(5, 5, 5)
                .addComponent(lblLoiTenDN1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblLoiTenDN3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(chkRMB)
                .addGap(10, 10, 10)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        txtUser.getAccessibleContext().setAccessibleName("");
        jLabel3.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(837, 480));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUserFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserFocusGained
        if (txtUser.getText().equals("Tên Đăng Nhập")) {
            txtUser.setText("");
        }

        if (txtPass.getText().equals("Mật Khẩu")) {
            txtPass.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_txtUserFocusGained

    private void txtUserFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtUserFocusLost
        if (!txtUser.getText().trim().isEmpty() || !txtUser.getText().equals("Tên Đăng Nhập") || txtUser.getText().length() > 5) {
            Hide();
        }

        if (txtUser.getText().equals("")) {
            txtUser.setText("Tên Đăng Nhập");
        }

        if (txtPass.getText().equals("")) {
            txtPass.setEchoChar((char) 0);
        }
    }//GEN-LAST:event_txtUserFocusLost

    private void txtPassFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusGained
        if (txtPass.getText().equals("Mật Khẩu")) {
            txtPass.setText("");
            txtPass.setEchoChar('•');
        }
    }//GEN-LAST:event_txtPassFocusGained

    private void txtPassFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPassFocusLost
        if (!txtPass.getText().trim().isEmpty() || !txtPass.getText().equals("Mật Khẩu") || txtPass.getText().length() > 5) {
            Hide();
        }

        if (txtPass.getText().equals("")) {
            txtPass.setText("Mật Khẩu");
            txtPass.setEchoChar('•');
        }
    }//GEN-LAST:event_txtPassFocusLost

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        this.setState(JFrame.ICONIFIED);
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel7MouseClicked
        System.exit(0);
    }//GEN-LAST:event_jLabel7MouseClicked

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        if (!check()) {
            dangNhap();
        }
    }//GEN-LAST:event_btnLoginActionPerformed

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

    private void lblShowMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblShowMousePressed
        this.lblHide.setVisible(true);
        this.lblShow.setVisible(false);
        txtPass.setEchoChar((char) 0);
    }//GEN-LAST:event_lblShowMousePressed

    private void lblHideMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblHideMousePressed
        this.lblShow.setVisible(true);
        this.lblHide.setVisible(false);
        txtPass.setEchoChar('•');
    }//GEN-LAST:event_lblHideMousePressed

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
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginForm.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginForm().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Body;
    private javax.swing.JLabel animationLogin;
    private rojerusan.RSMaterialButtonRectangle btnLogin;
    private javax.swing.JCheckBox chkRMB;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel lblErrorNhanVien;
    private javax.swing.JLabel lblHide;
    private javax.swing.JLabel lblLoiTenDN1;
    private javax.swing.JLabel lblLoiTenDN2;
    private javax.swing.JLabel lblLoiTenDN3;
    private javax.swing.JLabel lblLoiTenDn1;
    private javax.swing.JLabel lblSaiPass2;
    private javax.swing.JLabel lblShow;
    private javax.swing.JPanel left;
    private javax.swing.JLabel lms_icon;
    private javax.swing.JLabel padding;
    private rojerusan.RSPasswordTextPlaceHolder txtPass;
    public static javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
