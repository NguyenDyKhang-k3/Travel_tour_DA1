package DungChung;

import java.awt.Component;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javaswingdev.Notification;
import javax.swing.JOptionPane;
import raven.glasspanepopup.GlassPanePopup;
import sample.message.ThongBao2;

/**
 * @author NHUTLQ
 */
public class MsgBox {

    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "Thông Báo", JOptionPane.DEFAULT_OPTION);
    }

    public static boolean comfirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "Thông Báo", JOptionPane.YES_NO_OPTION, JOptionPane.DEFAULT_OPTION);
        return result == JOptionPane.YES_OPTION;
    }

    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, "Thông Báo", JOptionPane.DEFAULT_OPTION);
    }

    public static void alertSuccess(Component parent, String message) {
        Notification noti = new Notification((Frame) parent, Notification.Type.SUCCESS, Notification.Location.TOP_RIGHT,
                message);
        noti.showNotification();
    }

    public static void alertInfo(Component parent, String message) {
        Notification noti = new Notification((Frame) parent, Notification.Type.INFO, Notification.Location.TOP_RIGHT,
                message);
        noti.showNotification();
    }

    public static void alertWarning(Component parent, String message) {
        Notification noti = new Notification((Frame) parent, Notification.Type.WARNING, Notification.Location.TOP_RIGHT,
                message);
        noti.showNotification();
    }

    public static void AlerThongBao(String message) {
        ThongBao2 obj = new ThongBao2(message);
        obj.eventOK(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                GlassPanePopup.closePopupLast();
            }
        });
        GlassPanePopup.showPopup(obj);
    }
}
