package UI;

import DAO.DoanhThuDAO;
import com.raven.chart.ModelChart;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.ImageIcon;

/**
 * @author NHUTLQ
 */
public class Frame_TrangChu extends javax.swing.JFrame {

    public Frame_TrangChu() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        setLocationRelativeTo(this);
        chart.start();
        BieuDo();
        loadFormStatusLBL();
    }

    public void BieuDo() {
        getContentPane().setBackground(new Color(250, 250, 250));
        chart.addLegend("Doanh thu khách hàng sử dụng tour du lịch hằng năm", new Color(190, 79, 60) {
        });
        chart.addData(new ModelChart("Năm 2020", new double[]{new DoanhThuDAO().getDoanhThuTheoNam(2020)}));
        chart.addData(new ModelChart("Năm 2021", new double[]{new DoanhThuDAO().getDoanhThuTheoNam(2021)}));
        chart.addData(new ModelChart("Năm 2022", new double[]{new DoanhThuDAO().getDoanhThuTheoNam(2022)}));
        chart.addData(new ModelChart("Năm 2023", new double[]{new DoanhThuDAO().getDoanhThuTheoNam(2023)}));
        loadTrangThaiDT();
        loadTrangThaiNV();
        loadTrangThaiLT();
        loadTrangThaiTour();
    }

//    public void loadBieuDo() {
//        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//        String str = "Doanh thu khách hàng sử dụng tour du lịch hằng năm";
//        dataset.addValue(232, str, "2022");
//        dataset.addValue(231, str, "2021");
//        dataset.addValue(123, str, "2020");
//        dataset.addValue(43, str, "2019");
//        dataset.addValue(421, str, "2018");
//        dataset.addValue(124, str, "2015");
//        dataset.addValue(245, str, "2014");
//        dataset.addValue(123, str, "2013");
//        dataset.addValue(65, str, "2013");
//        dataset.addValue(587, str, "2011");
//        dataset.addValue(23, str, "2010");
//        JFreeChart barChart = ChartFactory.createBarChart("BIỂU ĐỒ THỐNG KÊ DOANH THU THEO NĂM", "Năm", "Tiền (Triệu)", dataset, PlotOrientation.VERTICAL, true, true, true);
//        ChartPanel chartPanel = new ChartPanel(barChart);
//        chartPanel.setPreferredSize(new java.awt.Dimension(pnlBieuDo.getWidth() - 10, pnlBieuDo.getHeight() - 10));
//        pnlBieuDo.setLayout(new java.awt.BorderLayout());
//        pnlBieuDo.add(chartPanel, BorderLayout.CENTER);
//        pnlBieuDo.validate();
//    }
    public void loadFormStatusLBL() {
        lblTongNV.setText(String.valueOf(new DoanhThuDAO().getTongNV()));
        lblTongTour.setText(String.valueOf(new DoanhThuDAO().getTongTour()));
        lblTongKH.setText(String.valueOf(new DoanhThuDAO().getTongKH()));
        lblTongDT.setText(String.valueOf(new DoanhThuDAO().loadTienHienThiTrenHome(2023)));
    }

    public void loadTrangThaiDT() {
        int nam2022 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().getDoanhThuTheoNam(2022) / 100000)));
        int nam2023 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().getDoanhThuTheoNam(2023) / 100000)));
        if (nam2023 == 0 || nam2022 == 0) {
            return;
        }
        int per = nam2023 * 100 / nam2022;
        lblTTDT.setText(String.valueOf(per) + "%");
