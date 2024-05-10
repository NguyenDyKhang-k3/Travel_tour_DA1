package DungChung;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author NHUTLQ
 */
public class SendEmail {

    public static void sendMail(String recepient) throws Exception {
        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        String myAccoutEmail = "traveltourpass@gmail.com";
        String passWord = "mfutbxhmarwteeem";

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccoutEmail, passWord);
            }
        });

        Message message = prepareMessage(session, myAccoutEmail, recepient);

        Transport.send(message);
    }

    private static Message prepareMessage(Session session, String myAccoutEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccoutEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("TRAVEL TOUR CHÚC MỪNG SINH NHẬT QUÝ KHÁCH");
            message.setText("Thân gửi Quý khách\n\n"
                    + "Cảm ơn Quý khách đã luôn tin tưởng và là một thành viên trong chương trình Khách hàng thân thiết\n"
                    + "TravelTour!\n"
                    + "\n"
                    + "TravelTour xin chúc Quý Khách một sinh nhật ấm áp, hạnh phúc, tràn đầy yêu thương bên gia đình và\n"
                    + "những người thân yêu.\n"
                    + "\n"
                    + "Mong rằng Quý khách sẽ tiếp tục đồng hành cùng TravelTour trong những hành trình khám phá, trải\n"
                    + "nghiệm và nghỉ dưỡng của tuổi mới tràn ngập niềm vui phía trước!\n"
                    + "\n"
                    + "Trân trọng!\n"
                    + "\n"
                    + "Bộ phận Trải nghiệm Khách hàng\n"
                    + "Chương trình Khách hàng thân thiết TravelTour Club\n\n"
                    + "Địa chỉ: 888 Nguyễn Văn Linh, phường An Khánh, quận Ninh Kiều Cần Thơ\n"
                    + "Điện thoại: 0898-965-425\n"
                    + "Lịch hẹn: traveltourfpoly.000webhostapp.com\n"
                    + "Gmail: traveltourpass@gmail.com\n"
            );
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            message.setSentDate(XDate.toDate(dtf.format(now), "dd-MM-yyyy"));
            return message;
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
    }
}
