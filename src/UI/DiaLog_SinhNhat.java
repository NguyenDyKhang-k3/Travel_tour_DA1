package UI;

import DAO.KhachHangDAO;
import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import DungChung.SendEmail;
import DungChung.XDate;
import TravelEntity.KhachHang;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author NHUTLQ
 */
public class DiaLog_SinhNhat extends javax.swing.JDialog {

    KhachHangDAO dao = new KhachHangDAO();

    public DiaLog_SinhNhat(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        initDate();
        fillTable();
        editColumnWidth();
    }

    //lấy ngày hiện tại fill lên form
    private void initDate() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM");
        String date = dateObj.format(formatter);
        lblDate.setText(date);
    }

    private void editColumnWidth() {
        int[] col = new int[]{50, 80, 220, 100, 120, 250, 100};
        new DungChung.DungChung().editColumnWidth(col, tblSN);
    }

    void fillTable() {
        int i = 1;
        DefaultTableModel model = (DefaultTableModel) tblSN.getModel();
        model.setRowCount(0);
        try {
            List<KhachHang> list = dao.fillTableBriday();
            for (KhachHang kh : list) {
                Object[] row = {
                    String.valueOf(i++),
                    kh.getMaKH(),
                    kh.getTenKH(),
                    XDate.toString(kh.getNS(), "dd/MM/yyyy"),
                    kh.getCCCD(),
                    kh.getEmail(),
                    kh.getSDT()
                };
                model.addRow(row);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnEmail = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSN = new DungChung.Table();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Sinh Nhật Khách Hàng");

        btnEmail.setBackground(new java.awt.Color(242, 242, 242));
        btnEmail.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btnEmail.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgGuiEmail.png"))); // NOI18N
        btnEmail.setBorder(null);
        btnEmail.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnEmailMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnEmailMouseExited(evt);
            }
        });
        btnEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmailActionPerformed(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(190, 79, 60));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("Danh Sách Khách Hàng Có Ngày Sinh Vào Ngày");

        lblDate.setFont(new java.awt.Font("Arial", 1, 20)); // NOI18N
        lblDate.setForeground(new java.awt.Color(190, 79, 60));
        lblDate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDate.setText("11-11");

        tblSN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã KH", "Tên KH", "Ngày Sinh", "CMND/CCCD", "Email", "SĐT"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSN.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(tblSN);
        if (tblSN.getColumnModel().getColumnCount() > 0) {
            tblSN.getColumnModel().getColumn(0).setPreferredWidth(0);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(209, 209, 209)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 918, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEmail)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(lblDate))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 372, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEmail)
                .addGap(17, 17, 17))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmailActionPerformed
        DefaultTableModel model = (DefaultTableModel) tblSN.getModel();
        if (model.getRowCount() == 0) {
            MsgBox.alertInfo(new Frame_TTKhachHang(), "Chưa Có Thông Tin Trong Bảng !");
            return;
        }
        try {
            List<KhachHang> list = dao.fillTableBriday();
            for (KhachHang khachHang : list) {
                SendEmail.sendMail(khachHang.getEmail());
            }
            MsgBox.alertSuccess(new Frame_TTKhachHang(), "Gửi Mail Thành Công !");
            AddLichSuHD.addLSHD("Gửi Email chúc mừng sinh nhật");
            model.setRowCount(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            MsgBox.alertWarning(new Frame_TTKhachHang(), "Thiết Bị Không Có Kết Nối Internet !");
        }
    }//GEN-LAST:event_btnEmailActionPerformed

    private void btnEmailMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmailMouseEntered
        new DungChung.DungChung().hoverButton(1, btnEmail, "bgGuiEmail-Hover.png");
    }//GEN-LAST:event_btnEmailMouseEntered

    private void btnEmailMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEmailMouseExited
        new DungChung.DungChung().hoverButton(2, btnEmail, "bgGuiEmail.png");
    }//GEN-LAST:event_btnEmailMouseExited

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
            java.util.logging.Logger.getLogger(DiaLog_SinhNhat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiaLog_SinhNhat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiaLog_SinhNhat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiaLog_SinhNhat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DiaLog_SinhNhat dialog = new DiaLog_SinhNhat(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnEmail;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDate;
    private DungChung.Table tblSN;
    // End of variables declaration//GEN-END:variables
}
