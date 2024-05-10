package UI;

import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import SQL.JDBCHelper;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import java.awt.Toolkit;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 * @author NHUTLQ
 */
public class Frame_LichTrinhDiChuyen extends javax.swing.JFrame {

    public Frame_LichTrinhDiChuyen() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        fillCboxLichTrinh();
        AutoCompleteDecorator.decorate(cboFind);
        tblLTDiChuyen.setDefaultEditor(Object.class, null);
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

    private void editColumnWidth() {
        int[] col = new int[]{50, 180, 270, 180, 250, 300};
        new DungChung.DungChung().editColumnWidth(col, tblLTDiChuyen);
    }

    public void fillCboxLichTrinh() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboFind.getModel();
        model.removeAllElements();
        try {
            String sql = "SELECT t.TenTour, MaLT FROM LICHTRINH lt INNER JOIN KHACHHANG kh ON kh.MaKH = lt.MaKH \n"
                    + "INNER JOIN dbo.TOUR t\n"
                    + "ON t.MaTour = lt.MaTour\n";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                model.addElement(rs.getString(1) + " - " + rs.getString(2));
            }
            fillTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillTable() {
        String cbo = (String) cboFind.getSelectedItem();
        String MaLT = cbo.substring(cbo.lastIndexOf("-") + 1).trim();
        int tongslkh = 0;

        try {
            String[] header = new String[]{"STT", "TT Tour", "TT Khách Hàng", "TT Phương Tiện", "TT Địa Điểm Tham Quan", "TT Khách Sạn (Điểm Cuối)"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY ltkh.MaLT) AS STT,\n"
                    + "CONCAT(lt.MaLT, ' - ', t.TenTour),\n"
                    + "CONCAT(kh.TenKH, ' - ', kh.GTTT),\n"
                    + "CONCAT(pt.BienSo, ' - ', pt.SDT),\n"
                    + "CONCAT(tq.TenDD, ' - ', FORMAT(tq.Gia, '#,###'), ' VNĐ'),\n"
                    + "CONCAT(ks.TenKS, ' - ', ks.DiaChi, ' - ', ks.SDT)\n"
                    + "FROM dbo.LICHTRINH_KHACHHANG ltkh\n"
                    + "JOIN dbo.LICHTRINH lt ON lt.MaLT = ltkh.MaLT\n"
                    + "JOIN dbo.KHACHHANG kh ON kh.MaKH = ltkh.MaKH\n"
                    + "JOIN dbo.LICHTRINH_KHACHSAN ltks ON ltks.MaLT = lt.MaLT\n"
                    + "JOIN dbo.KHACHSAN ks ON ks.MaKS = ltks.MaKS\n"
                    + "JOIN dbo.LICHTRINH_PHUONGTIEN ltpt ON ltpt.MaLT = lt.MaLT\n"
                    + "JOIN dbo.PHUONGTIEN pt ON pt.MaPT = ltpt.MaPT\n"
                    + "JOIN dbo.LICHTRINH_THAMQUAN lttq ON lttq.MaLT = lt.MaLT\n"
                    + "JOIN dbo.THAMQUAN tq ON tq.MaDD = lttq.MaDD\n"
                    + "JOIN dbo.TOUR t ON t.MaTour = lt.MaTour\n"
                    + "WHERE ltkh.MaLT = ?";
            ResultSet rs = JDBCHelper.query(
                    sql,
                    MaLT
            );
            while (rs.next()) {
                Vector data = new Vector();
                tongslkh = rs.getInt(1);
                for (int i = 0; i < header.length; i++) {
                    data.add(rs.getObject(i + 1));
                }
                model.addRow(data);
            }
            tblLTDiChuyen.setModel(model);
            editColumnWidth();
//            lblTongKH.setText(tongslkh + " Khách Hàng");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void ExportExcel() {
        try {
            AddLichSuHD.addLSHD("Xuất File Excel");
            new DungChung.ExportExcel().exportExcel("DANH SÁCH BẢNG LỊCH TRÌNH DI CHUYỂN TOUR", "Nhân viên", tblLTDiChuyen, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000});
        } catch (Exception e) {
            MsgBox.alert(this, "Chưa Có Dữ Liệu Trong Bảng");
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dateChooser = new com.raven.datechooser.DateChooser();
        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        cboFind = new CboAndTxtSugestion.ComboBoxSuggestion();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLTDiChuyen = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        lblLoiGiaTour2 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        cboFind.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        cboFind.setForeground(new java.awt.Color(190, 79, 60));
        cboFind.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboFindActionPerformed(evt);
            }
        });
        QLNV.add(cboFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 30, 240, 30));

        tblLTDiChuyen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã LT", "Mã HDViên", "Mã KHàng", "Mã Tour", "Ngày BĐ", "Ngày KT", "Tên HDViên", "Phụ Thu", "Số Khách", "Địa Điểm", "Phương Tiện"
            }
        ));
        tblLTDiChuyen.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(tblLTDiChuyen);
        if (tblLTDiChuyen.getColumnModel().getColumnCount() > 0) {
            tblLTDiChuyen.getColumnModel().getColumn(0).setHeaderValue("STT");
            tblLTDiChuyen.getColumnModel().getColumn(1).setHeaderValue("Mã LT");
            tblLTDiChuyen.getColumnModel().getColumn(2).setHeaderValue("Mã HDViên");
            tblLTDiChuyen.getColumnModel().getColumn(3).setHeaderValue("Mã KHàng");
            tblLTDiChuyen.getColumnModel().getColumn(4).setHeaderValue("Mã Tour");
            tblLTDiChuyen.getColumnModel().getColumn(5).setHeaderValue("Ngày BĐ");
            tblLTDiChuyen.getColumnModel().getColumn(6).setHeaderValue("Ngày KT");
            tblLTDiChuyen.getColumnModel().getColumn(7).setHeaderValue("Tên HDViên");
            tblLTDiChuyen.getColumnModel().getColumn(8).setHeaderValue("Phụ Thu");
            tblLTDiChuyen.getColumnModel().getColumn(9).setHeaderValue("Số Khách");
            tblLTDiChuyen.getColumnModel().getColumn(10).setHeaderValue("Địa Điểm");
            tblLTDiChuyen.getColumnModel().getColumn(11).setHeaderValue("Phương Tiện");
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(35, 82, 1210, 620));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN LỊCH TRÌNH DI CHUYỂN");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, 30));

        lblLoiGiaTour2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaTour2.setForeground(new java.awt.Color(255, 0, 0));
        QLNV.add(lblLoiGiaTour2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 220, 180, 20));

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
        QLNV.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1130, 710, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(190, 79, 60));
        jLabel8.setText("Tìm Kiếm");
        QLNV.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 30, 80, 30));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(QLNV, javax.swing.GroupLayout.DEFAULT_SIZE, 1403, Short.MAX_VALUE)
        );
        pnlCardNhanVienLayout.setVerticalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlCardNhanVienLayout.createSequentialGroup()
                .addComponent(QLNV, javax.swing.GroupLayout.PREFERRED_SIZE, 834, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1516, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(pnlCardNhanVien, javax.swing.GroupLayout.PREFERRED_SIZE, 1403, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 113, Short.MAX_VALUE)))
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

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void cboFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboFindActionPerformed
        fillTable();
    }//GEN-LAST:event_cboFindActionPerformed

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
            java.util.logging.Logger.getLogger(Frame_LichTrinhDiChuyen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_LichTrinhDiChuyen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_LichTrinhDiChuyen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_LichTrinhDiChuyen.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_LichTrinhDiChuyen().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnExcel;
    private CboAndTxtSugestion.ComboBoxSuggestion cboFind;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiGiaTour2;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblLTDiChuyen;
    // End of variables declaration//GEN-END:variables
}
