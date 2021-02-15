package notification;

import repository.RepositoryDetails;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Email notification for reporting build output to associated repository committer.
 * The email body contains an HTML-based summary of the build output. If visible, the
 * email will be sent to the email associated with the account that made the last commit.
 *
 * @author Alexander E, Ioana C, Joaquin B Q, Johan H, Theodor M
 *  @since 2021-02-08
 */
public class EmailNotification {
    private final String sender = "sef.ci.group21@gmail.com";
    private final String password = "sef_ci_group21";
    private Session session;
    private Properties properties;
    private MimeMessage email;

    /**
     * Class constructor.
     */
    public EmailNotification(){
    }

    /**
     * Initializes, authenticates, creates and sends a new email.
     * If anny step fails the email is not sent and the error message is
     * outputted on the console.
     *
     * @param status the status of the build and test execution of the repository
     *               if true then build was successful otherwise build failed
     * @param repositoryDetails the details of the repository on which the build was performed
     * @see RepositoryDetails
     */
    public void send(Boolean status, RepositoryDetails repositoryDetails){
        try {
            initializeProperties();
            initializeAndAuthenticate();
            createMessage(status,repositoryDetails);
            addRecipient(repositoryDetails.getPusherEmail());
            sendEmail();
        }catch (MessagingException exception){
            System.out.println("Email error!");
            exception.printStackTrace();
        }
    }

    /**
     * Initializes the properties of the email system
     *
     * Note:
     * Due to private access this method will not be included in the generated JavaDoc HTML files.
     */
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

    /**
     *
     *
     * Note:
     * Due to private access this method will not be included in the generated JavaDoc HTML files.
     */
    private void initializeAndAuthenticate() {
        Authenticator auth = new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        };

        this.session = Session.getInstance(properties,auth);
    }

    /**
     * Creates a new email and sets its body/content, sender email and subject.
     *
     * Note:
     * Due to private access this method will not be included in the generated JavaDoc HTML files.
     *
     * @param status the status of the build and test execution of the repository
     *               if true then build was successful otherwise build failed
     * @param repositoryDetails the details of the repository on which the build was performed
     * @throws MessagingException
     * @see RepositoryDetails
     * @see EmailBuilder
     *
     */
    private void createMessage(Boolean status, RepositoryDetails repositoryDetails) throws MessagingException {
        this.email = new MimeMessage(session);
        email.setFrom(new InternetAddress(sender));

        EmailBuilder emailBuilder = new EmailBuilder(status, repositoryDetails);
        email.setContent(emailBuilder.getContent(),"text/html" );
        email.setSubject("CI server notification");
    }

    /**
     * Adds a recipient to an email. If the provided recipient is invalid the email
     * will be sent at a predefined address (in tis case to the sender itself).
     *
     * Note:
     * Due to private access this method will not be included in the generated JavaDoc HTML files.
     *
     * @param recipient the email of the recipient of the email. If email is hidden (i.e it contains
     *                  "noreply.github.com" then email is sent to default address.
     * @throws MessagingException
     */
    private void addRecipient(String recipient) throws MessagingException {
        if (recipient.contains("noreply.github.com")){
            this.email.addRecipient(Message.RecipientType.TO, new InternetAddress(sender));
        }else {
            this.email.addRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
        }
    }

    /**
     * Sends the created email.
     * If access is denied or the account settings are not appropriate
     * (allow third party applications) then a MessageException is thrown
     * and the email is not sent.
     *
     * Note:
     * Due to private access this method will not be included in the generated JavaDoc HTML files.
     *
     * @throws MessagingException
     */
    private void sendEmail() throws MessagingException{
        Transport.send(this.email);
        System.out.println("Mail sent");
    }
}
