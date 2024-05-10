package DungChung;

import SQL.JDBCHelper;
import java.awt.BorderLayout;
import java.awt.Color;
import java.sql.ResultSet;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.AxisLocation;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.labels.StandardCategoryToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.DatasetRenderingOrder;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

/**
 * A simple demonstration application showing how to create a dual axis chart
 * based on data from two {@link CategoryDataset} instances.
 *
 */
public class BieuDo2TrungTung extends ApplicationFrame {

    /**
     * Creates a new demo instance.
     *
     * @param title the frame title.
     */
    public BieuDo2TrungTung(String title, JPanel pnl, int nam) {

        super(title);

        final CategoryDataset dataset1 = createDataset1(nam);

        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
                "Biểu Đồ Thống Kê Số Lượng Tour Và Khách Hàng Qua Từng Năm", // chart title
                "Quý", // domain axis label
                "Tour, Khách Hàng", // range axis label
                dataset1, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true, // tooltips?
                false // URL generator?  Not required...
        );

        chart.setBackgroundPaint(Color.white);

        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(new Color(0xEE, 0xEE, 0xFF));
        plot.setDomainAxisLocation(AxisLocation.BOTTOM_OR_RIGHT);

        final CategoryDataset dataset2 = createDataset2(nam);
        plot.setDataset(1, dataset2);
        plot.mapDatasetToRangeAxis(1, 1);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
        final ValueAxis axis2 = new NumberAxis("Doanh Thu");
        plot.setRangeAxis(1, axis2);

        final LineAndShapeRenderer renderer2 = new LineAndShapeRenderer();
        renderer2.setToolTipGenerator(new StandardCategoryToolTipGenerator());
        plot.setRenderer(1, renderer2);
        plot.setDatasetRenderingOrder(DatasetRenderingOrder.REVERSE);
        // OPTIONAL CUSTOMISATION COMPLETED.

        // add the chart to a panel...
        final ChartPanel chartPanel = new ChartPanel(chart);
//        setContentPane(chartPanel);
        pnl.setLayout(new java.awt.BorderLayout());
        pnl.add(chartPanel, BorderLayout.CENTER);
        pnl.validate();
    }

    public void getDuLieu(int nam) {

    }

    private CategoryDataset createDataset1(int nam) {
        int tourQuy1 = 0;
        int khachHangQuy1 = 0;
        try {
            String sql1 = "SELECT COUNT(lt.MaTour) SoTours, sum(lt.SoKhach) SoKhach\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '1'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql1, nam);
            while (rs.next()) {
                tourQuy1 = rs.getInt(1);
                khachHangQuy1 = rs.getInt(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int tourQuy2 = 0;
        int khachHangQuy2 = 0;
        try {
            String sql2 = "SELECT COUNT(lt.MaTour) SoTours, sum(lt.SoKhach) SoKhach\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '2'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql2, nam);
            while (rs.next()) {
                tourQuy2 = rs.getInt(1);
                khachHangQuy2 = rs.getInt(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int tourQuy3 = 0;
        int khachHangQuy3 = 0;
        try {
            String sql3 = "SELECT COUNT(lt.MaTour) SoTours, sum(lt.SoKhach) SoKhach\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '3'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql3, nam);
            while (rs.next()) {
                tourQuy3 = rs.getInt(1);
                khachHangQuy3 = rs.getInt(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        int tourQuy4 = 0;
        int khachHangQuy4 = 0;
        try {
            String sql4 = "SELECT COUNT(lt.MaTour) SoTours, sum(lt.SoKhach) SoKhach\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '4'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql4, nam);
            while (rs.next()) {
                tourQuy4 = rs.getInt(1);
                khachHangQuy4 = rs.getInt(2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // row keys...
        final String tour = "Tour";
        final String khachhang = "Khách Hàng";

        // column keys...
        final String category1 = "Quý 1";
        final String category2 = "Quý 2";
        final String category3 = "Quý 3";
        final String category4 = "Quý 4";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(tourQuy1, tour, category1);
        dataset.addValue(tourQuy2, tour, category2);
        dataset.addValue(tourQuy3, tour, category3);
        dataset.addValue(tourQuy4, tour, category4);

        dataset.addValue(khachHangQuy1, khachhang, category1);
        dataset.addValue(khachHangQuy2, khachhang, category2);
        dataset.addValue(khachHangQuy3, khachhang, category3);
        dataset.addValue(khachHangQuy4, khachhang, category4);
        return dataset;
    }

    private CategoryDataset createDataset2(int nam) {

        long tongDoanhThuQuy1 = 0;
        try {
            String sql1 = "SELECT SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '1'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql1, nam);
            while (rs.next()) {
                tongDoanhThuQuy1 = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long tongDoanhThuQuy2 = 0;
        try {
            String sql2 = "SELECT SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '2'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql2, nam);
            while (rs.next()) {
                tongDoanhThuQuy2 = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long tongDoanhThuQuy3 = 0;
        try {
            String sql3 = "SELECT SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '3'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql3, nam);
            while (rs.next()) {
                tongDoanhThuQuy3 = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long tongDoanhThuQuy4 = 0;
        try {
            String sql4 = "SELECT SUM(tr.Gia*lt.SoKhach) TongDthuTour\n"
                    + "FROM LICHTRINH lt \n"
                    + "JOIN TOUR tr ON lt.MaTour = tr.MaTour\n"
                    + "JOIN dbo.HOPDONG hdong ON hdong.MaLT = lt.MaLT\n"
                    + "JOIN dbo.HOADON hdon ON hdon.MaHopDong = hdong.MaHopDong\n"
                    + "WHERE DATEPART(quarter,NgayLapHoaDon) = '4'\n"
                    + "AND YEAR(hdon.NgayLapHoaDon) = ?";
            ResultSet rs = JDBCHelper.query(sql4, nam);
            while (rs.next()) {
                tongDoanhThuQuy4 = rs.getLong(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // row keys...
        final String series1 = "Doanh Thu";

        // column keys...
        final String category1 = "Quý 1";
        final String category2 = "Quý 2";
        final String category3 = "Quý 3";
        final String category4 = "Quý 4";

        // create the dataset...
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        dataset.addValue(tongDoanhThuQuy1, series1, category1);
        dataset.addValue(tongDoanhThuQuy2, series1, category2);
        dataset.addValue(tongDoanhThuQuy3, series1, category3);
        dataset.addValue(tongDoanhThuQuy4, series1, category4);

        return dataset;
    }
}
