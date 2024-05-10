package UI;

import DAO.HoaDonDAO;
import DAO.HopDongDAO;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.MsgBox;
import DungChung.XDate;
import SQL.JDBCHelper;
import TravelEntity.HoaDon;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author khang
 */
public class Frame_TTHopDong extends javax.swing.JFrame {

    HopDongDAO hdDao = new HopDongDAO();
    HoaDonDAO hdonDAO = new HoaDonDAO();
    int row = -1;

    public Frame_TTHopDong() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        fillTable();
        tblHDong.setDefaultEditor(Object.class, null);
    }

    private void editColumnWidth() {
        int[] col = new int[]{50, 90, 250, 120, 120, 120, 100, 120, 200};
        new DungChung.DungChung().editColumnWidth(col, tblHDong);
    }

    public void fillTable() {
        try {
            String[] header = new String[]{"STT", "Mã HĐồng", "TT Lịch Trình", "Tổng Tiền", "Tiền Cọc", "Còn Lại", "TT Nhân Viên", "Ngày Lập HĐồng", "Trạng Thái Thanh Toán"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY hd.MaHopDong),\n"
                    + "hd.MaHopDong,\n"
                    + "CONCAT(t.TenTour, ' - ', hd.MaLT),\n"
                    + "hd.TongTien,\n"
                    + "hd.TienCoc,\n"
                    + "hd.MaNV,\n"
                    + "hd.NgayLapHopDong,\n"
                    + "hd.TrangThaiThanhToan\n"
                    + "FROM dbo.HOPDONG hd\n"
                    + "JOIN dbo.LICHTRINH lt ON lt.MaLT = hd.MaLT\n"
                    + "JOIN dbo.TOUR t ON t.MaTour = lt.MaTour\n"
                    + "JOIN dbo.NHANVIEN nv ON nv.MaNV = lt.MaNV\n"
                    + "WHERE hd.MaHopDong LIKE ?\n"
                    + "OR CONCAT(t.TenTour, ' - ', hd.MaLT) LIKE ?\n"
                    + "OR hd.NgayLapHopDong LIKE ?\n"
                    + "OR hd.MaNV LIKE ?";
            ResultSet rs = JDBCHelper.query(sql,
                    "%" + txtFind1.getText() + "%",
                    "%" + txtFind1.getText() + "%",
                    "%" + txtFind1.getText() + "%",
                    "%" + txtFind1.getText() + "%"
            );
            while (rs.next()) {
                String tongTien = rs.getString(4);
                String tienCoc = rs.getString(5);
                long conLai = (long) (Double.parseDouble(tongTien) - Double.parseDouble(tienCoc));
                Vector data = new Vector();
                data.add(rs.getString(1));
                data.add(rs.getString(2));
                data.add(rs.getString(3));
                data.add(new DungChung.DungChung().convertTien(rs.getString(4)) + " VNĐ");
                data.add(new DungChung.DungChung().convertTien(rs.getString(5)) + " VNĐ");
                data.add(new DungChung.DungChung().convertTien(String.valueOf(conLai)) + " VNĐ");
                data.add(rs.getString(6));
                data.add(XDate.toString(rs.getDate(7), "dd-MM-yyyy"));
                data.add(rs.getString(8));
                model.addRow(data);
            }
            tblHDong.setModel(model);
            editColumnWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Delete() {
        int a = tblHDong.getSelectedRow();
        String MaHopDong = tblHDong.getValueAt(a, 1).toString();

        if (Auth.user.getChucVu().equals("Nhân Viên")) {
            MsgBox.alertWarning(this, "Nhân Viên Không Đủ Thẩm Quyền Xoá");
            return;
        }

        List<HoaDon> hdon = hdonDAO.selectAll();
        for (HoaDon hoaDon : hdon) {
            if (hoaDon.getMaHopDong().equals(MaHopDong)) {
                MsgBox.alertWarning(this, "Hợp Đồng " + MaHopDong + " Đã Được Lập Hoá Đơn!");
                return;
            }
        }

        ThongBao1 obj = new ThongBao1("Bạn Có Chắc Muốn Xoá Hợp Đồng " + MaHopDong + " Không ?");
        obj.eventOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                for (int row : tblHDong.getSelectedRows()) {
                    hdDao.delete(MaHopDong);
                }
                AddLichSuHD.addLSHD("Xoá Hợp Đồng Khỏi Lịch Trình");
                fillTable();
                GlassPanePopup.closePopupLast();
                MsgBox.alertSuccess(new Frame_LT_DiaDiem(), "Xoá Hợp Đồng Thành Công !");
            }
        });
        GlassPanePopup.showPopup(obj);
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH THÔNG TIN HỢP ĐỒNG", "Nhân viên", tblHDong, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new DungChung.ExportPDF().exportPDF("DANH SÁCH THÔNG TIN HỢP ĐỒNG", tblHDong);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlCardNhanVien = new javax.swing.JPanel();
        QLNV = new javax.swing.JPanel();
        btnXoa = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblHDong = new DungChung.Table();
        jLabel7 = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        txtFind1 = new swing.TextFieldAnimation();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        QLNV.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonXoaMoi.png"))); // NOI18N
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
        QLNV.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(780, 710, -1, -1));

        tblHDong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã Hóa Đơn", "Mã Hợp Đồng", "Người Tạo", "Trạng Thái", "Ghi Chú"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblHDong.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblHDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblHDongMouseClicked(evt);
            }
        });
        tblHDong.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblHDongKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblHDong);
        if (tblHDong.getColumnModel().getColumnCount() > 0) {
            tblHDong.getColumnModel().getColumn(0).setPreferredWidth(0);
            tblHDong.getColumnModel().getColumn(1).setPreferredWidth(10);
            tblHDong.getColumnModel().getColumn(2).setPreferredWidth(150);
        }

        QLNV.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 80, 1145, 620));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ HỢP ĐỒNG");
        QLNV.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, 40));

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
        QLNV.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(940, 710, -1, -1));

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

        txtFind1.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind1.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFind1CaretUpdate(evt);
            }
        });
        QLNV.add(txtFind1, new org.netbeans.lib.awtextra.AbsoluteConstraints(1000, 20, 250, -1));

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

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPDF, "bgButtonFPTHover.png");
    }//GEN-LAST:event_btnPDFMouseEntered

    private void btnPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseExited
        new DungChung.DungChung().hoverButton(2, btnPDF, "bgButtonFPT.png");
    }//GEN-LAST:event_btnPDFMouseExited

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void tblHDongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblHDongMouseClicked
        int a = tblHDong.getSelectedRow();
        if (evt.getClickCount() == 2) {
            DiaLog_XemLaiHopDong.TTHopDong(tblHDong.getValueAt(a, 1).toString());
            new DiaLog_XemLaiHopDong(this, true).setVisible(true);
        }
    }//GEN-LAST:event_tblHDongMouseClicked

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "buttonXoaMoi_Hover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "buttonXoaMoi.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        row = tblHDong.getSelectedRow();
        if (row < 0) {
            MsgBox.alertInfo(this, "Vui Lòng Chọn Một Hoá Đơn Để Xoá");
            return;
        } else {
            Delete();
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void txtFind1CaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFind1CaretUpdate
        fillTable();
    }//GEN-LAST:event_txtFind1CaretUpdate

    private void tblHDongKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblHDongKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblHDong.getSelectedRow();
            Delete();
        }
    }//GEN-LAST:event_tblHDongKeyPressed

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
            java.util.logging.Logger.getLogger(Frame_TTHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTHopDong.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTHopDong().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel QLNV;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JPanel pnlCardNhanVien;
    private DungChung.Table tblHDong;
    private swing.TextFieldAnimation txtFind1;
    // End of variables declaration//GEN-END:variables
}
