package notification;

import repository.RepositoryDetails;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailNotification {
    private final String sender = "sef.ci.group21@gmail.com";
    private final String password = "sef_ci_group21";
    private Session session;
    private Properties properties;
    private MimeMessage email;

    public EmailNotification(){
        initializeProperties();
        initializeAndAuthenticate();
    }

    public void send(Boolean status, RepositoryDetails repositoryDetails){
        try {
            createMessage(status,repositoryDetails);
            addRecipient(repositoryDetails.getPusherEmail());
            sendEmail();
        }catch (MessagingException exception){
            System.out.println("Email error!");
            exception.printStackTrace();
        }
    }

    private void initializeProperties() {
        this.properties = System.getProperties();
        String port = "465";
        String host = "smtp.gmail.com";
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.ssl.enable", true);
        properties.put("mail.smtp.auth", "true");
    }

    private void initializeAndAuthenticate() {
        Authenticator auth = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        };

        this.session = Session.getInstance(properties,auth);
    }

    private void createMessage(Boolean status, RepositoryDetails repositoryDetails) throws MessagingException {
        this.email = new MimeMessage(session);
        email.setFrom(new InternetAddress(sender));

        EmailBuilder emailBuilder = new EmailBuilder(true, repositoryDetails);
        email.setContent(emailBuilder.getContent(),"text/html" );
        email.setSubject("CI server notification");
        //email.setText(message);
    }

    private void addRecipient(String recipient) throws MessagingException {
        if (recipient.contains("noreply.github.com")){
            this.email.addRecipient(Message.RecipientType.TO, new InternetAddress(sender));
        }else {
            this.email.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
    }

    private void sendEmail() throws MessagingException{
        Transport.send(this.email);
        System.out.println("Mail sent");
    }
}
