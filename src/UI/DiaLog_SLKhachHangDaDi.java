package UI;

import SQL.JDBCHelper;
import static UI.MainForm.tbpMenu;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

/**
 * @author NHUTLQ
 */
public class DiaLog_SLKhachHangDaDi extends javax.swing.JDialog {

    int row = -1;

    public DiaLog_SLKhachHangDaDi(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        lblTenTitle.setText("Tổng Số Lượng Các Tour Mà Khách Hàng Đã Đi");
        fillTable();
        tblLS.setDefaultEditor(Object.class, null);
    }

    private void editColumnWidth() {
        int[] col = new int[]{50, 400, 400};
        new DungChung.DungChung().editColumnWidth(col, tblLS);
    }

    public void fillTable() {
        try {
            String[] header = new String[]{"STT", "Thông Tin Lịch Trình", "Thông Tin Khách Hàng"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY kh.MaKH) STT, CONCAT(ltkh.MaLT, ' - ', t.TenTour), CONCAT(ltkh.MaKH, ' - ', kh.TenKH)\n"
                    + "FROM LICHTRINH_KHACHHANG ltkh\n"
                    + "JOIN KHACHHANG kh ON ltkh.MaKH = kh.MaKH \n"
                    + "JOIN dbo.LICHTRINH lt ON lt.MaLT = ltkh.MaLT\n"
                    + "JOIN dbo.TOUR t ON t.MaTour = lt.MaTour\n"
                    + "WHERE kh.MaKH = ?\n"
                    + "GROUP BY kh.MaKH, CONCAT(ltkh.MaLT, ' - ', t.TenTour),  CONCAT(ltkh.MaKH, ' - ', kh.TenKH)";
            ResultSet rs = new JDBCHelper().query(sql, Frame_TTKhachHang.txtMaKH.getText());
            while (rs.next()) {
                Vector data = new Vector();
                for (int i = 0; i < header.length; i++) {
                    data.add(rs.getObject(i + 1));
                }
                model.addRow(data);
            }
            tblLS.setModel(model);
            editColumnWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLS = new DungChung.Table();
        lblTenTitle = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Thông Tin Khách Hàng Đã Đi Tour");

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        tblLS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblLS.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblLS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLSMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLS);

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 80, 760, 600));

        lblTenTitle.setFont(new java.awt.Font("Segoe UI", 1, 20)); // NOI18N
        lblTenTitle.setForeground(new java.awt.Color(190, 79, 60));
        lblTenTitle.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTenTitle.setText("Tổng Số Lượng Tour Khách Hàng Đã Đi");
        QLNV.add(lblTenTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 10, 780, 60));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlCardNhanVienLayout.setVerticalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 781, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 781, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 687, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 687, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void tblLSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLSMouseClicked
        if (evt.getClickCount() == 1) {
            dispose();
            tbpMenu.removeAll();
            Frame_TTLichTrinh ltks = new Frame_TTLichTrinh();
            tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);
            row = tblLS.getSelectedRow();

            String subString = tblLS.getValueAt(row, 1).toString();
            String malt = subString.substring(0, subString.indexOf("-")).trim();
            Frame_TTLichTrinh.txtFind.setText(malt);
        }
    }//GEN-LAST:event_tblLSMouseClicked

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
            java.util.logging.Logger.getLogger(DiaLog_SLKhachHangDaDi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DiaLog_SLKhachHangDaDi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DiaLog_SLKhachHangDaDi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DiaLog_SLKhachHangDaDi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                DiaLog_SLKhachHangDaDi dialog = new DiaLog_SLKhachHangDaDi(new javax.swing.JFrame(), true);
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
    private javax.swing.JPanel QLNV;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblTenTitle;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblLS;
    // End of variables declaration//GEN-END:variables
}
