package UI;

import DAO.HopDongDAO;
import DAO.KhachHangDAO;
import DAO.LT_DiaDiemDAO;
import DAO.LT_KhachHangDAO;
import DAO.LT_KhachSanDAO;
import DAO.LT_PhuongTienDAO;
import DAO.LichTrinhDAO;
import DAO.NhanVienDAO;
import DAO.ThongTinTourDAO;
import DAO.TruyVanSQL;
import DungChung.AddLichSuHD;
import DungChung.Auth;
import DungChung.ExportPDF;
import DungChung.MsgBox;
import DungChung.XDate;
import SQL.JDBCHelper;
import TravelEntity.HopDong;
import TravelEntity.KhachHang;
import TravelEntity.LT_DiaDiem;
import TravelEntity.LT_KhachHang;
import TravelEntity.LT_KhachSan;
import TravelEntity.LT_PhuongTien;
import TravelEntity.LichTrinh;
import TravelEntity.NhanVien;
import TravelEntity.ThongTinTour;
import com.raven.datechooser.EventDateChooser;
import com.raven.datechooser.SelectedAction;
import com.raven.datechooser.SelectedDate;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao1;

/**
 * @author NHUTLQ
 */
public class Frame_TTLichTrinh extends javax.swing.JFrame {

    LichTrinhDAO ltDao = new LichTrinhDAO();
    KhachHangDAO khDao = new KhachHangDAO();
    ThongTinTourDAO tourDao = new ThongTinTourDAO();
    NhanVienDAO nvDao = new NhanVienDAO();
    LT_PhuongTienDAO ltptDao = new LT_PhuongTienDAO();
    LT_KhachHangDAO ltkhDao = new LT_KhachHangDAO();
    LT_DiaDiemDAO ltddDao = new LT_DiaDiemDAO();
    LT_KhachSanDAO ltksDao = new LT_KhachSanDAO();
    HopDongDAO hdDao = new HopDongDAO();
    private int row = -1;