//        if (nam2023 < nam2022) {
//            ImageIcon icon = new ImageIcon("src//icon//downward-arrow.png");
//            lblTTDT.setIcon(icon);
//        } else {
//            ImageIcon icon = new ImageIcon("src//Icon//arrow-up.png");
//            lblTTDT.setIcon(icon);
//        }
    }

    public void loadTrangThaiNV() {
        int thang3 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().loadTrangThaiNV(3))));
        int thang4 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().loadTrangThaiNV(4))));

        if (thang4 == 0 || thang3 == 0) {
            return;
        }
        int per = thang4 * 10 / thang3;
        lblTTNV.setText(String.valueOf(per) + "%");
        if (thang4 < thang3) {
            ImageIcon icon = new ImageIcon("src//Icon//arrow-up.png");
            lblTTNV.setIcon(icon);
        } else {
            ImageIcon icon = new ImageIcon("src//icon//downward-arrow.png");
            lblTTNV.setIcon(icon);
        }
    }

    public void loadTrangThaiLT() {
        int thang3 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().loadTrangThaiLT(3))));
        int thang4 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().loadTrangThaiLT(4))));

        if (thang4 == 0 || thang3 == 0) {
            return;
        }
        int per = thang4 * 10 / thang3;
        lblTTKH.setText(String.valueOf(per) + "%");
        if (thang4 < thang3) {
            ImageIcon icon = new ImageIcon("src//icon//downward-arrow.png");
            lblTTKH.setIcon(icon);
        } else {
            ImageIcon icon = new ImageIcon("src//Icon//arrow-up.png");
            lblTTKH.setIcon(icon);
        }
    }

    public void loadTrangThaiTour() {
        int thang3 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().loadTrangThaiTour(3))));
        int thang4 = Integer.parseInt(String.valueOf(Math.round(new DoanhThuDAO().loadTrangThaiTour(4))));

        if (thang4 == 0 || thang3 == 0) {
            return;
        }
        int per = thang4 * 10 / thang3;
        lblTTTour.setText(String.valueOf(per) + "%");
        if (thang4 < thang3) {
            ImageIcon icon = new ImageIcon("src//icon//downward-arrow.png");
            lblTTTour.setIcon(icon);
        } else {
            ImageIcon icon = new ImageIcon("src//Icon//arrow-up.png");
            lblTTTour.setIcon(icon);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        pnlQLy2 = new UI.pnlQLy();
        jLabel10 = new javax.swing.JLabel();
        lblTongKH = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        lblTTKH = new javax.swing.JLabel();
        pnlQLy4 = new UI.pnlQLy();
        jLabel11 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        lblTongDT = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        lblTTDT = new javax.swing.JLabel();
        pnlQLy6 = new UI.pnlQLy();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblTongNV = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblTTNV = new javax.swing.JLabel();
        pnlQLy7 = new UI.pnlQLy();
        jLabel13 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        lblTongTour = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        lblTTTour = new javax.swing.JLabel();
        pnlQLy5 = new UI.pnlQLy();
        chart = new com.raven.chart.Chart();
        jLabel14 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setPreferredSize(new java.awt.Dimension(1290, 820));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlQLy2.setBackground(new java.awt.Color(0, 102, 204));
        pnlQLy2.setPreferredSize(new java.awt.Dimension(250, 0));
        pnlQLy2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/car-building.png"))); // NOI18N
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy2.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 27, 25, -1));

        lblTongKH.setFont(new java.awt.Font("Tahoma", 1, 45)); // NOI18N
        lblTongKH.setForeground(new java.awt.Color(255, 255, 255));
        lblTongKH.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongKH.setText("9235");
        pnlQLy2.add(lblTongKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 60, 150, -1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Tổng Lượng Khách Hàng");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy2.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(51, 29, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("So Với Tháng Trước");
        pnlQLy2.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 30));

        lblTTKH.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblTTKH.setForeground(new java.awt.Color(255, 255, 255));
        lblTTKH.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-up.png"))); // NOI18N
        lblTTKH.setText("8% ");
        pnlQLy2.add(lblTTKH, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 50, 30));

        jPanel1.add(pnlQLy2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 30, 260, 150));

        pnlQLy4.setBackground(new java.awt.Color(101, 186, 169));
        pnlQLy4.setPreferredSize(new java.awt.Dimension(250, 145));
        pnlQLy4.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/messages-dollar.png"))); // NOI18N
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy4.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 30, -1));

        jLabel15.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Tổng Doanh Thu Năm");
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy4.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        lblTongDT.setFont(new java.awt.Font("Tahoma", 1, 45)); // NOI18N
        lblTongDT.setForeground(new java.awt.Color(255, 255, 255));
        lblTongDT.setText("60.00");
        pnlQLy4.add(lblTongDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 230, -1));

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(242, 242, 242));
        jLabel19.setText("Tỷ Đồng");
        pnlQLy4.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 80, -1, 40));

        jLabel22.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("So Với Năm Trước");
        pnlQLy4.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 30));

        lblTTDT.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblTTDT.setForeground(new java.awt.Color(255, 255, 255));
        lblTTDT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/dthu.png"))); // NOI18N
        lblTTDT.setText("8% ");
        pnlQLy4.add(lblTTDT, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 120, 70, 30));

        jPanel1.add(pnlQLy4, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 30, -1, 150));

        pnlQLy6.setBackground(new java.awt.Color(255, 153, 51));
        pnlQLy6.setPreferredSize(new java.awt.Dimension(250, 145));
        pnlQLy6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/users.png"))); // NOI18N
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy6.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 27, -1, -1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("Tổng Số Nhân Viên");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy6.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 29, -1, -1));

        lblTongNV.setFont(new java.awt.Font("Tahoma", 1, 45)); // NOI18N
        lblTongNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTongNV.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongNV.setText("9235");
        pnlQLy6.add(lblTongNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 50, 130, 70));

        jLabel5.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("So Với Tháng Trước");
        pnlQLy6.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 30));

        lblTTNV.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblTTNV.setForeground(new java.awt.Color(255, 255, 255));
        lblTTNV.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblTTNV.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-up.png"))); // NOI18N
        lblTTNV.setText("8% ");
        pnlQLy6.add(lblTTNV, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 50, 30));

        jPanel1.add(pnlQLy6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, 150));

        pnlQLy7.setBackground(new java.awt.Color(102, 102, 0));
        pnlQLy7.setPreferredSize(new java.awt.Dimension(250, 145));
        pnlQLy7.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel13.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/Tour.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy7.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, 30, -1));

        jLabel17.setFont(new java.awt.Font("Segoe UI", 1, 16)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("Tổng Số Tour Du Lịch");
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        pnlQLy7.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        lblTongTour.setFont(new java.awt.Font("Tahoma", 1, 45)); // NOI18N
        lblTongTour.setForeground(new java.awt.Color(255, 255, 255));
        lblTongTour.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblTongTour.setText("9235");
        pnlQLy7.add(lblTongTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 60, 130, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("So Với Tháng Trước");
        pnlQLy7.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 120, 120, 30));

        lblTTTour.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        lblTTTour.setForeground(new java.awt.Color(255, 255, 255));
        lblTTTour.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/arrow-up.png"))); // NOI18N
        lblTTTour.setText("8% ");
        pnlQLy7.add(lblTTTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 120, 50, 30));

        jPanel1.add(pnlQLy7, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 30, 250, 150));

        pnlQLy5.setBackground(new java.awt.Color(255, 255, 255));

        chart.setForeground(new java.awt.Color(102, 102, 102));
        chart.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N

        jLabel14.setBackground(new java.awt.Color(0, 0, 0));
        jLabel14.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("BIỂU ĐỒ THỐNG KÊ DOANH THU TOUR DU LỊCH QUA TỪNG NĂM (VNĐ / NĂM)");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        javax.swing.GroupLayout pnlQLy5Layout = new javax.swing.GroupLayout(pnlQLy5);
        pnlQLy5.setLayout(pnlQLy5Layout);
        pnlQLy5Layout.setHorizontalGroup(
            pnlQLy5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLy5Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(pnlQLy5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 1140, Short.MAX_VALUE)
                    .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(25, 25, 25))
        );
        pnlQLy5Layout.setVerticalGroup(
            pnlQLy5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQLy5Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chart, javax.swing.GroupLayout.DEFAULT_SIZE, 494, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel1.add(pnlQLy5, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 210, 1190, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(Frame_TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TrangChu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TrangChu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.chart.Chart chart;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblTTDT;
    private javax.swing.JLabel lblTTKH;
    private javax.swing.JLabel lblTTNV;
    private javax.swing.JLabel lblTTTour;
    private javax.swing.JLabel lblTongDT;
    private javax.swing.JLabel lblTongKH;
    private javax.swing.JLabel lblTongNV;
    private javax.swing.JLabel lblTongTour;
    private UI.pnlQLy pnlQLy2;
    private UI.pnlQLy pnlQLy4;
    private UI.pnlQLy pnlQLy5;
    private UI.pnlQLy pnlQLy6;
    private UI.pnlQLy pnlQLy7;
    // End of variables declaration//GEN-END:variables
}
