import java.util.Date;
import java.util.Properties;
 
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
 
public class Mail {
    private String host;
    private String port;
 
    private boolean debug;
 
    private Properties props;
 
    public Mail(String host, String port) {
    this.host = host;
    this.port = port;
 
    /** Get a Properties object */
    props = System.getProperties();
    }
 
    public Properties addProxy(String host, String port) {
    props.setProperty("proxySet", "true");
    props.setProperty("socksProxyHost", host);
    props.setProperty("socksProxyPort", port);
    return props;
    }
 
    public boolean send(String from, String password, String to, String cc, String title, String text) {
    boolean result = false;
    try {
 
        props.setProperty("mail.smtp.host", this.host);
        props.setProperty("mail.smtp.port", this.port);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "true");
        props.put("mail.store.protocol", "pop3");
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.starttls.enable", "true");
 
        Session session = Session.getDefaultInstance(props, new Authenticator() {
        protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(from, password);
        }
        });
 
        session.setDebug(debug);
 
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress(from));
 
        InternetAddress[] addressTO = { new InternetAddress(to) };
        msg.setRecipients(Message.RecipientType.TO, addressTO);
 
        InternetAddress[] addressCC = { new InternetAddress(cc) };
        msg.setRecipients(Message.RecipientType.CC, addressCC);
 
        InternetAddress addressFROM = new InternetAddress(from);
        msg.setFrom(addressFROM);
 
        msg.setSentDate(new Date());
 
        msg.setSubject(title);
        msg.setText(text);
 
        Transport.send(msg);
        result = true;
    } catch (Exception ex) {
        ex.printStackTrace();
        result = false;
    }
 
    return result;
    }
 
    public boolean isDebug() {
    return debug;
    }
 
    public void setDebug(boolean debug) {
    this.debug = debug;
    }
 
    public static void main(String[] args) {
 
    System.out.println("Send emails example using JavaMail");
    
    String host = "smtp.gmail.com";
    String port = "587";
 
    Mail sender = new Mail(host, port);
 
    /** Activate this line for proxy authentication and change the settings with your details */
    // sender.addProxy("10.10.10.10", "8080");
 
    /** Activate this line if you need to see more details */
    //     sender.setDebug(true);
 
    String from = "sucorreo@gmail.com";
    String password = "su contraseña";
 
    String to = "oscar.roncancio@yopmail.com";
    String cc = "oscar.roncancio@yopmail.com";
 
    String subject = "test";
    String msg = "Message sent using JavaMail.";
 
    System.out.println();
    System.out.println();
    boolean b = sender.send(from, password, to, cc, subject, msg);
    if (b) {
        System.out.println("Message sent successfully.");
    } else {
        System.out.println("Message failed.");
    }
    }
}