    public Frame_TTLichTrinh() {
        initComponents();
        init();
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginForm.class.getResource("/icon/favicon.png")));
    }

    public void init() {
        HideError();
        setDateInTextFilde();
        AutoCompleteDecorator.decorate(cboTenHDV);
        AutoCompleteDecorator.decorate(cboTenTour);
        AutoCompleteDecorator.decorate(cboTruongDoan);
        fillTable();
        updateStatusButton();
        fillCboxTenHDV();
        fillCboxTenTour();
        fillCboxTenKH();
        setText();
        initDate();
        tblLT.setDefaultEditor(Object.class, null);
        txtNgayBD.setEditable(true);
        txtNgayKT.setEditable(true);
    }

    public void initDate() {
        NgayBD.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    NgayBD.hidePopup();
                }
            }
        });

        NgayKT.addEventDateChooser(new EventDateChooser() {
            @Override
            public void dateSelected(SelectedAction action, SelectedDate date) {
                if (action.getAction() == SelectedAction.DAY_SELECTED) {
                    NgayKT.hidePopup();
                }
            }
        });
    }

    public void updateStatus() {
        try {
            List<LichTrinh> lichTrinhs = ltDao.selectAll();
            LocalDate now = LocalDate.now();
            for (LichTrinh lichTrinh : lichTrinhs) {
                LocalDate BD = LocalDate.parse(lichTrinh.getNgayBD().toString());
                LocalDate KT = LocalDate.parse(lichTrinh.getNgayKT().toString());
                String status = "";
                if (lichTrinh.getTrangThai().equals("Đã huỷ")) {
                    continue;
                }
                if (ChronoUnit.DAYS.between(BD, now) < 0) {
                    status = "Chưa đi";
                } else if (ChronoUnit.DAYS.between(KT, now) > 0) {
                    status = "Đã đi";
                } else {
                    status = "Đang đi";
                }
                lichTrinh.setTrangThai(status);
                ltDao.update(lichTrinh);
            }
            fillTable();
            MsgBox.alertSuccess(this, "Cập Nhật Bảng Thành Công");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText() {
        String[] arr = new String[]{
            "An Giang", "Bắc Kạn", "Bến Tre", "Bình Phước", "Cao Bằng", "Đắk Lắk", "Đồng Nai", "Hà Giang", "Hà Tây", "Hải Phòng",
            "Hậu Giang", "Kiên Giang", "Lào Cai", "Long An", "Ninh Bình", "Phú Yên", "Quảng Ngãi", "Sóc Trăng", "Thái Bình", "Huế",
            "Tuyên Quang", "Yên Bái", "Vũng Tàu", "Bắc Giang", "Bình Dương", "Bình Thuận", "Cần Thơ", "Đắk Nông", "Đồng Tháp", "Hà Nam",
            "Hà Nội", "Hà Tĩnh", "Hải Dương", "Hòa Bình", "Hưng Yên", "Lai Châu", "Lạng Sơn", "Nam Định", "Quảng Bình", "Quảng Ninh",
            "Sơn La", "Tây Ninh", "Thái Nguyên", "Tiền Giang", "Vĩnh Long", "Quy Nhơn", "Bạc Liêu", "Bắc Ninh", "Bình Định", "Cà Mau",
            "Đà Nẵng", "Điện Biên", "Gia Lai", "Hồ Chí Minh", "Khánh Hòa", "Lâm Đồng", "Nghệ An", "Phú Thọ", "Quảng Nam", "Quảng Trị",
            "Thanh Hóa", "Trà Vinh", "Vĩnh Phúc"
        };

        for (int i = 0; i < arr.length; i++) {
            txtDiaDiem.addItemSuggestion(arr[i]);
        }
        txtPhuThu.setText("0");
        txtMaLT.setText(TruyVanSQL.MaLT());
        txtMaLT.setEditable(false);
        txtGioVe.setEditable(false);
        txtGiodi.setEditable(false);
    }

    private void editColumnWidth() {
        int[] col = new int[]{50, 60, 200, 300, 100, 100, 100, 150, 150};
        new DungChung.DungChung().editColumnWidth(col, tblLT);
    }

    private void setDateInTextFilde() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        txtNgayBD.setText(String.valueOf(dtf.format(now)));
        txtNgayKT.setText(String.valueOf(dtf.format(now)));
    }

    public void HideError() {
        new DungChung.DungChung().hideLBLError(new JLabel[]{
            lblSoKhach,
            lblDuaDon,
            lblLoiPhuThu,
            lblLoiGioVe,
            lblLoiNKT
        });
    }

    public boolean checkRegex() {
        if (txtSoKhach.getText().trim().isEmpty()) {
            lblSoKhach.setVisible(true);
            return false;
        }
        try {
            Integer a = Integer.valueOf(txtSoKhach.getText());
            if (a < 0 || a > 110) {
                lblSoKhach.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            lblSoKhach.setVisible(true);
            return false;
        }
        if (txtPhuThu.getText().trim().isEmpty()) {
            lblLoiPhuThu.setVisible(true);
            return false;
        }
        try {
            Double a = Double.valueOf(txtPhuThu.getText());
            if (a < 0 || a > 1000000000) {
                lblLoiPhuThu.setVisible(true);
                return false;
            }
        } catch (NumberFormatException e) {
            lblLoiPhuThu.setVisible(true);
            return false;
        }
        if (txtDiaDiem.getText().trim().isEmpty()) {
            lblDuaDon.setVisible(true);
            return false;
        }

//        try {
//            SimpleDateFormat formater = new SimpleDateFormat();
//            formater.applyPattern("dd-MM-yyyy");
//            Date date = formater.parse(txtNgayBD.getText());
//            Date date3 = formater.parse(txtNgayKT.getText());
//
//            if (date.compareTo(date3) > date.compareTo(date)) {
//                lblLoiNKT.setVisible(true);
//                lblLoiNKT.setText("Ngày Kết Thúc Phải Sau Ngày Bắt Đầu !");
//                return false;
//            }
//
//            if (date.compareTo(date3) == date.compareTo(date)) {
//                LocalTime gioBD = LocalTime.parse(txtGiodi.getText());
//                LocalTime gioKT = LocalTime.parse(txtGioVe.getText());
//                int Gio = gioBD.getHour();
//                int phut = gioBD.getMinute();
//                int Gio1 = gioKT.getHour();
//                int phut1 = gioKT.getMinute();
//                LocalTime gioDi = LocalTime.of(Gio, phut);
//                LocalTime gioVe = LocalTime.of(Gio1, phut1);
//                if (gioDi.isBefore(gioVe)) {
//                    lblLoiGioVe.setVisible(true);
//                    return false;
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            lblLoiGioVe.setVisible(true);
//            return false;
//        }
        return true;
    }

    public void fillCboxTenTour() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenTour.getModel();
        model.removeAllElements();
        List<ThongTinTour> list = tourDao.selectAll();
        for (ThongTinTour nhanVien : list) {
            model.addElement(nhanVien.getTenTour() + " - " + nhanVien.getMaTour());
        }
    }

    public void fillCboxTenKH() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTruongDoan.getModel();
        model.removeAllElements();
        List<KhachHang> list = khDao.selectAll();
        for (KhachHang nhanVien : list) {
            model.addElement(nhanVien.getTenKH() + " - " + nhanVien.getCCCD());
        }

        try {
            String sql = "SELECT * FROM dbo.KHACHHANG kh\n"
                    + "JOIN dbo.LICHTRINH lt ON lt.MaKH = kh.MaKH\n"
                    + "WHERE lt.TrangThai LIKE N'Đã đi' OR lt.TrangThai LIKE N'Chưa đi'";
            ResultSet rs = JDBCHelper.query(sql);
            while (rs.next()) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void fillCboxTenHDV() {
        DefaultComboBoxModel model = (DefaultComboBoxModel) cboTenHDV.getModel();
        model.removeAllElements();
        List<NhanVien> list = nvDao.selectTenHDV();
        for (NhanVien nhanVien : list) {
            model.addElement(nhanVien.getHoTen());
        }
    }

    public void fillTable() {
        try {
            String[] header = new String[]{"STT", "Mã LT", "Tên Tour", "Tên Trưởng Đoàn", "Ngày Đi", "Ngày Về", "Số Khách", "Hướng Dẫn Viên", "Trạng Thái"};
            DefaultTableModel model = new DefaultTableModel(header, 0);
            String sql = "SELECT ROW_NUMBER() OVER (ORDER BY lt.MaLT), lt.MaLT, CONCAT(t.TenTour, ' - ', t.MaTour) AS TenTour, CONCAT(kh.TenKH, ' - ', kh.GTTT) AS TenKH , lt.NgayBD, lt.NgayKT, lt.SoKhach, lt.HuongDV, lt.TrangThai\n"
                    + "FROM dbo.LICHTRINH lt \n"
                    + "INNER JOIN dbo.KHACHHANG kh \n"
                    + "ON kh.MaKH = lt.MaKH \n"
                    + "INNER JOIN dbo.TOUR t \n"
                    + "ON t.MaTour = lt.MaTour \n"
                    + "WHERE lt.MaLT LIKE ?\n"
                    + "OR CONCAT(t.TenTour, ' - ', t.MaTour) LIKE ? \n"
                    + "OR CONCAT(kh.TenKH, ' - ', kh.GTTT) LIKE ? \n"
                    + "OR lt.NgayBD LIKE ?\n"
                    + "OR lt.NgayKT LIKE ?\n"
                    + "OR lt.TrangThai LIKE ?\n"
                    + "OR lt.HuongDV LIKE ?";
            ResultSet rs = JDBCHelper.query(
                    sql,
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%",
                    "%" + txtFind.getText() + "%"
            );
            while (rs.next()) {
                Vector data = new Vector();
                for (int i = 0; i < header.length; i++) {
                    data.add(rs.getString(1));
                    data.add(rs.getString(2));
                    data.add(rs.getString(3));
                    data.add(rs.getString(4));
                    data.add(XDate.toString(rs.getDate(5), "dd-MM-yyyy"));
                    data.add(XDate.toString(rs.getDate(6), "dd-MM-yyyy"));
                    data.add(rs.getString(7));
                    data.add(rs.getString(8));
                    data.add(rs.getString(9));
                }
                model.addRow(data);
            }
            tblLT.setModel(model);
            editColumnWidth();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean chkHDV_TheoNgay() {
        String cbo = (String) cboTenHDV.getSelectedItem();
        String TenHDV = cbo.trim();
        try {
            String sql = "{call GetHDV(?)}";
            ResultSet rs = JDBCHelper.query(sql, TenHDV);
            while (rs.next()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate startDate = LocalDate.parse(rs.getString(1), formatter);
                LocalDate endDate = LocalDate.parse(rs.getString(2), formatter);
                LocalDate rangeStartDate = LocalDate.parse(txtNgayBD.getText(), formatter1);
                LocalDate rangeEndDate = LocalDate.parse(txtNgayKT.getText(), formatter1);

                startDate.toString();
                endDate.toString();
                rangeStartDate.toString();
                rangeEndDate.toString();

                if (endDate.isBefore(rangeStartDate) || startDate.isAfter(rangeEndDate)) {
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean chkTruongDoan_TheoNgay() {
        String cbo = (String) cboTruongDoan.getSelectedItem();
        String TenTruong = cbo.substring(cbo.lastIndexOf("-") + 1).trim();
        try {
            String sql = "{call GetTruongDoan(?)}";
            ResultSet rs = JDBCHelper.query(sql, TenTruong);
            while (rs.next()) {

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate startDate = LocalDate.parse(rs.getString(1), formatter);
                LocalDate endDate = LocalDate.parse(rs.getString(2), formatter);
                LocalDate rangeStartDate = LocalDate.parse(txtNgayBD.getText(), formatter1);
                LocalDate rangeEndDate = LocalDate.parse(txtNgayKT.getText(), formatter1);

                startDate.toString();
                endDate.toString();
                rangeStartDate.toString();
                rangeEndDate.toString();

                if (endDate.isBefore(rangeStartDate) || startDate.isAfter(rangeEndDate)) {
                } else {
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public void insert() {
        if (!chkHDV_TheoNgay()) {
            MsgBox.alertWarning(this, "Hướng Dẫn Viên Đang Tồn Tại Ở Lịch Trình Khác !");
            return;
        } else if (!chkTruongDoan_TheoNgay()) {
            MsgBox.alertWarning(this, "Trưởng Đoàn Đang Tồn Tại Ở Lịch Trình Khác !");
            return;
        }

        LichTrinh lt = getForm();
        try {
            ltDao.insert(lt);
            txtMaLT.setText(TruyVanSQL.MaLT());
            fillTable();
            clearForm();
            AddLichSuHD.addLSHD("Thêm Lịch Trình");
            MsgBox.alertSuccess(this, "Thêm Mới Thành Công !");
            btnSua.setEnabled(false);
            btnXoa.setEnabled(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {
        String malt = tblLT.getValueAt(row, 1).toString();

        List<LichTrinh> lichTrinh = ltDao.selectSLKH(malt);
        List<HopDong> hopDong = hdDao.selectAll();

        for (LichTrinh ltrinh : lichTrinh) {
            for (HopDong hdong : hopDong) {
                if (ltrinh.getMaLT().equals(hdong.getMaLT())) {
                    MsgBox.AlerThongBao("Không Thể Cập Nhật, Vì Lịch Trình Đã Được Lập Hợp Đồng !");
                    return;
                }
            }
        }

        LichTrinh lt = getForm();

        try {
            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Cập Nhật Lịch Trình " + lt.getMaLT() + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ltDao.update(lt);
                    txtMaLT.setText(TruyVanSQL.MaLT());
                    fillTable();
                    clearForm();
                    AddLichSuHD.addLSHD("Cập Nhật Lịch Trình");
                    btnThem.setEnabled(true);
                    btnSua.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                    MsgBox.alertSuccess(new Frame_TTKhachHang(), "Cập Nhật Thành Công !");

                }
            });
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void delete() {
        String malt = tblLT.getValueAt(row, 1).toString();
        try {
            if (Auth.user.getChucVu().equals("Nhân Viên")) {
                MsgBox.alertWarning(this, "Nhân Viên Không Đủ Thẩm Quyền Xoá");
                return;
            }

            List<LichTrinh> lichTrinh = ltDao.selectSLKH(malt);
            List<LT_PhuongTien> ltpt = ltptDao.selectByIdPhuongtien(malt);
            List<LT_KhachHang> ltkh = ltkhDao.selectSLKH(malt);
            List<LT_DiaDiem> ltdd = ltddDao.selectByIdDiaDiem(malt);
            List<LT_KhachSan> ltks = ltksDao.selectByIdKhachSan(malt);
            List<HopDong> hopDong = hdDao.selectAll();

            for (LichTrinh lt : lichTrinh) {
                for (LT_PhuongTien pt : ltpt) {
                    if (pt.getMaLT().equals(lt.getMaLT())) {
                        MsgBox.alertWarning(this, "Lịch Trình Hiện Đang Được Sử Dụng!");
                        return;
                    }
                }
            }

            for (LichTrinh lt : lichTrinh) {
                for (LT_KhachHang kh : ltkh) {
                    if (kh.getMalt().equals(lt.getMaLT())) {
                        MsgBox.alertWarning(this, "Lịch Trình Hiện Đang Được Sử Dụng!");
                        return;
                    }
                }
            }

            for (LichTrinh lt : lichTrinh) {
                for (LT_DiaDiem kh : ltdd) {
                    if (kh.getMaLT().equals(lt.getMaLT())) {
                        MsgBox.alertWarning(this, "Lịch Trình Hiện Đang Được Sử Dụng!");
                        return;
                    }
                }
            }

            for (LichTrinh lt : lichTrinh) {
                for (LT_KhachSan kh : ltks) {
                    if (kh.getMaLT().equals(lt.getMaLT())) {
                        MsgBox.alertWarning(this, "Lịch Trình Hiện Đang Được Sử Dụng!");
                        return;
                    }
                }
            }

            for (LichTrinh lt : lichTrinh) {
                for (HopDong hd : hopDong) {
                    if (hd.getMaLT().equals(lt.getMaLT())) {
                        MsgBox.alertWarning(this, "Lịch Trình Đã Được Tạo Hợp Đồng!");
                        return;
                    }
                }
            }

            ThongBao1 obj = new ThongBao1("Bạn Có Chắn Chắc Xoá Lịch Trình " + malt + " Không ?");
            obj.eventOK(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    ltDao.delete(malt);
                    txtMaLT.setText(TruyVanSQL.MaLT());
                    fillTable();
                    clearForm();
                    AddLichSuHD.addLSHD("Xoá Lịch Trình");
                    btnThem.setEnabled(true);
                    btnSua.setEnabled(false);
                    btnXoa.setEnabled(false);
                    GlassPanePopup.closePopupLast();
                }
            });
            MsgBox.alertSuccess(this, "Xoá Lịch Trình Thành Công !");
            GlassPanePopup.showPopup(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LichTrinh getForm() {
        String cbo = (String) cboTenTour.getSelectedItem();
        String subTring = cbo.substring(cbo.lastIndexOf("-") + 1).trim();

        String cbo1 = (String) cboTruongDoan.getSelectedItem();
        String subTring1 = cbo1.substring(cbo1.lastIndexOf("-") + 1).trim();

        LichTrinh lt = new LichTrinh();
        lt.setMaLT(txtMaLT.getText());
        lt.setMaNV(MainForm.lblTitle.getText());
        lt.setMaKH(TruyVanSQL.selectMaKH(subTring1));
        lt.setMaTour(subTring);
        lt.setGiodi(txtGiodi.getText());
        lt.setNgayBD(XDate.toDate(txtNgayBD.getText(), "dd-MM-yyyy"));
        lt.setGiove(txtGioVe.getText());
        lt.setNgayKT(XDate.toDate(txtNgayKT.getText(), "dd-MM-yyyy"));
        lt.setHuongHV((String) cboTenHDV.getSelectedItem());
        lt.setPhuThu(Integer.parseInt(txtPhuThu.getText()));
        lt.setSoKhach(Integer.parseInt(txtSoKhach.getText()));
        lt.setDiaDiem(txtDiaDiem.getText());
        lt.setTrangThai((String) cboTrangthai.getSelectedItem());
        return lt;
    }

    public void fillForm(LichTrinh lt) {
        txtMaLT.setText(lt.getMaLT());
        txtSoKhach.setText(String.valueOf(lt.getSoKhach()));
        txtPhuThu.setText(String.valueOf(lt.getPhuThu()));
        txtDiaDiem.setText(lt.getDiaDiem());
        cboTenTour.setSelectedItem(tblLT.getValueAt(row, 2).toString());
        cboTenHDV.setSelectedItem((String) lt.getHuongHV());
        cboTruongDoan.setSelectedItem(tblLT.getValueAt(row, 3).toString());
        cboTrangthai.setSelectedItem(tblLT.getValueAt(row, 8));
        txtNgayBD.setText(XDate.toString(lt.getNgayBD(), "dd-MM-yyyy"));
        txtGiodi.setText(lt.getGiodi());
        txtNgayKT.setText(XDate.toString(lt.getNgayKT(), "dd-MM-yyyy"));
        txtGioVe.setText(lt.getGiove());
    }

    public static void fillTTLichTrinh(JTextField txt) {
        txt.setText(TruyVanSQL.fillTTLichTrinhInTTHopDong(txtMaLT.getText()));
    }

//    public static void isLichTrinh(JTable tblLS, int row) {
//        int a = tblLS.getSelectedRow();
//        String malt = tblLS.getValueAt(a, 1).toString();
//        String maltSub = malt.substring(0, malt.indexOf("-")).trim();
//        String maltCut = maltSub.substring(2);
//
//        LichTrinhDAO ltDAO = new LichTrinhDAO();
//        List<LichTrinh> lt = ltDAO.selectAll();
//        for (LichTrinh lichTrinh : lt) {
//            if (lichTrinh.getMaLT().equals(maltSub)) {
//                tbpMenu.removeAll();
//                Frame_TTLichTrinh ltks = new Frame_TTLichTrinh();
//                tbpMenu.addTab("", null, ltks.getContentPane().getComponentAt(0, 0), null);
//                tblLT.setRowSelectionInterval(Integer.parseInt(maltCut) - 1, Integer.parseInt(maltCut) - 1);
//                scrollToVisible(tblLT, row, row);
//            }
//        }
//    }
    public void checkTrangThaiShowHopDong() {
        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String date = dateObj.format(formatter);

        String trangThai = tblLT.getValueAt(row, 8).toString();

        List<LichTrinh> list = ltDao.selectSLKH(txtMaLT.getText());
//        List<LT_KhachHang> ltkh = ltkhDao.selectAll();
//        List<LT_KhachSan> ltks = ltksDao.selectAll();
//        List<LT_DiaDiem> ltdd = ltddDao.selectAll();
//        List<LT_PhuongTien> ltpt = ltptDao.selectAll();
        List<HopDong> HopDong = hdDao.selectAll();

        for (LichTrinh lt : list) {
            for (HopDong hopDong : HopDong) {
                if (lt.getMaLT().equals(hopDong.getMaLT())) {
                    MsgBox.alertInfo(this, "Lịch Trình Đã Được Lập Hợp Đồng");
                    return;
                }
                if (trangThai.equals("Đã huỷ")) {
                    MsgBox.alertInfo(this, "Lịch Trình Đã Bị Huỷ Bởi Khách Hàng");
                    return;
                }
            }

//            for (LT_KhachHang kh : ltkh) {
//                if (!kh.getMalt().equals(txtMaLT.getText())) {
//                    MsgBox.alertInfo(this, "Vui Lòng Hoàn Tất Lịch Trình Đã Chọn");
//                    return;
//                }
//            }
//
//            for (LT_KhachSan ks : ltks) {
//                if (!lt.getMaLT().equals(ks.getMaLT())) {
//                    MsgBox.alertInfo(this, "Vui Lòng Hoàn Tất Lịch Trình Đã Chọn");
//                    return;
//                }
//            }
//
//            for (LT_DiaDiem dd : ltdd) {
//                if (!lt.getMaLT().equals(dd.getMaLT())) {
//                    MsgBox.alertInfo(this, "Vui Lòng Hoàn Tất Lịch Trình Đã Chọn");
//                    return;
//                }
//            }
//
//            for (LT_PhuongTien pt : ltpt) {
//                if (!lt.getMaLT().equals(pt.getMaLT())) {
//                    MsgBox.alertInfo(this, "Vui Lòng Hoàn Tất Lịch Trình Đã Chọn");
//                    return;
//                }
//            }
            if (!lt.getNgayBD().after(XDate.toDate(date, "dd-MM-yyyy"))) {
                MsgBox.alertInfo(this, "Vui Lòng Lập Hợp Đồng Trước Khi Tour Bắt Đầu");
                return;
            } else {
                new Dialog_XuatHDong(this, true).setVisible(true);
            }
        }
    }

    public void edit() {
        try {
            String malt = tblLT.getValueAt(row, 1).toString();
            LichTrinh lt = ltDao.selectById(malt);
            if (lt != null) {
                fillForm(lt);
                updateStatusButton();
                cboTenTour.setEnabled(false);
                cboTruongDoan.setEnabled(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Record() {
        lblSTT.setText(tblLT.getValueAt(row, 0).toString());
    }

    public void updateStatusButton() {
        boolean edit = (this.row >= 0);

        btnThem.setEnabled(!edit);
        btnSua.setEnabled(edit);
        btnXoa.setEnabled(edit);
    }

    public static void scrollToVisible(final JTable table, final int rowIndex, final int vColIndex) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                table.scrollRectToVisible(table.getCellRect(rowIndex, vColIndex, false));
            }
        });
    }

    public void timKiem() {
        fillTable();
        row = -1;
    }

    public void first() {
        row = 0;
        tblLT.setRowSelectionInterval(row, row);
        scrollToVisible(tblLT, row, row);
        Record();
        edit();
    }

    public void prev() {
        if (row > 0) {
            row = row - 1;
            tblLT.setRowSelectionInterval(row, row);
            scrollToVisible(tblLT, row, row);
            edit();
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Đầu Danh Sách");
        }
        Record();
    }

    public void next() {
        if (this.row < tblLT.getRowCount() - 1) {
            this.row++;
            tblLT.setRowSelectionInterval(row, row);
            edit();
            scrollToVisible(tblLT, row, row);
        } else {
            MsgBox.alertInfo(this, "Bạn Đang Ở Cuối Danh Sách");
        }
        Record();
    }

    public void last() {
        row = tblLT.getRowCount() - 1;
        tblLT.setRowSelectionInterval(row, row);
        scrollToVisible(tblLT, row, row);
        edit();
        Record();
    }

    private void clearForm() {
        JTextField[] txt = new JTextField[]{
            txtSoKhach,
            txtDiaDiem,
            txtPhuThu
        };
        txtMaLT.setText(TruyVanSQL.MaLT());
        new DungChung.DungChung().reset(txt);
        updateStatusButton();
        setDateInTextFilde();
        HideError();
        lblSTT.setText("0");
        tblLT.getSelectionModel().clearSelection();
        txtPhuThu.setText("0");
        row = -1;
    }

    public void ExportExcel() {
        AddLichSuHD.addLSHD("Xuất File Excel");
        new DungChung.ExportExcel().exportExcel("DANH SÁCH THÔNG TIN LỊCH TRÌNH", "Nhân viên", tblLT, new int[]{1500, 3000, 8000, 5000, 6500, 5500, 6000, 6000, 6000});
    }

    private void ExportPDF() {
        AddLichSuHD.addLSHD("Xuất File PDF");
        new ExportPDF().exportPDF("DANH SÁCH THÔNG TIN LỊCH TRÌNH", tblLT);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnG = new javax.swing.ButtonGroup();
        NgayBD = new com.raven.datechooser.DateChooser();
        NgayKT = new com.raven.datechooser.DateChooser();
        choseGioVe = new com.raven.swing.TimePicker();
        choseGioDi = new com.raven.swing.TimePicker();
        pnlCardNhanVien = new javax.swing.JPanel();
        pnlTTKH = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        txtSoKhach = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        btnLamMoi = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnThem = new javax.swing.JButton();
        lblDuaDon = new javax.swing.JLabel();
        btnFrist = new javax.swing.JButton();
        btnPrev = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        btnLast = new javax.swing.JButton();
        lblSTT = new javax.swing.JLabel();
        btnExcel = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txtPhuThu = new javax.swing.JTextField();
        lblLoiPhuThu = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        txtMaLT = new javax.swing.JTextField();
        lblSoKhach = new javax.swing.JLabel();
        btnNgayKT = new javax.swing.JButton();
        txtNgayKT = new javax.swing.JTextField();
        txtNgayBD = new javax.swing.JTextField();
        btnNgayBD = new javax.swing.JButton();
        jLabel32 = new javax.swing.JLabel();
        cboTrangthai = new javax.swing.JComboBox<>();
        cboTenTour = new CboAndTxtSugestion.ComboBoxSuggestion();
        cboTenHDV = new CboAndTxtSugestion.ComboBoxSuggestion();
        cboTruongDoan = new CboAndTxtSugestion.ComboBoxSuggestion();
        txtGiodi = new javax.swing.JTextField();
        txtGioVe = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLT = new DungChung.Table();
        txtFind = new swing.TextFieldAnimation();
        btnGioVe = new javax.swing.JButton();
        btnGioDi = new javax.swing.JButton();
        btnXuatHDong = new javax.swing.JButton();
        txtDiaDiem = new ComboBox.TextFieldSuggestion();
        lblLoiNKT = new javax.swing.JLabel();
        lblLoiGioVe = new javax.swing.JLabel();
        btnLoadBang = new javax.swing.JButton();

        NgayBD.setTextRefernce(txtNgayBD);

        NgayKT.setTextRefernce(txtNgayKT);

        choseGioVe.setForeground(new java.awt.Color(190, 79, 60));
        choseGioVe.setDisplayText(txtGioVe);

        choseGioDi.setForeground(new java.awt.Color(190, 79, 60));
        choseGioDi.setDisplayText(txtGiodi);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        pnlCardNhanVien.setPreferredSize(new java.awt.Dimension(1290, 820));

        pnlTTKH.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(190, 79, 60));
        jLabel7.setText("QUẢN LÝ THÔNG TIN LỊCH TRÌNH");
        pnlTTKH.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 30, -1, -1));

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(190, 79, 60));
        jLabel9.setText("Mã Lịch Trình");
        pnlTTKH.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 90, 110, -1));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(190, 79, 60));
        jLabel18.setText("Tên Hướng Dẫn Viên");
        pnlTTKH.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 180, -1, -1));

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(190, 79, 60));
        jLabel20.setText("Tên Tour Du Lịch");
        pnlTTKH.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, 150, -1));

        jLabel21.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(190, 79, 60));
        jLabel21.setText("Ngày Bắt Đầu");
        pnlTTKH.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 260, 100, -1));

        jLabel25.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(190, 79, 60));
        jLabel25.setText("Số Khách");
        pnlTTKH.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 90, 90, -1));

        txtSoKhach.setBackground(new java.awt.Color(242, 242, 242));
        txtSoKhach.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtSoKhach.setForeground(new java.awt.Color(190, 79, 60));
        txtSoKhach.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtSoKhach.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtSoKhachFocusLost(evt);
            }
        });
        pnlTTKH.add(txtSoKhach, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 110, 260, 30));

        jLabel27.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(190, 79, 60));
        jLabel27.setText("Địa Điểm Đưa Đón Khách");
        pnlTTKH.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 90, -1, -1));

        btnLamMoi.setForeground(new java.awt.Color(255, 255, 255));
        btnLamMoi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonMoi.png"))); // NOI18N
        btnLamMoi.setBorder(null);
        btnLamMoi.setBorderPainted(false);
        btnLamMoi.setContentAreaFilled(false);
        btnLamMoi.setDefaultCapable(false);
        btnLamMoi.setFocusPainted(false);
        btnLamMoi.setFocusable(false);
        btnLamMoi.setIconTextGap(0);
        btnLamMoi.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLamMoiMouseExited(evt);
            }
        });
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnLamMoi, new org.netbeans.lib.awtextra.AbsoluteConstraints(810, 340, -1, -1));

        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonXoa.png"))); // NOI18N
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
        pnlTTKH.add(btnXoa, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 340, -1, -1));

        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonCapNhat.png"))); // NOI18N
        btnSua.setBorder(null);
        btnSua.setBorderPainted(false);
        btnSua.setContentAreaFilled(false);
        btnSua.setDefaultCapable(false);
        btnSua.setFocusPainted(false);
        btnSua.setFocusable(false);
        btnSua.setIconTextGap(0);
        btnSua.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnSuaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnSuaMouseExited(evt);
            }
        });
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnSua, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 340, -1, -1));

        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgButtonThem.png"))); // NOI18N
        btnThem.setBorder(null);
        btnThem.setBorderPainted(false);
        btnThem.setContentAreaFilled(false);
        btnThem.setDefaultCapable(false);
        btnThem.setFocusPainted(false);
        btnThem.setFocusable(false);
        btnThem.setIconTextGap(0);
        btnThem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnThemMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnThemMouseExited(evt);
            }
        });
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnThem, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 340, -1, -1));

        lblDuaDon.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblDuaDon.setForeground(new java.awt.Color(255, 0, 0));
        lblDuaDon.setText("Địa Điểm Đưa Đón");
        pnlTTKH.add(lblDuaDon, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 140, 170, -1));

        btnFrist.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgDau.png"))); // NOI18N
        btnFrist.setBorder(null);
        btnFrist.setBorderPainted(false);
        btnFrist.setContentAreaFilled(false);
        btnFrist.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFrist.setDefaultCapable(false);
        btnFrist.setFocusPainted(false);
        btnFrist.setFocusable(false);
        btnFrist.setIconTextGap(0);
        btnFrist.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnFristMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnFristMouseExited(evt);
            }
        });
        btnFrist.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFristActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnFrist, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 720, -1, -1));

        btnPrev.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgPre.png"))); // NOI18N
        btnPrev.setBorder(null);
        btnPrev.setBorderPainted(false);
        btnPrev.setContentAreaFilled(false);
        btnPrev.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrev.setDefaultCapable(false);
        btnPrev.setFocusPainted(false);
        btnPrev.setFocusable(false);
        btnPrev.setIconTextGap(0);
        btnPrev.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPrevMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPrevMouseExited(evt);
            }
        });
        btnPrev.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrevActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnPrev, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 720, -1, -1));

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgNext.png"))); // NOI18N
        btnNext.setBorder(null);
        btnNext.setBorderPainted(false);
        btnNext.setContentAreaFilled(false);
        btnNext.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNext.setDefaultCapable(false);
        btnNext.setFocusPainted(false);
        btnNext.setFocusable(false);
        btnNext.setIconTextGap(0);
        btnNext.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnNextMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnNextMouseExited(evt);
            }
        });
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnNext, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 720, -1, -1));

        btnLast.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/bgCuoi.png"))); // NOI18N
        btnLast.setBorder(null);
        btnLast.setBorderPainted(false);
        btnLast.setContentAreaFilled(false);
        btnLast.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLast.setDefaultCapable(false);
        btnLast.setFocusPainted(false);
        btnLast.setFocusable(false);
        btnLast.setIconTextGap(0);
        btnLast.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLastMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLastMouseExited(evt);
            }
        });
        btnLast.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLastActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnLast, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 720, -1, -1));

        lblSTT.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSTT.setForeground(new java.awt.Color(55, 38, 91));
        lblSTT.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSTT.setText("0");
        pnlTTKH.add(lblSTT, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 720, 30, -1));

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
        pnlTTKH.add(btnExcel, new org.netbeans.lib.awtextra.AbsoluteConstraints(990, 710, -1, -1));

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
        pnlTTKH.add(btnPDF, new org.netbeans.lib.awtextra.AbsoluteConstraints(1120, 710, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(190, 79, 60));
        jLabel24.setText("Thông Tin Trưởng Đoàn");
        pnlTTKH.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 180, -1, -1));

        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(190, 79, 60));
        jLabel26.setText("Phụ Thu");
        pnlTTKH.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 90, -1, -1));

        txtPhuThu.setBackground(new java.awt.Color(242, 242, 242));
        txtPhuThu.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtPhuThu.setForeground(new java.awt.Color(190, 79, 60));
        txtPhuThu.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtPhuThu.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtPhuThuFocusLost(evt);
            }
        });
        pnlTTKH.add(txtPhuThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 110, 260, 30));

        lblLoiPhuThu.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiPhuThu.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiPhuThu.setText("Phụ Thu Không Hợp Lệ !");
        pnlTTKH.add(lblLoiPhuThu, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 140, 170, -1));

        jLabel30.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(190, 79, 60));
        jLabel30.setText("Ngày Kết Thúc");
        pnlTTKH.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 260, -1, -1));

        txtMaLT.setBackground(new java.awt.Color(242, 242, 242));
        txtMaLT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtMaLT.setForeground(new java.awt.Color(190, 79, 60));
        txtMaLT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        pnlTTKH.add(txtMaLT, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 110, 260, 30));

        lblSoKhach.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblSoKhach.setForeground(new java.awt.Color(255, 0, 0));
        lblSoKhach.setText("Số Khách Không Hợp Lệ");
        pnlTTKH.add(lblSoKhach, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 140, 160, -1));

        btnNgayKT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnNgayKT.setBorder(null);
        btnNgayKT.setBorderPainted(false);
        btnNgayKT.setContentAreaFilled(false);
        btnNgayKT.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgayKT.setDefaultCapable(false);
        btnNgayKT.setFocusPainted(false);
        btnNgayKT.setFocusable(false);
        btnNgayKT.setIconTextGap(0);
        btnNgayKT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgayKTActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnNgayKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(890, 280, 30, 30));

        txtNgayKT.setBackground(new java.awt.Color(242, 242, 242));
        txtNgayKT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgayKT.setForeground(new java.awt.Color(190, 79, 60));
        txtNgayKT.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtNgayKT.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtNgayKTFocusLost(evt);
            }
        });
        pnlTTKH.add(txtNgayKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 280, 230, 30));

        txtNgayBD.setBackground(new java.awt.Color(242, 242, 242));
        txtNgayBD.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtNgayBD.setForeground(new java.awt.Color(190, 79, 60));
        txtNgayBD.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        pnlTTKH.add(txtNgayBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 280, 230, 30));

        btnNgayBD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnNgayBD.setBorder(null);
        btnNgayBD.setBorderPainted(false);
        btnNgayBD.setContentAreaFilled(false);
        btnNgayBD.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNgayBD.setDefaultCapable(false);
        btnNgayBD.setFocusPainted(false);
        btnNgayBD.setFocusable(false);
        btnNgayBD.setIconTextGap(0);
        btnNgayBD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNgayBDActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnNgayBD, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 280, 30, 30));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(190, 79, 60));
        jLabel32.setText("Trạng Thái");
        pnlTTKH.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 180, 110, -1));

        cboTrangthai.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        cboTrangthai.setForeground(new java.awt.Color(190, 79, 60));
        cboTrangthai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chưa đi", "Đang đi", "Đã đi", "Đã huỷ", " " }));
        cboTrangthai.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        pnlTTKH.add(cboTrangthai, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 200, 260, 30));

        cboTenTour.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        cboTenTour.setForeground(new java.awt.Color(190, 79, 60));
        cboTenTour.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlTTKH.add(cboTenTour, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 200, 260, 30));

        cboTenHDV.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        cboTenHDV.setForeground(new java.awt.Color(190, 79, 60));
        cboTenHDV.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlTTKH.add(cboTenHDV, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 200, 260, 30));

        cboTruongDoan.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        cboTruongDoan.setForeground(new java.awt.Color(190, 79, 60));
        cboTruongDoan.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        pnlTTKH.add(cboTruongDoan, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 200, 260, 30));

        txtGiodi.setBackground(new java.awt.Color(242, 242, 242));
        txtGiodi.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGiodi.setForeground(new java.awt.Color(190, 79, 60));
        txtGiodi.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        pnlTTKH.add(txtGiodi, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 280, 230, 30));

        txtGioVe.setBackground(new java.awt.Color(242, 242, 242));
        txtGioVe.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtGioVe.setForeground(new java.awt.Color(190, 79, 60));
        txtGioVe.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        pnlTTKH.add(txtGioVe, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 280, 230, 30));

        jLabel28.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(190, 79, 60));
        jLabel28.setText("Giờ Về");
        pnlTTKH.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 260, -1, -1));

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(190, 79, 60));
        jLabel33.setText("Giờ Đi");
        pnlTTKH.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 260, 90, -1));

        tblLT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblLT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        tblLT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLTMouseClicked(evt);
            }
        });
        tblLT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tblLTKeyPressed(evt);
            }
        });
        jScrollPane2.setViewportView(tblLT);

        pnlTTKH.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 400, 1180, 300));

        txtFind.setAnimationColor(new java.awt.Color(190, 79, 60));
        txtFind.setAutoscrolls(false);
        txtFind.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtFindCaretUpdate(evt);
            }
        });
        pnlTTKH.add(txtFind, new org.netbeans.lib.awtextra.AbsoluteConstraints(980, 20, 250, -1));

        btnGioVe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnGioVe.setBorder(null);
        btnGioVe.setBorderPainted(false);
        btnGioVe.setContentAreaFilled(false);
        btnGioVe.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGioVe.setDefaultCapable(false);
        btnGioVe.setFocusPainted(false);
        btnGioVe.setFocusable(false);
        btnGioVe.setIconTextGap(0);
        btnGioVe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioVeActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnGioVe, new org.netbeans.lib.awtextra.AbsoluteConstraints(1200, 280, 30, 30));

        btnGioDi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icon/down-filled-triangular-arrow.png"))); // NOI18N
        btnGioDi.setBorder(null);
        btnGioDi.setBorderPainted(false);
        btnGioDi.setContentAreaFilled(false);
        btnGioDi.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnGioDi.setDefaultCapable(false);
        btnGioDi.setFocusPainted(false);
        btnGioDi.setFocusable(false);
        btnGioDi.setIconTextGap(0);
        btnGioDi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGioDiActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnGioDi, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 280, 30, 30));

        btnXuatHDong.setForeground(new java.awt.Color(255, 255, 255));
        btnXuatHDong.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonXuatHopDong.png"))); // NOI18N
        btnXuatHDong.setBorder(null);
        btnXuatHDong.setBorderPainted(false);
        btnXuatHDong.setContentAreaFilled(false);
        btnXuatHDong.setDefaultCapable(false);
        btnXuatHDong.setFocusPainted(false);
        btnXuatHDong.setFocusable(false);
        btnXuatHDong.setIconTextGap(0);
        btnXuatHDong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnXuatHDongMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnXuatHDongMouseExited(evt);
            }
        });
        btnXuatHDong.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXuatHDongActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnXuatHDong, new org.netbeans.lib.awtextra.AbsoluteConstraints(860, 710, -1, -1));

        txtDiaDiem.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(190, 79, 60)));
        txtDiaDiem.setForeground(new java.awt.Color(190, 79, 60));
        txtDiaDiem.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtDiaDiem.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDiaDiemFocusLost(evt);
            }
        });
        pnlTTKH.add(txtDiaDiem, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 110, 260, 30));

        lblLoiNKT.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiNKT.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiNKT.setText("Ngày kết thúc không hợp lệ !");
        pnlTTKH.add(lblLoiNKT, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 310, 260, -1));

        lblLoiGioVe.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblLoiGioVe.setForeground(new java.awt.Color(255, 0, 0));
        lblLoiGioVe.setText("Giờ về phải lớn hơn giờ đi !");
        pnlTTKH.add(lblLoiGioVe, new org.netbeans.lib.awtextra.AbsoluteConstraints(970, 310, 160, -1));

        btnLoadBang.setForeground(new java.awt.Color(255, 255, 255));
        btnLoadBang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Hinh/buttonLoadBang.png"))); // NOI18N
        btnLoadBang.setBorder(null);
        btnLoadBang.setBorderPainted(false);
        btnLoadBang.setContentAreaFilled(false);
        btnLoadBang.setDefaultCapable(false);
        btnLoadBang.setFocusPainted(false);
        btnLoadBang.setFocusable(false);
        btnLoadBang.setIconTextGap(0);
        btnLoadBang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnLoadBangMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnLoadBangMouseExited(evt);
            }
        });
        btnLoadBang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoadBangActionPerformed(evt);
            }
        });
        pnlTTKH.add(btnLoadBang, new org.netbeans.lib.awtextra.AbsoluteConstraints(1020, 340, -1, -1));

        javax.swing.GroupLayout pnlCardNhanVienLayout = new javax.swing.GroupLayout(pnlCardNhanVien);
        pnlCardNhanVien.setLayout(pnlCardNhanVienLayout);
        pnlCardNhanVienLayout.setHorizontalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTTKH, javax.swing.GroupLayout.DEFAULT_SIZE, 1403, Short.MAX_VALUE)
        );
        pnlCardNhanVienLayout.setVerticalGroup(
            pnlCardNhanVienLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlTTKH, javax.swing.GroupLayout.DEFAULT_SIZE, 832, Short.MAX_VALUE)
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

    private void btnThemMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseEntered
        new DungChung.DungChung().hoverButton(1, btnThem, "bgButtonThemHover.png");
    }//GEN-LAST:event_btnThemMouseEntered

    private void btnThemMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnThemMouseExited
        new DungChung.DungChung().hoverButton(2, btnThem, "bgButtonThem.png");
    }//GEN-LAST:event_btnThemMouseExited

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (checkRegex()) {
            insert();
        }
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnSua, "bgButtonCapNhatHover.png");
    }//GEN-LAST:event_btnSuaMouseEntered

    private void btnSuaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnSuaMouseExited
        new DungChung.DungChung().hoverButton(2, btnSua, "bgButtonCapNhat.png");
    }//GEN-LAST:event_btnSuaMouseExited

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (checkRegex()) {
            update();
        }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXoa, "bgButtonXoaHover.png");
    }//GEN-LAST:event_btnXoaMouseEntered

    private void btnXoaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXoaMouseExited
        new DungChung.DungChung().hoverButton(2, btnXoa, "bgButtonXoa.png");
    }//GEN-LAST:event_btnXoaMouseExited

    private void btnLamMoiMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseExited
        new DungChung.DungChung().hoverButton(2, btnLamMoi, "bgButtonMoi.png");
    }//GEN-LAST:event_btnLamMoiMouseExited

    private void btnLamMoiMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLamMoiMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLamMoi, "bgButtonMoiHover.png");
    }//GEN-LAST:event_btnLamMoiMouseEntered

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        clearForm();
        cboTruongDoan.setEnabled(true);
        cboTenTour.setEnabled(true);
        btnThem.setEnabled(true);
        btnSua.setEnabled(false);
        btnXoa.setEnabled(false);
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        delete();
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnFristMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseEntered
        new DungChung.DungChung().hoverButton(1, btnFrist, "bgDauHover.png");
    }//GEN-LAST:event_btnFristMouseEntered

    private void btnFristMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnFristMouseExited
        new DungChung.DungChung().hoverButton(2, btnFrist, "bgDau.png");
    }//GEN-LAST:event_btnFristMouseExited

    private void btnFristActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFristActionPerformed
        first();
    }//GEN-LAST:event_btnFristActionPerformed

    private void btnPrevMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPrev, "bgPreHover.png");
    }//GEN-LAST:event_btnPrevMouseEntered

    private void btnPrevMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPrevMouseExited
        new DungChung.DungChung().hoverButton(2, btnPrev, "bgPre.png");
    }//GEN-LAST:event_btnPrevMouseExited

    private void btnPrevActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrevActionPerformed
        prev();
    }//GEN-LAST:event_btnPrevActionPerformed

    private void btnNextMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseEntered
        new DungChung.DungChung().hoverButton(1, btnNext, "bgNextHover.png");
    }//GEN-LAST:event_btnNextMouseEntered

    private void btnNextMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnNextMouseExited
        new DungChung.DungChung().hoverButton(2, btnNext, "bgNext.png");
    }//GEN-LAST:event_btnNextMouseExited

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        next();
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnLastMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLast, "bgCuoiHover.png");
    }//GEN-LAST:event_btnLastMouseEntered

    private void btnLastMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLastMouseExited
        new DungChung.DungChung().hoverButton(2, btnLast, "bgCuoi.png");
    }//GEN-LAST:event_btnLastMouseExited

    private void btnLastActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLastActionPerformed
        last();
    }//GEN-LAST:event_btnLastActionPerformed

    private void btnExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseEntered
        new DungChung.DungChung().hoverButton(1, btnExcel, "bgButtonExcelHover.png");
    }//GEN-LAST:event_btnExcelMouseEntered

    private void btnExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnExcelMouseExited
        new DungChung.DungChung().hoverButton(2, btnExcel, "bgButtonExcel.png");
    }//GEN-LAST:event_btnExcelMouseExited

    private void btnPDFMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseEntered
        new DungChung.DungChung().hoverButton(1, btnPDF, "bgButtonFPTHover.png");
    }//GEN-LAST:event_btnPDFMouseEntered

    private void btnPDFMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPDFMouseExited
        new DungChung.DungChung().hoverButton(2, btnPDF, "bgButtonFPT.png");
    }//GEN-LAST:event_btnPDFMouseExited

    private void btnExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcelActionPerformed
        ExportExcel();
    }//GEN-LAST:event_btnExcelActionPerformed

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPDFActionPerformed
        ExportPDF();
    }//GEN-LAST:event_btnPDFActionPerformed

    private void btnNgayKTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgayKTActionPerformed
        NgayKT.showPopup();
    }//GEN-LAST:event_btnNgayKTActionPerformed

    private void btnNgayBDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNgayBDActionPerformed
        NgayBD.showPopup();
    }//GEN-LAST:event_btnNgayBDActionPerformed

    private void tblLTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLTMouseClicked
        if (evt.getClickCount() == 1) {
            row = tblLT.getSelectedRow();
            edit();
        }
        Record();
    }//GEN-LAST:event_tblLTMouseClicked

    private void txtFindCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtFindCaretUpdate
        timKiem();
    }//GEN-LAST:event_txtFindCaretUpdate

    private void btnGioDiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioDiActionPerformed
        choseGioDi.showPopup(pnlTTKH, 370, 310);
    }//GEN-LAST:event_btnGioDiActionPerformed

    private void btnGioVeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGioVeActionPerformed
        choseGioVe.showPopup(pnlTTKH, 940, 310);
    }//GEN-LAST:event_btnGioVeActionPerformed

    private void btnXuatHDongMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatHDongMouseEntered
        new DungChung.DungChung().hoverButton(1, btnXuatHDong, "buttonXuatHopDong-Hover.png");
    }//GEN-LAST:event_btnXuatHDongMouseEntered

    private void btnXuatHDongMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnXuatHDongMouseExited
        new DungChung.DungChung().hoverButton(2, btnXuatHDong, "buttonXuatHopDong.png");
    }//GEN-LAST:event_btnXuatHDongMouseExited

    private void btnXuatHDongActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXuatHDongActionPerformed
        if (row < 0) {
            MsgBox.alertWarning(this, "Vui Lòng Chọn Lịch Trình Để Lập Hợp Đồng");
            return;
        }
        checkTrangThaiShowHopDong();
    }//GEN-LAST:event_btnXuatHDongActionPerformed

    private void txtSoKhachFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtSoKhachFocusLost
        if (!txtSoKhach.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtSoKhachFocusLost

    private void txtPhuThuFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtPhuThuFocusLost
        if (!txtPhuThu.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtPhuThuFocusLost

    private void txtDiaDiemFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDiaDiemFocusLost
        if (!txtDiaDiem.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtDiaDiemFocusLost

    private void txtNgayKTFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtNgayKTFocusLost
        if (!txtNgayKT.getText().isEmpty()) {
            HideError();
        }
    }//GEN-LAST:event_txtNgayKTFocusLost

    private void tblLTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tblLTKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_DELETE) {
            row = tblLT.getSelectedRow();
            delete();
        }

        if (evt.getKeyCode() == KeyEvent.VK_UP) {
            row = tblLT.getSelectedRow();
            prev();
        }

        if (evt.getKeyCode() == KeyEvent.VK_DOWN) {
            row = tblLT.getSelectedRow();
            next();
        }

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            row = tblLT.getSelectedRow();
            next();
        }
    }//GEN-LAST:event_tblLTKeyPressed

    private void btnLoadBangMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadBangMouseEntered
        new DungChung.DungChung().hoverButton(1, btnLoadBang, "buttonLoadBang-Hover.png");
    }//GEN-LAST:event_btnLoadBangMouseEntered

    private void btnLoadBangMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLoadBangMouseExited
        new DungChung.DungChung().hoverButton(2, btnLoadBang, "buttonLoadBang.png");
    }//GEN-LAST:event_btnLoadBangMouseExited

    private void btnLoadBangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoadBangActionPerformed
        updateStatus();
    }//GEN-LAST:event_btnLoadBangActionPerformed

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
            java.util.logging.Logger.getLogger(Frame_TTLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame_TTLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame_TTLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame_TTLichTrinh.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame_TTLichTrinh().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.raven.datechooser.DateChooser NgayBD;
    private com.raven.datechooser.DateChooser NgayKT;
    private javax.swing.JButton btnExcel;
    private javax.swing.JButton btnFrist;
    private javax.swing.ButtonGroup btnG;
    private javax.swing.JButton btnGioDi;
    private javax.swing.JButton btnGioVe;
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnLast;
    private javax.swing.JButton btnLoadBang;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnNgayBD;
    private javax.swing.JButton btnNgayKT;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnPrev;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JButton btnXuatHDong;
    private CboAndTxtSugestion.ComboBoxSuggestion cboTenHDV;
    public static CboAndTxtSugestion.ComboBoxSuggestion cboTenTour;
    private javax.swing.JComboBox<String> cboTrangthai;
    public static CboAndTxtSugestion.ComboBoxSuggestion cboTruongDoan;
    private com.raven.swing.TimePicker choseGioDi;
    private com.raven.swing.TimePicker choseGioVe;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblDuaDon;
    private javax.swing.JLabel lblLoiGioVe;
    private javax.swing.JLabel lblLoiNKT;
    private javax.swing.JLabel lblLoiPhuThu;
    private javax.swing.JLabel lblSTT;
    private javax.swing.JLabel lblSoKhach;
    private javax.swing.JPanel pnlCardNhanVien;
    private javax.swing.JPanel pnlTTKH;
    private DungChung.Table tblLT;
    private ComboBox.TextFieldSuggestion txtDiaDiem;
    public static swing.TextFieldAnimation txtFind;
    private javax.swing.JTextField txtGioVe;
    private javax.swing.JTextField txtGiodi;
    public static javax.swing.JTextField txtMaLT;
    private javax.swing.JTextField txtNgayBD;
    private javax.swing.JTextField txtNgayKT;
    public static javax.swing.JTextField txtPhuThu;
    private javax.swing.JTextField txtSoKhach;
    // End of variables declaration//GEN-END:variables
}
