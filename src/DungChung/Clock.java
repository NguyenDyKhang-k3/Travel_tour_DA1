package DungChung;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;

/**
 * @author NHUTLQ
 */
public class Clock extends Thread {

    private JLabel lbl;

    public Clock(JLabel lbl) {
        this.lbl = lbl;
    }

    public void run() {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("hh:mm:ss aa");
        while (true) {
            Date now = new Date();
            String st = format.format(now);
            lbl.setText(st);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
            }
        }
    }
}
