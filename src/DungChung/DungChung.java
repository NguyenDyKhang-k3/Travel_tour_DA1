package DungChung;

import DAO.LichTrinhDAO;
import TravelEntity.LichTrinh;
import UI.Frame_LichSuHoatDong;
import UI.Frame_TTLichTrinh;
import static UI.MainForm.tbpMenu;
import java.awt.Cursor;
import static java.awt.Frame.HAND_CURSOR;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;

/**
 *
 * @author NHUTLQ
 */
public class DungChung {

    public void hideLBLError(JLabel[] lbls) {
        for (JLabel lbl : lbls) {
            lbl.setVisible(false);
        }
    }

    public void hoverButton(int so, JButton btn, String hinh) {
        if (so == 1) {
            ImageIcon icon = new ImageIcon("src//Hinh//" + hinh);
            btn.setIcon(icon);
            btn.setCursor(new Cursor(HAND_CURSOR));
        } else {
            ImageIcon icon = new ImageIcon("src//Hinh//" + hinh);
            btn.setIcon(icon);
        }
    }

    public void reset(JTextField[] txts) {
        for (JTextField txt : txts) {
            txt.setText(null);
        }
        txts[0].requestFocus();
        txts[0].setEditable(true);
    }

    public void editColumnWidth(int[] col, JTable tbl) {
        for (int i = 0; i < tbl.getColumnCount(); i++) {
            TableColumn column = tbl.getColumnModel().getColumn(i);
            column.setMinWidth(col[i]);
            column.setMaxWidth(col[i]);
            column.setPreferredWidth(col[i]);
        }
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        tbl.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        ((DefaultTableCellRenderer) tbl.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
    }

    public String convertTien(String tien) {
        double amout = Double.parseDouble(tien);
        return String.format("%,.0f", amout);
    }
}
