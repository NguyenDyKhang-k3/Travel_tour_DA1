package UI;

import DAO.DoanhThuDAO;
import DungChung.AddLichSuHD;
import DungChung.BieuDo2TrungTung;
import SQL.JDBCHelper;
import java.awt.Toolkit;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;

/**
 * @author NHUTLQ
 */
public class Frame_ThongKeDoanhThu extends javax.swing.JFrame {

    public Frame_ThongKeDoanhThu() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        fillComBoBoxNam();
        loadChart();
        loadTable_DoanhThu();
        cboQuy.setSelectedItem("2");
        cboNam.setSelectedItem("2023");
    }

    public void editColumnWidth() {
        int[] col = new int[]{50, 80, 170, 130, 120, 100, 250};
        new DungChung.DungChung().editColumnWidth(col, tblDoanhthu);
    }

    public void fillComBoBoxNam() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboNam.getModel();
        model.removeAllElements();
        try {
            String sql = "SELECT YEAR(NgayLapHoaDon) FROM dbo.HOADON GROUP BY YEAR(NgayLapHoaDon)";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {
                model.addElement(rs.getInt(1));
            }
            loadTable_DoanhThu();
            loadChart();
        } catch (Exception e) {
        }
    }

    public void loadTable_DoanhThu() {
        try {
            int quy = 0;
            int nam = 0;
            if (cboQuy.getSelectedItem() != null) {
                quy = Integer.parseInt(String.valueOf(cboQuy.getSelectedItem()));
            }
            if (cboNam.getSelectedItem() != null) {
                nam = Integer.parseInt(String.valueOf(cboNam.getSelectedItem()));
            }
            new DoanhThuDAO().loadTable_DoanhThu(tblDoanhthu, quy, nam);
            editColumnWidth();

            new DoanhThuDAO().TongTienQuy(lblTongTourMo, lblTongDTQuy, quy, nam);

            int tongSLKH = 0;
            for (int i = 0; i < tblDoanhthu.getRowCount(); i++) {
                tongSLKH += Integer.parseInt(tblDoanhthu.getValueAt(i, 5).toString());
            }
//            lblTongKH.setText(tongSLKH + " Khách Hàng");
        } catch (Exception e) {
        }
    }

    public void loadChart() {
        pnlBieuDo.removeAll();
        int quy = Integer.parseInt(String.valueOf(cboQuy.getSelectedItem()));
        int nam = Integer.parseInt(String.valueOf(cboNam.getSelectedItem()));
        new DoanhThuDAO().loadChartDoanhThang(pnlBieuDo, quy, nam);
        new DoanhThuDAO().loadChartDoanhCaoNhat(pnlBieuDo1, quy, nam);

        loadChart2TrucTung(nam);
    }

    public void loadChart2TrucTung(int nam) {
        pnlChart.removeAll();
        new BieuDo2TrungTung("", pnlChart, nam);
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("BẢNG THỐNG KÊ DOANH THU TOUR DU LỊCH NĂM 2023", "Nhân viên", tblDoanhthu, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new DungChung.ExportPDF().exportPDF("BẢNG THỐNG KÊ DOANH THU TOUR DU LỊCH NĂM 2023", tblDoanhthu);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        pnlBieuDo = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblDoanhthu = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        cboQuy = new CboAndTxtSugestion.ComboBoxSuggestion();
        cboNam = new CboAndTxtSugestion.ComboBoxSuggestion();
        cc = new javax.swing.JLabel();
        lblTongTourMo = new javax.swing.JLabel();
        pnlChart = new javax.swing.JPanel();
        qwd1 = new javax.swing.JLabel();
        lblTongDTQuy = new javax.swing.JLabel();
        pnlBieuDo1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlBieuDo.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlBieuDoLayout = new javax.swing.GroupLayout(pnlBieuDo);
        pnlBieuDo.setLayout(pnlBieuDoLayout);
        pnlBieuDoLayout.setHorizontalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlBieuDoLayout.setVerticalGroup(
            pnlBieuDoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        QLNV.add(pnlBieuDo, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 410, 340, 290));

        tblDoanhthu.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Tên Tour", "Giá Bán", "Số Khách", "Thành Tiền"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblDoanhthu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(tblDoanhthu);

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 410, 820, 290));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("THỐNG KÊ DOANH THU THEO QUÝ TRONG NĂM ");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 30));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Quý");
        QLNV.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 20, -1, 30));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Năm");
        QLNV.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(1070, 20, -1, 30));

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
        QLNV.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 710, -1, -1));

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
        QLNV.add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 710, -1, -1));

        cboQuy.setForeground(new java.awt.Color(190, 79, 60));
        cboQuy.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "1", "2", "3", "4", " " }));
        cboQuy.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboQuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboQuyActionPerformed(evt);
            }
        });
        QLNV.add(cboQuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 20, 120, 30));

        cboNam.setForeground(new java.awt.Color(190, 79, 60));
        cboNam.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboNam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cboNamActionPerformed(evt);
            }
        });
        QLNV.add(cboNam, new org.netbeans.lib.awtextra.AbsoluteConstraints(1110, 20, 120, 30));

        cc.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        cc.setForeground(new java.awt.Color(190, 79, 60));
        cc.setText("Tổng Số Tour Đã Mở:");
        QLNV.add(cc, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 720, -1, 20));

        lblTongTourMo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongTourMo.setForeground(new java.awt.Color(190, 79, 60));
        lblTongTourMo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTongTourMo.setText("000000");
        QLNV.add(lblTongTourMo, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 720, 60, 20));

        pnlChart.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlChartLayout = new javax.swing.GroupLayout(pnlChart);
        pnlChart.setLayout(pnlChartLayout);
        pnlChartLayout.setHorizontalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        pnlChartLayout.setVerticalGroup(
            pnlChartLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        QLNV.add(pnlChart, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, 820, 300));

        qwd1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        qwd1.setForeground(new java.awt.Color(190, 79, 60));
        qwd1.setText("Tổng Doanh Thu Quý:");
        QLNV.add(qwd1, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 720, -1, 20));

        lblTongDTQuy.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblTongDTQuy.setForeground(new java.awt.Color(190, 79, 60));
        lblTongDTQuy.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTongDTQuy.setText("000000");
        QLNV.add(lblTongDTQuy, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 720, 130, 20));

        pnlBieuDo1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pnlBieuDo1Layout = new javax.swing.GroupLayout(pnlBieuDo1);
        pnlBieuDo1.setLayout(pnlBieuDo1Layout);
        pnlBieuDo1Layout.setHorizontalGroup(
            pnlBieuDo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 340, Short.MAX_VALUE)
        );
        pnlBieuDo1Layout.setVerticalGroup(
            pnlBieuDo1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        QLNV.add(pnlBieuDo1, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 80, 340, 300));

        jSeparator1.setForeground(new java.awt.Color(102, 102, 102));
        jSeparator1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        QLNV.add(jSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 390, 1180, 30));

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

    private void cboNamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboNamActionPerformed
        loadChart();
        loadTable_DoanhThu();
    }//GEN-LAST:event_cboNamActionPerformed

    private void cboQuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cboQuyActionPerformed
        loadChart();
        loadTable_DoanhThu();
    }//GEN-LAST:event_cboQuyActionPerformed

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
            java.util.logging.Logger.getLogger(Frame_ThongKeDoanhThu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_ThongKeDoanhThu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_ThongKeDoanhThu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_ThongKeDoanhThu.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_ThongKeDoanhThu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnPDF;
    public static CboAndTxtSugestion.ComboBoxSuggestion cboNam;
    private CboAndTxtSugestion.ComboBoxSuggestion cboQuy;
    private javax.swing.JLabel cc;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblTongDTQuy;
    private javax.swing.JLabel lblTongTourMo;
    private javax.swing.JPanel pnlBieuDo;
    private javax.swing.JPanel pnlBieuDo1;
    private javax.swing.JPanel pnlCardNhanVien;
    private javax.swing.JPanel pnlChart;
    private javax.swing.JLabel qwd1;
    private DungChung.Table tblDoanhthu;
    // End of variables declaration//GEN-END:variables
}
