package UI;

import DAO.LichSuHoatDongDAO;
import DungChung.AddLichSuHD;
import DungChung.MsgBox;
import DungChung.XDate;
import SQL.JDBCHelper;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author NHUTLQ
 */
public class Frame_LichSuHoatDong extends javax.swing.JFrame {

    LichSuHoatDongDAO lsDao = new LichSuHoatDongDAO();

    public Frame_LichSuHoatDong() {
        initComponents();
        init();
        editColumnWidth();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        fillTable();
        fillCuoiDong();
        tblLS.setDefaultEditor(Object.class, null);
    }

    public void fillCuoiDong() {
        int lastRow = tblLS.getRowCount() - 1;

        tblLS.setCellSelectionEnabled(true);
        tblLS.changeSelection(lastRow, lastRow, false, false);
        tblLS.scrollRectToVisible(new Rectangle(tblLS.getCellRect(lastRow, 0, true)));
    }

    public static void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                table.scrollRectToVisible(table.getCellRect(rowIndex, vColIndex, false));
            }
        });
    }

    public void fillTable() {
        try {
            String[] header = new String[]{"STT", "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Truy Cập", "Giờ Truy Cập", "Chức Năng Hoạt Động"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY ls.MaLS), ls.MaNV, nv.HoTen, ls.NgayTruyCap, ls.ThoiGianTruyCap, ls.HoatDong FROM dbo.LICHSUHD ls \n"
                    + "INNER JOIN dbo.NHANVIEN nv \n"
                    + "ON nv.MaNV = ls.MaNV WHERE ls.NgayTruyCap = ?";
            ResultSet rs = new JDBCHelper().query(sql, XDate.toDate(txtDate.getText(), "dd-MM-yyyy"));
            while (rs.next()) {
                Vector data = new Vector();
                for (int i = 0; i < header.length; i++) {
                    data.add(rs.getString(1));
                    data.add(rs.getString(2));
                    data.add(rs.getString(3));
                    data.add(XDate.toString(rs.getDate(4), "dd-MM-yyyy"));
                    data.add(rs.getString(5));
                    data.add(rs.getString(6));
                }
                model.addRow(data);
            }
            tblLS.setModel(model);
        } catch (Exception e) {
        }
    }

    public void deleleLichSu() {
        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắc Xoá Tất Cả Lịch Sử Truy Cập Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    lsDao.delete();
                    AddLichSuHD.addLSHD("Xoá Lịch Sử Hoạt Động");
                    fillTable();
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_LT_PhuongTien(), "Bạn Đã Xoá Dữ Liệu Thành Công");
                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
            MsgBox.alertSuccess(this, "Xoá Thất Bại");
        }
    }

    private void editColumnWidth() {
        int[] col = new int[]{50, 200, 200, 200, 200, 300};
        new DungChung.DungChung().editColumnWidth(col, tblLS);
    }

    public void ExportExcel() {
        try {
            AddLichSuHD.addLSHD("Xuất File Excel");
            new DungChung.ExportExcel().exportExcel("DANH SÁCH LỊCH SỬ HOẠT ĐỘNG TRAVEL TOUR", "Nhân viên", tblLS, new int[]{1500, 3000, 8000, 5000, 6500, 5500});
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
        btnNgayLap = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLS = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        lblLoiGiaTour2 = new javax.swing.JLabel();
        btnXoaLS = new javax.swing.JButton();
        btnExcel = new javax.swing.JButton();

        dateChooser.setTextRefernce(txtDate);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnNgayLap.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnNgayLap.setBorder(null);
        btnNgayLap.setBorderPainted(false);
        btnNgayLap.setContentAreaFilled(false);
        btnNgayLap.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgayLap.setDefaultCapable(false);
        btnNgayLap.setFocusPainted(false);
        btnNgayLap.setFocusable(false);
        btnNgayLap.setIconTextGap(0);
        btnNgayLap.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgayLapActionPerformed(evt);
            }
        });
        QLNV.add(btnNgayLap, new org.netbeans.lib.awtextra.AbsoluteConstraints(1210, 20, 30, 30));

        tblLS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Nhân Viên", "Tên Nhân Viên", "Ngày Truy Cập", "Giờ Truy Cập", "Chức Năng Truy Cập"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblLS.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        jScrollPane2.setViewportView(tblLS);
        if (tblLS.getColumnModel().getColumnCount() > 0) {
            tblLS.getColumnModel().getColumn(0).setPreferredWidth(5);
            tblLS.getColumnModel().getColumn(1).setPreferredWidth(30);
            tblLS.getColumnModel().getColumn(2).setPreferredWidth(60);
            tblLS.getColumnModel().getColumn(3).setPreferredWidth(20);
            tblLS.getColumnModel().getColumn(4).setPreferredWidth(20);
            tblLS.getColumnModel().getColumn(5).setPreferredWidth(200);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 72, 1145, 630));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN - LỊCH SỬ HOẠT ĐỘNG");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(190, 79, 60));
        jLabel8.setText("Tìm Kiếm");
        QLNV.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 30, 80, -1));

        txtDate.setBackground(new java.awt.Color(242, 242, 242));
        txtDate.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDate.setForeground(new java.awt.Color(190, 79, 60));
        txtDate.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtDate.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtDateCaretUpdate(evt);
            }
        });
        QLNV.add(txtDate, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 20, 250, 30));

        lblLoiGiaTour2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGiaTour2.setForeground(new java.awt.Color(255, 0, 0));
        QLNV.add(lblLoiGiaTour2, new org.netbeans.lib.awtextra.AbsoluteConstraints(930, 220, 180, 20));

        btnXoaLS.setForeground(new java.awt.Color(255, 255, 255));
        btnXoaLS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgXoaLSHD.png"))); // NOI18N
        btnXoaLS.setBorder(null);
        btnXoaLS.setBorderPainted(false);
        btnXoaLS.setContentAreaFilled(false);
        btnXoaLS.setDefaultCapable(false);
        btnXoaLS.setFocusPainted(false);
        btnXoaLS.setFocusable(false);
        btnXoaLS.setIconTextGap(0);
        btnXoaLS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXoaLSMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXoaLSMouseExited(evt);
            }
        });
        btnXoaLS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaLSActionPerformed(evt);
            }
        });
        QLNV.add(btnXoaLS, new org.netbeans.lib.awtextra.AbsoluteConstraints(960, 710, -1, -1));

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
        QLNV.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 710, -1, -1));

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

    private void btnNgayLapActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgayLapActionPerformed
        dateChooser.showPopup();
    }//GEN-LAST:event_btnNgayLapActionPerformed

    private void btnXoaLSMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaLSMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoaLS, "bgXoaLSHD-Hover.png");
    }//GEN-LAST:event_btnXoaLSMouseEntered

    private void btnXoaLSMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaLSMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoaLS, "bgXoaLSHD.png");
    }//GEN-LAST:event_btnXoaLSMouseExited

    private void btnXoaLSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaLSActionPerformed
        deleleLichSu();
    }//GEN-LAST:event_btnXoaLSActionPerformed

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void txtDateCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtDateCaretUpdate
        fillTable();
        fillCuoiDong();
        editColumnWidth();
    }//GEN-LAST:event_txtDateCaretUpdate

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
            java.util.logging.Logger.getLogger(Frame_LichSuHoatDong.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_LichSuHoatDong.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_LichSuHoatDong.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_LichSuHoatDong.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_LichSuHoatDong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnNgayLap;
    private javax.swing.JButton btnXoaLS;
    private com.raven.datechooser.DateChooser dateChooser;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblLoiGiaTour2;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblLS;
    private javax.swing.JTextField txtDate;
    // End of variables declaration//GEN-END:variables
}
